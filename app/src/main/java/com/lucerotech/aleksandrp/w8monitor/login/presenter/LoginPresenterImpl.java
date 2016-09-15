package com.lucerotech.aleksandrp.w8monitor.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;
import com.lucerotech.aleksandrp.w8monitor.login.LoginPresenter;
import com.lucerotech.aleksandrp.w8monitor.login.LoginView;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity;
import com.lucerotech.aleksandrp.w8monitor.register.RegisterActivity;
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
        if (ValidationText.checkLenghtPassword(mPasswordText) &&
                ValidationText.checkValidEmail(mEmailText)) {
            mLoginView.hideWrong();
        } else {
            mLoginView.showWrong();
        }
    }

    @Override
    public void showDeletePassword() {
        mLoginView.showDeleteImages(false, true);
    }

    @Override
    public void showDeleteLogin() {
        mLoginView.showDeleteImages(true, false);
    }

    @Override
    public void hideAllDelete() {
        mLoginView.showDeleteImages(false, false);
    }

    @Override
    public void goToProfile() {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        mContext.startActivity(intent);
        mLoginView.finishActivity();
    }

    @Override
    public void goToRegistering() {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        mContext.startActivity(intent);
        mLoginView.finishActivity();
    }

    @Override
    public void onActivityResultFB(
            int mRequestCode, int mResultCode, Intent mData, RegisterFacebook mRegisterFacebook) {
        mRegisterFacebook.onActivityResultFB(mRequestCode,  mResultCode, mData, mContext);
    }
}
