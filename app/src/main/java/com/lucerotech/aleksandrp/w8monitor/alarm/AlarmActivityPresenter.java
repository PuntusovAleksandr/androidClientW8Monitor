package com.lucerotech.aleksandrp.w8monitor.alarm;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public interface AlarmActivityPresenter {

    void startRepeatingTimer(long mTime);

    void getAlarmFromDb(AlarmView mAlarmView);

    void setAlarmInDb(boolean isAmPicker, String mTime, AlarmView mAlarmView);

    void deleteAlarmFromDb(String mTimeText, AlarmView mAlarmView);

    void stopAlarm();
}
