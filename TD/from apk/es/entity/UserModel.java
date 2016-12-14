/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.entity;

import java.io.Serializable;

public class UserModel
implements Serializable {
    private static final long serialVersionUID = -8295238475381724571L;
    private int ageMonth = 0;
    private int ageYear = 0;
    private float bheigth = 0.0f;
    private String birth;
    private String danwei = "kg";
    private String group;
    private int id;
    private String level;
    private int number;
    private String per_photo;
    private String scaleType;
    private String sex;
    private float targweight;
    private String uniqueID;
    private String userName;

    public UserModel() {
    }

    public UserModel(int n, String string2, String string3, String string4, String string5, float f, int n2, int n3, int n4, String string6, String string7) {
        this.id = n;
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n2;
        this.ageMonth = n3;
        this.number = n4;
        this.scaleType = string6;
        this.uniqueID = string7;
    }

    public UserModel(int n, String string2, String string3, String string4, String string5, float f, int n2, int n3, int n4, String string6, String string7, String string8) {
        this.id = n;
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n2;
        this.ageMonth = n3;
        this.number = n4;
        this.scaleType = string6;
        this.uniqueID = string7;
        this.birth = string8;
    }

    public UserModel(int n, String string2, String string3, String string4, String string5, float f, int n2, int n3, int n4, String string6, String string7, String string8, String string9) {
        this.id = n;
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n2;
        this.ageMonth = n3;
        this.number = n4;
        this.scaleType = string6;
        this.uniqueID = string7;
        this.birth = string8;
        this.per_photo = string9;
    }

    public UserModel(int n, String string2, String string3, String string4, String string5, float f, int n2, int n3, int n4, String string6, String string7, String string8, String string9, float f2) {
        this.id = n;
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n2;
        this.ageMonth = n3;
        this.number = n4;
        this.scaleType = string6;
        this.uniqueID = string7;
        this.birth = string8;
        this.per_photo = string9;
        this.targweight = f2;
    }

    public UserModel(int n, String string2, String string3, String string4, String string5, float f, int n2, int n3, int n4, String string6, String string7, String string8, String string9, float f2, String string10) {
        this.id = n;
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n2;
        this.ageMonth = n3;
        this.number = n4;
        this.scaleType = string6;
        this.uniqueID = string7;
        this.birth = string8;
        this.per_photo = string9;
        this.targweight = f2;
        this.danwei = string10;
    }

    public UserModel(String string2, String string3, String string4, String string5, float f, int n, int n2, int n3, String string6) {
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n;
        this.ageMonth = n2;
        this.number = n3;
        this.scaleType = string6;
    }

    public UserModel(String string2, String string3, String string4, String string5, float f, int n, int n2, int n3, String string6, String string7) {
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n;
        this.ageMonth = n2;
        this.number = n3;
        this.scaleType = string6;
        this.uniqueID = string7;
    }

    public UserModel(String string2, String string3, String string4, String string5, float f, int n, int n2, int n3, String string6, String string7, String string8) {
        this.userName = string2;
        this.group = string3;
        this.sex = string4;
        this.level = string5;
        this.bheigth = f;
        this.ageYear = n;
        this.ageMonth = n2;
        this.number = n3;
        this.scaleType = string6;
        this.uniqueID = string7;
        this.birth = string8;
    }

    public int getAgeMonth() {
        return this.ageMonth;
    }

    public int getAgeYear() {
        return this.ageYear;
    }

    public float getBheigth() {
        return this.bheigth;
    }

    public String getBirth() {
        return this.birth;
    }

    public String getDanwei() {
        return this.danwei;
    }

    public String getGroup() {
        return this.group;
    }

    public int getId() {
        return this.id;
    }

    public String getLevel() {
        return this.level;
    }

    public int getNumber() {
        return this.number;
    }

    public String getPer_photo() {
        return this.per_photo;
    }

    public String getScaleType() {
        return this.scaleType;
    }

    public String getSex() {
        return this.sex;
    }

    public float getTargweight() {
        return this.targweight;
    }

    public String getUniqueID() {
        if (this.uniqueID != null && this.uniqueID.length() > 0) {
            return this.uniqueID;
        }
        return "";
    }

    public String getUserName() {
        return this.userName;
    }

    public void setAgeMonth(int n) {
        this.ageMonth = n;
    }

    public void setAgeYear(int n) {
        this.ageYear = n;
    }

    public void setBheigth(float f) {
        this.bheigth = f;
    }

    public void setBirth(String string2) {
        this.birth = string2;
    }

    public void setDanwei(String string2) {
        this.danwei = string2;
    }

    public void setGroup(String string2) {
        this.group = string2;
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setLevel(String string2) {
        this.level = string2;
    }

    public void setNumber(int n) {
        this.number = n;
    }

    public void setPer_photo(String string2) {
        this.per_photo = string2;
    }

    public void setScaleType(String string2) {
        this.scaleType = string2;
    }

    public void setSex(String string2) {
        this.sex = string2;
    }

    public void setTargweight(float f) {
        this.targweight = f;
    }

    public void setUniqueID(String string2) {
        this.uniqueID = string2;
    }

    public void setUserName(String string2) {
        this.userName = string2;
    }
}

