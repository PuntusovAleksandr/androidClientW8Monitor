package com.lucertech.w8monitor.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.lucertech.w8monitor.android.R;
import com.lucertech.w8monitor.android.api.event.UpdateUiEvent;
import com.lucertech.w8monitor.android.api.service.ApiService;
import com.lucertech.w8monitor.android.ble.BluetoothHandler;
import com.lucertech.w8monitor.android.d_base.model.UserLibr;
import com.lucertech.w8monitor.android.facebook.RegisterFacebook;
import com.lucertech.w8monitor.android.activity.interfaces.presentts.LoginPresenter;
import com.lucertech.w8monitor.android.activity.interfaces.views.LoginView;
import com.lucertech.w8monitor.android.presents.login.presenter.LoginPresenterImpl;
import com.lucertech.w8monitor.android.utils.STATICS_PARAMS;
import com.lucertech.w8monitor.android.utils.SetThemeDark;
import com.lucertech.w8monitor.android.utils.SettingsApp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.lucertech.w8monitor.android.api.constant.ApiConstants.LOGIN;
import static com.lucertech.w8monitor.android.api.constant.ApiConstants.RESET_PASSWORD;
import static com.lucertech.w8monitor.android.utils.FontsTextView.getFontRobotoLight;
import static com.lucertech.w8monitor.android.utils.InternetUtils.checkInternetConnection;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.SERVICE_MAIL;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASS;

public class LoginActivity extends AppCompatActivity implements LoginView,
        BluetoothHandler.onResultScanDevice {

    private LoginPresenter presenter;

    @Bind(R.id.tv_wrong_email)
    TextView tv_wrong_email;
    @Bind(R.id.tv_keep_me)
    TextView tv_keep_me;

    @Bind(R.id.ll_keep_me)
    RelativeLayout ll_kepp_me;
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
    @Bind(R.id.iv_forgot)
    ImageView iv_forgot;
    @Bind(R.id.iv_login_me)
    ImageView iv_keep_me;
    @Bind(R.id.ib_facebook)
    ImageView ib_facebook;
    @Bind(R.id.ib_register)
    ImageView ib_register;
    @Bind(R.id.ib_login)
    ImageView ib_login;

    private boolean autoLogin = true;
    private RegisterFacebook mRegisterFacebook;

    public static final int REG_LOGIN = 1;
    public static final int REQUEST_REGISTER = 11;


    private Intent serviceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        et_login.clearFocus();
        et_password.clearFocus();

        tv_keep_me.setText(Html.fromHtml(getString(R.string.keep_me_logged_in)));
        tv_keep_me.setTypeface(getFontRobotoLight());
        tv_wrong_email.setTypeface(getFontRobotoLight());
        et_login.setTypeface(getFontRobotoLight());
        et_password.setTypeface(getFontRobotoLight());

        presenter = new LoginPresenterImpl(LoginActivity.this, this);

        setIconAutoLogin();
        setTouchLogin();
        setTouchPassword();


        serviceIntent = new Intent(this, ApiService.class);

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
                rl_login_register.setVisibility(View.VISIBLE);
                presenter.onActivityResultFB(requestCode, resultCode, data, mRegisterFacebook);
            } else if (requestCode == REQUEST_REGISTER) {
                String name = data.getStringExtra("name");
                et_login.setText(name);
                et_login.setSelection(name.length());
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

    @OnClick(R.id.ll_keep_me)
    public void setAutoSave() {

        if (autoLogin) {
            autoLogin = false;
            showIKeep(autoLogin);
        } else {
            autoLogin = true;
            showIKeep(autoLogin);
        }
        SettingsApp.getInstance().setAutoLogin(autoLogin);
    }


    @OnClick(R.id.ib_facebook)
    public void registerFacebook() {
        mRegisterFacebook = new RegisterFacebook(LoginActivity.this, REG_LOGIN);
        mRegisterFacebook.register();
    }

    @OnClick(R.id.ib_login)
    public void login() {

        String testName = STATICS_PARAMS.TEST_USER;
        presenter.inputEmptyUser(testName, testName, this);
//        SettingsApp.getInstance().setUserName(testName);
//        SettingsApp.getInstance().setUserPassword(testName);
//        SettingsApp.getInstance().setProfileBLE(1);
//        }
    }

    @OnClick(R.id.ib_register)
    public void registration() {
        presenter.goToRegistering(this);
    }

//    ==========================================================
//   END  on Clicks
//    ==========================================================

    private void setIconAutoLogin() {
        autoLogin = SettingsApp.getInstance().getAutoLogin();
        showIKeep(autoLogin);
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
                Snackbar.make(et_login,  R.string.check_email, Snackbar.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, ((String) mEvent.getData()), Toast.LENGTH_SHORT).show();
        }
    }
//    =================================================
//    END        answer from LoginView
//    =================================================


    private void showIKeep(boolean mAutoLogin) {
        if (mAutoLogin) {
            tv_keep_me.setAlpha(1f);
        } else {
            tv_keep_me.setAlpha(0.3f);
        }
    }


    private void showIconOk(int resource) {
//        Picasso.with(LoginActivity.this)
//                .load(resource)
//                .into(iv_keep_me);
        iv_keep_me.setImageResource(resource);
    }

    //    =================================================
//    from onResultScanDevice
//    =================================================
    @Override
    public void scanOk(boolean mEnabled) {

    }


    //    =================================================
//    =================================================

}