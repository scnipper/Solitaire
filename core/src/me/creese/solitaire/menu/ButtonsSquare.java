package me.creese.solitaire.menu;

import com.badlogic.gdx.scenes.scene2d.Group;

import me.creese.solitaire.menu.buttons.SquareBtn;
import me.creese.util.display.Display;

public class ButtonsSquare extends Group {
    public ButtonsSquare(Display root) {
        SquareBtn videoBtn = new SquareBtn(root);
        videoBtn.setMode(SquareBtn.Mode.WATCH_VIDEO);
        SquareBtn adBtn = new SquareBtn(root);
        adBtn.setMode(SquareBtn.Mode.DISABLE_AD);
        SquareBtn instBtn = new SquareBtn(root);
        instBtn.setMode(SquareBtn.Mode.INST);
        addActor(videoBtn);
        addActor(adBtn);
        addActor(instBtn);
    }
}
