package com.w8.w8monitor.android.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksandrP on 16.11.2016.
 */

public class ProfileApi {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("activity_type")
    @Expose
    private int activity_type;

    @SerializedName("height")
    @Expose
    private int height;

    @SerializedName("gender")
    @Expose
    private int gender;

    @SerializedName("age")
    @Expose
    private int birthday;


    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("number")
    @Expose
    private int number;

    @SerializedName("is_current")
    @Expose
    private boolean is_current;

    @SerializedName("target_weight")
    @Expose
    private int target_weight;

    @SerializedName("measurements_count")
    @Expose
    private int measurements_count;


    public ProfileApi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int mUser_id) {
        user_id = mUser_id;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int mActivity_type) {
        activity_type = mActivity_type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int mHeight) {
        height = mHeight;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int mGender) {
        gender = mGender;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int mBirthday) {
        birthday = mBirthday;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int mNumber) {
        number = mNumber;
    }

    public boolean is_current() {
        return is_current;
    }

    public void setIs_current(boolean mIs_current) {
        is_current = mIs_current;
    }

    public int getTarget_weight() {
        return target_weight;
    }

    public void setTarget_weight(int mTarget_weight) {
        target_weight = mTarget_weight;
    }

    public int getMeasurements_count() {
        return measurements_count;
    }

    public void setMeasurements_count(int mMeasurements_count) {
        measurements_count = mMeasurements_count;
    }
}
