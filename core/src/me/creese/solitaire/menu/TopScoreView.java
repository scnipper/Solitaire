package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class TopScoreView extends Actor {

    private final BitmapFont font;
    private final PolygonRegion polygonRegion;
    private final BitmapFont fontBold;
    private final Display root;
    private boolean isTimeStart;
    private float time;
    private int step;
    private int score;
    private Sprite topRect;
    private float targetWidthFont;
    private Color fontColor;

    public TopScoreView(Display root) {
        this.root = root;
        setBounds(0, P.HEIGHT-226,P.WIDTH,226);

       Pixmap pix = new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pix.setColor(Color.WHITE);
        pix.fill();

        Texture textureBack = new Texture(pix);
        pix.dispose();

        fontColor = new Color(0x678A6Cff);

        polygonRegion = new PolygonRegion(new TextureRegion(textureBack), new float[]{
                0, 0,
                0, getHeight(),
                P.WIDTH, getHeight(),
                P.WIDTH, 0},
                new short[]{0, 1, 2,
                        0, 2, 3});

        font = P.get().asset.get(Loading.FONT_ROBOTO,BitmapFont.class);
        fontBold = P.get().asset.get(Loading.FONT_ROBOTO_BOLD,BitmapFont.class);
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

    public void decrementStep() {
        step--;
    }

    public void startTime() {
        time = 0;
        isTimeStart = true;
    }

    public void setTimeStart(boolean timeStart) {
        isTimeStart = timeStart;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isTimeStart) {
            time += delta;
        }

    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {

            TexturePrepare texturePrepare = root.getTransitObject(TexturePrepare.class);

            topRect = texturePrepare.getByName(FTextures.TOP_MENU_RECT);
            targetWidthFont = P.WIDTH / 2 - topRect.getWidth() / 2;
            topRect.setPosition(targetWidthFont,P.HEIGHT-300);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(P.TOP_MENU_COLOR);
        ((PolygonSpriteBatch) batch).draw(polygonRegion,getX(),getY());
        batch.setColor(Color.WHITE);

        topRect.setColor(P.BACKGROUND_COLOR);
        topRect.draw(batch);

        fontBold.getData().setScale(0.6f);
        fontBold.draw(batch,formatTime((long) time),0,P.HEIGHT-44,P.WIDTH,Align.center,false);
        fontBold.setColor(P.TOP_MENU_COLOR);
        fontBold.draw(batch,P.get().pref.getBoolean(S.DIF_CELL) ? "Сложный" : "Легкий",0,P.HEIGHT-193,P.WIDTH,Align.center,false);
        fontBold.setColor(Color.WHITE);
        fontBold.getData().setScale(1);

        font.getData().setScale(0.42f);

        font.setColor(fontColor);
        font.draw(batch,"Ходы",0,P.HEIGHT-77,targetWidthFont,Align.center,false);
        font.draw(batch,"Счёт",P.WIDTH-targetWidthFont,P.HEIGHT-77,targetWidthFont,Align.center,false);
        font.setColor(Color.WHITE);
        font.getData().setScale(1);

        fontBold.getData().setScale(0.42f);

        fontBold.draw(batch,String.valueOf(step),0,P.HEIGHT-143,targetWidthFont,Align.center,false);
        fontBold.draw(batch,String.valueOf(score),P.WIDTH-targetWidthFont,P.HEIGHT-143,targetWidthFont,Align.center,false);

        fontBold.getData().setScale(1);
    }
}
