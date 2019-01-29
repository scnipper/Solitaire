package me.creese.solitaire.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class FillBlack extends Actor {
    private final Sprite backFill;
    private Runnable whatClose;

    public FillBlack() {
        setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0x000000aa));
        pix.fill();

        Texture texture = new Texture(pix);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        backFill = new Sprite(textureRegion);


        backFill.setPosition(getX(), getY());

        addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (whatClose != null) {

                    whatClose.run();
                    whatClose = null;
                }
            }
        });

        pix.dispose();
    }

    public void setWhatClose(Runnable whatClose) {
        this.whatClose = whatClose;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        backFill.draw(batch);
    }
}
