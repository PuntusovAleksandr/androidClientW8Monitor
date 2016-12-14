/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.NotificationManager
 *  android.content.Context
 *  android.content.Intent
 *  android.media.AudioManager
 *  android.media.SoundPool
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.Window
 *  android.widget.Button
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 */
package com.lefu.es.system;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import com.lefu.es.ble.BlueSingleton;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.db.RecordDao;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.TimeService;
import com.lefu.es.service.UserService;
import com.lefu.es.system.BabyScaleActivity;
import com.lefu.es.system.BathScaleActivity;
import com.lefu.es.system.BodyFatScaleActivity;
import com.lefu.es.system.KitchenScaleActivity;
import com.lefu.es.system.LoadingActivity;

public class ReceiveAlertActivity
extends Activity {
    String readMessage;
    private Records recod;
    private RecordService recordService;
    private SoundPool soundpool;
    private UserService uservice;

    static /* synthetic */ void access$0(ReceiveAlertActivity receiveAlertActivity, SoundPool soundPool) {
        receiveAlertActivity.soundpool = soundPool;
    }

    static /* synthetic */ SoundPool access$1(ReceiveAlertActivity receiveAlertActivity) {
        return receiveAlertActivity.soundpool;
    }

    private boolean isOutOfBounds(Activity activity, MotionEvent motionEvent) {
        int n = (int)motionEvent.getX();
        int n2 = (int)motionEvent.getY();
        int n3 = ViewConfiguration.get((Context)activity).getScaledWindowTouchSlop();
        View view = activity.getWindow().getDecorView();
        if (n >= - n3 && n2 >= - n3 && n <= n3 + view.getWidth() && n2 <= n3 + view.getHeight()) {
            return false;
        }
        return true;
    }

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
        this.setContentView(2130903059);
        this.uservice = new UserService((Context)this);
        this.recordService = new RecordService((Context)this);
        Bundle bundle2 = this.getIntent().getExtras();
        this.recod = (Records)this.getIntent().getSerializableExtra("record");
        this.readMessage = bundle2.getString("duedate");
        this.playSound();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.uservice != null) {
            this.uservice.closeDB();
        }
        if (this.recordService != null) {
            this.recordService.closeDB();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && keyEvent.getRepeatCount() == 0) {
            this.finish();
            if (!BluetoolUtil.bleflag) {
                TimeService.setIsdoing(false);
                do {
                    return true;
                    break;
                } while (true);
            }
            BlueSingleton.setIsdoing(false);
            return true;
        }
        if (n != 3) return super.onKeyDown(n, keyEvent);
        ((NotificationManager)this.getSystemService("notification")).cancel(0);
        return super.onKeyDown(n, keyEvent);
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.isOutOfBounds(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void playSound() {
        new Thread(new Runnable(){

            /*
             * Unable to fully structure code
             * Enabled aggressive exception aggregation
             */
            public void run() {
                ReceiveAlertActivity.access$0(ReceiveAlertActivity.this, new SoundPool(10, 1, 5));
                var1_1 = ReceiveAlertActivity.access$1(ReceiveAlertActivity.this).load((Context)ReceiveAlertActivity.this, 2131034113, 0);
                var2_2 = ((AudioManager)ReceiveAlertActivity.this.getSystemService("audio")).getStreamVolume(3);
                try {
                    Thread.sleep((long)500);
                }
                catch (Exception var3_3) {
                    ** continue;
                }
lbl8: // 2 sources:
                do {
                    ReceiveAlertActivity.access$1(ReceiveAlertActivity.this).play(var1_1, (float)var2_2, (float)var2_2, 1, 0, 1.0f);
                    return;
                    break;
                } while (true);
            }
        }).start();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void saveOrCancle(View view) {
        if (!(view instanceof Button)) return;
        switch (view.getId()) {
            default: {
                return;
            }
            case 2131296341: {
                if (!BluetoolUtil.bleflag) {
                    TimeService.setIsdoing(false);
                } else {
                    BlueSingleton.setIsdoing(false);
                }
                this.finish();
                return;
            }
            case 2131296342: 
        }
        AppData.hasCheckData = true;
        if (!BluetoolUtil.bleflag) {
            TimeService.setIsdoing(true);
        } else {
            BlueSingleton.setIsdoing(true);
        }
        if (this.recod != null && this.recod.getScaleType() != null) {
            if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                boolean bl = TextUtils.isEmpty((CharSequence)this.recod.getRphoto());
                NutrientBo nutrientBo = null;
                if (!bl) {
                    nutrientBo = CacheHelper.queryNutrientsByName(this.recod.getRphoto());
                }
                RecordDao.dueKitchenDate(this.recordService, this.readMessage, nutrientBo);
            } else {
                RecordDao.handleData(this.recordService, this.recod, this.readMessage);
            }
            if (!BluetoolUtil.bleflag) {
                TimeService.setIsdoing(false);
            } else {
                BlueSingleton.setIsdoing(false);
            }
            Intent intent = new Intent();
            if (UtilConstants.BATHROOM_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                intent.setClass((Context)this, (Class)BathScaleActivity.class);
            } else if (UtilConstants.BODY_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                intent.setClass((Context)this, (Class)BodyFatScaleActivity.class);
            } else if (UtilConstants.BABY_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                intent.setClass((Context)this, (Class)BabyScaleActivity.class);
            } else if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                intent.setClass((Context)this, (Class)KitchenScaleActivity.class);
            }
            intent.putExtra("ItemID", UtilConstants.CURRENT_USER.getId());
            intent.addFlags(131072);
            this.startActivity(intent);
        }
        this.finish();
    }

}

