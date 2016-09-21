package com.lucerotech.aleksandrp.w8monitor.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.login.LoginActivity;
import com.lucerotech.aleksandrp.w8monitor.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class RegisterFacebook implements RealmObj.RealmListener {


    private CallbackManager mCallbackManager;
    private Context mContext;
    private int regKey;
    private Activity mActivity;
    private UserFacebook mUser;

    private ListenerFacebookLogin mListenerFacebook;
    private ListenerFacebookRegistr mListenerFacebookRegistr;

    public RegisterFacebook(Context mContext, int mRegKey, ListenerFacebookLogin mListenerFacebook) {
        this.mContext = mContext;
        this.regKey = mRegKey;
        this.mListenerFacebook = mListenerFacebook;
        mCallbackManager = CallbackManager.Factory.create();
        if (mRegKey == LoginActivity.REG_LOGIN) {
            mActivity =(LoginActivity) mContext;
        } else {
            mActivity =(RegisterActivity) mContext;
        }
    }
    public RegisterFacebook(Context mContext, int mRegKey, ListenerFacebookRegistr mListenerFacebook) {
        this.mContext = mContext;
        this.regKey = mRegKey;
        this.mListenerFacebookRegistr = mListenerFacebook;
        mCallbackManager = CallbackManager.Factory.create();
        if (mRegKey == LoginActivity.REG_LOGIN) {
            mActivity =(LoginActivity) mContext;
        } else {
            mActivity =(RegisterActivity) mContext;
        }
    }

    public void register() {
        LoginManager mInstance = LoginManager.getInstance();
        mInstance
                .logInWithReadPermissions(mActivity,
                        Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
        mInstance
                .registerCallback(mCallbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                AccessToken mAccessToken = loginResult.getAccessToken();
                                GraphRequest request = GraphRequest.newMeRequest(
                                        mAccessToken,
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(
                                                    JSONObject object,
                                                    GraphResponse response) {
                                                // Application code
                                                try {
                                                    String mId = object.getString("id");
                                                    mUser = new UserFacebook(
                                                            mId,
                                                            object.getString("name"),
                                                            object.getString("email"),
                                                            object.getString("birthday"),
                                                            "https://graph.facebook.com/" +
                                                                    mId + "/picture?type=large"
                                                    );
                                                } catch (JSONException mE) {
                                                    mE.printStackTrace();
                                                }
                                                if (mUser != null) {
                                                    saveInDb(mUser);
                                                } else errorUser();
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "birthday,email,id,name");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                errorUser();
                            }
                        });
    }

    private void saveInDb(UserFacebook mUser) {
        RealmObj.getInstance().addUserFromFacebook(mUser, regKey , this);
    }

    public void onActivityResultFB(int mRequestCode, int mResultCode, Intent mData, Context mContext) {
        this.mContext = mContext;
        mCallbackManager.onActivityResult(mRequestCode, mResultCode, mData);
    }

    private void errorUser() {
        Toast.makeText(mContext, "User error", Toast.LENGTH_SHORT).show();
    }

//    ===================================================
//        answer from db
//===================================================
    @Override
    public void isUserSaveLogin(boolean isSave, int mRegKey) {
        if (mRegKey == LoginActivity.REG_LOGIN) {
            mListenerFacebook.onSaveUserLogin(isSave);
        } else {
            mListenerFacebookRegistr.onSaveUserLogin(isSave);
        }
    }
//    ===================================================
//    END answer from db
//    ===================================================

    public interface ListenerFacebookLogin {
        void onSaveUserLogin(boolean mIsSave);
    }

    public interface ListenerFacebookRegistr {
        void onSaveUserLogin(boolean mIsSave);
    }

    public class UserFacebook {
        private String id;
        private String name;
        private String e_mail;
        private String birth;
        private String icon;

        public UserFacebook() {
        }

        public UserFacebook(String mId, String mName, String mE_mail, String mBirth, String mIcon) {
            id = mId;
            name = mName;
            e_mail = mE_mail;
            birth = mBirth;
            icon = mIcon;
        }

        public String getId() {
            return id;
        }

        public void setId(String mId) {
            id = mId;
        }

        public String getName() {
            return name;
        }

        public void setName(String mName) {
            name = mName;
        }

        public String getE_mail() {
            return e_mail;
        }

        public void setE_mail(String mE_mail) {
            e_mail = mE_mail;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String mBirth) {
            birth = mBirth;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String mIcon) {
            icon = mIcon;
        }
    }

}
