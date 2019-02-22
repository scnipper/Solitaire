package me.creese.solitaire.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.solitaire.menu.WinCircle;
import me.creese.solitaire.menu.buttons.GetCoinsBtn;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;
import me.creese.util.display.GameView;

public class WinScreen extends GameView {

    private static final String NO_THANKS = "Нет, спасибо";
    private final Stage fillStage;
    private final WinCircle winCircle;
    private final GetCoinsBtn getCoinsBtn;
    private final NoBtn noBtn;

    public WinScreen(Display root) {
        super(new FitViewport(P.WIDTH, P.HEIGHT), root);
        fillStage = new Stage(new FillViewport(P.WIDTH, P.HEIGHT), new PolygonSpriteBatch());
        fillStage.addActor(new BlackRect());

        addStage(fillStage,0);
        winCircle = new WinCircle(root);
        getCoinsBtn = new GetCoinsBtn(root);
        addActor(getCoinsBtn);
        noBtn = new NoBtn();

    }

    public void videoCoin() {
        getCoinsBtn.setCoinCount(60);
        winCircle.videoCoinsSet(60);

        addActor(noBtn);
        getCoinsBtn.addAction(Actions.moveTo(getCoinsBtn.getX(),268,0.3f));
    }

    @Override
    public void addRoot(Display display) {
        super.addRoot(display);
        if (display != null) {
            addActor(winCircle);
            winCircle.setCoinWin(85);
            getCoinsBtn.setCoinCount(85);

        } else {
            winCircle.remove();
        }
    }

    class BlackRect extends Actor {

        private final PolygonRegion region;

        public BlackRect() {
            region = new PolygonRegion(new TextureRegion(P.get().whiteDot), new float[]{0, 0, 0, 960, P.WIDTH, 960, P.WIDTH, 0}, new short[]{0, 1, 2, 0, 2, 3});
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(P.TOP_MENU_COLOR);
            ((PolygonSpriteBatch) batch).draw(region, 0, 960);
            batch.setColor(Color.WHITE);
        }
    }

    class NoBtn extends Actor {

        private final BitmapFont font;

        public NoBtn() {
            font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
            setBounds(76,59,928,174);
            addListener(new ActorGestureListener(){
                @Override
                public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    getRoot().getGameViewForName(GameScreen.class).getBaseGame().restart();
                    getRoot().showGameView(GameScreen.class);

                }
            });
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            FontUtil.drawText(batch,font,NO_THANKS,getX(),getY(),0.65f,Color.WHITE,getWidth(), Align.center,false,getHeight());
        }
    }
}
