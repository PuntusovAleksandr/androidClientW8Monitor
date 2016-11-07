package com.lucerotech.aleksandrp.w8monitor.help.fragments;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.help.HelpView;

import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_3;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_5;

/**
 * Created by AleksandrP on 27.10.2016.
 */

public class FragmentScreen4 extends FragmentScreenBase {

    private HelpView mHelpView;

    public FragmentScreen4(HelpView mHelpView) {
        this.mHelpView = mHelpView;
    }

    @Override
    public void setTextsInView(String title, String description) {

//        title = getString(R.string.introduction4);
        description = getString(R.string.description_screen4);
        super.setTextsInView(title, description);
    }


    //    =====================================
//            onClick
    //    =====================================

    @Override
    public void clickNextFragment() {
        mHelpView.goToNextFragment(SCREEN_5);
    }

    @Override
    public void clickBackFragment() {
        mHelpView.goToBackFragment(SCREEN_3);
    }
}
