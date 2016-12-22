package com.w8.w8monitor.android.dialog;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.utils.SettingsApp;

/**
 * Created by AleksandrP on 23.12.2016.
 */

public class DialogLogout extends AlertDialog implements View.OnClickListener,
        RealmObj.DeleteDataUserListener {

    private ProfileActivity mActivity;
    private LogoutListener mListener;

    public DialogLogout(LogoutListener mListener, ProfileActivity mActivity) {
        super(mActivity);
        this.mActivity = mActivity;
        this.mListener = mListener;
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.context_logout_dialog, null);

        initUi(view);
    }

    private void initUi(View mView) {
        mView.findViewById(R.id.bt_register).setOnClickListener(this);
        mView.findViewById(R.id.bt_erase_data).setOnClickListener(this);
        mView.findViewById(R.id.bt_cancel).setOnClickListener(this);
        this.setView(mView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                mListener.logoutOk(true);
                dismiss();
                break;
            case R.id.bt_erase_data:
                RealmObj.getInstance().deleteAllDataTestUser(this);
                break;
            case R.id.bt_cancel:
                dismiss();
                break;

        }
    }

    //    ===========================================
//            from DeleteDataUserListener mListener
//    ===========================================
    @Override
    public void isDeleteOk(boolean isDelete) {
        if (isDelete) {
            SettingsApp.getInstance().setFirstStart(false);
            SettingsApp.getInstance().setSettingsStatus(false);
            SettingsApp.getInstance().setMetric(false);
            SettingsApp.getInstance().setShowLoginTutorial(true);
            SettingsApp.getInstance().setShowMainTutorial(true);
            mActivity.logout();
        }
        dismiss();
    }

//    ===================================

    public interface LogoutListener {
        void logoutOk(boolean isOk);
    }

}
