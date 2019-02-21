package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;

import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class ThemeItemBtn extends Actor {

    private final Sprite squareBottom;
    private final BitmapFont font;
    private final Texture coin;
    private final int price;

    public ThemeItemBtn(final Display root, int price, final int numTheme) {
        this.price = price;
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);
        squareBottom = prepare.getByName(FTextures.BIG_SQUARE_BTN);

        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);

        coin = new Texture("coin.png");

        squareBottom.setColor(Theme.themes.get(numTheme).getMainColor());

        setWidth(squareBottom.getWidth());
        setHeight(squareBottom.getHeight());
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Theme.CURR_THEME = numTheme;


                SnapshotArray<Actor> children = getParent().getChildren();
                for (Actor child : children) {
                    if(child instanceof ThemeItemBtn) {
                        child.setColor(P.TOP_MENU_COLOR);
                    }
                }

                setColor(P.SUB_YELLOW_COLOR);
                root.setBackgroundColor(Theme.getCurrentTheme().getMainColor());
            }
        });

        setColor(P.TOP_MENU_COLOR);


    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        squareBottom.setPosition(getX(),getY());
        squareBottom.draw(batch);
        batch.setColor(getColor());
        batch.draw(squareBottom.getTexture(),getX(),getY(),squareBottom.getWidth(),
               105,squareBottom.getRegionX(),squareBottom.getRegionY(),
               ((int) squareBottom.getWidth()), 105,false,true);
        batch.setColor(Color.WHITE);

        batch.draw(coin,getX()+179,getY()+24);

        FontUtil.drawText(batch,font,String.valueOf(price),getX()+10,getY(),0.65f,P.YELLOW_COLOR,
                169, Align.center,false,105);


    }
}
