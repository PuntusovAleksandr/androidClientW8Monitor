package com.lucerotech.aleksandrp.w8monitor.ble;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.Profile;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.general.MainActivity;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import java.util.List;

import io.realm.RealmList;

import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.logger;
import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.saveAllLogs;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.REQUEST_ENABLE_BT;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.TIME_CHECK_BLE;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothHandler {

    private final static String TAG = "BLE HANDLER";

    // scan bluetooth device
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mEnabled = false;
    private boolean mScanning = false;
    public static final long SCAN_PERIOD = 15000;
//    private BLEDeviceListAdapter mDevListAdapter;
//    private String mCurrentConnectedBLEAddr;

    // connect bluetooth device
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress = null;
    private boolean mConnected = false;
    private boolean isBroadcastRegistered = false;
    private OnRecievedDataListener onRecListener;
    private OnConnectedListener onConnectedListener;
    private OnScanListener onScanListener;
    private onResultScanDevice mScanDevice;

    public static final int REQUEST_ACCESS_FINE_LOCATION = 0x111;

    private List<BluetoothGattService> gattServices = null;

    private Context context;
    private Activity mActivity;

//    ===========================================================
//    Ansver from BT
//    ===========================================================

    /**
     * Ansver from BT for all activity and fragments
     */
    public interface OnRecievedDataListener {
        void onRecievedData(byte[] bytes);
    }
//    ===========================================================

    //    ===========================================================
    public interface OnConnectedListener {
        void onConnected(boolean isConnected);

        void disconnectBLEMain();
    }
    //    ===========================================================

    //    ===========================================================
    public interface OnScanListener {
        void onScan(BluetoothDevice device, int rssi, byte[] scanRecord);

        void onScanFinished();
    }

    //    ===========================================================
    //    ===========================================================
    public interface onResultScanDevice {
        void scanOk(boolean mEnabled);
    }
    //    ===========================================================

    //    ===========================================================
    public void setOnScanListener(OnScanListener l) {
        onScanListener = l;
    }

    public BluetoothHandler(final Activity mActivity, onResultScanDevice mScanDevice) {
        // TODO Auto-generated constructor stub
        this.mActivity = mActivity;
        this.context = mActivity;
        this.mScanDevice = mScanDevice;
//        mDevListAdapter = new BLEDeviceListAdapter(context);
        mBluetoothAdapter = null;
//        mCurrentConnectedBLEAddr = null;

//        makeDataPacage();
//        if (mActivity.getClass().getName().equalsIgnoreCase(MainActivity.class.getName())) {
//            checkPermission(mActivity);
//        }
    }

    public void checkPermission(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(mActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(mActivity,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                logger("not granted");
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    logger("shouldn't request permissions");
                } else {
                    logger("request permissions");
                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_ACCESS_FINE_LOCATION);
                }

            }

            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("You must enable location");
                dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(myIntent);
                        //get gps
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "The application may not work properly", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        }
    }

    // get data for connection and send by BLE
    private byte[] makeDataPacage() {
        UserLibr userLibr = RealmObj.getInstance().getUserForConnectBLE();
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        int gender = 0;
        int activityType = 0;
        int height = 0;
        int birthday = 0;
        RealmList<Profile> profiles = userLibr.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);
            if (profile.getNumber() == profileBLE) {
                gender = profile.getGender();
                activityType = profile.getActivity_type();
                height = profile.getHeight();
                birthday = profile.getBirthday();
                birthday = profile.getBirthday();
            }
        }

        if (userLibr == null) {
            return null;
        }
        byte data[] = new byte[8];
        data[0] = (byte) 0xfe;
        data[1] = (byte) profileBLE; // номер группы (это профиль BLE
        data[2] = (byte) gender;  // пол
        data[3] = (byte) activityType;     // какой то уровень
        data[4] = (byte) height;   // рост
        data[5] = (byte) birthday;  // возраст
        data[6] = (byte) (SettingsApp.getInstance().getMetric() ? 1 : 2); // какойто unit (метрическая система)

        byte check = 0;
        for (int i = 1; i < 7; i++) {
            check ^= data[i];
        }

        data[7] = check;

        String showText = "";
        for (int i = 0; i < data.length; i++) {
            showText = "\nshow   _ " + data[i];
            System.out.println(showText);
        }
        System.out.println("BYTE " + data.toString());

        String answerFromBLE = "ПЕРЕДАННЫЕ ___ " +
                "\n0xfe " + 0xfe +
                "\nProfileBLE " + userLibr.getProfileBLE() +
                "\nState " + gender +
                "\nTypeBody " + activityType +
                "\nHeight " + height +
                "\nвозраст " + birthday +
                "\nMetric " + (SettingsApp.getInstance().getMetric() ? 1 : 2) +
                "\ncheck " + check;
        logger(answerFromBLE);

