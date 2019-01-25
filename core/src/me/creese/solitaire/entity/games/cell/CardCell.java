package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;

public class CardCell extends Card {


    public static final int DOUBLE_CLICK_TIME = 400; // ms
    private int stackNum;
    private int posInStack;
    private boolean deckMode;
    private boolean dontMoveStack;
    private long startTimeDown;
    // если карта не видна за лругими чуть приподнять ее
    private boolean isMoveUp;

    public CardCell(float x, float y, CardType cardType, int numberCard) {
        super(x, y, cardType, numberCard);
    }

    /**
     * Проверка карты на совпадение позиции и правильной комбинации
     */
    private void checkPosition() {

        CellGame parent = (CellGame) getParent();
        ArrayList<ArrayList<CardCell>> stackCard = parent.getStackCard();


        for (int i = 0; i < stackCard.size(); i++) {
            ArrayList<CardCell> cells = stackCard.get(i);
            if (cells.size() == 0) continue;
            CardCell child = cells.get(cells.size() - 1);
            if (checkBounds(child)) {

                if (moveToPosition(i, child, cells)) break;

            }
        }


        if (stackNum == CellGame.CARD_DECK_NUM) {
            ArrayList<CardCell> stack = stackCard.get(stackNum);
            for (int i = 0; i < stack.size(); i++) {
                stack.get(i).posStack(i);
            }
        }

        moveToStartPosition();

    }

    /**
     * Если не правильное перемещение возвращение карт на начальную позицию
     */
    private void moveToStartPosition() {
        ArrayList<ArrayList<CardCell>> stackCard = ((CellGame) getParent()).getStackCard();
        for (int i = posInStack; i < stackCard.get(stackNum).size(); i++) {
            stackCard.get(stackNum).get(i).addAction(Actions.moveTo(
                    stackCard.get(stackNum).get(i).getStartPos().x,
                    stackCard.get(stackNum).get(i).getStartPos().y, 0.2f + (0.05f * (i-posInStack))));
        }
    }

    /**
     * Перемещение карты на позицию
     *
     * @param num
     * @param child
     * @param cells
     * @return
     */
    private boolean moveToPosition(int num, CardCell child, ArrayList<CardCell> cells) {
        CellGame parent = (CellGame) getParent();
        ArrayList<ArrayList<CardCell>> stackCard = parent.getStackCard();
        ArrayList<CardCell> tmpStack = stackCard.get(stackNum);
        int prevCard = num < 7 ? getNumberCard() + 1 : getNumberCard() - 1;
        if (child.getNumberCard() == prevCard && !child.isDrawBack() && (num >= 7 || child.getColorCard() != getColorCard()) && (num < 7 || cells.size() <= 1 || child.getCardType().equals(getCardType()))) {

            parent.getTopScoreView().iterateStep();
            if (num > 7) parent.getTopScoreView().addScore(20);
            else parent.getTopScoreView().addScore((getNumberCard() * 2) + 5);
            if (getPosInStack() > 1 && !dontMoveStack) {
                tmpStack.get(getPosInStack() - 1).setDrawBack(false);
                tmpStack.get(getPosInStack() - 1).setMove(true);
            }

            for (int j = posInStack; j < tmpStack.size(); j++) {
                float _y;
                if (num < 7) {
                    _y = 400 - 40 * (cells.size() - 1);
                } else {
                    _y = child.getY();
                }
                int n = tmpStack.get(j).getStackNum();
                tmpStack.get(j).getStartPos().set(child.getX(), _y);
                tmpStack.get(j).setStackNum(child.getStackNum());
                cells.add(tmpStack.get(j));
                tmpStack.get(j).posStack(cells.size() - 1);
                tmpStack.remove(j);
                j--;
                // если карта в колоде
                if (n == CellGame.CARD_DECK_NUM) {

                    break;
                }
            }
            dontMoveStack = false;


            return true;
        }
        return false;
    }


    /**
     * Двойной клик по карте
     */
    private void doubleClick() {
        ArrayList<ArrayList<CardCell>> stackCard = ((CellGame) getParent()).getStackCard();

        for (int i = stackCard.size()-1; i >=0; i--) {
            ArrayList<CardCell> cells = stackCard.get(i);
            CardCell child = cells.get(cells.size() - 1);
            if (moveToPosition(i, child, cells)) break;
        }
        moveToStartPosition();
    }

    private boolean checkEmptyDeck(ArrayList<CardCell> cells) {

        for (CardCell cell : cells) {
            if (cell.isDeckMode()) return false;
        }

        return true;
    }


    public void posStack(int posInStack) {
        this.posInStack = posInStack;
    }

    public int getStackNum() {
        return stackNum;
    }

    public void setStackNum(int stackNum) {
        this.stackNum = stackNum;
    }

    public int getPosInStack() {
        return posInStack;
    }

    public void setDontMoveStack(boolean dontMoveStack) {
        this.dontMoveStack = dontMoveStack;
    }

    public boolean isDeckMode() {
        return deckMode;
    }

    public void setDeckMode(boolean deckMode) {
        this.deckMode = deckMode;
    }

    @Override
    protected void touchDown(InputEvent event, float x, float y) {

        if (!isDrawBack()) {
            if (startTimeDown > 0) {
                long time = System.currentTimeMillis() - startTimeDown;

                if (time <= DOUBLE_CLICK_TIME) {
                    doubleClick();
                    startTimeDown = 0;
                    return;
                } else {
                    startTimeDown = 0;
                }
            } else startTimeDown = System.currentTimeMillis();
        }
        if (getActions().size > 0) return;

        ArrayList<CardCell> cells = ((CellGame) getParent()).getStackCard().get(stackNum);
        if (this instanceof EmptyCardDeck) {

            if (checkEmptyDeck(cells)) {
                for (int i = 0; i < cells.size(); i++) {
                    CardCell cell = cells.get(i);
                    cell.addAction(Actions.moveBy(-230, 0, 0.1f+(0.01f*i)));
                    cell.getStartPos().add(-230, 0);
                    cell.setZIndex(9999);
                    cell.setDeckMode(true);
                    cell.setDrawBack(true);
                    cell.setMove(false);
                }
            }
            return;
        }

        if(posInStack < cells.size()-1 && stackNum != CellGame.CARD_DECK_NUM)
        isMoveUp = true;
        if (!dontMoveStack) {
            for (int i = posInStack; i < cells.size(); i++) {
                cells.get(i).setZIndex(9999);
            }
        } else {
            setZIndex(9999);
        }

        getStartTouch().set(x, y);
    }

    @Override
    protected void touchUp(InputEvent event, float x, float y) {
        if (getActions().size > 0 || this instanceof EmptyCardDeck) return;


        isMoveUp = false;
        // если в колоде
        if (deckMode) {
            setMove(true);
            addAction(Actions.moveBy(230, 0, 0.3f));
            getStartPos().add(230, 0);
            setZIndex(9999);
            deckMode = false;
            ((CellGame) getParent()).getTopScoreView().iterateStep();
        } else {
            checkPosition();
        }
        super.touchUp(event, x, y);


    }

    @Override
    protected void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        super.pan(event,x,y,deltaX,deltaY);
        if (!isMove()) return;
        CellGame parent = (CellGame) getParent();
        ArrayList<CardCell> stack = parent.getStackCard().get(stackNum);

        if(isMoveUp) {
            moveBy(0, 60);
            isMoveUp = false;
        }
        if(posInStack < stack.size()-1 && stackNum != CellGame.CARD_DECK_NUM) {

            stack.get(posInStack+1).pan(event,x,y,deltaX,deltaY);

        }

    }
}
