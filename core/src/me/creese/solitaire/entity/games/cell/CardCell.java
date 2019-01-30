package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;

public class CardCell extends Card {


    private int stackNum;
    private int posInStack;
    private boolean deckMode;
    private boolean dontMoveStack;
    // если карта не видна за лругими чуть приподнять ее
    private boolean isMoveUp;
    private boolean movingCard;
    // если true недоступная карта из трех
    private boolean isSubCard;
    private int offsetX;

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
                if (tryMoveToPosition(i, child)) {

                    break;
                }

            }
        }

        if (tmpStackNum == CellGame.CARD_DECK_NUM) {
            parent.updateDeckIndex();
        }


        moveToStartPosition();

    }

    public void backMoveToDeck() {
        setMove(false);
        setLock(true);
        addAction(Actions.sequence(Actions.moveBy(-(230 + offsetX), 0, 0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
            }
        })));
        getStartPos().add(-(230 + offsetX), 0);
        setZIndex(9999);
        deckMode = true;
        offsetX = 0;
        setDrawBack(true);
        setSubCard(false);
        CellGame parent = (CellGame) getParent();
        parent.getTopScoreView().decrementStep();
    }

    public void moveToStartPosition() {
        moveToStartPosition(-1);
    }

    /**
     * Если не правильное перемещение возвращение карт на начальную позицию
     */
    public void moveToStartPosition(int startIndex) {

        if (startIndex == -1) startIndex = posInStack;
        setLock(true);
        setZIndex(99999);
        addAction(Actions.sequence(Actions.moveTo(getStartPos().x, getStartPos().y, 0.2f + (0.05f * (posInStack - startIndex))), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
            }
        })));
        ArrayList<ArrayList<CardCell>> stackCard = ((CellGame) getParent()).getStackCard();
        ArrayList<CardCell> sta = stackCard.get(stackNum);
        int next = posInStack + 1;

        if (next < sta.size() && stackNum != CellGame.CARD_DECK_NUM) {

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
        final CellGame parent = (CellGame) getParent();
        ArrayList<ArrayList<CardCell>> stackCard = parent.getStackCard();
        final ArrayList<CardCell> tmpStack = stackCard.get(stackNum);
        final ArrayList<CardCell> cells = stackCard.get(child.getStackNum());
        int prevCard = num < 7 ? getNumberCard() + 1 : getNumberCard() - 1;
        if (child.getNumberCard() == prevCard && !child.isDrawBack() && (num >= 7 || child.getColorCard() != getColorCard()) && (num < 7 || cells.size() <= 1 || child.getCardType().equals(getCardType()))) {

            final int numScoreAdd;
            if (num >= 7) {
                // поместить на конечный стек карту можно только если она в самом конце стека
                if (posInStack != tmpStack.size() - 1 && stackNum != CellGame.CARD_DECK_NUM)
                    return false;
                numScoreAdd = 20;
            } else {
                numScoreAdd = (getNumberCard() * 2) + 5;
            }
            parent.getTopScoreView().addScore(numScoreAdd);

            parent.getTopScoreView().iterateStep();

            CardCell cardPrev = null;
            if (posInStack > 1 && posInStack <= tmpStack.size() && !dontMoveStack) {
                cardPrev = tmpStack.get(posInStack - 1);
                cardPrev.setDrawBack(false);
                cardPrev.setMove(true);
            }

            StepBack s = new StepBack(child.getStackNum(), stackNum, cells.size(), offsetX);
            if(stackNum == CellGame.CARD_DECK_NUM) {
                s.toPosAdd = posInStack;
            }
            parent.getSteps().push(s);
            /*final CardCell finalCardPrev = cardPrev;
            final int copyStackNum = stackNum;
            final int savePosInStack = posInStack;

            parent.getSteps().push(new Runnable() {
                @Override
                public void run() {
                    if (finalCardPrev != null) {
                        finalCardPrev.setDrawBack(true);
                        finalCardPrev.setMove(false);
                    }
                    CardCell to = tmpStack.get(tmpStack.size() - 1);
                    parent.getTopScoreView().decrementStep();
                    parent.getTopScoreView().addScore(-numScoreAdd);

                    for (int j = posInStack; j < cells.size(); j++) {
                        float _y;
                        if (copyStackNum < 7) {
                            _y = 400 - 40 * (tmpStack.size() - 1);
                        } else {
                            _y = to.getStartPos().y;
                        }
                        int n = cells.get(j).getStackNum();
                        float _x = tmpStack.get( 0).getX();
                        if(to.getStackNum() == CellGame.CARD_DECK_NUM) {
                            _x = 280;
                        }



                        int next = 0;
                        if(P.get().pref.getBoolean(S.DIF_CELL)) {
                            for (int i = 0; i < tmpStack.size(); i++) {
                                CardCell cardCell = tmpStack.get(i);

                                if(cardCell.isSubCard()){

                                    next = i - 1;

                                    if(next >=0) {
                                        _x = tmpStack.get(next).getX()+60;
                                        tmpStack.get(next).setSubCard(true);
                                    }
                                    break;
                                }
                            }
                        }

                        next--;
                        cells.get(j).getStartPos().set(_x, _y);
                        cells.get(j).setStackNum(to.getStackNum());


                        if(copyStackNum == CellGame.CARD_DECK_NUM) {
                            tmpStack.add(savePosInStack, cells.get(j));
                            cells.get(j).posStack(savePosInStack);
                        } else {
                            tmpStack.add( cells.get(j));
                            cells.get(j).posStack(tmpStack.size()-1);
                        }
                        cells.remove(j);
                        j--;

                        // если карта в колоде
                        if (n == CellGame.CARD_DECK_NUM) {

                            break;
                        }

                        if(copyStackNum == CellGame.CARD_DECK_NUM) {
                            tmpStack.get(savePosInStack).setZIndex(9999);
                            tmpStack.get(savePosInStack).moveToStartPosition(tmpStack.get(savePosInStack).posInStack);
                        } else {
                            tmpStack.get(tmpStack.size()-1).setZIndex(9999);
                            tmpStack.get(tmpStack.size()-1).moveToStartPosition(tmpStack.get(tmpStack.size()-1).posInStack);
                        }
                    }


                }
            });*/


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
                tmpStack.remove(j);
                j--;

                // если карта в колоде
                if (n == CellGame.CARD_DECK_NUM) {

                    if (P.get().pref.getBoolean(S.DIF_CELL)) {
                        int next = j + 1;
                        if (next < tmpStack.size()) {
                            tmpStack.get(next).setSubCard(false);
                        }
                    }
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

        if (!isDrawBack() && !isSubCard) {


            ArrayList<ArrayList<CardCell>> stackCard = ((CellGame) getParent()).getStackCard();
            boolean isMoveToStart = false;
            int tmpStackCardNum = stackNum;
            for (int i = stackCard.size() - 2; i >= 0; i--) {
                ArrayList<CardCell> cells = stackCard.get(i);
                CardCell child = cells.get(cells.size() - 1);
                if (tryMoveToPosition(i, child)) {
                    isMoveToStart = true;
                    break;
                }
            }
            if (tmpStackCardNum == CellGame.CARD_DECK_NUM) {
                ((CellGame) getParent()).updateDeckIndex();
            }
            if (isMoveToStart) moveToStartPosition();
        }
    }

    public void openCardInDeck() {
        openCardInDeck(0);
    }

    /**
     * Получение карты из колоды
     */
    public void openCardInDeck(final int indexOpen) {
        setMove(true);
        setLock(true);
        offsetX = 60 * indexOpen;
        addAction(Actions.sequence(Actions.moveBy(230 + offsetX, 0, 0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
            }
        })));
        getStartPos().add(230 + (60 * indexOpen), 0);
        setZIndex(9999);
        deckMode = false;
        setDrawBack(false);
        final CellGame parent = (CellGame) getParent();

        parent.getSteps().peek().countBackToDeck++;
        parent.getTopScoreView().iterateStep();


        if (P.get().pref.getBoolean(S.DIF_CELL)) {
            final ArrayList<CardCell> deck = parent.getStackCard().get(stackNum);
            int prev = posInStack - 1;
            if (indexOpen < 2 && prev >= 0) {

                isSubCard = true;


                deck.get(prev).openCardInDeck(indexOpen + 1);

            } else {
               /* final int copyPos = posInStack;
                final int copyIndex = indexOpen;
                parent.getSteps().push(new Runnable() {
                    @Override
                    public void run() {
                        int next = copyPos + copyIndex + 1;
                        for (int i = copyPos, j = copyIndex; i < next; i++,j--) {
                            if(i < deck.size()) {
                                deck.get(i).backMoveToDeck(j);
                                deck.get(i).setSubCard(false);
                            }
                        }


                        for (int i = next,j=2; i < next+3; i++,j--) {
                            if(i < deck.size()) {
                                deck.get(i).moveBy(60 * j, 0);

                                deck.get(i).setSubCard(true);
                                deck.get(i).getStartPos().x = deck.get(i).getX();
                            }
                        }
                    }
                });*/
            }

        } else {
           /* parent.getSteps().push(new Runnable() {
                @Override
                public void run() {

                    backMoveToDeck(0);
                }
            });*/
        }

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

    public boolean isSubCard() {
        return isSubCard;
    }

    public void setSubCard(boolean subCard) {
        isSubCard = subCard;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    @Override
    protected void touchDown(InputEvent event, float x, float y) {


        if (getActions().size > 0 || isSubCard) return;

        ArrayList<CardCell> cells = ((CellGame) getParent()).getStackCard().get(stackNum);
        if (this instanceof EmptyCardDeck) {

            final CellGame parent = (CellGame) getParent();
            if (parent.checkEmptyDeck()) {

                parent.restartDeck();
            }
            return;
        }

        if (posInStack < cells.size() - 1 && stackNum != CellGame.CARD_DECK_NUM) isMoveUp = true;
        if (stackNum != CellGame.CARD_DECK_NUM) {
            for (int i = posInStack; i < cells.size(); i++) {
                cells.get(i).setZIndex(9999);
            }
        }

        getStartTouch().set(x, y);
    }

    @Override
    protected void touchUp(InputEvent event, float x, float y) {
        if (getActions().size > 0 || this instanceof EmptyCardDeck || isSubCard) return;

        isMoveUp = false;
        // если в колоде
        if (deckMode) {
            StepBack s = new StepBack(0,0,0,0);
            s.beginPosBackToDeck = posInStack;
            ((CellGame) getParent()).getSteps().push(s);
            if (P.get().pref.getBoolean(S.DIF_CELL)) {
                ((CellGame) getParent()).clearDeckFromSub();
            }
            openCardInDeck();
        } else if (movingCard) {
            checkPosition();
        }

        movingCard = false;
        super.touchUp(event, x, y);


    }

    @Override
    protected void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        if (isSubCard) return;
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
