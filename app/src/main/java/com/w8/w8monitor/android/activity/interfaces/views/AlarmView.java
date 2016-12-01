package com.w8.w8monitor.android.activity.interfaces.views;


import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.d_base.model.AlarmModel;

import io.realm.RealmResults;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public interface AlarmView {

    void setAlarmItem(RealmResults<AlarmModel> mAlarmItems);

    void setAlarmInSystem(boolean mIsAmPicker, String mTime);

    void updateAlarm(UpdateUiEvent mEvent);

    void updateAlarms();
}
