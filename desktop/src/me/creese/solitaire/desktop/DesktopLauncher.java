package me.creese.solitaire.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.creese.solitaire.CardsGames;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 360;
		config.height = 640;
		config.title = "Soliter";
		config.samples = 6;
		//config.useGL30 = true;
		config.useHDPI = true;
		new LwjglApplication(new CardsGames(), config);
	}
}
