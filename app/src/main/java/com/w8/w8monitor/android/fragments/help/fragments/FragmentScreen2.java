package com.w8.w8monitor.android.fragments.help.fragments;

import android.graphics.Color;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.interfaces.views.HelpView;

import static com.w8.w8monitor.android.fragments.FragmentMapker.SCREEN_1;
import static com.w8.w8monitor.android.fragments.FragmentMapker.SCREEN_8;


/**
 * Created by AleksandrP on 27.10.2016.
 */

public class FragmentScreen2 extends FragmentScreenBase {


    private HelpView mHelpView;

    public FragmentScreen2() {
    }

    public FragmentScreen2(HelpView mHelpView) {
        this.mHelpView = mHelpView;
    }


    @Override
    public void setTextsInView(String title, String description) {
        tv_title_help.setTextColor(Color.RED);
        title = getString(R.string.introduction2);
        description = getString(R.string.description_screen2);
        super.setTextsInView(title, description);
    }


    //    =====================================
//            onClick
    //    =====================================

    @Override
    public void clickNextFragment() {
        mHelpView.goToNextFragment(SCREEN_8);
    }

    @Override
    public void clickBackFragment() {
        mHelpView.goToBackFragment(SCREEN_1);
    }
}
