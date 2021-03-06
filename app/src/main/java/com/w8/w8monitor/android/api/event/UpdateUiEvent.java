package com.w8.w8monitor.android.api.event;

/**
 * Created by AleksandrP on 17.11.2016.
 */

public class UpdateUiEvent<T> {

    public static final int LOGIN = 1;
    public static final int LOGIN_SOCIAL = 2;
    public static final int REGISTER = 3;
    public static final int PROFILE = 4;
    public static final int MEASUREMENTS = 5;
    public static final int MEASUREMENTS_MASS = 6;
    public static final int UPDATE_PROFILE = 7;
    public static final int ALARM_UPDATE = 8;
    public static final int CHANGE_PASS = 9;
    public static final int USER_SUNS = 10;
    public static final int MEASUREMENTS_SUNS = 11;
    public static final int ALL_MEASUREMENTS = 12;
    public static final int MEASUREMENTS_MASS_UPDATE = 13;
    public static final int RESET_PASSWORD = 14;
    public static final int RESPONSE_SUPPORT_API = 15;

    private int id;

    private T data;
    private boolean sucess;

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public T getData() {
        return data;
    }

    public void setData(T mData) {
        data = mData;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean mSucess) {
        sucess = mSucess;
    }
}
