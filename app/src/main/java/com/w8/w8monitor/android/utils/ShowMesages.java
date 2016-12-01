package com.w8.w8monitor.android.utils;

import android.widget.Toast;

import com.w8.w8monitor.android.App;

/**
 * Created by AleksandrP on 04.10.2016.
 */

public class ShowMesages {

    /**
     * for show all messages by String
     *
     * @param mString
     */
    public static void showMessageToast(String mString) {
        Toast.makeText(App.getContext(), mString, Toast.LENGTH_SHORT).show();
    }

    /**
     * for show all messages by int res
     *
     * @param mString
     */
    public static void showMessageToast(int mString) {
        Toast.makeText(App.getContext(), mString, Toast.LENGTH_SHORT).show();
    }
}
