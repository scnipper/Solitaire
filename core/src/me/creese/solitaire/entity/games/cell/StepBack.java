package me.creese.solitaire.entity.games.cell;

public class StepBack {
    // из колоды
    public final int fromStack;
    // в колоду
    public final int toStack;
    // из позиции
    public final int fromPos;
    public final int offsetX;

    public int beginPosBackToDeck = -1;
    public int countBackToDeck = 0;

    public int beginIndexOffset = -1;
    public int countOffsets = 0;

    public int toPosAdd = -1;
    // если колода была перемешана
    public boolean forwardDeck;

    public int minusScore = 0;
    // перевернута ли предыдущая карта
    public boolean drawBackPrevCards;


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
