package me.creese.solitaire.entity.games.cell;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.Card;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;
import me.creese.util.display.Display;

public class CardCell extends Card {


    private int stackNum;

    private boolean deckMode;
    // если карта не видна за лругими чуть приподнять ее
    private boolean isMoveUp;

    // если true недоступная карта из трех
    private boolean isSubCard;
    private int offsetX;

    public CardCell(float x, float y, CardType cardType, int numberCard, Display root) {
        super(x, y, cardType, numberCard,root);
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

    /**
     * Возврат в колоду
     */
    public void backMoveToDeck() {
        CellGame parent = (CellGame) getParent();

        //if(!parent.isCardActionsClear()) return;

        setMove(false);
        setLock(true);
        addAction(Actions.sequence(Actions.moveTo(parent.getEmptyCardDeck().getX(), parent.getEmptyCardDeck().getY(),0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
            }
        })));
        getStartPos().set(parent.getEmptyCardDeck().getX(), parent.getEmptyCardDeck().getY());
        setZIndex(9999);
        deckMode = true;
        offsetX = 0;
        setDrawBack(true);
        setSubCard(false);

        int prev = posInStack - 2;
        if(prev > 0) {
            parent.getStackCard().get(stackNum).get(prev).setDrawShadow(false);
        }

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

        ArrayList<ArrayList<CardCell>> stackCard = ((CellGame) getParent()).getStackCard();
        ArrayList<CardCell> sta = stackCard.get(stackNum);
        int next = posInStack + 1;

