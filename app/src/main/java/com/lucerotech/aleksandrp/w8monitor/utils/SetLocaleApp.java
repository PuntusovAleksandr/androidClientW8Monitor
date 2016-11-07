package com.lucerotech.aleksandrp.w8monitor.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.lucerotech.aleksandrp.w8monitor.App;

import java.util.Locale;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public class SetLocaleApp {

    public static void setLocale() {
        String lang = SettingsApp.getInstance().getLanguages();
        Locale myLocale = new Locale(lang);
        Resources res = App.getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(this, AndroidLocalize.class);
//        startActivity(refresh);
    }

}
