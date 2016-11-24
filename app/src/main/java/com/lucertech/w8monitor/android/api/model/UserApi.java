package com.lucertech.w8monitor.android.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksandrP on 16.11.2016.
 */

public class UserApi {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private UserApiData user;


    public UserApi() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String mToken) {
        token = mToken;
    }

    public UserApiData getUser() {
        return user;
    }

    public void setUser(UserApiData mUser) {
        user = mUser;
    }
}
