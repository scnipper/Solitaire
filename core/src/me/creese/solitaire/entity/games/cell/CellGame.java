package me.creese.solitaire.entity.games.cell;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.S;


public class CellGame extends BaseGame {

    public static final int CARD_DECK_NUM = 11;
    private final Random random;
    private final ArrayList<ArrayList<CardCell>> stackCard;
    // история ходов
    private final LinkedList<StepBack> steps;
    private ArrayList<DeckItem> deck;
    private boolean lockBackStep;
    private EmptyCardDeck emptyCardDeck;
    private Menu menu;
    private boolean isStarted;


    public CellGame() {
        random = new Random();

        steps = new LinkedList<>();

        stackCard = new ArrayList<>();
        createDeck();

    }


    @Override
    public void start() {

        if(isStarted) return;

        isStarted = true;

        steps.clear();
        menu = getRoot().getGameViewForName(GameScreen.class).getMenu();
        menu.getTopScoreView().startTime();
        for (int i = 0; i < 11; i++) {
            stackCard.add(new ArrayList<CardCell>());
            if(i < 7) {

                EmptyCard cardFirst = new EmptyCard(33 + (i * 148), 1213, CardType.DIAMONDS, 1);
                cardFirst.setStackNum(i);
                cardFirst.posStack(0);
                stackCard.get(i).add(cardFirst);
                addActor(cardFirst);
                for (int j = 0; j < i + 1; j++) {

                    int indexDeck = random.nextInt(deck.size());
                    DeckItem deckItem = deck.get(indexDeck);
                    int spaceBetweenCards = 24;

                    CardCell card = new CardCell(33 + (i * 148), 1213 - (j * spaceBetweenCards),
                            deckItem.getType(), deckItem.getNumber());
                    deck.remove(indexDeck);

                    if (j == i) {
                        card.setMove(true);
                    } else card.setDrawBack(true);
                    card.setStackNum(i);
                    card.posStack(stackCard.get(i).size());
                    stackCard.get(i).add(card);
                    addActor(card);
                }
            } else {
                PlaceCard placeCard = new PlaceCard(33 + ((i-7) * 148), 1439, CardType.getForNum(i-7), 1);
                placeCard.setStackNum(i);
                placeCard.posStack(0);
                stackCard.get(i).add(placeCard);
                addActor(placeCard);
            }

        }
        ArrayList<CardCell> cardCells = new ArrayList<>();
        stackCard.add(cardCells);

        emptyCardDeck = new EmptyCardDeck(922, 1439, CardType.DIAMONDS, 1, 11);
        addActor(emptyCardDeck);

        for (int i = 0; i < deck.size(); i++) {

            int index = random.nextInt(deck.size());
            DeckItem deckItem = deck.get(index);

            CardCell card = new CardCell(922, 1439, deckItem.getType(), deckItem.getNumber());


            card.setDrawBack(true);
            card.setDeckMode(true);
            card.posStack(cardCells.size());
            card.setStackNum(11);
            cardCells.add(card);
            addActor(card);
            deck.remove(index);
            i--;

        }

        //debugWinCards();
        //debugWinCardsRead();


    }

    private void debugWinCardsRead() {


        FileHandle debugWin = Gdx.files.internal("debug_win");

        String s = debugWin.readString();

        String[] split = s.split("\n");


        for (int i = 0; i < split.length - 1; i++) {
            ArrayList<CardCell> cardCells = new ArrayList<>();
            stackCard.add(cardCells);
            EmptyCard cardFirst = new EmptyCard(50 + (i * 230), 400, me.creese.solitaire.entity.CardType.DIAMONDS, 1);
            cardFirst.setStackNum(i);
            cardFirst.posStack(0);
            stackCard.get(i).add(cardFirst);
            addActor(cardFirst);
            String[] cards = split[i].split("/");

            for (int j = 0; j < cards.length; j++) {

                String[] options = cards[j].split(",");


                CardCell card = new CardCell(Float.valueOf(options[2]), Float.valueOf(options[3]), CardType.valueOf(options[0]), Integer.valueOf(options[1]));

                if (j == i) {
                    card.setMove(true);
                } else card.setDrawBack(true);
                card.setStackNum(i);
                card.posStack(stackCard.get(i).size());
                stackCard.get(i).add(card);
                addActor(card);
            }
        }

        for (int i = 7; i < 11; i++) {
            ArrayList<CardCell> cardCells = new ArrayList<>();
            stackCard.add(cardCells);
            PlaceCard placeCard = new PlaceCard(900 + ((i - 7) * 230), 750, CardType.DIAMONDS, 1);
            placeCard.setStackNum(i);
            placeCard.posStack(0);
            cardCells.add(placeCard);
            addActor(placeCard);
        }


        String[] last = split[split.length - 1].split("/");

        ArrayList<CardCell> cardCells = new ArrayList<>();
        stackCard.add(cardCells);

        addActor(new EmptyCardDeck(50, 750, me.creese.solitaire.entity.CardType.DIAMONDS, 1, 11));


        for (int j = 0; j < last.length; j++) {

            String[] options = last[j].split(",");

            CardCell card = new CardCell(Float.valueOf(options[2]), Float.valueOf(options[3]), CardType.valueOf(options[0]), Integer.valueOf(options[1]));


            card.setDrawBack(true);
            card.setDeckMode(true);
            card.posStack(cardCells.size());
            card.setStackNum(CARD_DECK_NUM);
            cardCells.add(card);
            addActor(card);

        }


    }

