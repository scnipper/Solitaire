package me.creese.solitaire.entity.games.cell;

public class HelpShow {
    private int fromStack;
    private int toStack;
    private int fromPos;

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

    @Override
    public String toString() {
        return "HelpShow{" + "fromStack=" + fromStack + ", toStack=" + toStack + ", fromPos=" + fromPos + '}';
    }
}
