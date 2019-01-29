package me.creese.solitaire.menu.settings;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.FillBlack;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.P;
import me.creese.util.display.Display;

public class SettingsMenu extends Group {
    private Sprite backFill;
    protected final BitmapFont font;
    private final TextureRegion textureRegion;
    private Display root;

    public SettingsMenu() {
        font = P.get().asset.get(Loading.FONT_ROBOTO, BitmapFont.class);

        Pixmap pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.ORANGE);
        pix.fill();

        Texture texture = new Texture(pix);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        textureRegion = new TextureRegion(texture);


        pix.dispose();

    }
    public void setCenter() {
        setPosition(P.WIDTH/2.f-getWidth()/2.f,P.HEIGHT/2.f-getHeight()/2.f);
        textureRegion.setRegion((int) getX(),(int)getY(),(int)getWidth(),(int)getHeight());
        backFill = new Sprite(textureRegion);
        backFill.setPosition(getX(),getY());
    }

    private void closeMenu() {
        GameScreen gameScreen = root.getGameViewForName(GameScreen.class);

        gameScreen.removeBlack();
        gameScreen.removeStage(getStage());
        remove();
        gameScreen.updateInput();
    }

    public void setRoot(Display root) {
        this.root = root;
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            FillBlack fillBlack = root.getGameViewForName(GameScreen.class).getFillBlack();
            fillBlack.setWhatClose(new Runnable() {
                @Override
                public void run() {
                    closeMenu();
                }
            });
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        backFill.draw(batch);
        super.draw(batch, parentAlpha);

    }

}
