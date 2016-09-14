package com.lucerotech.aleksandrp.w8monitor.login.presenter;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.login.LoginPresenter;
import com.lucerotech.aleksandrp.w8monitor.login.LoginView;
import com.lucerotech.aleksandrp.w8monitor.utils.ValidationText;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mLoginView;
    private Context mContext;


    public LoginPresenterImpl(Context mContext, LoginView mLoginView) {
        this.mContext = mContext;
        this.mLoginView = mLoginView;
    }

    @Override
    public void checkPassword(String mPasswordText, String mEmailText) {
        if (mLoginView != null) {
            if (ValidationText.checkLenghtPassword(mPasswordText) &&
                    ValidationText.checkValidEmail(mEmailText)) {
                mLoginView.hideWrong();
            } else {
                mLoginView.showWrong();
            }
        }
    }
}
