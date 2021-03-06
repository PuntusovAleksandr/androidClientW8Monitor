package com.w8.w8monitor.android.presents.register.presenter;

import android.content.Context;
import android.content.Intent;

import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.facebook.RegisterFacebook;
import com.w8.w8monitor.android.activity.LoginActivity;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.RegisterPresenter;
import com.w8.w8monitor.android.activity.interfaces.views.RegisterView;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.ValidationText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    public void checkPassword(String mPasswordText, String mEmailText, String mRepeatPasswordText,
                              RegisterView mRegisterView) {
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
    public void checkShowButton(String mPasswordText, String mEmailText, String mRepearPasswordText, RegisterView mRegisterView) {
        if (ValidationText.checkLenghtPassword(mPasswordText) &&
                ValidationText.checkValidEmail(mEmailText) &&
                ValidationText.checkLenghtPassword(mRepearPasswordText) &&
                mPasswordText.equals(mRepearPasswordText)) {
            mRegisterView.isShowButton(true);
        } else {
            mRegisterView.isShowButton(false);
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
        intent.putExtra(STATICS_PARAMS.INNER_MARKER_PROFILE, ProfileActivity.MARKER_REGISTER);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        mRegisterView.finish();
    }

    @Override
    public void onActivityResultFB(
            int mRequestCode, int mResultCode, Intent mData, RegisterFacebook mRegisterFacebook) {
        mRegisterFacebook.onActivityResultFB(mRequestCode, mResultCode, mData, mContext);
    }

    @Override
    public void getBackLoginActivity() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        mRegisterView.finish();
    }

    @Override
    public void checkUserInDb(String mMail, String mPassword, RegisterView mListener,
                              UpdateUiEvent mEvent) {
        RealmObj.getInstance().putUser(
                mMail,
                mPassword,
                mListener, mEvent);
    }

    @Subscribe
    @Override
    public void registerEvenBus() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    @Override
    public void unregisterEvenBus() {
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(UpdateUiEvent event) {
        mRegisterView.updateLogin(event);
        System.out.println(event.getData().toString());
    }
}
