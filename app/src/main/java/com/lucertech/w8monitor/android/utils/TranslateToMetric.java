package com.lucertech.w8monitor.android.utils;

/**
 * Created by AleksandrP on 31.10.2016.
 */

public class TranslateToMetric {

    // for translate inch to metric 0.453592f - kg in funt
    // or 2.20462f - funt in kg
    public static float translateToMetricFloat(float mValue) {
        if (SettingsApp.getInstance().getMetric()) {
            return mValue;
        } else {
            float result = mValue * 2.20462f;
            String s = String.format("%.1f", result);
            s = s.replaceAll(",", ".");
            return Float.parseFloat(s);
        }
    }
}
