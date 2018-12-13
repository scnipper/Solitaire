package me.creese.soliter.entity;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;


public class PackCard extends Group {

    public static final int TOTAL_CARD = 52;
    private final Random random;
    private final ArrayList<ArrayList<CardCell>> stackCard;
    private ArrayList<DeckItem> deck;


    public PackCard() {
        random = new Random();


        stackCard = new ArrayList<>();
        createDeck();

    }

    public void start() {

        for (int i = 0; i < 11; i++) {
            stackCard.add(new ArrayList<CardCell>());
            if(i < 7) {

                EmptyCard cardFirst = new EmptyCard(50 + (i * 230), 400, CardType.DIAMONDS, 1);
                cardFirst.setStackNum(i);
                cardFirst.posStack(0);
                stackCard.get(i).add(cardFirst);
                addActor(cardFirst);
                for (int j = 0; j < i + 1; j++) {

                    int indexDeck = random.nextInt(deck.size());
                    DeckItem deckItem = deck.get(indexDeck);

                    CardCell card = new CardCell(50 + (i * 230), 400 - (j * 40),
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
                PlaceCard placeCard = new PlaceCard(900 + ((i-7) * 230), 800, CardType.DIAMONDS, 1);
                placeCard.setStackNum(i);
                placeCard.posStack(0);
                stackCard.get(i).add(placeCard);
                addActor(placeCard);
            }

        }
        ArrayList<CardCell> cardCells = new ArrayList<>();
        stackCard.add(cardCells);

        addActor(new EmptyCardDeck(50, 800, CardType.DIAMONDS, 1,11));
        for (int i = 0; i < deck.size(); i++) {

            int index = random.nextInt(deck.size());
            DeckItem deckItem = deck.get(index);

            CardCell card = new CardCell(50, 800, deckItem.getType(), deckItem.getNumber());


            card.setDrawBack(true);
            card.setDeckMode(true);
            card.setDontMoveStack(true);
            card.posStack(cardCells.size());
            card.setStackNum(11);
            cardCells.add(card);
            addActor(card);
            deck.remove(index);
            i--;

        }


    }

    private void createDeck() {
        deck = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                DeckItem deckItem = new DeckItem(CardType.getForNum(j),i+1);
                deck.add(deckItem);
            }
        }
    }


    public ArrayList<ArrayList<CardCell>> getStackCard() {
        return stackCard;
    }
}
