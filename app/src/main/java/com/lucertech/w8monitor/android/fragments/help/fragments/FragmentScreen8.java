package  com.lucertech.w8monitor.android.fragments.help.fragments;

import android.view.View;

import  com.lucertech.w8monitor.android.R;
import  com.lucertech.w8monitor.android.activity.interfaces.views.HelpView;


import static com.lucertech.w8monitor.android.fragments.FragmentMapker.SCREEN_2;


/**
 * Created by AleksandrP on 27.10.2016.
 */

public class FragmentScreen8 extends FragmentScreenBase {

    private HelpView mHelpView;

    public FragmentScreen8() {
    }

    public FragmentScreen8(HelpView mHelpView) {
        this.mHelpView = mHelpView;
    }

    @Override
    public void setTextsInView(String title, String description) {
        tv_title_help.setVisibility(View.GONE);
        title = "";
        description = getString(R.string.description_screen8);
        super.setTextsInView(title, description);
    }


    //    =====================================
//            onClick
    //    =====================================

    @Override
    public void clickNextFragment() {
        mHelpView.finishHelp();
    }

    @Override
    public void clickBackFragment() {
        mHelpView.goToBackFragment(SCREEN_2);
    }
}
