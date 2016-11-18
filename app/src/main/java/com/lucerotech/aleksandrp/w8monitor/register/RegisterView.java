package com.lucerotech.aleksandrp.w8monitor.register;

import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;

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