    private void debugWinCards() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < stackCard.size(); i++) {
            ArrayList<CardCell> cardCells = stackCard.get(i);
            if (i < 7 || i == 11) {
                for (int j = i < 7 ? 1 : 0; j < cardCells.size(); j++) {
                    CardCell cardCell = cardCells.get(j);
                    sb.append(cardCell.getCardType());
                    sb.append(',');
                    sb.append(cardCell.getNumberCard());
                    sb.append(',');
                    sb.append(cardCell.getX());
                    sb.append(',');
                    sb.append(cardCell.getY());
                    if (j < cardCells.size() - 1) sb.append('/');
                }
                if (i < stackCard.size() - 1) sb.append('\n');
            }
        }

    }

    public LinkedList<StepBack> getSteps() {
        return steps;
    }

    @Override
    public void restart() {

    }

    @Override
    public void cancelStep() {
        if(steps.size() > 0 && !lockBackStep) {

            final ArrayList<CardCell> deck = stackCard.get(CARD_DECK_NUM);
            final StepBack stepBack = steps.pop();

            menu.getTopScoreView().decrementStep();
            menu.getTopScoreView().addScore(-stepBack.minusScore);
            if(stepBack.forwardDeck) {
                forwardDeck(new Runnable() {
                    @Override
                    public void run() {
                        if(stepBack.beginIndexOffset != -1) {
                            int end = stepBack.beginIndexOffset+stepBack.countOffsets;
                            for (int i = stepBack.beginIndexOffset-1,j=stepBack.countOffsets; i < end; i++,j--) {
                                final int finalI = i;
                                deck.get(i).addAction(Actions.sequence(Actions.moveBy(60*j,0,0.2f),Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        deck.get(finalI).getStartPos().x = deck.get(finalI).getX();
                                        lockBackStep = false;
                                    }
                                })));

                                deck.get(i).setSubCard(i >= stepBack.beginIndexOffset);
                                deck.get(i).setOffsetX(60*j);

                            }

                        }

                    }
                });


                return;
            }


            if(stepBack.beginPosBackToDeck != -1) {
                int count = stepBack.countBackToDeck;

                int startPos = (stepBack.beginPosBackToDeck-count)+1;
                for (int i = startPos; i < startPos+count; i++) {
                    deck.get(i).backMoveToDeck();
                    deck.get(i).setZIndex(99999);
                }

                if(stepBack.beginIndexOffset != -1) {
                    int end = stepBack.beginIndexOffset+stepBack.countOffsets+1;
                    for (int i = stepBack.beginIndexOffset,j=stepBack.countOffsets; i < end; i++,j--) {

                        deck.get(i).moveBy(60*j,0);
                        deck.get(i).setSubCard(i > stepBack.beginIndexOffset);
                        deck.get(i).getStartPos().x = deck.get(i).getX();
                        deck.get(i).setOffsetX(60*j);
                    }

                }

            } else {
                ArrayList<CardCell> fromStack = stackCard.get(stepBack.fromStack);
                ArrayList<CardCell> toStack = stackCard.get(stepBack.toStack);
                int toPosAdd = stepBack.toPosAdd;

                if (toStack.size() > 1 && stepBack.toStack < 7) {
                    CardCell c = toStack.get(toStack.size() - 1);
                    c.setDrawBack(true);
                    c.setMove(false);
                }
                CardCell first = fromStack.get(stepBack.fromPos);
                for (int i = stepBack.fromPos; i < fromStack.size(); i++) {

                    CardCell tmp = fromStack.get(i);


                    tmp.setStackNum(stepBack.toStack);
                    if (stepBack.toStack == CARD_DECK_NUM) {
                        tmp.getStartPos().x = 280 + tmp.getOffsetX();

                        tmp.getStartPos().y = toStack.get(0).getY();
                    } else {
                        tmp.getStartPos().x = toStack.get(0).getX();
                        if (stepBack.toStack >= 7) {
                            tmp.getStartPos().y = toStack.get(0).getY();
                        } else
                        tmp.getStartPos().y = 400 - ((toStack.size() - 1) * 40);
                    }

                    if(toPosAdd != -1) {
                        if(tmp.getOffsetX() > 0) {
                            toStack.get(toPosAdd).setSubCard(true);
                        }
                        toStack.add(toPosAdd, tmp);
                        tmp.posStack(toPosAdd);
                        updateDeckIndex();
                    }
                    else {
                        tmp.posStack(toStack.size());
                        toStack.add(tmp);
                    }
                    fromStack.remove(i);
                    i--;

                }
                first.moveToStartPosition();

            }

        }
    }

    private void stepBuildCards() {


        for (int i = 0; i < 8; i++) {
            if (i == 7) i = CARD_DECK_NUM;
            ArrayList<CardCell> cardCells = stackCard.get(i);
            if (i == CARD_DECK_NUM) {

                boolean isEnd = false;

                if (cardCells.size() > 0) {
                    if (cardCells.get(cardCells.size() - 1).isDeckMode())
                        cardCells.get(cardCells.size() - 1).openCardInDeck();
                }

                for (int l = 0; l < cardCells.size(); l++) {
                    CardCell cardDeck = cardCells.get(l);

                    if (!cardDeck.isDeckMode()) {
                        for (int k = 7; k < 11; k++) {

                            if (cardDeck.tryMoveToPosition(k, stackCard.get(k).get(stackCard.get(k).size() - 1), false)) {
                                cardDeck.setZIndex(9999);
                                cardDeck.moveToStartPosition();

                                isEnd = false;
                                break;
                            } else {
                                isEnd = true;
                            }
                            updateDeckIndex();

                        }
                        if (isEnd) {
                            if (checkEmptyDeck()) {
                                restartDeck();
                            } else {
                                CardCell tmpCard = cardCells.get(Math.max(0, l - 1));
                                if (tmpCard.isDeckMode()) {
                                    tmpCard.openCardInDeck();

                                    break;
                                }
                            }
                            break;
                        }
                    }
                }


            } else {

                for (int j = cardCells.size() - 1; j >= 1; j--) {
                    CardCell cardCell = cardCells.get(j);
                    boolean isEnd = false;

                    for (int k = 7; k < 11; k++) {

                        ArrayList<CardCell> endCards = stackCard.get(k);

                        if (cardCell.tryMoveToPosition(k, endCards.get(endCards.size() - 1), false)) {
                            cardCell.setZIndex(9999);
                            cardCell.moveToStartPosition();

                            break;
                        } else {
                            isEnd = true;
                        }

                    }
                    if (isEnd) {

                        break;
                    }
                }


            }
        }

    }

    /**
     * Автоматическая сборка карт
     */
    public void checkToAuto() {


        for (int i = 0; i < 7; i++) {
            ArrayList<CardCell> cardCells = stackCard.get(i);
            for (CardCell cardCell : cardCells) {
                if (cardCell.isDrawBack()) return;
            }
        }

        lockCards(true);


        addAction(Actions.repeat(-1, Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                if(!checkToGameOver())
                stepBuildCards();
                else getActions().clear();
            }
        }))));

    }

    public boolean checkToGameOver() {
        for (int i = 0; i < 8; i++) {
            if (i == 7) {
                i = CARD_DECK_NUM;
                if (stackCard.get(i).size() > 0) {
                    return false;
                }
            } else {

                if (stackCard.get(i).size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkEmptyDeck() {

        ArrayList<CardCell> cardCells = stackCard.get(CARD_DECK_NUM);
        for (CardCell cell : cardCells) {
            if (cell.isDeckMode()) return false;
        }

        return true;
    }

    private void createDeck() {
        deck = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                DeckItem deckItem = new DeckItem(CardType.getForNum(j), i + 1);
                deck.add(deckItem);
            }
        }

    }

    public void updateDeckIndex() {
        ArrayList<CardCell> stack = stackCard.get(CARD_DECK_NUM);
        for (int i = 0; i < stack.size(); i++) {
            stack.get(i).posStack(i);
        }
    }

    public void lockCards(boolean isLock) {
        for (ArrayList<CardCell> cardCells : stackCard) {
            for (CardCell cardCell : cardCells) {
                cardCell.setLock(isLock);
            }
        }
    }

    public void clearDeckFromSub() {



        final ArrayList<CardCell> deck = stackCard.get(CARD_DECK_NUM);
        int startIndex = -1;
        int count = 0;
        for (int i = 0; i < deck.size(); i++) {
            CardCell cardCell = deck.get(i);
            if(cardCell.isSubCard()) {
                if(startIndex < 0) startIndex = i;
                count++;
            }
        }




        startIndex--;
        if(startIndex >=0) {
            StepBack tmp = steps.peek();
            if(tmp.beginPosBackToDeck != -1) {
                tmp.beginIndexOffset = startIndex;
                tmp.countOffsets = count;
            }
            for (int i = startIndex,j=count; i < startIndex+count+1; i++,j--) {

                deck.get(i).moveBy(63*j,0);
                deck.get(i).setSubCard(false);
                deck.get(i).getStartPos().x = deck.get(i).getX();
                deck.get(i).setOffsetX(0);

            }
        }

    }

    public void forwardDeck(final Runnable afterAllMove) {
        final ArrayList<CardCell> cardCells = stackCard.get(CARD_DECK_NUM);
        lockBackStep = true;
        for (int i = 0; i < cardCells.size(); i++) {
            final CardCell cell = cardCells.get(i);
            cell.setLock(true);
            cell.addAction(Actions.sequence(Actions.moveTo(emptyCardDeck.getX(), emptyCardDeck.getY(), 0.1f + (0.013f * i)),Actions.run(new Runnable() {
                @Override
                public void run() {
                    if(cell.getPosInStack() == cardCells.size()-1) {

                        lockCards(false);
                        if (afterAllMove != null) {
                            afterAllMove.run();
                        }

                    }
                }
            })));

            cell.getStartPos().set(emptyCardDeck.getX(), emptyCardDeck.getY());
            cell.setZIndex(0);
            cell.setDeckMode(false);
            cell.setDrawBack(false);
            cell.setMove(true);

        }

        if(!P.get().pref.getBoolean(S.DIF_CELL)) {
            lockBackStep = false;
        }
    }
    public void restartDeck() {
        final ArrayList<CardCell> deck = stackCard.get(CARD_DECK_NUM);
        StepBack s = new StepBack(0,0,0,0);
        s.forwardDeck = true;
        steps.push(s);
        if(P.get().pref.getBoolean(S.DIF_CELL)) {
            for (int i = deck.size()-1; i >= 0; i--) {
                if(deck.get(i).isSubCard()) {
                    s.beginIndexOffset = i;
                    s.countOffsets++;
                }
            }
            clearDeckFromSub();
        }


        for (int i = 0; i < deck.size(); i++) {
            final CardCell cell = deck.get(i);
            cell.setLock(true);
            cell.addAction(Actions.sequence(Actions.moveTo(emptyCardDeck.getX(), emptyCardDeck.getY(), 0.1f + (0.013f * i)),Actions.run(new Runnable() {
                @Override
                public void run() {
                    if(cell.getPosInStack() == deck.size()-1) {

                        lockCards(false);

                    }
                }
            })));
            cell.getStartPos().set(emptyCardDeck.getX(), emptyCardDeck.getY());
            cell.setZIndex(9999);
            cell.setDeckMode(true);
            cell.setDrawBack(true);
            cell.setMove(false);
            cell.posStack(i);

        }
    }

    public EmptyCardDeck getEmptyCardDeck() {
        return emptyCardDeck;
    }


    public Menu getMenu() {
        return menu;
    }

    private void printDeck(int deckNum) {
        ArrayList<CardCell> cardCells = stackCard.get(deckNum);
        StringBuilder sb = new StringBuilder();
        System.out.println(deckNum);
        for (CardCell cardCell : cardCells) {
            sb.append(cardCell.getPosInStack());
            sb.append(" ");
        }
        System.out.println(sb);
    }
    public ArrayList<ArrayList<CardCell>> getStackCard() {
        return stackCard;
    }
}
