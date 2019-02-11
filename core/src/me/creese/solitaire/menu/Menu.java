package me.creese.solitaire.menu;

import com.badlogic.gdx.scenes.scene2d.Group;

import me.creese.util.display.Display;

public class Menu extends Group {

    private final TopScoreView topScoreView;
    private final BottomMenu bottomMenu;
    public Menu(Display root) {
        topScoreView = new TopScoreView(root);

        bottomMenu = new BottomMenu(root);
    }

    public TopScoreView getTopScoreView() {
        return topScoreView;
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent != null) {
            addActor(bottomMenu);
            addActor(topScoreView);
        }
    }
}
