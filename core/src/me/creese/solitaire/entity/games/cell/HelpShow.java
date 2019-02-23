package me.creese.solitaire.entity.games.cell;

public class HelpShow {
    private int fromStack;
    private int toStack;
    private int fromPos;

    private boolean justDeckHighlight;

    public HelpShow(int fromStack, int toStack, int fromPos) {
        this.fromStack = fromStack;
        this.toStack = toStack;
        this.fromPos = fromPos;
    }

    public int getFromStack() {
        return fromStack;
    }

    public int getToStack() {
        return toStack;
    }

    public int getFromPos() {
        return fromPos;
    }

    public boolean isJustDeckHighlight() {
        return justDeckHighlight;
    }

    public void setJustDeckHighlight(boolean justDeckHighlight) {
        this.justDeckHighlight = justDeckHighlight;
    }

    @Override
    public String toString() {
        return "HelpShow{" + "fromStack=" + fromStack + ", toStack=" + toStack + ", fromPos=" + fromPos + '}';
    }
}
