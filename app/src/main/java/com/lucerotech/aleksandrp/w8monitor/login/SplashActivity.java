package com.lucerotech.aleksandrp.w8monitor.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.lucerotech.aleksandrp.w8monitor.R;

/**
 * Created by AleksandrP on 13.09.2016.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler mHandlerStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            };
    }
}
