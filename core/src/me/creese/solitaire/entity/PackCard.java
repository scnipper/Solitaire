package me.creese.solitaire.entity;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;


public class PackCard extends Group {

    public static final int TOTAL_CARD = 52;
    private final Random random;
    private final ArrayList<ArrayList<me.creese.solitaire.entity.CardCell>> stackCard;
    private ArrayList<me.creese.solitaire.entity.DeckItem> deck;


    public PackCard() {
        random = new Random();


        stackCard = new ArrayList<>();
        createDeck();

    }

    public void start() {

        for (int i = 0; i < 11; i++) {
            stackCard.add(new ArrayList<me.creese.solitaire.entity.CardCell>());
            if(i < 7) {

                me.creese.solitaire.entity.EmptyCard cardFirst = new me.creese.solitaire.entity.EmptyCard(50 + (i * 230), 400, me.creese.solitaire.entity.CardType.DIAMONDS, 1);
                cardFirst.setStackNum(i);
                cardFirst.posStack(0);
                stackCard.get(i).add(cardFirst);
                addActor(cardFirst);
                for (int j = 0; j < i + 1; j++) {

                    int indexDeck = random.nextInt(deck.size());
                    me.creese.solitaire.entity.DeckItem deckItem = deck.get(indexDeck);

                    me.creese.solitaire.entity.CardCell card = new me.creese.solitaire.entity.CardCell(50 + (i * 230), 400 - (j * 40),
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
                PlaceCard placeCard = new PlaceCard(900 + ((i-7) * 230), 800, me.creese.solitaire.entity.CardType.DIAMONDS, 1);
                placeCard.setStackNum(i);
                placeCard.posStack(0);
                stackCard.get(i).add(placeCard);
                addActor(placeCard);
            }

        }
        ArrayList<me.creese.solitaire.entity.CardCell> cardCells = new ArrayList<>();
        stackCard.add(cardCells);

        addActor(new me.creese.solitaire.entity.EmptyCardDeck(50, 800, me.creese.solitaire.entity.CardType.DIAMONDS, 1,11));
        for (int i = 0; i < deck.size(); i++) {

            int index = random.nextInt(deck.size());
            me.creese.solitaire.entity.DeckItem deckItem = deck.get(index);

            me.creese.solitaire.entity.CardCell card = new me.creese.solitaire.entity.CardCell(50, 800, deckItem.getType(), deckItem.getNumber());


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
                me.creese.solitaire.entity.DeckItem deckItem = new me.creese.solitaire.entity.DeckItem(me.creese.solitaire.entity.CardType.getForNum(j),i+1);
                deck.add(deckItem);
            }
        }
    }


    public ArrayList<ArrayList<me.creese.solitaire.entity.CardCell>> getStackCard() {
        return stackCard;
    }
}
