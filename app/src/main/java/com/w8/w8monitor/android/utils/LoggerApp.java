package com.w8.w8monitor.android.utils;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

/**
 * Created by AleksandrP on 23.10.2016.
 */

public class LoggerApp {

    public static final String TAG_LOG = "TAG_LOG";

    public static void logger(String textLog) {
        Log.d(TAG_LOG, textLog);
    }

    public static void loggerE(String textLog) {
        Log.e(TAG_LOG, textLog);
    }


    public static void loggerCrashlytics(String textLog) {
        Crashlytics.log(textLog);
    }

    public static void saveAllLogs(String textLog) {
        logger(textLog);
        loggerCrashlytics(textLog);
    }


}
