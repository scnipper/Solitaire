package me.creese.solitaire;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import me.creese.solitaire.entity.games.cell.CellGame;
import me.creese.solitaire.entity.games.spider.SpiderGame;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.Theme;
import me.creese.solitaire.screens.CoinsScreen;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.screens.SettingsScreen;
import me.creese.solitaire.screens.ThemesScreen;
import me.creese.solitaire.screens.WinScreen;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class CardsGames extends Display {



    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);

        Theme.init();

        setBackgroundColor(Theme.getCurrentTheme().getMainColor());
        addListGameViews(new Loading(this));
        showGameView(Loading.class);
        P.get().pref = Gdx.app.getPreferences("cell_settings");

        Pixmap pix = new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pix.setColor(Color.WHITE);
        pix.fill();

        P.get().whiteDot = new Texture(pix);
        pix.dispose();


    }

    public void loadOk() {
        addListGameViews(new GameScreen(this));
        addListGameViews(new SettingsScreen(this));
        addListGameViews(new CoinsScreen(this));
        addListGameViews(new WinScreen(this));
        addListGameViews(new ThemesScreen(this));
        getGameViewForName(GameScreen.class).setBaseGame(new SpiderGame());
        showGameView(GameScreen.class);
    }

    @Override
    public void dispose() {
        getTransitObject(TexturePrepare.class).dispose();

    }

    @Override
    public void render() {

        super.render();
    }

    @Override
    public void pause() {
        if (P.get().pref != null) {

            P.get().pref.flush();
        }
    }
}
