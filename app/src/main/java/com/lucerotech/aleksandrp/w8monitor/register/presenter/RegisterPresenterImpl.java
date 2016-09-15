package com.lucerotech.aleksandrp.w8monitor.register.presenter;

import android.content.Context;
import android.content.Intent;

import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity;
import com.lucerotech.aleksandrp.w8monitor.register.RegisterPresenter;
import com.lucerotech.aleksandrp.w8monitor.register.RegisterView;
import com.lucerotech.aleksandrp.w8monitor.utils.ValidationText;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView mRegisterView;
    private Context mContext;

    public RegisterPresenterImpl(RegisterView mRegisterView, Context mContext) {
        this.mRegisterView = mRegisterView;
        this.mContext = mContext;
    }


    @Override
    public void checkPassword(String mPasswordText, String mEmailText, String mRepeatPasswordText) {
        if (ValidationText.checkLenghtPassword(mPasswordText) &&
                ValidationText.checkValidEmail(mEmailText) &&
                ValidationText.checkLenghtPassword(mRepeatPasswordText) &&
                mPasswordText.equals(mRepeatPasswordText)) {
            mRegisterView.isValidData(true);
        } else {
            mRegisterView.isValidData(false);
        }
    }

    @Override
    public void showDeleteLogin() {
        mRegisterView.showDeleteImages(true, false, false);
    }

    @Override
    public void showDeletePassword() {
        mRegisterView.showDeleteImages(false, true, false);
    }

    @Override
    public void showDeleteRepeatPassword() {
        mRegisterView.showDeleteImages(false, false, true);
    }

    @Override
    public void hideAllDelete() {
        mRegisterView.showDeleteImages(false, false, false);
    }

    @Override
    public void goToProfile() {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onActivityResultFB(
            int mRequestCode, int mResultCode, Intent mData, RegisterFacebook mRegisterFacebook) {
        mRegisterFacebook.onActivityResultFB(mRequestCode, mResultCode, mData, mContext);
    }
}
