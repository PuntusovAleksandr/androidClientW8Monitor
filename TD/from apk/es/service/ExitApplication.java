/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package com.lefu.es.service;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExitApplication
extends Application {
    private static ExitApplication ea;
    private List<Activity> list = new ArrayList();

    private ExitApplication() {
    }

    public static ExitApplication getInstance() {
        if (ea == null) {
            ea = new ExitApplication();
        }
        return ea;
    }

    public void addActivity(Activity activity) {
        this.list.add((Object)activity);
    }

    public void exit(Context context) {
        Iterator iterator = this.list.iterator();
        while (iterator.hasNext()) {
            ((Activity)iterator.next()).finish();
        }
        return;
    }
}

