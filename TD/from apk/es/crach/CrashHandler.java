/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Environment
 *  android.os.Process
 *  android.util.Log
 *  android.widget.Toast
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.PrintWriter
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Thread$UncaughtExceptionHandler
 *  java.lang.Throwable
 *  java.lang.reflect.Field
 *  java.text.DateFormat
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.lefu.es.crach;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressLint(value={"SdCardPath"})
public class CrashHandler
implements Thread.UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    public static final String TAG = "CrashHandler";
    @SuppressLint(value={"SimpleDateFormat"})
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private Map<String, String> infos = new HashMap();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        this.collectDeviceInfo(this.mContext);
        this.saveCrashInfo2File(throwable);
        return true;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String saveCrashInfo2File(Throwable throwable) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator iterator = this.infos.entrySet().iterator();
        do {
            if (!iterator.hasNext()) break;
            Map.Entry entry = (Map.Entry)iterator.next();
            String string2 = (String)entry.getKey();
            String string3 = (String)entry.getValue();
            stringBuffer.append(String.valueOf((Object)string2) + "=" + string3 + "\n");
        } while (true);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer)stringWriter);
        throwable.printStackTrace(printWriter);
        Throwable throwable2 = throwable.getCause();
        do {
            if (throwable2 == null) {
                printWriter.close();
                stringBuffer.append(stringWriter.toString());
                long l = System.currentTimeMillis();
                String string4 = this.formatter.format(new Date());
                String string5 = "crash-" + string4 + "-" + l + ".log";
                if (!Environment.getExternalStorageState().equals((Object)"mounted")) return string5;
                File file = new File("/sdcard/crash/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf((Object)"/sdcard/crash/") + string5);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.close();
                return string5;
            }
            throwable2.printStackTrace(printWriter);
            throwable2 = throwable2.getCause();
        } while (true);
        catch (Exception exception) {
            Log.e((String)"CrashHandler", (String)"an error occured while writing file...", (Throwable)exception);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (packageInfo != null) {
                String string2 = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String string3 = String.valueOf((int)packageInfo.versionCode);
                this.infos.put((Object)"versionName", (Object)string2);
                this.infos.put((Object)"versionCode", (Object)string3);
            }
        }
        catch (PackageManager.NameNotFoundException var2_5) {
            Log.e((String)"CrashHandler", (String)"an error occured when collect package info", (Throwable)var2_5);
        }
        Field[] arrfield = Build.class.getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            Field field = arrfield[n2];
            try {
                field.setAccessible(true);
                this.infos.put((Object)field.getName(), (Object)field.get((Object)null).toString());
                Log.d((String)"CrashHandler", (String)(String.valueOf((Object)field.getName()) + " : " + field.get((Object)null)));
            }
            catch (Exception var8_10) {
                Log.e((String)"CrashHandler", (String)"an error occured when collect crash info", (Throwable)var8_10);
            }
            ++n2;
        }
        return;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)this);
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!this.handleException(throwable) && this.mDefaultHandler != null) {
            Toast.makeText((Context)this.mContext, (CharSequence)"Program encountered problems", (int)1).show();
            this.mDefaultHandler.uncaughtException(thread, throwable);
            return;
        }
        Process.killProcess((int)Process.myPid());
        System.exit((int)1);
    }
}

