package me.creese.solitaire.util;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;

public class P {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1920;
    // colors
    public static final Color BACKGROUND_COLOR = new Color(0x349422ff);
    public static final Color BLACK_TRANSPARENT_COLOR = new Color(0x00000017);
    public static final Color TOP_MENU_COLOR = new Color(0x2E4631ff);

    private static P instance;
    public AssetManager asset;
    public Preferences pref;

    public static P get() {
        if(instance == null) instance = new P();

        return instance;
    }

}