//        for (int i = 0; i < 3; i++) {
//            Toast.makeText(App.getContext(), answerFromBLE, Toast.LENGTH_LONG).show();
//        }
        return data;
    }

    public boolean checkSupport() {
        logger("checkSupport");
        boolean answer = false;
        // close app
        if (!isSupportBle()) {
            Toast.makeText(context, R.string.device_not_support, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, TIME_CHECK_BLE);
            answer = false;
            return answer;
        }
        // open bluetooth
        Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (!getBluetoothAdapter().isEnabled()) {
            answer = false;
            mActivity.startActivityForResult(mIntent, REQUEST_ENABLE_BT);
        } else {
            setEnabled(true);
            answer = true;
            mScanDevice.scanOk(mEnabled);
        }
        return answer;
    }

//    public BLEDeviceListAdapter getDeviceListAdapter() {
//        return mDevListAdapter;
//    }

    // connect to ble
    public void connect(String deviceAddress, final Activity mActivity) {
        logger("connect deviceAddress = " + deviceAddress);
        this.mDeviceAddress = deviceAddress;

        boolean bll = false;
        if (mBluetoothLeService == null) {
            try {
                Intent gattServiceIntent = new Intent(mActivity, BluetoothLeService.class);
                bll = ((MainActivity) context).bindService(gattServiceIntent, mServiceConnection,
                        ((MainActivity) mActivity).BIND_AUTO_CREATE);
            } catch (Exception mE) {
                mE.printStackTrace();
            }
        } else {
            bll = true;
        }

        if (!bll) {
            System.out.println("bindService failed!");
        }

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logger("Fuck broadcast  " + isBroadcastRegistered);
//                if (!mGattUpdateReceiver.isOrderedBroadcast()) {
                if (!isBroadcastRegistered) {
//                    try {
//                        ((MainActivity) context).unregisterReceiver(mGattUpdateReceiver);
//                    } catch (Exception mE) {
//                        logger("unregisterReceiver " + mE.getMessage());
//                        mE.printStackTrace();
//                    }

                    ((MainActivity) mActivity).registerReceiver(mGattUpdateReceiver,
                            makeGattUpdateIntentFilter());
                    isBroadcastRegistered = true;
                    logger("Fuck OK broadcast  " + isBroadcastRegistered);
                }
            }
        });
    }

    public BroadcastReceiver getGattUpdateReceiver() {
        return mGattUpdateReceiver;
    }

    public boolean isBroadcastRegistered() {
        return isBroadcastRegistered;
    }

    public void disConnect() {
        logger("disConnect mBluetoothLeService = " + mBluetoothLeService);
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            Log.d(TAG, "Connect request result=");
        } else {
            System.out.println("mBluetoothLeService = null");
        }

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            System.out.println("SERVICE CONNECT");
            logger("SERVICE CONNECT");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                logger("onServiceConnected Unable to initialize Bluetooth");
                ((MainActivity) context).finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            byte[] userLibr = makeDataPacage();
            if (userLibr != null)
                mBluetoothLeService.connect(mDeviceAddress, userLibr);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("SERVICE DISCONNECT");
            logger("SERVICE DISCONNECT componentName " + componentName);
            mBluetoothLeService.disconnect();
            mBluetoothLeService = null;
            scanLeDevice(true);
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("BROADCAST CONNECT");
            logger("BROADCAST CONNECT");
            final String action = intent.getAction();
            //if connect to device = true
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                System.out.println("BROADCAST ACTION_GATT_CONNECTED");
                logger("BROADCAST ACTION_GATT_CONNECTED");
                mConnected = true;
//                mCurrentConnectedBLEAddr = mDeviceAddress;
                if (onConnectedListener != null) {
                    onConnectedListener.onConnected(true);
                }
                //if no connect to device = false
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                System.out.println("BROADCAST ACTION_GATT_DISCONNECTED");
                logger("BROADCAST ACTION_GATT_DISCONNECTED");
//                mConnected = false;
////                mCurrentConnectedBLEAddr = null;
//                if (onConnectedListener != null) {
//                    onConnectedListener.onConnected(false);
//                }
                onConnectedListener.disconnectBLEMain();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                System.out.println("BROADCAST ACTION_GATT_SERVICES_DISCOVERED");
                logger("BROADCAST ACTION_GATT_SERVICES_DISCOVERED");
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLEService.getSupportedGattServices());
                if (mBluetoothLeService != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // регистрация получения сообщений от весов
                            logger("регистрация получения сообщений от весов");
                            try {
                                BluetoothGattCharacteristic characteristic = mBluetoothLeService.getCharacteristic(
                                        mBluetoothLeService.getSupportedGattServices(), "fff4");
                                mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                            } catch (NullPointerException mE) {
                                saveAllLogs("characteristic " + mE.getMessage());
                                mE.printStackTrace();
                            }
                        }
                    }, 250);
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                System.out.println("BROADCAST ACTION_DATA_AVAILABLE");
                logger("BROADCAST ACTION_DATA_AVAILABLE");
                final byte[] value = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
//                //System.out.println("len:"+dataString.length()+"data:"+dataString);
                if (onRecListener != null) {
                    System.out.println("BROADCAST NULL");
                    onRecListener.onRecievedData(value);
//                    displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                }
            }
        }
    };

    public void setOnRecievedDataListener(OnRecievedDataListener l) {
        onRecListener = l;
    }

    public void setOnConnectedListener(OnConnectedListener l) {
        onConnectedListener = l;
    }

