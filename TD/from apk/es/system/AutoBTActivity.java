/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Parcelable
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.Toast
 *  java.io.PrintStream
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lefu.es.constant.AppData;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.progressbar.NumberProgressBar;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.TimeService;
import com.lefu.es.system.AutoBLEActivity;
import com.lefu.es.system.LoadingActivity;
import com.lefu.es.system.SearchDevicesView;
import com.lefu.es.system.UserEditActivity;
import com.lefu.es.util.SharedPreferencesUtil;

@SuppressLint(value={"HandlerLeak"})
public class AutoBTActivity
extends Activity {
    private static int checkTime = 30000;
    private static boolean isOnlySupprotBT = false;
    private static boolean isRegisterReceiver = false;
    View.OnClickListener OnClickListener;
    private Runnable ScanRunnable;
    private Runnable TimeoutRunnable;
    private Button backButton;
    private NumberProgressBar bnp;
    private BluetoothDevice connectdevice;
    Handler handler;
    private boolean isBack = false;
    private boolean keepScaleWorking = true;
    private BluetoothAdapter mBtAdapter;
    private final BroadcastReceiver mReceiver;
    private SearchDevicesView search_device_view;
    private long startTime = System.currentTimeMillis();

    public AutoBTActivity() {
        this.OnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                if (view.getId() == 2131296266) {
                    AutoBTActivity.this.backToUserEdit();
                }
            }
        };
        this.handler = new Handler(){

            /*
             * Enabled aggressive block sorting
             */
            public void handleMessage(Message message) {
                super.handleMessage(message);
                switch (message.what) {
                    default: {
                        return;
                    }
                    case 2: {
                        if (UtilConstants.checkScaleTimes == 0) {
                            Toast.makeText((Context)AutoBTActivity.this, (CharSequence)AutoBTActivity.this.getString(2131361908), (int)1).show();
                            UtilConstants.checkScaleTimes = 1 + UtilConstants.checkScaleTimes;
                            AutoBTActivity.this.startActivity(new Intent((Context)AutoBTActivity.this, (Class)AutoBLEActivity.class));
                            AutoBTActivity.this.finish();
                            return;
                        }
                        Toast.makeText((Context)AutoBTActivity.this, (CharSequence)AutoBTActivity.this.getString(2131361909), (int)1).show();
                        return;
                    }
                    case 3: {
                        AutoBTActivity.this.handler.removeCallbacks(AutoBTActivity.this.TimeoutRunnable);
                        UtilConstants.serveIntent = null;
                        Intent intent = new Intent();
                        intent.setClass((Context)AutoBTActivity.this, (Class)LoadingActivity.class);
                        ExitApplication.getInstance().exit((Context)AutoBTActivity.this);
                        AutoBTActivity.this.startActivity(intent);
                        return;
                    }
                    case 4: 
                }
                if (AutoBTActivity.this.keepScaleWorking) {
                    Toast.makeText((Context)AutoBTActivity.this, (CharSequence)AutoBTActivity.this.getString(2131361913), (int)0).show();
                    AutoBTActivity.access$3(AutoBTActivity.this, false);
                } else {
                    AutoBTActivity.access$3(AutoBTActivity.this, true);
                }
                if (isOnlySupprotBT) {
                    AutoBTActivity.this.bnp.incrementProgressBy(4);
                    return;
                }
                AutoBTActivity.this.bnp.incrementProgressBy(2);
            }
        };
        this.mReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context context, Intent intent) {
                String string2 = intent.getAction();
                if ("android.bluetooth.device.action.FOUND".equals((Object)string2)) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice == null) return;
                    {
                        String string3 = bluetoothDevice.getName();
                        System.out.println(String.valueOf((Object)string3) + "=" + bluetoothDevice.getAddress());
                        if (string3 == null || !string3.equalsIgnoreCase("Electronic Scale") || AutoBTActivity.this.connectdevice != null) return;
                        {
                            AutoBTActivity.access$7(AutoBTActivity.this, bluetoothDevice);
                            BluetoolUtil.mChatService.connect(bluetoothDevice, true);
                            AutoBTActivity.this.stopDiscovery();
                            return;
                        }
                    }
                } else {
                    if (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals((Object)string2)) return;
                    {
                        AutoBTActivity.this.stopDiscovery();
                        return;
                    }
                }
            }
        };
        this.ScanRunnable = new Runnable(){

            public void run() {
                AutoBTActivity.this.startDiscovery();
                AutoBTActivity.this.handler.postDelayed((Runnable)this, 7000);
            }
        };
        this.TimeoutRunnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            public void run() {
                if (AutoBTActivity.this.isBack) return;
                if (TimeService.scaleType != null) {
                    AutoBTActivity.this.handler.sendEmptyMessage(3);
                    return;
                }
                if (System.currentTimeMillis() - AutoBTActivity.this.startTime > (long)checkTime) {
                    if (AppData.hasCheckData) return;
                    AutoBTActivity.this.handler.sendEmptyMessage(2);
                    return;
                }
                if (!AppData.hasCheckData) {
                    AutoBTActivity.this.handler.sendEmptyMessage(4);
                } else {
                    AutoBTActivity.this.bnp.setProgress(100);
                }
                AutoBTActivity.this.handler.postDelayed((Runnable)this, 1000);
            }
        };
    }

    static /* synthetic */ void access$3(AutoBTActivity autoBTActivity, boolean bl) {
        autoBTActivity.keepScaleWorking = bl;
    }

    static /* synthetic */ void access$7(AutoBTActivity autoBTActivity, BluetoothDevice bluetoothDevice) {
        autoBTActivity.connectdevice = bluetoothDevice;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void backToUserEdit() {
        boolean bl;
        this.isBack = bl = true;
        Boolean bl2 = (Boolean)UtilConstants.su.readbackUp("lefuconfig", "reCheck", false);
        ExitApplication.getInstance().exit((Context)this);
        if (bl2 == null) {
            bl = false;
        }
        if (bl & bl2) {
            this.startActivity(new Intent((Context)this, (Class)LoadingActivity.class));
            return;
        }
        this.startActivity(new Intent((Context)this, (Class)UserEditActivity.class));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903040);
        this.backButton = (Button)this.findViewById(2131296266);
        this.backButton.setOnClickListener(this.OnClickListener);
        ExitApplication.getInstance().addActivity(this);
        UtilConstants.su = new SharedPreferencesUtil((Context)this);
        if (BluetoolUtil.mBluetoothAdapter == null) {
            BluetoolUtil.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (BluetoolUtil.mBluetoothAdapter != null && !BluetoolUtil.mBluetoothAdapter.isEnabled()) {
            this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 3);
        }
        if (UtilConstants.serveIntent == null) {
            UtilConstants.serveIntent = new Intent((Context)this, (Class)TimeService.class);
            this.startService(UtilConstants.serveIntent);
        }
        new Thread(this.ScanRunnable).start();
        new Thread(this.TimeoutRunnable).start();
        this.search_device_view = (SearchDevicesView)this.findViewById(2131296268);
        this.search_device_view.setWillNotDraw(false);
        this.search_device_view.setSearching(true);
        this.bnp = (NumberProgressBar)this.findViewById(2131296269);
        if (Build.VERSION.SDK_INT < 18) {
            isOnlySupprotBT = true;
        }
        if (isOnlySupprotBT) {
            checkTime = 25000;
            this.bnp.setProgress(0);
        } else {
            this.bnp.setProgress(40);
        }
        AppData.isCheckScale = true;
    }

    protected void onDestroy() {
        isRegisterReceiver = false;
        if (UtilConstants.serveIntent != null) {
            this.stopService(UtilConstants.serveIntent);
        }
        if (this.mBtAdapter != null) {
            this.mBtAdapter.cancelDiscovery();
        }
        this.handler.removeCallbacks(this.ScanRunnable);
        super.onDestroy();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4) {
            this.backToUserEdit();
            return true;
        }
        if (n == 3 && UtilConstants.serveIntent != null) {
            this.stopService(UtilConstants.serveIntent);
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStart() {
        super.onStart();
    }

    public void startDiscovery() {
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
        this.registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.registerReceiver(this.mReceiver, intentFilter2);
        isRegisterReceiver = true;
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBtAdapter.startDiscovery();
    }

    public void stopDiscovery() {
        this.mBtAdapter.cancelDiscovery();
        if (isRegisterReceiver) {
            this.unregisterReceiver(this.mReceiver);
            isRegisterReceiver = false;
        }
    }

}

