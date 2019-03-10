package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import me.creese.solitaire.entity.CardType;
import me.creese.util.display.Display;

public class EmptyCardSpider extends SpiderCard {
    public EmptyCardSpider( float y, Display root) {
        super(0, y, CardType.DIAMONDS, 1, root);

        texture = new Sprite(new Texture("cards/c_place.png"));
        setNumberCard(-1);
        setDrawShadow(false);
    }
}
