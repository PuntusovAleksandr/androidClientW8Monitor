package com.lucerotech.aleksandrp.w8monitor.d_base.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 17.11.2016.
 */

public class Profile extends RealmObject {

    @PrimaryKey
    private int id;
    private int user_id;
    //  1 - woman 2 - man, 3 - children
    private int activity_type;
    private int height;
    //    1 - men, 2 - woman
    private int gender;
    private int birthday;
    private String created_at;
    private String updated_at;
    private int number;


    public Profile() {
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
}
