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
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
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
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.BluetoothChatService;
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
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressLint(value={"HandlerLeak"})
public class BabyScaleActivity
extends Activity
implements Runnable {
    private static final boolean D = true;
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String PAR_KEY = "com.tutor.objecttran.par";
    private static final int REQUEST_ENABLE_BT_CLICK = 31;
    private static final String TAG = "BathScaleActivity";
    private static boolean receiverReleased = false;
    private Runnable CheckHasDataRunnable;
    private int ItemID;
    private Runnable ScanRunnable;
    private MyPageAdapter adapter = null;
    private ImageView backgroundImage;
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
    private boolean isServiceReg;
    private ImageView leftImg;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBtAdapter;
    private final BroadcastReceiver mGattUpdateReceiver;
    private final BroadcastReceiver mReceiver;
    public final ServiceConnection mServiceConnection;
    View.OnClickListener menuBtnOnClickListener;
    private TextView norecord_tv;
    private ViewPager pager;
    private List<Records> rList;
    private RecordService recordService;
    private ImageView rightImg;
    RelativeLayout rlGuide = null;
    private TextView scale_connect_state;
    private int selectedPosition = -1;
    private int sendCodeCount = 0;
    private Button setingImg;
    private BlueSingleton singleton;
    private MyTextView2 targetv = null;
    private TextView time_tv;
    private TextView tvBmi = null;
    private TextView username_tv;
    private UserService uservice;
    private ArrayList<View> views = new ArrayList();

    public BabyScaleActivity() {
        this.menuBtnOnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 2131296300: {
                        Intent intent = new Intent();
                        intent.setFlags(268435456);
                        intent.setClass((Context)BabyScaleActivity.this, (Class)InfoActivity.class);
                        BabyScaleActivity.this.startActivity(intent);
                        return;
                    }
                    case 2131296301: {
                        Intent intent = new Intent();
                        intent.setFlags(268435456);
                        intent.setClass((Context)BabyScaleActivity.this, (Class)SettingActivity.class);
                        BabyScaleActivity.this.startActivity(intent);
                        return;
                    }
                    case 2131296302: 
                }
                BabyScaleActivity.this.dialog(BabyScaleActivity.this.getString(2131361847), view.getId());
            }
        };
        this.btnOnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 2131296298: 
                }
                Intent intent = new Intent();
                intent.setFlags(4194304);
                intent.setClass((Context)BabyScaleActivity.this, (Class)UserListActivity.class);
                BabyScaleActivity.this.startActivity(intent);
            }
        };
        this.imgOnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass((Context)BabyScaleActivity.this, (Class)RecordListActivity.class);
                intent.putExtra("type", 0);
                intent.addFlags(131072);
                BabyScaleActivity.this.startActivityForResult(intent, 0);
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
                        BabyScaleActivity.this.setViews();
                        BabyScaleActivity.this.Init(CacheHelper.recordListDesc);
                        if (TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_INSTALL_BABY_SCALE) && CacheHelper.recordListDesc.size() > 0) {
                            BabyScaleActivity.this.showTipMask();
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 1: {
                        BabyScaleActivity.this.setViews();
                        return;
                    }
                    case 5: {
                        BabyScaleActivity.this.exit();
                        ExitApplication.getInstance().exit((Context)BabyScaleActivity.this);
                        return;
                    }
                    case 101: 
                }
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)LoadingActivity.mainActivty);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
                try {
                    BabyScaleActivity.this.uservice.update(UtilConstants.CURRENT_USER);
                }
                catch (Exception var3_3) {
                    var3_3.printStackTrace();
                }
                ExitApplication.getInstance().exit((Context)BabyScaleActivity.this);
                Intent intent = new Intent();
                intent.setClass((Context)BabyScaleActivity.this, (Class)LoadingActivity.class);
                BabyScaleActivity.this.startActivity(intent);
            }
        };
        this.isServiceReg = false;
        this.mServiceConnection = new ServiceConnection(){

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                BabyScaleActivity.access$5(BabyScaleActivity.this, ((BluetoothLeService.LocalBinder)iBinder).getService());
                if (!BabyScaleActivity.this.mBluetoothLeService.initialize()) {
                    BabyScaleActivity.this.finish();
                }
                BabyScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                BabyScaleActivity.access$7(BabyScaleActivity.this, true);
            }

            public void onServiceDisconnected(ComponentName componentName) {
                BabyScaleActivity.access$5(BabyScaleActivity.this, null);
                BabyScaleActivity.access$7(BabyScaleActivity.this, false);
            }
        };
        this.mGattUpdateReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context context, Intent intent) {
                String string2 = intent.getAction();
                if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals((Object)string2)) {
                    BabyScaleActivity.this.singleton.setmConnected(true);
                    return;
                } else if ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals((Object)string2)) {
                    BabyScaleActivity.this.scale_connect_state.setText(2131361919);
                    if (!BabyScaleActivity.this.singleton.getmConnected()) return;
                    {
                        BabyScaleActivity.this.singleton.setmConnected(false);
                        if (BabyScaleActivity.this.mBluetoothLeService != null) {
                            boolean bl = BabyScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                            Log.d((String)"BathScaleActivity", (String)("Connect request result=" + bl));
                        }
                        BabyScaleActivity.this.singleton.scanLeDevice(true, BabyScaleActivity.this, BabyScaleActivity.this.mServiceConnection);
                        return;
                    }
                } else if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals((Object)string2)) {
                    BabyScaleActivity.access$10(BabyScaleActivity.this, 0);
                    BabyScaleActivity.this.scale_connect_state.setText(2131361918);
                    AppData.hasCheckData = true;
                    Toast.makeText((Context)BabyScaleActivity.this, (CharSequence)BabyScaleActivity.this.getString(2131361912), (int)1).show();
                    Toast.makeText((Context)BabyScaleActivity.this, (CharSequence)BabyScaleActivity.this.getString(2131361912), (int)1).show();
                    if (BabyScaleActivity.this.mBluetoothLeService == null) return;
                    {
                        new Thread(new Runnable(){

                            public void run() {
                                if (6.this.BabyScaleActivity.this.singleton.getmConnected() && 6.this.BabyScaleActivity.this.mBluetoothLeService != null) {
                                    6.this.BabyScaleActivity.this.send_Data();
                                }
                            }
                        }).start();
                        return;
                    }
                } else {
                    if (!"com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals((Object)string2)) return;
                    {
                        String string3 = intent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
                        System.out.println("\u6536\u5230\u6570\u636e\uff1a" + string3);
                        if (string3.equals((Object)UtilConstants.ERROR_CODE)) {
                            if (BabyScaleActivity.this.sendCodeCount <= 2) {
                                if (BabyScaleActivity.this.mBluetoothLeService != null) {
                                    BabyScaleActivity.this.send_Data();
                                }
                                BabyScaleActivity babyScaleActivity = BabyScaleActivity.this;
                                BabyScaleActivity.access$10(babyScaleActivity, 1 + babyScaleActivity.sendCodeCount);
                            } else {
                                Toast.makeText((Context)BabyScaleActivity.this, (CharSequence)BabyScaleActivity.this.getString(2131361911), (int)0).show();
                            }
                        } else if (string3.equals((Object)UtilConstants.ERROR_CODE_TEST)) {
                            Toast.makeText((Context)BabyScaleActivity.this, (CharSequence)BabyScaleActivity.this.getString(2131361914), (int)0).show();
                        }
                        if (System.currentTimeMillis() - UtilConstants.receiveDataTime < 1000) return;
                        {
                            UtilConstants.receiveDataTime = System.currentTimeMillis();
                            if (!string3.startsWith(UtilConstants.BABY_SCALE) && string3.length() > 31) {
                                if (string3.startsWith(UtilConstants.BODY_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.BODY_SCALE;
                                    UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                                    RecordDao.dueDate(BabyScaleActivity.this.recordService, string3);
                                } else if (string3.startsWith(UtilConstants.BATHROOM_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.BATHROOM_SCALE;
                                    UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                                    RecordDao.dueDate(BabyScaleActivity.this.recordService, string3);
                                } else if (string3.startsWith(UtilConstants.KITCHEN_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.KITCHEN_SCALE;
                                    UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                                    RecordDao.dueKitchenDate(BabyScaleActivity.this.recordService, string3, null);
                                }
                                BabyScaleActivity.this.handler.sendEmptyMessage(101);
                                return;
                            }
                            if (string3.equals((Object)UtilConstants.ERROR_CODE_GETDATE)) {
                                BabyScaleActivity.this.openErrorDiolg("2");
                                return;
                            }
                            if (!string3.startsWith("c") || string3.length() != 32) return;
                            {
                                BabyScaleActivity.this.dueDate(string3);
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
                            BabyScaleActivity.this.stopDiscovery();
                            BabyScaleActivity.this.handler.postDelayed(BabyScaleActivity.this.ScanRunnable, 15000);
                            return;
                        }
                    }
                } else {
                    if (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals((Object)string2)) return;
                    {
                        BabyScaleActivity.this.stopDiscovery();
                        BabyScaleActivity.this.handler.postDelayed(BabyScaleActivity.this.ScanRunnable, 10000);
                        return;
                    }
                }
            }
        };
        this.ScanRunnable = new Runnable(){

            public void run() {
                BabyScaleActivity.this.startDiscovery();
            }
        };
        this.CheckHasDataRunnable = new Runnable(){

            public void run() {
                if (!AppData.hasCheckData && BabyScaleActivity.this.isCurrentActivoty && !UtilConstants.isTipChangeScale) {
                    BabyScaleActivity.this.scaleChangeAlert();
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
        if (list == null || list.size() <= 0) {
            this.leftImg.setImageDrawable(this.getResources().getDrawable(2130837610));
            this.rightImg.setImageDrawable(this.getResources().getDrawable(2130837649));
            this.rightImg.setVisibility(4);
            this.leftImg.setVisibility(4);
            this.norecord_tv.setVisibility(4);
            this.pager.removeAllViews();
            return;
        }
        this.rightImg.setVisibility(0);
        this.leftImg.setVisibility(0);
        this.rightImg.setImageDrawable(this.getResources().getDrawable(2130837533));
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

    static /* synthetic */ void access$10(BabyScaleActivity babyScaleActivity, int n) {
        babyScaleActivity.sendCodeCount = n;
    }

    static /* synthetic */ void access$19(BabyScaleActivity babyScaleActivity, int n) {
        babyScaleActivity.selectedPosition = n;
    }

    static /* synthetic */ void access$21(BabyScaleActivity babyScaleActivity, Records records) {
        babyScaleActivity.curRecord = records;
    }

    static /* synthetic */ Records access$25(BabyScaleActivity babyScaleActivity) {
        return babyScaleActivity.curRecord;
    }

    static /* synthetic */ int access$26(BabyScaleActivity babyScaleActivity) {
        return babyScaleActivity.ItemID;
    }

    static /* synthetic */ void access$5(BabyScaleActivity babyScaleActivity, BluetoothLeService bluetoothLeService) {
        babyScaleActivity.mBluetoothLeService = bluetoothLeService;
    }

    static /* synthetic */ void access$7(BabyScaleActivity babyScaleActivity, boolean bl) {
        babyScaleActivity.isServiceReg = bl;
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
        this.norecord_tv = (TextView)this.findViewById(2131296278);
        this.username_tv = (TextView)this.findViewById(2131296271);
        this.compare_tv = (MyTextView2)this.findViewById(2131296280);
        this.targetv = (MyTextView2)this.findViewById(2131296297);
        if (UtilConstants.su != null) {
            UtilConstants.CHOICE_KG = (String)UtilConstants.su.readbackUp("lefuconfig", "unit", UtilConstants.UNIT_KG);
        }
        if (UtilConstants.CURRENT_USER != null) {
            this.compare_tv.setTexts("0.0" + UtilConstants.CURRENT_USER.getDanwei(), null);
        }
        this.leftImg = (ImageView)this.findViewById(2131296281);
        this.rightImg = (ImageView)this.findViewById(2131296282);
        this.rlGuide = (RelativeLayout)this.findViewById(2131296286);
        this.tvBmi = (TextView)this.findViewById(2131296287);
        this.time_tv = (TextView)this.findViewById(2131296270);
        this.scale_connect_state = (TextView)this.findViewById(2131296273);
        this.infoImg = (Button)this.findViewById(2131296300);
        this.setingImg = (Button)this.findViewById(2131296301);
        this.deletdImg = (Button)this.findViewById(2131296302);
        this.headImg = (ImageView)this.findViewById(2131296298);
        this.intentImg = (ImageView)this.findViewById(2131296272);
        this.backgroundImage = (ImageView)this.findViewById(2131296277);
        this.infoImg.setOnClickListener(this.menuBtnOnClickListener);
        this.deletdImg.setOnClickListener(this.menuBtnOnClickListener);
        this.setingImg.setOnClickListener(this.menuBtnOnClickListener);
        this.headImg.setOnClickListener(this.btnOnClickListener);
        this.intentImg.setOnClickListener(this.btnOnClickListener);
        this.pager = (ViewPager)this.findViewById(2131296279);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            public void onPageScrollStateChanged(int n) {
            }

            public void onPageScrolled(int n, float f, int n2) {
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onPageSelected(int n) {
                if (BabyScaleActivity.this.views != null && BabyScaleActivity.this.views.size() > 0) {
                    if (n == -1 + BabyScaleActivity.this.views.size() || n == 0) {
                        if (n == 0) {
                            BabyScaleActivity.this.pager.setCurrentItem(n + 1);
                        } else {
                            BabyScaleActivity.this.pager.setCurrentItem(n - 1);
                        }
                    } else {
                        if (n > 0) {
                            BabyScaleActivity.access$19(BabyScaleActivity.this, n - 1);
                        } else {
                            BabyScaleActivity.access$19(BabyScaleActivity.this, 0);
                        }
                        BabyScaleActivity.access$21(BabyScaleActivity.this, (Records)CacheHelper.recordListDesc.get(BabyScaleActivity.this.selectedPosition));
                        BabyScaleActivity.this.handler.sendEmptyMessage(1);
                    }
                    if (BabyScaleActivity.this.pager.getCurrentItem() < 2) {
                        BabyScaleActivity.this.leftImg.setImageDrawable(BabyScaleActivity.this.getResources().getDrawable(2130837610));
                    } else {
                        BabyScaleActivity.this.leftImg.setImageDrawable(BabyScaleActivity.this.getResources().getDrawable(2130837528));
                    }
                    if (BabyScaleActivity.this.pager.getCurrentItem() >= -2 + BabyScaleActivity.this.views.size()) {
                        BabyScaleActivity.this.rightImg.setImageDrawable(BabyScaleActivity.this.getResources().getDrawable(2130837649));
                        return;
                    }
                    BabyScaleActivity.this.rightImg.setImageDrawable(BabyScaleActivity.this.getResources().getDrawable(2130837532));
                    return;
                }
                BabyScaleActivity.this.leftImg.setImageDrawable(BabyScaleActivity.this.getResources().getDrawable(2130837610));
                BabyScaleActivity.this.rightImg.setImageDrawable(BabyScaleActivity.this.getResources().getDrawable(2130837649));
            }
        });
        if (UtilConstants.CURRENT_USER != null) {
            this.username_tv.setText((CharSequence)UtilConstants.CURRENT_USER.getUserName());
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                this.targetv.setTexts(String.valueOf((Object)UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight())) + (Object)this.getText(2131361891), null);
            } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                this.targetv.setTexts(String.valueOf((Object)UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight()))) + (Object)this.getText(2131361892), null);
            } else {
                this.targetv.setTexts(String.valueOf((Object)UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight())) + (Object)this.getText(2131361891), null);
            }
            if (UtilConstants.CURRENT_USER.getPer_photo() != null && !"".equals((Object)UtilConstants.CURRENT_USER.getPer_photo()) && !UtilConstants.CURRENT_USER.getPer_photo().equals((Object)"null")) {
                Bitmap bitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
                this.headImg.setImageBitmap(bitmap);
            }
        }
        ((CheckBox)this.findViewById(2131296299)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Log.i((String)"BathScaleActivity", (String)"click to start scan");
                if (BabyScaleActivity.this.mBluetoothAdapter.isEnabled() && BabyScaleActivity.this.singleton != null) {
                    Log.i((String)"BathScaleActivity", (String)"start scan");
                    BabyScaleActivity.this.singleton.scanLeDevice(false, BabyScaleActivity.this, BabyScaleActivity.this.mServiceConnection);
                    if (BabyScaleActivity.this.mBluetoothLeService != null) {
                        BabyScaleActivity.this.mBluetoothLeService.disconnect();
                    }
                    BabyScaleActivity.this.singleton.scanLeDevice(true, BabyScaleActivity.this, BabyScaleActivity.this.mServiceConnection);
                }
            }
        });
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
    private void send_Data() {
        this.sendDateToScale();
        try {
            Thread.sleep((long)500);
        }
        catch (InterruptedException var1_1) {
            var1_1.printStackTrace();
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
                this.targetv.setTexts(String.valueOf((Object)UtilTooth.toOnePonit(UtilConstants.CURRENT_USER.getTargweight())) + (Object)this.getText(2131361891), null);
            } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                this.targetv.setTexts(String.valueOf((Object)UtilTooth.onePoint(UtilTooth.kgToLB_target(UtilConstants.CURRENT_USER.getTargweight()))) + (Object)this.getText(2131361892), null);
            }
            if (UtilConstants.CURRENT_USER.getPer_photo() != null && !"".equals((Object)UtilConstants.CURRENT_USER.getPer_photo()) && !UtilConstants.CURRENT_USER.getPer_photo().equals((Object)"null")) {
                Bitmap bitmap = imageUtil.getBitmapfromPath(UtilConstants.CURRENT_USER.getPer_photo());
                this.headImg.setImageBitmap(bitmap);
            }
        }
        if (this.curRecord != null) {
            Date date = UtilTooth.stringToTime(this.curRecord.getRecordTime());
            if (date != null) {
                Locale.getDefault();
                this.time_tv.setText((CharSequence)StringUtils.getDateString(date, 5));
            }
            float f = UtilTooth.countBMI2(this.curRecord.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0f);
            this.curRecord.setRbmi(UtilTooth.myround(f));
            this.tvBmi.setText((CharSequence)String.valueOf((float)this.curRecord.getRbmi()));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            int n = DisplayUtil.dip2px((Context)this, 64.0f);
            int n2 = DisplayUtil.getWidth((Context)this) - n;
            layoutParams.setMargins((int)UtilTooth.changeBMIBaby(this.curRecord.getRbmi(), n2), 0, 0, 0);
            this.rlGuide.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                this.backgroundImage.setBackgroundResource(2130837536);
                if (this.curRecord.getCompareRecord() == null || "".equals((Object)this.curRecord.getCompareRecord())) {
                    this.compare_tv.setTexts("0.0", null);
                    this.compare_tv.setTexts("0.0 " + (Object)this.getText(2131361891), null);
                    return;
                }
                float f2 = new BigDecimal(Double.parseDouble((String)this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
                if (f2 > 0.0f) {
                    this.compare_tv.setTexts("\u2191" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f2))).toString()) + (Object)this.getText(2131361891), null);
                    return;
                }
                if (f2 < 0.0f) {
                    this.compare_tv.setTexts("\u2193" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f2))).toString()) + (Object)this.getText(2131361891), null);
                    return;
                }
                this.compare_tv.setTexts(String.valueOf((Object)UtilTooth.myroundString3(new StringBuilder(String.valueOf((Object)this.curRecord.getCompareRecord())).toString())) + (Object)this.getText(2131361891), null);
                return;
            }
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                this.backgroundImage.setBackgroundResource(2130837537);
                if (this.curRecord.getCompareRecord() == null || "".equals((Object)this.curRecord.getCompareRecord().trim())) {
                    this.curRecord.setCompareRecord("0");
                    this.compare_tv.setTexts("0.0  " + (Object)this.getText(2131361892), null);
                    return;
                }
                float f3 = Float.parseFloat((String)this.curRecord.getCompareRecord());
                if (f3 > 0.0f) {
                    this.compare_tv.setTexts("\u2191" + UtilTooth.kgToLB(Math.abs((float)Float.parseFloat((String)this.curRecord.getCompareRecord()))) + " " + (Object)this.getText(2131361892), null);
                    return;
                }
                if (f3 < 0.0f) {
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
                float f4 = Float.parseFloat((String)this.curRecord.getCompareRecord());
                String string2 = UtilTooth.kgToLB_new(Math.abs((float)Float.parseFloat((String)this.curRecord.getCompareRecord())));
                String[] arrstring = UtilTooth.kgToStLbForScaleFat2(Math.abs((float)Float.parseFloat((String)this.curRecord.getCompareRecord())));
                if (f4 > 0.0f) {
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
                if (f4 >= 0.0f) {
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
            this.backgroundImage.setBackgroundResource(2130837536);
            if (this.curRecord.getCompareRecord() == null || "".equals((Object)this.curRecord.getCompareRecord())) {
                this.compare_tv.setTexts("0.0", null);
                this.compare_tv.setTexts("0.0 " + (Object)this.getText(2131361891), null);
                return;
            }
            float f5 = new BigDecimal(Double.parseDouble((String)this.curRecord.getCompareRecord())).setScale(2, 4).floatValue();
            if (f5 > 0.0f) {
                this.compare_tv.setTexts("\u2191" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f5))).toString()) + (Object)this.getText(2131361891), null);
                return;
            }
            if (f5 < 0.0f) {
                this.compare_tv.setTexts("\u2193" + UtilTooth.myroundString3(new StringBuilder(String.valueOf((float)Math.abs((float)f5))).toString()) + (Object)this.getText(2131361891), null);
                return;
            }
            this.compare_tv.setTexts(String.valueOf((Object)UtilTooth.myroundString3(new StringBuilder(String.valueOf((Object)this.curRecord.getCompareRecord())).toString())) + (Object)this.getText(2131361891), null);
            return;
        }
        if (UtilConstants.CURRENT_USER != null) {
            this.compare_tv.setTexts("0.0 " + UtilConstants.CURRENT_USER.getDanwei(), null);
        }
        this.tvBmi.setText((CharSequence)"0.0");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        int n = DisplayUtil.dip2px((Context)this, 64.0f);
        layoutParams.setMargins((int)UtilTooth.changeBMIBaby(0.0f, DisplayUtil.getWidth((Context)this) - n), 0, 0, 0);
        this.rlGuide.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    private void showTipMask() {
        HighLightGuideView.builder(this).setText(this.getString(2131361792)).addNoHighLightGuidView(2130837592).addHighLightGuidView((View)this.pager, 0, 0.5f, 1).setTouchOutsideDismiss(false).setOnDismissListener(new HighLightGuideView.OnDismissListener(){

            @Override
            public void onDismiss() {
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)BabyScaleActivity.this);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_baby_scale", "1");
                UtilConstants.FIRST_INSTALL_BABY_SCALE = "1";
            }
        }).show();
    }

    private void stopScanService() {
        if (UtilConstants.serveIntent != null) {
            this.stopService(UtilConstants.serveIntent);
        }
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
            TextView textView2 = (TextView)view.findViewById(2131296506);
            View view2 = view.findViewById(2131296503);
            textView2.setVisibility(8);
            view2.setVisibility(8);
            view.setTag((Object)records);
            view.setOnClickListener(this.imgOnClickListener);
        }
        if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_ST)) {
            if (UtilConstants.CURRENT_SCALE.equals((Object)UtilConstants.BODY_SCALE)) {
                String[] arrstring = UtilTooth.kgToStLbForScaleFat2(records.getRweight());
                myTextView.setTexts(arrstring[0], arrstring[1]);
                if (textView == null) return view;
                {
                    textView.setText(this.getText(2131361894));
                    return view;
                }
            } else {
                myTextView.setTexts(UtilTooth.kgToLB_new(records.getRweight()), null);
                if (textView == null) return view;
                {
                    textView.setText(this.getText(2131361894));
                    return view;
                }
            }
        } else if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_LB)) {
            myTextView.setTexts(UtilTooth.kgToLB(records.getRweight()), null);
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
                    if (BabyScaleActivity.access$25(BabyScaleActivity.this) != null) {
                        BabyScaleActivity.access$13(BabyScaleActivity.this).delete(BabyScaleActivity.access$25(BabyScaleActivity.this));
                    }
                    if ((CacheHelper.recordListDesc = BabyScaleActivity.access$13(BabyScaleActivity.this).getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_USER.getScaleType(), BabyScaleActivity.access$26(BabyScaleActivity.this), 167.0f)) == null || CacheHelper.recordListDesc.size() <= 0) ** GOTO lbl17
                    BabyScaleActivity.access$21(BabyScaleActivity.this, (Records)CacheHelper.recordListDesc.get(0));
