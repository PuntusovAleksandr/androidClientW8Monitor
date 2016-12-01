package com.w8.w8monitor.android.activity.interfaces.views;


import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.d_base.model.UserLibr;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public interface RegisterView {

    void showDeleteImages(boolean email, boolean pass, boolean repeatPass);

    void isValidData(boolean isValid);

    void isShowButton(boolean mIsShow);

    void isUserSaveLogin(boolean isValid, UserLibr mOser);

    void finish();

    void updateLogin(UpdateUiEvent mEvent);
}
