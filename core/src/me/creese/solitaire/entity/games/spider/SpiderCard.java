package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;
import me.creese.solitaire.screens.GameScreen;
import me.creese.util.display.Display;

public class SpiderCard extends Card {
    private int deckNum;

    public SpiderCard(float x, float y, CardType cardType, int numberCard, Display root) {
        super(x, y, cardType, numberCard, root);

    }

    /**
     * Перемещение на начальную позицию
     *
     * @param startIndex
     * @param afterMove
     */
    public void moveToStartPos(int startIndex, Runnable afterMove) {
        setZIndex(9999);
        if (afterMove != null) {

            addAction(Actions.sequence(Actions.moveTo(getStartPos().x, getStartPos().y, 0.17f + (0.05f * (posInStack - startIndex))), Actions.run(afterMove)));
        } else {
            addAction(Actions.moveTo(getStartPos().x, getStartPos().y, 0.17f + (0.05f * (posInStack - startIndex))));
        }

        if (startIndex != -1) {
            SpiderGame parent = (SpiderGame) getParent();

            ArrayList<SpiderCard> deck = parent.getDecks().get(deckNum);


            int next = posInStack + 1;
            if (next < deck.size()) {
                deck.get(next).moveToStartPos(startIndex, afterMove);
            }
        }
    }

    private void checkPosition() {
        SpiderGame parent = (SpiderGame) getParent();

        ArrayList<ArrayList<SpiderCard>> decks = parent.getDecks();

        ArrayList<SpiderCard> currDeck = decks.get(deckNum);
        int tmpDeck = this.deckNum;
        for (int i = 0; i < decks.size() - 5; i++) {
            ArrayList<SpiderCard> spiderCards = decks.get(i);

            SpiderCard lastCard = spiderCards.get(spiderCards.size() - 1);

            if (checkBounds(lastCard)) {
                boolean moveLast = true;
                boolean drawBackLast = false;

                if(posInStack > 1) {
                    SpiderCard prevCard = currDeck.get(posInStack - 1);
                    moveLast = prevCard.isMove();
                    drawBackLast = prevCard.isDrawBack();
                }



                if (tryMoveToPosition(i, false, false)) {
                    afterMove(i, tmpDeck);

                    StepBackSpider stepBackSpider = new StepBackSpider(i, tmpDeck, posInStack);
                    stepBackSpider.moveLast = moveLast;
                    stepBackSpider.drawBackLast = drawBackLast;
                    parent.getSteps().push(stepBackSpider);
                    return;
                }
            }
        }

        moveToStartPos(this.posInStack, null);
    }

    private void afterMove(int i, int tmpDeck) {
        SpiderGame parent = (SpiderGame) getParent();
        parent.updateMoveCards(tmpDeck);
        parent.updateMoveCards(i);
        parent.checkWinCombination(i);
        parent.getRoot().getGameViewForName(GameScreen.class).getMenu().getTopScoreView().iterateStep();
        parent.getRoot().getGameViewForName(GameScreen.class).getMenu().getTopScoreView().addScore(25);
    }

    /**
     * Попытка перемещения на позицию
     *
     * @param toStackNum
     * @param justCheck
     * @return
     */
    public boolean tryMoveToPosition(int toStackNum, boolean justCheck, boolean withoutConsider) {
        SpiderGame parent = (SpiderGame) getParent();


        ArrayList<SpiderCard> toStack = parent.getDecks().get(toStackNum);

        SpiderCard toCard = toStack.get(toStack.size() - 1);

        ArrayList<SpiderCard> fromStack = parent.getDecks().get(deckNum);

        if (toCard.getNumberCard() - 1 == getNumberCard() || toCard.getNumberCard() == -1 || withoutConsider) {

            if (justCheck) return true;

            int offsetY = 0;

            if(toStack.size() > 1) {
                if(toCard.isDrawBack()) {
                    offsetY = SpiderGame.SPACE_BETWEEN_TWO_CARDS;
                } else {
                    offsetY = SpiderGame.SPACE_BETWEEN_TWO_OPEN_CARDS;
                }
            }

            getStartPos().set(toCard.getStartPos().x, toCard.getStartPos().y - offsetY);

            int savePosInStack = posInStack;

            fromStack.get(posInStack - 1).setDrawBack(false);
            fromStack.get(posInStack - 1).setMove(true);

            posStack(toStack.size());
            toStack.add(this);
            deckNum = toStackNum;

            int next = savePosInStack + 1;

            if (next < fromStack.size()) {
                fromStack.get(next).tryMoveToPosition(toStackNum, false, withoutConsider);
            }
            moveToStartPos(savePosInStack, null);
            fromStack.remove(savePosInStack);


            return true;
        }

        return false;

    }

    /**
     * Правильное условие для перемещения карт друг на друга
     *
     * @param toCard
     * @return
     */
    public boolean rightConditionCard(SpiderCard toCard) {
        return (toCard.getNumberCard() - 1 == getNumberCard() || toCard.getNumberCard() == -1) && getCardType().equals(toCard.getCardType());
    }

    @Override
    protected void touchDown(InputEvent event, float x, float y) {

        super.touchDown(event, x, y);
        SpiderGame parent = (SpiderGame) getParent();

        ArrayList<SpiderCard> deck = parent.getDecks().get(deckNum);

        for (int i = posInStack; i < deck.size(); i++) {
            deck.get(i).setZIndex(9999);
        }
    }

    @Override
    protected void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {

        super.pan(event, x, y, deltaX, deltaY);

        if (!isMove()) return;

        movingCard = true;
        Array<Action> actions = getActions();

        for (Action action : actions) {
            ((MoveToAction) action).finish();
        }

        SpiderGame parent = (SpiderGame) getParent();

        ArrayList<SpiderCard> deck = parent.getDecks().get(deckNum);

        int next = posInStack + 1;
        if (next < deck.size()) {
            deck.get(next).pan(event, x, y, deltaX, deltaY);
        }
    }

    @Override
    protected void touchUp(InputEvent event, float x, float y) {

        if (movingCard) checkPosition();

        movingCard = false;
        super.touchUp(event, x, y);

    }

    @Override
    public void doubleClick() {

        if (!isMove() && getActions().size == 0) return;

        SpiderGame parent = (SpiderGame) getParent();

        ArrayList<ArrayList<SpiderCard>> decks = parent.getDecks();

        int saveEmptyNum = -1;
        int tmpDeck = this.deckNum;
        for (int i = 0; i < decks.size() - 5; i++) {
            if (i == deckNum) continue;

            if (decks.get(i).size() == 1) {
                saveEmptyNum = i;
                continue;
            }


            if (tryMoveToPosition(i, false, false)) {
                afterMove(i, tmpDeck);
                return;
            }
        }
        if (saveEmptyNum != -1) {
            if (tryMoveToPosition(saveEmptyNum, false, false)) {
                afterMove(saveEmptyNum, tmpDeck);
            }
        }

    }

    public void setDeckNum(int addDeck) {
        deckNum = addDeck;
    }
}
