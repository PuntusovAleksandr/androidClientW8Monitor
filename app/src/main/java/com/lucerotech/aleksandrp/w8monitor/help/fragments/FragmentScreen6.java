package com.lucerotech.aleksandrp.w8monitor.help.fragments;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.help.HelpView;

import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_5;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_7;

/**
 * Created by AleksandrP on 27.10.2016.
 */

public class FragmentScreen6 extends FragmentScreenBase {

    private HelpView mHelpView;

    public FragmentScreen6(HelpView mHelpView) {
        this.mHelpView = mHelpView;
    }

    @Override
    public void setTextsInView(String title, String description) {

//        title = getString(R.string.introduction6);
        description = getString(R.string.description_screen6);
        super.setTextsInView(title, description);
    }


    //    =====================================
//            onClick
    //    =====================================

    @Override
    public void clickNextFragment() {
        mHelpView.goToNextFragment(SCREEN_7);
    }

    @Override
    public void clickBackFragment() {
        mHelpView.goToBackFragment(SCREEN_5);
    }
}
