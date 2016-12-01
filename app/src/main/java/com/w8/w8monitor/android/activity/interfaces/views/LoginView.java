package com.w8.w8monitor.android.activity.interfaces.views;


import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.d_base.model.UserLibr;

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

    void loginServer(String mLogin, String mPass);

    void updateLogin(UpdateUiEvent mEvent);
}
