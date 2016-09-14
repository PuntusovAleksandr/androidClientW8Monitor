package com.lucerotech.aleksandrp.w8monitor.utils;

import android.content.SharedPreferences;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class SettingsApp {

    /**
     * The constant FILE_NAME.
     */
// Settings xml file name
    public static final String FILE_NAME = "settings";

    // Keys for opening settings from xml file
    private static final String KEY_USER = "key_user";

    // Default values of settings
    private static final String DEF_EMPTY_STRING = "";


    public static String getParam(SharedPreferences preferences) {
        return preferences.getString(KEY_USER, DEF_EMPTY_STRING);
    }


    public static void setParam(String ip, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER, ip);
        editor.commit();
    }

}
