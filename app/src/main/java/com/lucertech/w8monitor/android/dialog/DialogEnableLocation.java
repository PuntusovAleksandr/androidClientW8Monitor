package com.lucertech.w8monitor.android.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lucertech.w8monitor.android.R;
import com.lucertech.w8monitor.android.utils.SettingsApp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.REQUEST_ACTION_LOCATION_SOURCE_SETTINGS;

/**
 * Created by AleksandrP on 01.12.2016.
 */

public class DialogEnableLocation extends AlertDialog {

    private Activity mActivity;
    private Context mContext;

    @Bind(R.id.bt_cancel)
    Button bt_cancel;
    @Bind(R.id.bt_settings)
    Button bt_settings;

    public DialogEnableLocation(Activity mActivity, Context mContext) {
        super(mContext);
        this.mContext = mContext;
        this.mActivity = mActivity;
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.context_enable_local_dialog, null);
        ButterKnife.bind(this, view);


        initUi(view);
    }


    private void initUi(View mView) {
        if (SettingsApp.getInstance().isThemeDark()) {

            bt_cancel.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_bt_dark));
            bt_settings.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_bt_dark));
        }else {
            bt_cancel.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_bt));
            bt_settings.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_bt));
        }
        this.setView(mView);
    }

    @OnClick(R.id.bt_settings)
    public void bt_settingsClick() {
        //get gps
        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mActivity.startActivityForResult(myIntent, REQUEST_ACTION_LOCATION_SOURCE_SETTINGS);
        dismiss();
    }

    @OnClick(R.id.bt_cancel)
    public void bt_cancelClick() {
        Toast.makeText(mContext, R.string.for_work_ble, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void cancel() {
        Toast.makeText(mContext, R.string.for_work_ble, Toast.LENGTH_SHORT).show();
    }
}
