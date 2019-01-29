package me.creese.solitaire.entity.impl;

import com.badlogic.gdx.scenes.scene2d.Group;

import me.creese.solitaire.menu.BottomMenu;
import me.creese.solitaire.menu.TopScoreView;
import me.creese.util.display.Display;

public abstract class BaseGame extends Group {

    private final TopScoreView topScoreView;
    private Display root;

    public BaseGame() {

        topScoreView = new TopScoreView();
        addActor(topScoreView);
        addActor(new BottomMenu());
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

    public TopScoreView getTopScoreView() {
        return topScoreView;
    }
}
