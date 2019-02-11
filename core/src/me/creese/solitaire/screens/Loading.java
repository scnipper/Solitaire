package me.creese.solitaire.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.solitaire.CardsGames;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.Shapes;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.GameView;


/**
 * Created by yoba2 on 09.04.2017.
 */

public class Loading extends GameView {

    public static final String FONT_ROBOTO = "fonts/font_light.fnt";
    public static final String FONT_ROBOTO_BOLD = "fonts/font_bold.fnt";
    private TexturePrepare prep;


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
        P.get().asset.load(FONT_ROBOTO_BOLD, BitmapFont.class, paramFont);

        loadFrameTextures();
    }

    private void loadFrameTextures() {
        prep = new TexturePrepare();
        getRoot().addTransitObject(prep);
        final Shapes shape = new Shapes();
        prep.setPaddingX(2);
        prep.setPaddingY(2);
        //prep.setDebugImage(true);
        prep.setPreAndPostDraw(new TexturePrepare.PreAndPostDraw() {
            @Override
            public void drawPre() {
                Gdx.gl.glDepthMask(false);
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }

            @Override
            public void drawPost() {
                shape.flush();
                Gdx.gl.glDepthMask(true);
            }
        });
        shape.setProjMatrix(prep.getCamera().combined);

        prep.addDraw(FTextures.STEP_BACK_BTN, 475, 162, new TexturePrepare.Draw() {
            @Override
            public void draw(float bX, float bY) {
                shape.setColor(P.BLACK_TRANSPARENT_COLOR);
                shape.rectRound(bX,bY,475,162,81);
                shape.setColor(Color.WHITE);
            }
        });
        prep.addDraw(FTextures.TOP_MENU_RECT, 450, 147, new TexturePrepare.Draw() {
            @Override
            public void draw(float bX, float bY) {
                shape.rectRound(bX,bY,450,147,52);
            }
        });

        prep.start();

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
                if (P.get().asset.update() && prep.isLoad()) {

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
