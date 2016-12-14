/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Service
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCallback
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattDescriptor
 *  android.bluetooth.BluetoothGattService
 *  android.bluetooth.BluetoothManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Binder
 *  android.os.IBinder
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Iterator
 *  java.util.List
 *  java.util.UUID
 */
package com.lefu.es.ble;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.lefu.es.ble.SampleGattAttributes;
import com.lefu.es.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@SuppressLint(value={"NewApi"})
public class BluetoothLeService
extends Service {
    private static final String TAG = BluetoothLeService.class.getSimpleName();
    private final IBinder mBinder;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private final BluetoothGattCallback mGattCallback;

    public BluetoothLeService() {
        this.mGattCallback = new BluetoothGattCallback(){

            public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", bluetoothGattCharacteristic);
            }

            public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
                if (n == 0) {
                    BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", bluetoothGattCharacteristic);
                    BluetoothLeService.this.disconnect();
                }
            }

            public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int n, int n2) {
                if (n2 == 2) {
                    BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_CONNECTED");
                    BluetoothLeService.this.mBluetoothGatt.discoverServices();
                    return;
                } else {
                    if (n2 != 0) return;
                    {
                        BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED");
                        return;
                    }
                }
            }

            public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int n) {
            }

            public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int n, int n2) {
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int n) {
                if (n == 0) {
                    BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
                }
            }
        };
        this.mBinder = new LocalBinder();
    }

    private void broadcastUpdate(String string2) {
        this.sendBroadcast(new Intent(string2));
    }

    private void broadcastUpdate(String string2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Intent intent = new Intent(string2);
        intent.putExtra("com.example.bluetooth.le.EXTRA_DATA", StringUtils.bytes2HexString(bluetoothGattCharacteristic.getValue()));
        this.sendBroadcast(intent);
    }

    public static IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_CONNECTED");
        intentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
        intentFilter.addAction("com.example.bluetooth.le.ACTION_DATA_AVAILABLE");
        return intentFilter;
    }

    public void close() {
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean connect(String string2) {
        boolean bl = true;
        if (this.mBluetoothAdapter == null) return false;
        if (string2 == null) {
            return false;
        }
        if (this.mBluetoothDeviceAddress != null && string2.equals((Object)this.mBluetoothDeviceAddress) && this.mBluetoothGatt != null) {
            if (this.mBluetoothGatt.connect()) return bl;
            return false;
        }
        BluetoothDevice bluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(string2);
        if (bluetoothDevice == null) {
            return false;
        }
        this.mBluetoothGatt = bluetoothDevice.connectGatt((Context)this, bl, this.mGattCallback);
        this.mBluetoothDeviceAddress = string2;
        return bl;
    }

    public void disconnect() {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            return;
        }
        this.mBluetoothGatt.disconnect();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public BluetoothGattCharacteristic getCharacteristic(List<BluetoothGattService> list, String string2) {
        BluetoothGattService bluetoothGattService;
        if (list == null) {
            return null;
        }
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext()) return null;
        } while (!(bluetoothGattService = (BluetoothGattService)iterator.next()).getUuid().toString().substring(4, 8).endsWith("fff0"));
        return bluetoothGattService.getCharacteristic(UUID.fromString((String)("0000" + string2 + "-0000-1000-8000-00805f9b34fb")));
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (this.mBluetoothGatt == null) {
            return null;
        }
        return this.mBluetoothGatt.getServices();
    }

    public boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager)this.getSystemService("bluetooth");
            if (this.mBluetoothManager == null) {
                Log.e((String)TAG, (String)"Unable to initialize BluetoothManager.");
                return false;
            }
        }
        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBluetoothAdapter == null) {
            Log.e((String)TAG, (String)"Unable to obtain a BluetoothAdapter.");
            return false;
        }
        return true;
    }

    public IBinder onBind(Intent intent) {
        Log.d((String)TAG, (String)"onBind");
        return this.mBinder;
    }

    public void onCreate() {
        super.onCreate();
        Log.d((String)TAG, (String)"onCreate");
    }

    public boolean onUnbind(Intent intent) {
        Log.d((String)TAG, (String)"onUnbind");
        this.close();
        return super.onUnbind(intent);
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean bl) {
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, bl);
            BluetoothGattDescriptor bluetoothGattDescriptor = bluetoothGattCharacteristic.getDescriptor(UUID.fromString((String)SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            if (bluetoothGattDescriptor != null) {
                bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                this.mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
            }
        }
    }

    public void wirteCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
        }
    }

    public class LocalBinder
    extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

}

