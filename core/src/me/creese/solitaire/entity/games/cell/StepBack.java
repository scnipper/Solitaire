package me.creese.solitaire.entity.games.cell;

public class StepBack {
    public final int fromStack;
    public final int toStack;
    public final int fromPos;
    public final int offsetX;

    public int beginPosBackToDeck = -1;
    public int countBackToDeck = 0;

    public int beginIndexOffset = -1;
    public int countOffsets = 0;

    public int toPosAdd = -1;
    public boolean forwardDeck;

    public int minusScore = 0;


    public StepBack(int fromStack, int toStack, int fromPos, int offsetX) {
        this.fromStack = fromStack;
        this.toStack = toStack;
        this.fromPos = fromPos;
        this.offsetX = offsetX;
    }


    @Override
    public String toString() {
        return "StepBack{" + "offsetX=" + offsetX + ", beginIndexOffset=" + beginIndexOffset + ", countOffsets=" + countOffsets + '}';
    }
}
