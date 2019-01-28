package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.menu.buttons.CancelStepBtn;
import me.creese.solitaire.util.P;

public class BottomMenu extends Group {
    private final Texture texture;
    private final Texture textureFill;

    public BottomMenu() {
        setBounds(0,0, P.WIDTH,60);
        setDebug(true);

        texture = new Texture("arrow_up.png");

        Pixmap pix = new Pixmap(P.WIDTH, (int) getHeight()+40, Pixmap.Format.RGBA8888);
        pix.setColor(Color.LIGHT_GRAY);
        pix.fill();

        textureFill = new Texture(pix);
        pix.dispose();

        addListener(new ActorGestureListener(){
            @Override
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                if(velocityY > 0) {
                    addAction(Actions.sequence(Actions.moveTo(0,textureFill.getHeight(),0.3f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    waitBack();
                                }
                            })));

                }
            }
        });

        CancelStepBtn cancelStepBtn = new CancelStepBtn();
        cancelStepBtn.setPosition(50,(getY()-textureFill.getHeight())+textureFill.getHeight()/2-cancelStepBtn.getHeight()/2);
        addActor(cancelStepBtn);
    }

    public void waitBack() {
        getActions().clear();

        addAction(Actions.sequence(Actions.delay(2),Actions.moveTo(0,0,0.3f)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(textureFill,0,getY()-textureFill.getHeight());
        batch.draw(texture,P.WIDTH/2.f-texture.getWidth()/2.f,getY());

        super.draw(batch, parentAlpha);

    }
}
