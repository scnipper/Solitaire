package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.menu.Theme;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.screens.ThemesScreen;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class ThemeBtn extends Actor {
    private static final String TEXT = "Сменить тему и карты";
    private final BitmapFont font;
    private final Sprite back;
    private final Texture icon;

    public ThemeBtn(final Display root) {
        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);

        back = prepare.getByName(FTextures.DEF_BUTTON_SETTINGS);
        back.setColor(Theme.getCurrentTheme().getSubColor());

        setBounds(P.WIDTH/2-back.getWidth()/2,468,back.getWidth(),back.getHeight());
        icon = new Texture("theme_btn_icon.png");

        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                root.showGameView(ThemesScreen.class);
            }
        });


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.setPosition(getX(),getY());
        back.draw(batch);
        batch.draw(icon,getX()+732,getY()+getHeight()/2-icon.getHeight()/2);

        FontUtil.drawText(batch,font,TEXT,getX(),getY(),0.65f, Color.WHITE,732, Align.center,false,getHeight());
    }
}
