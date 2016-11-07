package com.lucerotech.aleksandrp.w8monitor.utils;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class STATICS_PARAMS {

    // REGISTERED TEST USER
    public static final String TEST_USER = "test@test.test";

    // FOR SET COUNT PAGES IN VIEW PAGER IN PROFILE
    public static final int COUNT_PAGES_PROFILE_DEFOULT = 4;
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
    // time check ble and show message
    public static final int TIME_CHECK_BLE = 3000;

    // set MAX and MIN value parametrs in picker
    public static final int MAX_VALUE_PICKER = 7;
    public static final int MIN_VALUE_PICKER = 1;


    // for check params value number picker weight
    public static final int PICKER_WATER = 1;
    public static final int PICKER_CALORIES = 2;
    public static final int PICKER_WEIGHT = 3;
    public static final int PICKER_FAT_LEVEL = 4;
    public static final int PICKER_MUSCLE_MASS = 5;
    public static final int PICKER_BONE_MASS = 6;
    public static final int PICKER_FAT = 7;

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

}
