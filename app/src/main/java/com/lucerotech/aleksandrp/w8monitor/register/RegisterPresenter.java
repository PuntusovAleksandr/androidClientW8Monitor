package com.lucerotech.aleksandrp.w8monitor.register;

import android.content.Intent;

import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface RegisterPresenter {

    void checkPassword(String mPasswordText, String mEmailText, String mRepeatPasswordText,
                       RegisterView mRegisterView);

    void checkShowButton(String mPasswordText, String mEmailText, String mRepearPasswordText,
                         RegisterView mRegisterView);

    void showDeleteLogin();

    void showDeletePassword();

    void showDeleteRepeatPassword();

    void hideAllDelete();

    void goToProfile();

    void onActivityResultFB(int mRequestCode, int mResultCode, Intent mData, RegisterFacebook mRegisterFacebook);

    void getBackLoginActivity();
}
