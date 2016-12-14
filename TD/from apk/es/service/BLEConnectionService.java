/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothAdapter$LeScanCallback
 *  android.content.Intent
 *  android.os.IBinder
 *  java.lang.InterruptedException
 *  java.lang.Thread
 */
package com.lefu.es.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;

import com.lefu.es.ble.BlueSingleton;

public class BLEConnectionServiceonRecievedData
extends Service {
    BluetoothAdapter adapter;
    BlueSingleton blueSingleton;
    boolean isScan = false;
    Thread myThread;

    public BLEConnectionService() {
        this.myThread = new Thread(){

            public void run() {
                super.run();
                BLEConnectionService.this.isScan = BLEConnectionService.this.blueSingleton.getmScanning();
                do {
                    if (BLEConnectionService.this.isScan) {
                        continue;
                    }
                    BLEConnectionService.this.adapter.startLeScan(BLEConnectionService.this.blueSingleton.mLeScanCallback);
                    try {
                        Thread.sleep((long)3000);
                        continue;
                    }
                    catch (InterruptedException var2_1) {
                        var2_1.printStackTrace();
                        continue;
                    }
                    break;
                } while (true);
            }
        };
    }

    public IBinder onBind(Intent intent) {
        this.myThread.start();
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.blueSingleton = BlueSingleton.getInstance(null);
        this.adapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onStart(Intent intent, int n) {
        super.onStart(intent, n);
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        return super.onStartCommand(intent, n, n2);
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}

