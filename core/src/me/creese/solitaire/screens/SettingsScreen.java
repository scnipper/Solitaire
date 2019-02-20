package me.creese.solitaire.screens;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.solitaire.menu.ButtonsSquare;
import me.creese.solitaire.menu.CoinCounter;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.buttons.ContinueBtn;
import me.creese.solitaire.menu.buttons.DefYellowBtn;
import me.creese.solitaire.menu.buttons.ExBtn;
import me.creese.solitaire.menu.buttons.MusicBtn;
import me.creese.solitaire.menu.buttons.StdTransparentBtn;
import me.creese.solitaire.menu.buttons.SwitchLevelBtn;
import me.creese.solitaire.menu.buttons.ThemeBtn;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;
import me.creese.util.display.GameView;

public class SettingsScreen extends GameView {
    private final ButtonsSquare buttonsSquare;
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
        addActor(new ContinueBtn(root));
        addActor(new CoinCounter(root));

        buttonsSquare = new ButtonsSquare(root);
        root.addTransitObject(buttonsSquare);



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
            addActor(buttonsSquare);
            menu.getBottomMenu().setVisible(false);
            menu.getTopScoreView().setTimeStart(false);
            addActor(menu);
        } else {
            menu.getBottomMenu().setVisible(true);
            menu.getTopScoreView().setTimeStart(true);
        }
    }
}
