package com.lucerotech.aleksandrp.w8monitor.profile.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private ProfileActivity mActivity;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_b_heigh_dark)
    ImageView iv_b_heigh_dark;
    @Bind(R.id.iv_b_bodytype_2_dark)
    ImageView iv_b_bodytype_2_dark;
    @Bind(R.id.iv_b_male_dark)
    ImageView iv_b_male_dark;
    @Bind(R.id.iv_b_dob_dark)
    ImageView iv_b_dob_dark;
    @Bind(R.id.iv_b_metric2_dark)
    ImageView iv_b_metric2_dark;
    @Bind(R.id.iv_b_reset_dark)
    ImageView iv_b_reset_dark;
    @Bind(R.id.iv_b_language_en_dark)
    ImageView iv_b_language_en_dark;
    @Bind(R.id.iv_b_scale_dark)
    ImageView iv_b_scale_dark;
    @Bind(R.id.iv_b_help_dark)
    ImageView iv_b_help_dark;
    @Bind(R.id.iv_b_mode2_dark)
    ImageView iv_b_mode2_dark;
    @Bind(R.id.iv_b_logout_dark)
    ImageView iv_b_logout_dark;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        return view;
    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.CONNECT_BLE);
    }

    @OnClick(R.id.iv_b_heigh_dark)
    public void clickHigh() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.USER_GROWTH);
    }

    @OnClick(R.id.iv_b_bodytype_2_dark)
    public void clickBody() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.TYPE_BODY);
    }

    @OnClick(R.id.iv_b_male_dark)
    public void clickMale() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.STATE_USER);
    }

    @OnClick(R.id.iv_b_dob_dark)
    public void clickDOB() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY);
    }

    @OnClick(R.id.iv_b_metric2_dark)
    public void clickMetric() {
//        no do
    }

    @OnClick(R.id.iv_b_reset_dark)
    public void clickReset() {
//        no do
    }

    @OnClick(R.id.iv_b_language_en_dark)
    public void clicLanguage() {
//        no do
    }

    @OnClick(R.id.iv_b_scale_dark)
    public void clickBScale() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.CONNECT_BLE);
    }

    @OnClick(R.id.iv_b_help_dark)
    public void clickHelp() {
//       no do
    }

    @OnClick(R.id.iv_b_mode2_dark)
    public void clickMode() {
//        no do
    }

    @OnClick(R.id.iv_b_logout_dark)
    public void clickLogout() {
        mActivity.logout();
    }

}
