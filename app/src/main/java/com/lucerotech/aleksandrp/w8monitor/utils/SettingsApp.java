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
    private static final String KEY_SELECT_THEME = "KEY_SELECT_THEME";
    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_PASSWORD = "KEY_USER_PASSWORD";
    private static final String KEY_SET_SETTINGS_PROFILE = "KEY_SET_SETTINGS_PROFILE";
    private static final String KEY_LAST_SETTINGS_PROFILE = "KEY_LAST_SETTINGS_PROFILE";

    // Default values of settings
    private static final String DEF_EMPTY_STRING = "";
    private static final boolean DEF_EMPTY_BOOLEAN = false;
    private static final boolean DEF_NOT_EMPTY_BOOLEAN = true;
    private static final int DEF_INT_EMPTY = 1;



    /**
     * check theme
     *
     * @param preferences
     * @return
     */
    public static boolean isThemeDark(SharedPreferences preferences) {
        return preferences.getBoolean(KEY_SELECT_THEME, DEF_NOT_EMPTY_BOOLEAN);
    }

    /**
     * set Theme
     *
     * @param ip
     * @param preferences
     */
    public static void setThemeDark(boolean ip, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_SELECT_THEME, ip);
        editor.commit();
    }

    /**
     * check auto login
     *
     * @param preferences
     * @return
     */
    public static boolean getAutoLogin(SharedPreferences preferences) {
        return preferences.getBoolean(KEY_AUTO_LOGIN, DEF_EMPTY_BOOLEAN);
    }

    /**
     * set Auto login
     *
     * @param ip
     * @param preferences
     */
    public static void setAutoLogin(boolean ip, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_AUTO_LOGIN, ip);
        editor.commit();
    }

    /**
     * set loginName user  for auto login
     *
     * @param mS
     * @param mSharedPreferences
     */
    public static void setUserName(String mS, SharedPreferences mSharedPreferences) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_USER_NAME, mS);
        editor.commit();
    }

    /**
     * get loginName user  for auto login
     *
     * @param preferences
     * @return
     */
    public static String getUserName(SharedPreferences preferences) {
        return preferences.getString(KEY_USER_NAME, DEF_EMPTY_STRING);
    }

    /**
     * set lPassword(Name user  for auto login
     *
     * @param mS
     * @param mSharedPreferences
     */
    public static void setUserPassword(String mS, SharedPreferences mSharedPreferences) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_USER_PASSWORD, mS);
        editor.commit();
    }

    /**
     * get lPassword(Name user  for auto login
     *
     * @param preferences
     * @return
     */
    public static String getUserPassword(SharedPreferences preferences) {
        return preferences.getString(KEY_USER_PASSWORD, DEF_EMPTY_STRING);
    }

    /**
     * set flag then user set all his data in profile
     * @param mS
     * @param mSharedPreferences
     */
    public static void setSettingsStatus(boolean mS, SharedPreferences mSharedPreferences) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_SET_SETTINGS_PROFILE, mS);
        editor.commit();
    }

    /**
     * get status flag settings profile
     * @param preferences
     * @return
     */
    public static boolean getLastProfile(SharedPreferences preferences) {
        return preferences.getBoolean(KEY_SET_SETTINGS_PROFILE, DEF_EMPTY_BOOLEAN);
    }

    /**
     * set flag then user set all his data in profile
     * @param mS
     * @param mSharedPreferences
     */
    public static void setSettingsProfile(int mS, SharedPreferences mSharedPreferences) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_LAST_SETTINGS_PROFILE, mS);
        editor.commit();
    }

    /**
     * get status flag settings profile
     * @param preferences
     * @return
     */
    public static int getSettingsProfile(SharedPreferences preferences) {
        return preferences.getInt(KEY_LAST_SETTINGS_PROFILE, DEF_INT_EMPTY);
    }

}
