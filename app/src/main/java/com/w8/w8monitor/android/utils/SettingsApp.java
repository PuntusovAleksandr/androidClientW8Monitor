package com.w8.w8monitor.android.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.w8.w8monitor.android.App;

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
    private static final String KEY_FIRST_START = "KEY_FIRST_START";
    private static final String KEY_AUTO_LOGIN = "key_auto_login";
    private static final String KEY_SELECT_THEME = "KEY_SELECT_THEME";
    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_NAME_FB = "KEY_USER_NAME_FB";
    private static final String KEY_USER_PASSWORD = "KEY_USER_PASSWORD";
    private static final String KEY_SET_SETTINGS_PROFILE = "KEY_SET_SETTINGS_PROFILE";
    private static final String KEY_LAST_SETTINGS_PROFILE = "KEY_LAST_SETTINGS_PROFILE";
    private static final String KEY_LANGUAGE = "KEY_LANGUAGE";
    private static final String KEY_METRIC = "KEY_METRIC";
    private static final String KEY_PROFILE_BLE_ID = "KEY_PROFILE_BLE_ID";
    private static final String KEY_ICON_IN_HOME = "KEY_ICON_IN_HOME";
    private static final String KEY_USER_SERVER = "KEY_USER_SERVER";
    private static final String KEY_USER_LOGIN_FB = "KEY_USER_LOGIN_FB";
    private static final String KEY_SHOW_WEIGHT_GOOGLE_FIT = "KEY_SHOW_WEIGHT_GOOGLE_FIT";
    private static final String KEY_SAVE_WEIGHT = "KEY_SAVE_WEIGHT";
    private static final String KEY_TARGET_WEIGHT = "KEY_TARGET_WEIGHT";
    private static final String KEY_GENDER_FB = "KEY_GENDER_FB";
    private static final String KEY_DATE_BIRTHDAY = "KEY_DATE_BIRTHDAY";
    private static final String KEY_AUTH_GOOGLE_FIT = "KEY_AUTH_GOOGLE_FIT";
    private static final String KEY_SHOW_LOGIN_TUTORIAL = "KEY_SHOW_LOGIN_TUTORIAL";
    private static final String KEY_SHOW_MAIN_TUTORIAL = "KEY_SHOW_MAIN_TUTORIAL";
    private static final String KEY_LOGIN_FROM_LOGOUT = "KEY_LOGIN_FROM_LOGOUT";

    // Default values of settings
    private static final String DEF_EMPTY_STRING = "";
    private static final String DEF_LANGUAGE = "en";
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
        return sPref.getBoolean(KEY_SELECT_THEME, DEF_EMPTY_BOOLEAN);
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
     * CHECK IS FIRST START
     *
     * @return
     */
    public boolean isFirstStart() {
        Log.d(TAG, "isFirstStart");
        return sPref.getBoolean(KEY_FIRST_START, DEF_NOT_EMPTY_BOOLEAN);
    }

    /**
     * SET FOR CHECK IS FIRST START
     *
     * @param ip
     */
    public void setFirstStart(boolean ip) {
        Log.d(TAG, "setFirstStart");
        editor.putBoolean(KEY_FIRST_START, ip).commit();
    }

    /**
     * check auto login
     *
     * @return
     */
    public boolean getAutoLogin() {
        Log.d(TAG, "getAutoLogin");
        return sPref.getBoolean(KEY_AUTO_LOGIN, DEF_NOT_EMPTY_BOOLEAN);
    }

    /**
     * set Auto login
     *
     * @param ip
     */
    public void setAutoLogin(boolean ip) {
//        ip = true;      // TODO: 05.12.2016  Убрать кнопку KEEP ME LOGGED IN, считать что она всегда включена https://3.basecamp.com/3110661/buckets/1532583/todos/310563548#__recording_310604978
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
     * set loginName FB user  for auto login
     *
     * @param mS
     */
    public void setUserNameFB(String mS) {
        Log.d(TAG, "setUserNameFB " + mS);
        editor.putString(KEY_USER_NAME_FB, mS).commit();
    }

    /**
     * get loginName FB user  for auto login
     *
     * @return
     */
    public String getUserNameFB() {
        Log.d(TAG, "getUserNameFB");
        return sPref.getString(KEY_USER_NAME_FB, DEF_EMPTY_STRING);
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
    public boolean getSettingsStatus() {
        Log.d(TAG, "getSettingsStatus");
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

    /**
     * set languages app
     *
     * @param mS
     */
    public void setLanguages(String mS) {
        Log.d(TAG, "setLanguages " + mS);
        editor.putString(KEY_LANGUAGE, mS).commit();
    }

    /**
     * get language app
     * 1
     *
     * @return
     */
    public String getLanguages() {
        Log.d(TAG, "getLanguages");
        return sPref.getString(KEY_LANGUAGE, DEF_LANGUAGE);
    }

    /**
     * set metric system
     *
     * @param mS
     */
    public void setMetric(boolean mS) {
        Log.d(TAG, "setMetrick " + mS);
        editor.putBoolean(KEY_METRIC, mS).commit();
    }

    /**
     * get metric sysem
     *
     * @return
     */
    public boolean getMetric() {
        Log.d(TAG, "getMetrick");
        return sPref.getBoolean(KEY_METRIC, DEF_EMPTY_BOOLEAN);
    }

    /**
     * set profile BLE
     *
     * @param mS
     */
    public void setProfileBLE(int mS) {
        Log.d(TAG, "setProfileBLE " + mS);
        editor.putInt(KEY_PROFILE_BLE_ID, mS).commit();
    }

    /**
     * get profile BLE
     *
     * @return
     */
    public int getProfileBLE() {
        Log.d(TAG, "getProfileBLE");
        return sPref.getInt(KEY_PROFILE_BLE_ID, DEF_INT_EMPTY);
    }

    /**
     * for added icon in main hom window device
     *
     * @param mS
     */
    public void setIconInHome(boolean mS) {
        Log.d(TAG, "setIconInHome " + mS);
        editor.putBoolean(KEY_ICON_IN_HOME, mS).commit();
    }

    /**
     * for added icon in main hom window device
     *
     * @return
     */
    public boolean isIconInHome() {
        Log.d(TAG, "getProfileBLE");
        return sPref.getBoolean(KEY_ICON_IN_HOME, DEF_EMPTY_BOOLEAN);
    }

    /**
     * for added icon in main hom window device
     *
     * @param mS
     */
    public void setUserServer(boolean mS) {
        Log.d(TAG, "setIconInHome " + mS);
        editor.putBoolean(KEY_USER_SERVER, mS).commit();
    }

    /**
     * for added icon in main hom window device
     *
     * @return
     */
    public boolean isUserServer() {
        Log.d(TAG, "getProfileBLE");
        return sPref.getBoolean(KEY_USER_SERVER, DEF_EMPTY_BOOLEAN);
    }

    /**
     * for check if user login FB
     *
     * @return
     */
    public boolean isUserLOginFB() {
        Log.d(TAG, "isUserLOginFB");
        return sPref.getBoolean(KEY_USER_LOGIN_FB, DEF_EMPTY_BOOLEAN);
    }

    /**
     * for check if user login FB
     *
     * @param mS
     */
    public void setUserLOginFB(boolean mS) {
        Log.d(TAG, "setUserLOginFB " + mS);
        editor.putBoolean(KEY_USER_LOGIN_FB, mS).commit();
    }

    /**
     * for show and synchronization weight from google fit
     *
     * @param mS
     */
    public void setShowWeight(boolean mS) {
        Log.d(TAG, "setShowWeight " + mS);
        editor.putBoolean(KEY_SHOW_WEIGHT_GOOGLE_FIT, mS).commit();
    }

    /**
     * for show and synchronization weight from google fit
     *
     * @return
     */
    public boolean isShowWeight() {
        Log.d(TAG, "isShowWeight");
        return sPref.getBoolean(KEY_SHOW_WEIGHT_GOOGLE_FIT, DEF_EMPTY_BOOLEAN);
    }

    /**
     * for save weight from google fit
     *
     * @param mS
     */
    public void saveWeight(String mS) {
        Log.d(TAG, "saveWeight " + mS);
        editor.putString(KEY_SAVE_WEIGHT, mS).commit();
    }

    /**
     * for get weight from google fit
     *
     * @return
     */
    public String getWeight() {
        Log.d(TAG, "getWeight");
        return sPref.getString(KEY_SAVE_WEIGHT, DEF_EMPTY_STRING);
    }

    /**
     * for save target weight from google fit
     *
     * @param mS
     */
    public void saveTargetWeight(int mS) {
        Log.d(TAG, "saveTargetWeight " + mS);
        editor.putInt(KEY_TARGET_WEIGHT, mS).commit();
    }

    /**
     * for get getTargetWeight from google fit
     *
     * @return
     */
    public int getTargetWeight() {
        Log.d(TAG, "getTargetWeight");
        return sPref.getInt(KEY_TARGET_WEIGHT, DEF_INT_EMPTY);
    }

    /**
     * save gender from facebook
     *
     * @param mGender
     */
    public void saveGenderFb(int mGender) {
        Log.d(TAG, "saveGenderFb " + mGender);
        editor.putInt(KEY_GENDER_FB, mGender).commit();
    }

    /**
     * get gender from facebook
     */
    public int getGenderFb() {
        Log.d(TAG, "getGenderFb");
        return sPref.getInt(KEY_GENDER_FB, -1);
    }

    /**
     * save birthday from facebook
     *
     * @param mDate
     */
    public void saveBirthdayFb(Long mDate) {
        Log.d(TAG, "saveBirthdayFb " + mDate);
        editor.putLong(KEY_DATE_BIRTHDAY, mDate).commit();
    }

    /**
     * get birthday from facebook
     */
    public long getBirthdayFb() {
        Log.d(TAG, "getGenderFb");
        return sPref.getLong(KEY_DATE_BIRTHDAY, -1);
    }

    /**
     * save status authorisation by google fit
     *
     * @param mAuth
     */
    public void saveAuthGoogleFit(boolean mAuth) {
        Log.d(TAG, "saveAuthGoogleFit " + mAuth);
        editor.putBoolean(KEY_AUTH_GOOGLE_FIT, mAuth).commit();
    }

    /**
     * check status authorisation by google fit
     */
    public boolean isAuthGoogleFit() {
        Log.d(TAG, "isAuthGoogleFit");
        return sPref.getBoolean(KEY_AUTH_GOOGLE_FIT, DEF_EMPTY_BOOLEAN);
    }

    /**
     * set is show tutorial in login activity
     *
     * @param mAuth
     */
    public void setShowLoginTutorial(boolean mAuth) {
        Log.d(TAG, "setShowLoginTutorial " + mAuth);
        editor.putBoolean(KEY_SHOW_LOGIN_TUTORIAL, mAuth).commit();
    }

    /**
     * check  is show tutorial in login activity
     */
    public boolean isShowLoginTutorial() {
        Log.d(TAG, "setShowLoginTutorial");
        return sPref.getBoolean(KEY_SHOW_LOGIN_TUTORIAL, DEF_NOT_EMPTY_BOOLEAN);
    }

    /**
     * set is show tutorial in Main activity
     *
     * @param mAuth
     */
    public void setShowMainTutorial(boolean mAuth) {
        Log.d(TAG, "setShowMainTutorial " + mAuth);
        editor.putBoolean(KEY_SHOW_MAIN_TUTORIAL, mAuth).commit();
    }

    /**
     * check  is show tutorial in Main activity
     */
    public boolean isShowMainTutorial() {
        Log.d(TAG, "isShowMainTutorial");
        return sPref.getBoolean(KEY_SHOW_MAIN_TUTORIAL, DEF_NOT_EMPTY_BOOLEAN);
    }


    /**
     * set STATUS LOGIN IF LOGOUT
     *
     * @param mAuth
     */
    public void setLoginFRomLogout(boolean mAuth) {
        Log.d(TAG, "setLoginFRomLogout " + mAuth);
        editor.putBoolean(KEY_LOGIN_FROM_LOGOUT, mAuth).commit();
    }

    /**
     * check  STATUS LOGIN IF LOGOUT
     */
    public boolean getLoginFRomLogout() {
        Log.d(TAG, "getLoginFRomLogout");
        return sPref.getBoolean(KEY_LOGIN_FROM_LOGOUT, DEF_EMPTY_BOOLEAN);
    }
}
