package com.lucerotech.aleksandrp.w8monitor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.presentts.HelpPresent;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.HelpView;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen1;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen2;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen3;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen4;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen5;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen6;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen7;
import com.lucerotech.aleksandrp.w8monitor.fragments.help.fragments.FragmentScreen8;
import com.lucerotech.aleksandrp.w8monitor.presents.help.impl.HelpPresentImpl;
import com.lucerotech.aleksandrp.w8monitor.utils.SetThemeDark;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import butterknife.ButterKnife;

import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_1;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_2;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_3;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_4;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_5;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_6;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_7;
import static com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker.SCREEN_8;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.KEY_EXTRA_FROM;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.KEY_FROM_SPLASH;

public class HelpActivity extends AppCompatActivity implements HelpView {

    private HelpPresent mPresent;

    private FragmentScreen1 mFragmentScreen1;
    private FragmentScreen2 mFragmentScreen2;
    private FragmentScreen3 mFragmentScreen3;
    private FragmentScreen4 mFragmentScreen4;
    private FragmentScreen5 mFragmentScreen5;
    private FragmentScreen6 mFragmentScreen6;
    private FragmentScreen7 mFragmentScreen7;
    private FragmentScreen8 mFragmentScreen8;

    private int extraFrom;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        extraFrom = getIntent().getIntExtra(KEY_EXTRA_FROM, KEY_FROM_SPLASH);

        mPresent = new HelpPresentImpl(this);

        mFragmentManager = getSupportFragmentManager();
        setStartFragment(SCREEN_1);
    }

    private void setStartFragment(String mScreen) {
        Fragment fragment = null;
        switch (mScreen) {
            case SCREEN_1:
                mFragmentScreen1 = (FragmentScreen1) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen1 == null) {
                    mFragmentScreen1 = new FragmentScreen1(this, extraFrom);
                }
                fragment = mFragmentScreen1;
                break;

            case SCREEN_2:
                mFragmentScreen2 = (FragmentScreen2) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen2 == null) {
                    mFragmentScreen2 = new FragmentScreen2(this);
                }
                fragment = mFragmentScreen2;
                break;

            case SCREEN_3:
                mFragmentScreen3 = (FragmentScreen3) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen3 == null) {
                    mFragmentScreen3 = new FragmentScreen3(this);
                }
                fragment = mFragmentScreen3;
                break;

            case SCREEN_4:
                mFragmentScreen4 = (FragmentScreen4) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen4 == null) {
                    mFragmentScreen4 = new FragmentScreen4(this);
                }
                fragment = mFragmentScreen4;
                break;

            case SCREEN_5:
                mFragmentScreen5 = (FragmentScreen5) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen5 == null) {
                    mFragmentScreen5 = new FragmentScreen5(this);
                }
                fragment = mFragmentScreen5;
                break;

            case SCREEN_6:
                mFragmentScreen6 = (FragmentScreen6) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen6 == null) {
                    mFragmentScreen6 = new FragmentScreen6(this);
                }
                fragment = mFragmentScreen6;
                break;

            case SCREEN_7:
                mFragmentScreen7 = (FragmentScreen7) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen7 == null) {
                    mFragmentScreen7 = new FragmentScreen7(this);
                }
                fragment = mFragmentScreen7;
                break;

            case SCREEN_8:
                mFragmentScreen8 = (FragmentScreen8) getSupportFragmentManager()
                        .findFragmentByTag(mScreen);
                if (mFragmentScreen8 == null) {
                    mFragmentScreen8 = new FragmentScreen8(this);
                }
                fragment = mFragmentScreen8;
                break;
        }
        setFragment(fragment, mScreen);
    }


    private void setFragment(Fragment mFragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.container_fragments_help, mFragment, tag)
                .commit();
    }

    //================================
//        from HelpView
//================================
    @Override
    public void goToBackMainActivity() {
        finish();
    }

    @Override
    public void goToNextFragment(String mScreen) {
        setStartFragment(mScreen);
    }

    @Override
    public void goToBackFragment(String mScreen) {
        setStartFragment(mScreen);
    }

    @Override
    public void finishHelp() {
        SettingsApp.getInstance().setFirstStart(false);
        Intent intent;
        if (extraFrom == KEY_FROM_SPLASH) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
