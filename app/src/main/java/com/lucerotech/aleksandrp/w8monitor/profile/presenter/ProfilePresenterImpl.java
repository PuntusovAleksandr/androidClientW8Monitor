package com.lucerotech.aleksandrp.w8monitor.profile.presenter;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.profile.ProfilePresenter;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileView;

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
}
