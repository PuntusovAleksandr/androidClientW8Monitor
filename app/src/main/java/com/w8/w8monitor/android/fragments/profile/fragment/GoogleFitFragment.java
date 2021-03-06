package com.w8.w8monitor.android.fragments.profile.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.api.constant.ApiConstants;
import com.w8.w8monitor.android.api.service.ApiService;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.d_base.model.RegisterUser;
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
    private RegisterUser mRegisterUser;

    private boolean temaDark;
    private int yes = -1;

    @Bind(R.id.tv_yes)
    TextView tv_yes;
    @Bind(R.id.tv_no)
    TextView tv_no;

    @Bind(R.id.iv_yes)
    ImageView iv_yes;
    @Bind(R.id.iv_no)
    ImageView iv_no;
    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;

    @Bind(R.id.rl_card_view)
    RelativeLayout rl_card_view;
    @Bind(R.id.rl_login_register)
    RelativeLayout rl_login_register;

    public GoogleFitFragment() {
    }

    @SuppressLint("ValidFragment")
    public GoogleFitFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom, boolean mFromSettings, RegisterUser mRegisterUser) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.mFromSettings = mFromSettings;
        this.mRegisterUser = mRegisterUser;
        temaDark = SettingsApp.getInstance().isThemeDark();
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
        mFitApp = new GoogleFitApp(mActivity, 3);

        mActivity.setListenerGoogleFit(this);

        return view;
    }

    @Override
    public void onDestroy() {
        mFitApp.onDestroy();
        super.onDestroy();
    }

    private void showHideProgress(float count) {
        rl_login_register.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_login_register.setVisibility(View.GONE);
            }
        }, (long) (count * 1000));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        if (yes < 0) {
            Toast.makeText(mActivity, R.string.take_your_pick, Toast.LENGTH_SHORT).show();
        } else {
            if (mFromSettings) {
                mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
            } else {
                hideBackAndNext();
                showHideProgress(2);
                if (yes == 1) {
                    // This ensures that if the user denies the permissions then uses Settings to re-enable
                    // them, the app will start working.
                    mFitApp.buildFitnessClient(false);
                    // This ensures that if the user denies the permissions then uses Settings to re-enable
                    // them, the app will start working.
                    mFitApp.connect();
                } else {
                    saveAuthorisationGoogleFit(false);
                    goToMainActivity();
                }
            }
        }
    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.TARGET_WEIGHT, false, mRegisterUser);
    }

    @OnClick(R.id.iv_yes)
    public void clickIvYes() {
        yes = 1;
        setIcon(iv_yes);
    }

    @OnClick(R.id.iv_no)
    public void clickIvNo() {
        yes = 0;
        setIcon(iv_no);
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
        showHideProgress(3.5f);
        if (mRequestCode == Activity.RESULT_OK) {
            showAlert();
            mFitApp.requestOauth(mRequestCode);
            saveAuthorisationGoogleFit(true);
        } else {
            Toast.makeText(mActivity, R.string.try_later, Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }
    }

    private void showAlert() {
        String title = getString(R.string.integreted);
        String message = getString(R.string.text_google_fit);
        String button1String = getString(R.string.yes);
        String button2String = getString(R.string.no);

        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение

        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                SettingsApp.getInstance().setShowWeight(true);
                weight = SettingsApp.getInstance().getWeight();
                mPresenter.saveWeight(
                        String.valueOf(weight),
                        GoogleFitFragment.this);
            }
        });

        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                SettingsApp.getInstance().setShowWeight(false);
                goToMainActivity();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                SettingsApp.getInstance().setShowWeight(false);
                goToMainActivity();
            }
        });

        AlertDialog alert = ad.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        //Set negative button background
//        nbutton.setBackgroundColor(Color.MAGENTA);
        //Set negative button text color
        nbutton.setTextColor(Color.parseColor("#4261DD"));
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        //Set positive button text color
        pbutton.setTextColor(Color.parseColor("#4261DD"));
        Button pbuttonEarse = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
        //Set positive button text color
        pbuttonEarse.setTextColor(Color.parseColor("#4261DD"));
    }


    private void saveAuthorisationGoogleFit(boolean mB) {
        // sa у  шт memory status authorisation by google fit
        SettingsApp.getInstance().saveAuthGoogleFit(mB);
    }


    private void hideBackAndNext() {
        iv_toolbar_back_press.setVisibility(View.INVISIBLE);
        iv_toolbar_next_press.setVisibility(View.INVISIBLE);
    }

    private void setIcon(ImageView mView) {
        int resYes = 0;
        int resNo = 0;
        if (yes == 1) {
            if (!temaDark) {
                resYes = R.drawable.b_health_yes_active_light;
                resNo = R.drawable.b_health_no_nonactive_light;
            } else {
                resYes = R.drawable.b_health_yes_active_dark;
                resNo = R.drawable.b_health_no_nonactive_dark;
            }
        } else {
            if (!temaDark) {
                resYes = R.drawable.b_health_yes_nonactive_light;
                resNo = R.drawable.b_health_no_active_light;
            } else {
                resYes = R.drawable.b_health_yes_nonactive_dark;
                resNo = R.drawable.b_health_no_active_dark;
            }
        }
        iv_no.setImageResource(resNo);
        iv_yes.setImageResource(resYes);
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