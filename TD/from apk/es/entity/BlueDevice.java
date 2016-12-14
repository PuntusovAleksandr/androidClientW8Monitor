/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.entity;

public class BlueDevice {
    private String deviceName;
    private String uuid;

    public BlueDevice() {
    }

    public BlueDevice(String string2, String string3) {
        this.uuid = string2;
        this.deviceName = string3;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setDeviceName(String string2) {
        this.deviceName = string2;
    }

    public void setUuid(String string2) {
        this.uuid = string2;
    }
}

