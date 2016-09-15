package com.lucerotech.aleksandrp.w8monitor.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;
import com.lucerotech.aleksandrp.w8monitor.login.presenter.LoginPresenterImpl;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView,
        RegisterFacebook.ListenerFacebookLogin {

    private LoginPresenter presenter;

    @Bind(R.id.tv_wrong_email)
    TextView tv_wrong_email;

    @Bind(R.id.et_login)
    EditText et_login;
    @Bind(R.id.et_password)
    EditText et_password;

    @Bind(R.id.iv_delete_login)
    ImageView iv_delete_login;
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

    private SharedPreferences mSharedPreferences;
    private boolean autoLogin;
    private RegisterFacebook mRegisterFacebook;

    public static final int REG_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        presenter = new LoginPresenterImpl(LoginActivity.this, this);

        mSharedPreferences = getSharedPreferences(SettingsApp.FILE_NAME, Context.MODE_PRIVATE);

        setTouchLogin();
        setTouchPassword();

    }


    @Override
    protected void onStart() {
        super.onStart();
        et_login.setFocusable(true);
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == STATICS_PARAMS.FB_CODE) {
                presenter.onActivityResultFB(requestCode, resultCode, data, mRegisterFacebook);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


//    ==========================================================
//    on Clicks
//    ==========================================================

    @OnClick(R.id.iv_delete_login)
    public void deleteLoginText() {
        et_login.setText("");
    }

    @OnClick(R.id.iv_delete_password)
    public void deletePasswordText() {
        et_password.setText("");
    }

    @OnClick(R.id.iv_forgot)
    public void showForgot() {
// TODO: 15.09.2016 make toast
    }

    @OnClick(R.id.iv_keep_me)
    public void autoLogin() {
        if (tv_wrong_email.getVisibility() == View.INVISIBLE) {
            presenter.checkUserInDb(et_login.getText().toString(),
                    et_password.getText().toString());
        }
//        if (autoLogin) {
//            showIconOk(R.drawable.b_confirm_nonactive_dark);
//            autoLogin = false;
//        } else {
//            showIconOk(R.drawable.b_confirm_active_dark);
//            autoLogin = true;
//        }
//        SettingsApp.setAutoLogin(autoLogin, mSharedPreferences);
    }

    @OnClick(R.id.ib_facebook)
    public void registerFacebook() {
        mRegisterFacebook = new RegisterFacebook(LoginActivity.this, REG_LOGIN, this);
        mRegisterFacebook.register();
    }

    @OnClick(R.id.ib_login)
    public void login() {
//        if (tv_wrong_email.getVisibility() == View.INVISIBLE) {
        presenter.goToProfile();
//        }
    }

    @OnClick(R.id.ib_register)
    public void registration() {
        presenter.goToRegistering();
    }

//    ==========================================================
//   END  on Clicks
//    ==========================================================

    private void setIconAutoLogin() {
        autoLogin = SettingsApp.getAutoLogin(mSharedPreferences);
        if (autoLogin) {
            showIconOk(R.drawable.b_confirm_active_dark);
        } else {
            showIconOk(R.drawable.b_confirm_nonactive_dark);
        }
    }

    private void setTouchPassword() {
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeletePassword();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                String passwordText = et_password.getText().toString();
                String emailText = et_login.getText().toString();
                presenter.checkPassword(passwordText, emailText);
                presenter.showDeletePassword();
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });
    }

    private void setTouchLogin() {
        et_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeleteLogin();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });
        et_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                String passwordText = et_password.getText().toString();
                String emailText = et_login.getText().toString();
                presenter.checkPassword(passwordText, emailText);
                presenter.showDeleteLogin();
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
        iv_keep_me.setClickable(false);
    }

    @Override
    public void hideWrong() {
        tv_wrong_email.setVisibility(View.INVISIBLE);
        showIconOk(R.drawable.b_confirm_active_dark);
        iv_keep_me.setClickable(true);
    }

    @Override
    public void showDeleteImages(boolean deleteLogin, boolean deletePassword) {
        if (deleteLogin) {
            iv_delete_login.setVisibility(View.VISIBLE);
            iv_delete_password.setVisibility(View.INVISIBLE);
        } else if (deletePassword) {
            iv_delete_login.setVisibility(View.INVISIBLE);
            iv_delete_password.setVisibility(View.VISIBLE);
        } else {
            iv_delete_login.setVisibility(View.INVISIBLE);
            iv_delete_password.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void userExist(boolean mUserExist) {
        if (mUserExist) {
            presenter.goToProfile();
        } else {
            Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
        }
    }
//    =================================================
//    END        answer from LoginView
//    =================================================


    private void showIconOk(int resource) {
        Picasso.with(LoginActivity.this)
                .load(resource)
                .into(iv_keep_me);
    }

//    =================================================
//    answer from RegFacebook
//    =================================================

    @Override
    public void onSaveUserLogin(boolean mIsSave) {
        if (mIsSave) {
           presenter.goToProfile();
        }
    }
}
