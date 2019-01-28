package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.P;

public abstract class DefButton extends Actor {
    private final Texture fillTexture;
    private final BitmapFont font;
    private String text;

    public DefButton() {
        setWidth(230);
        setHeight(70);
        Pixmap pix = new Pixmap((int)getWidth(),(int)getHeight(), Pixmap.Format.RGBA8888);
        pix.setColor(Color.ORANGE);
        pix.fill();
        font = P.get().asset.get(Loading.FONT_ROBOTO, BitmapFont.class);

        fillTexture = new Texture(pix);


    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(fillTexture,getX(),getY());

        font.getData().setScale(0.4f);
        font.draw(batch,text,getX(),getY()+getHeight()/2+(font.getData().getFirstGlyph().height*0.4f)/2,getWidth(), Align.center,false);
        font.getData().setScale(1f);
    }
}
