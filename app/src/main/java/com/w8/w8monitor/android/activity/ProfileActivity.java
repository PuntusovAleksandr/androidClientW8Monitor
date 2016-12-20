package com.w8.w8monitor.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;
import com.viewpagerindicator.CirclePageIndicator;
import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.adapter.CirclePagerAdapter;
import com.w8.w8monitor.android.d_base.model.RegisterUser;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.fragments.profile.fragment.BLEFragment;
import com.w8.w8monitor.android.fragments.profile.fragment.BLEFragmentView;
import com.w8.w8monitor.android.fragments.profile.fragment.BirthdayFragment;
import com.w8.w8monitor.android.fragments.profile.fragment.BodyFragment;
import com.w8.w8monitor.android.fragments.profile.fragment.GoogleFitFragment;
import com.w8.w8monitor.android.fragments.profile.fragment.GrowthFragment;
import com.w8.w8monitor.android.fragments.profile.fragment.SettingsFragment;
import com.w8.w8monitor.android.fragments.profile.fragment.SettingsFragmentView;
import com.w8.w8monitor.android.fragments.profile.fragment.StateFragment;
import com.w8.w8monitor.android.fragments.profile.fragment.TargetWeightFragment;
import com.w8.w8monitor.android.presents.profile.presenter.ProfilePresenterImpl;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.SetThemeDark;
import com.w8.w8monitor.android.utils.SettingsApp;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

import static com.w8.w8monitor.android.fragments.FragmentMapker.BIRTHDAY_FRAGMENT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.BODY_FRAGMENT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.CONNECT_BLE;
import static com.w8.w8monitor.android.fragments.FragmentMapker.CONNECT_BLE_FRAGMENT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.DATA_BIRTHDAY;
import static com.w8.w8monitor.android.fragments.FragmentMapker.GOOGLE_FIT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.GOOGLE_FIT_FRAGMENT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.GROWTH_FRAGMENT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.SELECT_STATE;
import static com.w8.w8monitor.android.fragments.FragmentMapker.STATE_FRAGMENT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.TARGET_WEIGHT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.TARGET_WEIGHT_FRAGMENT;
import static com.w8.w8monitor.android.fragments.FragmentMapker.TYPE_BODY;
import static com.w8.w8monitor.android.fragments.FragmentMapker.USER_GROWTH;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.COUNT_PAGES_PROFILE_DEFOULT;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.KEI_CONNECTION;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.REQUEST_ENABLE_BT;
import static com.w8.w8monitor.android.utils.ShowMesages.showMessageToast;

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    private SettingsFragment mSettingsFragment;
    private StateFragment mStateFragment;
    private BodyFragment mBodyFragment;
    private BirthdayFragment mBirthdayFragment;
    private GrowthFragment mGrowthFragment;
    private BLEFragment mBLEFragment;
    private TargetWeightFragment mTargetWeightFragment;
    private GoogleFitFragment mGoogleFitFragment;

    private FragmentManager mFragmentManager;

    private ProfilePresenter mPresenter;

    private CirclePagerAdapter mAdapter;

    private int INNER_MARKER;
    public static final int MARKER_LOGIN = 1;
    public static final int MARKER_REGISTER = 2;
    public static final int MARKER_MAIN = 3;

    private boolean isConnected = false;

    private RegisterUser mRegisterUser;

    @Bind(R.id.walk_through_view_pager)
    ViewPager mViewPager;

    @Bind(R.id.circle_pager_indicator)
    CirclePageIndicator circlePageIndicator;

    @Bind(R.id.ll_footer_bar)
    RelativeLayout ll_footer_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mRegisterUser = new RegisterUser();

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ll_footer_bar.getWindowToken(), 0);

        INNER_MARKER = getIntent().getIntExtra(STATICS_PARAMS.INNER_MARKER_PROFILE, 1);
        isConnected = getIntent().getBooleanExtra(KEI_CONNECTION, false);

        mPresenter = new ProfilePresenterImpl(this, this);

        int lastSettingsFragment = SettingsApp.getInstance().getSettingsProfile();
        boolean lastProfile = SettingsApp.getInstance().getSettingsStatus();

        mFragmentManager = getSupportFragmentManager();

        int countPages = COUNT_PAGES_PROFILE_DEFOULT;
        if (INNER_MARKER == MARKER_MAIN) {
//            countPages = COUNT_PAGES_PROFILE_FROM_MAIN;
            circlePageIndicator.setVisibility(View.INVISIBLE);
            mViewPager.setVisibility(View.INVISIBLE);
            ll_footer_bar.setVisibility(View.INVISIBLE);
        }

        mAdapter = new CirclePagerAdapter(mFragmentManager, countPages);
        mViewPager.setPageMargin(0);
        mViewPager.setAdapter(mAdapter);
        setStartFragment();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                if (mBLEFragmentView != null)
                    mBLEFragmentView.showListDevisesDialog();
                if (mSettingsFragmentView != null)
                    mSettingsFragmentView.isConnectedBluetooth(true);
            } else {
                showMessageToast(getString(R.string.yuo_did_not_enable_ble));
                if (mBLEFragmentView != null)
                    mBLEFragmentView.setDefaultUI();
                if (mSettingsFragmentView != null)
                    mSettingsFragmentView.isConnectedBluetooth(false);
            }
