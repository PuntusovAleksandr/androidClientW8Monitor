package com.w8.w8monitor.android.facebook;

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
import com.w8.w8monitor.android.App;
import com.w8.w8monitor.android.activity.LoginActivity;
import com.w8.w8monitor.android.activity.RegisterActivity;
import com.w8.w8monitor.android.api.service.ApiService;
import com.w8.w8monitor.android.utils.SettingsApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static com.w8.w8monitor.android.api.constant.ApiConstants.LOGIN_SOCIAL;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_MAIL;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SOCIAL_ID;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SOCIAL_NAME;


/**
 * Created by AleksandrP on 14.09.2016.
 */

public class RegisterFacebook {


    private CallbackManager mCallbackManager;
    private Context mContext;
    private int regKey;
    private Activity mActivity;
    private UserFacebook mUser;


    public RegisterFacebook(Context mContext, int mRegKey) {
        this.mContext = mContext;
        this.regKey = mRegKey;
        mCallbackManager = CallbackManager.Factory.create();
        if (mRegKey == LoginActivity.REG_LOGIN) {
            mActivity = (LoginActivity) mContext;
        } else {
            mActivity = (RegisterActivity) mContext;
        }
    }

    public void register() {
        final LoginManager mInstance = LoginManager.getInstance();
        mInstance.logInWithReadPermissions(mActivity,
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
                                                    String birthday = null;
                                                    try {
                                                        birthday = object.getString("birthday");
                                                    } catch (JSONException mE) {
                                                        mE.printStackTrace();
                                                    }
                                                    String gender = null;
                                                    try {
                                                        gender = object.getString("gender");
                                                    } catch (JSONException mE) {
                                                        mE.printStackTrace();
                                                    }
                                                    mUser = new UserFacebook(
                                                            mId,
                                                            object.getString("name"),
                                                            object.getString("email"),
                                                            birthday,
                                                            gender
//                                                            "https://graph.facebook.com/" +
//                                                                    mId + "/picture?type=large"
                                                    );
                                                } catch (JSONException mE) {
                                                    Toast.makeText(mContext, "JSONException " + mE.getMessage(), Toast.LENGTH_SHORT).show();
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
                                mInstance.logOut();
                                mInstance.logInWithReadPermissions(mActivity,
                                        Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                errorUser();
                            }
                        });
    }

    private void saveInDb(UserFacebook mUser) {
        saveDateShared(mUser);

        Intent serviceIntent = new Intent(App.getContext(), ApiService.class);
        serviceIntent.putExtra(SERVICE_MAIL, mUser.getE_mail());
        serviceIntent.putExtra(SOCIAL_ID, mUser.getId());
        serviceIntent.putExtra(SOCIAL_NAME, mUser.getName());
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, LOGIN_SOCIAL);
        App.getContext().startService(serviceIntent);
    }

    private void saveDateShared(UserFacebook mUser) {
        int gender = -1;
        long date = -1;
        String genderStr = mUser.getGender();
        if (genderStr != null) {
            gender = genderStr.toLowerCase().contains("male") ? 1 : 2;
        }
        String birth = mUser.getBirth();
        if (birth != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            try {
                date = format.parse(birth).getTime();
            } catch (ParseException mE) {
                mE.printStackTrace();
            }
        }
        SettingsApp.getInstance().saveGenderFb(gender);
        SettingsApp.getInstance().saveBirthdayFb(date);
    }

    public void onActivityResultFB(int mRequestCode, int mResultCode, Intent mData, Context mContext) {
        this.mContext = mContext;
        mCallbackManager.onActivityResult(mRequestCode, mResultCode, mData);
    }

    private void errorUser() {
        Toast.makeText(mContext, "UserApi error", Toast.LENGTH_SHORT).show();
    }

//    ===================================================
//    END answer from db
//    ===================================================


    public class UserFacebook {
        private String id;
        private String name;
        private String e_mail;
        private String birth;
        private String gender;
        private String icon;

        public UserFacebook() {
        }


        public UserFacebook(String mId, String mName, String mE_mail, String mBirth, String mGender) {
            id = mId;
            name = mName;
            e_mail = mE_mail;
            birth = mBirth;
            gender = mGender;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String mGender) {
            gender = mGender;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String mIcon) {
            icon = mIcon;
        }
    }

}
