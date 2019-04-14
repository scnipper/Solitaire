package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.menu.Theme;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class BuyCoinsBtn extends Actor {
    private final Sprite back;
    private final int plusCoin;
    private final int price;
    private final BitmapFont font;
    private final Texture coin;

    public BuyCoinsBtn(Display root,int plusCoin,int price,float y) {
        this.plusCoin = plusCoin;
        this.price = price;
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);
        back = prepare.getByName(FTextures.DEF_BUTTON_SETTINGS);

        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        setBounds(P.WIDTH/2-back.getWidth()/2,y,back.getWidth(),back.getHeight());
        back.setPosition(getX(),getY());

        coin = new Texture("coin.png");


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.setColor(Theme.getCurrentTheme().getSubColor());
        back.draw(batch);
        FontUtil.drawText(batch,font,"+ "+plusCoin,getX()+43,getY(),0.65f, Color.WHITE,getWidth(), Align.left,false,getHeight());
        FontUtil.drawText(batch,font,price+" руб",getX(),getY(),0.65f,P.BLACK_TRANSPARENT_COLOR_TEXT,getWidth()-42,Align.right,false,getHeight());
        batch.draw(coin,getX()+245,getY()+getHeight()/2-coin.getHeight()/2.f);
    }
}
