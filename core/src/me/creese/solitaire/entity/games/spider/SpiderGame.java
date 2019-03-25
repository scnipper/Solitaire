package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.util.ArrayList;
import java.util.LinkedList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.games.HelpShow;
import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.entity.impl.Card;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.TopScoreView;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.util.P;

public class SpiderGame extends BaseGame {

    public static final int SPACE_BETWEEN_TWO_CARDS = 24;
    public static final int SPACE_BETWEEN_TWO_OPEN_CARDS = 67;

    private static final int EASY_DIF = 300;
    private static final int MEDIUM_DIF = 302;
    private static final int HARD_DIF = 304;
    private static final int STEP_HOUSE_CARD = 50;

    private final ArrayList<ArrayList<SpiderCard>> decks;
    private final ArrayList<SpiderCard> startDeck;
    private final ArrayList<AddNewCard> newCards;
    private final Vector2 houseCardPos;
    private final LinkedList<StepBackSpider> steps;
    private final ArrayList<Card> houseCards;
    private boolean isStart;
    private LinkedList<HelpShow> hints;

    public SpiderGame() {
        decks = new ArrayList<>();
        startDeck = new ArrayList<>();
        newCards = new ArrayList<>();
        houseCardPos = new Vector2();
        steps = new LinkedList<>();
        houseCards = new ArrayList<>();
        hints = new LinkedList<>();
    }

