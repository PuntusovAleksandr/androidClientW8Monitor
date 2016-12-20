package com.w8.w8monitor.android.fragments.profile.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.api.constant.ApiConstants;
import com.w8.w8monitor.android.api.service.ApiService;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.google.fit.GoogleFitApp;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.SettingsApp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.w8.w8monitor.android.utils.InternetUtils.checkInternetConnection;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;

/**
 * Created by AleksandrP on 19.12.2016.
 */

public class GoogleFitFragment extends Fragment implements
        ProfileActivity.ListenerGoogleFit,
        RealmObj.ProfileFirstStartGoogleFit {


    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    private boolean mFromSettings;
    private int markerFrom;

    private GoogleFitApp mFitApp;

    private String weight;

    private Intent serviceIntent;

    private boolean metric;

    @Bind(R.id.tv_yes)
    TextView tv_yes;
    @Bind(R.id.tv_no)
    TextView tv_no;

    @Bind(R.id.rl_card_view)
    RelativeLayout rl_card_view;
    @Bind(R.id.rl_login_register)
    RelativeLayout rl_login_register;

    public GoogleFitFragment() {
    }

    @SuppressLint("ValidFragment")
    public GoogleFitFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom, boolean mFromSettings) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.mFromSettings = mFromSettings;
        metric = SettingsApp.getInstance().getMetric();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_fit, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();
        serviceIntent = new Intent(getActivity(), ApiService.class);

        // TODO: 19.12.2016 здесь нужен запрос на интеграцию с гугл фит
        mFitApp = new GoogleFitApp(mActivity);

        mActivity.setListenerGoogleFit(this);

        showHideProgress(1);

        return view;
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

    @Override
    public void onStart() {
        super.onStart();

        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        mFitApp.buildFitnessClient();
    }

    @Override
    public void onResume() {
        super.onResume();

        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        mFitApp.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick(R.id.tv_no)
    public void clickNo() {
        SettingsApp.getInstance().setShowWeight(false);
        goToMainActivity();
    }

    @OnClick(R.id.tv_yes)
    public void clickYes() {
        SettingsApp.getInstance().setShowWeight(true);
        weight = SettingsApp.getInstance().getWeight();
        mPresenter.saveWeight(
                String.valueOf(weight),
                this);
    }

    //    =============================================
//        answer from onActivityResult
//    =============================================
    @Override
    public void onResult(int mRequestCode) {
        showHideProgress(2);
        if (mRequestCode == Activity.RESULT_OK) {
            rl_card_view.setVisibility(View.VISIBLE);
            mFitApp.requestOauth(mRequestCode);
        } else {
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        if (checkInternetConnection() &&
                !SettingsApp.getInstance().getUserName().equalsIgnoreCase(STATICS_PARAMS.TEST_USER)) {
            serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, ApiConstants.PROFILE);
            getActivity().startService(serviceIntent);
        } else {
            goToNext();
        }
    }

    private void goToNext() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mPresenter.setFullSettings(this);
    }

    //    ==============================================
//            from ProfileFirstStartGoogleFit
//    ==============================================
    @Override
    public void isOkFullSettings(boolean mIsOkFullSettings) {
        mPresenter.goToMainActivity();
        mActivity.finish();
    }

    @Override
    public void isTargetWeight(String height) {
        if (height.equalsIgnoreCase(weight)) {
            goToMainActivity();
        }
    }
}