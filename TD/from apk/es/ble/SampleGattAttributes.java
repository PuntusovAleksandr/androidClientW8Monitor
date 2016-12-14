/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.lefu.es.ble;

import java.util.HashMap;

public class SampleGattAttributes {
    public static String CLIENT_CHARACTERISTIC_CONFIG;
    public static String HEART_RATE_MEASUREMENT;
    private static HashMap<String, String> attributes;

    static {
        attributes = new HashMap();
        HEART_RATE_MEASUREMENT = "0000C004-0000-1000-8000-00805f9b34fb";
        CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
        attributes.put((Object)"0000fff00000-1000-8000-00805f9b34fb", (Object)"Heart Rate Service");
        attributes.put((Object)"0000180a-0000-1000-8000-00805f9b34fb", (Object)"Device Information Service");
        attributes.put((Object)HEART_RATE_MEASUREMENT, (Object)"Heart Rate Measurement");
        attributes.put((Object)"00002a29-0000-1000-8000-00805f9b34fb", (Object)"Manufacturer Name String");
    }

    public static String lookup(String string2, String string3) {
        String string4 = (String)attributes.get((Object)string2);
        if (string4 == null) {
            return string3;
        }
        return string4;
    }
}

