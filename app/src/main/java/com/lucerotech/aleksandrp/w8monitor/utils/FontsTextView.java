package com.lucerotech.aleksandrp.w8monitor.utils;

import android.graphics.Typeface;

import static com.lucerotech.aleksandrp.w8monitor.App.getContext;

/**
 * Created by AleksandrP on 16.10.2016.
 */

public class FontsTextView {

    public static Typeface getFontRoboLight() {
        return Typeface.createFromAsset(getContext().getAssets(), "fons/Lato-Light.ttf");
    }

    public static Typeface getFontOpenSansLight() {
        return Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf");
    }


    public static Typeface getFontRobotoLight() {
        return Typeface.createFromAsset(getContext().getAssets(), "roboto_light.ttf");
    }
}
