package com.w8.w8monitor.android.utils;

import android.graphics.Point;
import android.support.v4.app.FragmentActivity;

/**
 * Created by AleksandrP on 03.11.2016.
 */

public class GetSizeWindow {

    public static int getSizeWindow(FragmentActivity mActivity) {
        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        return -((size.x / 5) * 3);
    }
}
