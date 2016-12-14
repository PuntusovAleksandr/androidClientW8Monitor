package com.w8.w8monitor.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.interfaces.presentts.LoginPresenter;
import com.w8.w8monitor.android.activity.interfaces.views.LoginView;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.api.service.ApiService;
import com.w8.w8monitor.android.ble.BluetoothHandler;
import com.w8.w8monitor.android.d_base.model.UserLibr;
import com.w8.w8monitor.android.facebook.RegisterFacebook;
import com.w8.w8monitor.android.presents.login.presenter.LoginPresenterImpl;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.SetThemeDark;
import com.w8.w8monitor.android.utils.SettingsApp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.w8.w8monitor.android.api.constant.ApiConstants.LOGIN;
import static com.w8.w8monitor.android.api.constant.ApiConstants.RESET_PASSWORD;
import static com.w8.w8monitor.android.utils.FontsTextView.getFontRobotoLight;
import static com.w8.w8monitor.android.utils.InternetUtils.checkInternetConnection;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.REQUEST_ACTION_LOCATION_SOURCE_SETTINGS;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_MAIL;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASS;

public class LoginActivity extends AppCompatActivity implements LoginView,
        BluetoothHandler.onResultScanDevice {

    private LoginPresenter presenter;

    @Bind(R.id.tv_wrong_email)
    TextView tv_wrong_email;
    @Bind(R.id.ib_login)
    TextView ib_login;
    @Bind(R.id.rl_login_register)
    RelativeLayout rl_login_register;

    @Bind(R.id.et_login)
    EditText et_login;
    @Bind(R.id.et_password)
    EditText et_password;

    @Bind(R.id.iv_delete_login)
    ImageView iv_delete_login;
    @Bind(R.id.iv_delete_password)
    ImageView iv_delete_password;
    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;

    @Bind(R.id.iv_login_me)
    RelativeLayout iv_keep_me;
    @Bind(R.id.iv_forgot)
    RelativeLayout iv_forgot;
    @Bind(R.id.activity_login_main)
    RelativeLayout activity_login;
    @Bind(R.id.rl_general)
    RelativeLayout rl_general;

    @Bind(R.id.ib_facebook)
    LinearLayout ib_facebook;
    @Bind(R.id.ib_register)
    LinearLayout ib_register;
    @Bind(R.id.ll_log_in)
    LinearLayout ll_log_in;

    private RegisterFacebook mRegisterFacebook;

    public static final int REG_LOGIN = 1;
    public static final int REQUEST_REGISTER = 11;

    private InputMethodManager imm;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_new_login);
        ButterKnife.bind(this);

        et_login.clearFocus();
        et_password.clearFocus();

        tv_wrong_email.setTypeface(getFontRobotoLight());
        et_login.setTypeface(getFontRobotoLight());
        et_password.setTypeface(getFontRobotoLight());

        presenter = new LoginPresenterImpl(LoginActivity.this, this);

        setTouchLogin();
        setTouchPassword();

        serviceIntent = new Intent(this, ApiService.class);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BluetoothHandler bluetoothHandler = new BluetoothHandler(this, this);
        bluetoothHandler.checkPermission(this);
        presenter.registerEvenBus();
    }

    @Override
    protected void onStop() {
        presenter.unregisterEvenBus();
        super.onStop();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == STATICS_PARAMS.FB_CODE) {
                showProgress();
                presenter.onActivityResultFB(requestCode, resultCode, data, mRegisterFacebook);
            } else if (requestCode == REQUEST_REGISTER) {
                String name = data.getStringExtra("name");
                et_login.setText(name);
                et_login.setSelection(name.length());
            }
        }
        if (requestCode == REQUEST_ACTION_LOCATION_SOURCE_SETTINGS) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, R.string.for_work_ble, Toast.LENGTH_SHORT).show();
            }
        }

        hideProgress();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (activity_login.getVisibility() == View.VISIBLE) {
            defaultVisibleLayout();
        } else {
            super.onBackPressed();
        }
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
        if (checkInternetConnection()) {
            String mMail = et_login.getText().toString();
            if (mMail.isEmpty()) {
                Toast.makeText(this, R.string.fill_email, Toast.LENGTH_SHORT).show();
            } else {
                serviceIntent.putExtra(SERVICE_MAIL, mMail);
                serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, RESET_PASSWORD);
                startService(serviceIntent);
            }
        } else {
            Toast.makeText(this, R.string.check_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.iv_login_me)
    public void autoLogin() {
        presenter.checkPassword(
                et_password.getText().toString(),
                et_login.getText().toString(),
                true);
    }


    @OnClick(R.id.ib_facebook)
    public void registerFacebook() {
        showProgress();
        mRegisterFacebook = new RegisterFacebook(LoginActivity.this, REG_LOGIN);
        mRegisterFacebook.register();
    }

    @OnClick(R.id.ib_login)
    public void login() {

        showProgress();
        String testName = STATICS_PARAMS.TEST_USER;
        presenter.inputEmptyUser(testName, testName, this);
    }

    @OnClick(R.id.ib_register)
    public void registration() {
        presenter.goToRegistering(this);
    }

    @OnClick(R.id.ll_log_in)
    public void loginBt() {
        showLoginLayout();
        tv_wrong_email.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void pressBack() {
        onBackPressed();
    }

//    ==========================================================
//   END  on Clicks
//    ==========================================================


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
                presenter.checkPassword(passwordText, emailText, false);
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
                presenter.checkPassword(passwordText, emailText, false);
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
        if (SettingsApp.getInstance().isThemeDark()) {
            showIconOk(R.drawable.b_confirm_nonactive_dark);
        } else {
            showIconOk(R.drawable.b_confirm_nonactive_light);
        }
        iv_keep_me.setClickable(false);
    }

    @Override
    public void hideWrong(boolean mLogin) {
        tv_wrong_email.setVisibility(View.INVISIBLE);
        if (SettingsApp.getInstance().isThemeDark()) {
            showIconOk(R.drawable.b_confirm_active_dark);
        } else {
            showIconOk(R.drawable.b_confirm_active_light);
        }
        iv_keep_me.setClickable(true);
        if (mLogin) {
            if (tv_wrong_email.getVisibility() == View.INVISIBLE) {
                presenter.checkUserInDb(
                        et_login.getText().toString(),
                        et_password.getText().toString(),
                        this, null);
            }
        }
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
    public void userExist(boolean mUserExist, UserLibr mUser) {
        if (mUserExist) {
            if (mUser.getEmail().equalsIgnoreCase(STATICS_PARAMS.TEST_USER)) {
                SettingsApp.getInstance().setUserName(mUser.getEmail());
                SettingsApp.getInstance().setUserPassword(mUser.getEmail());
            }

            SettingsApp.getInstance().setProfileBLE(mUser.getProfileBLE());
            SettingsApp.getInstance().setLanguages(mUser.getLanguage());
            SettingsApp.getInstance().setThemeDark(mUser.getTheme() == 1 ? true : false);
            SettingsApp.getInstance().setAutoLogin(mUser.getKeep_login());
            SettingsApp.getInstance().setMetric(!mUser.getIs_imperial());
            if (mUser != null && mUser.isFullProfile()) {
                SettingsApp.getInstance().setSettingsStatus(true);
                presenter.goToMainActivity();
            } else {
                SettingsApp.getInstance().setSettingsStatus(false);
                presenter.goToProfile();
            }
        } else {
            Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changePassUserExist(boolean mExist, String mEmail) {
        if (mExist) {
            presenter.goToChangePasswordActivity(mEmail);
        } else {
            Toast.makeText(this, R.string.email_not_registered, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void goToProfile() {
        presenter.goToProfile();
    }

    @Override
    public void loginServer(String mLogin, String mPass) {
        serviceIntent.putExtra(SERVICE_MAIL, mLogin);
        serviceIntent.putExtra(SERVICE_PASS, mPass);
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, LOGIN);
        startService(serviceIntent);
    }

    @Override
    public void updateLogin(UpdateUiEvent mEvent) {
        if (mEvent.isSucess()) {
            if (mEvent.getId() == UpdateUiEvent.LOGIN ||
                    mEvent.getId() == UpdateUiEvent.LOGIN_SOCIAL) {
                presenter.checkUserInDb(
                        SettingsApp.getInstance().getUserName(),
                        SettingsApp.getInstance().getUserPassword(),
                        this,
                        mEvent);
            } else if (mEvent.getId() == UpdateUiEvent.RESET_PASSWORD) {
                Snackbar.make(et_login, R.string.check_email, Snackbar.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, ((String) mEvent.getData()), Toast.LENGTH_SHORT).show();
        }
    }
//    =================================================
//    END        answer from LoginView
//    =================================================


    private void showIconOk(int resource) {
//        iv_keep_me.setImageResource(resource);
    }

    //    =================================================
//    from onResultScanDevice
//    =================================================
    @Override
    public void scanOk(boolean mEnabled) {

    }


    //    =================================================
//    =================================================


    private void showProgress() {
        rl_login_register.setVisibility(View.VISIBLE);
    }

    private void showProgressWithStop() {
        rl_login_register.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showProgress();
            }
        }, 500);
    }

    private void hideProgress() {
        rl_login_register.setVisibility(View.GONE);
    }


//    =================================================

    private void defaultVisibleLayout() {
        imm.hideSoftInputFromWindow(et_login.getWindowToken(), 0);

        activity_login.setVisibility(View.GONE);
        rl_general.setVisibility(View.VISIBLE);
    }

    private void showLoginLayout() {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
        et_login.setFocusableInTouchMode(true);
        et_login.requestFocus();
        et_login.setFocusable(true);
        activity_login.setVisibility(View.VISIBLE);
        rl_general.setVisibility(View.GONE);
        iv_keep_me.setClickable(true);
    }

}