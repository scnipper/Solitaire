package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;

public class CardCell extends Card {



    private int stackNum;
    private int posInStack;
    private boolean deckMode;
    private boolean dontMoveStack;
    // если карта не видна за лругими чуть приподнять ее
    private boolean isMoveUp;
    private boolean movingCard;

    public CardCell(float x, float y, CardType cardType, int numberCard) {
        super(x, y, cardType, numberCard);
    }

    /**
     * Проверка карты на совпадение позиции и правильной комбинации
     */
    private void checkPosition() {

        CellGame parent = (CellGame) getParent();
        ArrayList<ArrayList<CardCell>> stackCard = parent.getStackCard();

        int tmpStackNum = stackNum;

        for (int i = 0; i < stackCard.size() - 1; i++) {
            ArrayList<CardCell> cells = stackCard.get(i);
            if (cells.size() == 0) continue;
            CardCell child = cells.get(cells.size() - 1);
            if (checkBounds(child)) {
                if (tryMoveToPosition(i, child)){

                    break;
                }

            }
        }

        if (tmpStackNum == CellGame.CARD_DECK_NUM) {
            parent.updateDeckIndex();
        }


        moveToStartPosition();

    }

    public void moveToStartPosition() {
        moveToStartPosition(-1);
    }

    /**
     * Если не правильное перемещение возвращение карт на начальную позицию
     */
    public void moveToStartPosition(int startIndex) {

        ArrayList<ArrayList<CardCell>> stackCard = ((CellGame) getParent()).getStackCard();
        ArrayList<CardCell> sta = stackCard.get(stackNum);

        if (startIndex == -1) startIndex = posInStack;
        setLock(true);

        System.out.println("move to "+getStartPos());
        addAction(Actions.sequence(Actions.moveTo(getStartPos().x, getStartPos().y, 0.2f + (0.05f * (posInStack - startIndex))), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
            }
        })));

        int next = posInStack + 1;

        if (next < sta.size()) {
            sta.get(next).moveToStartPosition(startIndex);
        }
    }

    public boolean tryMoveToPosition(int num, CardCell child) {
        return tryMoveToPosition(num, child, true);
    }

    /**
     * Перемещение карты на позицию
     *
     * @param num   Номер стека на который помещаем
     * @param child
     * @return
     */
    public boolean tryMoveToPosition(int num, CardCell child, boolean isCheckToAuto) {
        CellGame parent = (CellGame) getParent();
        ArrayList<ArrayList<CardCell>> stackCard = parent.getStackCard();
        ArrayList<CardCell> tmpStack = stackCard.get(stackNum);
        ArrayList<CardCell> cells = stackCard.get(child.getStackNum());
        int prevCard = num < 7 ? getNumberCard() + 1 : getNumberCard() - 1;
        if (child.getNumberCard() == prevCard && !child.isDrawBack() && (num >= 7 || child.getColorCard() != getColorCard()) && (num < 7 || cells.size() <= 1 || child.getCardType().equals(getCardType()))) {

            System.out.println("карта помещена в " +num+ "\n"+child);
            if (num >= 7) {
                // поместить на конечный стек карту можно только если она в самом конце стека
                if (posInStack != tmpStack.size() - 1 && stackNum != CellGame.CARD_DECK_NUM)
                    return false;
                parent.getTopScoreView().addScore(20);
            } else parent.getTopScoreView().addScore((getNumberCard() * 2) + 5);

            parent.getTopScoreView().iterateStep();


            if (posInStack > 1 && posInStack <=tmpStack.size() && !dontMoveStack) {
                tmpStack.get(posInStack - 1).setDrawBack(false);
                tmpStack.get(posInStack - 1).setMove(true);
            }

            for (int j = posInStack; j < tmpStack.size(); j++) {
                float _y;
                if (num < 7) {
                    _y = 400 - 40 * (cells.size() - 1);
                } else {
                    _y = child.getStartPos().y;
                }
                int n = tmpStack.get(j).getStackNum();
                tmpStack.get(j).getStartPos().set(cells.get(0).getX(), _y);
                tmpStack.get(j).setStackNum(child.getStackNum());
                cells.add(tmpStack.get(j));
                tmpStack.get(j).posStack(cells.size() - 1);
                System.out.println("позиция задана "+tmpStack.get(j).getStartPos());
                tmpStack.remove(j);
                j--;

                // если карта в колоде
                if (n == CellGame.CARD_DECK_NUM) {

                    break;
                }
            }
            dontMoveStack = false;

            if (isCheckToAuto) parent.checkToAuto();


            return true;
        }
        return false;
    }


    /**
     * Двойной клик по карте
     */
    @Override
    public void doubleClick() {

        if (!isDrawBack()) {


            ArrayList<ArrayList<CardCell>> stackCard = ((CellGame) getParent()).getStackCard();
            boolean isMoveToStart = false;
            int tmpStackCardNum = stackNum;
            for (int i = stackCard.size() - 2; i >= 0; i--) {
                ArrayList<CardCell> cells = stackCard.get(i);
                CardCell child = cells.get(cells.size() - 1);
                if (tryMoveToPosition(i, child)){
                    isMoveToStart = true;
                    break;
                }
            }
            if(tmpStackCardNum == CellGame.CARD_DECK_NUM) {
                ((CellGame) getParent()).updateDeckIndex();
            }
            if(isMoveToStart)
            moveToStartPosition();
        }
    }

    /**
     * Получение карты из колоды
     */
    public void openCardInDeck() {

        setMove(true);
        setLock(true);
        addAction(Actions.sequence(Actions.moveBy(230, 0, 0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
            }
        })));
        getStartPos().add(230, 0);
        setZIndex(9999);
        deckMode = false;
        setDrawBack(false);
        ((CellGame) getParent()).getTopScoreView().iterateStep();
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

        if (getActions().size > 0) return;

        ArrayList<CardCell> cells = ((CellGame) getParent()).getStackCard().get(stackNum);
        if (this instanceof EmptyCardDeck) {

            final CellGame parent = (CellGame) getParent();
            if (parent.checkEmptyDeck()) {

                parent.restartDeck();
            }
            return;
        }

        if (posInStack < cells.size() - 1 && stackNum != CellGame.CARD_DECK_NUM) isMoveUp = true;
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
            openCardInDeck();
        } else if(movingCard){
            checkPosition();
        }

        movingCard = false;
        super.touchUp(event, x, y);


    }

    @Override
    protected void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        super.pan(event, x, y, deltaX, deltaY);
        if (!isMove()) return;

        movingCard = true;
        CellGame parent = (CellGame) getParent();
        ArrayList<CardCell> stack = parent.getStackCard().get(stackNum);

        if (isMoveUp) {
            moveBy(0, 60);
            isMoveUp = false;
        }
        if (posInStack < stack.size() - 1 && stackNum != CellGame.CARD_DECK_NUM) {

            stack.get(posInStack + 1).pan(event, x, y, deltaX, deltaY);

        }

    }
}
