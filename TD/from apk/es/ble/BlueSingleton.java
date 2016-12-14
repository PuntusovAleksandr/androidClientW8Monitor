/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothAdapter$LeScanCallback
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Handler
 *  android.util.Log
 *  java.io.PrintStream
 *  java.io.Serializable
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 */
package com.lefu.es.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.util.Log;
import com.lefu.es.ble.BleAdvertisedData;
import com.lefu.es.ble.BleUtil;
import com.lefu.es.ble.BluetoothLeService;
import com.lefu.es.constant.BluetoolUtil;
import java.io.PrintStream;
import java.io.Serializable;

public class BlueSingleton
implements Serializable {
    static boolean isdoings = false;
    private static final long serialVersionUID = -7953127527312783591L;
    private static BlueSingleton uniqueInstance = null;
    private Activity activity;
    public boolean isExit = false;
    public boolean isSearch = false;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mConnected = false;
    private boolean mHasBind;
    public BluetoothAdapter.LeScanCallback mLeScanCallback;
    private boolean mScanning = false;
    private ServiceConnection mServiceConnection;

    static {
        isdoings = false;
    }

    private BlueSingleton() {
        this.mLeScanCallback = new BluetoothAdapter.LeScanCallback(){

            public void onLeScan(final BluetoothDevice bluetoothDevice, int n, final byte[] arrby) {
                new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    public void run() {
                        try {
                            BleAdvertisedData bleAdvertisedData = BleUtil.parseAdertisedData(arrby);
                            String string2 = bluetoothDevice.getName();
                            if (string2 == null) {
                                string2 = bleAdvertisedData.getName();
                            }
                            if (bluetoothDevice != null && string2 != null && string2.indexOf("Scale") >= 0) {
                                System.out.println("\u53d1\u73b0BLE\u79f0=" + string2 + "[" + bluetoothDevice.getAddress() + "]");
                                .access$0(()1.this).isSearch = true;
                                if (1.this.BlueSingleton.this.mScanning) {
                                    1.this.BlueSingleton.this.mBluetoothAdapter.stopLeScan(.access$0(()1.this).mLeScanCallback);
                                    BlueSingleton.access$2(1.this.BlueSingleton.this, false);
                                }
                                BluetoolUtil.mDeviceAddress = bluetoothDevice.getAddress();
                                BluetoolUtil.mConnectedDeviceName = string2;
                                if (1.this.BlueSingleton.this.mHasBind) {
                                    1.this.BlueSingleton.this.activity.getApplicationContext().unbindService(1.this.BlueSingleton.this.mServiceConnection);
                                    BlueSingleton.access$6(1.this.BlueSingleton.this, false);
                                }
                                1.this.BlueSingleton.this.activity.getApplicationContext().bindService(new Intent((Context)1.this.BlueSingleton.this.activity, (Class)BluetoothLeService.class), 1.this.BlueSingleton.this.mServiceConnection, 1);
                                BlueSingleton.access$6(1.this.BlueSingleton.this, true);
                            }
                            return;
                        }
                        catch (Exception var1_3) {
                            var1_3.printStackTrace();
                            return;
                        }
                    }
                }.run();
            }

        };
    }

    static /* synthetic */ void access$2(BlueSingleton blueSingleton, boolean bl) {
        blueSingleton.mScanning = bl;
    }

    static /* synthetic */ void access$6(BlueSingleton blueSingleton, boolean bl) {
        blueSingleton.mHasBind = bl;
    }

    public static BlueSingleton getInstance(Handler handler) {
        if (uniqueInstance == null) {
            uniqueInstance = new BlueSingleton();
        }
        return uniqueInstance;
    }

    public static boolean isIsdoing() {
        reference var2 = BlueSingleton.class;
        synchronized (BlueSingleton.class) {
            boolean bl = isdoings;
            // ** MonitorExit[var2] (shouldn't be in output)
            return bl;
        }
    }

    public static void setIsdoing(boolean bl) {
        reference var2_1 = BlueSingleton.class;
        synchronized (BlueSingleton.class) {
            isdoings = bl;
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return;
        }
    }

    public boolean getSearchState() {
        return this.isSearch;
    }

    public boolean getmConnected() {
        return this.mConnected;
    }

    public boolean getmScanning() {
        return this.mScanning;
    }

    public void linkout() {
        if (this.mScanning && !this.mConnected) {
            this.scanLeDevice(false, this.activity, this.mServiceConnection);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void scanLeDevice(boolean bl, Activity activity, ServiceConnection serviceConnection) {
        try {
            this.activity = activity;
            this.mServiceConnection = serviceConnection;
            BluetoothManager bluetoothManager = (BluetoothManager)activity.getSystemService("bluetooth");
            if (this.mBluetoothAdapter == null) {
                this.mBluetoothAdapter = bluetoothManager.getAdapter();
            }
            if (bl) {
                this.isSearch = false;
                this.mScanning = true;
                new ScanThread().start();
                return;
            }
            this.mScanning = false;
            if (this.mBluetoothAdapter == null) {
                this.mBluetoothAdapter = bluetoothManager.getAdapter();
            }
            this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
            return;
        }
        catch (Exception var4_5) {
            var4_5.printStackTrace();
            return;
        }
    }

    public void setmConnected(boolean bl) {
        this.mConnected = bl;
    }

    public void setmScanning(boolean bl) {
        this.mScanning = bl;
    }

    public void setmServiceConnection(ServiceConnection serviceConnection) {
        this.mServiceConnection = serviceConnection;
    }

    class ScanThread
    extends Thread {
        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void run() {
            super.run();
            while (!BlueSingleton.this.isExit) {
                if (BlueSingleton.this.getSearchState()) {
                    return;
                }
                Log.e((String)"bluesingleton", (String)"ScanThread...");
                if (BlueSingleton.this.mBluetoothAdapter == null) return;
                BlueSingleton.this.mBluetoothAdapter.startLeScan(BlueSingleton.this.mLeScanCallback);
                try {
                    Thread.sleep((long)3000);
                }
                catch (InterruptedException var3_1) {
                    var3_1.printStackTrace();
                }
                if (BlueSingleton.this.getSearchState()) continue;
                BlueSingleton.access$2(BlueSingleton.this, false);
                BlueSingleton.this.mBluetoothAdapter.stopLeScan(BlueSingleton.this.mLeScanCallback);
            }
        }
    }

}

