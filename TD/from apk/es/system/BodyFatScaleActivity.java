/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.NotificationManager
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattService
 *  android.bluetooth.BluetoothManager
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Parcelable
 *  android.support.v4.view.PagerAdapter
 *  android.support.v4.view.ViewPager
 *  android.support.v4.view.ViewPager$OnPageChangeListener
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.io.PrintStream
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.Math
 *  java.lang.NoClassDefFoundError
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Thread
 *  java.math.BigDecimal
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Locale
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lefu.es.adapter.MyPageAdapter;
import com.lefu.es.ble.BlueSingleton;
import com.lefu.es.ble.BluetoothLeService;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.constant.AppData;
import com.lefu.es.constant.BluetoolUtil;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
import com.lefu.es.db.RecordDao;
import com.lefu.es.entity.Records;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.TimeService;
import com.lefu.es.service.UserService;
import com.lefu.es.system.CustomDialogActivity;
import com.lefu.es.system.InfoActivity;
import com.lefu.es.system.LoadingActivity;
import com.lefu.es.system.ReCheckActivity;
import com.lefu.es.system.ReceiveAlertActivity;
import com.lefu.es.system.RecordListActivity;
import com.lefu.es.system.ScaleChangeAlertActivity;
import com.lefu.es.system.SettingActivity;
import com.lefu.es.system.UserListActivity;
import com.lefu.es.util.DisplayUtil;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.MyTextView;
import com.lefu.es.view.MyTextView2;
import com.lefu.es.view.guideview.HighLightGuideView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressLint(value={"HandlerLeak"})
public class BodyFatScaleActivity
extends Activity
implements Runnable {
    private static final boolean D = true;
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    private static final int REQUEST_ENABLE_BT_CLICK = 31;
    private static final String TAG = "BodyFatActivity";
    private static boolean receiverReleased = false;
    private Runnable CheckHasDataRunnable;
    private int ItemID;
    private Runnable ScanRunnable;
    private MyPageAdapter adapter = null;
    private TextView bmi_tv;
    private TextView bmr_tv;
    private TextView bodyfat_tv;
    private TextView bodywater_tv;
    private TextView bone_tv;
    View.OnClickListener btnOnClickListener;
    private MyTextView2 compare_tv;
    private Records curRecord;
    private Button deletdImg;
    Handler handler;
    private ImageView headImg;
    View.OnClickListener imgOnClickListener;
    private LayoutInflater inflater;
    private Button infoImg;
    private ImageView intentImg;
    private boolean isCurrentActivoty = true;
    private boolean isNotOpenBL = false;
    private boolean isServiceReg;
    View.OnClickListener layoutClickListener;
    private ImageView leftImg;
    private LinearLayout lineLayout1;
    private LinearLayout lineLayout2;
    private LinearLayout lineLayout3;
    private LinearLayout lineLayout4;
    private LinearLayout lineLayout5;
    private LinearLayout lineLayout7;
    private LinearLayout lineLayout8;
    private LinearLayout linearLayout77;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBtAdapter;
    private final BroadcastReceiver mGattUpdateReceiver;
    private final BroadcastReceiver mReceiver;
    public final ServiceConnection mServiceConnection;
    View.OnClickListener menuBtnOnClickListener;
    private TextView musle_tv;
    private TextView norecord_tv;
    private ViewPager pager;
    private TextView physicage_tv;
    private List<Records> rList;
    private RecordService recordService;
    private ImageView rightImg;
    RelativeLayout rlGuide = null;
    private TextView scale_connect_state;
    private int selectedPosition = -1;
    private int sendCodeCount = 0;
    private Button setingImg;
    private BlueSingleton singleton;
    private TextView targetv;
    private TextView time_tv;
    private TextView tvBmi = null;
    private TextView username_tv;
    private UserService uservice;
    private ArrayList<View> views = new ArrayList();
    private TextView visal_tv;
    private TextView weight_textView17;

    public BodyFatScaleActivity() {
        this.layoutClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass((Context)BodyFatScaleActivity.this, (Class)RecordListActivity.class);
                if (view.getTag() != null) {
                    intent.putExtra("type", ((Integer)view.getTag()).intValue());
                }
                intent.addFlags(131072);
                BodyFatScaleActivity.this.startActivityForResult(intent, 0);
            }
        };
        this.menuBtnOnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 2131296300: {
                        Intent intent = new Intent();
                        intent.setFlags(268435456);
                        intent.setClass((Context)BodyFatScaleActivity.this, (Class)InfoActivity.class);
                        BodyFatScaleActivity.this.startActivity(intent);
                        return;
                    }
                    case 2131296301: {
                        Intent intent = new Intent();
                        intent.setFlags(268435456);
                        intent.setClass((Context)BodyFatScaleActivity.this, (Class)SettingActivity.class);
                        BodyFatScaleActivity.this.startActivity(intent);
                        return;
                    }
                    case 2131296302: 
                }
                BodyFatScaleActivity.this.dialog(BodyFatScaleActivity.this.getString(2131361847), view.getId());
            }
        };
        this.btnOnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 2131296338: 
                }
                Intent intent = new Intent();
                intent.setClass((Context)BodyFatScaleActivity.this, (Class)UserListActivity.class);
                BodyFatScaleActivity.this.startActivity(intent);
            }
        };
        this.imgOnClickListener = new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass((Context)BodyFatScaleActivity.this, (Class)RecordListActivity.class);
                intent.putExtra("type", 0);
                if (view.getTag() != null) {
                    intent.putExtra("id", ((Records)view.getTag()).getId());
                } else {
                    intent.putExtra("id", -1);
                }
                intent.addFlags(131072);
                BodyFatScaleActivity.this.startActivityForResult(intent, 0);
            }
        };
        this.handler = new Handler(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void handleMessage(Message message) {
                super.handleMessage(message);
                switch (message.what) {
                    case 0: {
                        BodyFatScaleActivity.this.setViews();
                        BodyFatScaleActivity.this.Init(CacheHelper.recordListDesc);
                        if (TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_INSTALL_BODYFAT_SCALE) && CacheHelper.recordListDesc.size() == 1) {
                            BodyFatScaleActivity.this.showTipMask();
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 1: {
                        BodyFatScaleActivity.this.setViews();
                        return;
                    }
                    case 5: {
                        BodyFatScaleActivity.this.exit();
                        ExitApplication.getInstance().exit((Context)BodyFatScaleActivity.this);
                        return;
                    }
                    case 101: 
                }
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)LoadingActivity.mainActivty);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
                try {
                    BodyFatScaleActivity.this.uservice.update(UtilConstants.CURRENT_USER);
                }
                catch (Exception var3_3) {
                    var3_3.printStackTrace();
                }
                System.out.println("jump to LoadingActivity");
                ExitApplication.getInstance().exit((Context)BodyFatScaleActivity.this);
                Intent intent = new Intent();
                intent.setClass((Context)BodyFatScaleActivity.this, (Class)LoadingActivity.class);
                BodyFatScaleActivity.this.startActivity(intent);
            }
        };
        this.isServiceReg = false;
        this.mServiceConnection = new ServiceConnection(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e((String)"BodyFatActivity", (String)"ServiceConnection  onServiceConnected...");
                BodyFatScaleActivity.access$5(BodyFatScaleActivity.this, ((BluetoothLeService.LocalBinder)iBinder).getService());
                if (!BodyFatScaleActivity.this.mBluetoothLeService.initialize()) {
                    BodyFatScaleActivity.this.finish();
                }
                BodyFatScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                try {
                    Thread.sleep((long)200);
                }
                catch (InterruptedException var5_3) {
                    var5_3.printStackTrace();
                }
                BodyFatScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                BodyFatScaleActivity.access$7(BodyFatScaleActivity.this, true);
            }

            public void onServiceDisconnected(ComponentName componentName) {
                Log.e((String)"BodyFatActivity", (String)"ServiceConnection  onServiceDisconnected...");
                BodyFatScaleActivity.access$7(BodyFatScaleActivity.this, false);
            }
        };
        this.mGattUpdateReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context context, Intent intent) {
                String string2 = intent.getAction();
                if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals((Object)string2)) {
                    Log.e((String)"BodyFatActivity", (String)"ACTION_GATT_CONNECTED");
                    BodyFatScaleActivity.this.singleton.setmConnected(true);
                    return;
                } else if ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals((Object)string2)) {
                    Log.e((String)"BodyFatActivity", (String)"ACTION_GATT_DISCONNECTED");
                    BodyFatScaleActivity.this.scale_connect_state.setText(2131361919);
                    if (BodyFatScaleActivity.this.isNotOpenBL || !BodyFatScaleActivity.this.singleton.getmConnected()) return;
                    {
                        BodyFatScaleActivity.this.singleton.setmConnected(false);
                        if (BodyFatScaleActivity.this.mBluetoothLeService != null) {
                            boolean bl = BodyFatScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                            Log.d((String)"BodyFatActivity", (String)("Connect request result=" + bl));
                        }
                        BodyFatScaleActivity.this.singleton.scanLeDevice(true, BodyFatScaleActivity.this, BodyFatScaleActivity.this.mServiceConnection);
                        return;
                    }
                } else {
                    if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals((Object)string2)) {
                        Log.e((String)"BodyFatActivity", (String)"ACTION_GATT_SERVICES_DISCOVERED");
                        BodyFatScaleActivity.access$11(BodyFatScaleActivity.this, 0);
                        BodyFatScaleActivity.this.scale_connect_state.setText(2131361918);
                        AppData.hasCheckData = true;
                        Toast.makeText((Context)BodyFatScaleActivity.this, (CharSequence)BodyFatScaleActivity.this.getString(2131361912), (int)1).show();
                        Toast.makeText((Context)BodyFatScaleActivity.this, (CharSequence)BodyFatScaleActivity.this.getString(2131361912), (int)1).show();
                        new Thread(new Runnable(){

                            public void run() {
                                if (7.this.BodyFatScaleActivity.this.mBluetoothLeService != null) {
                                    7.this.BodyFatScaleActivity.this.send_Data();
                                }
                            }
                        }).start();
                        return;
                    }
                    if (!"com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals((Object)string2)) return;
                    {
                        Log.e((String)"BodyFatActivity", (String)"ACTION_DATA_AVAILABLE");
                        String string3 = intent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
                        System.out.println("\u8bfb\u53d6\u5230\u6570\u636e\uff1a" + string3);
                        if (string3.equals((Object)UtilConstants.ERROR_CODE)) {
                            if (BodyFatScaleActivity.this.sendCodeCount < 1) {
                                if (BodyFatScaleActivity.this.mBluetoothLeService != null) {
                                    BodyFatScaleActivity.this.send_Data();
                                }
                                BodyFatScaleActivity bodyFatScaleActivity = BodyFatScaleActivity.this;
                                BodyFatScaleActivity.access$11(bodyFatScaleActivity, 1 + bodyFatScaleActivity.sendCodeCount);
                            } else {
                                Toast.makeText((Context)BodyFatScaleActivity.this, (CharSequence)BodyFatScaleActivity.this.getString(2131361911), (int)1).show();
                                Toast.makeText((Context)BodyFatScaleActivity.this, (CharSequence)BodyFatScaleActivity.this.getString(2131361911), (int)1).show();
                            }
                        } else if (string3.equals((Object)UtilConstants.ERROR_CODE_TEST)) {
                            Toast.makeText((Context)BodyFatScaleActivity.this, (CharSequence)BodyFatScaleActivity.this.getString(2131361914), (int)0).show();
                        }
                        if (System.currentTimeMillis() - UtilConstants.receiveDataTime < 1000) return;
                        {
                            UtilConstants.receiveDataTime = System.currentTimeMillis();
                            if (!string3.startsWith(UtilConstants.BODY_SCALE) && string3.length() > 31) {
                                if (string3.startsWith(UtilConstants.BATHROOM_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.BATHROOM_SCALE;
                                    UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                                    RecordDao.dueDate(BodyFatScaleActivity.this.recordService, string3);
                                } else if (string3.startsWith(UtilConstants.BABY_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.BABY_SCALE;
                                    UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                                    RecordDao.dueDate(BodyFatScaleActivity.this.recordService, string3);
                                } else if (string3.startsWith(UtilConstants.KITCHEN_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.KITCHEN_SCALE;
                                    UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                                    RecordDao.dueKitchenDate(BodyFatScaleActivity.this.recordService, string3, null);
                                }
                                BodyFatScaleActivity.this.handler.sendEmptyMessage(101);
                                return;
                            }
                            if (string3.equals((Object)UtilConstants.ERROR_CODE_GETDATE)) {
                                BodyFatScaleActivity.this.openErrorDiolg("2");
                                return;
                            }
                            if (!string3.startsWith("c") || string3.length() <= 31) return;
                            {
                                BodyFatScaleActivity.this.dueDate(string3);
                                return;
                            }
                        }
                    }
                }
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
                        if (string3 == null || !string3.equalsIgnoreCase("Electronic Scale")) return;
                        {
                            BluetoolUtil.mChatService.connect(bluetoothDevice, true);
                            BodyFatScaleActivity.this.stopDiscovery();
                            BodyFatScaleActivity.this.handler.postDelayed(BodyFatScaleActivity.this.ScanRunnable, 15000);
                            return;
                        }
                    }
                } else {
                    if (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals((Object)string2)) return;
                    {
                        BodyFatScaleActivity.this.stopDiscovery();
                        BodyFatScaleActivity.this.handler.postDelayed(BodyFatScaleActivity.this.ScanRunnable, 10000);
                        return;
                    }
                }
            }
        };
        this.ScanRunnable = new Runnable(){

            public void run() {
                BodyFatScaleActivity.this.startDiscovery();
            }
        };
        this.CheckHasDataRunnable = new Runnable(){

            public void run() {
                if (!AppData.hasCheckData && BodyFatScaleActivity.this.isCurrentActivoty && !UtilConstants.isTipChangeScale) {
                    BodyFatScaleActivity.this.scaleChangeAlert();
                    UtilConstants.isTipChangeScale = true;
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private void Init(List<Records> list) {
        if (this.views == null) {
            this.views = new ArrayList();
        } else {
            this.views.clear();
        }
        if (list != null && list.size() > 0) {
            this.weight_textView17.setVisibility(8);
            this.rightImg.setVisibility(0);
            this.leftImg.setVisibility(0);
            if (list.size() > 1) {
                this.rightImg.setImageDrawable(this.getResources().getDrawable(2130837533));
            }
        } else {
            this.leftImg.setImageDrawable(this.getResources().getDrawable(2130837610));
            this.rightImg.setImageDrawable(this.getResources().getDrawable(2130837649));
            this.weight_textView17.setVisibility(0);
            this.rightImg.setVisibility(4);
            this.leftImg.setVisibility(4);
            this.norecord_tv.setVisibility(4);
            this.pager.removeAllViews();
            return;
        }
        this.norecord_tv.setVisibility(4);
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this);
        View view = layoutInflater.inflate(2130903080, null);
        this.views.add((Object)view);
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext()) {
                View view2 = layoutInflater.inflate(2130903080, null);
                this.views.add((Object)view2);
                this.adapter = new MyPageAdapter(this.views);
                this.pager.setAdapter((PagerAdapter)this.adapter);
                this.adapter.notifyDataSetChanged();
                this.pager.setCurrentItem(1);
                return;
            }
            View view3 = this.creatView((Records)iterator.next(), null);
            this.views.add((Object)view3);
        } while (true);
    }

    static /* synthetic */ void access$11(BodyFatScaleActivity bodyFatScaleActivity, int n) {
        bodyFatScaleActivity.sendCodeCount = n;
    }

    static /* synthetic */ void access$20(BodyFatScaleActivity bodyFatScaleActivity, int n) {
        bodyFatScaleActivity.selectedPosition = n;
    }

    static /* synthetic */ void access$22(BodyFatScaleActivity bodyFatScaleActivity, Records records) {
        bodyFatScaleActivity.curRecord = records;
    }

    static /* synthetic */ Records access$26(BodyFatScaleActivity bodyFatScaleActivity) {
        return bodyFatScaleActivity.curRecord;
    }

    static /* synthetic */ int access$27(BodyFatScaleActivity bodyFatScaleActivity) {
        return bodyFatScaleActivity.ItemID;
    }

    static /* synthetic */ void access$5(BodyFatScaleActivity bodyFatScaleActivity, BluetoothLeService bluetoothLeService) {
        bodyFatScaleActivity.mBluetoothLeService = bluetoothLeService;
    }

    static /* synthetic */ void access$7(BodyFatScaleActivity bodyFatScaleActivity, boolean bl) {
        bodyFatScaleActivity.isServiceReg = bl;
    }

    private void exit() {
        this.stopScanService();
        ((NotificationManager)this.getSystemService("notification")).cancel(0);
        if (LoadingActivity.mainActivty != null) {
            LoadingActivity.mainActivty.finish();
        }
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initView() {
        this.uservice = new UserService((Context)this);
        this.weight_textView17 = (TextView)this.findViewById(2131296337);
        this.norecord_tv = (TextView)this.findViewById(2131296278);
        this.username_tv = (TextView)this.findViewById(2131296271);
        this.compare_tv = (MyTextView2)this.findViewById(2131296280);
        this.targetv = (TextView)this.findViewById(2131296323);
        if (UtilConstants.su != null) {
            UtilConstants.CHOICE_KG = (String)UtilConstants.su.readbackUp("lefuconfig", "unit", UtilConstants.UNIT_KG);
            Log.i((String)"BodyFatActivity", (String)("weight unit: " + UtilConstants.CHOICE_KG));
        }
        if (UtilConstants.CURRENT_USER != null) {
            this.compare_tv.setTexts("0.0" + UtilConstants.CURRENT_USER.getDanwei(), null);
        }
        this.rlGuide = (RelativeLayout)this.findViewById(2131296286);
        this.bodywater_tv = (TextView)this.findViewById(2131296297);
        this.bodyfat_tv = (TextView)this.findViewById(2131296308);
        this.bone_tv = (TextView)this.findViewById(2131296313);
        this.musle_tv = (TextView)this.findViewById(2131296328);
        this.visal_tv = (TextView)this.findViewById(2131296321);
        this.bmi_tv = (TextView)this.findViewById(2131296317);
        this.bmr_tv = (TextView)this.findViewById(2131296331);
        this.time_tv = (TextView)this.findViewById(2131296270);
        this.physicage_tv = (TextView)this.findViewById(2131296335);
        this.tvBmi = (TextView)this.findViewById(2131296287);
        this.scale_connect_state = (TextView)this.findViewById(2131296273);
        this.infoImg = (Button)this.findViewById(2131296300);
        this.setingImg = (Button)this.findViewById(2131296301);
        this.deletdImg = (Button)this.findViewById(2131296302);
        this.headImg = (ImageView)this.findViewById(2131296338);
        this.intentImg = (ImageView)this.findViewById(2131296272);
        this.leftImg = (ImageView)this.findViewById(2131296277);
        this.rightImg = (ImageView)this.findViewById(2131296281);
        this.infoImg.setOnClickListener(this.menuBtnOnClickListener);
        this.deletdImg.setOnClickListener(this.menuBtnOnClickListener);
        this.setingImg.setOnClickListener(this.menuBtnOnClickListener);
        this.headImg.setOnClickListener(this.btnOnClickListener);
        this.intentImg.setOnClickListener(this.btnOnClickListener);
        this.pager = (ViewPager)this.findViewById(2131296279);
        this.pager.bringToFront();
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            public void onPageScrollStateChanged(int n) {
            }

            public void onPageScrolled(int n, float f, int n2) {
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onPageSelected(int n) {
                if (BodyFatScaleActivity.this.views != null && BodyFatScaleActivity.this.views.size() > 0) {
                    if (n == -1 + BodyFatScaleActivity.this.views.size() || n == 0) {
                        if (n == 0) {
                            BodyFatScaleActivity.this.pager.setCurrentItem(n + 1);
                        } else {
                            BodyFatScaleActivity.this.pager.setCurrentItem(n - 1);
                        }
                    } else {
                        if (n > 0) {
                            BodyFatScaleActivity.access$20(BodyFatScaleActivity.this, n - 1);
                        } else {
                            BodyFatScaleActivity.access$20(BodyFatScaleActivity.this, 0);
                        }
                        BodyFatScaleActivity.access$22(BodyFatScaleActivity.this, (Records)CacheHelper.recordListDesc.get(BodyFatScaleActivity.this.selectedPosition));
                        BodyFatScaleActivity.this.handler.sendEmptyMessage(1);
                    }
                    if (BodyFatScaleActivity.this.pager.getCurrentItem() < 2) {
                        BodyFatScaleActivity.this.leftImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837610));
                    } else {
                        BodyFatScaleActivity.this.leftImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837529));
                    }
                    if (BodyFatScaleActivity.this.pager.getCurrentItem() >= -2 + BodyFatScaleActivity.this.views.size()) {
                        BodyFatScaleActivity.this.rightImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837649));
                        return;
                    }
                    BodyFatScaleActivity.this.rightImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837533));
                    return;
                }
                BodyFatScaleActivity.this.leftImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837610));
                BodyFatScaleActivity.this.rightImg.setImageDrawable(BodyFatScaleActivity.this.getResources().getDrawable(2130837649));
            }
        });
        if (UtilConstants.CURRENT_USER != null) {
            this.username_tv.setText((CharSequence)UtilConstants.CURRENT_USER.getUserName());
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                this.targetv.setText((CharSequence)(String.valueOf((Object)UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight())) + (Object)this.getText(2131361891)));
            } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                this.targetv.setText((CharSequence)(String.valueOf((Object)UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight()))) + (Object)this.getText(2131361892)));
            } else {
                this.targetv.setText((CharSequence)(String.valueOf((Object)UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight())) + (Object)this.getText(2131361891)));
            }
            if (UtilConstants.CURRENT_USER.getPer_photo() != null && !"".equals((Object)UtilConstants.CURRENT_USER.getPer_photo()) && !UtilConstants.CURRENT_USER.getPer_photo().equals((Object)"null")) {
                Bitmap bitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
                this.headImg.setImageBitmap(bitmap);
            }
        }
        ((CheckBox)this.findViewById(2131296299)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Log.i((String)"BodyFatActivity", (String)"click to start scan");
                if (BodyFatScaleActivity.this.mBluetoothAdapter.isEnabled() && BodyFatScaleActivity.this.singleton != null) {
                    Log.i((String)"BodyFatActivity", (String)"start scan");
                    BodyFatScaleActivity.this.singleton.scanLeDevice(false, BodyFatScaleActivity.this, BodyFatScaleActivity.this.mServiceConnection);
                    if (BodyFatScaleActivity.this.mBluetoothLeService != null) {
                        BodyFatScaleActivity.this.mBluetoothLeService.disconnect();
                    }
                    BodyFatScaleActivity.this.singleton.scanLeDevice(true, BodyFatScaleActivity.this, BodyFatScaleActivity.this.mServiceConnection);
                }
            }
        });
    }

    private void layoutView() {
        this.lineLayout1 = (LinearLayout)this.findViewById(2131296283);
        this.lineLayout2 = (LinearLayout)this.findViewById(2131296307);
        this.lineLayout3 = (LinearLayout)this.findViewById(2131296315);
        this.lineLayout4 = (LinearLayout)this.findViewById(2131296311);
        this.lineLayout5 = (LinearLayout)this.findViewById(2131296319);
        this.lineLayout7 = (LinearLayout)this.findViewById(2131296325);
        this.lineLayout8 = (LinearLayout)this.findViewById(2131296326);
        this.linearLayout77 = (LinearLayout)this.findViewById(2131296333);
        this.lineLayout1.setTag((Object)4);
        this.lineLayout2.setTag((Object)2);
        this.lineLayout3.setTag((Object)6);
        this.lineLayout4.setTag((Object)1);
        this.lineLayout5.setTag((Object)5);
        this.lineLayout7.setTag((Object)3);
        this.lineLayout8.setTag((Object)7);
        this.linearLayout77.setTag((Object)8);
        this.linearLayout77.setVisibility(8);
        this.lineLayout1.setOnClickListener(this.layoutClickListener);
        this.lineLayout2.setOnClickListener(this.layoutClickListener);
        this.lineLayout3.setOnClickListener(this.layoutClickListener);
        this.lineLayout4.setOnClickListener(this.layoutClickListener);
        this.lineLayout5.setOnClickListener(this.layoutClickListener);
        this.lineLayout7.setOnClickListener(this.layoutClickListener);
        this.lineLayout8.setOnClickListener(this.layoutClickListener);
        this.linearLayout77.setOnClickListener(this.layoutClickListener);
    }

    private void openErrorDiolg(String string2) {
        try {
            Intent intent = new Intent((Context)this, (Class)CustomDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("error", string2);
            intent.putExtras(bundle);
            intent.addFlags(268435456);
            this.startActivity(intent);
            return;
        }
        catch (Exception var4_4) {
            return;
        }
    }

    private void requestOfflineData() {
        this.sendDateToScale("f200");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendDateToScale() {
        BluetoothGattCharacteristic bluetoothGattCharacteristic;
        System.out.println("\u53d1\u9001\u6570\u636e=" + MyUtil.getUserInfo());
        BluetoothGattCharacteristic bluetoothGattCharacteristic2 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff1");
        if (bluetoothGattCharacteristic2 != null) {
            bluetoothGattCharacteristic2.setValue(StringUtils.hexStringToByteArray(MyUtil.getUserInfo()));
            this.mBluetoothLeService.wirteCharacteristic(bluetoothGattCharacteristic2);
            bluetoothGattCharacteristic2.getProperties();
        }
        try {
            Thread.sleep((long)500);
        }
        catch (InterruptedException var2_3) {
            var2_3.printStackTrace();
        }
        if ((bluetoothGattCharacteristic = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff4")) != null) {
            this.mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristic, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendDateToScale(String string2) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic;
        System.out.println("\u53d1\u9001\u6570\u636e=" + string2);
        BluetoothGattCharacteristic bluetoothGattCharacteristic2 = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff1");
        if (bluetoothGattCharacteristic2 != null) {
            bluetoothGattCharacteristic2.setValue(StringUtils.hexStringToByteArray(string2));
            this.mBluetoothLeService.wirteCharacteristic(bluetoothGattCharacteristic2);
            bluetoothGattCharacteristic2.getProperties();
        }
        try {
            Thread.sleep((long)500);
        }
        catch (InterruptedException var3_4) {
            var3_4.printStackTrace();
        }
        if ((bluetoothGattCharacteristic = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff4")) != null) {
            this.mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristic, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void send_Data() {
        System.out.println("scale name: " + BluetoolUtil.mConnectedDeviceName);
        if (BluetoolUtil.mConnectedDeviceName.equals((Object)"Health Scale")) {
            try {
                Thread.sleep((long)300);
            }
            catch (InterruptedException var2_1) {
                var2_1.printStackTrace();
            }
            this.requestOfflineData();
            return;
        }
        this.sendDateToScale();
        try {
            Thread.sleep((long)500);
        }
        catch (InterruptedException var1_2) {
            var1_2.printStackTrace();
        }
        this.sendDateToScale();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setViews() {
        if (UtilConstants.CURRENT_USER != null) {
            this.username_tv.setText((CharSequence)UtilConstants.CURRENT_USER.getUserName());
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                this.targetv.setText((CharSequence)(String.valueOf((Object)UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight())) + (Object)this.getText(2131361891)));
            } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                this.targetv.setText((CharSequence)(String.valueOf((Object)UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight()))) + (Object)this.getText(2131361892)));
            } else {
                this.targetv.setText((CharSequence)(String.valueOf((Object)UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight())) + (Object)this.getText(2131361891)));
            }
            if (UtilConstants.CURRENT_USER.getPer_photo() != null && !"".equals((Object)UtilConstants.CURRENT_USER.getPer_photo()) && !UtilConstants.CURRENT_USER.getPer_photo().equals((Object)"null")) {
                Bitmap bitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
                this.headImg.setImageBitmap(bitmap);
            }
        }
        if (this.curRecord != null) {
            Date date;
            this.bodywater_tv.setText((CharSequence)(String.valueOf((float)this.curRecord.getRbodywater()) + "%"));
            this.bodyfat_tv.setText((CharSequence)(String.valueOf((float)this.curRecord.getRbodyfat()) + "%"));
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                this.bone_tv.setText((CharSequence)(String.valueOf((float)this.curRecord.getRbone()) + UtilConstants.UNIT_KG));
                this.musle_tv.setText((CharSequence)(String.valueOf((float)this.curRecord.getRmuscle()) + UtilConstants.UNIT_KG));
            } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                this.bone_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.kgToLB(this.curRecord.getRbone())) + UtilConstants.UNIT_LB));
                this.musle_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.kgToLB(this.curRecord.getRmuscle())) + UtilConstants.UNIT_LB));
            } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                this.bone_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.kgToLB(this.curRecord.getRbone())) + UtilConstants.UNIT_ST));
                this.musle_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.kgToLB(this.curRecord.getRmuscle())) + UtilConstants.UNIT_ST));
            } else {
                this.bone_tv.setText((CharSequence)(String.valueOf((float)this.curRecord.getRbone()) + UtilConstants.UNIT_KG));
                this.musle_tv.setText((CharSequence)(String.valueOf((float)this.curRecord.getRmuscle()) + UtilConstants.UNIT_KG));
            }
            this.visal_tv.setText((CharSequence)String.valueOf((float)this.curRecord.getRvisceralfat()));
            this.bmi_tv.setText((CharSequence)String.valueOf((float)this.curRecord.getRbmi()));
            this.bmr_tv.setText((CharSequence)(String.valueOf((float)this.curRecord.getRbmr()) + "kcal"));
            if (this.curRecord.getBodyAge() > 0.0f) {
                this.linearLayout77.setVisibility(0);
                this.physicage_tv.setText((CharSequence)UtilTooth.keep0Point(this.curRecord.getBodyAge()));
            } else {
                this.linearLayout77.setVisibility(8);
                this.physicage_tv.setText((CharSequence)"0");
            }
            if ((date = UtilTooth.stringToTime(this.curRecord.getRecordTime())) != null) {
                Locale.getDefault();
                this.time_tv.setText((CharSequence)StringUtils.getDateString(date, 5));
            }
            if (UtilConstants.CURRENT_USER != null) {
                float f = UtilTooth.countBMI2(this.curRecord.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0f);
                this.curRecord.setRbmi(UtilTooth.myround(f));
                this.tvBmi.setText((CharSequence)String.valueOf((float)this.curRecord.getRbmi()));
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            int n = DisplayUtil.dip2px((Context)this, 64.0f);
            int n2 = DisplayUtil.getWidth((Context)this) - n;
            layoutParams.setMargins((int)UtilTooth.changeBMI(this.curRecord.getRbmi(), n2), 0, 0, 0);
            this.rlGuide.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                if (this.curRecord.getCompareRecord() == null || "".equals((Object)this.curRecord.getCompareRecord())) {
                    this.compare_tv.setTexts("0.0", null);
                    this.compare_tv.setTexts("0.0 " + (Object)this.getText(2131361891), null);
                    return;
                }
                float f = new BigDecimal(Double.parseDouble((String)this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
                if (f > 0.0f) {
                    this.compare_tv.setTexts("\u2191" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f))).toString()) + (Object)this.getText(2131361891), null);
                    return;
                }
                if (f < 0.0f) {
                    this.compare_tv.setTexts("\u2193" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f))).toString()) + (Object)this.getText(2131361891), null);
                    return;
                }
                this.compare_tv.setTexts(String.valueOf((Object)UtilTooth.myroundString3(new StringBuilder(String.valueOf((Object)this.curRecord.getCompareRecord())).toString())) + (Object)this.getText(2131361891), null);
                return;
            }
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                if (this.curRecord.getCompareRecord() == null || "".equals((Object)this.curRecord.getCompareRecord().trim())) {
                    this.curRecord.setCompareRecord("0");
                    this.compare_tv.setTexts("0.0  " + (Object)this.getText(2131361892), null);
                    return;
                }
                float f = Float.parseFloat((String)this.curRecord.getCompareRecord());
                if (f > 0.0f) {
                    this.compare_tv.setTexts("\u2191" + UtilTooth.kgToLB(Math.abs((float)Float.parseFloat((String)this.curRecord.getCompareRecord()))) + " " + (Object)this.getText(2131361892), null);
                    return;
                }
                if (f < 0.0f) {
                    this.compare_tv.setTexts("\u2193" + UtilTooth.kgToLB(Math.abs((float)Float.parseFloat((String)this.curRecord.getCompareRecord()))) + " " + (Object)this.getText(2131361892), null);
                    return;
                }
                this.compare_tv.setTexts("0.0 " + (Object)this.getText(2131361892), null);
                return;
            }
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                if (this.curRecord.getCompareRecord() == null || "".equals((Object)this.curRecord.getCompareRecord().trim())) {
                    this.curRecord.setCompareRecord("0");
                    this.compare_tv.setTexts("0.0 " + (Object)this.getText(2131361894), null);
                }
                float f = Float.parseFloat((String)this.curRecord.getCompareRecord());
                String string2 = UtilTooth.kgToLB_new(Math.abs((float)Float.parseFloat((String)this.curRecord.getCompareRecord())));
                String[] arrstring = UtilTooth.kgToStLbForScaleFat2(Math.abs((float)Float.parseFloat((String)this.curRecord.getCompareRecord())));
                if (f > 0.0f) {
                    if (!this.curRecord.getScaleType().equals((Object)UtilConstants.BODY_SCALE)) {
                        this.compare_tv.setTexts("\u2191" + string2 + (Object)this.getText(2131361894), null);
                        return;
                    }
                    if (arrstring[1] != null) {
                        this.compare_tv.setTexts("\u2191" + arrstring[0], arrstring[1]);
                        return;
                    }
                    this.compare_tv.setTexts("\u2191" + arrstring[0] + (Object)this.getText(2131361894), null);
                    return;
                }
                if (f >= 0.0f) {
                    this.compare_tv.setTexts("0.0 " + (Object)this.getText(2131361894), null);
                    return;
                }
                if (!this.curRecord.getScaleType().equals((Object)UtilConstants.BODY_SCALE)) {
                    this.compare_tv.setTexts("\u2193" + string2 + (Object)this.getText(2131361894), null);
                    return;
                }
                if (arrstring[1] != null) {
                    this.compare_tv.setTexts("\u2193" + arrstring[0], arrstring[1]);
                    return;
                }
                this.compare_tv.setTexts("\u2193" + arrstring[0] + (Object)this.getText(2131361894), null);
                return;
            }
            if (this.curRecord.getCompareRecord() == null || "".equals((Object)this.curRecord.getCompareRecord())) {
                this.compare_tv.setTexts("0.0", null);
                this.compare_tv.setTexts("0.0 " + (Object)this.getText(2131361891), null);
                return;
            }
            float f = new BigDecimal(Double.parseDouble((String)this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
            if (f > 0.0f) {
                this.compare_tv.setTexts("\u2191" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f))).toString()) + (Object)this.getText(2131361891), null);
                return;
            }
            if (f < 0.0f) {
                this.compare_tv.setTexts("\u2193" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f))).toString()) + (Object)this.getText(2131361891), null);
                return;
            }
            this.compare_tv.setTexts(String.valueOf((Object)UtilTooth.myroundString3(new StringBuilder(String.valueOf((Object)this.curRecord.getCompareRecord())).toString())) + (Object)this.getText(2131361891), null);
            return;
        }
        this.bodywater_tv.setText((CharSequence)"0.0%");
        this.bodyfat_tv.setText((CharSequence)"0.0%");
        this.bone_tv.setText((CharSequence)"0.0");
        this.musle_tv.setText((CharSequence)"0.0");
        this.visal_tv.setText((CharSequence)"0");
        this.bmi_tv.setText((CharSequence)"0");
        this.bmr_tv.setText((CharSequence)"0kcal");
        this.tvBmi.setText((CharSequence)"0.0");
        this.physicage_tv.setText((CharSequence)"0");
        if (UtilConstants.CURRENT_USER != null) {
            this.compare_tv.setTexts("0.0" + UtilConstants.CURRENT_USER.getDanwei(), null);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        int n = DisplayUtil.dip2px((Context)this, 64.0f);
        layoutParams.setMargins((int)UtilTooth.changeBMI(0.0f, DisplayUtil.getWidth((Context)this) - n), 0, 0, 0);
        this.rlGuide.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    private void showAlertDailog(String string2) {
        new com.lefu.es.view.AlertDialog((Context)this).builder().setTitle(this.getResources().getString(2131361795)).setMsg(string2).setPositiveButton(this.getResources().getString(2131361854), new View.OnClickListener(){

            public void onClick(View view) {
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)BodyFatScaleActivity.this);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_dailog", "1");
                UtilConstants.FIRST_INSTALL_DAILOG = "1";
            }
        }).show();
    }

    private void showTipMask() {
        HighLightGuideView.builder(this).setText(this.getString(2131361792)).addNoHighLightGuidView(2130837592).addHighLightGuidView((View)this.pager, 0, 0.5f, 1).addHighLightGuidView((View)this.bodywater_tv, 0, 10.0f, 2).setTouchOutsideDismiss(false).setOnDismissListener(new HighLightGuideView.OnDismissListener(){

            @Override
            public void onDismiss() {
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)BodyFatScaleActivity.this);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_bodyfat_scale", "1");
                UtilConstants.FIRST_INSTALL_BODYFAT_SCALE = "1";
            }
        }).show();
    }

    private void stopScanService() {
        if (UtilConstants.serveIntent != null) {
            this.stopService(UtilConstants.serveIntent);
        }
        if (BluetoolUtil.bleflag) {
            this.singleton.scanLeDevice(false, this, this.mServiceConnection);
        }
    }

    private void syncDate(String string2) {
        this.sendDateToScale(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public View creatView(Records records, ViewGroup viewGroup) {
        View view = null;
        MyTextView myTextView = null;
        TextView textView = null;
        if (!false) {
            view = this.inflater.inflate(2130903086, null);
            myTextView = (MyTextView)view.findViewById(2131296504);
            textView = (TextView)view.findViewById(2131296505);
            view.setTag((Object)records);
            view.setOnClickListener(this.imgOnClickListener);
        }
        if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
            if (UtilConstants.CURRENT_SCALE.equals((Object)UtilConstants.BODY_SCALE)) {
                String[] arrstring = UtilTooth.kgToStLbForScaleFat2(records.getRweight());
                myTextView.setTexts(arrstring[0], arrstring[1]);
                if (textView == null) return view;
                {
                    textView.setText(this.getText(2131361894));
                    return view;
                }
            } else {
                myTextView.setTexts(UtilTooth.kgToLB_ForFatScale(records.getRweight()), null);
                if (textView == null) return view;
                {
                    textView.setText(this.getText(2131361894));
                    return view;
                }
            }
        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB)) {
            myTextView.setTexts(UtilTooth.kgToLB_ForFatScale(records.getRweight()), null);
            if (textView == null) return view;
            {
                textView.setText(this.getText(2131361892));
                return view;
            }
        } else {
            myTextView.setTexts(String.valueOf((float)records.getRweight()), null);
            if (textView == null) return view;
            {
                textView.setText(this.getText(2131361891));
                return view;
            }
        }
    }

    protected void dialog(String string2, final int n) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setMessage((CharSequence)string2);
        builder.setNegativeButton(2131361855, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(2131361854, new DialogInterface.OnClickListener(){

            /*
             * Unable to fully structure code
             * Enabled aggressive exception aggregation
             */
            public void onClick(DialogInterface var1_1, int var2_2) {
                var4_3 = n;
                switch (var4_3) {
                    default: lbl-1000: // 3 sources:
                    {
                        do {
                            var1_1.dismiss();
                            return;
                            break;
                        } while (true);
                    }
                    case 2131296302: 
                }
                try {
                    if (BodyFatScaleActivity.access$26(BodyFatScaleActivity.this) != null) {
                        BodyFatScaleActivity.access$14(BodyFatScaleActivity.this).delete(BodyFatScaleActivity.access$26(BodyFatScaleActivity.this));
                    }
                    if ((CacheHelper.recordListDesc = BodyFatScaleActivity.access$14(BodyFatScaleActivity.this).getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, BodyFatScaleActivity.access$27(BodyFatScaleActivity.this), UtilConstants.CURRENT_USER.getBheigth())) == null || CacheHelper.recordListDesc.size() <= 0) ** GOTO lbl17
                    BodyFatScaleActivity.access$22(BodyFatScaleActivity.this, (Records)CacheHelper.recordListDesc.get(0));
lbl14: // 2 sources:
                    do {
                        BodyFatScaleActivity.this.handler.sendEmptyMessage(0);
                        ** GOTO lbl-1000
                        break;
                    } while (true);
lbl17: // 1 sources:
                    BodyFatScaleActivity.access$22(BodyFatScaleActivity.this, null);
                    ** continue;
                }
                catch (Exception var3_4) {
                    ** continue;
                }
            }
        });
        builder.create().show();
    }

    public void dueDate(String string2) {
        BodyFatScaleActivity bodyFatScaleActivity = this;
        synchronized (bodyFatScaleActivity) {
            if (!BlueSingleton.isIsdoing()) {
                BlueSingleton.setIsdoing(true);
                Intent intent = new Intent();
                intent.setFlags(268435456);
                intent.putExtra("duedate", string2);
                Records records = MyUtil.parseMeaage(this.recordService, string2);
                if (records != null && records.getScaleType() != null && records.getScaleType().equalsIgnoreCase(UtilConstants.CURRENT_SCALE)) {
                    if (TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_INSTALL_DAILOG)) {
                        records.getRbodywater();
                    }
                    if (records.getRweight() != 0.0f && records.getBodyAge() == 0.0f && records.getRbodyfat() == 0.0f && TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_RECEIVE_BODYFAT_SCALE_KEEP_STAND_WITH_BARE_FEET)) {
                        this.showAlertDailog(this.getResources().getString(2131362074));
                        if (UtilConstants.su == null) {
                            UtilConstants.su = new SharedPreferencesUtil((Context)this);
                        }
                        UtilConstants.su.editSharedPreferences("lefuconfig", "first_badyfat_scale_keep_stand_with_bare_feet", "1");
                        UtilConstants.FIRST_RECEIVE_BODYFAT_SCALE_KEEP_STAND_WITH_BARE_FEET = "1";
                    }
                    if (records.getUgroup() != null && Integer.parseInt((String)records.getUgroup().replace((CharSequence)"P", (CharSequence)"")) < 10) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("record", (Serializable)records);
                        intent.putExtras(bundle);
                        intent.setClass(this.getApplicationContext(), (Class)ReceiveAlertActivity.class);
                        this.startActivity(intent);
                    }
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n == 99) {
            this.ItemID = intent.getIntExtra("ItemID", 0);
            this.handler.sendEmptyMessage(0);
            return;
        } else {
            if (n != 31 || n2 != 0) return;
            {
                Toast.makeText((Context)this, (CharSequence)this.getString(2131361915), (int)1).show();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903043);
        this.recordService = new RecordService((Context)this);
        this.inflater = (LayoutInflater)this.getSystemService("layout_inflater");
        this.ItemID = this.getIntent().getIntExtra("ItemID", 0);
        this.initView();
        this.layoutView();
        SharedPreferencesUtil sharedPreferencesUtil = UtilConstants.su;
        String string2 = null;
        if (sharedPreferencesUtil != null) {
            string2 = (String)UtilConstants.su.readbackUp("lefuconfig", "bluetooth_type" + UtilConstants.CURRENT_USER.getId(), String.class);
        }
        System.out.println("\u84dd\u7259\u7c7b\u578b:" + string2);
        BluetoolUtil.bleflag = string2 == null || !"BT".equals((Object)string2);
        if (BluetoolUtil.bleflag) {
            try {
                this.mBluetoothAdapter = ((BluetoothManager)this.getSystemService("bluetooth")).getAdapter();
            }
            catch (NoClassDefFoundError var4_4) {
                var4_4.printStackTrace();
            }
            if (!this.mBluetoothAdapter.isEnabled()) {
                this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 31);
                this.isNotOpenBL = true;
            }
        } else {
            if (BluetoolUtil.mBluetoothAdapter == null) {
                BluetoolUtil.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (BluetoolUtil.mBluetoothAdapter != null && !BluetoolUtil.mBluetoothAdapter.isEnabled()) {
                this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 3);
            }
        }
        ExitApplication.getInstance().addActivity(this);
    }

    protected void onDestroy() {
        this.exit();
        super.onDestroy();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && keyEvent.getRepeatCount() == 0) {
            this.handler.sendEmptyMessage(5);
            return true;
        }
        if (n == 3) {
            this.exit();
            ExitApplication.getInstance().exit((Context)this);
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onPause() {
        this.isCurrentActivoty = false;
        this.stopScanService();
        super.onPause();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onResume() {
        super.onResume();
        this.isCurrentActivoty = true;
        if (!AppData.hasCheckData) {
            this.handler.postDelayed(this.CheckHasDataRunnable, 30000);
        }
        AppData.isCheckScale = false;
        if (AppData.reCheck) {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            intent.setClass((Context)this, (Class)ReCheckActivity.class);
            this.startActivity(intent);
            this.exit();
            ExitApplication.getInstance().exit((Context)this);
            return;
        } else {
            new Thread((Runnable)this).start();
            if (!BluetoolUtil.bleflag) return;
            {
                this.singleton = BlueSingleton.getInstance(this.handler);
                if (!this.mBluetoothAdapter.isEnabled() || this.singleton.getmConnected() || this.singleton.getmScanning()) return;
                {
                    this.singleton.scanLeDevice(true, this, this.mServiceConnection);
                    return;
                }
            }
        }
    }

    protected void onStart() {
        if (!BluetoolUtil.bleflag && UtilConstants.serveIntent == null) {
            UtilConstants.serveIntent = new Intent((Context)this, (Class)TimeService.class);
            this.startService(UtilConstants.serveIntent);
            new Thread(this.ScanRunnable).start();
            TimeService.scale_connect_state = this.scale_connect_state;
        }
        if (BluetoolUtil.bleflag && !receiverReleased) {
            this.registerReceiver(this.mGattUpdateReceiver, BluetoothLeService.makeGattUpdateIntentFilter());
            receiverReleased = false;
        }
        super.onStart();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        if (this.ItemID != 0) {
            try {
                UtilConstants.CURRENT_USER = this.uservice.find(this.ItemID);
                CacheHelper.recordListDesc = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, this.ItemID, 167.0f);
                this.curRecord = CacheHelper.recordListDesc != null && CacheHelper.recordListDesc.size() > 0 ? (Records)CacheHelper.recordListDesc.get(0) : null;
                Log.i((String)"BodyFatActivity", (String)("record time: " + String.valueOf((Object)this.curRecord.getRecordTime())));
                this.handler.sendEmptyMessage(0);
                return;
            }
            catch (Exception var1_1) {
                var1_1.printStackTrace();
            }
        }
    }

    public void scaleChangeAlert() {
        Intent intent = new Intent();
        intent.setClass(this.getApplicationContext(), (Class)ScaleChangeAlertActivity.class);
        this.startActivity(intent);
    }

    public void startDiscovery() {
        System.out.println("BT\u5f00\u59cb\u626b\u63cf...");
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
        this.registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.registerReceiver(this.mReceiver, intentFilter2);
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBtAdapter.startDiscovery();
    }

    public void stopDiscovery() {
        this.mBtAdapter.cancelDiscovery();
        this.unregisterReceiver(this.mReceiver);
    }

}

