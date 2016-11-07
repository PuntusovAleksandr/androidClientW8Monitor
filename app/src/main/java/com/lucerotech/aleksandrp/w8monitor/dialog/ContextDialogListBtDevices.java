//package com.lucerotech.aleksandrp.w8monitor.dialog;
//
//import android.app.AlertDialog;
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListView;
//
//import com.lucerotech.aleksandrp.w8monitor.R;
//import com.lucerotech.aleksandrp.w8monitor.ble.BLEDeviceListAdapter;
//
///**
// * Created by AleksandrP on 03.10.2016.
// */
//
//public class ContextDialogListBtDevices extends AlertDialog {
//
//    private BLEDeviceListAdapter mArrayAdapter;
//    private Context mContext;
//    private RefreshInterface mInterface;
//    private boolean flagCancel;
//
//    public ContextDialogListBtDevices(Context mContext, BLEDeviceListAdapter mArrayAdapter) {
//        super(mContext);
//        this.mContext = mContext;
//        this.mArrayAdapter = mArrayAdapter;
//
//        this.flagCancel = false;
//
//        if (mContext instanceof RefreshInterface) {
//            mInterface = (RefreshInterface) mContext;
//        }
//
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.context_dialog, null);
//        this.mContext = mContext;
//
//        initUi(view);
//    }
//
//    private void initUi(View mView) {
//        final ListView mListView = (ListView) mView.findViewById(R.id.lv_devices);
//        mListView.setAdapter(mArrayAdapter);
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                flagCancel = true;
//                BluetoothDevice device = mArrayAdapter.getItem(position).device;
//                // connect
//                mInterface.onClickItem(device.getAddress());
////                bluetoothHandler.connect(device.getAddress());
//            }
//        });
//
//
//        ImageButton ibCancel = (ImageButton) mView.findViewById(R.id.bt_cancel_refresh);
//        ibCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                flagCancel = true;
//                cancel();
//            }
//        });
//
//        Button mButton = (Button) mView.findViewById(R.id.bt_refresh);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mInterface.onRefresh();
//                mListView.smoothScrollToPosition(mArrayAdapter.getCount() + 1);
//            }
//        });
//
//        this.setView(mView);
//    }
//
//    public interface RefreshInterface {
//        void onRefresh();
//
//        void onClickItem(String mItem);
//    }
//
//    @Override
//    public void cancel() {
//        if (flagCancel) {
//            super.cancel();
//        }
//    }
//}
//
