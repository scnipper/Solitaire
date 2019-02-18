package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;

public class MusicBtn extends Actor {

    private final Sprite back;
    private final Texture icon;

    public MusicBtn(TexturePrepare prepare) {
        back = prepare.getByName(FTextures.CIRCLE_162);
        back.setColor(P.BLACK_TRANSPARENT_COLOR);
        setBounds(76,P.HEIGHT-300-back.getHeight(),back.getWidth(),back.getHeight());
        icon = new Texture("icon_music.png");
        back.setPosition(getX(),getY());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.draw(batch);
        batch.draw(icon,getX()+getWidth()/2-icon.getWidth()/2,getY()+getHeight()/2-icon.getHeight()/2);
    }
}
