package com.w8.w8monitor.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.interfaces.presentts.RegisterPresenter;
import com.w8.w8monitor.android.activity.interfaces.views.RegisterView;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.api.service.ApiService;
import com.w8.w8monitor.android.d_base.model.UserLibr;
import com.w8.w8monitor.android.facebook.RegisterFacebook;
import com.w8.w8monitor.android.presents.register.presenter.RegisterPresenterImpl;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.SetThemeDark;
import com.w8.w8monitor.android.utils.SettingsApp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.w8.w8monitor.android.api.constant.ApiConstants.REGISTER;
import static com.w8.w8monitor.android.api.event.UpdateUiEvent.LOGIN_SOCIAL;
import static com.w8.w8monitor.android.utils.FontsTextView.getFontRobotoLight;
import static com.w8.w8monitor.android.utils.InternetUtils.checkInternetConnection;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_MAIL;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASS;


public class RegisterActivity extends AppCompatActivity implements RegisterView {
//        RealmObj.RealmListener {

    private RegisterPresenter presenter;

    @Bind(R.id.et_email_register)
    EditText et_email_register;
    @Bind(R.id.et_password_register)
    EditText et_password_register;

    @Bind(R.id.iv_delete_email)
    ImageView iv_delete_email;
    @Bind(R.id.iv_delete_password_register)
    ImageView iv_delete_password_register;
    @Bind(R.id.rl_progress_register)
    RelativeLayout rl_progress_register;
    @Bind(R.id.iv_register_ok)
    RelativeLayout iv_register_ok;

    public static final int REG_REG = 2;

    private RegisterFacebook mRegisterFacebook;

    private Intent serviceIntent;

    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        presenter = new RegisterPresenterImpl(RegisterActivity.this, this);

        et_email_register.setTypeface(getFontRobotoLight());
        et_password_register.setTypeface(getFontRobotoLight());

        setTouchLogin();
        setTouchPassword();

        serviceIntent = new Intent(this, ApiService.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        et_email_register.requestFocus();
        et_email_register.setFocusable(true);
        imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

        presenter.registerEvenBus();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_email_register.getWindowToken(), 0);

        presenter.unregisterEvenBus();

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == STATICS_PARAMS.FB_CODE) {
                rl_progress_register.setVisibility(View.VISIBLE);
                presenter.onActivityResultFB(requestCode, resultCode, data, mRegisterFacebook);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_email_register.getWindowToken(), 0);
        presenter.getBackLoginActivity();
    }

//    ==========================================================
//       on Clicks
//    ==========================================================

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBack() {
        onBackPressed();
    }

    @OnClick(R.id.iv_register_ok)
    public void clickRegisterOk() {
        String passwordText = et_password_register.getText().toString();
        String emailText = et_email_register.getText().toString();
        presenter.checkPassword(passwordText, emailText, passwordText, this);
    }

    @OnClick(R.id.iv_delete_email)
    public void clickDeleteEmail() {
        et_email_register.setText("");
    }

    @OnClick(R.id.iv_delete_password_register)
    public void clickDeletePass() {
        et_password_register.setText("");
    }

//    ==========================================================
//   END  on Clicks
//    ==========================================================

    private void setTouchPassword() {
        et_password_register.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeletePassword();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });

        et_password_register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                checkDataRegister();
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });

    }


    private void setTouchLogin() {
        et_email_register.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeleteLogin();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });

        et_email_register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                checkDataRegister();
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });
    }


    private void checkDataRegister() {
        String emailText = et_email_register.getText().toString();
        String passwordText = et_password_register.getText().toString();
        presenter.checkShowButton(passwordText, emailText, passwordText, this);
    }


    //    =================================================
//            answer from RegisterView
//    =================================================
    @Override
    public void showDeleteImages(boolean email, boolean pass, boolean repeatPass) {
        if (email) {
            iv_delete_email.setVisibility(View.VISIBLE);
            iv_delete_password_register.setVisibility(View.INVISIBLE);
        } else if (pass) {
            iv_delete_email.setVisibility(View.INVISIBLE);
            iv_delete_password_register.setVisibility(View.VISIBLE);
        } else if (repeatPass) {
            iv_delete_email.setVisibility(View.INVISIBLE);
            iv_delete_password_register.setVisibility(View.INVISIBLE);
        } else {
            iv_delete_email.setVisibility(View.INVISIBLE);
            iv_delete_password_register.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void isValidData(boolean isValid) {
        if (isValid) {
            if (checkInternetConnection()) {
                { // send data to server
                    if (checkInternetConnection()) {
                        String emailText = et_email_register.getText().toString();
                        String passwordText = et_password_register.getText().toString();

                        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, REGISTER);
                        serviceIntent.putExtra(SERVICE_MAIL, emailText);
                        serviceIntent.putExtra(SERVICE_PASS, passwordText);
                        startService(serviceIntent);
                    }
                }
            } else {
                Snackbar.make(
                        et_email_register, R.string.please_check_internet_connection,
                        Snackbar.LENGTH_SHORT).show();
            }

        } else {
            Snackbar.make(
                    et_email_register, R.string.wrong_email_or_password, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isShowButton(boolean mIsShow) {
        int resource = 0;
        if (SettingsApp.getInstance().isThemeDark()) {
            resource = R.drawable.b_confirm_active_dark;
            if (!mIsShow) {
                resource = R.drawable.b_confirm_nonactive_dark;
            }
        } else {
            resource = R.drawable.b_confirm_active_light;
            if (!mIsShow) {
                resource = R.drawable.b_confirm_nonactive_light;
            }
        }
//        iv_register_ok.setImageResource(resource);
    }


    //    =================================================
//        END    answer from RegisterView
    //    =================================================
//    answer from RealmListener
//    =================================================
    @Override
    public void isUserSaveLogin(boolean isSave, UserLibr mRegKey) {
        if (isSave) {

            SettingsApp.getInstance().setUserName(mRegKey.getEmail());
            SettingsApp.getInstance().setUserPassword(mRegKey.getPassword());
            SettingsApp.getInstance().setProfileBLE(mRegKey.getProfileBLE());
            SettingsApp.getInstance().setMetric(!mRegKey.getIs_imperial());
            SettingsApp.getInstance().setSettingsStatus(false);
            if (mRegKey.isFullProfile()) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else
                presenter.goToProfile();

        } else {
            Snackbar.make(
                    et_email_register, R.string.not_save, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateLogin(UpdateUiEvent mEvent) {
        if (mEvent.isSucess()) {
            if (mEvent.getId() == REGISTER ||
                    mEvent.getId() == LOGIN_SOCIAL) {

                if (mEvent.getId() == UpdateUiEvent.LOGIN_SOCIAL) {
                    et_email_register.setText(SettingsApp.getInstance().getUserName());
                    et_password_register.setText(SettingsApp.getInstance().getUserPassword());
                }

                presenter.checkUserInDb(
                        et_email_register.getText().toString(),
                        et_password_register.getText().toString(),
                        this,
                        mEvent);
            }
        } else {
            Toast.makeText(this, ((String) mEvent.getData()), Toast.LENGTH_SHORT).show();
        }
    }
    //    =================================================
//    END from RealmListener
//    =================================================
}
