package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.screens.SettingsScreen;
import me.creese.util.display.Display;

public class ContinueBtn extends DefYellowBtn {
    public ContinueBtn(final Display root) {
        super(root);
        setY(638);
        text = "Продолжить игру";
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                root.getGameViewForName(SettingsScreen.class).onBackPress();
            }
        });
    }
}
