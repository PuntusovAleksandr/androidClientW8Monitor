package com.lucerotech.aleksandrp.w8monitor.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

/**
 * Created by AleksandrP on 13.09.2016.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler mHandlerStart;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean themeDark = SettingsApp.getInstance().isThemeDark();
        int modeNightNo = 0;
        if (themeDark) {
            modeNightNo = AppCompatDelegate.MODE_NIGHT_NO;
        } else {
            modeNightNo = AppCompatDelegate.MODE_NIGHT_YES;
        }
        AppCompatDelegate.setDefaultNightMode(modeNightNo);
        boolean autoLogin = SettingsApp.getInstance().getAutoLogin();
        if (autoLogin) {
            String userName = SettingsApp.getInstance().getUserName();
            String userPassword = SettingsApp.getInstance().getUserPassword();
            if (!userName.isEmpty() && !userPassword.isEmpty()) {
                intent = new Intent(SplashActivity.this, ProfileActivity.class);
            } else {
                SettingsApp.getInstance().setAutoLogin(false);
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }

        // init handler
        mHandlerStart = new Handler();
        Runnable startRun = initRunableStart();

        {
            // TODO: 14.09.2016 нужно сделать проверку на подключение блютуза и прочих модулей
            // TODO: 14.09.2016 нужно сделать проверку на нвсе пермишины для андроид6
            mHandlerStart.postDelayed(startRun, 2000);
        }

    }

    @NonNull
    private Runnable initRunableStart() {
        return new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
    }
}
