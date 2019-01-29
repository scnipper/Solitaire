package me.creese.solitaire;


import com.badlogic.gdx.Gdx;

import me.creese.solitaire.entity.games.cell.CellGame;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.P;
import me.creese.util.display.Display;

public class CardsGames extends Display {


    @Override
    public void create() {
        addListGameViews(new Loading(this));
        showGameView(Loading.class);
        P.get().pref = Gdx.app.getPreferences("cell_settings");
    }

    public void loadOk() {
        addListGameViews(new GameScreen(this));
        getGameViewForName(GameScreen.class).setBaseGame(new CellGame());
        showGameView(GameScreen.class);
    }

    @Override
    public void pause() {
        if (P.get().pref != null) {

            P.get().pref.flush();
        }
    }
}
