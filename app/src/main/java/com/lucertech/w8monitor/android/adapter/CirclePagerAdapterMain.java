package com.lucertech.w8monitor.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lucertech.w8monitor.android.App;
import com.lucertech.w8monitor.android.fragments.main.MyFragment;

public class CirclePagerAdapterMain extends FragmentStatePagerAdapter {

//    public final static float BIG_SCALE = 1.0f;
//    public final static float SMALL_SCALE = 0.7f;
//    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
//
////    private MyLinearLayout cur = null;
////    private MyLinearLayout next = null;
//    private MainActivity context;
//    private FragmentManager fm;
//    private float scale;

    private int countPage;
    private int position;

    public CirclePagerAdapterMain(FragmentManager fm, int countPage) {
        super(fm);
        this.countPage = countPage;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public Fragment getItem(int position) {
        this.position = position;
//        // make the first pager bigger than others
//        if (position == FIRST_PAGE)
//            scale = BIG_SCALE;
//        else
//            scale = SMALL_SCALE;

//        position = position % PAGES;
        return new MyFragment(App.getContext(), position, 0, countPage);
    }

    @Override
    public int getCount() {
        return countPage;
    }


//    @Override
//    public void transformPage(View page, float position) {
////        MyLinearLayout myLinearLayout = (MyLinearLayout) page.findViewById(R.id.root);
////        float scale = BIG_SCALE;
////        if (position > 0) {
////            scale = scale - position * DIFF_SCALE;
////        } else {
////            scale = scale + position * DIFF_SCALE;
////        }
////        if (scale < 0) scale = 0;
////        myLinearLayout.setScaleBoth(scale);
//    }


    public void setPosition(int mPosition) {
        this.position = position;
        notifyDataSetChanged();
    }
}
