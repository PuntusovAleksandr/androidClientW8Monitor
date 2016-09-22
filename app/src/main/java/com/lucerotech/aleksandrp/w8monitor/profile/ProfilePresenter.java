package com.lucerotech.aleksandrp.w8monitor.profile;

import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;

/**
 * Created by AleksandrP on 21.09.2016.
 */

public interface ProfilePresenter {

    void saveStateUser(int mSatate, RealmObj.StateListener mProfileView);

    void getStateUser(RealmObj.StateListener mProfileViewt);

    void getBodyUser(RealmObj.BodyListener mBodyListener);

    void setBody(int mBodyType, RealmObj.BodyListener mBodyListener);
}
