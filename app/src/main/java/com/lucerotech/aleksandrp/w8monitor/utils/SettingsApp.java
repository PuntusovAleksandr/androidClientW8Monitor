package com.lucerotech.aleksandrp.w8monitor.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.lucerotech.aleksandrp.w8monitor.App;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class SettingsApp {

    private static final String TAG = SettingsApp.class.getSimpleName();

    /**
     * Instance of SharedPreferences object
     */
    private SharedPreferences sPref;
    /**
     * Editor of SharedPreferences object
     */
    private SharedPreferences.Editor editor;

    private static SettingsApp ourInstance = new SettingsApp();

    /**
     * The constant FILE_NAME.
     */
// Settings xml file name
    public static final String FILE_NAME = "settings";

    /**
     * get instance settingsApp
     *
     * @return
     */
    public static SettingsApp getInstance() {
        return ourInstance;
    }


    /**
     * Construct the instance of the object
     */
    public SettingsApp() {
        sPref = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        editor = sPref.edit();
    }


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
     * @return
     */
    public boolean isThemeDark() {
        Log.d(TAG, "isThemeDark");
        return sPref.getBoolean(KEY_SELECT_THEME, DEF_NOT_EMPTY_BOOLEAN);
    }

    /**
     * set Theme
     *
     * @param ip
     */
    public void setThemeDark(boolean ip) {
        Log.d(TAG, "setThemeDark " + ip);
        editor.putBoolean(KEY_SELECT_THEME, ip).commit();
    }

    /**
     * check auto login
     *
     * @return
     */
    public boolean getAutoLogin() {
        Log.d(TAG, "getAutoLogin");
        return sPref.getBoolean(KEY_AUTO_LOGIN, DEF_EMPTY_BOOLEAN);
    }

    /**
     * set Auto login
     *
     * @param ip
     */
    public void setAutoLogin(boolean ip) {
        Log.d(TAG, "setAutoLogin");
        editor.putBoolean(KEY_AUTO_LOGIN, ip).commit();
    }

    /**
     * set loginName user  for auto login
     *
     * @param mS
     */
    public void setUserName(String mS) {
        Log.d(TAG, "setUserName " + mS);
        editor.putString(KEY_USER_NAME, mS).commit();
    }

    /**
     * get loginName user  for auto login
     *
     * @return
     */
    public String getUserName() {
        Log.d(TAG, "getUserName");
        return sPref.getString(KEY_USER_NAME, DEF_EMPTY_STRING);
    }

    /**
     * set lPassword(Name user  for auto login
     *
     * @param mS
     */
    public void setUserPassword(String mS) {
        Log.d(TAG, "setUserPassword " + mS);
        editor.putString(KEY_USER_PASSWORD, mS).commit();
    }

    /**
     * get lPassword(Name user  for auto login
     *
     * @return
     */
    public String getUserPassword() {
        Log.d(TAG, "getUserPassword");
        return sPref.getString(KEY_USER_PASSWORD, DEF_EMPTY_STRING);
    }

    /**
     * set flag then user set all his data in profile
     *
     * @param mS
     */
    public void setSettingsStatus(boolean mS) {
        Log.d(TAG, "setSettingsStatus " + mS);
        editor.putBoolean(KEY_SET_SETTINGS_PROFILE, mS).commit();
    }

    /**
     * get status flag settings profile
     *
     * @return
     */
    public boolean getLastProfile() {
        Log.d(TAG, "getLastProfile");
        return sPref.getBoolean(KEY_SET_SETTINGS_PROFILE, DEF_EMPTY_BOOLEAN);
    }

    /**
     * set flag then user set all his data in profile
     *
     * @param mS
     */
    public void setSettingsProfile(int mS) {
        Log.d(TAG, "setSettingsProfile " + mS);
        editor.putInt(KEY_LAST_SETTINGS_PROFILE, mS).commit();
    }

    /**
     * get status flag settings profile
     *
     * @return
     */
    public int getSettingsProfile() {
        Log.d(TAG, "getSettingsProfile");
        return sPref.getInt(KEY_LAST_SETTINGS_PROFILE, DEF_INT_EMPTY);
    }

}
