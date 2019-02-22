package me.creese.solitaire.entity.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

import me.creese.solitaire.menu.BottomMenu;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.TopScoreView;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.Shapes;
import me.creese.util.display.Display;

public abstract class BaseGame extends Group {

    private final Sprite logo;
    private final Shapes shapes;

    private Display root;

    public BaseGame() {


        logo = new Sprite(new Texture("splash/splash.png"));
        logo.setOrigin(0,0);
        logo.setScale(416/logo.getWidth());
        logo.setColor(P.BLACK_TRANSPARENT_COLOR);
        logo.setPosition(P.WIDTH/2-(logo.getWidth()*logo.getScaleX())/2,P.HEIGHT/2 - (logo.getHeight()*logo.getScaleY())/2);
        shapes = new Shapes();

    }

    public abstract void start();
    public abstract void restart();
    public abstract void cancelStep();
    public abstract void showHelp();

    public Display getRoot() {
        return root;
    }

    public void setRoot(Display root) {
        this.root = root;

    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            shapes.setProjMatrix(parent.getStage().getCamera().combined);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        logo.draw(batch);
        /*batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);

        SnapshotArray<Actor> children = getChildren();
        for (Actor child : children) {
            if(child instanceof Card) {
                ((Card) child).drawShadow(shapes);

            }
        }
        shapes.flush();
        batch.begin();*/
        super.draw(batch, parentAlpha);

    }
}
