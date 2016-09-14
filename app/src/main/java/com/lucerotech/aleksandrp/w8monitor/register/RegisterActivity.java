package com.lucerotech.aleksandrp.w8monitor.register;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.register.presenter.RegisterPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenter mPresenter;

    @Bind(R.id.et_email_register)
    EditText et_email_register;
    @Bind(R.id.et_password_register)
    EditText et_password_register;
    @Bind(R.id.et_repeat_password)
    EditText et_repeat_password;

    @Bind(R.id.iv_delete_email)
    ImageView iv_delete_email;
    @Bind(R.id.iv_delete_password_register)
    ImageView iv_delete_password_register;
    @Bind(R.id.iv_delete_repeat_password_register)
    ImageView iv_delete_repeat_password_register;
    @Bind(R.id.iv_register_ok)
    ImageView iv_register_ok;
    @Bind(R.id.iv_logo_register)
    ImageView iv_logo_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mPresenter = new RegisterPresenterImpl(RegisterActivity.this, this);

        setTouchLogin();
        setTouchPassword();
        setTouchRepeatPassword();


    }

    @Override
    protected void onStart() {
        super.onStart();
        et_email_register.setFocusable(true);
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
    }

//    ==========================================================
//       on Clicks
//    ==========================================================

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBack() {
        onBackPressed();
        finish();
    }

    @OnClick(R.id.ib_facebook_register)
    public void clickRegisterFacebook() {
        Toast.makeText(this, "Скоро будет регистрация файсбука", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv_register_ok)
    public void clickRegisterOk() {
        String passwordText = et_password_register.getText().toString();
        String repeatPasswordText = et_repeat_password.getText().toString();
        String emailText = et_email_register.getText().toString();
        mPresenter.checkPassword(passwordText, emailText, repeatPasswordText);
    }

//    ==========================================================
//   END  on Clicks
//    ==========================================================


    private void setTouchRepeatPassword() {
        et_repeat_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    mPresenter.showDeleteRepeatPassword();
                } else {
                    mPresenter.hideAllDelete();
                }
            }
        });
    }

    private void setTouchPassword() {
        et_password_register.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    mPresenter.showDeletePassword();
                } else {
                    mPresenter.hideAllDelete();
                }
            }
        });
    }

    private void setTouchLogin() {
        et_email_register.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    mPresenter.showDeleteLogin();
                } else {
                    mPresenter.hideAllDelete();
                }
            }
        });
    }

    //    =================================================
//            answer from RegisterView
//    =================================================
    @Override
    public void showDeleteImages(boolean email, boolean pass, boolean repeatPass) {
        if (email) {
            iv_delete_email.setVisibility(View.VISIBLE);
            iv_delete_password_register.setVisibility(View.INVISIBLE);
            iv_delete_repeat_password_register.setVisibility(View.INVISIBLE);
        } else if (pass) {
            iv_delete_email.setVisibility(View.INVISIBLE);
            iv_delete_password_register.setVisibility(View.VISIBLE);
            iv_delete_repeat_password_register.setVisibility(View.INVISIBLE);
        } else if (repeatPass) {
            iv_delete_email.setVisibility(View.INVISIBLE);
            iv_delete_password_register.setVisibility(View.INVISIBLE);
            iv_delete_repeat_password_register.setVisibility(View.VISIBLE);
        } else {
            iv_delete_email.setVisibility(View.INVISIBLE);
            iv_delete_password_register.setVisibility(View.INVISIBLE);
            iv_delete_repeat_password_register.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void isValidData(boolean isValid) {
        if (isValid) {
// TODO: 14.09.2016 надо сделать запись в базе и покидаем активность
            mPresenter.goToProfile();
            finish();
        } else {
            Snackbar.make(
                    et_email_register, R.string.wrong_email_or_password, Snackbar.LENGTH_SHORT).show();
        }
    }


    //    =================================================
//        END    answer from RegisterView
//    =================================================

}
