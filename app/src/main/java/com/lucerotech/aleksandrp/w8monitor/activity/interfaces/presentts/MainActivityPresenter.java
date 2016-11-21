package com.lucerotech.aleksandrp.w8monitor.activity.interfaces.presentts;

import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.MainView;
import com.lucerotech.aleksandrp.w8monitor.api.model.Measurement;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;
import com.lucerotech.aleksandrp.w8monitor.fragments.main.CircleGraphView;
import com.lucerotech.aleksandrp.w8monitor.fragments.main.LinerGraphView;

import java.util.ArrayList;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public interface MainActivityPresenter {

    void goToSettingsProfile();

    void setLineGraph(int mValue);

    void addParamsBody(float mWeightBody, float mBody, float mFat, float mMuscul,
                       float mWaterBody, float mFatVis, float mEmr, float mAgeBody, float bmi,
                       CircleGraphView mCircleGraphView);

    void getDataForCircle(int mI, CircleGraphView mCircleGraphView);

    void getDataForLineChart(long timeNow,
                             long timeStart,
                             int mPickerBottomValue,
                             LinerGraphView mLinerGraphView);

    void sendProfileData();

    void registerEvenBus();

    void unregisterEvenBus();

    void makeUpdateUserDb(MainView mGraphView, UserApi mEvent);

    void makeMessurementsDb(MainView mGraphView, ArrayList<Measurement> mData);
}
