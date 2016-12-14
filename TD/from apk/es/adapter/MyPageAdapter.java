/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.support.v4.view.PagerAdapter
 *  android.support.v4.view.ViewPager
 *  android.util.Log
 *  android.view.View
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.lefu.es.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class MyPageAdapter
extends PagerAdapter {
    private ArrayList<View> listViews;

    public MyPageAdapter(ArrayList<View> arrayList) {
        this.listViews = arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void destroyItem(View view, int n, Object object) {
        if (this.listViews == null || this.listViews.size() <= n || this.listViews.get(n) == null) {
            return;
        }
        ((ViewPager)view).removeView((View)this.listViews.get(n));
    }

    public void finishUpdate(View view) {
    }

    public int getCount() {
        if (this.listViews == null) {
            return 0;
        }
        return this.listViews.size();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object instantiateItem(View view, int n) {
        try {
            if (this.listViews != null && this.listViews.size() > 0 && n < this.listViews.size()) {
                ((ViewPager)view).addView((View)this.listViews.get(n), 0);
            }
        }
        catch (Exception var3_3) {
            Log.e((String)"zhou", (String)("exception\ufffd\ufffd" + var3_3.getMessage()));
        }
        if (this.listViews != null && this.listViews.size() > 0 && n < this.listViews.size()) {
            return this.listViews.get(n);
        }
        return null;
    }

    public boolean isViewFromObject(View view, Object object) {
        if (view == object) {
            return true;
        }
        return false;
    }

    public void setListViews(ArrayList<View> arrayList) {
        if (this.listViews != null && this.listViews.size() > 0) {
            this.listViews.clear();
        }
        this.listViews = arrayList;
    }
}

