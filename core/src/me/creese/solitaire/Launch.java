package me.creese.solitaire;


import com.badlogic.gdx.scenes.scene2d.Stage;

import me.creese.util.display.Display;

public class Launch extends Display {

    @Override
    public void create() {
        addListGameViews(new me.creese.solitaire.screens.GameScreen(this));

        showGameView(me.creese.solitaire.screens.GameScreen.class);
    }


    @Override
    public void resize(int i, int i1) {
        for (Stage stage : getGameViewForName(me.creese.solitaire.screens.GameScreen.class).getStages()) {
            stage.getViewport().update(i,i1);
        }
    }
}
