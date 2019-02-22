package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class HelpBtn extends Actor {

    private final Texture icon;
    private final Display root;
    private Sprite sprite;

    public HelpBtn(final Display root) {
        this.root = root;
        setBounds(615,68,162,162);
        icon = new Texture("icon_help.png");
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                root.getGameViewForName(GameScreen.class).getBaseGame().showHelp();
            }
        });
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            TexturePrepare texturePrepare = root.getTransitObject(TexturePrepare.class);

            sprite = texturePrepare.getByName(FTextures.CIRCLE_162);
            sprite.setColor(P.BLACK_TRANSPARENT_COLOR);
            sprite.setPosition(getX(),getY());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        batch.setColor(P.YELLOW_COLOR);
        batch.draw(icon,getX()+(getWidth()/2 -icon.getWidth()/2),getY()+(getHeight()/2-icon.getHeight()/2));
        batch.setColor(Color.WHITE);
    }
}
