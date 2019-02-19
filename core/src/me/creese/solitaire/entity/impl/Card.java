package me.creese.solitaire.entity.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.TexturePrepare;

public class Card extends Actor {
    public static final int RED_CARD = 0;
    public static final int BLACK_CARD = 1;
    private final CardType cardType;
    private final Vector2 startTouch;
    private final Vector2 startPos;
    private final Texture textureBack;
    protected Texture texture;
    private int numberCard;
    private boolean isMove;
    private boolean drawBack;
    private boolean lock;
    private boolean isDrawShadow;
    private Sprite shadow;

    public Card(float x, float y, CardType cardType, int numberCard) {
        this.numberCard = numberCard;
        this.cardType = cardType;
        startTouch = new Vector2();
        startPos = new Vector2(x, y);
        isDrawShadow = true;
        texture = new Texture("cards/c_" + numberCard + "_" + cardType.name().substring(0, 1) + ".png");
        textureBack = new Texture("cards/c_back.png");
        setBounds(x, y, texture.getWidth(), texture.getHeight());


        addListener(new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (lock) {
                    return;
                }
                if (count >= 2) doubleClick();
            }


            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (lock) return;
                Card.this.touchDown(event, x, y);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (lock) return;
                Card.this.touchUp(event, x, y);


            }

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                if (lock) return;
                Card.this.pan(event, x, y, deltaX, deltaY);
            }
        });


    }

    public void doubleClick() {
    }

    protected void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        if (isMove) {

            moveBy(deltaX, deltaY);
        }
    }

    protected void touchUp(InputEvent event, float x, float y) {
        if (isMove) drawBack = false;
    }

    protected void touchDown(InputEvent event, float x, float y) {
        if (isMove) {
            startTouch.set(x, y);
            setZIndex(99999);
        }
    }


    public boolean checkBounds(Actor child) {
        return (getX() > child.getX() && getX() < child.getX() + child.getWidth() && getY() > child.getY() && getY() < child.getY() + child.getHeight()) || (getX() + getWidth() < child.getX() + child.getWidth() && getX() + getWidth() > child.getX() && getY() > child.getY() && getY() < child.getY() + child.getHeight()) || (getY() + getHeight() > child.getY() && getY() + getHeight() < child.getY() + child.getHeight() && getX() + getWidth() > child.getX() && getX() + getWidth() < child.getX() + child.getWidth()) || (getY() + getHeight() > child.getY() && getY() + getHeight() < child.getY() + child.getHeight() && getX() > child.getX() && getX() < child.getX() + child.getWidth());
    }

    public int getColorCard() {
        if (cardType.equals(CardType.DIAMONDS) || cardType.equals(CardType.HEARTS)) {
            return RED_CARD;
        }
        return BLACK_CARD;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public boolean isDrawBack() {
        return drawBack;
    }

    public void setDrawBack(boolean drawBack) {
        this.drawBack = drawBack;
    }

    public CardType getCardType() {
        return cardType;
    }

    public int getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(int numberCard) {
        this.numberCard = numberCard;
    }

    public Vector2 getStartPos() {
        return startPos;
    }

    public Vector2 getStartTouch() {
        return startTouch;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public void setDrawShadow(boolean drawShadow) {
        isDrawShadow = drawShadow;
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            TexturePrepare prepare = ((BaseGame) parent).getRoot().getTransitObject(TexturePrepare.class);

            shadow = prepare.getByName(FTextures.SHADOW_CARD);
            shadow.setColor(Color.BLACK);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isDrawShadow) {
            shadow.setPosition(getX()-10,getY()-10);
            shadow.draw(batch);
        }
        if (drawBack) {

            batch.draw(textureBack, getX(), getY());
        } else batch.draw(texture, getX(), getY());

    }

    @Override
    public String toString() {
        return "Card{" + "numberCard=" + numberCard + ", cardType=" + cardType + '}';
    }
}
