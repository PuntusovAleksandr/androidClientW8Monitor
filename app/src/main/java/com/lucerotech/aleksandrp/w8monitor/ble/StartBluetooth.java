//package com.lucerotech.aleksandrp.w8monitor.ble;
//
//import android.app.Activity;
//import android.bluetooth.BluetoothA2dp;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.lucerotech.aleksandrp.w8monitor.App;
//import com.lucerotech.aleksandrp.w8monitor.R;
//
//import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.REQUEST_ENABLE_BT;
//
///**
// * Created by AleksandrP on 03.10.2016.
// */
//
//public class StartBluetooth {
//
//    private final String TAG = "START_BLE";
//
//    private Context mContext;
//    private BluetoothManager btManager;
//    private Activity mActivity;
//    private BluetoothHandler bluetoothHandler;
//    private boolean isConnected;
//
//    private BLEDeviceListAdapter mDeviceListAdapter;
//
//
//    public StartBluetooth(Activity mActivity) {
//        this.mActivity = mActivity;
//        this.mContext = App.getContext();
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//    public void startBLE() {
//        btManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
//        BluetoothAdapter btAdapter = btManager.getAdapter();
//        if (btAdapter != null && !btAdapter.isEnabled()) {
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            mActivity.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//        }
//    }
//
//
//    /**
//     * check bluetooth on device
//     */
//    public void checkBluetooth() {
//        registeredResieverBluetooth();
//
//        bluetoothHandler = new BluetoothHandler(mContext);
//        bluetoothHandler.setOnConnectedListener(new BluetoothHandler.OnConnectedListener() {
//
//            @Override
//            public void onConnected(boolean isConnected) {
//                setConnectStatus(isConnected);
//            }
//        });
//
//        // show data from BLE
//        bluetoothHandler.setOnRecievedDataListener(new BluetoothHandler.OnRecievedDataListener() {
//            @Override
//            public void onRecievedData(String bytes) {
//                parseAnswer(bytes);
//                System.out.println("Answer  " + bytes.toString() + "<-");
//            }
//
//        });
//
//        if (bluetoothHandler.isEnabled()) {
//            mActivity.onActivityResult(REQUEST_ENABLE_BT, Activity.RESULT_OK, null);
//        }
//    }
//
//    /**
//     * set status connection
//     *
//     * @param isConnected
//     */
//    public void setConnectStatus(boolean isConnected) {
//        logger("setConnectStatus " + isConnected);
//        this.isConnected = isConnected;
//        if (isConnected) {
//            showMessage(mContext.getString(R.string.connection_successful));
//        } else {
//            bluetoothHandler.onPause();
//            bluetoothHandler.onDestroy();
//        }
//        if (mDeviceListAdapter != null) {
//            mDeviceListAdapter.notifyDataSetChanged();
//        }
//    }
//
//    /**
//     * parse data from BLE
//     *
//     * @param mBytes
//     */
//    private void parseAnswer(String mBytes) {
//        logger("parseAnswer " + mBytes.toString());
//        System.out.println("Update data in DB " + mBytes);
//
//    }
//
//    private void showMessage(String str) {
//        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
//    }
//
//    //    =====================================================================
////    RECEIVER REGISTERED
////    =====================================================================
//    public void registeredResieverBluetooth() {
//        mContext.registerReceiver(mBluetoothReceiver, makeBluetoothIntentFilter());
//        logger("registeredResieverBluetooth");
//    }
//
//    private IntentFilter makeBluetoothIntentFilter() {
//        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//        intentFilter.addAction(BluetoothAdapter.EXTRA_STATE);
//        intentFilter.addAction(BluetoothAdapter.EXTRA_CONNECTION_STATE);
//        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
//        return intentFilter;
//    }
//
//
//    private final BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (mDeviceListAdapter != null) {
//                mDeviceListAdapter.notifyDataSetChanged();
//            }
//        }
//    };
//
//    /**
//     * for check log
//     */
//    private void logger(String log) {
//        Log.d(TAG, log);
//    }
//}
