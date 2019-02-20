package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.screens.SettingsScreen;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class DefYellowBtn extends Actor {


    private final BitmapFont font;
    private final Sprite back;
    protected String text = "";

    public DefYellowBtn(final Display root) {
        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);

        back = prepare.getByName(FTextures.DEF_BUTTON_BIG);

        back.setColor(P.YELLOW_COLOR);

        setX(P.WIDTH/2-back.getWidth()/2);
        setWidth(back.getWidth());
        setHeight(back.getHeight());

    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.setPosition(getX(),getY());
        back.draw(batch);

        FontUtil.drawText(batch,font,text,getX(),getY(),0.65f,P.SUB_YELLOW_COLOR,getWidth(), Align.center,false,getHeight());


    }
}
