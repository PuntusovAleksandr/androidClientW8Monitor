package com.w8.w8monitor.android.activity.interfaces.presentts;


import com.w8.w8monitor.android.activity.interfaces.views.ChangePasswordView;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;

/**
 * Created by AleksandrP on 16.09.2016.
 */

public interface ChangePasswordPresenter {

    void showDeleteRepeatPassword();

    void showDeletePassword();

    void showDeleteLogin();

    void hideAllDelete();

    void checkShowButton(String mPasswordText, String passwordTextOld, String mRepearPasswordText,
                         ChangePasswordView mPasswordView);

    void changePassword(String mMailUser, String mPasswordTextOld, String mPasswordText,
                        String mRepearPasswordText, ChangePasswordView mPasswordView);

    void changePasswordInDb(String mMailUser, String mPasswordTextOld, String mPasswordText,
                            String mRepearPasswordText, UpdateUiEvent mEvent, ChangePasswordView mPasswordView);
}
