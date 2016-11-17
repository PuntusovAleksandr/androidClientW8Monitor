package com.lucerotech.aleksandrp.w8monitor.api.model;

/**
 * Created by AleksandrP on 16.11.2016.
 */

public class UserApi {

    private String token;
    private int id;
    private String email;
    private String created_at;
    private String updated_at;

    public UserApi() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String mToken) {
        token = mToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mEmail) {
        email = mEmail;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String mCreated_at) {
        created_at = mCreated_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String mUpdated_at) {
        updated_at = mUpdated_at;
    }
}
