package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import me.creese.solitaire.entity.CardType;
import me.creese.util.display.Display;

public class PlaceCard extends CardCell {

    public PlaceCard(float x, float y, CardType cardType, int numberCard, Display root) {
        super(x, y, cardType, numberCard,root);
        texture = new Sprite(new Texture("cards/c_place_"+cardType.name().substring(0,1)+".png"));
        setNumberCard(0);
        setDrawShadow(false);
    }
}
