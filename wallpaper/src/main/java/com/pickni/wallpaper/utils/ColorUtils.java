package com.pickni.wallpaper.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * date        : 2020/7/21 10:23
 * author      : JerRay
 * email       : chenjietop@gmail.com
 * description :
 */
public class ColorUtils {

    private static List<Drawable> gColorDrawableList;
    private static List<Drawable> gTextDrawableList;
    private static List<Drawable> gGradientDrawableList;

    public static final String[] textColorList = {
            "#FFFFFF", "#1B1B1B", "#919290", "#CA1E22",
            "#FF5A60", "#FF458E", "#CA5116", "#FB6C19", "#FEAEAD",
            "#FFBA02", "#FFE431", "#FFFE9B", "#5DEBCD", "#37CEE1",
            "#2B5BAF", "#2256FF", "#8A9CFF", "#8327EA", "#E640FF",
            "#2F3290", "#8A9CFF", "#98D2F8", "#8841FE", "#A32EA0",
            "#73321E", "#253F00", "#26C652"
    };

    public static final String[] colorList = {
            "#FF1B1B1B", "#FF919290", "#FFFFFFFF", "#FF9A1E40", "#FFB80E57",
            "#FFD73447", "#FFFF9596", "#FFFFBCBD", "#FFF1D1D1", "#FFCA5116",
            "#FFF17809", "#FFFAAD10", "#FFFFD31E", "#FFFFE277", "#FFFFEAC5",
            "#FFA5146B", "#FFF54292", "#FFFF78AF", "#FFFFA1D2", "#FFFFDCF8",
            "#FFFDDEDF", "#FF2D003F", "#FF614603", "#FF512B58", "#FF8A2C9C",
            "#FF8567AA", "#FFCD66FF", "#FFBE7ADF", "#FFFBD0FC", "#FFFFE3FF",
            "#FF162447", "#FF161982", "#FF0F2E89", "#FF434CCD", "#FF2861A8",
            "#FF7F97D7", "#FF81ADEA", "#FF98D2F8", "#FFDEF4F0", "#FF253F00",
            "#FF3E6600", "#FF5B8C00", "#FF26C652", "#FFA0D814", "#FFA8FF00",
            "#FFBAE637", "#FFBAE637", "#FF55D0B4", "#FF75B79F", "#FFA7EAAF",
            "#FF886800", "#FFAC8A01", "#FFB3A777", "#FFD5D5A3", "#FFEFF0E8"
    };

    public static final String[][] gradientList = {
            {"0", "#FFFF9A9E", "#FFFAD0C4"}, {"135", "#FFFFD1FF", "#FFFAD0C4"},
            {"0", "#FFFCB69F", "#FFFFECD2"}, {"180", "#FFFECFEF", "#FFFF9A9E"},
            {"180", "#FFF6D365", "#FFFDA085"}, {"0", "#FFFBC2EB", "#FFA6C1EE"},
            {"180", "#FFFDCBF1", "#FFE6DEE9"}, {"180", "#FFA1C4FD", "#FFC9FFFC"},
            {"180", "#FFCCF97F", "#FF9FE99C"}, {"180", "#FF87F5BB", "#FF8CDBE7"},
            {"180", "#FFF1F5F8", "#FFD6E0E6"}, {"180", "#FFA0C5FD", "#FFD1C4FC"},
            {"180", "#FFF28BE7", "#FFF46182"}, {"270", "#FF37F8D4", "#FF43EA7D"},
            {"180", "#FFFDF7D7", "#FFD39CC3"}, {"180", "#FFECF0F7", "#FFCCD6E6"},
            {"315", "#FF7455AF", "#FF6A74DD", "#FF6976E0"}, {"225", "#FFD3FCFD", "#FFFDDC94"},
            {"180", "#FFB1F2D1", "#FF9A95E2"}, {"235", "#FFFED58B", "#FFFFDEAA", "#FFF8879C"},
            {"135", "#FFED8EE0", "#FFB286F1", "#FF7935E2"}, {"225", "#FFFFB199", "#FFFF0F45"},
            {"180", "#FFD7E3E5", "#FFA4B4D5"}, {"180", "#FFFF7EB3", "#FFBE75FF"},
            {"180", "#FFF9D123", "#FFF84204"}, {"315", "#FFFE5394", "#FFF6CC63"},
            {"180", "#FF6A317D", "#FF162447"}
    };

    public static String[] getColorList() {
        return colorList;
    }

    public static List<Drawable> getColorDrawableList() {
        if (gColorDrawableList == null) {
            gColorDrawableList = new ArrayList<>();
            gColorDrawableList.clear();
            for (String colorStr : colorList) {
                int color = Color.parseColor(colorStr);
                ColorDrawable colorDrawable = new ColorDrawable(color);
                gColorDrawableList.add(colorDrawable);
            }
        }
        return gColorDrawableList;
    }

    public static List<Drawable> getTextColorDrawableList() {
        if (gTextDrawableList == null) {
            gTextDrawableList = new ArrayList<>();
            gTextDrawableList.clear();
            for (String colorStr : textColorList) {
                int color = Color.parseColor(colorStr);
                ColorDrawable colorDrawable = new ColorDrawable(color);
                gTextDrawableList.add(colorDrawable);
            }
        }
        return gTextDrawableList;
    }

    public static int getRandColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256));
    }

    public static List<Drawable> getGradientDrawableList() {
        if (gGradientDrawableList == null) {
            gGradientDrawableList = new ArrayList<>();
            gGradientDrawableList.clear();
            for (String[] colors : gradientList) {
                int[] colorList = new int[colors.length - 1];
                int angle = Integer.parseInt(colors[0]);
                for (int i = 1; i < colors.length; i++) {
                    colorList[i - 1] = Color.parseColor(colors[i]);
                }
                GradientDrawable.Orientation orientation;
                switch (angle) {
                    case 45:
                        orientation = GradientDrawable.Orientation.BL_TR;
                        break;
                    case 90:
                        orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                        break;
                    case 135:
                        orientation = GradientDrawable.Orientation.TL_BR;
                        break;
                    case 180:
                        orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                        break;
                    case 225:
                        orientation = GradientDrawable.Orientation.TR_BL;
                        break;
                    case 270:
                        orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                        break;
                    case 315:
                        orientation = GradientDrawable.Orientation.BR_TL;
                        break;
                    default:
                        orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                        break;

                }
                GradientDrawable drawable = new GradientDrawable(orientation, colorList);
                gGradientDrawableList.add(drawable);
            }
        }
        return gGradientDrawableList;
    }

    public static int getGray(int color) {
        return (int) (Color.red(color) * 0.3F + Color.green(color) * 0.59F + Color.blue(color) * 0.11F);
    }

    /**
     * 判断颜色是否偏黑色
     *
     * @param color 颜色
     * @return
     */
    public static boolean isBlackColor(int color) {
        int grey = toGrey(color);
        return grey < 50;
    }

    /**
     * 颜色转换成灰度值
     *
     * @param rgb 颜色
     * @return　灰度值
     */
    public static int toGrey(int rgb) {
        int blue = rgb & 0x000000FF;
        int green = (rgb & 0x0000FF00) >> 8;
        int red = (rgb & 0x00FF0000) >> 16;
        return (red * 38 + green * 75 + blue * 15) >> 7;
    }

    public static boolean isWhiteColor(int color) {
        int grey = toGrey(color);
        return grey > 200;
    }
}
