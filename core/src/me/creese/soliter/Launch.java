package me.creese.soliter;


import com.badlogic.gdx.scenes.scene2d.Stage;

import me.creese.soliter.screens.GameScreen;
import me.creese.util.display.Display;

public class Launch extends Display {

    @Override
    public void create() {
        addListGameViews(new GameScreen(this));

        showGameView(GameScreen.class);
    }


    @Override
    public void resize(int i, int i1) {
        for (Stage stage : getGameViewForName(GameScreen.class).getStages()) {
            stage.getViewport().update(i,i1);
        }
    }
}
