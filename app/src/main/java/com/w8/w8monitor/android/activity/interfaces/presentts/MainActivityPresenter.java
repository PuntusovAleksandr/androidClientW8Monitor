package com.w8.w8monitor.android.activity.interfaces.presentts;

import com.w8.w8monitor.android.activity.interfaces.views.MainView;
import com.w8.w8monitor.android.api.model.UserApi;
import com.w8.w8monitor.android.fragments.main.CircleGraphView;
import com.w8.w8monitor.android.fragments.main.LinerGraphView;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public interface MainActivityPresenter {

    void goToSettingsProfile();

    void setLineGraph(int mValue);

    void addParamsBody(float mWeightBody, float mBody, float mFat, float mMuscul,
                       float mWaterBody, float mFatVis, float mEmr, float mAgeBody, float bmi,
                       long mTime, CircleGraphView mCircleGraphView, boolean mSync);

    void addParamBody(float mWeightBody, float mBody, float mFat, float mMuscul,
                      float mWaterBody, float mFatVis, float mEmr, float mAgeBody, float bmi,
                      long mTime, CircleGraphView mCircleGraphView, boolean mSync);

    void getDataForCircle(int mI, CircleGraphView mCircleGraphView);

    void getDataForLineChart(long timeNow,
                             long timeStart,
                             int mPickerBottomValue,
                             LinerGraphView mLinerGraphView);

    void sendProfileData();

    void registerEvenBus();

    void unregisterEvenBus();

    void makeUpdateUserDb(MainView mGraphView, UserApi mEvent);

    void getAllMeasurements(MainView mMainView);
}