//        } else if (requestCode == REQUEST_OAUTH && mGoogleFit != null) {
        } else if (requestCode == 65537 && mGoogleFit != null) {
            mGoogleFit.onResult(resultCode);
        }
    }

    private void setStartFragment() {
        switch (INNER_MARKER) {
            case MARKER_LOGIN:
            case MARKER_REGISTER:
                setEnterProfileDataFragment(SELECT_STATE, false, mRegisterUser);
                break;

            case MARKER_MAIN:
                setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, MARKER_MAIN);
                break;
        }
    }

    public void setSettingsFragment(String mStringExtra, int from) {
        mSettingsFragment = (SettingsFragment) getSupportFragmentManager()
                .findFragmentByTag(mStringExtra);
        if (mSettingsFragment == null) {
            mSettingsFragment = new SettingsFragment(this, this, mPresenter, INNER_MARKER, from);
        }
        ll_footer_bar.setVisibility(View.INVISIBLE);
        setFragment(mSettingsFragment, mStringExtra);
        if (from == MARKER_MAIN && isConnected) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onActivityResult(REQUEST_ENABLE_BT, RESULT_OK, null);
                }
            }, 25);
        }
    }

    public void setEnterProfileDataFragment(int mLastSettingsFragment, boolean mFromSettings,
                                            RegisterUser mRegisterUser) {
        String tagFragment = "";
        Fragment fragment = null;
        ll_footer_bar.setVisibility(View.VISIBLE);
        switch (mLastSettingsFragment) {
            case SELECT_STATE:
                tagFragment = STATE_FRAGMENT;
                mStateFragment = (StateFragment) getSupportFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mStateFragment == null) {
                    mStateFragment = new StateFragment(
                            this, mPresenter, INNER_MARKER, mFromSettings, mRegisterUser);
                }
                fragment = mStateFragment;
                circlePageIndicator.setViewPager(mViewPager, SELECT_STATE - 1);
                break;

            case TYPE_BODY:
                tagFragment = BODY_FRAGMENT;
                mBodyFragment = (BodyFragment) getSupportFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mBodyFragment == null) {
                    mBodyFragment = new BodyFragment(
                            this, mPresenter, INNER_MARKER, mFromSettings, mRegisterUser);
                }
                fragment = mBodyFragment;
                circlePageIndicator.setViewPager(mViewPager, TYPE_BODY - 1);
                break;

            case DATA_BIRTHDAY:
                tagFragment = BIRTHDAY_FRAGMENT;
                mBirthdayFragment = (BirthdayFragment) getSupportFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mBirthdayFragment == null) {
                    mBirthdayFragment = new BirthdayFragment(
                            this, mPresenter, INNER_MARKER, mFromSettings, mRegisterUser);
                }
                fragment = mBirthdayFragment;
                circlePageIndicator.setViewPager(mViewPager, DATA_BIRTHDAY - 1);
                break;

            case USER_GROWTH:
                tagFragment = GROWTH_FRAGMENT;
                mGrowthFragment = (GrowthFragment) getSupportFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mGrowthFragment == null) {
                    mGrowthFragment = new GrowthFragment(
                            this, mPresenter, INNER_MARKER, mFromSettings, mRegisterUser);
                }
                fragment = mGrowthFragment;
                circlePageIndicator.setViewPager(mViewPager, USER_GROWTH - 1);
                break;

            case CONNECT_BLE:
                tagFragment = CONNECT_BLE_FRAGMENT;
                mBLEFragment = (BLEFragment) getSupportFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mBLEFragment == null) {
                    mBLEFragment = new BLEFragment(this, mPresenter, INNER_MARKER, mFromSettings);
                }
                fragment = mBLEFragment;
                circlePageIndicator.setViewPager(mViewPager, CONNECT_BLE - 1);
                break;


            case TARGET_WEIGHT:
                tagFragment = TARGET_WEIGHT_FRAGMENT;
                mTargetWeightFragment = (TargetWeightFragment) getSupportFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mTargetWeightFragment == null) {
                    mTargetWeightFragment = new TargetWeightFragment(
                            this, mPresenter, INNER_MARKER, mFromSettings, mRegisterUser);
                }
                fragment = mTargetWeightFragment;
                circlePageIndicator.setViewPager(mViewPager, TARGET_WEIGHT - 2);
                break;

            case GOOGLE_FIT:
                tagFragment = GOOGLE_FIT_FRAGMENT;
                mGoogleFitFragment = (GoogleFitFragment) getSupportFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mGoogleFitFragment == null) {
                    mGoogleFitFragment = new GoogleFitFragment(this, mPresenter, INNER_MARKER, mFromSettings);
                }
                fragment = mGoogleFitFragment;
                circlePageIndicator.setViewPager(mViewPager, GOOGLE_FIT - 2);
                break;

        }
        setFragment(fragment, tagFragment);
    }

    private void setFragment(Fragment mFragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.container_fragmentsy, mFragment, tag)
                .commit();
    }

    public void logout() {
        SettingsApp.getInstance().setUserName("");
        SettingsApp.getInstance().setUserPassword("");
        SettingsApp.getInstance().setProfileBLE(1);
        final Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.putExtra(STATICS_PARAMS.INNER_MARKER_PROFILE, ProfileActivity.MARKER_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    public void onBackPressedFromState() {
        switch (INNER_MARKER) {
            case MARKER_LOGIN:
            case MARKER_REGISTER:
                mPresenter.goToLoginActivity();
                finish();
                break;

            case MARKER_MAIN:
                onBackPressed();
                break;
        }
    }

    // init listeneer BLE View
    private SettingsFragmentView mSettingsFragmentView;

    public void initListenerSettingsFragment(SettingsFragmentView mSettingsFragmentView) {
        this.mSettingsFragmentView = mSettingsFragmentView;
    }

    // init listeneer BLE View
    private BLEFragmentView mBLEFragmentView;

    public void initListenerBleFragment(BLEFragmentView mBLEFragmentView) {
        this.mBLEFragmentView = mBLEFragmentView;
    }


    //    ===================================================
    private ListenerGoogleFit mGoogleFit;

    public void setListenerGoogleFit(ListenerGoogleFit mGoogleFit) {
        this.mGoogleFit = mGoogleFit;
    }

    public interface ListenerGoogleFit {
        void onResult(int mRequestCode);
    }

}
