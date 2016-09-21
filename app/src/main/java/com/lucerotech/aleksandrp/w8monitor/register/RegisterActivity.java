package com.lucerotech.aleksandrp.w8monitor.register;

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

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;
import com.lucerotech.aleksandrp.w8monitor.register.presenter.RegisterPresenterImpl;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView,
        RegisterFacebook.ListenerFacebookRegistr{
//        RealmObj.RealmListener {

    private RegisterPresenter presenter;

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

    public static final int REG_REG = 2;

    private RegisterFacebook mRegisterFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        presenter = new RegisterPresenterImpl(RegisterActivity.this, this);

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

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == STATICS_PARAMS.FB_CODE) {
                presenter.onActivityResultFB(requestCode, resultCode, data, mRegisterFacebook);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        presenter.getBackLoginActivity();
    }

//    ==========================================================
//       on Clicks
//    ==========================================================

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBack() {
        presenter.getBackLoginActivity();
    }

    @OnClick(R.id.ib_facebook_register)
    public void clickRegisterFacebook() {
        mRegisterFacebook = new RegisterFacebook(RegisterActivity.this, REG_REG, this);
        mRegisterFacebook.register();
    }

    @OnClick(R.id.iv_register_ok)
    public void clickRegisterOk() {
        String passwordText = et_password_register.getText().toString();
        String repeatPasswordText = et_repeat_password.getText().toString();
        String emailText = et_email_register.getText().toString();
        presenter.checkPassword(passwordText, emailText, repeatPasswordText, this);
    }

    @OnClick(R.id.iv_delete_email)
    public void clickDeleteEmail() {
        et_email_register.setText("");
    }

    @OnClick(R.id.iv_delete_password_register)
    public void clickDeletePass() {
        et_password_register.setText("");
    }

    @OnClick(R.id.iv_delete_repeat_password_register)
    public void clickDeleteReraetePass() {
        et_repeat_password.setText("");
    }

//    ==========================================================
//   END  on Clicks
//    ==========================================================


    private void setTouchRepeatPassword() {
        et_repeat_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View mView, boolean mB) {
                if (mB) {
                    presenter.showDeleteRepeatPassword();
                } else {
                    presenter.hideAllDelete();
                }
            }
        });

        et_repeat_password.addTextChangedListener(new TextWatcher() {
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
        String repearPasswordText = et_repeat_password.getText().toString();
        presenter.checkShowButton(passwordText, emailText, repearPasswordText, this);
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
            RealmObj.getInstance().putUser(
                    et_email_register.getText().toString(),
                    et_password_register.getText().toString(),
                    this);

        } else {
            Snackbar.make(
                    et_email_register, R.string.wrong_email_or_password, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isShowButton(boolean mIsShow) {
        int resource = R.drawable.b_confirm_active_dark;
        if (!mIsShow) {
            resource = R.drawable.b_confirm_nonactive_dark;
        }
        Picasso.with(this)
                .load(resource)
                .into(iv_register_ok);
    }


    //    =================================================
//        END    answer from RegisterView
    //    =================================================
//    answer from RegFacebook
//    =================================================

    @Override
    public void onSaveUserLogin(boolean mIsSave) {
        presenter.goToProfile();
    }

    //    =================================================
//        END    answer from RegFacebook
    //    =================================================
//    answer from RealmListener
//    =================================================
    @Override
    public void isUserSaveLogin(boolean isSave, int mRegKey) {
        if (isSave) {
            presenter.goToProfile();
        } else {
            Snackbar.make(
                    et_email_register, R.string.not_save, Snackbar.LENGTH_SHORT).show();
        }
    }
    //    =================================================
//    END from RealmListener
//    =================================================
}
