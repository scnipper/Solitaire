package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.screens.CoinsScreen;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class CoinCounter extends Actor {
    private final BitmapFont font;
    private final Sprite back;
    private final Texture coin;
    private final Texture plusIcon;

    public CoinCounter(final Display root) {
        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);

        back = prepare.getByName(FTextures.DEF_BUTTON_SETTINGS);
        back.setColor(P.BLACK_TRANSPARENT_COLOR);
        setBounds(P.WIDTH/2-back.getWidth()/2,1222,back.getWidth(),back.getHeight());
        coin = new Texture("coin.png");
        plusIcon = new Texture("plus_icon.png");
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                root.showGameView(CoinsScreen.class);
            }
        });
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.setPosition(getX(),getY());
        back.draw(batch);

        batch.draw(coin,getX()+43,getY()+getHeight()/2-coin.getHeight()/2);
        batch.draw(plusIcon,getX()+819,getY()+getHeight()/2-plusIcon.getHeight()/2);

        FontUtil.drawText(batch,font,String.valueOf(P.get().pref.getInt(S.COIN_COUNT,700)/7),getX()+129,getY(),0.65f, Color.WHITE,getWidth(), Align.left,false,getHeight());


    }
}
