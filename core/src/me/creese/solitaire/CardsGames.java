package me.creese.solitaire;


import com.badlogic.gdx.scenes.scene2d.Stage;

import me.creese.solitaire.entity.games.cell.CellGame;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.screens.Loading;
import me.creese.util.display.Display;

public class CardsGames extends Display {


    @Override
    public void create() {
        addListGameViews(new Loading(this));
        showGameView(Loading.class);
    }

    public void loadOk() {
        addListGameViews(new GameScreen(this));
        getGameViewForName(GameScreen.class).setBaseGame(new CellGame());
        showGameView(GameScreen.class);
    }
}
