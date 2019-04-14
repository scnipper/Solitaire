package me.creese.solitaire.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.solitaire.menu.ButtonsSquare;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.buttons.BuyCoinsBtn;
import me.creese.solitaire.menu.buttons.StdTransparentBtn;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;
import me.creese.util.display.Display;
import me.creese.util.display.GameView;

public class CoinsScreen extends GameView {
    private final GlyphLayout glyph;
    private final BitmapFont font;
    private ButtonsSquare buttonsSquare;
    private Menu menu;

    public CoinsScreen(Display root) {
        super(new FitViewport(P.WIDTH, P.HEIGHT), root, new PolygonSpriteBatch());


        StdTransparentBtn btn = new StdTransparentBtn(root);
        btn.setMode(StdTransparentBtn.Mode.EXIT);
        addActor(btn);

        addActor(new BuyCoinsBtn(root, 500, 15, 1308));
        addActor(new BuyCoinsBtn(root, 1000, 25, 1138));
        addActor(new BuyCoinsBtn(root, 3000, 65, 968));
        addActor(new BuyCoinsBtn(root, 7000, 125, 798));

        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        final Texture coin = new Texture("coin.png");

        glyph = new GlyphLayout();

        addActor(new Actor() {
            float xFont = 0;

            @Override
            public void draw(Batch batch, float parentAlpha) {
                font.getData().setScale(0.65f);
                xFont = P.WIDTH / 2.f - glyph.width / 2;
                font.draw(batch, glyph, xFont, P.HEIGHT - 338);
                font.getData().setScale(1f);

                batch.draw(coin, xFont - 90, P.HEIGHT - 390);


            }
        });

    }

    @Override
    public void addRoot(Display display) {
        super.addRoot(display);
        if (display != null) {
            font.getData().setScale(0.65f);
            glyph.setText(font, P.get().pref.getInt(S.COIN_COUNT,700)/7+"");
            font.getData().setScale(1f);
            buttonsSquare = display.getTransitObject(ButtonsSquare.class);

            addActor(buttonsSquare);
            buttonsSquare.moveBy(0, -604);

            menu = display.getTransitObject(Menu.class);
            addActor(menu);

            menu.getBottomMenu().setVisible(false);
            menu.getTopScoreView().setTimeStart(false);
            menu.getTopScoreView().setCustomHeadText("Монеты");
        } else {
            buttonsSquare.moveBy(0, 604);
            menu.getTopScoreView().setCustomHeadText(null);

        }
    }

    @Override
    public void onBackPress() {
        getRoot().showGameView(getRoot().getPrevView());
    }
}
