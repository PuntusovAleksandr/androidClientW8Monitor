/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.UUID
 */
package com.lefu.es.constant;

import java.util.UUID;

public class BTConstant {
    public static final UUID MY_UUID_INSECURE;
    public static final UUID MY_UUID_SECURE;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE;

    static {
        MY_UUID_SECURE = UUID.fromString((String)"00001101-0000-1000-8000-00805F9B34FB");
        MY_UUID_INSECURE = UUID.fromString((String)"00001101-0000-1000-8000-00805F9B34FB");
    }
}

