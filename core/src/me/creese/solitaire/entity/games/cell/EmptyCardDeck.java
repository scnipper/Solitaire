package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.graphics.Texture;

import me.creese.solitaire.entity.CardType;

public class EmptyCardDeck extends CardCell {
    public EmptyCardDeck(float x, float y, CardType cardType, int numberCard, int numStack) {
        super(x, y, cardType, numberCard);
        setStackNum(numStack);
        texture = new Texture("cards/c_place.png");
    }
}
