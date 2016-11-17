package com.lucerotech.aleksandrp.w8monitor.api.model;

/**
 * Created by AleksandrP on 16.11.2016.
 */

public class Measurement {

    private String bmi;
    private String body_water;
    private String bone_mass;
    private String calories;
    private String fat;
    private String fat_level;
    private String muscule_mass;
    private String float_weight;

    private String created_at;
    private String updated_at;
    private int id;
    private boolean synced;

    public Measurement() {
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String mBmi) {
        bmi = mBmi;
    }

    public String getBody_water() {
        return body_water;
    }

    public void setBody_water(String mBody_water) {
        body_water = mBody_water;
    }

    public String getBone_mass() {
        return bone_mass;
    }

    public void setBone_mass(String mBone_mass) {
        bone_mass = mBone_mass;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String mCalories) {
        calories = mCalories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String mFat) {
        fat = mFat;
    }

    public String getFat_level() {
        return fat_level;
    }

    public void setFat_level(String mFat_level) {
        fat_level = mFat_level;
    }

    public String getMuscule_mass() {
        return muscule_mass;
    }

    public void setMuscule_mass(String mMuscule_mass) {
        muscule_mass = mMuscule_mass;
    }

    public String getFloat_weight() {
        return float_weight;
    }

    public void setFloat_weight(String mFloat_weight) {
        float_weight = mFloat_weight;
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
