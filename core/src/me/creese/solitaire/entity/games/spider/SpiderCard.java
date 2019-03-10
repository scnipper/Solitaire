package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;
import me.creese.util.display.Display;

public class SpiderCard extends Card {
    private int deckNum;

    public SpiderCard(float x, float y, CardType cardType, int numberCard, Display root) {
        super(x, y, cardType, numberCard, root);

    }

    /**
     * Перемещение на начальную позицию
     * @param startIndex
     * @param afterMove
     */
    public void moveToStartPos(int startIndex, Runnable afterMove) {
        if (afterMove != null) {

            addAction(Actions.sequence(Actions.moveTo(getStartPos().x,getStartPos().y,0.17f+ (0.05f * (posInStack - startIndex))),
                    Actions.run(afterMove)));
        }else {
            addAction(Actions.moveTo(getStartPos().x,getStartPos().y,0.17f+ (0.05f * (posInStack - startIndex))));
        }

        if(startIndex != -1) {
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

        int tmpDeck = this.deckNum;
        for (int i = 0; i < decks.size()-5; i++) {
            ArrayList<SpiderCard> spiderCards = decks.get(i);

            SpiderCard lastCard = spiderCards.get(spiderCards.size() - 1);

            if (checkBounds(lastCard)) {
                if(tryMoveToPosition(i,false)) {
                    parent.updateMoveCards(tmpDeck);
                    parent.checkWinCombination(i);
                    return;
                }
            }
        }

        moveToStartPos(this.posInStack, null);
    }

    /**
     * Попытка перемещения на позицию
     * @param toStackNum
     * @param justCheck
     * @return
     */
    public boolean tryMoveToPosition(int toStackNum,boolean justCheck) {
        SpiderGame parent = (SpiderGame) getParent();


        ArrayList<SpiderCard> toStack = parent.getDecks().get(toStackNum);

        SpiderCard toCard = toStack.get(toStack.size() - 1);

        ArrayList<SpiderCard> fromStack = parent.getDecks().get(deckNum);

        if(rightConditionCard(toCard)) {

            if(justCheck) return true;

            getStartPos().set(toCard.getStartPos().x,toCard.getStartPos().y-
                    (toStack.size() > 1 ?SpiderGame.SPACE_BETWEEN_TWO_OPEN_CARDS:0));

            int savePosInStack = posInStack;

            fromStack.get(posInStack -1).setDrawBack(false);
            fromStack.get(posInStack -1).setMove(true);

            posStack(toStack.size());
            toStack.add(this);
            deckNum = toStackNum;

            int next = savePosInStack + 1;

            if(next < fromStack.size()) {
                fromStack.get(next).tryMoveToPosition(toStackNum,false);
            }
            moveToStartPos(savePosInStack, null);
            fromStack.remove(savePosInStack);


            return true;
        }

        return false;

    }

    /**
     * Правильное условие для перемещения карт друг на друга
     * @param toCard
     * @return
     */
    public boolean rightConditionCard(SpiderCard toCard) {
        return toCard.getNumberCard()-1 == getNumberCard() || toCard.getNumberCard() == -1;
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

        if(!isMove()) return;

        Array<Action> actions = getActions();

        for (Action action : actions) {
            ((MoveToAction) action).finish();
        }

        SpiderGame parent = (SpiderGame) getParent();

        ArrayList<SpiderCard> deck = parent.getDecks().get(deckNum);

        int next = posInStack + 1;
        if(next < deck.size()) {
            deck.get(next).pan(event,x,y,deltaX,deltaY);
        }
    }

    @Override
    protected void touchUp(InputEvent event, float x, float y) {

        super.touchUp(event, x, y);

        checkPosition();
    }

    public void setDeckNum(int addDeck) {
        deckNum = addDeck;
    }
}
