package me.creese.solitaire.util;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class P {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1920;
    // colors
/*    public static final Color BACKGROUND_COLOR          = new Color(0x349422ff);
    public static final Color SUB_BACKGROUND_COLOR      = new Color(0x37B420ff);*/
    public static final Color BLACK_TRANSPARENT_COLOR   = new Color(0x00000017);
    public static final Color TOP_MENU_COLOR            = new Color(0x2E4631ff);
    public static final Color YELLOW_COLOR              = new Color(0xFFF834ff);
    public static final Color SUB_YELLOW_COLOR          = new Color(0xA7A222ff);
    public static final Color BUY_COIN_PRICE_COLOR      = new Color(0x2C771Eff);

    public static final int COUNT_COIN =100500;

    private static P instance;
    public AssetManager asset;
    public Preferences pref;
    public Texture whiteDot;

    public static P get() {
        if(instance == null) instance = new P();

        return instance;
    }

}
