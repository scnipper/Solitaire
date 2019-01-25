package me.creese.solitaire.util;

import com.badlogic.gdx.assets.AssetManager;

public class P {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private static P instance;
    public AssetManager asset;

    public static P get() {
        if(instance == null) instance = new P();

        return instance;
    }

}
