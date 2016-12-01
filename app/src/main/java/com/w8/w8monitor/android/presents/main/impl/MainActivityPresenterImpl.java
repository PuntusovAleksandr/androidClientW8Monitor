package com.w8.w8monitor.android.presents.main.impl;

import android.content.Context;

import com.w8.w8monitor.android.activity.interfaces.presentts.MainActivityPresenter;
import com.w8.w8monitor.android.activity.interfaces.views.MainView;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.api.model.Measurement;
import com.w8.w8monitor.android.api.model.UserApi;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.d_base.model.ParamsBody;
import com.w8.w8monitor.android.fragments.main.CircleGraphView;
import com.w8.w8monitor.android.fragments.main.LinerGraphView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.w8.w8monitor.android.api.event.UpdateUiEvent.ALL_MEASUREMENTS;
import static com.w8.w8monitor.android.api.event.UpdateUiEvent.MEASUREMENTS;
import static com.w8.w8monitor.android.api.event.UpdateUiEvent.MEASUREMENTS_MASS_UPDATE;
import static com.w8.w8monitor.android.api.event.UpdateUiEvent.MEASUREMENTS_SUNS;
import static com.w8.w8monitor.android.api.event.UpdateUiEvent.USER_SUNS;
import static com.w8.w8monitor.android.utils.LoggerApp.logger;

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
                              long mTime, CircleGraphView mCircleGraphView, boolean mSync) {
        addParamBody(mWeightBody, mBody, mFat, mMuscul,
                mWaterBody, mFatVis, mEmr, mAgeBody, bmi,
                mTime, mCircleGraphView, mSync);
//        }
    }

    @Override
    public void addParamBody(float mWeightBody, float mBody, float mFat, float mMuscul,
                             float mWaterBody, float mFatVis, float mEmr, float mAgeBody, float bmi,
                             long mTime, CircleGraphView mCircleGraphView, boolean mSync) {
        RealmObj.getInstance().addParamsBody(mWeightBody, mBody, mFat, mMuscul,
                mWaterBody, mFatVis, mEmr, mAgeBody, bmi, mTime, mCircleGraphView, mSync);
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
    public void getAllMeasurements(MainView mMainView) {
        RealmObj.getInstance().getLastBodyParamsByServer(mMainView);
    }

    @Subscribe
    public void onEvent(UpdateUiEvent event) {
        if (event.isSucess()) {
            if (event.getId() == MEASUREMENTS_SUNS || event.getId() == ALL_MEASUREMENTS) {
                ArrayList<Measurement> data = (ArrayList<Measurement>) event.getData();
                if (data != null && data.size() > 0) {
                    updateParamsBody(data);
                }
            } else if (event.getId() == USER_SUNS) {
                UserApi data = (UserApi) (event.getData());
                if (data != null)
                    mMainView.makeUpdateUserSync(data);
            } else if (event.getId() == MEASUREMENTS) {
                Measurement data = (Measurement) event.getData();
                if (data != null)
                    addParamBodies(data);
            } else if (event.getId() == MEASUREMENTS_MASS_UPDATE) {
                ParamsBody data = (ParamsBody) event.getData();
                if (data != null)
                    mMainView.updateUi(data);
            }
        } else logger("Error read server " + (String) event.getData());
        System.out.println(event.getData().toString());
    }

    private void updateParamsBody(ArrayList<Measurement> mData) {

        for (int i = 0; i < mData.size(); i++) {
            Measurement measurement = mData.get(i);
            addParamBodies(measurement);
        }
    }

    private void addParamBodies(Measurement mMeasurement) {
        mMainView.addParamBody(
                mMeasurement.getFloat_weight(),
                mMeasurement.getFat(),
                mMeasurement.getBone_mass(),
                mMeasurement.getMuscle_mass(),
                mMeasurement.getFat_level(),
                mMeasurement.getBody_water(),
                mMeasurement.getCalories(),
                0,
                mMeasurement.getBmi(),
                mMeasurement.getProfile_id(),
                mMeasurement.getCreated_at()
        );
    }
}
