package me.creese.solitaire.screens;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.creese.solitaire.menu.CoinCounter;
import me.creese.solitaire.menu.Menu;
import me.creese.solitaire.menu.Theme;
import me.creese.solitaire.menu.ThemeItemBtn;
import me.creese.solitaire.menu.buttons.StdTransparentBtn;
import me.creese.solitaire.util.P;
import me.creese.util.display.Display;
import me.creese.util.display.GameView;

public class ThemesScreen extends GameView {
    private Menu menu;
    private CoinCounter coinCounter;

    public ThemesScreen(Display root) {
        super(new FitViewport(P.WIDTH,P.HEIGHT), root, new PolygonSpriteBatch());




        for (int i = 0; i < Theme.themes.size(); i++) {
            ThemeItemBtn themeItem = new ThemeItemBtn(root,Theme.themes.get(i).getPrice(),i);
            if(i == Theme.CURR_THEME) themeItem.setColor(P.SUB_YELLOW_COLOR);
            int triple = i / 3;
            themeItem.setPosition(76+((i-triple*3)*(themeItem.getWidth()+32)),1135-(triple *(themeItem.getHeight()+32)));
            addActor(themeItem);
        }

        StdTransparentBtn stdTransparentBtn = new StdTransparentBtn(root);
        stdTransparentBtn.setY(240);
        stdTransparentBtn.setMode(StdTransparentBtn.Mode.EXIT);
        addActor(stdTransparentBtn);

    }

    @Override
    public void addRoot(Display display) {
        super.addRoot(display);
        if (display != null) {
            menu = display.getTransitObject(Menu.class);
            addActor(menu);
            menu.getTopScoreView().setTimeStart(false);
            menu.getBottomMenu().setVisible(false);
            menu.getTopScoreView().setCustomHeadText("Темы");

            coinCounter = display.getTransitObject(CoinCounter.class);
            coinCounter.moveBy(0,226);
            addActor(coinCounter);
        } else {
            menu.getTopScoreView().setTimeStart(true);
            menu.getBottomMenu().setVisible(true);
            coinCounter.moveBy(0,-226);
            menu.getTopScoreView().setCustomHeadText(null);
        }
    }
}
