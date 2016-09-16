package com.lucerotech.aleksandrp.w8monitor.change_pass;

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

    void changePasswordInDb(String mMailUser, String mPasswordTextOld, String mPasswordText, String mRepearPasswordText,
                            ChangePasswordView mPasswordView);
}
