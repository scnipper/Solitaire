package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.graphics.Texture;

import me.creese.solitaire.entity.CardType;

public class PlaceCard extends CardCell {

    public PlaceCard(float x, float y, CardType cardType, int numberCard) {
        super(x, y, cardType, numberCard);
        texture = new Texture("cards/c_place_"+cardType.name().substring(0,1)+".png");
        setNumberCard(0);
        setDrawShadow(false);
    }
}
