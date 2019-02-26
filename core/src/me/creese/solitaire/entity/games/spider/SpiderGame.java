package me.creese.solitaire.entity.games.spider;

import java.util.ArrayList;

import me.creese.solitaire.entity.CardType;
import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.util.P;

public class SpiderGame extends BaseGame {

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
                SpiderCard spiderCard = new SpiderCard(P.WIDTH - 500, 100, CardType.PEAKS, j + 1, getRoot());
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

            if(countCard < 54) {
                decks.get(addDeck).add(card);

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
        decks.remove(decks.size()-1);

        System.out.println("size start deck = "+startDeck.size());

        for (int i = 0; i < decks.size(); i++) {
            System.out.println("size deck "+i+" "+decks.get(i).size());
        }

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
}
