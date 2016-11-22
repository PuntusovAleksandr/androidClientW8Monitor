package com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views;

import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.ParamsBody;

import io.realm.RealmResults;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public interface MainView {

    void goToSettings();

    void setLineGraph(int mValue);

    void sendProfileToServer();

    void makeUpdateUserSync(UserApi mEvent);

    void makeRequestUpdateMeasurement();

    void makeAllUpdateUi();

    void getAllMeasurementsFromServer(RealmResults<ParamsBody> mAllSorted);

    void addParamBody(float weight, float fat, float gugeBody,
                      float muscle, int level_fat, float waterRate,
                      int emr, int mPhysicalAge, float mBmi, int profileId, long mCreated_at);

    void semdMeasurementToServer(long mTime);
}
