package com.w8.w8monitor.android.dialog;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;

/**
 * Created by AleksandrP on 23.12.2016.
 */

public class DialogRegister extends AlertDialog implements View.OnClickListener {

    private ProfileActivity mActivity;

    public DialogRegister(ProfileActivity mActivity) {
        super(mActivity);
        this.mActivity = mActivity;
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.context_new_register_dialog, null);

        initUi(view);
    }

    private void initUi(View mView) {
        mView.findViewById(R.id.bt_register_email).setOnClickListener(this);
        mView.findViewById(R.id.bt_register_fb).setOnClickListener(this);
        mView.findViewById(R.id.bt_cancel).setOnClickListener(this);
        this.setView(mView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register_email:
                dismiss();
                break;
            case R.id.bt_register_fb:
                break;
            case R.id.bt_cancel:
                dismiss();
                break;

        }
    }

//    ===================================

    public interface LogoutListener {
        void logoutOk(boolean isOk);
    }

}
