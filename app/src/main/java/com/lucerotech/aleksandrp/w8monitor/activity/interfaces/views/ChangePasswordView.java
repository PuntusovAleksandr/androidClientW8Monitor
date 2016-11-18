package com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views;

/**
 * Created by AleksandrP on 16.09.2016.
 */

public interface ChangePasswordView {
    void showDeleteIcons(boolean login, boolean pass, boolean newPass);

    void isShowButton(boolean isValid);

    void showMessage();

    void showMessageNotFoundUser();

    void showMessageOkChangePass();
}
