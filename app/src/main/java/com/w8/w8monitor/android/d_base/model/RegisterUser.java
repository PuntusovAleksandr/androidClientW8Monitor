package com.w8.w8monitor.android.d_base.model;

/**
 * Created by AleksandrP on 19.12.2016.
 */

public class RegisterUser {


    private int gender;
    private int activityLevel;
    private long age;
    private int height;
    private int targetWeight;

    public RegisterUser() {
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int mGender) {
        gender = mGender;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(int mActivityLevel) {
        activityLevel = mActivityLevel;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long mAge) {
        age = mAge;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int mHeight) {
        height = mHeight;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int mTargetWeight) {
        targetWeight = mTargetWeight;
    }
}
