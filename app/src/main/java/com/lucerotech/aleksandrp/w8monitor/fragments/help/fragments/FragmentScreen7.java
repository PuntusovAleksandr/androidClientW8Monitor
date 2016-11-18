package com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.HelpView;

import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_6;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_8;

/**
 * Created by AleksandrP on 27.10.2016.
 */

public class FragmentScreen7 extends FragmentScreenBase {

    private HelpView mHelpView;

    public FragmentScreen7(HelpView mHelpView) {
        this.mHelpView = mHelpView;
    }

    @Override
    public void setTextsInView(String title, String description) {

//        title = getString(R.string.introduction7);
        description = getString(R.string.description_screen7);
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
        mHelpView.goToBackFragment(SCREEN_6);
    }
}
