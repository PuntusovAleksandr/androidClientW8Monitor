package com.w8.w8monitor.android.fragments.profile.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ChangePasswordActivity;
import com.w8.w8monitor.android.activity.HelpActivity;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.SupportActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.d_base.model.Profile;
import com.w8.w8monitor.android.d_base.model.UserLibr;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.google.fit.GoogleFitApp;
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
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.TEST_USER;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements
        RealmObj.GetUserForSettings,
        SettingsFragmentView,
        ProfileActivity.ListenerGoogleFitSettings,
        GoogleFitApp.GisconnectListener {

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
    @Bind(R.id.iv_b_help_web)
    ImageView iv_b_help_web;

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

    @Bind(R.id.view_for_top)
    View view_for_top;
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
    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.ll_support)
    LinearLayout ll_support;
    @Bind(R.id.dempfer_view)
    LinearLayout dempfer_view;


    @Bind(R.id.rl_login_register)
    RelativeLayout rl_login_register;

    @Bind(R.id.view_center_settings)
    View view_center_settings;

    private boolean isAuthorisationGoogleFit;
    private boolean isVisible;
    private int SELECTED = 0;
    private final int SELECT_PRSONAL = 1;
    private final int SELECT_ACCOUNT = 2;
    private final int SELECT_WEB = 3;

    private GoogleFitApp mFitApp;
    private boolean isBuildFit;

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
        // set listener activity
        mActivity.initListenerSettingsFragment(this);
        mActivity.setListenerGoogleFit(this);

        setDefaultIconLanguages();
        setIconMetricDef();
        setIconLightDefault();

        mPresenter.getUserForSettings(this);

        isVisible = false;
        SELECTED = 0;

        // show register button when login in test account
        if (SettingsApp.getInstance().getUserName().equalsIgnoreCase(TEST_USER)) {
            ll_iv_register.setVisibility(View.VISIBLE);
            ll_iv_b_reset_dark.setVisibility(View.GONE);
            dempfer_view.setVisibility(View.VISIBLE);
        } else {
            if (SettingsApp.getInstance().isUserLOginFB()) {
                ll_iv_b_reset_dark.setVisibility(View.GONE);
            } else {
                dempfer_view.setVisibility(View.VISIBLE);
                ll_iv_register.setVisibility(View.GONE);
            }
        }



        setIconOnButtonGoogleFit();

        isBuildFit = false;

        tv_swipe.setText(R.string.settings);

        return view;
    }

    @Override
    public void onDestroy() {
        if (mFitApp != null)
            mFitApp.onDestroy();
        super.onDestroy();
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
        tv_swipe.setText(R.string.personal_settings);
        if (SELECTED == SELECT_PRSONAL) {
            tv_swipe.setText(R.string.settings);
            hideAllLL(true, ll_help_personal);
            return;
        }
        hideAllLL(false, ll_help_personal);
        SELECTED = SELECT_PRSONAL;
        setVisibleLL(ll_help_personal);
        setSelectionIcons(iv_personal, R.drawable.b_personal_active_light, R.drawable.b_personal_active_dark);
    }

    @OnClick(R.id.ll_account)
    public void ll_accountpClick() {
        tv_swipe.setText(R.string.account_settings);
        if (SELECTED == SELECT_ACCOUNT) {
            tv_swipe.setText(R.string.settings);
            hideAllLL(true, ll_help_account);
            return;
        }
        hideAllLL(false, ll_help_account);
        SELECTED = SELECT_ACCOUNT;
        setVisibleLL(ll_help_account);
        setSelectionIcons(iv_account, R.drawable.b_account_active_light, R.drawable.b_account_active_dark);
    }

    @OnClick(R.id.ll_help)
    public void ll_helpClick() {
        tv_swipe.setText(R.string.help);
        if (SELECTED == SELECT_WEB) {
            tv_swipe.setText(R.string.settings);
            hideAllLL(true, ll_help_settings);
            return;
        }
        hideAllLL(false, ll_help_settings);
        SELECTED = SELECT_WEB;
        setVisibleLL(ll_help_settings);
        setSelectionIcons(iv_help, R.drawable.b_help_active_light, R.drawable.b_help_active_dark);
    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.onBackPressedFromState();
    }

    @OnClick(R.id.iv_b_help_web)
    public void iv_b_help_webClock() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.web_link)));
        startActivity(browserIntent);
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
        String oldText = getString(R.string.personal);
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

        showCustomMessages(textButtom, textButtom2, oldText);
    }

    @OnClick(R.id.iv_b_male_dark)
    public void clickMale() {
//        mActivity.setEnterProfileDataFragment(FragmentMapker.SELECT_STATE, true);
        int res = 0;
        String textButtom = "";
        String oldText = getString(R.string.personal);
        if (state == 1) {
            res = 0;
            textButtom = getString(R.string.female);
        } else {
            res = 1;
            textButtom = getString(R.string.male);
        }
        mPresenter.setStateUser(res, this);

        showCustomMessages(textButtom, "", oldText);
    }

    @OnClick(R.id.iv_b_dob_dark)
    public void clickDOB() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY, true, null);
    }

    @OnClick(R.id.ll_iv_target_weight)
    public void ll_iv_target_weightClock() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.TARGET_WEIGHT, true, null);
    }

    @OnClick(R.id.ll_support)
    public void ll_supportClock() {
        Intent intent = new Intent(getActivity(), SupportActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.iv_b_metric2_dark)
    public void clickMetric() {
//        no do
        boolean metric = SettingsApp.getInstance().getMetric();
        String textButtom = "";
        String oldText = getString(R.string.personal);
        if (metric) {
            metric = false;
            textButtom = getString(R.string.imperial);
        } else {
            metric = true;
            textButtom = getString(R.string.metric_);
        }
        SettingsApp.getInstance().setMetric(metric);
        setIconMetricDef();
        showCustomMessages(textButtom, "", oldText);
    }


    private void showCustomMessages(String mString, String mTextButtom, final String mOldText) {

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
                tv_title_buttom_top_settings.setVisibility(View.INVISIBLE);
                tv_swipe.setText(mOldText);
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

    @OnClick(R.id.iv_b_logout_dark)
    public void ll_iv_registerClick() {
        if (ll_iv_register.getVisibility() == View.VISIBLE) {
            // TODO: 20.12.2016 Здесь нужно химичить с регистрацией
        }
    }

    @OnClick(R.id.iv_google_fit)
    public void iv_google_fitClick() {
//        showHideProgress(2);
//        if (mFitApp == null) {
//            mFitApp = new GoogleFitApp(mActivity, 2);
//        }
        if (isAuthorisationGoogleFit) {
            return;
//            mFitApp.disableFit(this);
//            setIconOnButtonGoogleFit();
        } else {
            showHideProgress(2);
            if (mFitApp == null) {
                mFitApp = new GoogleFitApp(mActivity, 2);
            }

            if (!isBuildFit)
                // This ensures that if the user denies the permissions then uses Settings to re-enable
                // them, the app will start working.
                mFitApp.buildFitnessClient(false);
            isBuildFit = true;
            // This ensures that if the user denies the permissions then uses Settings to re-enable
            // them, the app will start working.
            mFitApp.connect();
        }
    }


//============================================================

    private void setVisibleLL(LinearLayout ll) {
        ll.setVisibility(View.VISIBLE);

        if (!isVisible) {
            isVisible = true;

            view_for_top.setVisibility(View.VISIBLE);

            view_center_settings.setVisibility(View.VISIBLE);
            view_center_settings.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
            ll.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));

            ll_main.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slid_up));
        }
    }

    private void hideAllLL(boolean mAnimation, final LinearLayout ll) {
        if (mAnimation) {
            // make time out gone for show animation
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view_for_top.setVisibility(View.GONE);
                }
            }, 335);

            ll.setVisibility(View.GONE);
            view_center_settings.setVisibility(View.GONE);
            ll.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout));
            view_center_settings.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout));

            SELECTED = 0;
            isVisible = false;

            ll_main.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slid_down));

        } else {
            ll_help_personal.setVisibility(View.GONE);
            ll_help_account.setVisibility(View.GONE);
            ll_help_settings.setVisibility(View.GONE);
        }
        setDefaultIcons();
    }

    private void setDefaultIcons() {
        boolean themeDark = SettingsApp.getInstance().isThemeDark();
        int resPerson = 0, resAccount = 0, resSettings = 0;
        if (themeDark) {
            resPerson = R.drawable.b_personal_nonactive_dark;
            resAccount = R.drawable.b_account_nonactive_dark;
            resSettings = R.drawable.b_help_nonactive_dark;
        } else {
            resPerson = R.drawable.b_personal_nonactive_light;
            resAccount = R.drawable.b_account_nonactive_light;
            resSettings = R.drawable.b_help_nonactive_light;
        }

        iv_personal.setImageResource(resPerson);
        iv_account.setImageResource(resAccount);
        iv_help.setImageResource(resSettings);
    }


    private void setSelectionIcons(ImageView mView, int resDark, int resLight) {
        boolean themeDark = SettingsApp.getInstance().isThemeDark();
        if (!themeDark) {
            mView.setImageResource(resDark);
        } else {
            mView.setImageResource(resLight);
        }
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


    private void setIconOnButtonGoogleFit() {
        // get status authorisation by google fit
        isAuthorisationGoogleFit = SettingsApp.getInstance().isAuthGoogleFit();
        int resIcon = 0;
        boolean themeDark = SettingsApp.getInstance().isThemeDark();
        if (!isAuthorisationGoogleFit) {
            if (themeDark) {
                resIcon = R.drawable.b_health_on_dark;
            } else {
                resIcon = R.drawable.b_health_on_light;
            }
        } else {
            if (themeDark) {
                resIcon = R.drawable.b_health_off_dark;
            } else {
                resIcon = R.drawable.b_health_off_light;
            }
        }
        iv_google_fit.setImageResource(resIcon);
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


    private void showHideProgress(int count) {
        rl_login_register.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_login_register.setVisibility(View.GONE);
            }
        }, count * 1000);
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

    @Override
    public void onResult(int mRequestCode) {
        if (mRequestCode == Activity.RESULT_OK) {
            // sa у  шт memory status authorisation by google fit
            SettingsApp.getInstance().saveAuthGoogleFit(true);
        } else {
            // sa у  шт memory status authorisation by google fit
            SettingsApp.getInstance().saveAuthGoogleFit(false);
            Toast.makeText(mActivity, R.string.try_later, Toast.LENGTH_SHORT).show();
        }

        setIconOnButtonGoogleFit();
    }

    //    =================================================
//            from GisconnectListener mListener
//    =================================================
    @Override
    public void disconnect() {
        setIconOnButtonGoogleFit();
    }
}
