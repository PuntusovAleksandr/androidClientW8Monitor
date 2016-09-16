package com.lucerotech.aleksandrp.w8monitor.chnge_pass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;

import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity {


//    @Bind(R.id.ib_login)
//    ImageView ib_login;

    private String mailUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        mailUser = getIntent().getStringExtra(STATICS_PARAMS.MAIL);


    }
}
