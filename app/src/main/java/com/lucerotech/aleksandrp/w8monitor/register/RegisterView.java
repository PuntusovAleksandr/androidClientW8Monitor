package com.lucerotech.aleksandrp.w8monitor.register;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface RegisterView {

    void showDeleteImages(boolean email, boolean pass, boolean repeatPass);

    void isValidData(boolean isValid);

    void isShowButton(boolean mIsShow);

    void isUserSaveLogin(boolean isValid, int mI);

    void finish();
}
