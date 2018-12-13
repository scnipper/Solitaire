package me.creese.soliter.entity;

import com.badlogic.gdx.graphics.Texture;

import me.creese.soliter.entity.CardType;
import me.creese.soliter.entity.impl.Card;

public class PlaceCard extends CardCell {

    public PlaceCard(float x, float y, CardType cardType, int numberCard) {
        super(x, y, cardType, numberCard);
        texture = new Texture("cards/c_place.png");
        setNumberCard(0);
    }
}
