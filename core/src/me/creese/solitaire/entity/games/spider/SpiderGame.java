package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.util.P;

public class SpiderGame extends BaseGame {

    public static final int SPACE_BETWEEN_TWO_CARDS = 24;
    public static final int SPACE_BETWEEN_TWO_OPEN_CARDS = 67;

    private final ArrayList<ArrayList<SpiderCard>> decks;
    private final ArrayList<SpiderCard> startDeck;
    private final ArrayList<AddNewCard> newCards;

    public SpiderGame() {
        decks = new ArrayList<>();
        startDeck = new ArrayList<>();
        newCards = new ArrayList<>();
    }

    @Override
    public void start() {

        AddNewCard addNewCard = new AddNewCard(P.WIDTH+200, 100, getRoot());
        addActor(addNewCard);
        addNewCard.posStack(0);
        newCards.add(addNewCard);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 13; j++) {
                SpiderCard spiderCard = new SpiderCard(P.WIDTH+200, 100, CardType.PEAKS, j + 1, getRoot());
                startDeck.add(spiderCard);
            }
        }


        for (int i = 0; i < 10; i++) {
            ArrayList<SpiderCard> tmp = new ArrayList<>();
            EmptyCardSpider cardSpider = new EmptyCardSpider( 1650,getRoot());
            cardSpider.setX(21 + (i * (cardSpider.getWidth() + 21)));
            tmp.add(cardSpider);
            addActor(cardSpider);
            decks.add(tmp);
        }

        System.out.println("begin size start deck = "+startDeck.size());
        int addDeck = 0;
        int countCard = 0;

        ArrayList<SpiderCard> tmp = new ArrayList<>();

        decks.add(tmp);
        do {
            int index = getRandom().nextInt(startDeck.size());
            SpiderCard card = startDeck.get(index);

            if(countCard < 54) {
                int posInStack = decks.get(addDeck).size();
                card.posStack(posInStack);
                card.setDeckNum(addDeck);
                decks.get(addDeck).add(card);

                card.getStartPos().set(21+(addDeck * (card.getWidth()+21)),1650-((posInStack-1) * SPACE_BETWEEN_TWO_CARDS));
                card.setDrawBack(true);

                addDeck++;

                countCard++;
                if (addDeck == decks.size()-1) addDeck = 0;
            } else {
                tmp.add(card);

                if(tmp.size() == 10) {
                    tmp = new ArrayList<>();
                    decks.add(tmp);
                }
            }
            startDeck.remove(index);

        } while (startDeck.size() > 0);


        moveToPositionDeck(false);


        decks.remove(decks.size()-1);





        /*System.out.println("size start deck = "+startDeck.size());

        for (int i = 0; i < decks.size(); i++) {
            System.out.println("size deck "+i+" "+decks.get(i).size());
        }*/

    }

    /**
     * Добавление новых карт
     * @return
     */
    public boolean addNewLineCard(int index) {

        for (ArrayList<SpiderCard> deck : decks) {
            if(deck.size() == 1) return false;
        }

        ArrayList<SpiderCard> from = decks.get(decks.size() - index-1);
        for (int i = 0; i < 10; i++) {
            ArrayList<SpiderCard> to = decks.get(i);

            SpiderCard fromCard = from.get(from.size() - 1);
            SpiderCard toCard = to.get(to.size() - 1);

            fromCard.setDeckNum(i);
            fromCard.posStack(to.size());

            to.add(fromCard);

            fromCard.setMove(true);
            from.remove(from.size()-1);

            fromCard.getStartPos().set(toCard.getStartPos().x,toCard.getStartPos().y-SPACE_BETWEEN_TWO_OPEN_CARDS);



        }
        moveToPositionDeck(true);
        return true;
    }

    private void moveToPositionDeck(final boolean onlyLast) {


        addAction(Actions.repeat(-1,Actions.run(new Runnable() {
            int deckNum = 0;
            int cardNum = onlyLast ? decks.get(deckNum).size()-1: 1;

            SpiderCard startCard = decks.get(deckNum).get(cardNum);
            @Override
            public void run() {
                if(startCard.getActions().size == 0) {
                    addActor(startCard);
                    startCard.addAction(Actions.sequence(Actions.moveTo(startCard.getStartPos().x,startCard.getStartPos().y,0.05f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    deckNum++;


                                    if(deckNum == 10) {
                                        deckNum = 0;
                                        cardNum++;
                                    }
                                    if(onlyLast) {
                                        cardNum = decks.get(deckNum).size() - 1;
                                        if(deckNum == 0) getActions().clear();
                                    }

                                    if(cardNum == decks.get(deckNum).size() && !onlyLast) {
                                        getActions().clear();

                                        for (int i = 0; i < 10; i++) {
                                            ArrayList<SpiderCard> deck = decks.get(i);
                                            deck.get(deck.size()-1).setDrawBack(false);
                                            deck.get(deck.size()-1).setMove(true);
                                        }


                                            for (int i = 1; i < 5; i++) {
                                                AddNewCard addNewCard = new AddNewCard(P.WIDTH + 200, 100, getRoot());
                                                addNewCard.setZIndex(0);
                                                addNewCard.posStack(i);
                                                addNewCard.getStartPos().x -= i * 30;
                                                newCards.add(addNewCard);
                                                addActor(addNewCard);
                                                addNewCard.moveToStartPos(500);

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
            stage.getViewport().setWorldWidth(P.WIDTH+400);
            stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);

        }
    }


    public ArrayList<ArrayList<SpiderCard>> getDecks() {
        return decks;
    }

    public ArrayList<AddNewCard> getNewCards() {
        return newCards;
    }
}
