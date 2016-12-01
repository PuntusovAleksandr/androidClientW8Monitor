package  com.w8.w8monitor.android.fragments.help.fragments;

import  com.w8.w8monitor.android.R;
import  com.w8.w8monitor.android.activity.interfaces.views.HelpView;

import static com.w8.w8monitor.android.fragments.FragmentMapker.SCREEN_4;
import static com.w8.w8monitor.android.fragments.FragmentMapker.SCREEN_6;

/**
 * Created by AleksandrP on 27.10.2016.
 */

public class FragmentScreen5 extends FragmentScreenBase {

    private HelpView mHelpView;

    public FragmentScreen5(HelpView mHelpView) {
        this.mHelpView = mHelpView;
    }

    @Override
    public void setTextsInView(String title, String description) {

//        title = getString(R.string.introduction5);
        description = getString(R.string.description_screen5);
        super.setTextsInView(title, description);
    }


    //    =====================================
//            onClick
    //    =====================================

    @Override
    public void clickNextFragment() {
        mHelpView.goToNextFragment(SCREEN_6);
    }

    @Override
    public void clickBackFragment() {
        mHelpView.goToBackFragment(SCREEN_4);
    }
}
