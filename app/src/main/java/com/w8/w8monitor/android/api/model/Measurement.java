package com.w8.w8monitor.android.api.model;

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
    private float bmi;

    @SerializedName("body_water")
    @Expose
    private float body_water;

    @SerializedName("bone_mass")
    @Expose
    private float bone_mass;

    @SerializedName("calories")
    @Expose
    private int calories;

    @SerializedName("fat")
    @Expose
    private float fat;

    @SerializedName("fat_level")
    @Expose
    private int fat_level;

    @SerializedName("muscle_mass")
    @Expose
    private float muscle_mass;

    @SerializedName("float_weight")
    @Expose
    private float float_weight;

    @SerializedName("created_at")
    @Expose
    private long created_at;

    @SerializedName("updated_at")
    @Expose
    private long updated_at;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("synced")
    @Expose
    private boolean synced;

    public Measurement() {
    }


    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int mProfile_id) {
        profile_id = mProfile_id;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float mBmi) {
        bmi = mBmi;
    }

    public float getBody_water() {
        return body_water;
    }

    public void setBody_water(float mBody_water) {
        body_water = mBody_water;
    }

    public float getBone_mass() {
        return bone_mass;
    }

    public void setBone_mass(float mBone_mass) {
        bone_mass = mBone_mass;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int mCalories) {
        calories = mCalories;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float mFat) {
        fat = mFat;
    }

    public int getFat_level() {
        return fat_level;
    }

    public void setFat_level(int mFat_level) {
        fat_level = mFat_level;
    }

    public float getMuscle_mass() {
        return muscle_mass;
    }

    public void setMuscle_mass(float mMuscle_mass) {
        muscle_mass = mMuscle_mass;
    }

    public float getFloat_weight() {
        return float_weight;
    }

    public void setFloat_weight(float mFloat_weight) {
        float_weight = mFloat_weight;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long mCreated_at) {
        created_at = mCreated_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long mUpdated_at) {
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
