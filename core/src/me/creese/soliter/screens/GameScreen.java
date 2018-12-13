package me.creese.soliter.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.soliter.entity.PackCard;
import me.creese.soliter.util.P;
import me.creese.util.display.Display;
import me.creese.util.display.GameView;

public class GameScreen extends GameView {
    private final PackCard packCard;

    public GameScreen(Display root) {
        super(new FitViewport(P.WIDTH,P.HEIGHT), root);

        Stage stage = new Stage(new FillViewport(P.WIDTH,P.HEIGHT));
        addStage(stage,0);

        final Texture back = new Texture("back.png");
        back.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);

        final TextureRegion region = new TextureRegion(back);
        region.setRegion(0,0,P.WIDTH,P.HEIGHT);
        stage.addActor(new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {

                batch.draw(region,0,0);
            }
        });
        packCard = new PackCard();
        addActor(packCard);

    }

    @Override
    public void addRoot(Display display) {
        super.addRoot(display);

        if (display != null) {
            packCard.start();
        }
    }
}