    @Override
    public void start() {

        if (isStart) return;

        TopScoreView topScoreView = getRoot().getGameViewForName(GameScreen.class).getMenu().getTopScoreView();
        topScoreView.startTime();
        topScoreView.setStep(0);
        topScoreView.setScore(0);

        isStart = true;
        houseCardPos.set(100, 100);
        AddNewCard addNewCard = new AddNewCard(P.WIDTH + 200, 100, getRoot());
        addActor(addNewCard);
        addNewCard.posStack(0);
        newCards.clear();
        newCards.add(addNewCard);

        //int dif = P.get().pref.getInteger(S.DIF_SPIDER,EASY_DIF);
        int dif = EASY_DIF;

        switch (dif) {
            case EASY_DIF:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 13; j++) {
                        SpiderCard spiderCard = new SpiderCard(P.WIDTH + 200, 100, CardType.PEAKS, j + 1, getRoot());
                        startDeck.add(spiderCard);
                    }
                }
                break;
            case MEDIUM_DIF:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 13; j++) {
                        SpiderCard spiderCard = new SpiderCard(P.WIDTH + 200, 100, CardType.PEAKS, j + 1, getRoot());
                        startDeck.add(spiderCard);
                        SpiderCard spiderCard2 = new SpiderCard(P.WIDTH + 200, 100, CardType.HEARTS, j + 1, getRoot());
                        startDeck.add(spiderCard2);
                    }

                }
                break;
            case HARD_DIF:
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 13; j++) {
                        SpiderCard spiderCard = new SpiderCard(P.WIDTH + 200, 100, CardType.PEAKS, j + 1, getRoot());
                        startDeck.add(spiderCard);
                        SpiderCard spiderCard2 = new SpiderCard(P.WIDTH + 200, 100, CardType.HEARTS, j + 1, getRoot());
                        startDeck.add(spiderCard2);
                        SpiderCard spiderCard3 = new SpiderCard(P.WIDTH + 200, 100, CardType.DIAMONDS, j + 1, getRoot());
                        startDeck.add(spiderCard3);
                        SpiderCard spiderCard4 = new SpiderCard(P.WIDTH + 200, 100, CardType.CLUBS, j + 1, getRoot());
                        startDeck.add(spiderCard4);
                    }

                }
                break;
        }


        for (int i = 0; i < 10; i++) {
            ArrayList<SpiderCard> tmp = new ArrayList<>();
            EmptyCardSpider cardSpider = new EmptyCardSpider(1650, getRoot());
            cardSpider.setX(21 + (i * (cardSpider.getWidth() + 21)));

            cardSpider.getStartPos().set(cardSpider.getX(), cardSpider.getY());
            tmp.add(cardSpider);
            addActor(cardSpider);
            decks.add(tmp);
        }

        int addDeck = 0;
        int countCard = 0;

        ArrayList<SpiderCard> tmp = new ArrayList<>();

        decks.add(tmp);
        do {
            int index = getRandom().nextInt(startDeck.size());
            SpiderCard card = startDeck.get(index);

            if (countCard < 54) {
                int posInStack = decks.get(addDeck).size();
                card.posStack(posInStack);
                card.setDeckNum(addDeck);
                decks.get(addDeck).add(card);

                card.getStartPos().set(21 + (addDeck * (card.getWidth() + 21)), 1650 - ((posInStack - 1) * SPACE_BETWEEN_TWO_CARDS));
                card.setDrawBack(true);

                addDeck++;

                countCard++;
                if (addDeck == decks.size() - 1) addDeck = 0;
            } else {
                tmp.add(card);

                if (tmp.size() == 10) {
                    tmp = new ArrayList<>();
                    decks.add(tmp);
                }
            }
            startDeck.remove(index);

        } while (startDeck.size() > 0);


        moveToPositionDeck(false);


        decks.remove(decks.size() - 1);


    }

    public void printStack() {
        System.out.println("size start deck = " + startDeck.size());

        for (int i = 0; i < decks.size(); i++) {
            System.out.println("size deck " + i + " " + decks.get(i).size());
        }
    }

    /**
     * Добавление дополнительных карт
     *
     * @return
     */
    public boolean addNewLineCard(int index) {

        for (ArrayList<SpiderCard> deck : decks) {
            if (deck.size() == 1) return false;
        }

        ArrayList<SpiderCard> from = decks.get(decks.size() - index - 1);
        for (int i = 0; i < 10; i++) {
            ArrayList<SpiderCard> to = decks.get(i);

            SpiderCard fromCard = from.get(from.size() - 1);
            SpiderCard toCard = to.get(to.size() - 1);


            addActor(fromCard);
            fromCard.setVisible(false);


            fromCard.setDeckNum(i);
            fromCard.posStack(to.size());

            to.add(fromCard);


            fromCard.setMove(true);
            from.remove(from.size() - 1);

            fromCard.getStartPos().set(toCard.getStartPos().x, toCard.getStartPos().y - SPACE_BETWEEN_TWO_OPEN_CARDS);
            updateMoveCards(i);


        }
        moveToPositionDeck(true);
        return true;
    }

    /**
     * Проверка колоды карт на возможность перемещения
     *
     * @param stackIndex
     */
    public void updateMoveCards(int stackIndex) {
        ArrayList<SpiderCard> cards = decks.get(stackIndex);

        for (int i = 0; i < cards.size() - 1; i++) {
            cards.get(i).setMove(false);
        }

        for (int i = cards.size() - 1; i > 1; i--) {
            SpiderCard placeCard = cards.get(i);
            SpiderCard toCard = cards.get(i - 1);

            if (placeCard.rightConditionCard(toCard)) {
                toCard.setMove(true);
            } else {
                break;
            }

        }
        cards.get(0).setMove(false);
    }

    /**
     * Проверка на собранность колоды
     */
    public StepBackSpider checkWinCombination(int stackNum) {
        ArrayList<SpiderCard> cards = decks.get(stackNum);

        if (cards.size() >= 14) {

            for (int i = 1; i < cards.size(); i++) {
                SpiderCard findCard = cards.get(i);

                if (findCard.getNumberCard() == 13 && i + 12 < cards.size()) {
                    boolean conditionOk = true;
                    for (int j = i + 1; j < cards.size(); j++) {
                        SpiderCard placeCard = cards.get(j);
                        SpiderCard toCard = cards.get(j - 1);
                        if (!placeCard.rightConditionCard(toCard)) {
                            conditionOk = false;
                            break;
                        }

                    }

                    if (conditionOk) {
                        SpiderCard prevCard = cards.get(i - 1);
                        StepBackSpider stepBackSpider = new StepBackSpider(0, stackNum, 0);

                        stepBackSpider.moveLast = prevCard.isMove();
                        stepBackSpider.drawBackLast = prevCard.isDrawBack();

                        if (prevCard.getNumberCard() != -1) {
                            prevCard.setDrawBack(false);
                            prevCard.setMove(true);
                        }

                        for (int j = cards.size() - 1; j >= i; j--) {
                            final SpiderCard moveCard = cards.get(j);
                            moveCard.setZIndex(9999);
                            moveCard.getStartPos().set(houseCardPos.x, houseCardPos.y);
                            moveCard.moveToStartPos(-1, new Runnable() {
                                @Override
                                public void run() {
                                    if (moveCard.getNumberCard() < 13) moveCard.remove();
                                }
                            });
                            cards.remove(j);

                        }
                        findCard.setMove(false);
                        houseCardPos.x += STEP_HOUSE_CARD;

                        getRoot().getGameViewForName(GameScreen.class).getMenu().getTopScoreView().addScore(100);

                        houseCards.add(findCard);
                        stepBackSpider.minusScore = 100;
                        stepBackSpider.fromHouse = true;

                        updateMoveCards(stackNum);
                        System.out.println(" win  cards");
                        return stepBackSpider;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Анимация перемещения карт на начальную позицию
     *
     * @param onlyLast только последняя линия
     */
    private void moveToPositionDeck(final boolean onlyLast) {


        addAction(Actions.repeat(-1, Actions.run(new Runnable() {
            int deckNum = 0;
            int cardNum = onlyLast ? decks.get(deckNum).size() - 1 : 1;

            SpiderCard startCard = decks.get(deckNum).get(cardNum);

            @Override
            public void run() {
                if (startCard.getActions().size == 0) {
                    addActor(startCard);

                    startCard.setVisible(true);
                    startCard.addAction(Actions.sequence(Actions.moveTo(startCard.getStartPos().x, startCard.getStartPos().y, 0.05f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            deckNum++;


                            if (deckNum == 10) {
                                deckNum = 0;
                                cardNum++;
                            }
                            if (onlyLast) {
                                cardNum = decks.get(deckNum).size() - 1;
                                if (deckNum == 0) getActions().clear();
                            }

                            if (cardNum == decks.get(deckNum).size() && !onlyLast) {
                                getActions().clear();

                                for (int i = 0; i < 10; i++) {
                                    ArrayList<SpiderCard> deck = decks.get(i);
                                    deck.get(deck.size() - 1).setDrawBack(false);
                                    deck.get(deck.size() - 1).setMove(true);
                                }


                                for (int i = 1; i < 5; i++) {
                                    AddNewCard addNewCard = new AddNewCard(P.WIDTH + 200, 100, getRoot());
                                    addNewCard.setZIndex(0);
                                    addNewCard.posStack(i);
                                    addNewCard.getStartPos().x -= i * 30;
                                    newCards.add(addNewCard);
                                    addActor(addNewCard);
                                    addNewCard.moveToStartPos(500, null);

                                }

                                return;
                            }
                            startCard = decks.get(deckNum).get(cardNum);

                        }
                    })));
                }
            }
        })));
    }

    /**
     * Метод возвращает true если все actions с карт удалены
     *
     * @return
     */
    public boolean isCardActionsClear() {

        for (ArrayList<SpiderCard> cardCells : decks) {
            for (SpiderCard cardCell : cardCells) {
                if (cardCell.getActions().size > 0) return false;
            }
        }

        return true;
    }

    /**
     * Создание подсказки
     */
    public void makeHelp() {
        hints.clear();

        for (int i = 0; i < decks.size() - 5; i++) {
            ArrayList<SpiderCard> cards = decks.get(i);

            for (int j = cards.size() - 1; j > 0; j--) {
                SpiderCard card = cards.get(j);


                SpiderCard prevCard = cards.get(j - 1);


                if (!card.isMove() || card.isDrawBack()) continue;
                if(!prevCard.isDrawBack() ) {
                    if(j > 2) {
                        if(!cards.get(j-2).isDrawBack()) continue;
                    }
                    HelpShow tmpHints = getOneHints(prevCard, i);
                    if(tmpHints == null) continue;
                }

                HelpShow oneHints = getOneHints(card, i);
                if (oneHints != null) {
                    hints.push(oneHints);
                }
            }
        }

        if(hints.size() == 0) {
            HelpShow helpShow = new HelpShow(0, 0, 0);
            helpShow.setJustDeckHighlight(true);
            hints.push(helpShow);
        }
    }

    private HelpShow getOneHints(SpiderCard card, int index) {
        for (int k = 0; k < decks.size() - 5; k++) {
            if (index == k) continue;

            if (card.tryMoveToPosition(k, true, false)) {
                return new HelpShow(card.getDeckNum(), k, card.getPosInStack());

            }
        }
        return null;
    }

    @Override
    public void restart() {

        isStart = false;
        decks.clear();
        startDeck.clear();
        clear();
    }

    @Override
    public void cancelStep() {
        if (steps.size() > 0 && isCardActionsClear()) {
            final StepBackSpider backSpider = steps.pop();
            TopScoreView topScoreView = getRoot().getTransitObject(Menu.class).getTopScoreView();
            if (!backSpider.noDecrementStep) {
                topScoreView.decrementStep();
            }
            topScoreView.addScore(-backSpider.minusScore);


            if (backSpider.fromHouse) {

                ArrayList<SpiderCard> toDeck = decks.get(backSpider.toStack);

                toDeck.get(toDeck.size() - 1).setMove(backSpider.moveLast);
                toDeck.get(toDeck.size() - 1).setDrawBack(backSpider.drawBackLast);

                Card houseCard = houseCards.get(houseCards.size() - 1);
                for (int i = 13; i >= 1; i--) {
                    SpiderCard card = new SpiderCard(houseCardPos.x, houseCardPos.y, houseCard.getCardType(), i, getRoot());
                    card.posStack(toDeck.size());
                    card.setDeckNum(backSpider.toStack);
                    card.setMove(true);


                    SpiderCard lastCard = toDeck.get(toDeck.size() - 1);
                    float lastY = lastCard.getStartPos().y;
                    toDeck.add(card);
                    if (lastCard.getNumberCard() != -1) {
                        if (lastCard.isDrawBack()) {
                            lastY -= SPACE_BETWEEN_TWO_CARDS;
                        } else {
                            lastY -= SPACE_BETWEEN_TWO_OPEN_CARDS;
                        }
                    }
                    card.getStartPos().set(lastCard.getStartPos().x, lastY);
                    addActor(card);
                    card.moveToStartPos(-1, null);
                }


                houseCardPos.x -= STEP_HOUSE_CARD;

                addAction(Actions.forever(Actions.sequence(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        if (isCardActionsClear()) {
                            getActions().clear();
                            cancelStep();
                            updateMoveCards(backSpider.toStack);
                        }
                    }
                }), Actions.delay(0.2f))));

                if (houseCards.size() > 0) {

                    houseCard.remove();
                    houseCards.remove(houseCards.size() - 1);

                }


                return;
            }

            if (backSpider.addNewLine) {
                for (int i = 0; i < decks.size() - 5; i++) {
                    ArrayList<SpiderCard> deck = decks.get(i);
                    int lastIndex = deck.size() - 1;
                    final SpiderCard lastCard = deck.get(lastIndex);


                    lastCard.getStartPos().set((P.WIDTH + 200) - newCards.size() * 30, 100);

                    deck.remove(lastIndex);
                    int indexStack = (4 - backSpider.toStack) + 10;
                    decks.get(indexStack).add(0, lastCard);
                    if (deck.size() > 1) {
                        SpiderCard spiderCard = deck.get(deck.size() - 1);
                        if (!spiderCard.isDrawBack()) spiderCard.setMove(true);
                    }

                    lastCard.moveToStartPos(-1, new Runnable() {
                        @Override
                        public void run() {
                            lastCard.remove();

                        }
                    });
                    updateMoveCards(i);
                }

                final AddNewCard newCard = new AddNewCard((P.WIDTH + 200) - newCards.size() * 30, 100, getRoot());

                newCard.posStack(newCards.size());
                newCards.add(newCard);
                addActor(newCard);

                printStack();

                return;
            }


            ArrayList<SpiderCard> fromStack = decks.get(backSpider.fromStack);
            ArrayList<SpiderCard> toStack = decks.get(backSpider.toStack);


            SpiderCard toCard = toStack.get(toStack.size() - 1);
            toCard.setMove(false);

            SpiderCard card = fromStack.get(backSpider.fromPos);

            toCard.setMove(backSpider.moveLast);
            toCard.setDrawBack(backSpider.drawBackLast);

            card.tryMoveToPosition(backSpider.toStack, false, true);

        }
    }

    @Override
    public void showHelp() {
        if (hints.size() > 0 && isCardActionsClear()) {
            HelpShow helpShow = hints.pop();

            System.out.println(helpShow);


            if (helpShow.isJustDeckHighlight()) {

                final AddNewCard addNewCard = newCards.get(newCards.size() - 1);
                addNewCard.getShadow().setScale(1.1f);
                addNewCard.getShadow().setColor(Color.YELLOW);

                addNewCard.addAction(Actions.sequence(Actions.delay(0.3f),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        addNewCard.getShadow().setScale(1f);
                        addNewCard.getShadow().setColor(Color.BLACK);
                    }
                })));


                if (hints.size() == 0) {
                    makeHelp();
                }
                return;
            }


            ArrayList<SpiderCard> fromStack = decks.get(helpShow.getFromStack());
            ArrayList<SpiderCard> toStack = decks.get(helpShow.getToStack());
            SpiderCard lastToCard = toStack.get(toStack.size() - 1);

            float offY = 0;

            if (lastToCard.getNumberCard() != -1) {
                offY = SPACE_BETWEEN_TWO_OPEN_CARDS;
            }
            for (int i = helpShow.getFromPos(); i < fromStack.size(); i++) {
                final SpiderCard card = fromStack.get(i);

                card.setDrawShadow(true);
                card.getShadow().setScale(1.1f);
                card.getShadow().setColor(Color.YELLOW);

                MoveToAction moveToAction = Actions.moveTo(lastToCard.getX(), lastToCard.getY() - offY, 0.4f);
                card.setZIndex(9999);
                offY += SPACE_BETWEEN_TWO_OPEN_CARDS;
                card.addAction(Actions.sequence(moveToAction, Actions.delay(0.15f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        card.moveToStartPos(-1, new Runnable() {
                            @Override
                            public void run() {
                                card.getShadow().setScale(1);
                                card.getShadow().setColor(Color.BLACK);
                            }
                        });
                    }
                })));
            }

            if (hints.size() == 0) {
                System.out.println("end helps");
                makeHelp();
            }
        }
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        if (stage != null) {
            stage.getViewport().setWorldWidth(P.WIDTH + 400);
            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        }
    }

    public LinkedList<StepBackSpider> getSteps() {
        return steps;
    }

    public ArrayList<ArrayList<SpiderCard>> getDecks() {
        return decks;
    }

    public ArrayList<AddNewCard> getNewCards() {
        return newCards;
    }
}
