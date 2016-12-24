package com.w8.w8monitor.android.tutorial.view.utils;

import android.os.Build;

/**
 * Created by AleksandrP on 24.12.2016.
 */

public class ApiUtils {
    public boolean isCompatWith(int versionCode) {
        return Build.VERSION.SDK_INT >= versionCode;
    }

    public boolean isCompatWithHoneycomb() {
        return isCompatWith(Build.VERSION_CODES.HONEYCOMB);
    }

}
