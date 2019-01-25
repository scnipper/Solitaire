package me.creese.solitaire.entity.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import me.creese.solitaire.entity.CardType;

public class Card extends Actor {
    public static final int RED_CARD = 0;
    public static final int BLACK_CARD = 1;
    private int numberCard;
    private final CardType cardType;
    private final Vector2 startTouch;
    private final Vector2 startPos;
    protected Texture texture;
    private final Texture textureBack;
    private boolean isMove;
    private boolean drawBack;

    public Card(float x, float y, CardType cardType, int numberCard) {
        this.numberCard = numberCard;
        this.cardType = cardType;
        startTouch = new Vector2();
        startPos = new Vector2(x, y);

        texture = new Texture("cards/c_" + numberCard + "_" + cardType.name().substring(0, 1) + ".png");
        textureBack = new Texture("cards/c_back.png");
        setBounds(x, y, texture.getWidth(), texture.getHeight());


        addListener(new ActorGestureListener() {

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Card.this.touchDown(event, x, y);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Card.this.touchUp(event, x, y);


            }

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {

                Card.this.pan(event,x,y,deltaX,deltaY);
            }
        });


    }

    protected void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        if (isMove) {

            moveBy(deltaX , deltaY );
        }
    }

    protected void touchUp(InputEvent event, float x, float y) {
        if (isMove) drawBack = false;
    }

    protected void touchDown(InputEvent event, float x, float y) {
        if (isMove) {
            startTouch.set(x, y);
            setZIndex(99);
        }
    }


    public boolean checkBounds(Actor child) {
        return (getX() > child.getX() && getX() < child.getX() + child.getWidth() && getY() > child.getY() && getY() < child.getY() + child.getHeight()) || (getX() + getWidth() < child.getX() + child.getWidth() && getX() + getWidth() > child.getX() && getY() > child.getY() && getY() < child.getY() + child.getHeight()) || (getY() + getHeight() > child.getY() && getY() + getHeight() < child.getY() + child.getHeight() && getX() + getWidth() > child.getX() && getX() + getWidth() < child.getX() + child.getWidth()) || (getY() + getHeight() > child.getY() && getY() + getHeight() < child.getY() + child.getHeight() && getX() > child.getX() && getX() < child.getX() + child.getWidth());
    }

    public int getColorCard() {
        if(cardType.equals(CardType.DIAMONDS) || cardType.equals(CardType.HEARTS)) {
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


    public Vector2 getStartPos() {
        return startPos;
    }

    public Vector2 getStartTouch() {
        return startTouch;
    }

    public void setNumberCard(int numberCard) {
        this.numberCard = numberCard;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (drawBack) {
            batch.draw(textureBack, getX(), getY());
        } else batch.draw(texture, getX(), getY());

    }

    @Override
    public String toString() {
        return "Card{" + "numberCard=" + numberCard + ", cardType=" + cardType + '}';
    }
}
