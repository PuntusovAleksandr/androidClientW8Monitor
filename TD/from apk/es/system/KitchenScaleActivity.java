/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.FragmentManager
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
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.io.PrintStream
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.NoClassDefFoundError
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
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
import android.app.FragmentManager;
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
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.BluetoothChatService;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.TimeService;
import com.lefu.es.service.UserService;
import com.lefu.es.system.CustomDialogActivity;
import com.lefu.es.system.InfoActivity;
import com.lefu.es.system.KitchenInfoActivity;
import com.lefu.es.system.LoadingActivity;
import com.lefu.es.system.ReCheckActivity;
import com.lefu.es.system.ReceiveAlertActivity;
import com.lefu.es.system.RecordKitchenListActivity;
import com.lefu.es.system.ScaleChangeAlertActivity;
import com.lefu.es.system.SettingActivity;
import com.lefu.es.system.UserListActivity;
import com.lefu.es.system.fragment.MyDialogFragment;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.MyTextView;
import com.lefu.es.view.guideview.HighLightGuideView;
import com.lefu.es.view.kmpautotextview.KMPAutoComplTextView;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressLint(value={"HandlerLeak"})
public class KitchenScaleActivity
extends Activity
implements MyDialogFragment.NatureSelectListener,
Runnable {
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
    private ArrayAdapter<String> autoAdapter = null;
    private TextView bmi_tv;
    private TextView bmr_tv;
    private TextView bodyfat_tv;
    private TextView bodywater_tv;
    private TextView bone_tv;
    View.OnClickListener btnOnClickListener;
    private Records curRecord;
    private Button deletdImg;
    private TextView food_name;
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
    private TextView milkname_tx;
    private TextView musle_tv;
    private TextView norecord_tv;
    private ViewPager pager;
    private TextView physicage_tv;
    private List<Records> rList;
    private RecordService recordService;
    private ImageView rightImg;
    private TextView save_tv;
    private TextView scale_connect_state;
    private KMPAutoComplTextView search_et;
    private TextView search_tv;
    private NutrientBo selectNutrient = null;
    private int selectedPosition = -1;
    private int sendCodeCount = 0;
    private Button setingImg;
    private BlueSingleton singleton;
    private TextView time_tv;
    private Button unit_btn;
    private TextView username_tv;
    private UserService uservice;
    private ArrayList<View> views = new ArrayList();
    private TextView visal_tv;
    private TextView weight_textView17;

    public KitchenScaleActivity() {
        this.layoutClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass((Context)KitchenScaleActivity.this, (Class)RecordKitchenListActivity.class);
                if (view.getTag() != null) {
                    intent.putExtra("type", ((Integer)view.getTag()).intValue());
                }
                intent.addFlags(131072);
                KitchenScaleActivity.this.startActivityForResult(intent, 0);
            }
        };
        this.menuBtnOnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 2131296300: {
                        if (UtilConstants.CURRENT_SCALE.equals((Object)UtilConstants.KITCHEN_SCALE)) {
                            Intent intent = new Intent();
                            intent.setFlags(268435456);
                            intent.setClass((Context)KitchenScaleActivity.this, (Class)KitchenInfoActivity.class);
                            KitchenScaleActivity.this.startActivity(intent);
                            return;
                        }
                        Intent intent = new Intent();
                        intent.setFlags(268435456);
                        intent.setClass((Context)KitchenScaleActivity.this, (Class)InfoActivity.class);
                        KitchenScaleActivity.this.startActivity(intent);
                        return;
                    }
                    case 2131296301: {
                        Intent intent = new Intent();
                        intent.setFlags(268435456);
                        intent.setClass((Context)KitchenScaleActivity.this, (Class)SettingActivity.class);
                        KitchenScaleActivity.this.startActivity(intent);
                        return;
                    }
                    case 2131296302: 
                }
                KitchenScaleActivity.this.dialog(KitchenScaleActivity.this.getString(2131361847), view.getId());
            }
        };
        this.btnOnClickListener = new View.OnClickListener(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public void onClick(View var1_1) {
                switch (var1_1.getId()) {
                    default: {
                        return;
                    }
                    case 2131296338: {
                        var9_2 = new Intent();
                        var9_2.setClass((Context)KitchenScaleActivity.this, (Class)UserListActivity.class);
                        KitchenScaleActivity.this.startActivity(var9_2);
                        return;
                    }
                    case 2131296384: {
                        if (TextUtils.isEmpty((CharSequence)KitchenScaleActivity.access$0(KitchenScaleActivity.this).getText().toString())) {
                            Toast.makeText((Context)KitchenScaleActivity.this, (CharSequence)"Please input something", (int)0).show();
                            return;
                        }
                        var7_3 = CacheHelper.findNutrientByName(KitchenScaleActivity.access$0(KitchenScaleActivity.this).getText().toString());
                        if (var7_3 != null && var7_3.size() != 0) {
                            var8_4 = KitchenScaleActivity.this.getFragmentManager();
                            MyDialogFragment.newInstance(var7_3).show(var8_4, "dialog");
                            return;
                        }
                        Toast.makeText((Context)KitchenScaleActivity.this, (CharSequence)"No Data had been found", (int)0).show();
                        return;
                    }
                    case 2131296383: {
                        if (KitchenScaleActivity.access$1(KitchenScaleActivity.this) == null) return;
                        if (KitchenScaleActivity.access$2(KitchenScaleActivity.this) == null) return;
                        try {
                            var5_5 = KitchenScaleActivity.access$3(KitchenScaleActivity.this).findRecordsByScaleTypeAndFoodNameAndKg(UtilConstants.KITCHEN_SCALE, KitchenScaleActivity.access$2(KitchenScaleActivity.this).getNutrientDesc(), KitchenScaleActivity.access$1(KitchenScaleActivity.this).getRweight());
                            if (var5_5 != null) {
                                if (var5_5.size() > 0) return;
                            }
                            KitchenScaleActivity.access$1(KitchenScaleActivity.this).setRphoto(KitchenScaleActivity.access$2(KitchenScaleActivity.this).getNutrientDesc());
                            if (!UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) ** GOTO lbl31
                            KitchenScaleActivity.access$1(KitchenScaleActivity.this).setUnitType(3);
                            ** GOTO lbl45
lbl31: // 1 sources:
                            if (!UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB)) ** GOTO lbl37
                            KitchenScaleActivity.access$1(KitchenScaleActivity.this).setUnitType(1);
                            ** GOTO lbl45
                        }
                        catch (Exception var4_6) {
                            var4_6.printStackTrace();
                            return;
                        }
lbl37: // 1 sources:
                        if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML)) {
                            KitchenScaleActivity.access$1(KitchenScaleActivity.this).setUnitType(2);
                        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML2)) {
                            KitchenScaleActivity.access$1(KitchenScaleActivity.this).setUnitType(4);
                        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_G)) {
                            KitchenScaleActivity.access$1(KitchenScaleActivity.this).setUnitType(0);
                        }
