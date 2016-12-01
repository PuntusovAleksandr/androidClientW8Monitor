package com.w8.w8monitor.android.presents.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.w8.w8monitor.android.activity.ChangePasswordActivity;
import com.w8.w8monitor.android.activity.LoginActivity;
import com.w8.w8monitor.android.activity.MainActivity;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.RegisterActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.LoginPresenter;
import com.w8.w8monitor.android.activity.interfaces.views.LoginView;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.facebook.RegisterFacebook;
import com.w8.w8monitor.android.utils.STATICS_PARAMS;
import com.w8.w8monitor.android.utils.ValidationText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.w8.w8monitor.android.utils.InternetUtils.checkInternetConnection;

/**
 * Created by AleksandrP on 14.09.2016.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mLoginView;
    private Context mContext;


    public LoginPresenterImpl() {
    }

    public LoginPresenterImpl(Context mContext, LoginView mLoginView) {
        this.mContext = mContext;
        this.mLoginView = mLoginView;
    }

    @Override
    public void checkPassword(String mPasswordText, String mEmailText, boolean mLogin) {
        if (ValidationText.checkLenghtPassword(mPasswordText) &&
                ValidationText.checkValidEmail(mEmailText)) {
            mLoginView.hideWrong(mLogin);
        } else {
            if (mLogin)
                mLoginView.showWrong();
        }
    }

    @Override
    public void showDeletePassword() {
        mLoginView.showDeleteImages(false, true);
    }

    @Override
    public void showDeleteLogin() {
        mLoginView.showDeleteImages(true, false);
    }

    @Override
    public void hideAllDelete() {
        mLoginView.showDeleteImages(false, false);
    }

    @Override
    public void goToProfile() {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.putExtra(STATICS_PARAMS.INNER_MARKER_PROFILE, ProfileActivity.MARKER_LOGIN);
        mContext.startActivity(intent);
        mLoginView.finishActivity();
    }

    @Override
    public void goToRegistering(LoginActivity mLoginActivity) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
//        mLoginActivity.startActivityForResult(intent, LoginActivity.REQUEST_REGISTER);
        mLoginActivity.startActivity(intent);
        mLoginView.finishActivity();
    }

    @Override
    public void onActivityResultFB(
            int mRequestCode, int mResultCode, Intent mData, RegisterFacebook mRegisterFacebook) {
        mRegisterFacebook.onActivityResultFB(mRequestCode, mResultCode, mData, mContext);
    }

    @Override
    public void checkUserInDb(String mLogin, String mPass, LoginView mListenerLoginView,
                              UpdateUiEvent mEvent) {
        if (checkInternetConnection() && mEvent == null) {
            mListenerLoginView.loginServer(mLogin, mPass);
        } else {
            RealmObj.getInstance().getUserByMailAndPass(mLogin, mPass, mListenerLoginView, mEvent);
        }
    }

    @Override
    public void goToChangePassword(String mEmail, LoginView mListenerLoginView) {
        RealmObj.getInstance().checkEmail(mEmail, mListenerLoginView);
    }

    @Override
    public void goToChangePasswordActivity(String mEmail) {
        Intent intent = new Intent(mContext, ChangePasswordActivity.class);
        intent.putExtra(STATICS_PARAMS.MAIL, mEmail);
        mContext.startActivity(intent);
    }

    @Override
    public void inputEmptyUser(String mMail, String mPass, LoginView mLoginView) {
        RealmObj.getInstance().putUser(mMail, mPass, mLoginView);
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
        mLoginView.finishActivity();
    }

    @Subscribe
    @Override
    public void registerEvenBus() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    @Override
    public void unregisterEvenBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(UpdateUiEvent event) {
        mLoginView.updateLogin(event);
        if (event != null && event.getData() != null)
            System.out.println(event.getData().toString());
    }
}
