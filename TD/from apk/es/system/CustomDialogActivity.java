/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lefu.es.system.LoadingActivity;

public class CustomDialogActivity
extends Activity {
    public static Handler handler;
    LinearLayout layout1;
    LinearLayout layout2;
    Button okBtn;
    ProgressBar pbar1;
    TextView tv1;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @SuppressLint(value={"HandlerLeak"})
    protected void onCreate(Bundle var1_1) {
        super.onCreate(var1_1);
        if (LoadingActivity.isPad) {
            this.setRequestedOrientation(2);
        } else {
            this.setRequestedOrientation(1);
        }
        this.requestWindowFeature(5);
        this.requestWindowFeature(1);
        this.setContentView(2130903089);
        this.layout1 = (LinearLayout)this.findViewById(2131296552);
        this.layout2 = (LinearLayout)this.findViewById(2131296379);
        this.tv1 = (TextView)this.findViewById(2131296296);
        this.pbar1 = (ProgressBar)this.findViewById(2131296553);
        this.okBtn = (Button)this.findViewById(2131296554);
        this.okBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                CustomDialogActivity.this.finish();
            }
        });
        var4_2 = this.getIntent().getExtras();
        try {
            var8_3 = var4_2.containsKey("error");
            var6_4 = null;
            if (var8_3) {
                var6_4 = var9_5 = var4_2.getString("error");
            }
            ** GOTO lbl-1000
        }
        catch (Exception var5_6) {
            var6_4 = null;
        }
        if (var6_4 != null && "2".equals((Object)var6_4)) {
            this.layout1.setVisibility(8);
            this.layout2.setVisibility(0);
        } else if (var6_4 != null && "3".equals((Object)var6_4)) {
            this.layout1.setVisibility(0);
            this.okBtn.setVisibility(0);
            this.layout2.setVisibility(8);
            this.pbar1.setVisibility(8);
            this.tv1.setText(this.getText(2131361883));
        } else lbl-1000: // 2 sources:
        {
            this.layout1.setVisibility(0);
            this.layout2.setVisibility(8);
        }
        CustomDialogActivity.handler = new Handler(){

            /*
             * Enabled aggressive block sorting
             */
            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        CustomDialogActivity.this.finish();
                        break;
                    }
                    case 1: {
                        CustomDialogActivity.this.layout1.setVisibility(8);
                        CustomDialogActivity.this.layout2.setVisibility(0);
                        CustomDialogActivity.this.finish();
                        break;
                    }
                    case 2: {
                        CustomDialogActivity.this.finish();
                    }
                }
                super.handleMessage(message);
            }
        };
        CustomDialogActivity.handler.postDelayed(new Runnable(){

            public void run() {
                CustomDialogActivity.this.finish();
            }
        }, 10000);
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

