package com.w8.w8monitor.android.fragments.profile.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ChangePasswordActivity;
import com.w8.w8monitor.android.activity.HelpActivity;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.d_base.model.Profile;
import com.w8.w8monitor.android.d_base.model.UserLibr;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.SettingsApp;
import com.w8.w8monitor.android.utils.ShowImages;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;

import static com.w8.w8monitor.android.activity.ProfileActivity.MARKER_MAIN;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.KEY_EXTRA_FROM;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.KEY_FROM_SETTINGS;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements
        RealmObj.GetUserForSettings,
        SettingsFragmentView {

    private ProfileActivity mActivity;
    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    private int markerFrom;
    private int fromMain = MARKER_MAIN;
    private int resState = 0, resBody = 0;
    private int state;
    private int typeBody;

    private boolean isDarkThene = true;

    private Handler mHandler;

    @Bind(R.id.tv_title_fragment)
    TextView tv_swipe;
    @Bind(R.id.tv_title_buttom_top_settings)
    TextView tv_title_buttom_top_settings;

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
    @Bind(R.id.iv_b_scale_dark)
    ImageView iv_b_scale_dark;
    @Bind(R.id.iv_b_help_dark)
    ImageView iv_b_help_dark;
    @Bind(R.id.iv_b_mode2_dark)
    ImageView iv_b_mode2_dark;
    @Bind(R.id.iv_register)
    ImageView iv_register;
    @Bind(R.id.iv_google_fit)
    ImageView iv_google_fit;
    @Bind(R.id.iv_b_logout_dark)
    ImageView iv_b_logout_dark;
    @Bind(R.id.iv_target_weight)
    ImageView iv_target_weight;

    @Bind(R.id.iv_personal)
    ImageView iv_personal;
    @Bind(R.id.iv_account)
    ImageView iv_account;
    @Bind(R.id.iv_help)
    ImageView iv_help;

    // this it hide
    @Bind(R.id.ll_help_personal)
    LinearLayout ll_help_personal;
    @Bind(R.id.ll_help_account)
    LinearLayout ll_help_account;
    @Bind(R.id.ll_help_settings)
    LinearLayout ll_help_settings;

    @Bind(R.id.ll_top)
    LinearLayout ll_top;

    // this it click
    @Bind(R.id.ll_personal)
    LinearLayout ll_personal;
    @Bind(R.id.ll_account)
    LinearLayout ll_account;
    @Bind(R.id.ll_help)
    LinearLayout ll_help;
    @Bind(R.id.ll_iv_b_heigh_dark)
    LinearLayout ll_iv_b_heigh_dark;
    @Bind(R.id.ll_iv_b_dob_dark)
    LinearLayout ll_iv_b_dob_dark;
    @Bind(R.id.ll_iv_b_male_dark)
    LinearLayout ll_iv_b_male_dark;
    @Bind(R.id.ll_iv_b_bodytype_2_dark)
    LinearLayout ll_iv_b_bodytype_2_dark;
    @Bind(R.id.ll_iv_b_metric2_dark)
    LinearLayout ll_iv_b_metric2_dark;
    @Bind(R.id.ll_iv_target_weight)
    LinearLayout ll_iv_target_weight;
    @Bind(R.id.ll_iv_google_fit)
    LinearLayout ll_iv_google_fit;
    @Bind(R.id.ll_iv_b_scale_dark)
    LinearLayout ll_iv_b_scale_dark;
    @Bind(R.id.ll_iv_b_mode2_dark)
    LinearLayout ll_iv_b_mode2_dark;
    @Bind(R.id.ll_iv_b_reset_dark)
    LinearLayout ll_iv_b_reset_dark;
    @Bind(R.id.ll_iv_register)
    LinearLayout ll_iv_register;
    @Bind(R.id.ll_iv_b_logout_dark)
    LinearLayout ll_iv_b_logout_dark;


    @Bind(R.id.view_center_settings)
    View view_center_settings;

    private boolean isVisible;

    public SettingsFragment() {
    }

    @SuppressLint("ValidFragment")
    public SettingsFragment(ProfileActivity mActivity, ProfileView mProfileView, ProfilePresenter mPresenter,
                            int markerFrom, int mFromMain) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.fromMain = mFromMain;
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = 2;
        typeBody = 1;
        isDarkThene = SettingsApp.getInstance().isThemeDark();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);


        mActivity.initListenerSettingsFragment(this);

        setDefaultIconLanguages();
        setIconMetricDef();
        setIconLightDefault();

        mPresenter.getUserForSettings(this);

        isVisible = false;
        return view;
    }


    private void setBodyAndState(int mState, int mTypeBody) {
        boolean dark = SettingsApp.getInstance().isThemeDark();
        if (dark) {
            resState = R.drawable.b_male_small_dark;
            if (mState == 0) {      // state woman
                resState = R.drawable.b_female_small_dark;
            }
            switch (mTypeBody) {
                case 1:
                    resBody = R.drawable.b_bodytype_1_small_dark;
                    break;
                case 2:
                    resBody = R.drawable.b_bodytype_2_small_dark;
                    break;
                default:
                    resBody = R.drawable.b_bodytype_3_small_dark;
            }

        } else {
            resState = R.drawable.b_male_small_light;
            if (mState == 0) {      // state woman
                resState = R.drawable.b_female_small_light;
            }
            switch (mTypeBody) {
                case 1:
                    resBody = R.drawable.b_bodytype_1_small_light;
                    break;
                case 2:
                    resBody = R.drawable.b_bodytype_2_small_light;
                    break;
                default:
                    resBody = R.drawable.b_bodytype_3_small_light;
            }
        }

        setImages(resState, resBody);
    }

    private void setImages(int mResState, int mResBody) {
        iv_b_male_dark.setImageResource(mResState);
        iv_b_bodytype_2_dark.setImageResource(mResBody);
    }

    private void setDefaultIconLanguages() {

        String languages = SettingsApp.getInstance().getLanguages();
        if (languages.equalsIgnoreCase(Locale.ENGLISH.getLanguage())) {
            setIconLanDefault(true);
        } else {
            setIconLanDefault(false);
        }
    }
