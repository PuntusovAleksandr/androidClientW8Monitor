package com.lucertech.w8monitor.android.presents.change_pass.presenter;

import android.content.Context;

import com.lucertech.w8monitor.android.activity.interfaces.presentts.ChangePasswordPresenter;
import com.lucertech.w8monitor.android.activity.interfaces.views.ChangePasswordView;
import com.lucertech.w8monitor.android.api.event.UpdateUiEvent;
import com.lucertech.w8monitor.android.d_base.RealmObj;
import com.lucertech.w8monitor.android.utils.ValidationText;

import static com.lucertech.w8monitor.android.utils.InternetUtils.checkInternetConnection;

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

    @Override
    public void showDeleteRepeatPassword() {
        mPasswordView.showDeleteIcons(false, false, true);
    }

    @Override
    public void showDeletePassword() {
        mPasswordView.showDeleteIcons(false, true, false);
    }

    @Override
    public void showDeleteLogin() {
        mPasswordView.showDeleteIcons(true, false, false);
    }

    @Override
    public void hideAllDelete() {
        mPasswordView.showDeleteIcons(false, false, false);
    }

    @Override
    public void checkShowButton(String mPasswordText, String passwordTextOld,
                                String mRepearPasswordText, ChangePasswordView mPasswordView) {
        if (ValidationText.checkLenghtPassword(mPasswordText) &&
                ValidationText.checkLenghtPassword(passwordTextOld) &&
                ValidationText.checkLenghtPassword(mRepearPasswordText) &&
                mPasswordText.equals(mRepearPasswordText)) {
            mPasswordView.isShowButton(true);
        } else {
            mPasswordView.isShowButton(false);
        }
    }

    @Override
    public void changePassword(String mMailUser, String mPasswordTextOld, String mPasswordText,
                               String mRepearPasswordText, ChangePasswordView mPasswordView) {
        if (ValidationText.checkLenghtPassword(mPasswordText) &&
                ValidationText.checkLenghtPassword(mPasswordTextOld) &&
                ValidationText.checkLenghtPassword(mRepearPasswordText) &&
                mPasswordText.equals(mRepearPasswordText)) {

            if (checkInternetConnection()) {
                mPasswordView.makeRequest();
            } else {
                mPasswordView.showMessageNoInternet();
            }

        } else {
            mPasswordView.showMessage();
        }
    }

    @Override
    public void changePasswordInDb(String mMailUser, String mPasswordTextOld, String mPasswordText,
                                   String mRepearPasswordText, UpdateUiEvent mEvent,
                                   ChangePasswordView mPasswordView) {
        RealmObj.getInstance().checkAndChangePassword(
                mMailUser, mPasswordTextOld, mPasswordText, mPasswordView, mEvent);
    }
}
