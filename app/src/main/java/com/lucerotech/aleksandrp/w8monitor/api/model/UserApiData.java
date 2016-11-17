package com.lucerotech.aleksandrp.w8monitor.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AleksandrP on 17.11.2016.
 */

public class UserApiData {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("profiles")
    @Expose
    private List<ProfileApi> mProfileApis;

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

    public List<ProfileApi> getProfileApis() {
        return mProfileApis;
    }

    public void setProfileApis(List<ProfileApi> mProfileApis) {
        this.mProfileApis = mProfileApis;
    }
}