lbl14: // 2 sources:
                    do {
                        BabyScaleActivity.this.handler.sendEmptyMessage(0);
                        ** GOTO lbl-1000
                        break;
                    } while (true);
lbl17: // 1 sources:
                    BabyScaleActivity.access$21(BabyScaleActivity.this, null);
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
        BabyScaleActivity babyScaleActivity = this;
        synchronized (babyScaleActivity) {
            if (!BlueSingleton.isIsdoing()) {
                BlueSingleton.setIsdoing(true);
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
        this.setContentView(2130903041);
        this.recordService = new RecordService((Context)this);
        this.inflater = (LayoutInflater)this.getSystemService("layout_inflater");
        this.ItemID = this.getIntent().getIntExtra("ItemID", 0);
        this.initView();
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
            catch (NoClassDefFoundError var5_4) {
                var5_4.printStackTrace();
            }
            if (!this.mBluetoothAdapter.isEnabled()) {
                this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 31);
            }
        } else {
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
                new SharedPreferencesUtil((Context)this).editSharedPreferences("lefuconfig", "user", this.ItemID);
                CacheHelper.recordListDesc = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.BABY_SCALE, this.ItemID, 167.0f);
                this.curRecord = CacheHelper.recordListDesc != null && CacheHelper.recordListDesc.size() > 0 ? (Records)CacheHelper.recordListDesc.get(0) : null;
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

