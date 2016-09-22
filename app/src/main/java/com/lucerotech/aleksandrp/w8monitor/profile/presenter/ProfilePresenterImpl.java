package com.lucerotech.aleksandrp.w8monitor.profile.presenter;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfilePresenter;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileView;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

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
}
