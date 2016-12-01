package com.w8.w8monitor.android.presents.profile.presenter;

import android.content.Context;
import android.content.Intent;

import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.activity.MainActivity;
import com.w8.w8monitor.android.activity.LoginActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.utils.SettingsApp;

/**
 * Created by AleksandrP on 21.09.2016.
 */

public class ProfilePresenterImpl implements ProfilePresenter {

    private Context mContext;
    private ProfileView mProfileView;

    public ProfilePresenterImpl(Context mContext, ProfileView mProfileView) {
        this.mContext = mContext;
        this.mProfileView = mProfileView;
    }

    @Override
    public void saveStateUser(int mState, RealmObj.StateListener mProfileView) {
        String userName = SettingsApp.getInstance().getUserName();
        RealmObj.getInstance().setStateUser(userName, mState, mProfileView);
    }

    @Override
    public void getStateUser(RealmObj.StateListener mProfileViewt) {
        RealmObj.getInstance().getStateUser(mProfileViewt);
    }

    @Override
    public void getBodyUser(RealmObj.BodyListener mListener) {
        RealmObj.getInstance().getBodyUser(mListener);
    }

    @Override
    public void setBody(int mBodyType, RealmObj.BodyListener mBodyListener) {
        RealmObj.getInstance().setBodyUser(mBodyType, mBodyListener);
    }

    @Override
    public void getProfileBLE(RealmObj.ProfileBLeListener mListener) {
        RealmObj.getInstance().getProfileBle(mListener);
    }

    @Override
    public void setProfileBLE(int mProfile, RealmObj.ProfileBLeListener mListener) {
        RealmObj.getInstance().setProfileBLE(mProfile, mListener);
    }

    @Override
    public void goToLoginActivity() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void setDateBirthDay(RealmObj.BirthDayListener mListener) {
        RealmObj.getInstance().getDayBith(mListener);
    }

    @Override
    public void saveDateBirthDay(String mDate, RealmObj.BirthDayListener mListener) {
        RealmObj.getInstance().setDayBith(mDate, mListener);
    }

    @Override
    public void setHeight(RealmObj.HeightListener mListener) {
        RealmObj.getInstance().getHeight(mListener);
    }

    @Override
    public void saveHeight(String mHeight, RealmObj.HeightListener mListener) {
        RealmObj.getInstance().saveHeight(mHeight, mListener);
    }

    @Override
    public void setFullSettings(RealmObj.ProfileBLeListener mBLeListener) {
        RealmObj.getInstance().setFullSettings(mBLeListener);
    }

    @Override
    public void setFullSettings(RealmObj.ProfileFirstStartBLeListener mBLeListener) {
        RealmObj.getInstance().setFullFirsStartSettings(mBLeListener);
    }

    public void getUserForSettings(RealmObj.GetUserForSettings mGetUserForSettings) {
        RealmObj.getInstance().getUserForSettings(mGetUserForSettings);
    }

    @Override
    public void setStateUser(int mRes, RealmObj.GetUserForSettings mGetUserForSettings) {
        RealmObj.getInstance().setStateUserFromSettings(mRes, mGetUserForSettings);
    }

    @Override
    public void setTypeProfile(int mRes, RealmObj.GetUserForSettings mGetUserForSettings) {
        RealmObj.getInstance().setTypeProfile(mRes, mGetUserForSettings);
    }
}
