package me.creese.solitaire.entity;

import com.badlogic.gdx.graphics.Texture;

public class PlaceCard extends me.creese.solitaire.entity.CardCell {

    public PlaceCard(float x, float y, CardType cardType, int numberCard) {
        super(x, y, cardType, numberCard);
        texture = new Texture("cards/c_place.png");
        setNumberCard(0);
    }
}
