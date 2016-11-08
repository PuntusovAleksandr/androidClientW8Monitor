package com.lucerotech.aleksandrp.w8monitor.general;

import com.lucerotech.aleksandrp.w8monitor.general.fragment.CircleGraphView;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.LinerGraphView;

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
}
