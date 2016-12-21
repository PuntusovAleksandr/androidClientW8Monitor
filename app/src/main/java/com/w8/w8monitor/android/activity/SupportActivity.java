package com.w8.w8monitor.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.api.service.ApiService;
import com.w8.w8monitor.android.utils.SetThemeDark;
import com.w8.w8monitor.android.utils.ValidationText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.w8.w8monitor.android.api.constant.ApiConstants.SUPPORT_API;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_MAIL;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_QUESTIONS;

public class SupportActivity extends AppCompatActivity {

    private Intent serviceIntent;
    private InputMethodManager imm;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;

    @Bind(R.id.ll_send_question)
    RelativeLayout iv_register_ok;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;

    @Bind(R.id.et_description_text)
    EditText et_description_text;
    @Bind(R.id.et_email_register)
    EditText et_email_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_support);
        ButterKnife.bind(this);
        serviceIntent = new Intent(this, ApiService.class);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
        et_email_register.setFocusableInTouchMode(true);
        et_email_register.requestFocus();
        et_email_register.setFocusable(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(UpdateUiEvent mEvent) {
        if (mEvent.isSucess()) {
            if (mEvent.getId() == UpdateUiEvent.RESPONSE_SUPPORT_API) {
                Toast.makeText(this, R.string.answer_support, Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        } else {
            Toast.makeText(this, ((String) mEvent.getData()), Toast.LENGTH_SHORT).show();
        }
        System.out.println(mEvent.getData().toString());

        rl_progress.setVisibility(View.GONE);
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        onBackPressed();
    }

    @OnClick(R.id.ll_send_question)
    public void iv_register_okClick() {

        String email = et_email_register.getText().toString();
        String text = et_description_text.getText().toString();
        int length = text.length();
        boolean emailBoolean = ValidationText.checkValidEmail(email);
        if (length > 3 && emailBoolean) {
            sendQuestion(email, text);
            rl_progress.setVisibility(View.VISIBLE);
        } else {
            if (!emailBoolean) {
                Toast.makeText(this, R.string.fill_email, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.fill_question, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendQuestion(String mEmail, String mText) {
        serviceIntent.putExtra(SERVICE_MAIL, mEmail);
        serviceIntent.putExtra(SERVICE_QUESTIONS, mText);
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, SUPPORT_API);
        startService(serviceIntent);
    }

}
