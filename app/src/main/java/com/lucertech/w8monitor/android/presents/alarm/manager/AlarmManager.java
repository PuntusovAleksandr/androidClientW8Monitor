package com.lucertech.w8monitor.android.presents.alarm.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.lucertech.w8monitor.android.App;
import com.lucertech.w8monitor.android.R;
import com.lucertech.w8monitor.android.activity.SplashActivity;
import com.lucertech.w8monitor.android.d_base.RealmObj;
import com.lucertech.w8monitor.android.d_base.model.AlarmModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmResults;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public class AlarmManager extends BroadcastReceiver implements RealmObj.AlarmListener {


    final public static String ONE_TIME = "onetime";

    PowerManager pm;
    PowerManager.WakeLock wl;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Осуществляем блокировку
        wl.acquire();

        //Здесь можно делать обработку.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();

        if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {
            //проверяем параметр ONE_TIME, если это одиночный будильник,
            //выводим соответствующее сообщение.
            msgStr.append("Одноразовый будильник: ");
        }


        makeAlarm(context, msgStr);

        //Разблокируем поток.
        wl.release();
    }


//=============================================================
//        set data ALARM
//=============================================================

    public void SetAlarm(Context context) {
        android.app.AlarmManager am = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManager.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);//Задаем параметр интента
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
//Устанавливаем интервал срабатывания в 1 минуту .
        am.setRepeating(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pi);
    }

    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmManager.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);//Отменяем будильник, связанный с интентом данного класса
    }

    public void setOnetimeTimer(Context context) {
        android.app.AlarmManager am = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManager.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);//Задаем параметр интента
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }


//=============================================================
//        make ALARM
//=============================================================

    private void makeAlarm(Context context, StringBuilder mMsgStr) {
        RealmObj.getInstance().getAlarmFromDb(mMsgStr, this);

    }

    // from AlarmListener
    @Override
    public void getAllAlarm(RealmResults<AlarmModel> mAll, StringBuilder mMsgStr) {
        Format formatter = new SimpleDateFormat("HH:mm");
        mMsgStr.append(formatter.format(new Date()));

        for (int i = 0; i < mAll.size(); i++) {
            AlarmModel model = mAll.get(i);
            String time = model.getTime();
            String anObject = mMsgStr.toString();
            if (time.equals(anObject)) {
                notification();

//                try {
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Ringtone r = RingtoneManager.getRingtone(App.getContext(), notification);
//                    r.play();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }


    protected void notification() {

        Context context = App.getContext();

        pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK
                        | PowerManager.ON_AFTER_RELEASE
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP, "bbbb");
        wl.acquire(1000);


        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.you_need_weigh))
                .setContentInfo("W8M")
                .setAutoCancel(true)
                .setOngoing(false)
                .setSound(alarmSound)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(strParse))
                .setContentIntent(pendingIntent)
                .setShowWhen(true)
                .setPriority(Notification.PRIORITY_HIGH);

        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(10, mBuilder.build());
        final PowerManager.WakeLock screenOn = ((PowerManager) context.getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "example");
        screenOn.acquire();
//        Handler mHandler = new Handler(Looper.getMainLooper());
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mNotificationManager.cancel(10);
//
//            }
//        }, 20000);
    }
}
