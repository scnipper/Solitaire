package me.creese.solitaire.menu;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class Theme {
    public static int CURR_THEME = 4;

    public static ArrayList<ThemeItem> themes;

    public static void init() {
        themes = new ArrayList<>();

        themes.add(new ThemeItem(new Color(0x349422ff),new Color(0x37B420ff),0));
        themes.add(new ThemeItem(new Color(0x2A9AE5ff),new Color(0x35ACFCff),0));
        themes.add(new ThemeItem(new Color(0xDE2DF1ff),new Color(0xF061FFff),0));
        themes.add(new ThemeItem(new Color(0xA0D919ff),new Color(0xB3F121ff),0));
        themes.add(new ThemeItem(new Color(0x9320DFff),new Color(0xB03FFBff),0));
        themes.add(new ThemeItem(new Color(0xF16937ff),new Color(0xFF8356ff),0));
        themes.add(new ThemeItem(new Color(0xE29A1Fff),new Color(0xF8B138ff),0));
        themes.add(new ThemeItem(new Color(0x5624E8ff),new Color(0x7D51FFff),0));

    }

    public static ThemeItem getCurrentTheme() {
        return themes.get(CURR_THEME);
    }


    public static class ThemeItem {

        private final Color mainColor;
        private final Color subColor;
        private final int price;

        public ThemeItem(Color mainColor, Color subColor,int price) {
            this.price = price;
            this.mainColor = mainColor;
            this.subColor = subColor;
        }

        public Color getMainColor() {
            return mainColor;
        }

        public Color getSubColor() {
            return subColor;
        }

        public int getPrice() {
            return price;
        }
    }
}
