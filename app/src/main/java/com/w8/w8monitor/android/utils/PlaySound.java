package com.w8.w8monitor.android.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import com.w8.w8monitor.android.App;
import com.w8.w8monitor.android.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by AleksandrP on 15.12.2016.
 */

public class PlaySound {

    private static MediaPlayer mPlayer;

    public static void playHeartBit() {
        Context context = App.getContext();

        Uri ringtone = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.one_heart_bit);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ringtone);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                r.play();
            }
        }, 300);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(null)
                .setContentText(null)
                .setSound(ringtone); //This sets the sound to play

        //Display notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }
}
