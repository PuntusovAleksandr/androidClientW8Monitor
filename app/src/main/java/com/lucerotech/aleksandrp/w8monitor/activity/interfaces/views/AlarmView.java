package com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views;

import com.lucerotech.aleksandrp.w8monitor.d_base.model.AlarmModel;

import io.realm.RealmResults;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public interface AlarmView {

    void setAlarmItem(RealmResults<AlarmModel> mAlarmItems);

    void setAlarmInSystem(boolean mIsAmPicker, String mTime);
}
