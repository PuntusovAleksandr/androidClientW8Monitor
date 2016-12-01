package com.w8.w8monitor.android.activity.interfaces.views;


import com.w8.w8monitor.android.api.model.UserApi;
import com.w8.w8monitor.android.d_base.model.ParamsBody;

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

    void updateUi(ParamsBody mData);
}
