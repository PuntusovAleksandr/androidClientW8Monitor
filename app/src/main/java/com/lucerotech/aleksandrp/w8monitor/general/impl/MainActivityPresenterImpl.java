package com.lucerotech.aleksandrp.w8monitor.general.impl;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.general.MainActivityPresenter;
import com.lucerotech.aleksandrp.w8monitor.general.MainView;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.CircleGraphView;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.LinerGraphView;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private Context mContext;
    private MainView mMainView;

    public MainActivityPresenterImpl(Context mContext) {
        this.mContext = mContext;
    }

    public MainActivityPresenterImpl(Context mContext, MainView mMainView) {
        this.mContext = mContext;
        this.mMainView = mMainView;
    }

    @Override
    public void goToSettingsProfile() {
        mMainView.goToSettings();
    }

    @Override
    public void setLineGraph(int mValue) {
        mMainView.setLineGraph(mValue);
    }

    @Override
    public void addParamsBody(float mWeightBody, float mBody, float mFat, float mMuscul,
                              float mWaterBody, float mFatVis, float mEmr, float mAgeBody, float bmi,
                              CircleGraphView mCircleGraphView) {
        RealmObj.getInstance().addParamsBody(mWeightBody, mBody, mFat, mMuscul,
                mWaterBody, mFatVis, mEmr, mAgeBody, bmi, mCircleGraphView);
    }

    @Override
    public void getDataForCircle(int mI, CircleGraphView mCircleGraphView) {
        RealmObj.getInstance().getDataForCircle(mI, mCircleGraphView);
    }

    @Override
    public void getDataForLineChart(long timeNow,
                                    long timeStart,
                                    int mPickerBottomValue,
                                    LinerGraphView mLinerGraphView) {
        RealmObj.getInstance().getDataForLineChart(timeNow, timeStart, mPickerBottomValue, mLinerGraphView);
    }
}
