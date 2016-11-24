package  com.lucertech.w8monitor.android.fragments.help.fragments;

import com.lucertech.w8monitor.android.R;
import com.lucertech.w8monitor.android.activity.interfaces.views.HelpView;

import static com.lucertech.w8monitor.android.fragments.FragmentMapker.SCREEN_3;
import static com.lucertech.w8monitor.android.fragments.FragmentMapker.SCREEN_5;

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
