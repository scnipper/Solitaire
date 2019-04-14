package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;

import me.creese.solitaire.menu.Theme;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.screens.ThemesScreen;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class ThemeItemBtn extends Actor {

    private final Sprite squareBottom;
    private final BitmapFont font;
    private final Texture coin;
    private final int price;
    private final int numTheme;
    private final Display root;
    private boolean isBuy;

    public ThemeItemBtn(final Display root, final int price, final int numTheme) {
        this.root = root;
        this.numTheme = numTheme;
        this.price = price;
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);
        squareBottom = prepare.getByName(FTextures.BIG_SQUARE_BTN);

        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);

        coin = new Texture("coin.png");

        if(numTheme == Theme.CURR_THEME)
        squareBottom.setColor(P.BLACK_TRANSPARENT_COLOR);
        else
        squareBottom.setColor(Theme.themes.get(numTheme).getMainColor());

        isBuy = P.get().pref.getBoolean(S.THEME_BUY + numTheme);


        setWidth(squareBottom.getWidth());
        setHeight(squareBottom.getHeight());
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(numTheme != Theme.CURR_THEME) {
                    if (!isBuy) {
                        int coins = P.get().pref.getInt(S.COIN_COUNT, 700);
                        if (coins >= price * 7) {
                            isBuy = true;
                            coins -= price * 7;

                            P.get().pref.putInt(S.COIN_COUNT, coins);
                            P.get().pref.putBoolean(S.THEME_BUY + numTheme, isBuy);
                            select();
                        }
                    } else {
                        select();
                    }
                }
            }
        });

        setColor(P.TOP_MENU_COLOR);


    }

    public void deselect() {
        squareBottom.setColor(Theme.themes.get(numTheme).getMainColor());
    }
    private void select() {
        squareBottom.setColor(P.BLACK_TRANSPARENT_COLOR);
        Theme.CURR_THEME = numTheme;

        P.get().pref.putInt(S.CURR_THEME,numTheme);
        ThemesScreen themesScreen = root.getGameViewForName(ThemesScreen.class);

        ThemeItemBtn themeItemBtn = themesScreen.getSelectThemeItemBtn();

        if (themeItemBtn != null) {
            themeItemBtn.deselect();
        }

        themesScreen.setSelectedThemeItem(this);

        /*SnapshotArray<Actor> children = getParent().getChildren();
        for (Actor child : children) {
            if(child instanceof ThemeItemBtn) {
                //child.setColor(P.TOP_MENU_COLOR);
                ((ThemeItemBtn) child).deselect();
            }
        }*/

        //setColor(P.SUB_YELLOW_COLOR);
        root.setBackgroundColor(Theme.getCurrentTheme().getMainColor());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        squareBottom.setPosition(getX(),getY());
        squareBottom.draw(batch);
        if(price > 0 && !isBuy) {
            batch.setColor(getColor());
            batch.draw(squareBottom.getTexture(), getX(), getY(), squareBottom.getWidth(), 105, squareBottom.getRegionX(), squareBottom.getRegionY(), ((int) squareBottom.getWidth()), 105, false, true);
            batch.setColor(Color.WHITE);

            batch.draw(coin, getX() + 179, getY() + 24);

            FontUtil.drawText(batch, font, String.valueOf(price), getX() + 10, getY(), 0.65f, P.YELLOW_COLOR, 169, Align.center, false, 105);
        }


    }
}
