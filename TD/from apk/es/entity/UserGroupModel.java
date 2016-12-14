/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.entity;

public class UserGroupModel {
    public static UserGroupModel group0 = null;
    public static UserGroupModel group1 = null;
    public static UserGroupModel group2 = null;
    public static UserGroupModel group3 = null;
    public static UserGroupModel group4 = null;
    public static UserGroupModel group5 = null;
    public static UserGroupModel group6 = null;
    public static UserGroupModel group7 = null;
    public static UserGroupModel group8 = null;
    private String groupName;
    private String groupNumber;
    private int id;

    static {
        group0 = new UserGroupModel("P0", "P0");
        group1 = new UserGroupModel("P1", "P1");
        group2 = new UserGroupModel("P2", "P2");
        group3 = new UserGroupModel("P3", "P3");
        group4 = new UserGroupModel("P4", "P4");
        group5 = new UserGroupModel("P5", "P5");
        group6 = new UserGroupModel("P6", "P6");
        group7 = new UserGroupModel("P7", "P7");
        group8 = new UserGroupModel("P8", "P8");
    }

    public UserGroupModel() {
    }

    public UserGroupModel(String string2, String string3) {
        this.groupName = string3;
        this.groupNumber = string2;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getGroupNumber() {
        return this.groupNumber;
    }

    public int getId() {
        return this.id;
    }

    public void setGroupName(String string2) {
        this.groupName = string2;
    }

    public void setGroupNumber(String string2) {
        this.groupNumber = string2;
    }

    public void setId(int n) {
        this.id = n;
    }
}

