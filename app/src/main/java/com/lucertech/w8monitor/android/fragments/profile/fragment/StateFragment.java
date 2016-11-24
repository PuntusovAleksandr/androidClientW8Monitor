package com.lucertech.w8monitor.android.fragments.profile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucertech.w8monitor.android.R;
import com.lucertech.w8monitor.android.activity.ProfileActivity;
import com.lucertech.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.lucertech.w8monitor.android.activity.interfaces.views.ProfileView;
import com.lucertech.w8monitor.android.d_base.RealmObj;
import com.lucertech.w8monitor.android.fragments.FragmentMapker;
import com.lucertech.w8monitor.android.utils.SettingsApp;
import com.lucertech.w8monitor.android.utils.ShowImages;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lucertech.w8monitor.android.activity.ProfileActivity.MARKER_MAIN;
import static com.lucertech.w8monitor.android.utils.FontsTextView.getFontRobotoLight;


/**
 * A simple {@link Fragment} subclass.
 */
public class StateFragment extends Fragment implements RealmObj.StateListener {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    public static final int MAN = 1;
    public static final int WOMAN = 2;

    private int state;
    private int markerFrom;
    private boolean mFromSettings;


    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;
    @Bind(R.id.iv_man)
    ImageView iv_man;
    @Bind(R.id.iv_woman)
    ImageView iv_woman;

    @Bind(R.id.tv_title_fragment)
    TextView tv_title_fragment;

    public StateFragment() {
    }

    public StateFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom,
                         boolean mFromSettings) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.mFromSettings = mFromSettings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_state, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        mPresenter.getStateUser(this);

        tv_title_fragment.setText(R.string.set_gender);
        tv_title_fragment.setTypeface(getFontRobotoLight());

        // hide button back
        if (markerFrom == MARKER_MAIN) {
            iv_toolbar_back_press.setVisibility(View.INVISIBLE);
        }
        return view;
    }


    private void saveDbMan(int mB) {
        mPresenter.saveStateUser(mB, this);
    }

    @OnClick(R.id.iv_man)
    public void clickNan() {
        saveDbMan(MAN);
    }

    @OnClick(R.id.iv_woman)
    public void clickWoman() {
        saveDbMan(WOMAN);
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        saveDbMan(state);
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.TYPE_BODY, false);
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.onBackPressedFromState();
    }


    //    ==========================================================
//            from StateListener
//    ==========================================================
    @Override
    public void isSave(int state) {
        this.state = state;
        int srcMan = 0;
        int srcWoman = 0;
        if (SettingsApp.getInstance().isThemeDark()) {
            srcMan = R.drawable.icon_male_nonactive_dark;
            srcWoman = R.drawable.icon_female_nonactive_dark;
            switch (state) {
                case -1:        // error
                    break;
                case MAN:         // man
                    srcMan = R.drawable.icon_male_active_dark;
                    break;
                case WOMAN:         // woman
                    srcWoman = R.drawable.icon_female_active_dark;
                    break;
            }
        } else {
            srcMan = R.drawable.icon_male_nonactive_light;
            srcWoman = R.drawable.icon_female_nonactive_light;
            switch (state) {
                case -1:        // error
                    break;
                case MAN:         // man
                    srcMan = R.drawable.icon_male_active_light;
                    break;
                case WOMAN:         // woman
                    srcWoman = R.drawable.icon_female_active_lght;
                    break;
            }
        }
        showPicture(srcMan, srcWoman);
    }

//    ==========================================================
//       END     from StateListener
//    ==========================================================


    private void showPicture(int mSrcMan, int mSrcWoman) {
        ShowImages.showImage(getActivity(), iv_man, mSrcMan);
        ShowImages.showImage(getActivity(), iv_woman, mSrcWoman);
    }

}
