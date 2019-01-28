package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.P;

public class TopScoreView extends Actor {
    private final Texture textureBack;
    private final BitmapFont font;
    private boolean timeStart;
    private float time;
    private int step;
    private int score;

    public TopScoreView() {
        setBounds(0, P.HEIGHT-40,P.WIDTH,40);

        Pixmap pix = new Pixmap(P.WIDTH,40,Pixmap.Format.RGBA8888);
        pix.setColor(Color.CORAL);
        pix.fill();

        textureBack = new Texture(pix);
        pix.dispose();

        font = P.get().asset.get(Loading.FONT_ROBOTO,BitmapFont.class);
    }

    /**
     * From seconds to format hh:mm:ss
     *
     * @param time in sec
     * @return
     */
    public static String formatTime(long time) {
        long tmp = time;
        int hour = (int) (time / 3600);
        tmp -= hour * 3600;
        int min = (int) (tmp / 60);
        int sec = (int) (tmp - min * 60);

        String hourText = hour < 10 ? "0" + hour : "" + hour;
        String minText = min < 10 ? "0" + min : "" + min;
        String secText = sec < 10 ? "0" + sec : "" + sec;


        return hourText + ":" + minText + ":" + secText;
    }

    /**
     * Добавление очков
     * @param numAdd
     */
    public void addScore(int numAdd) {
        score += numAdd;
    }
    /**
     * Итерация ходов
     */
    public void iterateStep() {
        step++;
    }

    public void startTime() {
        time = 0;
        timeStart = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(timeStart) {
            time += delta;
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureBack,getX(),getY());

        font.getData().setScale(0.35f);
        font.setColor(Color.BLACK);
        font.draw(batch,formatTime((long) time)+"           Ход     "+step+"            Счет    "+score,0,getY()+getHeight()/2+(font.getData().getFirstGlyph().height*0.35f) / 2,P.WIDTH, Align.center,false);
        font.setColor(Color.WHITE);
        font.getData().setScale(1f);
    }
}
