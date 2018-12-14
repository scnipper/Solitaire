package me.creese.solitaire.entity;

public class DeckItem {
    private CardType type;
    private int number;

    public DeckItem(CardType type, int number) {
        this.type = type;
        this.number = number;
    }

    public CardType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }
}
