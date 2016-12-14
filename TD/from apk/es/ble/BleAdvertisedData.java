/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.UUID
 */
package com.lefu.es.ble;

import java.util.List;
import java.util.UUID;

public class BleAdvertisedData {
    private String mName;
    private List<UUID> mUuids;

    public BleAdvertisedData(List<UUID> list, String string2) {
        this.mUuids = list;
        this.mName = string2;
    }

    public String getName() {
        return this.mName;
    }

    public List<UUID> getUuids() {
        return this.mUuids;
    }
}

