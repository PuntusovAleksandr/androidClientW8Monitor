package com.lucerotech.aleksandrp.w8monitor.login;

import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface LoginView {

    void showWrong();

    void hideWrong(boolean mLogin);

    void showDeleteImages(boolean deleteLogin, boolean deletePassword);

    void finishActivity();

    void userExist(boolean mUserExist, UserLibr mUser);

    void changePassUserExist(boolean mExist, String mEmail);

    void goToProfile();

    void loginServer();

    void updateLogin(UpdateUiEvent mEvent);
}
