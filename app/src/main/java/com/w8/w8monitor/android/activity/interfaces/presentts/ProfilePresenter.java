package com.w8.w8monitor.android.activity.interfaces.presentts;


import com.w8.w8monitor.android.d_base.RealmObj;

/**
 * Created by AleksandrP on 21.09.2016.
 */

public interface ProfilePresenter {

    void saveStateUser(int mSatate, RealmObj.StateListener mProfileView);

    void getStateUser(RealmObj.StateListener mProfileViewt);

    void getBodyUser(RealmObj.BodyListener mBodyListener);

    void setBody(int mBodyType, RealmObj.BodyListener mBodyListener);

    void getProfileBLE(RealmObj.ProfileBLeListener mListener);

    void setProfileBLE(int mProfile, RealmObj.ProfileBLeListener mListener);

    void goToLoginActivity();

    void goToMainActivity();

    void setDateBirthDay(RealmObj.BirthDayListener mListener);

    void saveDateBirthDay(String mDate, RealmObj.BirthDayListener mListener);

    void saveTargetWeight(String mDate, RealmObj.TargetWeightListener mListener);

    void setHeight(RealmObj.HeightListener mListener);

    void saveHeight(String mHeight, RealmObj.HeightListener mListener);

    void setFullSettings(RealmObj.ProfileBLeListener mBLeListener);

    void setFullSettings(RealmObj.ProfileFirstStartBLeListener mBLeListener);

    void getUserForSettings(RealmObj.GetUserForSettings mGetUserForSettings);

    void setStateUser(int mRes, RealmObj.GetUserForSettings mGetUserForSettings);

    void setTypeProfile(int mRes, RealmObj.GetUserForSettings mGetUserForSettings);
}
