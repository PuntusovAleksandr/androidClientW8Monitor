/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.widget.TextView
 *  java.io.PrintStream
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.lefu.es.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.BluetoothChatService;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.UserService;
import com.lefu.es.system.ReceiveAlertActivity;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.StringUtils;

import java.io.Serializable;

@SuppressLint(value={"HandlerLeak"})
public class TimeService
extends Service {
    static boolean isdoings = false;
    public static String scaleType;
    public static TextView scale_connect_state;
    private int count = 0;
    private final Handler handler;
    private RecordService recordService;
    private UserService uservice;

    static {
        scale_connect_state = null;
    }

    public TimeService() {
        this.handler = new Handler(){

            /*
             * Exception decompiling
             */
            public void handleMessage(Message var1_1) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
                // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:486)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:334)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:527)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
                // org.benf.cfr.reader.Main.doJar(Main.java:128)
                // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
                // java.lang.Thread.run(Thread.java:841)
                throw new IllegalStateException("Decompilation failed");
            }
        };
    }

    static /* synthetic */ void access$0(TimeService timeService, int n) {
        timeService.count = n;
    }

    static /* synthetic */ void access$1(TimeService timeService) {
        timeService.save2Device();
    }

    static /* synthetic */ int access$2(TimeService timeService) {
        return timeService.count;
    }

    static /* synthetic */ void access$3(TimeService timeService) {
        timeService.send_shutdown();
    }

    static /* synthetic */ UserService access$4(TimeService timeService) {
        return timeService.uservice;
    }

    static /* synthetic */ RecordService access$5(TimeService timeService) {
        return timeService.recordService;
    }

    public static boolean isIsdoing() {
        reference var2 = TimeService.class;
        synchronized (TimeService.class) {
            boolean bl = isdoings;
            // ** MonitorExit[var2] (shouldn't be in output)
            return bl;
        }
    }

    private void save2Device() {
        if (BluetoolUtil.mChatService != null) {
            String string2 = MyUtil.getUserInfo();
            System.out.println("\u53d1\u9001\u6570\u636e\uff1a" + string2);
            byte[] arrby = StringUtils.hexStringToByteArray(string2);
            BluetoolUtil.mChatService.write(arrby);
        }
    }

    private void send_shutdown() {
        if (BluetoolUtil.mChatService != null) {
            byte[] arrby = StringUtils.hexStringToByteArray(UtilConstants.SCALE_ORDER_SHUTDOWN);
            BluetoolUtil.mChatService.write(arrby);
        }
    }

    public static void setIsdoing(boolean bl) {
        reference var2_1 = TimeService.class;
        synchronized (TimeService.class) {
            isdoings = bl;
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return;
        }
    }

    public void dueDate(String string2) {
        TimeService timeService = this;
        synchronized (timeService) {
            if (!isdoings) {
                TimeService.setIsdoing(true);
                Intent intent = new Intent();
                intent.setFlags(268435456);
                intent.putExtra("duedate", string2);
                Records records = MyUtil.parseMeaage(this.recordService, string2);
                if (records.getScaleType() != null && records.getScaleType().equalsIgnoreCase(UtilConstants.CURRENT_SCALE) && records != null && records.getUgroup() != null && Integer.parseInt((String)records.getUgroup().replace((CharSequence)"P", (CharSequence)"")) < 10) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("record", (Serializable)records);
                    intent.putExtras(bundle);
                    intent.setClass(this.getApplicationContext(), (Class)ReceiveAlertActivity.class);
                    this.startActivity(intent);
                }
            }
            return;
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        this.uservice = new UserService((Context)this);
        this.recordService = new RecordService((Context)this);
        if (BluetoolUtil.mChatService == null) {
            BluetoolUtil.mChatService = new BluetoothChatService((Context)this, this.handler);
        }
        if (BluetoolUtil.mChatService != null && BluetoolUtil.mChatService.getState() == 0) {
            BluetoolUtil.mChatService.start();
        }
        if (UtilConstants.CURRENT_USER == null) {
            UtilConstants.CURRENT_USER = (UserModel)JSONObject.parseObject((String)UtilConstants.su.readbackUp("lefuconfig", "addUser", ""), UserModel.class);
        }
    }

    public void onDestroy() {
        this.stopSelf();
        super.onDestroy();
    }

    public void onStart(Intent intent, int n) {
        super.onStart(intent, n);
    }

}

