package  com.lucertech.w8monitor.android.fragments.help.fragments;

import android.view.View;

import com.lucertech.w8monitor.android.R;
import com.lucertech.w8monitor.android.activity.interfaces.views.HelpView;

import static com.lucertech.w8monitor.android.fragments.FragmentMapker.SCREEN_2;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.KEY_FROM_SPLASH;

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
