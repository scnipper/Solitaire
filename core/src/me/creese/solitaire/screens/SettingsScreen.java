package me.creese.solitaire.screens;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.solitaire.menu.CoinCounter;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.buttons.ContinueGameBtn;
import me.creese.solitaire.menu.buttons.ExBtn;
import me.creese.solitaire.menu.buttons.MusicBtn;
import me.creese.solitaire.menu.buttons.SquareBtn;
import me.creese.solitaire.menu.buttons.StdTransparentBtn;
import me.creese.solitaire.menu.buttons.SwitchLevelBtn;
import me.creese.solitaire.menu.buttons.ThemeBtn;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;
import me.creese.util.display.GameView;

public class SettingsScreen extends GameView {
    private Menu menu;

    public SettingsScreen(Display root) {
        super(new FitViewport(P.WIDTH,P.HEIGHT), root,new PolygonSpriteBatch());

        setBackgroundColor(P.BACKGROUND_COLOR);
        StdTransparentBtn ruleBtn = new StdTransparentBtn(root);
        ruleBtn.setMode(StdTransparentBtn.Mode.RULE);
        addActor(ruleBtn);
        StdTransparentBtn restartBtn = new StdTransparentBtn(root);
        restartBtn.setMode(StdTransparentBtn.Mode.RESTART);
        addActor(restartBtn);
        addActor(new ThemeBtn(root));
        addActor(new ContinueGameBtn(root));
        addActor(new CoinCounter(root));

        SquareBtn videoBtn = new SquareBtn(root);
        videoBtn.setMode(SquareBtn.Mode.WATCH_VIDEO);
        SquareBtn adBtn = new SquareBtn(root);
        adBtn.setMode(SquareBtn.Mode.DISABLE_AD);
        SquareBtn instBtn = new SquareBtn(root);
        instBtn.setMode(SquareBtn.Mode.INST);
        addActor(videoBtn);
        addActor(adBtn);
        addActor(instBtn);


        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);
        addActor(new MusicBtn(prepare));
        addActor(new ExBtn(prepare));
        addActor(new SwitchLevelBtn(prepare));
    }

    @Override
    public void onBackPress() {
        getRoot().showGameView(GameScreen.class);
    }

    @Override
    public void addRoot(Display display) {
        super.addRoot(display);
        if (display != null) {
            menu = display.getTransitObject(Menu.class);

            menu.getBottomMenu().setVisible(false);
            menu.getTopScoreView().setTimeStart(false);
            addActor(menu);
        } else {
            menu.getBottomMenu().setVisible(true);
            menu.getTopScoreView().setTimeStart(true);
        }
    }
}