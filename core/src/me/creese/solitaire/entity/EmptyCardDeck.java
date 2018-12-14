package me.creese.solitaire.entity;

import com.badlogic.gdx.graphics.Texture;

public class EmptyCardDeck extends me.creese.solitaire.entity.CardCell {
    public EmptyCardDeck(float x, float y, CardType cardType, int numberCard,int numStack) {
        super(x, y, cardType, numberCard);
        setStackNum(numStack);
        texture = new Texture("cards/c_place.png");
    }
}
