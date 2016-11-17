package com.lucerotech.aleksandrp.w8monitor.login;

import android.content.Intent;

import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;
import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface LoginPresenter {


    void checkPassword(String mPasswordText, String mEmailText, boolean mLogin);

    void showDeletePassword();

    void showDeleteLogin();

    void hideAllDelete();

    void goToProfile();

    void goToRegistering(LoginActivity mLoginActivity);

    void onActivityResultFB(int mRequestCode, int mResultCode, Intent mData, RegisterFacebook mRegisterFacebook);

    void checkUserInDb(String mLogin, String mPass, LoginView mListenerLoginView, UpdateUiEvent mEvent);

    void goToChangePassword(String mEmail, LoginView mListenerLoginView);

    void goToChangePasswordActivity(String mEmail);

    void inputEmptyUser(String mMail, String mPass, LoginView mLoginView);

    void goToMainActivity();

    void registerEvenBus();

    void unregisterEvenBus();

}
