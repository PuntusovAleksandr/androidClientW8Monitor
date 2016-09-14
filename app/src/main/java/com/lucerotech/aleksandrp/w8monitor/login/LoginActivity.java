package com.lucerotech.aleksandrp.w8monitor.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.login.presenter.LoginPresenterImpl;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity  implements LoginView{

    private LoginPresenter presenter;

    @Bind(R.id.tv_wrong_email)
    TextView tv_wrong_email;

    @Bind(R.id.et_login)
    EditText et_login;
    @Bind(R.id.et_password)
    EditText et_password;

    @Bind(R.id.iv_delete_password)
    ImageView iv_delete_password;
    @Bind(R.id.iv_forgot)
    ImageView iv_forgot;
    @Bind(R.id.iv_keep_me)
    ImageView iv_keep_me;
    @Bind(R.id.ib_facebook)
    ImageView ib_facebook;
    @Bind(R.id.ib_register)
    ImageView ib_register;
    @Bind(R.id.ib_login)
    ImageView ib_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenterImpl(LoginActivity.this, this);

        setTouchLogin();
        setTouchPasword();

    }

    @Override
    protected void onStart() {
        super.onStart();
        et_login.setFocusable(true);
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
    }

    @OnClick(R.id.iv_forgot)
    public void showForgot() {

    }

    @OnClick(R.id.iv_delete_password)
    public void deletePassword() {

    }



    private void setTouchPasword() {
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                String passwordText = et_password.getText().toString();
                String emailText = et_login.getText().toString();
                presenter.checkPassword(passwordText, emailText);
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });
    }

    private void setTouchLogin() {
        et_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                String passwordText = et_password.getText().toString();
                String emailText = et_login.getText().toString();
                presenter.checkPassword(passwordText, emailText);
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });
    }



//    =================================================
//            answer from LoginView
//    =================================================
    @Override
    public void showWrong() {
        tv_wrong_email.setVisibility(View.VISIBLE);
        showIconOk(R.drawable.b_confirm_nonactive_dark);
    }

    @Override
    public void hideWrong() {
        tv_wrong_email.setVisibility(View.INVISIBLE);
        showIconOk(R.drawable.b_confirm_active_dark);
    }
//    =================================================
//    END        answer from LoginView
//    =================================================


    private void showIconOk(int resource) {
        Picasso.with(LoginActivity.this)
                .load(resource)
                .into(iv_keep_me);
    }

}
