package com.lucerotech.aleksandrp.w8monitor.presents.alarm.impl;

import android.content.Context;
import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.presentts.AlarmActivityPresenter;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.AlarmView;
import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.presents.alarm.manager.AlarmManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public class AlarmActivityPresenterImpl implements AlarmActivityPresenter {

    private Context mContext;
    private AlarmManager mAlarmManager;
    private AlarmView mAlarmView;

    public AlarmActivityPresenterImpl(Context mContext, AlarmView mAlarmView) {
        this.mContext = mContext;
        this.mAlarmView = mAlarmView;
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

    @Subscribe
    @Override
    public void registerEvenBus() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    @Override
    public void unregisterEvenBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(UpdateUiEvent event) {
        mAlarmView.updateAlarm(event);
        System.out.println(event.getData().toString());
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
