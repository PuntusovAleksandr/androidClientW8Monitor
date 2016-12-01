package com.w8.w8monitor.android.activity.interfaces.presentts;

import android.content.Intent;

import com.w8.w8monitor.android.activity.LoginActivity;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.facebook.RegisterFacebook;
import com.w8.w8monitor.android.activity.interfaces.views.LoginView;

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
