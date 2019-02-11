package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import me.creese.solitaire.menu.buttons.CancelStepBtn;
import me.creese.solitaire.menu.buttons.HelpBtn;
import me.creese.solitaire.menu.buttons.SettingsBtn;
import me.creese.solitaire.util.P;
import me.creese.util.display.Display;

public class BottomMenu extends Group {


    private final HelpBtn helpBtn;
    private CancelStepBtn cancelStepBtn;
    private SettingsBtn settingsBtn;

    public BottomMenu(Display root) {
        cancelStepBtn = new CancelStepBtn(root);
        settingsBtn = new SettingsBtn(root);
        helpBtn = new HelpBtn(root);
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {

            addActor(cancelStepBtn);
            addActor(settingsBtn);
            addActor(helpBtn);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);

    }
}
