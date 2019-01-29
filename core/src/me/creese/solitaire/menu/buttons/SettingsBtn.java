package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.menu.settings.DifficultSettings;
import me.creese.solitaire.screens.GameScreen;
import me.creese.util.display.Display;

public class SettingsBtn extends DefButton {

    private DifficultSettings dificult;

    public SettingsBtn() {
        setText("Сложность");
        dificult = new DifficultSettings();
        addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Display root = ((BaseGame) getParent().getParent()).getRoot();

                GameScreen gameScreen = root.getGameViewForName(GameScreen.class);
                gameScreen.showBlack();
                gameScreen.addStage(gameScreen.getStageFit());
                dificult.setRoot(root);
                gameScreen.getStageFit().addActor(dificult);

                Gdx.input.setInputProcessor(new InputMultiplexer(gameScreen.getStageFit(),gameScreen.getStageScreen()));
            }
        });
    }
}
