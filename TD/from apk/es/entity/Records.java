/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.entity;

import com.lefu.es.entity.UserModel;

import java.io.Serializable;

public class Records
implements Serializable {
    private static final long serialVersionUID = 1781102348190850847L;
    private float bodyAge;
    private String compareRecord;
    private int id;
    public boolean isNull = false;
    private String level;
    private String rPhoto;
    private float rbmi = 0.0f;
    private float rbmr;
    private float rbodyfat;
    private float rbodywater;
    private float rbone;
    private String recordTime;
    private float rmuscle;
    private float rvisceralfat;
    private float rweight;
    private String sAge;
    private String sHeight;
    private String sbmi = "0";
    private String sbmr;
    private String sbodyfat;
    private String sbodywater;
    private String sbone;
    private String scaleType;
    private String sex;
    private String smuscle;
    private String svisceralfat;
    private String sweight;
    private String ugroup;
    private int unitType;
    private int useId;
    private UserModel user;

    public Records() {
    }

    public Records(int n, int n2, String string2, String string3, String string4, String string5, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.id = n;
        this.recordTime = string4;
        this.useId = n2;
        this.compareRecord = string5;
        this.scaleType = string2;
        this.ugroup = string3;
        this.rbmi = f2;
        this.rbmr = f8;
        this.rbodyfat = f4;
        this.rbodywater = f6;
        this.rbone = f3;
        this.rmuscle = f5;
        this.rvisceralfat = f7;
        this.rweight = f;
    }

    public Records(int n, int n2, String string2, String string3, String string4, String string5, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.id = n;
        this.recordTime = string4;
        this.useId = n2;
        this.compareRecord = string5;
        this.scaleType = string2;
        this.ugroup = string3;
        this.rbmi = f2;
        this.rbmr = f8;
        this.rbodyfat = f4;
        this.rbodywater = f6;
        this.rbone = f3;
        this.rmuscle = f5;
        this.rvisceralfat = f7;
        this.rweight = f;
        this.bodyAge = f9;
    }

    public Records(int n, String string2, String string3) {
        this.isNull = true;
        this.useId = n;
        this.scaleType = string2;
        this.ugroup = string3;
    }

    public Records(int n, String string2, String string3, String string4, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.useId = n;
        this.compareRecord = string4;
        this.scaleType = string2;
        this.ugroup = string3;
        this.rbmi = f2;
        this.rbmr = f8;
        this.rbodyfat = f4;
        this.rbodywater = f6;
        this.rbone = f3;
        this.rmuscle = f5;
        this.rvisceralfat = f7;
        this.rweight = f;
    }

    public Records(int n, String string2, String string3, String string4, String string5, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.recordTime = string4;
        this.useId = n;
        this.compareRecord = string5;
        this.scaleType = string2;
        this.ugroup = string3;
        this.rbmi = f2;
        this.rbmr = f8;
        this.rbodyfat = f4;
        this.rbodywater = f6;
        this.rbone = f3;
        this.rmuscle = f5;
        this.rvisceralfat = f7;
        this.rweight = f;
    }

    public Records(int n, String string2, String string3, String string4, String string5, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.recordTime = string4;
        this.useId = n;
        this.compareRecord = string5;
        this.scaleType = string2;
        this.ugroup = string3;
        this.rbmi = f2;
        this.rbmr = f8;
        this.rbodyfat = f4;
        this.rbodywater = f6;
        this.rbone = f3;
        this.rmuscle = f5;
        this.rvisceralfat = f7;
        this.rweight = f;
        this.bodyAge = f9;
    }

    public float getBodyAge() {
        return this.bodyAge;
    }

    public String getCompareRecord() {
        return this.compareRecord;
    }

    public int getId() {
        return this.id;
    }

    public String getLevel() {
        return this.level;
    }

    public float getRbmi() {
        return this.rbmi;
    }

    public float getRbmr() {
        return this.rbmr;
    }

    public float getRbodyfat() {
        return this.rbodyfat;
    }

    public float getRbodywater() {
        return this.rbodywater;
    }

    public float getRbone() {
        return this.rbone;
    }

    public String getRecordTime() {
        return this.recordTime;
    }

    public float getRmuscle() {
        return this.rmuscle;
    }

    public String getRphoto() {
        return this.rPhoto;
    }

    public float getRvisceralfat() {
        return this.rvisceralfat;
    }

    public float getRweight() {
        return this.rweight;
    }

    public String getSbmi() {
        return this.sbmi;
    }

    public String getSbmr() {
        return this.sbmr;
    }

    public String getSbodyfat() {
        return this.sbodyfat;
    }

    public String getSbodywater() {
        return this.sbodywater;
    }

    public String getSbone() {
        return this.sbone;
    }

    public String getScaleType() {
        return this.scaleType;
    }

    public String getSex() {
        return this.sex;
    }

    public String getSmuscle() {
        return this.smuscle;
    }

    public String getSvisceralfat() {
        return this.svisceralfat;
    }

    public String getSweight() {
        return this.sweight;
    }

    public String getUgroup() {
        return this.ugroup;
    }

    public int getUnitType() {
        return this.unitType;
    }

    public int getUseId() {
        return this.useId;
    }

    public UserModel getUser() {
        return this.user;
    }

    public String getsAge() {
        return this.sAge;
    }

    public String getsHeight() {
        return this.sHeight;
    }

    public void setBodyAge(float f) {
        this.bodyAge = f;
    }

    public void setCompareRecord(String string2) {
        this.compareRecord = string2;
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setLevel(String string2) {
        this.level = string2;
    }

    public void setRbmi(float f) {
        this.rbmi = f;
    }

    public void setRbmr(float f) {
        this.rbmr = f;
    }

    public void setRbodyfat(float f) {
        this.rbodyfat = f;
    }

    public void setRbodywater(float f) {
        this.rbodywater = f;
    }

    public void setRbone(float f) {
        this.rbone = f;
    }

    public void setRecordTime(String string2) {
        this.recordTime = string2;
    }

    public void setRmuscle(float f) {
        this.rmuscle = f;
    }

    public void setRphoto(String string2) {
        this.rPhoto = string2;
    }

    public void setRvisceralfat(float f) {
        this.rvisceralfat = f;
    }

    public void setRweight(float f) {
        this.rweight = f;
    }

    public void setSbmi(String string2) {
        this.sbmi = string2;
    }

    public void setSbmr(String string2) {
        this.sbmr = string2;
    }

    public void setSbodyfat(String string2) {
        this.sbodyfat = string2;
    }

    public void setSbodywater(String string2) {
        this.sbodywater = string2;
    }

    public void setSbone(String string2) {
        this.sbone = string2;
    }

    public void setScaleType(String string2) {
        this.scaleType = string2;
    }

    public void setSex(String string2) {
        this.sex = string2;
    }

    public void setSmuscle(String string2) {
        this.smuscle = string2;
    }

    public void setSvisceralfat(String string2) {
        this.svisceralfat = string2;
    }

    public void setSweight(String string2) {
        this.sweight = string2;
    }

    public void setUgroup(String string2) {
        this.ugroup = string2;
    }

    public void setUnitType(int n) {
        this.unitType = n;
    }

    public void setUseId(int n) {
        this.useId = n;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public void setsAge(String string2) {
        this.sAge = string2;
    }

    public void setsHeight(String string2) {
        this.sHeight = string2;
    }

    public String toString() {
        return "Records [scaleType=" + this.scaleType + ", ugroup=" + this.ugroup + ", recordTime=" + this.recordTime + ", compareRecord=" + this.compareRecord + ", rweight=" + this.rweight + ", rbmi=" + this.rbmi + ", rbone=" + this.rbone + ", rbodyfat=" + this.rbodyfat + ", rmuscle=" + this.rmuscle + ", rbodywater=" + this.rbodywater + ", rvisceralfat=" + this.rvisceralfat + ", rbmr=" + this.rbmr + ", level=" + this.level + ", sex=" + this.sex + ", sweight=" + this.sweight + ", sbmi=" + this.sbmi + ", sbone=" + this.sbone + ", sbodyfat=" + this.sbodyfat + ", smuscle=" + this.smuscle + ", sbodywater=" + this.sbodywater + ", svisceralfat=" + this.svisceralfat + ", sbmr=" + this.sbmr + ", sHeight=" + this.sHeight + ", sAge=" + this.sAge + "]";
    }
}

