package me.creese.solitaire.screens;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import me.creese.solitaire.entity.FillBlack;
import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.util.P;
import me.creese.util.display.Display;
import me.creese.util.display.GameView;

public class GameScreen extends GameView {


    private final Stage stageScreen;
    private final Stage stageFit;
    private final FillBlack fillBlack;
    private BaseGame baseGame;

    public GameScreen(Display root) {
        super(new FitViewport(P.WIDTH, P.HEIGHT), root, new PolygonSpriteBatch());

        Stage stage = new Stage(new ScreenViewport());
        addStage(stage, 0);

        stageScreen = new Stage(new ScreenViewport());
        fillBlack = new FillBlack();
        stageScreen.addActor(fillBlack);
        stageFit = new Stage(new FitViewport(P.WIDTH, P.HEIGHT));


    }


    public void showBlack() {
        addStage(stageScreen);


    }

    public void removeBlack() {
        removeStage(stageScreen);
    }

    public FillBlack getFillBlack() {
        return fillBlack;
    }

    public Stage getStageFit() {
        return stageFit;
    }

    public Stage getStageScreen() {
        return stageScreen;
    }

    @Override
    public void addRoot(Display display) {
        super.addRoot(display);

        if (display != null) {
            baseGame.setRoot(display);
            addActor(baseGame);
            baseGame.start();
        }
    }

    public BaseGame getBaseGame() {
        return baseGame;
    }

    public void setBaseGame(BaseGame baseGame) {

        this.baseGame = baseGame;
    }
}
