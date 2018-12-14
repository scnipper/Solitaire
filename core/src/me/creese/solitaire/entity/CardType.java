package me.creese.solitaire.entity;

public enum CardType {
    CLUBS,
    DIAMONDS,
    HEARTS,
    PEAKS;

    public static CardType getForNum(int i) {
        switch (i) {
            case 0:
                return CLUBS;
            case 1:
                return DIAMONDS;
            case 2:
                return HEARTS;
            case 3:
                return PEAKS;

        }
        return null;
    }
}
