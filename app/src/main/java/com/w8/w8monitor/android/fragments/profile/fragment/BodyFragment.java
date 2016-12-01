package com.w8.w8monitor.android.fragments.profile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.utils.SettingsApp;
import com.w8.w8monitor.android.utils.ShowImages;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.w8.w8monitor.android.activity.ProfileActivity.MARKER_MAIN;
import static com.w8.w8monitor.android.utils.FontsTextView.getFontRobotoLight;


public class BodyFragment extends Fragment implements RealmObj.BodyListener {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    public static final int BODY_TYPE_1 = 1;
    public static final int BODY_TYPE_2 = 2;
    public static final int BODY_TYPE_3 = 3;

    private int body;
    private int markerFrom;
    private boolean mFromSettings;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;

    @Bind(R.id.iv_type1)
    ImageView iv_type1;
    @Bind(R.id.iv_type2)
    ImageView iv_type2;
    @Bind(R.id.iv_type3)
    ImageView iv_type3;

    @Bind(R.id.tv_title_fragment)
    TextView tv_title_fragment;

    public BodyFragment() {
    }

    public BodyFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom,
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
        View view = inflater.inflate(R.layout.fragment_body, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        mPresenter.getBodyUser(this);

        tv_title_fragment.setText(R.string.activity_level);
        tv_title_fragment.setTypeface(getFontRobotoLight());

        // hide button back
        if (markerFrom == MARKER_MAIN) {
            iv_toolbar_back_press.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        saveLastBody();
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY, false);
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        saveLastBody();
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.SELECT_STATE, false);
    }

    @OnClick(R.id.iv_type1)
    public void clickType1() {
        setBody(BODY_TYPE_1);
    }


    @OnClick(R.id.iv_type2)
    public void clickType2() {
        setBody(BODY_TYPE_2);
    }

    @OnClick(R.id.iv_type3)
    public void clickType3() {
        setBody(BODY_TYPE_3);
    }


    private void setBody(int mBodyType) {
        mPresenter.setBody(mBodyType, this);
    }

    //    ==================================================
//            answer from BodyListener
//    ==================================================
    @Override
    public void isBody(int body) {
        this.body = body;
        int resIcon1 = 0;
        int resIcon2 = 0;
        int resIcon3 = 0;
        if (SettingsApp.getInstance().isThemeDark()) {
            resIcon1 = R.drawable.type_1_nonactive_dark;
            resIcon2 = R.drawable.type_2_nonactive_dark;
            resIcon3 = R.drawable.type_3_nonactive_dark;
            switch (body) {
                case -1:
                    break;
                case BODY_TYPE_1:
                    resIcon1 = R.drawable.type_1_active_dark;
                    break;
                case BODY_TYPE_2:
                    resIcon2 = R.drawable.type_2_active_dark;
                    break;
                case BODY_TYPE_3:
                    resIcon3 = R.drawable.type_3_active_dark;
                    break;
            }
        } else {
            resIcon1 = R.drawable.type_1_nonactive_light;
            resIcon2 = R.drawable.type_2_nonactive_light;
            resIcon3 = R.drawable.type_3_nonactive_light;
            switch (body) {
                case -1:
                    break;
                case BODY_TYPE_1:
                    resIcon1 = R.drawable.type_1_active_light;
                    break;
                case BODY_TYPE_2:
                    resIcon2 = R.drawable.type_2_active_light;
                    break;
                case BODY_TYPE_3:
                    resIcon3 = R.drawable.type_3_active_light;
                    break;
            }
        }

        showPicture(resIcon1, resIcon2, resIcon3);
    }


//    ==========================================================
//       END     from StateListener
//    ==========================================================

    private void saveLastBody() {
        if (body == -1) {
            body = 2;
        }
        setBody(body);
    }

    private void showPicture(int mResIcon1, int mResIcon2, int mResIcon3) {
        ShowImages.showImage(getActivity(), iv_type1, mResIcon1);
        ShowImages.showImage(getActivity(), iv_type2, mResIcon2);
        ShowImages.showImage(getActivity(), iv_type3, mResIcon3);
    }
}
