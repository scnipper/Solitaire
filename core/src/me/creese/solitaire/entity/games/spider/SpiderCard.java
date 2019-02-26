package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;
import me.creese.util.display.Display;

public class SpiderCard extends Card {
    public SpiderCard(float x, float y, CardType cardType, int numberCard, Display root) {
        super(x, y, cardType, numberCard, root);
        setMove(true);
    }

    public void moveToStartPos() {
        addAction(Actions.moveTo(getStartPos().x,getStartPos().y,0.3f));
    }
}
