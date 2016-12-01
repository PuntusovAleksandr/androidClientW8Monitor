package com.w8.w8monitor.android.utils;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by AleksandrP on 15.10.2016.
 */

public class SoundUtils {

    // play sound when data incoming
    public static void playSound() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }


}
