/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.KeyEvent
 */
package com.lefu.es.system;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import com.lefu.es.system.LoadingActivity;

public class GetFatDateErrorActivity
extends Activity {
    public static Handler handler;

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903049);
        if (LoadingActivity.isPad) {
            this.setRequestedOrientation(2);
        } else {
            this.setRequestedOrientation(1);
        }
        this.requestWindowFeature(5);
        this.requestWindowFeature(1);
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && keyEvent.getRepeatCount() == 0) {
            this.finish();
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onPause() {
        this.finish();
        super.onPause();
    }
}

