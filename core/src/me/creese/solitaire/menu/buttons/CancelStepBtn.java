package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.menu.BottomMenu;

public class CancelStepBtn extends DefButton {
    public CancelStepBtn() {
        setText("Отменить ход");

        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((BottomMenu) getParent()).waitBack();
                ((BaseGame) getParent().getParent()).cancelStep();
            }
        });
    }

}
