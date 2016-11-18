package com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments;

import android.view.View;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.HelpView;

import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_2;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.KEY_FROM_SPLASH;

/**
 * Created by AleksandrP on 27.10.2016.
 */

public class FragmentScreen1 extends FragmentScreenBase {

    private HelpView mHelpView;
    private int mExttraFrom;

    public FragmentScreen1() {
    }

    public FragmentScreen1(HelpView mHelpView, int mExtraFrom) {
        this.mHelpView = mHelpView;
        this.mExttraFrom = mExtraFrom;
    }

    @Override
    public void setTextsInView(String title, String description) {
        if (mExttraFrom == KEY_FROM_SPLASH) {
            iv_toolbar_back_press.setVisibility(View.INVISIBLE);
        }
        title = getString(R.string.introduction1);
        description = getString(R.string.description_screen1);
        super.setTextsInView(title, description);
    }

    //    =====================================
//            onClick
    //    =====================================

    @Override
    public void clickNextFragment() {
        mHelpView.goToNextFragment(SCREEN_2);
    }

    @Override
    public void clickBackFragment() {
        mHelpView.goToBackMainActivity();
    }
}
