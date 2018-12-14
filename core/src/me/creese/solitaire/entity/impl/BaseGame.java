package me.creese.solitaire.entity.impl;

import com.badlogic.gdx.scenes.scene2d.Group;

import me.creese.solitaire.menu.TopScoreView;

public abstract class BaseGame extends Group {

    public BaseGame() {
        addActor(new TopScoreView());
    }

    public abstract void start();
    public abstract void restart();
}
