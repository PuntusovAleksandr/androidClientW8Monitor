package com.lucerotech.aleksandrp.w8monitor.utils;

import android.app.Activity;
import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.App;
import com.lucerotech.aleksandrp.w8monitor.R;

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
