package com.lucerotech.aleksandrp.w8monitor.presents.change_pass.presenter;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.presentts.ChangePasswordPresenter;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.ChangePasswordView;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.utils.ValidationText;

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
    public void changePasswordInDb(String mMailUser, String mPasswordTextOld, String mPasswordText,
                                   String mRepearPasswordText, ChangePasswordView mPasswordView) {
        if (ValidationText.checkLenghtPassword(mPasswordText) &&
                ValidationText.checkLenghtPassword(mPasswordTextOld) &&
                ValidationText.checkLenghtPassword(mRepearPasswordText) &&
                mPasswordText.equals(mRepearPasswordText)) {

            RealmObj.getInstance().checkAndChangePassword(
                    mMailUser, mPasswordTextOld, mPasswordText, mPasswordView);
        } else {
            mPasswordView.showMessage();
        }
    }
}
