package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import me.creese.solitaire.util.P;

public class Logo extends Actor {
    private final Sprite logo;

    public Logo() {
        logo = new Sprite(new Texture("splash/splash.png"));
        logo.setOrigin(0, 0);
        logo.setScale(416 / logo.getWidth());
        logo.setColor(P.BLACK_TRANSPARENT_COLOR);
        logo.setPosition(P.WIDTH / 2 - (logo.getWidth() * logo.getScaleX()) / 2, P.HEIGHT / 2 - (logo.getHeight() * logo.getScaleY()) / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        logo.draw(batch);
    }
}
