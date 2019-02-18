package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;
import me.creese.solitaire.util.TexturePrepare;

public class SwitchLevelBtn extends Actor {

    private final Sprite back;
    private final BitmapFont font;

    public SwitchLevelBtn(TexturePrepare prepare) {
        back = prepare.getByName(FTextures.TOP_MENU_RECT);
        back.setOrigin(0,0);
        back.setScale(497/back.getWidth());
        back.setColor(P.TOP_MENU_COLOR);
        setBounds(P.WIDTH/2-(back.getWidth()*back.getScaleX())/2,P.HEIGHT-300-back.getHeight()*back.getScaleY(),
                back.getWidth()*back.getScaleX(),back.getHeight()*back.getScaleY());

        back.setPosition(getX(),getY());
        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);

        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean dif = P.get().pref.getBoolean(S.DIF_CELL);
                P.get().pref.putBoolean(S.DIF_CELL,!dif);

            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.draw(batch);
        FontUtil.drawText(batch,font,P.get().pref.getBoolean(S.DIF_CELL) ? "Легкий" : "Сложный",getX(),getY(),0.65f, Color.WHITE,getWidth(), Align.center,false,getHeight());
    }
}
