package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.menu.settings.DifficultSettings;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class SettingsBtn extends Actor {

    private final Display root;
    private DifficultSettings dificult;
    private Sprite sprite;
    private Sprite icon;

    public SettingsBtn(Display root) {
        this.root = root;
        dificult = new DifficultSettings();
        setBounds(808, 68, 162, 162);
        addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {

                getParent().getParent().setVisible(false);
                /*Display root = ((BaseGame) getParent().getParent()).getRoot();

                GameScreen gameScreen = root.getGameViewForName(GameScreen.class);
                gameScreen.showBlack();
                gameScreen.addStage(gameScreen.getStageFit());
                dificult.setRoot(root);
                gameScreen.getStageFit().addActor(dificult);

                Gdx.input.setInputProcessor(new InputMultiplexer(gameScreen.getStageFit(),gameScreen.getStageScreen()));*/
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
