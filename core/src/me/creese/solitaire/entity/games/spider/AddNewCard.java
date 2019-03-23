package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.screens.GameScreen;
import me.creese.util.display.Display;

public class AddNewCard extends SpiderCard {
    public AddNewCard(float x, float y, Display root) {
        super(x, y, CardType.DIAMONDS, 1, root);

        setDrawBack(true);

    }

    @Override
    protected void touchDown(InputEvent event, float x, float y) {
        SpiderGame parent = (SpiderGame) getParent();
        ArrayList<AddNewCard> newCards = parent.getNewCards();
        if (posInStack == newCards.size() - 1) {

            int tmpPos = this.posInStack;
            if (parent.addNewLineCard(this.posInStack)) {

                StepBackSpider backSpider = new StepBackSpider(0, tmpPos, 0);

                backSpider.addNewLine = true;

                parent.getSteps().push(backSpider);

                parent.getRoot().getGameViewForName(GameScreen.class).getMenu().getTopScoreView().iterateStep();
                newCards.remove(this.posInStack);
                remove();
            }
        }

    }

    @Override
    protected void touchUp(InputEvent event, float x, float y) {
        // none
    }
}
