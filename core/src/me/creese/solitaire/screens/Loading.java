package me.creese.solitaire.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.solitaire.CardsGames;
import me.creese.solitaire.util.P;
import me.creese.util.display.GameView;


/**
 * Created by yoba2 on 09.04.2017.
 */

public class Loading extends GameView {

    public static final String FONT_ROBOTO = "fonts/font_light.fnt";


    public Loading(CardsGames root) {
        super(new FitViewport(P.WIDTH, P.HEIGHT), root);
        setBackgroundColor(Color.WHITE);
        addActor(new LogoDraw(root));
        load();
    }

    private void load() {
        P.get().asset = new AssetManager();


        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.genMipMaps = true; // enabling mipmaps


        BitmapFontLoader.BitmapFontParameter paramFont = new BitmapFontLoader.BitmapFontParameter();
        paramFont.genMipMaps = true;
        paramFont.magFilter = Texture.TextureFilter.Linear;
        paramFont.minFilter = Texture.TextureFilter.Linear;

        P.get().asset.load(FONT_ROBOTO, BitmapFont.class, paramFont);

    }


    class LogoDraw extends Group {


        private final Sprite splash;
        private final CardsGames root;


        private boolean drawBar;

        LogoDraw(CardsGames root) {
            this.root = root;
            Texture texture = new Texture(Gdx.files.internal("splash/splash.png"), Pixmap.Format.RGBA8888, true);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            splash = new Sprite(texture);

            SequenceAction sequence = new SequenceAction();
            sequence.addAction(Actions.delay(0.3f));

            sequence.addAction(Actions.color(Color.WHITE, 0.5f));

            addAction(sequence);

            setColor(new Color(0));
            //splash.setColor(getColor());
            splash.setPosition(P.WIDTH / 2 - (splash.getWidth() / 2), P.HEIGHT / 2 - (splash.getHeight() / 2));
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            splash.setColor(getColor());
            splash.draw(batch);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            try {
                if (P.get().asset.update()) {

                    if (getActions().size == 0 && !drawBar) {
                        addAction(Actions.color(new Color(0), 1));
                        drawBar = true;

                    }

                }
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }

            if (drawBar && getActions().size == 0) {
                root.loadOk();
                remove();
                splash.getTexture().dispose();
            }
        }

    }

}
