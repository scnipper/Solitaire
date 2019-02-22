package me.creese.solitaire.menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import me.creese.solitaire.entity.impl.BaseGame;
import me.creese.solitaire.screens.GameScreen;
import me.creese.solitaire.screens.Loading;
import me.creese.solitaire.screens.SettingsScreen;
import me.creese.solitaire.util.FTextures;
import me.creese.solitaire.util.FontUtil;
import me.creese.solitaire.util.P;
import me.creese.solitaire.util.TexturePrepare;
import me.creese.util.display.Display;

public class StdTransparentBtn extends Actor {

    private final BitmapFont font;
    private final Sprite back;
    private Mode mode;
    private String text;

    public enum Mode {
        RULE,RESTART,EXIT
    }

    public StdTransparentBtn(final Display root) {
        font = P.get().asset.get(Loading.FONT_ROBOTO_BOLD, BitmapFont.class);
        TexturePrepare prepare = root.getTransitObject(TexturePrepare.class);

        back = prepare.getByName(FTextures.DEF_BUTTON_SETTINGS);
        back.setColor(P.BLACK_TRANSPARENT_COLOR);
        setX(P.WIDTH/2-back.getWidth()/2);
        setWidth(back.getWidth());
        setHeight(back.getHeight());
        addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (mode) {
                    case RESTART:
                        BaseGame baseGame = root.getGameViewForName(GameScreen.class).getBaseGame();

                        baseGame.restart();
                        root.showGameView(GameScreen.class);
                        break;
                    case RULE:

                        break;

                    case EXIT:
                        root.showGameView(SettingsScreen.class);
                        break;
                }
            }
        });

    }

    public void setMode(Mode mode) {
        this.mode = mode;

        switch (mode) {
            case EXIT:
                text = "Выйти назад";
                setY(104);
                break;
            case RULE:
                text = "Правила игры";
                setY(128);
                break;
            case RESTART:
                setY(298);
                text = "Начать игру заново";
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        back.setPosition(getX(),getY());
        back.draw(batch);

        FontUtil.drawText(batch,font,text,getX(),getY(),0.65f, Color.WHITE,getWidth(), Align.center,false,getHeight());
    }
}