//==========================================================================
//        OnClick
//==========================================================================

    @OnClick(R.id.ll_personal)
    public void ll_personalClick() {
        hideAllLL();
        setVisibleLL(ll_help_personal);
    }

    @OnClick(R.id.ll_account)
    public void ll_accountpClick() {
        hideAllLL();
        setVisibleLL(ll_help_account);
    }

    @OnClick(R.id.ll_help)
    public void ll_helpClick() {
        hideAllLL();
        setVisibleLL(ll_help_settings);
    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.onBackPressedFromState();
    }

    @OnClick(R.id.iv_b_heigh_dark)
    public void clickHigh() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.USER_GROWTH, true, null);
    }

    @OnClick(R.id.iv_b_bodytype_2_dark)
    public void clickBody() {
//        mActivity.setEnterProfileDataFragment(FragmentMapker.TYPE_BODY, true);
        int res = 1;
        String textButtom = "";
        String textButtom2 = "";
        if (typeBody == 2) {
            res = 3;
            textButtom = getString(R.string.pro);
            textButtom2 = getString(R.string.exercise_every_day);
        } else if (typeBody == 3) {
            res = 1;
            textButtom = getString(R.string.ordinary);
            textButtom2 = getString(R.string.exercise_less_than_2_times_a_week);
        } else {
            res = 2;
            textButtom = getString(R.string.amateur);
            textButtom2 = getString(R.string.exercise_2_5_times_week);
        }
        mPresenter.setTypeProfile(res, this);

        showCustomMessages(textButtom, textButtom2);
    }

    @OnClick(R.id.iv_b_male_dark)
    public void clickMale() {
//        mActivity.setEnterProfileDataFragment(FragmentMapker.SELECT_STATE, true);
        int res = 0;
        String textButtom = "";
        if (state == 1) {
            res = 0;
            textButtom = getString(R.string.female);
        } else {
            res = 1;
            textButtom = getString(R.string.male);
        }
        mPresenter.setStateUser(res, this);

        showCustomMessages(textButtom, "");
    }

    @OnClick(R.id.iv_b_dob_dark)
    public void clickDOB() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY, true, null);
    }

    @OnClick(R.id.iv_b_metric2_dark)
    public void clickMetric() {
//        no do
        boolean metric = SettingsApp.getInstance().getMetric();
        String textButtom = "";
        if (metric) {
            metric = false;
            textButtom = getString(R.string.imperial);
        } else {
            metric = true;
            textButtom = getString(R.string.metric_);
        }
        SettingsApp.getInstance().setMetric(metric);
        setIconMetricDef();
        showCustomMessages(textButtom, "");
    }


    private void showCustomMessages(String mString, String mTextButtom) {

        tv_swipe.setText(mString);
        tv_title_buttom_top_settings.setText(mTextButtom);

        tv_swipe.setVisibility(View.VISIBLE);
        tv_title_buttom_top_settings.setVisibility(View.VISIBLE);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_swipe.setVisibility(View.INVISIBLE);
                tv_title_buttom_top_settings.setVisibility(View.INVISIBLE);
                tv_swipe.setText("");
                tv_title_buttom_top_settings.setText("");
            }
        }, 2000);
    }

    @OnClick(R.id.iv_b_reset_dark)
    public void clickReset() {
//        no do
        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        intent.putExtra(STATICS_PARAMS.MAIL, SettingsApp.getInstance().getUserName());
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.iv_b_scale_dark)
    public void clickBScale() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.CONNECT_BLE, true, null);
    }

    @OnClick(R.id.iv_b_help_dark)
    public void clickHelp() {
//       no do
        Intent intent = new Intent(getActivity(), HelpActivity.class);
        intent.putExtra(KEY_EXTRA_FROM, KEY_FROM_SETTINGS);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.iv_b_mode2_dark)
    public void clickMode() {
        if (isDarkThene) {
            isDarkThene = false;
            setIconLightDefault();
        } else {
            isDarkThene = true;
            setIconLightDefault();
        }
        SettingsApp.getInstance().setThemeDark(isDarkThene);
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

    @OnClick(R.id.iv_b_logout_dark)
    public void clickLogout() {
        mActivity.logout();
    }


//============================================================

    private void setVisibleLL(LinearLayout ll) {
        ll.setVisibility(View.VISIBLE);

        if (!isVisible) {
            isVisible = true;

            view_center_settings.setVisibility(View.VISIBLE);
            ll_top.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slid_up));
            ll.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slid_up));

        }
    }

    private void hideAllLL() {
        ll_help_personal.setVisibility(View.GONE);
        ll_help_account.setVisibility(View.GONE);
        ll_help_settings.setVisibility(View.GONE);
    }


    private void setIconMetricDef() {
        boolean isMetic = SettingsApp.getInstance().getMetric();
        if (isMetic) {
            if (SettingsApp.getInstance().isThemeDark()) {
                setIcon(iv_b_metric2_dark, R.drawable.b_metric2_dark);
            } else {
                setIcon(iv_b_metric2_dark, R.drawable.b_metric2_light);
            }
        } else {
            if (SettingsApp.getInstance().isThemeDark()) {
                setIcon(iv_b_metric2_dark, R.drawable.b_metric_dark);
            } else {
                setIcon(iv_b_metric2_dark, R.drawable.b_metric_light);
            }
        }
    }


    private void setIconLanDefault(boolean isEnglish) {
//        if (isEnglish) {
//            if (SettingsApp.getInstance().isThemeDark()) {
//                setIcon(iv_b_language_en_dark, R.drawable.b_language_en_dark);
//            } else {
//                setIcon(iv_b_language_en_dark, R.drawable.b_language_en_light);
//            }
//        } else {
//            if (SettingsApp.getInstance().isThemeDark()) {
//                setIcon(iv_b_language_en_dark, R.drawable.b_language_es_dark);
//            } else {
//                setIcon(iv_b_language_en_dark, R.drawable.b_language_es_light);
//            }
//        }
    }


    private void setIconLightDefault() {
        if (SettingsApp.getInstance().isThemeDark()) {
            setIcon(iv_b_mode2_dark, R.drawable.b_mode1_dark);
        } else {
            setIcon(iv_b_mode2_dark, R.drawable.b_mode2_light);
        }
    }

    private void setIcon(ImageView mView, int res) {
        ShowImages.showImage(getActivity(), mView, res);
    }


    //    =================================================
//            from GetUserForSettings
//    =================================================
    @Override
    public void getUserForSettings(UserLibr mUserLibr) {
        if (mUserLibr == null) {
            return;
        }
        state = 1;
        typeBody = 1;
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        RealmList<Profile> profiles = mUserLibr.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getNumber() == profileBLE) {
                state = profiles.get(i).getGender();
                typeBody = profiles.get(i).getActivity_type();
            }
        }

        setBodyAndState(state, typeBody);
    }

    //    =================================================
//            from SettingsFragmentView
//    =================================================
    @Override
    public void isConnectedBluetooth(boolean isConnect) {
        // no do
//        if (isConnect) {
//            tv_swipe.setVisibility(View.INVISIBLE);
//        } else {
//            tv_swipe.setVisibility(View.VISIBLE);
//        }
    }
}