        if (next < sta.size() && stackNum != CellGame.CARD_DECK_NUM) {

            sta.get(next).moveToStartPosition(startIndex);
        }
        addAction(Actions.sequence(Actions.moveTo(getStartPos().x, getStartPos().y, 0.2f + (0.05f * (posInStack - startIndex))), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
            }
        })));
    }


    public boolean tryMoveToPosition(int num, CardCell child) {
        return tryMoveToPosition(num, child, true,false,false);
    }

    /**
     * Попытаться переместить карты на позицию
     *
     * @param num   Номер стека на который помещаем
     * @param child
     * @param justCheck Простая проверка без изменения startPos
     * @param isCheckToAuto Проверка на автосбор
     * @param useAllStack Проверять конечные стеки карт даже если карта находится не наверху стека
     * @return
     */
    public boolean tryMoveToPosition(int num, CardCell child, boolean isCheckToAuto,boolean justCheck,boolean useAllStack) {
        final CellGame parent = (CellGame) getParent();
        ArrayList<ArrayList<CardCell>> stackCard = parent.getStackCard();
        final ArrayList<CardCell> tmpStack = stackCard.get(stackNum);
        final ArrayList<CardCell> cells = stackCard.get(child.getStackNum());
        int prevCard = num < 7 ? getNumberCard() + 1 : getNumberCard() - 1;
        if (child.getNumberCard() == prevCard && !child.isDrawBack()) {



            final int numScoreAdd;
            if (num >= 7) {
                if(!useAllStack) {
                    // поместить на конечный стек карту можно только если она в самом конце стека
                    if (posInStack != tmpStack.size() - 1 && stackNum != CellGame.CARD_DECK_NUM
                            || !child.getCardType().equals(getCardType()))
                        return false;
                } else {
                    if(!child.getCardType().equals(getCardType())) return false;
                }

                numScoreAdd = 20;
                //тени
                if (num != CellGame.CARD_DECK_NUM && !justCheck) {
                    ArrayList<CardCell> c = stackCard.get(num);

                    int n = c.size() - 2;

                    if(n >=1) {
                        c.get(n).setDrawShadow(false);
                    }
                }




            } else {
                if(child.getColorCard() == getColorCard())
                    return false;

                numScoreAdd = (getNumberCard() * 2) + 5;
            }

            if(justCheck) return true;

            parent.getMenu().getTopScoreView().addScore(numScoreAdd);

            parent.getMenu().getTopScoreView().iterateStep();



            StepBack s = new StepBack(child.getStackNum(), stackNum, cells.size(), offsetX);
            s.minusScore = numScoreAdd;

            if(posInStack-1 > 0) {
                CardCell pre = tmpStack.get(posInStack - 1);

                s.drawBackPrevCards = pre.isDrawBack();
            }

            if (posInStack > 1 && posInStack <= tmpStack.size() && stackNum != CellGame.CARD_DECK_NUM) {
                CardCell cardPrev = tmpStack.get(posInStack - 1);
                cardPrev.setDrawBack(false);
                cardPrev.setMove(true);
            }
            if(stackNum == CellGame.CARD_DECK_NUM) {
                s.toPosAdd = posInStack;
                // рисуем тень на предыдущей картой
                int n = posInStack + 2;
                if(n < tmpStack.size()) {
                    tmpStack.get(n).setDrawShadow(true);
                }

            }

            parent.getSteps().push(s);
            if(stackNum >= 7 && stackNum < CellGame.CARD_DECK_NUM) {


                int n = tmpStack.size() - 2;

                if(n >=1) {
                    tmpStack.get(n).setDrawShadow(true);
                }
            }


            for (int j = posInStack; j < tmpStack.size(); j++) {
                float _y;
                if (num < 7) {
                    if(cells.size() == 1) {
                        _y = cells.get(0).getY();
                    } else {
                        _y = cells.get(cells.size() - 1).getStartPos().y - 67;
                    }
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

            if (isCheckToAuto) {
                parent.checkToAuto();
                parent.makeHelp();
            }

            parent.checkToWin();


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
        final CellGame parent = (CellGame) getParent();

        //if(!parent.isCardActionsClear() && indexOpen == 0) return;

        final ArrayList<CardCell> deck = parent.getStackCard().get(stackNum);

        setMove(true);
        setLock(true);


        final int n = posInStack + 2;



        setDrawShadow(true);
        offsetX = 63 * indexOpen;
        int moveOffset = CellGame.FROM_DECK_DEF_DIFICULT;
        if(P.get().pref.getBoolean(S.DIF_CELL)) {
            moveOffset = CellGame.FROM_DECK_HARD_DIFICULT;
        }
        addAction(Actions.sequence(Actions.moveBy(moveOffset + offsetX, 0, 0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                setLock(false);
                if(n < deck.size()) {
                    final CardCell nextCard = deck.get(n);
                    nextCard.setDrawShadow(false);
                }
            }
        })));
        getStartPos().add(moveOffset + (63 * indexOpen), 0);
        setZIndex(9999);
        deckMode = false;
        setDrawBack(false);

        parent.getSteps().peek().countBackToDeck++;



        if (P.get().pref.getBoolean(S.DIF_CELL)) {
            int prev = posInStack - 1;
            if (indexOpen < 2 && prev >= 0) {

                isSubCard = true;


                deck.get(prev).openCardInDeck(indexOpen + 1);

            } else {
                parent.getMenu().getTopScoreView().iterateStep();
            }

        } else {
            parent.getMenu().getTopScoreView().iterateStep();
        }

        if(posInStack == deck.size()-1) {
            parent.toGameOver(true);
        }
        if(indexOpen == 0) {
            parent.makeHelp();

        }



    }




    public int getStackNum() {
        return stackNum;
    }

    public void setStackNum(int stackNum) {
        this.stackNum = stackNum;
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
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            if(stackNum == CellGame.CARD_DECK_NUM ) {

                if(posInStack >0)
                setDrawShadow(false);

            }
        }
    }

    @Override
    protected void touchDown(InputEvent event, float x, float y) {


        if (getActions().size > 0 || isSubCard) return;

        ArrayList<CardCell> cells = ((CellGame) getParent()).getStackCard().get(stackNum);
        if (this instanceof EmptyCardDeck) {

            final CellGame parent = (CellGame) getParent();
            if (parent.checkEmptyDeck() && parent.isCardActionsClear()) {

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
                ((CellGame) getParent()).clearDeckFromSub(false);
            }
            openCardInDeck();
        } else if (panCard) {
            checkPosition();
        }

        panCard = false;
        super.touchUp(event, x, y);


    }

    @Override
    protected void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        if (isSubCard) return;
        super.pan(event, x, y, deltaX, deltaY);
        if (!isMove()) return;

        panCard = true;
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
