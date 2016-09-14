package com.lucerotech.aleksandrp.w8monitor.d_base.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class UserLibr extends RealmObject {

    @PrimaryKey
    public String mail;
    public String password;
    public String faceboolId;
    public String birthday;
    public String height;

    //    1 - men, 2 - woman, 3 - children
    public int state;
    //    1 - dark, 2 - light
    public int themeApp;

    public int typeBody;

    public UserLibr() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mMail) {
        mail = mMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String mPassword) {
        password = mPassword;
    }

    public String getFaceboolId() {
        return faceboolId;
    }

    public void setFaceboolId(String mFaceboolId) {
        faceboolId = mFaceboolId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String mBirthday) {
        birthday = mBirthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String mHeight) {
        height = mHeight;
    }

    public int getState() {
        return state;
    }

    public void setState(int mState) {
        state = mState;
    }

    public int getThemeApp() {
        return themeApp;
    }

    public void setThemeApp(int mThemeApp) {
        themeApp = mThemeApp;
    }

    public int getTypeBody() {
        return typeBody;
    }

    public void setTypeBody(int mTypeBody) {
        typeBody = mTypeBody;
    }
}
