package com.w8.w8monitor.android.utils;

import android.app.Activity;
import android.content.Context;

import com.w8.w8monitor.android.App;
import com.w8.w8monitor.android.R;

/**
 * Created by AleksandrP on 02.10.2016.
 */

public class SetThemeDark {

    private Context mContext;
    private static SetThemeDark ourInstance = new SetThemeDark();

    /**
     * Construct the instance of the object
     */
    public SetThemeDark() {
        mContext = App.getContext();
    }

    /**
     * get instance settingsApp
     *
     * @return
     */
    public static SetThemeDark getInstance() {
        return ourInstance;
    }


    public void setTheme(Activity mActivity) {
        boolean themeDark = SettingsApp.getInstance().isThemeDark();
        if (themeDark) {
            mActivity.setTheme(R.style.AppTheme);
        } else {
            mActivity.setTheme(R.style.AppThemeLight);
        }
    }
}
