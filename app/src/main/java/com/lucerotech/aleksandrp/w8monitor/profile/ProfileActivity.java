package com.lucerotech.aleksandrp.w8monitor.profile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.login.LoginActivity;
import com.lucerotech.aleksandrp.w8monitor.profile.fragment.BLEFragment;
import com.lucerotech.aleksandrp.w8monitor.profile.fragment.BirthdayFragment;
import com.lucerotech.aleksandrp.w8monitor.profile.fragment.BodyFragment;
import com.lucerotech.aleksandrp.w8monitor.profile.fragment.GrowthFragment;
import com.lucerotech.aleksandrp.w8monitor.profile.fragment.SettingsFragment;
import com.lucerotech.aleksandrp.w8monitor.profile.fragment.StateFragment;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import butterknife.ButterKnife;

import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.BIRTHDAY_FRAGMENT;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.BODY_FRAGMENT;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.CONNECT_BLE;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.CONNECT_BLE_FRAGMENT;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.DATA_BIRTHDAY;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.GROWTH_FRAGMENT;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.SELECT_STATE;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.STATE_FRAGMENT;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.TYPE_BODY;
import static com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker.USER_GROWTH;

public class ProfileActivity extends AppCompatActivity {

    private SettingsFragment mSettingsFragment;
    private StateFragment mStateFragment;
    private BodyFragment mBodyFragment;
    private BirthdayFragment mBirthdayFragment;
    private GrowthFragment mGrowthFragment;
    private BLEFragment mBLEFragment;

    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        int lastSettingsFragment = SettingsApp.getInstance().getSettingsProfile();
        boolean lastProfile = SettingsApp.getInstance().getLastProfile();

        mFragmentManager = getFragmentManager();

        if (lastProfile) {
            setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT);
        } else {
            setEnterProfileDataFragment(lastSettingsFragment);
        }

    }

    public void setSettingsFragment(String mStringExtra) {
        mSettingsFragment = (SettingsFragment) getFragmentManager()
                .findFragmentByTag(mStringExtra);
        if (mSettingsFragment == null) {
            mSettingsFragment = new SettingsFragment();
        }
        setFragment(mSettingsFragment, mStringExtra);
    }

    public void setEnterProfileDataFragment(int mLastSettingsFragment) {
        String tagFragment = "";
        Fragment fragment = null;
        switch (mLastSettingsFragment) {
            case SELECT_STATE:
                tagFragment = STATE_FRAGMENT;
                mStateFragment = (StateFragment) getFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mStateFragment == null) {
                    mStateFragment = new StateFragment();
                }
                fragment = mStateFragment;
                break;

            case TYPE_BODY:
                tagFragment = BODY_FRAGMENT;
                mBodyFragment = (BodyFragment) getFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mBodyFragment == null) {
                    mBodyFragment = new BodyFragment();
                }
                fragment = mBodyFragment;
                break;

            case DATA_BIRTHDAY:
                tagFragment = BIRTHDAY_FRAGMENT;
                mBirthdayFragment = (BirthdayFragment) getFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mBirthdayFragment == null) {
                    mBirthdayFragment = new BirthdayFragment();
                }
                fragment = mBirthdayFragment;
                break;

            case USER_GROWTH:
                tagFragment = GROWTH_FRAGMENT;
                mGrowthFragment = (GrowthFragment) getFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mGrowthFragment == null) {
                    mGrowthFragment = new GrowthFragment();
                }
                fragment = mGrowthFragment;
                break;

            case CONNECT_BLE:
                tagFragment = CONNECT_BLE_FRAGMENT;
                mBLEFragment = (BLEFragment) getFragmentManager()
                        .findFragmentByTag(tagFragment);
                if (mBLEFragment == null) {
                    mBLEFragment = new BLEFragment();
                }
                fragment = mBLEFragment;
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
        SettingsApp.getInstance().setAutoLogin(false);
        final Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
