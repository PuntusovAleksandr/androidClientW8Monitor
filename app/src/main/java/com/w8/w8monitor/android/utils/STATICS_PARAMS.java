package com.w8.w8monitor.android.utils;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class STATICS_PARAMS {

    // REGISTERED TEST USER
    public static final String TEST_USER = "test@test.test";

    // FOR SET COUNT PAGES IN VIEW PAGER IN PROFILE
    public static final int COUNT_PAGES_PROFILE_DEFOULT = 6;
    public static final int COUNT_PAGES_PROFILE_FROM_MAIN = 5;

    //    for validation
    protected static final int REGULAR_PASS = 7;
    protected static final String REGULAR_MAIL = "^[a-z0-9](\\.?[a-z0-9_-]){0,}@[a-z0-9-]+\\.([a-z]{1,6}\\.)?[a-z]{2,15}$";

    public static final String KEY_EXTRA_FROM = "KEY_EXTRA_FROM";
    public static final int KEY_FROM_SPLASH = 1;
    public static final int KEY_FROM_SETTINGS = 2;


    public static final String MAIL = "MAIL";

    // KEI_CONNECTION foe extra from main to profile
    public static final String KEI_CONNECTION = "KEI_CONNECTION";

    public static final int FB_CODE = 64206;

    // version DB
    public static final long VERSION_DB = 1;

    // MARKER FOR INPUT AT PROFILE
    public static final String INNER_MARKER_PROFILE = "INNER_MARKER_PROFILE";


    // for request ble
    public static final int REQUEST_ENABLE_BT = 101;
  // for request google_fit
    public static final int REQUEST_OAUTH = 344;
    public static final int REQUEST_CUST = 345;
    // time check ble and show message
    public static final int TIME_CHECK_BLE = 3000;

    // set MAX and MIN value parametrs in picker
    public static final int MAX_VALUE_PICKER = 8;
    public static final int MIN_VALUE_PICKER = 1;



    // for check params value number picker weight
    public static final int PICKER_WEIGHT = 1;
    public static final int PICKER_BMI = 2;
    public static final int PICKER_FAT = 3;
    public static final int PICKER_FAT_LEVEL = 4;
    public static final int PICKER_MUSCLE_MASS = 5;
    public static final int PICKER_WATER = 6;
    public static final int PICKER_CALORIES = 7;
    public static final int PICKER_BONE_MASS = 8;

////old list
//    // for check params value number picker weight
//    public static final int PICKER_WATER = 1;
//    public static final int PICKER_CALORIES = 2;
//    public static final int PICKER_WEIGHT = 3;
//    public static final int PICKER_BMI = 4;
//    public static final int PICKER_FAT_LEVEL = 5;
//    public static final int PICKER_MUSCLE_MASS = 6;
//    public static final int PICKER_BONE_MASS = 7;
//    public static final int PICKER_FAT = 8;

    // for check params value number picker time
    public static final int PICKER_DAY = 1;
    public static final int PICKER_DAY_ = 5;
    public static final int PICKER_WEEK = 2;
    public static final int PICKER_WEEK_ = 6;
    public static final int PICKER_MONTH = 3;
    public static final int PICKER_MONTH_ = 7;
    public static final int PICKER_YEAR = 4;
    public static final int PICKER_YEAR_ = 8;

    // count time in calendar
    public static long TIME_IN_DAY = 24 * 60 * 60 * 1000;
    public static long TIME_IN_WEEK = TIME_IN_DAY * 7;
    public static long TIME_IN_MONTH = TIME_IN_WEEK * 30;
    public static long TIME_IN_YEAR = 356 * TIME_IN_DAY;

    // for update speed pickers
    public static final long SPEED_UPDATE_PICKER = 500;


    // for work api with app
    public static final String SERVICE_JOB_ID_TITLE = "serviceJobId";
    public static final String SERVICE_MAIL = "SERVICE_MAIL";
    public static final String SERVICE_PASS = "SERVICE_PASS";
    public static final String SERVICE_KEY_PROFILE = "SERVICE_KEY_PROFILE";
    public static final String SERVICE_VALUE_KEY_PROFILE = "SERVICE_VALUE_KEY_PROFILE";
    public static final String SERVICE_PASSWORD_OLS = "SERVICE_PASSWORD_OLS";
    public static final String SERVICE_PASSWORD_NEW = "SERVICE_PASSWORD_NEW";
    public static final String SERVICE_PASSWORD_NEW_CONFIRM = "SERVICE_PASSWORD_NEW_CONFIRM";
    // key for send extra by api
    public static final String SOCIAL_ID = "SOCIAL_ID ";
    public static final String NEW_PASS = "NEW_PASS ";
    public static final String PROFILE_API = "PROFILE_API ";
    public static final String MESSUREMENTS = "MEASUREMENTS ";
    public static final String GENDER = "GENDER ";

    // extra
    public static final String EXTRA_TIME_CREATE = "EXTRA_TIME_CREATE ";
    public static final String EXTRA_TIMESTAMP = "EXTRA_TIMESTAMP ";

    // settings activity result
    public static final int SETTINGS_ACTIVITY_RESULT = 321;

    // settings activity result
    public static final int REQUEST_ACTION_LOCATION_SOURCE_SETTINGS = 323;

}
