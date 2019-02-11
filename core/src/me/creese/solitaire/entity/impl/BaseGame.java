package me.creese.solitaire.entity.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;

import me.creese.solitaire.menu.BottomMenu;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.TopScoreView;
import me.creese.solitaire.util.P;
import me.creese.util.display.Display;

public abstract class BaseGame extends Group {

    private final Sprite logo;

    private Display root;

    public BaseGame() {


        logo = new Sprite(new Texture("splash/splash.png"));
        logo.setOrigin(0,0);
        logo.setScale(416/logo.getWidth());
        logo.setColor(P.BLACK_TRANSPARENT_COLOR);
        logo.setPosition(P.WIDTH/2-(logo.getWidth()*logo.getScaleX())/2,P.HEIGHT/2 - (logo.getHeight()*logo.getScaleY())/2);

    }

    public abstract void start();
    public abstract void restart();
    public abstract void cancelStep();

    public Display getRoot() {
        return root;
    }

    public void setRoot(Display root) {
        this.root = root;

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        logo.draw(batch);
        super.draw(batch, parentAlpha);
    }
}
