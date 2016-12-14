/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.List
 */
package com.lefu.es.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.UserService;
import com.lefu.es.system.AutoBLEActivity;
import com.lefu.es.system.AutoBTActivity;
import com.lefu.es.system.BabyScaleActivity;
import com.lefu.es.system.BathScaleActivity;
import com.lefu.es.system.BodyFatScaleActivity;
import com.lefu.es.system.KitchenScaleActivity;
import com.lefu.es.system.UserAddActivity;
import com.lefu.es.util.SharedPreferencesUtil;
import java.util.List;

public class LoadingActivity
extends Activity {
    private static final String TAG = LoadingActivity.class.getSimpleName();
    public static boolean isPad = false;
    public static boolean isV = true;
    public static LoadingActivity mainActivty = null;
    private UserService uservice;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void getFirstUser() {
        List<UserModel> list;
        try {
            list = this.uservice.getAllDatas();
            if (list == null) return;
        }
        catch (Exception var1_2) {
            var1_2.printStackTrace();
            return;
        }
        if (list.size() <= 0) return;
        UtilConstants.CURRENT_USER = (UserModel)list.get(0);
        UtilConstants.SELECT_USER = ((UserModel)list.get(0)).getId();
    }

    private void isPad() {
        new DisplayMetrics();
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int n = displayMetrics.widthPixels;
        int n2 = displayMetrics.heightPixels;
        float f = displayMetrics.density;
        if (Math.sqrt((double)(Math.pow((double)n, (double)2.0) + Math.pow((double)n2, (double)2.0))) / (double)(160.0f * f) >= 6.0) {
            isPad = true;
        }
        if (n > n2) {
            isV = false;
            return;
        }
        isV = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void loadData() {
        UtilConstants.SELECT_USER = (Integer)UtilConstants.su.readbackUp("lefuconfig", "user", 0);
        if (UtilConstants.SELECT_USER == 0) {
            this.getFirstUser();
        } else {
            try {
                UtilConstants.CURRENT_USER = this.uservice.find(UtilConstants.SELECT_USER);
                if (UtilConstants.CURRENT_USER == null) {
                    this.getFirstUser();
                }
            }
            catch (Exception var1_2) {
                var1_2.printStackTrace();
            }
        }
        if (UtilConstants.CURRENT_USER == null) {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            intent.setClass((Context)this, (Class)UserAddActivity.class);
            this.startActivity(intent);
            return;
        }
        UtilConstants.CURRENT_SCALE = UtilConstants.CURRENT_USER.getScaleType();
        String string2 = (String)UtilConstants.su.readbackUp("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), String.class);
        if (string2 != null && !"".equals((Object)string2)) {
            UtilConstants.su.editSharedPreferences("lefuconfig", "reCheck", false);
            Intent intent = new Intent();
            if (UtilConstants.BATHROOM_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                intent.setClass((Context)this, (Class)BathScaleActivity.class);
                Log.e((String)TAG, (String)"to BathScaleActivity");
            } else if (UtilConstants.BABY_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                intent.setClass((Context)this, (Class)BabyScaleActivity.class);
                Log.e((String)TAG, (String)"to BabyScaleActivity");
            } else if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                intent.setClass((Context)this, (Class)KitchenScaleActivity.class);
                Log.e((String)TAG, (String)"to KitchenScaleActivity");
            } else {
                intent.setClass((Context)this, (Class)BodyFatScaleActivity.class);
                Log.e((String)TAG, (String)"to BodyFatScaleActivity");
            }
            intent.putExtra("ItemID", UtilConstants.SELECT_USER);
            this.startActivity(intent);
            return;
        }
        int n = Build.VERSION.SDK_INT;
        Intent intent = new Intent();
        intent.setFlags(268435456);
        if (n < 18) {
            intent.setClass((Context)this, (Class)AutoBTActivity.class);
        } else {
            intent.setClass((Context)this, (Class)AutoBLEActivity.class);
        }
        this.startActivity(intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903057);
        mainActivty = this;
        this.isPad();
        UtilConstants.su = new SharedPreferencesUtil((Context)this);
        UtilConstants.FIRST_INSTALL_BABY_SCALE = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_baby_scale", "");
        UtilConstants.FIRST_INSTALL_BATH_SCALE = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_bath_scale", "");
        UtilConstants.FIRST_INSTALL_BODYFAT_SCALE = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_bodyfat_scale", "");
        UtilConstants.FIRST_INSTALL_KITCHEN_SCALE = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_kitchen_scale", "");
        UtilConstants.FIRST_INSTALL_DETAIL = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_detail", "");
        UtilConstants.FIRST_INSTALL_DAILOG = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_dailog", "");
        UtilConstants.FIRST_INSTALL_SHARE = (String)UtilConstants.su.readbackUp("lefuconfig", "first_install_share", "");
        UtilConstants.FIRST_RECEIVE_BODYFAT_SCALE_KEEP_STAND_WITH_BARE_FEET = (String)UtilConstants.su.readbackUp("lefuconfig", "first_badyfat_scale_keep_stand_with_bare_feet", "");
        this.uservice = new UserService((Context)this);
        ExitApplication.getInstance().addActivity(this);
        UtilConstants.checkScaleTimes = 0;
        UtilConstants.su = new SharedPreferencesUtil((Context)this);
        UtilConstants.SELECT_LANGUAGE = 1;
    }

    protected void onDestroy() {
        if (UtilConstants.serveIntent != null) {
            this.stopService(UtilConstants.serveIntent);
        }
        System.exit((int)0);
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        this.loadData();
    }
}

