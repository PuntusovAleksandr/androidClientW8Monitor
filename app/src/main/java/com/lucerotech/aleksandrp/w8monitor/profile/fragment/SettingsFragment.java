package com.lucerotech.aleksandrp.w8monitor.profile.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.change_pass.ChangePasswordActivity;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.help.HelpActivity;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfilePresenter;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileView;
import com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;
import com.lucerotech.aleksandrp.w8monitor.utils.ShowImages;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity.MARKER_MAIN;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.KEY_EXTRA_FROM;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.KEY_FROM_SETTINGS;

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
    }

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

        return view;
    }


    private void setBodyAndState(int mState, int mTypeBody) {
        boolean dark = SettingsApp.getInstance().isThemeDark();
        if (dark) {
            resState = R.drawable.b_male_small_dark;
            if (mState == 2) {      // state woman
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
            if (mState == 2) {      // state woman
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

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.onBackPressedFromState();
    }

    @OnClick(R.id.iv_b_heigh_dark)
    public void clickHigh() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.USER_GROWTH, true);
    }

    @OnClick(R.id.iv_b_bodytype_2_dark)
    public void clickBody() {
//        mActivity.setEnterProfileDataFragment(FragmentMapker.TYPE_BODY, true);
        int res = 1;
        String textButtom = "";
        if (typeBody == 2) {
            res = 3;
            textButtom = getString(R.string.pro);
        } else if (typeBody == 3) {
            res = 1;
            textButtom = getString(R.string.ordinary);
        } else {
            res = 2;
            textButtom = getString(R.string.amateur);
        }
        mPresenter.setTypeProfile(res, this);

        showCustomMessages(getString(R.string.ordinary_amauter_pro), textButtom);
    }

    @OnClick(R.id.iv_b_male_dark)
    public void clickMale() {
//        mActivity.setEnterProfileDataFragment(FragmentMapker.SELECT_STATE, true);
        int res = 0;
        String textButtom = "";
        if (state == 1) {
            res = 2;
            textButtom = getString(R.string.female);
        } else {
            res = 1;
            textButtom = getString(R.string.male);
        }
        mPresenter.setStateUser(res, this);

        showCustomMessages(getString(R.string.wale_female), textButtom);
    }

    @OnClick(R.id.iv_b_dob_dark)
    public void clickDOB() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY, true);
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
        showCustomMessages(getString(R.string.metric), textButtom);
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

    @OnClick(R.id.iv_b_language_en_dark)
    public void clicLanguage() {
//        no do https://3.basecamp.com/3110661/buckets/1532583/todos/275800708
//        String languages = SettingsApp.getInstance().getLanguages();
//        if (languages.equalsIgnoreCase(Locale.ENGLISH.getLanguage())) {
//            languages = "ru";
//            setIconLanDefault(false);
//        } else {
//            languages = Locale.ENGLISH.getLanguage();
//            setIconLanDefault(true);
//        }
//        SettingsApp.getInstance().setLanguages(languages);
//        SetLocaleApp.setLocale();
//        getActivity().finish();
//        startActivity(getActivity().getIntent());
    }


    @OnClick(R.id.iv_b_scale_dark)
    public void clickBScale() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.CONNECT_BLE, true);
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
        if (isEnglish) {
            if (SettingsApp.getInstance().isThemeDark()) {
                setIcon(iv_b_language_en_dark, R.drawable.b_language_en_dark);
            } else {
                setIcon(iv_b_language_en_dark, R.drawable.b_language_en_light);
            }
        } else {
            if (SettingsApp.getInstance().isThemeDark()) {
                setIcon(iv_b_language_en_dark, R.drawable.b_language_es_dark);
            } else {
                setIcon(iv_b_language_en_dark, R.drawable.b_language_es_light);
            }
        }
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
        state = mUserLibr.getState();
        typeBody = mUserLibr.getTypeBody();

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
