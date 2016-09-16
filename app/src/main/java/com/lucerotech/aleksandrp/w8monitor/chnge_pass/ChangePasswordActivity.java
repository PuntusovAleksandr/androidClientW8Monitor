package com.lucerotech.aleksandrp.w8monitor.chnge_pass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {


    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_delete_password_change)
    ImageView iv_delete_password_change;
    @Bind(R.id.iv_delete_password_change_new)
    ImageView iv_delete_password_change_new;
    @Bind(R.id.iv_delete_password_change_new_2)
    ImageView iv_delete_password_change_new_2;
    @Bind(R.id.iv_change_password)
    ImageView iv_change_password;

    @Bind(R.id.et_password_old)
    EditText et_password_old;
    @Bind(R.id.et_password_new)
    EditText et_password_new;
    @Bind(R.id.et_password_new_confirm)
    EditText et_password_new_confirm;

    private String mailUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        mailUser = getIntent().getStringExtra(STATICS_PARAMS.MAIL);


    }

    @OnClick(R.id.iv_toolbar_back_press)
    void presOnBack() {
        onBackPressed();
        finish();
    }
}
