package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.menu.settings.DifficultSettings;
import me.creese.solitaire.menu.settings.SettingsMenu;
import me.creese.solitaire.screens.SettingsScreen;
import me.creese.solitaire.screens.WinScreen;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class SettingsBtn extends Actor {

    private final Display root;
    private DifficultSettings dificult;
    private Sprite sprite;
    private Sprite icon;

    public SettingsBtn(final Display root) {
        this.root = root;
        dificult = new DifficultSettings();
        setBounds(808, 68, 162, 162);
        addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {

                root.showGameView(SettingsScreen.class);
                //root.showGameView(WinScreen.class);

            }
        });


    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            TexturePrepare texturePrepare = root.getTransitObject(TexturePrepare.class);

            sprite = texturePrepare.getByName(FTextures.CIRCLE_162);
            sprite.setColor(P.YELLOW_COLOR);
            sprite.setPosition(getX(), getY());
            icon = texturePrepare.getByName(FTextures.ICON_MENU);
            icon.setPosition(getX() + (getWidth() / 2 - icon.getWidth() / 2), getY() + (getHeight() / 2 - icon.getHeight() / 2));

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        icon.draw(batch);

    }
}
