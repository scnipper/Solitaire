package me.creese.solitaire.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.Random;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.Shapes;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class WinCircle extends Group {

    private static final String GREAT = "Великолепно!";
    private static final String CONGRAT = "Поздравляем!";
    private final Sprite circle;
    private final Sprite circleFill;
    private final BitmapFont font;
    private final Random random;
    private final Display root;
    private final Texture coin;
    private final Shapes shapes;

    private final ArrayList<Vector2> pointLines;
    private final ArrayList<Actor> linesMove;
    private int coinWin;

    public WinCircle(Display root) {
        this.root = root;
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);
        circle = prepare.getByName(FTextures.WIN_CIRCLE);
        circleFill = prepare.getByName(FTextures.WIN_CIRCLE_FILL);

        circle.setColor(P.SUB_BACKGROUND_COLOR);
        circleFill.setColor(P.SUB_BACKGROUND_COLOR);
        circle.setPosition(P.WIDTH/2-circle.getWidth()/2,448);
        circleFill.setPosition(circle.getX()+22,circle.getY()+22);

        circleFill.setAlpha(1);
        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        random = new Random();
        coin = new Texture("coin_big.png");

        shapes = new Shapes();
        shapes.setColor(P.SUB_BACKGROUND_COLOR);

        pointLines = new ArrayList<>();
        linesMove = new ArrayList<>();

    }

    public void videoCoinsSet(int coinWin) {
        this.coinWin = coinWin;

        SnapshotArray<Actor> children = getChildren();
        for (int i = 0; i < children.size; i++) {
            final Actor child = children.get(i);
            if(child instanceof Card) {
                child.addAction(Actions.sequence(Actions.moveBy(0, -340, 0.45f + (i * 0.1f)), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        child.remove();
                    }
                })));
            }
        }

        addAction(Actions.scaleTo(0,0,0.4f));

        for (int i = 0; i < linesMove.size(); i++) {
            Actor move = linesMove.get(i);
            move.addAction(Actions.moveTo(pointLines.get(i).x,pointLines.get(i).y,0.3f+(0.6f*random.nextFloat())));

        }
    }

    private void drawLines(float fromX,float fromY) {
        float minX ,det;
        float minY = circle.getY();
        float detY = (circle.getY()+circle.getHeight()+200)-minY;


        if(pointLines.size() == 0) {
            for (int i = 0; i < 8; i++) {

                Actor linesMove = new Actor();
                linesMove.setPosition(fromX,fromY);
                addActor(linesMove);
                this.linesMove.add(linesMove);

                if(random.nextBoolean()) {
                    minX = circle.getX() + circle.getWidth() + 50;
                    det = P.WIDTH-minX;
                } else {
                    minX = 0;
                    det = circle.getX()-50;
                }

                pointLines.add(new Vector2(random.nextInt((int) det)+minX,minY+random.nextInt((int) detY)));


            }
        }

        for (Actor actor : linesMove) {

            shapes.line(fromX,fromY,actor.getX(),actor.getY(),7);
        }




    }


    public void setCoinWin(int coinWin) {
        this.coinWin = coinWin;
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        circle.setScale(scaleX,scaleY);
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            for (int i = 0; i < 4; i++) {
                Card card = new Card(372+(i*70), 661, CardType.getForNum(random.nextInt(4)), random.nextInt(13) + 1,root);
                addActor(card);
                card.setRotation(30);

                card.addAction(Actions.moveBy(0,340,0.45f+(i*0.1f)));
            }
        } else {
            clearChildren();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shapes.setProjMatrix(batch.getProjectionMatrix());
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        drawLines(P.WIDTH/2,circle.getY()+circle.getHeight()/2);


        shapes.flush();
        batch.begin();

        circle.draw(batch);
        super.draw(batch,parentAlpha);
        circleFill.draw(batch);
        circleFill.draw(batch);


        FontUtil.drawText(batch,font,GREAT,0,P.HEIGHT-288,1.07f, Color.WHITE,P.WIDTH, Align.center);
        FontUtil.drawText(batch,font,CONGRAT,0,P.HEIGHT-500,0.65f, P.YELLOW_COLOR,P.WIDTH, Align.center);

        FontUtil.drawText(batch,font,"+ "+coinWin,circle.getX(),circle.getY(),1.8f,P.YELLOW_COLOR,388,Align.right,false,circle.getHeight());
        batch.draw(coin,circle.getX()+434,circle.getY()+circle.getHeight()/2-coin.getHeight()/2);
    }
}
