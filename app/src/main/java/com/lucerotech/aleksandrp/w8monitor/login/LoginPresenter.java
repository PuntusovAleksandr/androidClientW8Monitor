package com.lucerotech.aleksandrp.w8monitor.login;

import android.content.Intent;

import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface LoginPresenter {


    void checkPassword(String mPasswordText, String mEmailText);

    void showDeletePassword();

    void showDeleteLogin();

    void hideAllDelete();

    void goToProfile();

    void goToRegistering();

    void onActivityResultFB(int mRequestCode, int mResultCode, Intent mData, RegisterFacebook mRegisterFacebook);

    void checkUserInDb(String mLogin, String mPass);
}