lbl45: // 7 sources:
                        KitchenScaleActivity.access$4(KitchenScaleActivity.this, RecordDao.handleKitchenData(KitchenScaleActivity.access$3(KitchenScaleActivity.this), KitchenScaleActivity.access$1(KitchenScaleActivity.this), KitchenScaleActivity.access$2(KitchenScaleActivity.this)));
                        CacheHelper.recordListDesc = KitchenScaleActivity.access$3(KitchenScaleActivity.this).getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, KitchenScaleActivity.access$5(KitchenScaleActivity.this), UtilConstants.CURRENT_USER.getBheigth());
                        KitchenScaleActivity.this.handler.sendEmptyMessage(0);
                        KitchenScaleActivity.access$6(KitchenScaleActivity.this).setText((CharSequence)"");
                        return;
                    }
                    case 2131296387: 
                }
                try {
                    if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                        UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_G);
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB)) {
                        UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_FATLB);
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML)) {
                        UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_LB);
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML2)) {
                        UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_ML);
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_G)) {
                        UtilConstants.CURRENT_USER.setDanwei(UtilConstants.UNIT_ML2);
                    }
                    KitchenScaleActivity.this.handler.sendEmptyMessage(0);
                    KitchenScaleActivity.access$7(KitchenScaleActivity.this).update(UtilConstants.CURRENT_USER);
                    return;
                }
                catch (Exception var2_7) {
                    return;
                }
            }
        };
        this.imgOnClickListener = new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass((Context)KitchenScaleActivity.this, (Class)RecordKitchenListActivity.class);
                intent.putExtra("type", 0);
                if (view.getTag() != null) {
                    intent.putExtra("id", ((Records)view.getTag()).getId());
                } else {
                    intent.putExtra("id", -1);
                }
                intent.addFlags(131072);
                KitchenScaleActivity.this.startActivityForResult(intent, 0);
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
                        KitchenScaleActivity.this.setViews();
                        KitchenScaleActivity.this.Init(CacheHelper.recordListDesc);
                        if (TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_INSTALL_KITCHEN_SCALE) && CacheHelper.recordListDesc.size() > 0) {
                            KitchenScaleActivity.this.showTipMask();
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 1: {
                        KitchenScaleActivity.this.setViews();
                        return;
                    }
                    case 5: {
                        KitchenScaleActivity.this.exit();
                        ExitApplication.getInstance().exit((Context)KitchenScaleActivity.this);
                        return;
                    }
                    case 101: 
                }
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)LoadingActivity.mainActivty);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "scale", UtilConstants.CURRENT_SCALE);
                try {
                    KitchenScaleActivity.this.uservice.update(UtilConstants.CURRENT_USER);
                }
                catch (Exception var3_3) {
                    var3_3.printStackTrace();
                }
                ExitApplication.getInstance().exit((Context)KitchenScaleActivity.this);
                Intent intent = new Intent();
                intent.setClass((Context)KitchenScaleActivity.this, (Class)LoadingActivity.class);
                KitchenScaleActivity.this.startActivity(intent);
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
                KitchenScaleActivity.access$12(KitchenScaleActivity.this, ((BluetoothLeService.LocalBinder)iBinder).getService());
                if (!KitchenScaleActivity.this.mBluetoothLeService.initialize()) {
                    KitchenScaleActivity.this.finish();
                }
                KitchenScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                try {
                    Thread.sleep((long)200);
                }
                catch (InterruptedException var4_3) {
                    var4_3.printStackTrace();
                }
                KitchenScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                KitchenScaleActivity.access$14(KitchenScaleActivity.this, true);
            }

            public void onServiceDisconnected(ComponentName componentName) {
                KitchenScaleActivity.access$14(KitchenScaleActivity.this, false);
            }
        };
        this.mGattUpdateReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context context, Intent intent) {
                String string2 = intent.getAction();
                if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals((Object)string2)) {
                    KitchenScaleActivity.this.singleton.setmConnected(true);
                    return;
                } else if ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals((Object)string2)) {
                    KitchenScaleActivity.this.scale_connect_state.setText(2131361919);
                    if (KitchenScaleActivity.this.isNotOpenBL || !KitchenScaleActivity.this.singleton.getmConnected()) return;
                    {
                        KitchenScaleActivity.this.singleton.setmConnected(false);
                        if (KitchenScaleActivity.this.mBluetoothLeService != null) {
                            boolean bl = KitchenScaleActivity.this.mBluetoothLeService.connect(BluetoolUtil.mDeviceAddress);
                            Log.d((String)"BodyFatActivity", (String)("Connect request result=" + bl));
                        }
                        KitchenScaleActivity.this.singleton.scanLeDevice(true, KitchenScaleActivity.this, KitchenScaleActivity.this.mServiceConnection);
                        return;
                    }
                } else {
                    if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals((Object)string2)) {
                        KitchenScaleActivity.access$18(KitchenScaleActivity.this, 0);
                        KitchenScaleActivity.this.scale_connect_state.setText(2131361918);
                        AppData.hasCheckData = true;
                        Toast.makeText((Context)KitchenScaleActivity.this, (CharSequence)KitchenScaleActivity.this.getString(2131361917), (int)1).show();
                        Toast.makeText((Context)KitchenScaleActivity.this, (CharSequence)KitchenScaleActivity.this.getString(2131361917), (int)1).show();
                        new Thread(new Runnable(){

                            public void run() {
                                if (7.this.KitchenScaleActivity.this.mBluetoothLeService != null) {
                                    7.this.KitchenScaleActivity.this.send_Data();
                                }
                            }
                        }).start();
                        return;
                    }
                    if (!"com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals((Object)string2)) return;
                    {
                        String string3 = intent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA");
                        System.out.println("\u8bfb\u53d6\u5230\u6570\u636e\uff1a" + string3);
                        if (string3.equals((Object)UtilConstants.ERROR_CODE)) {
                            if (KitchenScaleActivity.this.sendCodeCount < 1) {
                                if (KitchenScaleActivity.this.mBluetoothLeService != null) {
                                    KitchenScaleActivity.this.send_Data();
                                }
                                KitchenScaleActivity kitchenScaleActivity = KitchenScaleActivity.this;
                                KitchenScaleActivity.access$18(kitchenScaleActivity, 1 + kitchenScaleActivity.sendCodeCount);
                            } else {
                                Toast.makeText((Context)KitchenScaleActivity.this, (CharSequence)KitchenScaleActivity.this.getString(2131361911), (int)1).show();
                            }
                        } else if (string3.equals((Object)UtilConstants.ERROR_CODE_TEST)) {
                            Toast.makeText((Context)KitchenScaleActivity.this, (CharSequence)KitchenScaleActivity.this.getString(2131361914), (int)0).show();
                        }
                        if (System.currentTimeMillis() - UtilConstants.receiveDataTime < 1000) return;
                        {
                            UtilConstants.receiveDataTime = System.currentTimeMillis();
                            if (string3.startsWith(UtilConstants.KITCHEN_SCALE)) {
                                KitchenScaleActivity.this.dueDate(string3);
                                return;
                            }
                            if (string3.length() > 31) {
                                if (string3.startsWith(UtilConstants.BATHROOM_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.BATHROOM_SCALE;
                                } else if (string3.startsWith(UtilConstants.BABY_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.BABY_SCALE;
                                } else if (string3.startsWith(UtilConstants.BODY_SCALE)) {
                                    UtilConstants.CURRENT_SCALE = UtilConstants.BODY_SCALE;
                                }
                                UtilConstants.CURRENT_USER.setScaleType(UtilConstants.CURRENT_SCALE);
                                RecordDao.dueDate(KitchenScaleActivity.this.recordService, string3);
                                KitchenScaleActivity.this.handler.sendEmptyMessage(101);
                                return;
                            }
                            if (!string3.equals((Object)UtilConstants.ERROR_CODE_GETDATE)) return;
                            {
                                KitchenScaleActivity.this.openErrorDiolg("2");
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
                            KitchenScaleActivity.this.stopDiscovery();
                            KitchenScaleActivity.this.handler.postDelayed(KitchenScaleActivity.this.ScanRunnable, 15000);
                            return;
                        }
                    }
                } else {
                    if (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals((Object)string2)) return;
                    {
                        KitchenScaleActivity.this.stopDiscovery();
                        KitchenScaleActivity.this.handler.postDelayed(KitchenScaleActivity.this.ScanRunnable, 10000);
                        return;
                    }
                }
            }
        };
        this.ScanRunnable = new Runnable(){

            public void run() {
                KitchenScaleActivity.this.startDiscovery();
            }
        };
        this.CheckHasDataRunnable = new Runnable(){

            public void run() {
                if (!AppData.hasCheckData && KitchenScaleActivity.this.isCurrentActivoty && !UtilConstants.isTipChangeScale) {
                    KitchenScaleActivity.this.scaleChangeAlert();
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
            this.weight_textView17.setVisibility(8);
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

    static /* synthetic */ Records access$1(KitchenScaleActivity kitchenScaleActivity) {
        return kitchenScaleActivity.curRecord;
    }

    static /* synthetic */ void access$12(KitchenScaleActivity kitchenScaleActivity, BluetoothLeService bluetoothLeService) {
        kitchenScaleActivity.mBluetoothLeService = bluetoothLeService;
    }

    static /* synthetic */ void access$14(KitchenScaleActivity kitchenScaleActivity, boolean bl) {
        kitchenScaleActivity.isServiceReg = bl;
    }

    static /* synthetic */ void access$18(KitchenScaleActivity kitchenScaleActivity, int n) {
        kitchenScaleActivity.sendCodeCount = n;
    }

    static /* synthetic */ NutrientBo access$2(KitchenScaleActivity kitchenScaleActivity) {
        return kitchenScaleActivity.selectNutrient;
    }

    static /* synthetic */ void access$24(KitchenScaleActivity kitchenScaleActivity, NutrientBo nutrientBo) {
        kitchenScaleActivity.selectNutrient = nutrientBo;
    }

    static /* synthetic */ void access$27(KitchenScaleActivity kitchenScaleActivity, int n) {
        kitchenScaleActivity.selectedPosition = n;
    }

    static /* synthetic */ void access$4(KitchenScaleActivity kitchenScaleActivity, Records records) {
        kitchenScaleActivity.curRecord = records;
    }

    static /* synthetic */ int access$5(KitchenScaleActivity kitchenScaleActivity) {
        return kitchenScaleActivity.ItemID;
    }

    static /* synthetic */ TextView access$6(KitchenScaleActivity kitchenScaleActivity) {
        return kitchenScaleActivity.food_name;
    }

    private void exit() {
        this.stopScanService();
        ((NotificationManager)this.getSystemService("notification")).cancel(0);
        if (LoadingActivity.mainActivty != null) {
            LoadingActivity.mainActivty.finish();
        }
        this.finish();
    }

    private void initView() {
        try {
            this.uservice = new UserService((Context)this);
            this.weight_textView17 = (TextView)this.findViewById(2131296337);
            this.norecord_tv = (TextView)this.findViewById(2131296278);
            this.username_tv = (TextView)this.findViewById(2131296271);
            this.milkname_tx = (TextView)this.findViewById(2131296386);
            if (UtilConstants.su != null) {
                UtilConstants.CHOICE_KG = (String)UtilConstants.su.readbackUp("lefuconfig", "unit", UtilConstants.UNIT_KG);
            }
            this.bodywater_tv = (TextView)this.findViewById(2131296297);
            this.bodyfat_tv = (TextView)this.findViewById(2131296308);
            this.bone_tv = (TextView)this.findViewById(2131296313);
            this.bmi_tv = (TextView)this.findViewById(2131296328);
            this.visal_tv = (TextView)this.findViewById(2131296321);
            this.musle_tv = (TextView)this.findViewById(2131296317);
            this.bmr_tv = (TextView)this.findViewById(2131296331);
            this.time_tv = (TextView)this.findViewById(2131296270);
            this.physicage_tv = (TextView)this.findViewById(2131296340);
            this.scale_connect_state = (TextView)this.findViewById(2131296273);
            this.food_name = (TextView)this.findViewById(2131296382);
            this.search_et = (KMPAutoComplTextView)this.findViewById(2131296385);
            this.search_tv = (TextView)this.findViewById(2131296384);
            this.save_tv = (TextView)this.findViewById(2131296383);
            this.infoImg = (Button)this.findViewById(2131296300);
            this.setingImg = (Button)this.findViewById(2131296301);
            this.deletdImg = (Button)this.findViewById(2131296302);
            this.headImg = (ImageView)this.findViewById(2131296338);
            this.intentImg = (ImageView)this.findViewById(2131296272);
            this.leftImg = (ImageView)this.findViewById(2131296277);
            this.rightImg = (ImageView)this.findViewById(2131296281);
            this.unit_btn = (Button)this.findViewById(2131296387);
            this.infoImg.setOnClickListener(this.menuBtnOnClickListener);
            this.deletdImg.setOnClickListener(this.menuBtnOnClickListener);
            this.setingImg.setOnClickListener(this.menuBtnOnClickListener);
            this.headImg.setOnClickListener(this.btnOnClickListener);
            this.intentImg.setOnClickListener(this.btnOnClickListener);
            this.search_tv.setOnClickListener(this.btnOnClickListener);
            this.save_tv.setOnClickListener(this.btnOnClickListener);
            this.unit_btn.setOnClickListener(this.btnOnClickListener);
            this.search_et.setDatas(CacheHelper.nutrientTempNameList);
            this.search_et.inputTextNull(this.food_name, this.selectNutrient);
            new Handler().postDelayed(new Runnable(){

                public void run() {
                    if (CacheHelper.nutrientNameList != null && CacheHelper.nutrientNameList.size() > 0) {
                        KitchenScaleActivity.this.search_et.setDatas(CacheHelper.nutrientNameList);
                    }
                }
            }, 5000);
            this.search_et.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener(){

                @Override
                public void onPopupItemClick(CharSequence charSequence) {
                    if (TextUtils.isEmpty((CharSequence)charSequence)) {
                        return;
                    }
                    NutrientBo nutrientBo = CacheHelper.queryNutrientsByName(charSequence.toString());
                    if (nutrientBo == null) {
                        Toast.makeText((Context)KitchenScaleActivity.this, (CharSequence)"No Data had been found", (int)0).show();
                        return;
                    }
                    KitchenScaleActivity.this.search_et.setText((CharSequence)nutrientBo.getNutrientDesc());
                    KitchenScaleActivity.access$24(KitchenScaleActivity.this, nutrientBo);
                    KitchenScaleActivity.this.natureSelectComplete(nutrientBo);
                }
            });
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
                    if (KitchenScaleActivity.this.views != null && KitchenScaleActivity.this.views.size() > 0) {
                        if (n == -1 + KitchenScaleActivity.this.views.size() || n == 0) {
                            if (n == 0) {
                                KitchenScaleActivity.this.pager.setCurrentItem(n + 1);
                            } else {
                                KitchenScaleActivity.this.pager.setCurrentItem(n - 1);
                            }
                        } else {
                            if (n > 0) {
                                KitchenScaleActivity.access$27(KitchenScaleActivity.this, n - 1);
                            } else {
                                KitchenScaleActivity.access$27(KitchenScaleActivity.this, 0);
                            }
                            KitchenScaleActivity.access$4(KitchenScaleActivity.this, (Records)CacheHelper.recordListDesc.get(KitchenScaleActivity.this.selectedPosition));
                            KitchenScaleActivity.this.handler.sendEmptyMessage(1);
                        }
                        if (KitchenScaleActivity.this.pager.getCurrentItem() < 2) {
                            KitchenScaleActivity.this.leftImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837610));
                        } else {
                            KitchenScaleActivity.this.leftImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837529));
                        }
                        if (KitchenScaleActivity.this.pager.getCurrentItem() >= -2 + KitchenScaleActivity.this.views.size()) {
                            KitchenScaleActivity.this.rightImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837649));
                            return;
                        }
                        KitchenScaleActivity.this.rightImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837533));
                        return;
                    }
                    KitchenScaleActivity.this.leftImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837610));
                    KitchenScaleActivity.this.rightImg.setImageDrawable(KitchenScaleActivity.this.getResources().getDrawable(2130837649));
                }
            });
            return;
        }
        catch (Exception var1_1) {
            Log.e((String)"BodyFatActivity", (String)var1_1.getMessage());
            return;
        }
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void sendDateToScale() {
        BluetoothGattCharacteristic bluetoothGattCharacteristic;
        String string2 = MyUtil.getUserInfo();
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return;
        }
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
        if ((bluetoothGattCharacteristic = this.mBluetoothLeService.getCharacteristic(this.mBluetoothLeService.getSupportedGattServices(), "fff4")) == null) return;
        this.mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristic, true);
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
            this.bodywater_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getRbodywater())) + "kcal"));
            this.bodyfat_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getRbodyfat())) + "g"));
            this.bone_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getRbone())) + "g"));
            this.musle_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getRmuscle())) + "g"));
            this.visal_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getRvisceralfat())) + "g"));
            this.bmr_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getRbmi())) + "mg"));
            this.bmi_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getRbmr())) + "mg"));
            this.physicage_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(this.curRecord.getBodyAge())) + "mg"));
            if (UtilConstants.CURRENT_USER != null && UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML2)) {
                this.milkname_tx.setVisibility(0);
            } else {
                this.milkname_tx.setVisibility(8);
            }
        } else {
            this.bodywater_tv.setText((CharSequence)"0.0kcal");
            this.bodyfat_tv.setText((CharSequence)"0.0g");
            this.bone_tv.setText((CharSequence)"0.0g");
            this.musle_tv.setText((CharSequence)"0.0g");
            this.visal_tv.setText((CharSequence)"0.0g");
            this.bmi_tv.setText((CharSequence)"0.0mg");
            this.bmr_tv.setText((CharSequence)"0.0mg");
            this.physicage_tv.setText((CharSequence)"0.0mg");
        }
        ((CheckBox)this.findViewById(2131296299)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Log.i((String)"BodyFatActivity", (String)"click to start scan");
                if (KitchenScaleActivity.this.mBluetoothAdapter.isEnabled() && KitchenScaleActivity.this.singleton != null) {
                    Log.i((String)"BodyFatActivity", (String)"start scan");
                    KitchenScaleActivity.this.singleton.scanLeDevice(false, KitchenScaleActivity.this, KitchenScaleActivity.this.mServiceConnection);
                    if (KitchenScaleActivity.this.mBluetoothLeService != null) {
                        KitchenScaleActivity.this.mBluetoothLeService.disconnect();
                    }
                    KitchenScaleActivity.this.singleton.scanLeDevice(true, KitchenScaleActivity.this, KitchenScaleActivity.this.mServiceConnection);
                }
            }
        });
    }

    private void showTipMask() {
        HighLightGuideView.builder(this).setText(this.getString(2131361804)).addNoHighLightGuidView(2130837592).addHighLightGuidView((View)this.pager, 0, 0.5f, 1).addHighLightGuidView((View)this.bodywater_tv, 0, 10.0f, 2).setTouchOutsideDismiss(false).setOnDismissListener(new HighLightGuideView.OnDismissListener(){

            @Override
            public void onDismiss() {
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)KitchenScaleActivity.this);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_kitchen_scale", "1");
                UtilConstants.FIRST_INSTALL_KITCHEN_SCALE = "1";
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

    /*
     * Enabled aggressive block sorting
     */
    public View creatView(Records records, ViewGroup viewGroup) {
        View view = null;
        MyTextView myTextView = null;
        TextView textView = null;
        if (!false) {
            view = this.inflater.inflate(2130903072, null);
            myTextView = (MyTextView)view.findViewById(2131296504);
            textView = (TextView)view.findViewById(2131296505);
            view.setTag((Object)records);
            view.setOnClickListener(this.imgOnClickListener);
        }
        if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
            myTextView.setTexts(UtilTooth.kgToFloz(records.getRweight()), null);
            if (textView == null) return view;
            {
                textView.setText(this.getText(2131361902));
                return view;
            }
        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB)) {
            myTextView.setTexts(UtilTooth.kgToLBoz(records.getRweight()), null);
            if (textView == null) return view;
            {
                textView.setText(this.getText(2131361895));
                return view;
            }
        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML2)) {
            myTextView.setTexts(String.valueOf((Object)UtilTooth.keep0Point(UtilTooth.kgToML(records.getRweight()))), null);
            if (textView == null) return view;
            {
                textView.setText(this.getText(2131361903));
                return view;
            }
        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML)) {
            myTextView.setTexts(String.valueOf((Object)UtilTooth.keep0Point(records.getRweight())), null);
            if (textView == null) return view;
            {
                textView.setText(this.getText(2131361901));
                return view;
            }
        } else {
            myTextView.setTexts(String.valueOf((Object)UtilTooth.keep0Point(records.getRweight())), null);
            if (textView == null) return view;
            {
                textView.setText(this.getText(2131361899));
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
                    if (KitchenScaleActivity.access$1(KitchenScaleActivity.this) != null) {
                        KitchenScaleActivity.access$3(KitchenScaleActivity.this).delete(KitchenScaleActivity.access$1(KitchenScaleActivity.this));
                    }
                    if ((CacheHelper.recordListDesc = KitchenScaleActivity.access$3(KitchenScaleActivity.this).getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, KitchenScaleActivity.access$5(KitchenScaleActivity.this), UtilConstants.CURRENT_USER.getBheigth())) == null || CacheHelper.recordListDesc.size() <= 0) ** GOTO lbl17
                    KitchenScaleActivity.access$4(KitchenScaleActivity.this, (Records)CacheHelper.recordListDesc.get(0));
lbl14: // 2 sources:
                    do {
                        KitchenScaleActivity.this.handler.sendEmptyMessage(0);
                        ** GOTO lbl-1000
                        break;
                    } while (true);
lbl17: // 1 sources:
                    KitchenScaleActivity.access$4(KitchenScaleActivity.this, null);
                    ** continue;
                }
                catch (Exception var3_4) {
                    ** continue;
                }
            }
        });
        builder.create().show();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dueDate(String string2) {
        KitchenScaleActivity kitchenScaleActivity = this;
        synchronized (kitchenScaleActivity) {
            if (!BlueSingleton.isIsdoing()) {
                BlueSingleton.setIsdoing(true);
                Intent intent = new Intent();
                intent.setFlags(268435456);
                intent.putExtra("duedate", string2);
                Records records = MyUtil.parseMeaage(this.recordService, string2);
                if (records != null && records.getScaleType() != null && records.getScaleType().equalsIgnoreCase(UtilConstants.CURRENT_SCALE)) {
                    if (TextUtils.isEmpty((CharSequence)this.food_name.getText())) {
                        this.selectNutrient = null;
                    } else if (this.selectNutrient != null) {
                        records.setRphoto(this.selectNutrient.getNutrientDesc());
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

    @Override
    public void natureSelectComplete(NutrientBo nutrientBo) {
        if (nutrientBo != null) {
            this.food_name.setText((CharSequence)nutrientBo.getNutrientDesc());
            this.selectNutrient = nutrientBo;
            if (this.curRecord != null) {
                boolean bl = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCalcium());
                float f = 0.0f;
                if (!bl) {
                    boolean bl2 = Tool.isDigitsOnly(nutrientBo.getNutrientCalcium());
                    f = 0.0f;
                    if (bl2) {
                        f = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientCalcium()) * this.curRecord.getRweight());
                    }
                }
                boolean bl3 = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientProtein());
                float f2 = 0.0f;
                if (!bl3) {
                    boolean bl4 = Tool.isDigitsOnly(nutrientBo.getNutrientProtein());
                    f2 = 0.0f;
                    if (bl4) {
                        f2 = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientProtein()) * this.curRecord.getRweight());
                    }
                }
                boolean bl5 = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientEnerg());
                float f3 = 0.0f;
                if (!bl5) {
                    boolean bl6 = Tool.isDigitsOnly(nutrientBo.getNutrientEnerg());
                    f3 = 0.0f;
                    if (bl6) {
                        f3 = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientEnerg()) * this.curRecord.getRweight());
                    }
                }
                boolean bl7 = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCarbohydrt());
                float f4 = 0.0f;
                if (!bl7) {
                    boolean bl8 = Tool.isDigitsOnly(nutrientBo.getNutrientCarbohydrt());
                    f4 = 0.0f;
                    if (bl8) {
                        f4 = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientCarbohydrt()) * this.curRecord.getRweight());
                    }
                }
                boolean bl9 = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientLipidTot());
                float f5 = 0.0f;
                if (!bl9) {
                    boolean bl10 = Tool.isDigitsOnly(nutrientBo.getNutrientLipidTot());
                    f5 = 0.0f;
                    if (bl10) {
                        f5 = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientLipidTot()) * this.curRecord.getRweight());
                    }
                }
                boolean bl11 = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFiberTD());
                float f6 = 0.0f;
                if (!bl11) {
                    boolean bl12 = Tool.isDigitsOnly(nutrientBo.getNutrientFiberTD());
                    f6 = 0.0f;
                    if (bl12) {
                        f6 = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientFiberTD()) * this.curRecord.getRweight());
                    }
                }
                boolean bl13 = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCholestrl());
                float f7 = 0.0f;
                if (!bl13) {
                    boolean bl14 = Tool.isDigitsOnly(nutrientBo.getNutrientCholestrl());
                    f7 = 0.0f;
                    if (bl14) {
                        f7 = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientCholestrl()) * this.curRecord.getRweight());
                    }
                }
                boolean bl15 = TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitB6());
                float f8 = 0.0f;
                if (!bl15) {
                    boolean bl16 = Tool.isDigitsOnly(nutrientBo.getNutrientVitB6());
                    f8 = 0.0f;
                    if (bl16) {
                        f8 = 0.01f * (Float.parseFloat((String)nutrientBo.getNutrientVitB6()) * this.curRecord.getRweight());
                    }
                }
                this.bodywater_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f3)) + "kcal"));
                this.bodyfat_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f2)) + "g"));
                this.bone_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f5)) + "g"));
                this.musle_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f4)) + "g"));
                this.visal_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f6)) + "g"));
                this.bmr_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f7)) + "mg"));
                this.bmi_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f8)) + "mg"));
                this.physicage_tv.setText((CharSequence)(String.valueOf((Object)UtilTooth.keep2Point(f)) + "mg"));
            }
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
        this.setContentView(2130903053);
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
                this.handler.sendEmptyMessage(0);
                return;
            }
            catch (Exception var1_1) {
                var1_1.printStackTrace();
            }
        }
    }

    public void saveSearchDate() {
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

