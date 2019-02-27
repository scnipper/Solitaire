package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;
import me.creese.util.display.Display;

public class SpiderCard extends Card {
    private int deckNum;

    public SpiderCard(float x, float y, CardType cardType, int numberCard, Display root) {
        super(x, y, cardType, numberCard, root);

    }

    public void moveToStartPos(int startIndex) {
        addAction(Actions.moveTo(getStartPos().x,getStartPos().y,0.17f+ (0.05f * (posInStack - startIndex))));

        SpiderGame parent = (SpiderGame) getParent();

        ArrayList<SpiderCard> deck = parent.getDecks().get(deckNum);


        int next = posInStack + 1;
        if(next < deck.size()) {
            deck.get(next).moveToStartPos(startIndex);
        }
    }

    private void checkPosition() {
        SpiderGame parent = (SpiderGame) getParent();

        ArrayList<ArrayList<SpiderCard>> decks = parent.getDecks();

        for (int i = 0; i < decks.size(); i++) {

            SpiderCard lastCard = decks.get(i).get(decks.get(i).size() - 1);

            if (checkBounds(lastCard)) {
                if(tryMoveToPosition(i)) return;
            }
        }

        moveToStartPos(posInStack);
    }

    private boolean tryMoveToPosition(int toStackNum) {
        SpiderGame parent = (SpiderGame) getParent();


        ArrayList<SpiderCard> toStack = parent.getDecks().get(toStackNum);

        SpiderCard toCard = toStack.get(toStack.size() - 1);

        ArrayList<SpiderCard> fromStack = parent.getDecks().get(deckNum);

        if(toCard.getNumberCard()-1 == getNumberCard()) {
            getStartPos().set(toCard.getStartPos().x,toCard.getStartPos().y-SpiderGame.SPACE_BETWEEN_TWO_OPEN_CARDS);

            int savePosInStack = posInStack;

            fromStack.get(posInStack -1).setDrawBack(false);
            fromStack.get(posInStack -1).setMove(true);

            posStack(toStack.size());
            toStack.add(this);
            deckNum = toStackNum;

            int next = savePosInStack + 1;

            if(next < fromStack.size()) {
                fromStack.get(next).tryMoveToPosition(toStackNum);
            }
            moveToStartPos(savePosInStack);
            fromStack.remove(savePosInStack);


            return true;
        }

        return false;

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
