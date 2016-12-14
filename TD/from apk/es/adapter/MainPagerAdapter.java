/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.support.v4.view.PagerAdapter
 *  android.support.v4.view.ViewPager
 *  android.view.View
 *  android.view.ViewGroup
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Collection
 */
package com.lefu.es.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainPagerAdapter
extends PagerAdapter {
    private ArrayList<View> views = new ArrayList();

    public int addView(View view) {
        return this.addView(view, this.views.size());
    }

    public int addView(View view, int n) {
        this.views.add(n, (Object)view);
        return n;
    }

    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        viewGroup.removeView((View)this.views.get(n));
    }

    public int getCount() {
        return this.views.size();
    }

    public int getItemPosition(Object object) {
        int n = this.views.indexOf(object);
        if (n == -1) {
            n = -2;
        }
        return n;
    }

    public View getView(int n) {
        return (View)this.views.get(n);
    }

    public Object instantiateItem(ViewGroup viewGroup, int n) {
        View view = (View)this.views.get(n);
        viewGroup.addView(view);
        return view;
    }

    public boolean isViewFromObject(View view, Object object) {
        if (view == object) {
            return true;
        }
        return false;
    }

    public void removeAllView(ViewPager viewPager) {
        viewPager.setAdapter(null);
        this.views.removeAll(this.views);
        viewPager.setAdapter((PagerAdapter)this);
    }

    public int removeView(ViewPager viewPager, int n) {
        viewPager.setAdapter(null);
        this.views.remove(n);
        viewPager.setAdapter((PagerAdapter)this);
        return n;
    }

    public int removeView(ViewPager viewPager, View view) {
        return this.removeView(viewPager, this.views.indexOf((Object)view));
    }
}

