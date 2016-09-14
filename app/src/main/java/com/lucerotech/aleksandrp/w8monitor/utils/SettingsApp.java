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
    private static final String KEY_AUTO_LOGIN = "key_auto_login";

    // Default values of settings
    private static final String DEF_EMPTY_STRING = "";
    private static final boolean DEF_EMPTY_BOOLEAN = false;



    public static boolean getAutoLogin(SharedPreferences preferences) {
        return preferences.getBoolean(KEY_AUTO_LOGIN, DEF_EMPTY_BOOLEAN);
    }


    public static void setAutoLogin(boolean ip, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_AUTO_LOGIN, ip);
        editor.commit();
    }

}