//    private final String LIST_NAME = "NAME";
//    private final String LIST_UUID = "UUID";
//
//    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
//            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
//
//    private BluetoothGattCharacteristic mNotifyCharacteristic;


//    public void getCharacteristic(List<BluetoothGattService> gattServices) {
//        this.gattServices = gattServices;
//        if (gattServices == null) return;
//        String uuid = null;
//        String unknownServiceString = "unknown_service)";
//        String unknownCharaString = "unknown_characteristic)";
//        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
//        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
//                = new ArrayList<ArrayList<HashMap<String, String>>>();
//        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
//
//        // Loops through available GATT Services.
//        for (BluetoothGattService gattService : gattServices) {
//            HashMap<String, String> currentServiceData = new HashMap<String, String>();
//            uuid = gattService.getUuid().toString();
//            currentServiceData.put(
//                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
//            currentServiceData.put(LIST_UUID, uuid);
//            gattServiceData.add(currentServiceData);
//
//            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
//                    new ArrayList<HashMap<String, String>>();
//            List<BluetoothGattCharacteristic> gattCharacteristics =
//                    gattService.getCharacteristics();
//            ArrayList<BluetoothGattCharacteristic> charas =
//                    new ArrayList<BluetoothGattCharacteristic>();
//
//            // Loops through available Characteristics.
//            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
//                charas.add(gattCharacteristic);
//                HashMap<String, String> currentCharaData = new HashMap<String, String>();
//                uuid = gattCharacteristic.getUuid().toString();
//                currentCharaData.put(
//                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
//                currentCharaData.put(LIST_UUID, uuid);
//                gattCharacteristicGroupData.add(currentCharaData);
//            }
//            mGattCharacteristics.add(charas);
//            gattCharacteristicData.add(gattCharacteristicGroupData);
//        }
//    }

    public void onPause() {
        // TODO Auto-generated method stub
        if (isBroadcastRegistered) {
            isBroadcastRegistered = false;
            try {
                ((MainActivity) context).unregisterReceiver(mGattUpdateReceiver);
            } catch (Exception mE) {
                logger("unregisterReceiver " + mE.getMessage());
                mE.printStackTrace();
            }
        }
    }

    public void onDestroy(boolean isScan) {
        if (mConnected) {
//            mDevListAdapter.clearDevice();
//            mDevListAdapter.notifyDataSetChanged();
            ((MainActivity) context).unbindService(mServiceConnection);
            System.out.println("SERVICE unbindService(mServiceConnection)");
            logger("SERVICE unbindService(mServiceConnection)");
            mBluetoothLeService = null;
            mConnected = false;
//            scanLeDevice(true);
        }
        scanLeDevice(isScan);
    }

    public boolean isSupportBle() {
        // is support 4.0 ?
        final BluetoothManager bluetoothManager = (BluetoothManager)
                context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null)
            return false;
        else
            return true;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.obj != null) {

                BluetoothHandler.BluetoothScanInfo device = (BluetoothScanInfo) msg.obj;

                System.out.println("DEVICE IS<><><><><<" +
                        device.device.getName() + "<>\n" +
                        device.device.toString());

                try {
                    if (device.device.getName().toLowerCase().contains("scale")) {
                        if (mConnected) {
                            disConnect();
                        }
                        connect(device.device.getAddress(), (MainActivity) mActivity);
                        scanLeDevice(false);
                    }
                } catch (NullPointerException mE) {
                    if (mConnected) {
                        disConnect();
                    }
                    logger("device.getName() " + mE.getMessage());
                    mE.printStackTrace();
                }
            }
        }
    };

    // scan device
    public void scanLeDevice(boolean enable) {
        logger("scanLeDevice enable  = " + enable);
        System.out.println("scanLeDevice enable  = " + enable);
        if (enable) {
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (mEnabled) {
//                        mScanning = false;
//                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                        if (onScanListener != null) {
//                            logger("scanLeDevice onScanListener");
//                            onScanListener.onScanFinished();
//                        }
//                    }
//                }
//            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            onScanListener.onScanFinished();
        }
    }

    public boolean isScanning() {
        return mScanning;
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi,
                             final byte[] scanRecord) {
            System.out.println("CALLBACK LESCAN");
            logger("CALLBACK LESCAN 537");

            if (onScanListener != null) {
                onScanListener.onScan(device, rssi, scanRecord);
            }

            System.out.println("scan info:");
            System.out.println("rssi=" + rssi);
            System.out.println("ScanRecord:");
            for (byte b : scanRecord)
                System.out.printf("%02X ", b);
            System.out.println("");

            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    BluetoothScanInfo info = new BluetoothScanInfo();
                    info.device = device;
                    info.rssi = rssi;
                    info.scanRecord = scanRecord;
                    msg.obj = info;
                    mHandler.sendMessage(msg);
                }
            });
        }
    };

    public class BluetoothScanInfo {
        public BluetoothDevice device;
        public int rssi;
        public byte[] scanRecord;
    }

    ;

    public static double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }
}
