package com.w8.w8monitor.android.activity.interfaces.views;

/**
 * Created by AleksandrP on 16.09.2016.
 */

public interface ChangePasswordView {

    void showDeleteIcons(boolean login, boolean pass, boolean newPass);

    void isShowButton(boolean isValid);

    void showMessage();

    void showMessageNotFoundUser();

    void showMessageOkChangePass();

    void showMessageNoInternet();

    void makeRequest();
}
