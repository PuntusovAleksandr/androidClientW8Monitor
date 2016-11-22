package com.lucerotech.aleksandrp.w8monitor.presents.main.impl;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.presentts.MainActivityPresenter;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.MainView;
import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;
import com.lucerotech.aleksandrp.w8monitor.api.model.Measurement;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.fragments.main.CircleGraphView;
import com.lucerotech.aleksandrp.w8monitor.fragments.main.LinerGraphView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent.MEASUREMENTS_SUNS;
import static com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent.USER_SUNS;
import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.logger;

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

    @Override
    public void sendProfileData() {
        mMainView.sendProfileToServer();
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

    @Override
    public void makeUpdateUserDb(MainView mGraphView, UserApi mEvent) {
        RealmObj.getInstance().updateUserDb(mGraphView, mEvent);
    }

    @Override
    public void makeMessurementsDb(MainView mGraphView, ArrayList<Measurement> mData) {
        RealmObj.getInstance().updateMessurementsDb(mGraphView, mData);
    }

    @Subscribe
    public void onEvent(UpdateUiEvent event) {
        if (event.isSucess()) {
            if (event.getId() == MEASUREMENTS_SUNS) {
                mMainView.makeUpdateMessurementsSync((ArrayList<Measurement>) (event.getData()));
            } else if (event.getId() == USER_SUNS) {
                mMainView.makeUpdateUserSync((UserApi) (event.getData()));
            }
        } else logger("Error read server " + (String) event.getData());
        System.out.println(event.getData().toString());
    }
}
