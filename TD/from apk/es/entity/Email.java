/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.entity;

public class Email {
    private String email;
    private int id;
    private String name;

    public Email(int n, String string2, String string3) {
        this.id = n;
        this.name = string2;
        this.email = string3;
    }

    public Email(String string2, String string3) {
        this.name = string2;
        this.email = string3;
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String string2) {
        this.email = string2;
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setName(String string2) {
        this.name = string2;
    }
}

