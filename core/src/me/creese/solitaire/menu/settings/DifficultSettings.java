package me.creese.solitaire.menu.settings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;

public class DifficultSettings extends SettingsMenu {

    private final Sprite backBtn;

    public DifficultSettings() {
        super();
        setWidth(300);
        setHeight(200);
        setCenter();


        Pixmap pix = new Pixmap(1,1, Pixmap.Format.RGBA8888);

        pix.setColor(Color.LIME);
        pix.fill();

        Texture texture = new Texture(pix);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion((int)getX(),(int)getY(),(int)getWidth(),100);

        backBtn = new Sprite(textureRegion);


        pix.dispose();
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(y < 100) {
                    backBtn.setY(getY());
                    P.get().pref.putBoolean(S.DIF_CELL,false);
                } else {
                    backBtn.setY(getY()+100);
                    P.get().pref.putBoolean(S.DIF_CELL,true);

                }
            }
        });

    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);

        if (parent != null) {
            if (!P.get().pref.getBoolean(S.DIF_CELL)) {
                backBtn.setPosition(getX(),getY());
            } else {
                backBtn.setPosition(getX(),getY()+100);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        backBtn.draw(batch);
        font.getData().setScale(0.9f);
        font.draw(batch,"Сложно",getX(),(getY()+200)-50+(font.getData().getFirstGlyph().height*0.9f)/2,getWidth(), Align.center,false);
        font.draw(batch,"Легко",getX(),(getY()+100)-50+(font.getData().getFirstGlyph().height*0.9f)/2,getWidth(), Align.center,false);
        font.getData().setScale(1);
    }
}
