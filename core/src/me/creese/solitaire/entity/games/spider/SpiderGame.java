package me.creese.solitaire.entity.games.spider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.util.P;

public class SpiderGame extends BaseGame {

    private static final int SPACE_BETWEEN_TWO_CARDS = 24;
    private static final int SPACE_BETWEEN_TWO_OPEN_CARDS = 67;

    private final ArrayList<ArrayList<SpiderCard>> decks;
    private final ArrayList<SpiderCard> startDeck;

    public SpiderGame() {
        decks = new ArrayList<>();
        startDeck = new ArrayList<>();
    }

    @Override
    public void start() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 13; j++) {
                SpiderCard spiderCard = new SpiderCard(P.WIDTH/2, 300, CardType.PEAKS, j + 1, getRoot());
                startDeck.add(spiderCard);
            }
        }


        for (int i = 0; i < 10; i++) {
            decks.add(new ArrayList<SpiderCard>());
        }

        System.out.println("begin size start deck = "+startDeck.size());
        int addDeck = 0;
        int countCard = 0;

        ArrayList<SpiderCard> tmp = new ArrayList<>();

        decks.add(tmp);
        do {
            int index = getRandom().nextInt(startDeck.size());
            SpiderCard card = startDeck.get(index);

            addActor(card);
            if(countCard < 54) {
                int posInStack = decks.get(addDeck).size();
                card.posStack(posInStack);
                decks.get(addDeck).add(card);

                card.getStartPos().set(21+(addDeck * (card.getWidth()+21)),1650-(posInStack * SPACE_BETWEEN_TWO_CARDS));
                card.setDrawBack(true);

                //card.moveToStartPos();
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


        moveToPositionDeck();


        decks.remove(decks.size()-1);



        /*System.out.println("size start deck = "+startDeck.size());

        for (int i = 0; i < decks.size(); i++) {
            System.out.println("size deck "+i+" "+decks.get(i).size());
        }*/

    }

    private void moveToPositionDeck() {


        addAction(Actions.repeat(-1,Actions.run(new Runnable() {
            int deckNum = 0;
            int cardNum = 0;
            SpiderCard startCard = decks.get(deckNum).get(cardNum);
            @Override
            public void run() {
                if(startCard.getActions().size == 0) {
                    startCard.addAction(Actions.sequence(Actions.moveTo(startCard.getStartPos().x,startCard.getStartPos().y,0.05f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    deckNum++;
                                    if(deckNum == 10) {
                                        deckNum = 0;
                                        cardNum++;
                                    }
                                    if(cardNum == decks.get(deckNum).size()) {
                                        getActions().clear();

                                        for (ArrayList<SpiderCard> deck : decks) {
                                            deck.get(deck.size()-1).setDrawBack(false);
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
}
