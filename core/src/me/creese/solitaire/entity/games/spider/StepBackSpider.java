package me.creese.solitaire.entity.games.spider;

public class StepBackSpider {
    // из колоды
    public final int fromStack;
    // в колоду
    public final int toStack;
    // из позиции
    public final int fromPos;
    public boolean moveLast;
    public boolean drawBackLast;
    public boolean addNewLine;
    public boolean fromHouse;
    public boolean noDecrementStep;



    public StepBackSpider(int fromStack, int toStack, int fromPos) {
        this.fromStack = fromStack;
        this.toStack = toStack;
        this.fromPos = fromPos;
    }
}
