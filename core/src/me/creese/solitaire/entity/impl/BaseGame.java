package me.creese.solitaire.entity.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.Random;

import me.creese.solitaire.util.Shapes;
import me.creese.util.display.Display;

public abstract class BaseGame extends Group {

    private final Shapes shapes;
    private final Random random;

    private Display root;

    public BaseGame() {
        random = new Random();


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

    public Random getRandom() {
        return random;
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
