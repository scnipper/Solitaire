package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.menu.BottomMenu;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class CancelStepBtn extends Actor {

    private final BitmapFont font;
    private final Texture icon;
    private final Display root;
    private Sprite sprite;

    public CancelStepBtn(final Display root) {
        this.root = root;

        setBounds(109,68,475,162);
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                root.getGameViewForName(GameScreen.class).getBaseGame().cancelStep();
            }
        });

        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        icon = new Texture("icon_back.png");

    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            TexturePrepare texturePrepare = root.getTransitObject(TexturePrepare.class);

            sprite = texturePrepare.getByName(FTextures.STEP_BACK_BTN);
            sprite.setColor(P.BLACK_TRANSPARENT_COLOR);
            sprite.setPosition(getX(),getY());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);

        batch.draw(icon,getX()+79,getY()+(getHeight()/2 - icon.getHeight()/2));

        font.getData().setScale(0.45f);
        font.draw(batch,"Ход назад",getX()+151,getY()+(getHeight()/2+(font.getData().getFirstGlyph().height*0.45f)/2),
                getWidth()-151, Align.center,false);
        font.getData().setScale(1f);
    }
}
