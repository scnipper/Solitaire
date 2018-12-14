package me.creese.solitaire.entity;

import com.badlogic.gdx.graphics.Texture;

public class EmptyCard extends me.creese.solitaire.entity.CardCell {
    public EmptyCard(float x, float y, CardType cardType, int numberCard) {
        super(x, y, cardType, numberCard);
        texture = new Texture("cards/c_place.png");
        setNumberCard(14);


    }

    @Override
    public int getColorCard() {
        return -1;
    }
}
