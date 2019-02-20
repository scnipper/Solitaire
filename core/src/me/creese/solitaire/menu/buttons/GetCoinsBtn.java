package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.screens.WinScreen;
import me.creese.util.display.Display;

public class GetCoinsBtn extends DefYellowBtn {
    public GetCoinsBtn(final Display root) {
        super(root);

        setY(175);
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {

                root.getGameViewForName(WinScreen.class).videoCoin();


            }
        });

    }

    public void setCoinCount(int coin) {
        text = "Получить "+coin+" монет";
    }
}
