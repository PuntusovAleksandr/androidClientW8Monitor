package com.w8.w8monitor.android.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.w8.w8monitor.android.App;
import com.w8.w8monitor.android.R;

import java.io.IOException;

/**
 * Created by AleksandrP on 15.12.2016.
 */

public class PlaySound {

    private static MediaPlayer mPlayer;

    public static void playHeartBit() {
        Context context = App.getContext();

        Uri ringtone = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.one_heart_bit);
//        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ringtone);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                r.play();
//            }
//        }, 300);


        MediaPlayer mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, ringtone);
        } catch (IOException mE) {
            mE.printStackTrace();
        }
        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.setLooping(true);
            try {
                mMediaPlayer.prepare();
            } catch (IOException mE) {
                mE.printStackTrace();
            }
            mMediaPlayer.start();
        }



//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
//                .setContentTitle(null)
//                .setContentText(null)
//                .setSound(ringtone); //This sets the sound to play
//
//        //Display notification
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, mBuilder.build());
    }
}
