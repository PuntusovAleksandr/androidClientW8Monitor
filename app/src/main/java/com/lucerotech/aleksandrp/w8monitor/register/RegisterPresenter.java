package com.lucerotech.aleksandrp.w8monitor.register;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface RegisterPresenter {

    void checkPassword(String mPasswordText, String mEmailText, String mRepeatPasswordText);

    void showDeleteLogin();

    void showDeletePassword();

    void showDeleteRepeatPassword();

    void hideAllDelete();

    void goToProfile();
}
