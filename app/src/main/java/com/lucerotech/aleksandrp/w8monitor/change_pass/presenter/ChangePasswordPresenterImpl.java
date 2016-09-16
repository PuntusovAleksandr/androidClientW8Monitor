package com.lucerotech.aleksandrp.w8monitor.change_pass.presenter;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.change_pass.ChangePasswordPresenter;
import com.lucerotech.aleksandrp.w8monitor.change_pass.ChangePasswordView;

/**
 * Created by AleksandrP on 16.09.2016.
 */

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {

    private Context mContext;
    private ChangePasswordView mPasswordView;

    public ChangePasswordPresenterImpl(Context mContext, ChangePasswordView mPasswordView) {
        this.mContext = mContext;
        this.mPasswordView = mPasswordView;
    }
}
