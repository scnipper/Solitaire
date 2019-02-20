package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import me.creese.solitaire.entity.CardType;
import me.creese.util.display.Display;

public class EmptyCardDeck extends CardCell {
    public EmptyCardDeck(float x, float y, CardType cardType, int numberCard, int numStack, Display root) {
        super(x, y, cardType, numberCard,root);
        setStackNum(numStack);
        texture = new Sprite(new Texture("cards/c_place.png"));
        setDrawShadow(false);
    }
}
