package com.lucerotech.aleksandrp.w8monitor.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucerotech.aleksandrp.w8monitor.R;

import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {


//    @Bind(R.id.ib_login)
//    ImageView ib_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);


    }
}
