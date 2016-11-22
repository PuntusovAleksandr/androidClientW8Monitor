package com.lucerotech.aleksandrp.w8monitor.d_base.model;

import io.realm.RealmObject;

/**
 * Created by AleksandrP on 15.10.2016.
 */

public class ParamsBody extends RealmObject {

    // user name for search in db
    private String userName_id;
    // Вес
    private float weight;
    // Скелет
    private float body;
    // Жир
    private float fat;
    // Мускул
    private float muscle;
    // Вода
    private float water;
    // Висцеральный жир
    private float visceralFat;
    // BMR
    private float emr;
    // Тело Возраст
    private float bodyAge;
    // расчетное Тело Возраст:
    private float countBodyAge;
    // time data in millsec
    private long date_time;

    public int profileBLE;

    private int profile_id;

    private float bmi;

    private int id;

    private boolean synced;

    public ParamsBody() {
    }

    public int getProfileBLE() {
        return profileBLE;
    }

    public void setProfileBLE(int mProfileBLE) {
        profileBLE = mProfileBLE;
    }

    public String getUserName_id() {
        return userName_id;
    }

    public void setUserName_id(String mUserName_id) {
        userName_id = mUserName_id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float mWeight) {
        weight = mWeight;
    }

    public float getBody() {
        return body;
    }

    public void setBody(float mBody) {
        body = mBody;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float mFat) {
        fat = mFat;
    }

    public float getMuscle() {
        return muscle;
    }

    public void setMuscle(float mMuscle) {
        muscle = mMuscle;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float mWater) {
        water = mWater;
    }

    public float getVisceralFat() {
        return visceralFat;
    }

    public void setVisceralFat(float mVisceralFat) {
        visceralFat = mVisceralFat;
    }

    public float getEmr() {
        return emr;
    }

    public void setEmr(float mEmr) {
        emr = mEmr;
    }

    public float getBodyAge() {
        return bodyAge;
    }

    public void setBodyAge(float mBodyAge) {
        bodyAge = mBodyAge;
    }

    public float getCountBodyAge() {
        return countBodyAge;
    }

    public void setCountBodyAge(float mCountBodyAge) {
        countBodyAge = mCountBodyAge;
    }

    public long getDate_time() {
        return date_time;
    }

    public void setDate_time(long mDate_time) {
        date_time = mDate_time;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float mBmi) {
        bmi = mBmi;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int mProfile_id) {
        profile_id = mProfile_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean mSynced) {
        synced = mSynced;
    }
}
