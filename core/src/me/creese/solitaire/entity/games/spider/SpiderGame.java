package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.menu.TopScoreView;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.util.P;

public class SpiderGame extends BaseGame {

    public static final int SPACE_BETWEEN_TWO_CARDS = 24;
    public static final int SPACE_BETWEEN_TWO_OPEN_CARDS = 67;

    private final ArrayList<ArrayList<SpiderCard>> decks;
    private final ArrayList<SpiderCard> startDeck;
    private final ArrayList<AddNewCard> newCards;
    private final Vector2 houseCardPos;
    private boolean isStart;

    public SpiderGame() {
        decks = new ArrayList<>();
        startDeck = new ArrayList<>();
        newCards = new ArrayList<>();
        houseCardPos = new Vector2();
    }

    @Override
    public void start() {

        if(isStart) return;

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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 13; j++) {
                SpiderCard spiderCard = new SpiderCard(P.WIDTH + 200, 100, CardType.PEAKS, j + 1, getRoot());
                startDeck.add(spiderCard);
            }
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





        /*System.out.println("size start deck = "+startDeck.size());

        for (int i = 0; i < decks.size(); i++) {
            System.out.println("size deck "+i+" "+decks.get(i).size());
        }*/

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
    }

    /**
     * Проверка на собранность колоды
     */
    public void checkWinCombination(int stackNum) {
        ArrayList<SpiderCard> cards = decks.get(stackNum);

        if (cards.size() >= 14) {

            for (int i = 1; i < cards.size(); i++) {
                SpiderCard findCard = cards.get(i);

                if (findCard.getNumberCard() == 13 && i + 12 < cards.size()) {
                    for (int j = i + 1; j < cards.size(); j++) {
                        SpiderCard placeCard = cards.get(j);
                        SpiderCard toCard = cards.get(j - 1);
                        if (!placeCard.rightConditionCard(toCard)) {
                            return;
                        }

                    }

                    cards.get(i - 1).setDrawBack(false);
                    cards.get(i - 1).setMove(true);

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
                    houseCardPos.x += 50;

                    getRoot().getGameViewForName(GameScreen.class).getMenu().getTopScoreView().addScore(100);
                    System.out.println(" win  cards");
                    return;
                }
            }
        }
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

    @Override
    public void restart() {

        isStart = false;
        decks.clear();
        startDeck.clear();
        clear();
    }

    @Override
    public void cancelStep() {

    }

    @Override
    public void showHelp() {

    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        if (stage != null) {
            stage.getViewport().setWorldWidth(P.WIDTH + 400);
            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        }
    }


    public ArrayList<ArrayList<SpiderCard>> getDecks() {
        return decks;
    }

    public ArrayList<AddNewCard> getNewCards() {
        return newCards;
    }
}
