package com.w8.w8monitor.android.activity.interfaces.presentts;

import android.content.Intent;

import com.w8.w8monitor.android.activity.interfaces.views.RegisterView;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.facebook.RegisterFacebook;


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

    void registerEvenBus();

    void unregisterEvenBus();

    void checkUserInDb(String mMail, String mPassword, RegisterView mListener, UpdateUiEvent mEvent);
}
