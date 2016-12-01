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
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.interfaces.presentts.ChangePasswordPresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ChangePasswordView;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.api.service.ApiService;
import com.w8.w8monitor.android.presents.change_pass.presenter.ChangePasswordPresenterImpl;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.SetThemeDark;
import com.w8.w8monitor.android.utils.SettingsApp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.w8.w8monitor.android.api.constant.ApiConstants.CHANGE_PASS;
import static com.w8.w8monitor.android.utils.FontsTextView.getFontRobotoLight;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASSWORD_NEW;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASSWORD_NEW_CONFIRM;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASSWORD_OLS;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordView {


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

    @Bind(R.id.tv_title)
    TextView tv_title;

    private String mailUser = "";

    private ChangePasswordPresenter presenter;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        mailUser = getIntent().getStringExtra(STATICS_PARAMS.MAIL);
        presenter = new ChangePasswordPresenterImpl(this, this);

        setTouchLogin();
        setTouchPassword();
        setTouchRepeatPassword();


        tv_title.setTypeface(getFontRobotoLight());

        serviceIntent = new Intent(this, ApiService.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_password_old.setFocusable(true);
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeKeyboard();

        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(UpdateUiEvent mEvent) {
        if (mEvent.isSucess()) {
            if (mEvent.getId() == UpdateUiEvent.CHANGE_PASS ||
                    mEvent.getId() == UpdateUiEvent.LOGIN_SOCIAL) {
                String passwordTextOld = et_password_old.getText().toString();
                String passwordText = et_password_new.getText().toString();
                String repearPasswordText = et_password_new_confirm.getText().toString();
                presenter.changePasswordInDb(
                        mailUser,
                        passwordTextOld,
                        passwordText,
                        repearPasswordText,
                        mEvent,
                        this);
            }
        } else {
            Toast.makeText(this, ((String) mEvent.getData()), Toast.LENGTH_SHORT).show();
        }
        System.out.println(mEvent.getData().toString());
    }

    private void closeKeyboard() {
        final InputMethodManager im =
                (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });
    }

    //    ==========================================================
//       on Clicks
//    ==========================================================

    @OnClick(R.id.iv_change_password)
    void changePassword() {
        closeKeyboard();
        String passwordTextOld = et_password_old.getText().toString();
        String passwordText = et_password_new.getText().toString();
        String repearPasswordText = et_password_new_confirm.getText().toString();
        presenter.changePassword(mailUser, passwordTextOld, passwordText, repearPasswordText, this);
    }

    @OnClick(R.id.iv_delete_password_change)
    void deletePassword() {
        et_password_old.setText("");
    }

    @OnClick(R.id.iv_delete_password_change_new)
    void deleteNewPassword() {
        et_password_new.setText("");
    }

    @OnClick(R.id.iv_delete_password_change_new_2)
    void deleteNewPasswordRepeat() {
        et_password_new_confirm.setText("");
    }

    @OnClick(R.id.iv_toolbar_back_press)
    void presOnBack() {
        closeKeyboard();
        onBackPressed();
        finish();
    }


//    ==========================================================
//      END   on Clicks
//    ==========================================================

    private void setTouchRepeatPassword() {
        et_password_old.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeleteRepeatPassword();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });

        et_password_old.addTextChangedListener(new TextWatcher() {
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

    private void setTouchPassword() {
        et_password_new.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeletePassword();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });

        et_password_new.addTextChangedListener(new TextWatcher() {
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
        et_password_new_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeleteLogin();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });

        et_password_new_confirm.addTextChangedListener(new TextWatcher() {
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
        String passwordTextOld = et_password_old.getText().toString();
        String passwordText = et_password_new.getText().toString();
        String repearPasswordText = et_password_new_confirm.getText().toString();
        presenter.checkShowButton(passwordText, passwordTextOld, repearPasswordText, this);
    }


//    ==========================================================
//      START from ChangePasswordView
//    ==========================================================


    @Override
    public void showDeleteIcons(boolean login, boolean pass, boolean newPass) {
        if (newPass) {
            iv_delete_password_change.setVisibility(View.VISIBLE);
            iv_delete_password_change_new.setVisibility(View.INVISIBLE);
            iv_delete_password_change_new_2.setVisibility(View.INVISIBLE);
        } else if (pass) {
            iv_delete_password_change.setVisibility(View.INVISIBLE);
            iv_delete_password_change_new.setVisibility(View.VISIBLE);
            iv_delete_password_change_new_2.setVisibility(View.INVISIBLE);
        } else if (login) {
            iv_delete_password_change.setVisibility(View.INVISIBLE);
            iv_delete_password_change_new.setVisibility(View.INVISIBLE);
            iv_delete_password_change_new_2.setVisibility(View.VISIBLE);
        } else {
            iv_delete_password_change.setVisibility(View.INVISIBLE);
            iv_delete_password_change_new.setVisibility(View.INVISIBLE);
            iv_delete_password_change_new_2.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void isShowButton(boolean isValid) {
        int resource = 0;
        if (SettingsApp.getInstance().isThemeDark()) {
            resource = R.drawable.b_confirm_active_dark;
            if (!isValid) {
                resource = R.drawable.b_confirm_nonactive_dark;
            }
        } else {
            resource = R.drawable.b_confirm_active_light;
            if (!isValid) {
                resource = R.drawable.b_confirm_nonactive_light;
            }
        }
        iv_change_password.setImageResource(resource);
    }

    @Override
    public void showMessage() {
        Snackbar.make(
                et_password_old, R.string.enter_correct_password, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageNotFoundUser() {
        Snackbar.make(
                et_password_old, R.string.user_not_found, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageOkChangePass() {
        et_password_old.setText("");
        et_password_new.setText("");
        et_password_new_confirm.setText("");
        Toast.makeText(this, R.string.ppass_change, Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }

    @Override
    public void showMessageNoInternet() {
        Snackbar.make(
                et_password_old, R.string.please_check_internet_connection, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void makeRequest() {
        String passwordTextOld = et_password_old.getText().toString();
        String passwordText = et_password_new.getText().toString();
        String repearPasswordText = et_password_new_confirm.getText().toString();
        serviceIntent.putExtra(SERVICE_PASSWORD_OLS, passwordTextOld);
        serviceIntent.putExtra(SERVICE_PASSWORD_NEW, passwordText);
        serviceIntent.putExtra(SERVICE_PASSWORD_NEW_CONFIRM, repearPasswordText);
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, CHANGE_PASS);
        startService(serviceIntent);
    }

//    ==========================================================
//      END from ChangePasswordView
//    ==========================================================
}
