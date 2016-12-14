/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattService
 *  android.bluetooth.BluetoothManager
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.Toast
 *  java.io.PrintStream
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.InterruptedException
 *  java.lang.NoClassDefFoundError
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.util.List
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lefu.es.ble.BlueSingleton;
import com.lefu.es.ble.BluetoothLeService;
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.db.RecordDao;
import com.lefu.es.entity.UserModel;
import com.lefu.es.progressbar.NumberProgressBar;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.UserService;
import com.lefu.es.system.AutoBTActivity;
import com.lefu.es.system.LoadingActivity;
import com.lefu.es.system.SearchDevicesView;
import com.lefu.es.system.UserEditActivity;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;

@SuppressLint(value={"HandlerLeak"})
public class AutoBLEActivity
extends Activity {
    private static final int REQUEST_ENABLE_BT_CLICK = 31;
    private static final String TAG = "AutoBLEActivity";
    private static boolean receiverReleased = false;
    View.OnClickListener OnClickListener;
    private Runnable TimeoutRunnable;
    private Button backButton;
    private NumberProgressBar bnp;
    Handler handler;
    private boolean isBack = false;
    private boolean isConneced = false;
    private boolean isServiceReg = false;
    private boolean keepScaleWorking = true;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private final BroadcastReceiver mGattUpdateReceiver;
    public final ServiceConnection mServiceConnection;
    private RecordService recordService;
    private String scaleType = null;
    private SearchDevicesView search_device_view;
    private int sendCodeCount = 0;
    private BlueSingleton singleton;
    private long startTime = System.currentTimeMillis();
    private UserService uservice;

    public AutoBLEActivity() {
        this.OnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                if (view.getId() == 2131296266) {
                    AutoBLEActivity.this.backToUserEdit();
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
                    case 1: {
                        if (!AutoBLEActivity.this.isBack) {
                            Intent intent = new Intent();
                            intent.setFlags(268435456);
                            intent.setClass((Context)AutoBLEActivity.this, (Class)AutoBTActivity.class);
                            AutoBLEActivity.this.startActivity(intent);
                            AutoBLEActivity.this.finish();
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 2: {
                        AutoBLEActivity.this.handler.removeCallbacks(AutoBLEActivity.this.TimeoutRunnable);
                        Log.i((String)"AutoBLEActivity", (String)"connected and jump to LoadingActivity");
                        ExitApplication.getInstance().exit((Context)AutoBLEActivity.this);
                        Intent intent = new Intent();
                        intent.setClass((Context)AutoBLEActivity.this, (Class)LoadingActivity.class);
                        AutoBLEActivity.this.startActivity(intent);
                        return;
                    }
                    case 3: 
                }
                if (AutoBLEActivity.this.keepScaleWorking) {
                    Toast.makeText((Context)AutoBLEActivity.this, (CharSequence)AutoBLEActivity.this.getString(2131361913), (int)0).show();
                    AutoBLEActivity.access$4(AutoBLEActivity.this, false);
                } else {
                    AutoBLEActivity.access$4(AutoBLEActivity.this, true);
                }
                AutoBLEActivity.this.bnp.incrementProgressBy(2);
            }
        };
        this.mServiceConnection = new ServiceConnection(){

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                AutoBLEActivity.access$6(AutoBLEActivity.this, ((BluetoothLeService.LocalBinder)iBinder).getService());
                if (!AutoBLEActivity.this.mBluetoothLeService.initialize()) {
                    AutoBLEActivity.this.finish();
                }
                AutoBLEActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                AutoBLEActivity.this.sleep(200);
                AutoBLEActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                AutoBLEActivity.access$9(AutoBLEActivity.this, true);
            }

            public void onServiceDisconnected(ComponentName componentName) {
                AutoBLEActivity.access$9(AutoBLEActivity.this, false);
            }
        };
        this.mGattUpdateReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onReceive(Context context, Intent intent) {
                String string2 = intent.getAction();
                if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals((Object)string2)) {
                    AutoBLEActivity.this.singleton.setmConnected(true);
                    return;
                }
                if ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals((Object)string2)) return;
                if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals((Object)string2)) {
                    AutoBLEActivity.access$11(AutoBLEActivity.this, 0);
                    AutoBLEActivity.access$12(AutoBLEActivity.this, true);
                    Toast toast = Toast.makeText((Context)AutoBLEActivity.this, (CharSequence)AutoBLEActivity.this.getString(2131361917), (int)0);
                    toast.setGravity(17, 0, 0);
                    toast.show();
                    Toast toast2 = Toast.makeText((Context)AutoBLEActivity.this, (CharSequence)AutoBLEActivity.this.getString(2131361917), (int)0);
                    toast2.setGravity(17, 0, 0);
                    toast2.show();
                    new Thread(new Runnable(){

                        public void run() {
                            if (4.this.AutoBLEActivity.this.singleton.getmConnected() && 4.this.AutoBLEActivity.this.mBluetoothLeService != null) {
                                4.this.AutoBLEActivity.this.send_data();
                            }
                        }
                    }).start();
                    return;
                }
                if (!"com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals((Object)string2)) return;
                String string3 = intent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
                System.out.println("\u68c0\u6d4b\u8bfb\u53d6\u5230\u6570\u636e\uff1a" + string3);
                if (string3.equals((Object)UtilConstants.ERROR_CODE)) {
                    if (AutoBLEActivity.this.sendCodeCount < 1) {
                        if (AutoBLEActivity.this.mBluetoothLeService != null) {
                            AutoBLEActivity.this.send_data();
                        }
                        AutoBLEActivity autoBLEActivity = AutoBLEActivity.this;
                        AutoBLEActivity.access$11(autoBLEActivity, 1 + autoBLEActivity.sendCodeCount);
                    } else {
                        Toast.makeText((Context)AutoBLEActivity.this, (CharSequence)AutoBLEActivity.this.getString(2131361911), (int)1).show();
                        Toast.makeText((Context)AutoBLEActivity.this, (CharSequence)AutoBLEActivity.this.getString(2131361911), (int)1).show();
                    }
                } else if (string3.equals((Object)UtilConstants.ERROR_CODE_TEST)) {
                    Toast.makeText((Context)AutoBLEActivity.this, (CharSequence)AutoBLEActivity.this.getString(2131361914), (int)1).show();
                }
                String string4 = "";
                if (string3.toLowerCase().startsWith(UtilConstants.BODY_SCALE) && string3.length() > 31) {
                    string4 = UtilConstants.BODY_SCALE;
                } else if (string3.toLowerCase().startsWith(UtilConstants.BATHROOM_SCALE) && string3.length() > 31) {
                    string4 = UtilConstants.BATHROOM_SCALE;
                } else if (string3.toLowerCase().startsWith(UtilConstants.BABY_SCALE) && string3.length() > 31) {
                    string4 = UtilConstants.BABY_SCALE;
                } else if (string3.toLowerCase().startsWith(UtilConstants.KITCHEN_SCALE) && string3.length() > 31) {
                    string4 = UtilConstants.KITCHEN_SCALE;
                }
                if (string3.length() <= 31 || System.currentTimeMillis() - UtilConstants.receiveDataTime <= 1000) return;
                UtilConstants.receiveDataTime = System.currentTimeMillis();
                UtilConstants.CURRENT_SCALE = string4;
                AutoBLEActivity.access$15(AutoBLEActivity.this, UtilConstants.CURRENT_SCALE);
                UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
                Boolean bl = (Boolean)UtilConstants.su.readbackUp("lefuconfig", "reCheck", false);
                UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                if (bl == null || !bl.booleanValue()) {
                    try {
                        AutoBLEActivity.this.uservice.save(UtilConstants.CURRENT_USER);
                        UtilConstants.CURRENT_USER = AutoBLEActivity.this.uservice.find(AutoBLEActivity.this.uservice.maxid());
                        UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                        UtilConstants.SELECT_USER = UtilConstants.CURRENT_USER.getId();
                        UtilConstants.su.editSharedPreferences("lefuconfig", "user", UtilConstants.SELECT_USER);
                    }
                    catch (Exception var8_10) {
                        var8_10.printStackTrace();
                    }
                } else {
                    try {
                        AutoBLEActivity.this.uservice.update(UtilConstants.CURRENT_USER);
                    }
                    catch (Exception var12_11) {
                        var12_11.printStackTrace();
                    }
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), "BLE");
                if (string3.toLowerCase().startsWith(UtilConstants.KITCHEN_SCALE)) {
                    RecordDao.dueKitchenDate(AutoBLEActivity.this.recordService, string3, null);
                } else {
                    RecordDao.dueDate(AutoBLEActivity.this.recordService, string3);
                }
                AutoBLEActivity.this.handler.sendEmptyMessage(2);
            }

        };
        this.TimeoutRunnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            public void run() {
                if (AutoBLEActivity.this.scaleType != null) return;
                if (AutoBLEActivity.this.isBack) return;
                if (System.currentTimeMillis() - AutoBLEActivity.this.startTime > 20000) {
                    if (AutoBLEActivity.this.isConneced) return;
                    AutoBLEActivity.this.handler.sendEmptyMessage(1);
                    return;
                }
                if (!AutoBLEActivity.this.isConneced) {
                    AutoBLEActivity.this.handler.sendEmptyMessage(3);
                } else {
                    AutoBLEActivity.this.bnp.setProgress(100);
                }
                AutoBLEActivity.this.handler.postDelayed((Runnable)this, 1000);
            }
        };
    }

    static /* synthetic */ void access$11(AutoBLEActivity autoBLEActivity, int n) {
        autoBLEActivity.sendCodeCount = n;
    }

    static /* synthetic */ void access$12(AutoBLEActivity autoBLEActivity, boolean bl) {
        autoBLEActivity.isConneced = bl;
    }

    static /* synthetic */ void access$15(AutoBLEActivity autoBLEActivity, String string2) {
        autoBLEActivity.scaleType = string2;
    }

    static /* synthetic */ void access$4(AutoBLEActivity autoBLEActivity, boolean bl) {
        autoBLEActivity.keepScaleWorking = bl;
    }

    static /* synthetic */ void access$6(AutoBLEActivity autoBLEActivity, BluetoothLeService bluetoothLeService) {
        autoBLEActivity.mBluetoothLeService = bluetoothLeService;
    }

    static /* synthetic */ void access$9(AutoBLEActivity autoBLEActivity, boolean bl) {
        autoBLEActivity.isServiceReg = bl;
    }

    private void backToUserEdit() {
        this.isBack = true;
        Boolean bl = (Boolean)UtilConstants.su.readbackUp("lefuconfig", "reCheck", false);
        ExitApplication.getInstance().exit((Context)this);
        if (bl != null && bl.booleanValue()) {
            this.startActivity(new Intent((Context)this, (Class)LoadingActivity.class));
            return;
        }
        this.startActivity(new Intent((Context)this, (Class)UserEditActivity.class));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private void send_data() {
        var1_1 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff4");
        this.mBluetoothLeService.setCharacteristicNotification(var1_1, true);
        try {
            this.sleep(500);
        }
        catch (Exception var2_4) {
            ** continue;
        }
lbl7: // 2 sources:
        do {
            var3_2 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff1");
            if (var3_2 != null) {
                var4_3 = MyUtil.getUserInfo();
                System.out.println("\u5199\u5165\u6570\u636e:" + var4_3);
                var3_2.setValue(StringUtils.hexStringToByteArray(var4_3));
                this.mBluetoothLeService.wirteCharacteristic(var3_2);
                var3_2.getProperties();
            }
            return;
            break;
        } while (true);
    }

    private void sleep(long l) {
        try {
            Thread.sleep((long)l);
            return;
        }
        catch (InterruptedException var3_2) {
            var3_2.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903040);
        this.backButton = (Button)this.findViewById(2131296266);
        this.backButton.setOnClickListener(this.OnClickListener);
        this.recordService = new RecordService((Context)this);
        this.uservice = new UserService((Context)this);
        ExitApplication.getInstance().addActivity(this);
        UtilConstants.su = new SharedPreferencesUtil((Context)this);
        try {
            this.mBluetoothAdapter = ((BluetoothManager)this.getSystemService("bluetooth")).getAdapter();
        }
        catch (NoClassDefFoundError var2_2) {
            var2_2.printStackTrace();
        }
        if (!this.mBluetoothAdapter.isEnabled()) {
            this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 31);
        }
        this.singleton = BlueSingleton.getInstance(this.handler);
        this.singleton.isExit = false;
        this.singleton.isSearch = false;
        this.singleton.setmConnected(false);
        if (this.mBluetoothAdapter.isEnabled() && !this.singleton.getmConnected() && !this.singleton.getmScanning()) {
            this.singleton.scanLeDevice(true, this, this.mServiceConnection);
        }
        if (!receiverReleased) {
            this.registerReceiver(this.mGattUpdateReceiver, BluetoothLeService.makeGattUpdateIntentFilter());
            receiverReleased = false;
        }
        this.search_device_view = (SearchDevicesView)this.findViewById(2131296268);
        this.search_device_view.setWillNotDraw(false);
        this.search_device_view.setSearching(true);
        this.bnp = (NumberProgressBar)this.findViewById(2131296269);
        new Thread(this.TimeoutRunnable).start();
        AppData.isCheckScale = true;
        if (UtilConstants.CURRENT_USER == null) {
            UtilConstants.CURRENT_USER = (UserModel)JSONObject.parseObject((String)UtilConstants.su.readbackUp("lefuconfig", "addUser", ""), UserModel.class);
        }
    }

    protected void onDestroy() {
        this.singleton.isExit = true;
        this.unregisterReceiver(this.mGattUpdateReceiver);
        super.onDestroy();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4) {
            this.backToUserEdit();
            return true;
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

}

