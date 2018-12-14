package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import me.creese.solitaire.util.P;

public class TopScoreView extends Actor {
    private final Texture textureBack;

    public TopScoreView() {
        setBounds(0, P.HEIGHT-40,P.WIDTH,40);

        Pixmap pix = new Pixmap(P.WIDTH,40,Pixmap.Format.RGBA8888);
        pix.setColor(Color.CORAL);
        pix.fill();

        textureBack = new Texture(pix);
        pix.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureBack,getX(),getY());
    }
}
