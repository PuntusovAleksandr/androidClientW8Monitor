package com.lucerotech.aleksandrp.w8monitor.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksandrP on 16.11.2016.
 */

public class Measurement {


    @SerializedName("profile_id")
    @Expose
    private int profile_id;

    @SerializedName("bmi")
    @Expose
    private String bmi;

    @SerializedName("body_water")
    @Expose
    private String body_water;

    @SerializedName("bone_mass")
    @Expose
    private String bone_mass;

    @SerializedName("calories")
    @Expose
    private String calories;

    @SerializedName("fat")
    @Expose
    private String fat;

    @SerializedName("fat_level")
    @Expose
    private String fat_level;

    @SerializedName("muscle_mass")
    @Expose
    private String muscle_mass;

    @SerializedName("float_weight")
    @Expose
    private String float_weight;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("synced")
    @Expose
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

    public String getMuscle_mass() {
        return muscle_mass;
    }

    public void setMuscle_mass(String mMuscle_mass) {
        muscle_mass = mMuscle_mass;
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

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int mProfile_id) {
        profile_id = mProfile_id;
    }
}
