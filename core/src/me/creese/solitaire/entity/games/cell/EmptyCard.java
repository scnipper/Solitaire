package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.graphics.Texture;

import me.creese.solitaire.entity.CardType;

public class EmptyCard extends CardCell {
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
