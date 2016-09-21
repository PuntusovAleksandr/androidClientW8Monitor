package com.lucerotech.aleksandrp.w8monitor.login;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface LoginView {

    void showWrong();

    void hideWrong();

    void showDeleteImages(boolean deleteLogin, boolean deletePassword);

    void finishActivity();

    void userExist(boolean mUserExist);

    void changePassUserExist(boolean mExist, String mEmail);

    void goToProfile();

}
