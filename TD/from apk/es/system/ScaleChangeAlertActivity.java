/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.Button
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.system;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.lefu.es.constant.AppData;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.system.LoadingActivity;
import com.lefu.es.util.SharedPreferencesUtil;

public class ScaleChangeAlertActivity
extends Activity {
    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (LoadingActivity.isPad) {
            this.setRequestedOrientation(2);
        } else {
            this.setRequestedOrientation(1);
        }
        this.requestWindowFeature(5);
        this.requestWindowFeature(1);
        this.setContentView(2130903044);
        if (UtilConstants.su == null) {
            UtilConstants.su = new SharedPreferencesUtil((Context)this);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && keyEvent.getRepeatCount() == 0) {
            this.finish();
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void saveOrCancle(View view) {
        if (!(view instanceof Button)) return;
        switch (view.getId()) {
            default: {
                return;
            }
            case 2131296341: {
                UtilConstants.su.editSharedPreferences("lefuconfig", "reCheck", false);
                this.finish();
                return;
            }
            case 2131296342: 
        }
        AppData.reCheck = true;
        UtilConstants.su.editSharedPreferences("lefuconfig", "reCheck", true);
        this.finish();
    }
}

