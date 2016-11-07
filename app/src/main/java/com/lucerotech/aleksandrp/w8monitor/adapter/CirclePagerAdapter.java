package com.lucerotech.aleksandrp.w8monitor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CirclePagerAdapter extends FragmentPagerAdapter {

    private int countPage = 5;

    public CirclePagerAdapter(FragmentManager fm, int countPage) {
        super(fm);
        this.countPage = countPage;
    }

    @Override
    public Fragment getItem(int position) {
        return new Fragment();
    }

    @Override
    public int getCount() {
        return countPage;
    }

}
