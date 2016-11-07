package com.lucerotech.aleksandrp.w8monitor.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.ble.BluetoothHandler;
import com.lucerotech.aleksandrp.w8monitor.general.MainActivity;
import com.lucerotech.aleksandrp.w8monitor.help.HelpActivity;
import com.lucerotech.aleksandrp.w8monitor.utils.SetLocaleApp;
import com.lucerotech.aleksandrp.w8monitor.utils.SetThemeDark;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import io.fabric.sdk.android.Fabric;

import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.KEY_EXTRA_FROM;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.KEY_FROM_SPLASH;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.REQUEST_ENABLE_BT;
import static com.lucerotech.aleksandrp.w8monitor.utils.ShowMesages.showMessageToast;

/**
 * Created by AleksandrP on 13.09.2016.
 */

public class SplashActivity extends AppCompatActivity implements
        BluetoothHandler.onResultScanDevice {

    private Handler mHandlerStart;
    private Intent intent;

    private BluetoothHandler bluetoothHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        SetLocaleApp.setLocale();
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        bluetoothHandler = new BluetoothHandler(this, this);

// TODO: 04.10.2016  пока убрана проверка  и поставлен переход на сл. окно
        if (!bluetoothHandler.checkSupport()) {
            return;
        }
//        goToNextActivity();
    }

    // start for next activity
    private void goToNextActivity() {
        if (!SettingsApp.getInstance().isFirstStart()) {
            boolean autoLogin = SettingsApp.getInstance().getAutoLogin();
            if (autoLogin) {
                String userName = SettingsApp.getInstance().getUserName();
                String userPassword = SettingsApp.getInstance().getUserPassword();
                if (!userName.isEmpty() && !userPassword.isEmpty()
//                        && !userName.equals(STATICS_PARAMS.TEST_USER)
                        ) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
//                    SettingsApp.getInstance().setAutoLogin(false);
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
        } else {
            intent = new Intent(SplashActivity.this, HelpActivity.class);
            intent.putExtra(KEY_EXTRA_FROM, KEY_FROM_SPLASH);
        }

        // init handler
        mHandlerStart = new Handler();
        Runnable startRun = initRunableStart();

        {
            // TODO: 14.09.2016 нужно сделать проверку на подключение блютуза и прочих модулей
            // TODO: 14.09.2016 нужно сделать проверку на нвсе пермишины для андроид6
            mHandlerStart.postDelayed(startRun, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode != RESULT_OK) {
                showMessageToast(getString(R.string.yuo_did_not_enable_ble));
            }
            goToNextActivity();
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

    //    ===========================================================
//            answer from onResultScanDevice
//    ===========================================================
    @Override
    public void scanOk(boolean mEnabled) {
        goToNextActivity();
    }

}
