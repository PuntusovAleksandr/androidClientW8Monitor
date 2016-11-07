package com.lucerotech.aleksandrp.w8monitor.alarm.impl;

import android.content.Context;
import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.alarm.AlarmActivityPresenter;
import com.lucerotech.aleksandrp.w8monitor.alarm.AlarmView;
import com.lucerotech.aleksandrp.w8monitor.alarm.manager.AlarmManager;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public class AlarmActivityPresenterImpl implements AlarmActivityPresenter {

    private Context mContext;
    private AlarmManager mAlarmManager;

    public AlarmActivityPresenterImpl(Context mContext) {
        this.mContext = mContext;
        mAlarmManager = new AlarmManager();
    }

    @Override
    public void startRepeatingTimer(long mTime) {
        if (mAlarmManager != null) {
            mAlarmManager.SetAlarm(mContext);
        } else {
            Toast.makeText(mContext, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getAlarmFromDb(AlarmView mAlarmView) {
        RealmObj.getInstance().getAlarmFromDb(mAlarmView);
    }

    @Override
    public void setAlarmInDb(boolean isAmPicker, String mTime, AlarmView mAlarmView) {
        RealmObj.getInstance().setAlarmInDb(isAmPicker, mTime, mAlarmView);
    }

    @Override
    public void deleteAlarmFromDb(String mTimeText, AlarmView mAlarmView) {
        RealmObj.getInstance().deleteAlarmFromDn(mTimeText, mAlarmView);
    }

    @Override
    public void stopAlarm() {
        if (mAlarmManager != null) {
            mAlarmManager.CancelAlarm(mContext);
        } else {
            Toast.makeText(mContext, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelRepeatingTimer() {
        if (mAlarmManager != null) {
            mAlarmManager.CancelAlarm(mContext);
        } else {
            Toast.makeText(mContext, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void onetimeTimer() {
        if (mAlarmManager != null) {
            mAlarmManager.setOnetimeTimer(mContext);
        } else {
            Toast.makeText(mContext, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

}
