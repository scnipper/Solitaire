package me.creese.solitaire.menu.buttons;

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

public class SquareBtn extends Actor {

    private final Sprite back;
    private final BitmapFont font;
    private final BitmapFont fontBold;
    private Mode mode;
    private String text;
    private DrawBottom drawBottom;

    public enum Mode {
        DISABLE_AD,WATCH_VIDEO,INST
    }

    interface DrawBottom {
        void draw(Batch batch);
    }
    public SquareBtn(Display root) {
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);

        back = prepare.getByName(FTextures.BIG_SQUARE_BTN);

        setWidth(back.getWidth());
        setHeight(back.getHeight());
        font = P.get().asset.get(Loading.FONT_ROBOTO, BitmapFont.class);
        fontBold = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);

    }

    public void setMode(Mode mode) {
        this.mode = mode;

        switch (mode) {
            case DISABLE_AD:
                back.setColor(P.YELLOW_COLOR);
                setColor(P.SUB_YELLOW_COLOR);
                text = "Отключить рекламу";
                setPosition(76,906);

                drawBottom = new DrawBottom() {
                    @Override
                    public void draw(Batch batch) {
                        FontUtil.drawText(batch,fontBold,"250 руб",getX(),(getY()+getHeight())-170,0.65f,Theme.getCurrentTheme().getSubColor(),getWidth(),Align.center);
                    }
                };

                break;
            case WATCH_VIDEO:
                back.setColor(Theme.getCurrentTheme().getSubColor());
                text = "Посмотреть видео";
                setPosition(396,906);

                final Texture coin = new Texture("coin.png");
                drawBottom = new DrawBottom() {
                    @Override
                    public void draw(Batch batch) {
                        FontUtil.drawText(batch,fontBold,"+40",getX(),(getY()+getHeight())-170,0.65f,P.YELLOW_COLOR,
                                168,Align.right);
                        batch.draw(coin,getX()+180,getY()+65);
                    }
                };
                break;
            case INST:
                back.setColor(Theme.getCurrentTheme().getSubColor());
                text = "Наш инстаграм";
                setPosition(716,906);

                final Texture inst = new Texture("inst_icon.png");
                drawBottom = new DrawBottom() {
                    @Override
                    public void draw(Batch batch) {
                        batch.draw(inst,getX()+getWidth()/2-inst.getWidth()/2,getY()+51);
                    }
                };
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.setPosition(getX(),getY());
        back.draw(batch);
        FontUtil.drawText(batch,font,text,getX(),(getY()+getHeight())-31,0.5f,getColor(),getWidth(), Align.center,true);

        drawBottom.draw(batch);
    }
}
