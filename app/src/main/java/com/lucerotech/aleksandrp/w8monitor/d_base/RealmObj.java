package com.lucerotech.aleksandrp.w8monitor.d_base;

import android.content.Context;
import android.util.Log;

import com.lucerotech.aleksandrp.w8monitor.App;
import com.lucerotech.aleksandrp.w8monitor.change_pass.ChangePasswordView;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;
import com.lucerotech.aleksandrp.w8monitor.login.LoginView;
import com.lucerotech.aleksandrp.w8monitor.register.RegisterView;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class RealmObj {

    public static final String LOG_REALM = "LOG_REALM";

    private static RealmObj sRealmObj;
    private Context context;
    private Realm realm;
    private int allNameTypeCalendarsByUser;

//    private RegisterView mRegisterView;
//    private RealmListener mListener;
//    private LoginView mListenerLoginView;

    public static RealmObj getInstance() {
        if (sRealmObj == null) {
            sRealmObj = new RealmObj();
        }
        return sRealmObj;
    }

//    public static RealmObj getInstance(RegisterView mListener) {
//        if (sRealmObj == null) {
//            sRealmObj = new RealmObj( mListener);
//        }
//        return sRealmObj;
//    }
//
//    public static RealmObj getInstance( RealmListener mListener) {
//        if (sRealmObj == null) {
//            sRealmObj = new RealmObj( mListener);
//        }
//        return sRealmObj;
//    }
//
//    public static RealmObj getInstance(LoginView mListener) {
//        if (sRealmObj == null) {
//            sRealmObj = new RealmObj( mListener);
//        }
//        return sRealmObj;
//    }

    /**
     * for creating (or change) data base, need reopen Realm
     * This method need calling after save data in Shared preference
     */
    public static void stopRealm() {
        if (sRealmObj != null) {
            sRealmObj.closeRealm(App.getContext());
        }
    }

    private void closeRealm(Context context) {
        if (realm != null) {
            realm.close();
            realm = null;
            setRealmData(context);
        }
    }


    private RealmObj() {
        this.context = App.getContext();
        if (realm == null) {
            setRealmData(context);
        }
    }
//
//    private RealmObj(RegisterView mListener) {
//        this.context = App.getContext();
//        this.mRegisterView = mListener;
//        if (realm == null) {
//            setRealmData(context);
//        }
//    }
//
//    private RealmObj( RealmListener mListener) {
//        this.context = App.getContext();
//        this.mListener = mListener;
//        if (realm == null) {
//            setRealmData(context);
//        }
//    }
//
//    private RealmObj(LoginView mListener) {
//        this.context = App.getContext();
//        this.mListenerLoginView = mListener;
//        if (realm == null) {
//            setRealmData(context);
//        }
//    }

    private void setRealmData(Context context) {
        String nameDB = RealmObj.class.getName();
        realm = Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name(nameDB)
                        .schemaVersion(STATICS_PARAMS.VERSION_DB)
                        .build()
        );
    }

//    ===============================================================
//    START GET
//    ===============================================================

    public UserLibr getUserByMail(String email) {
        return realm.where(UserLibr.class)
                .equalTo("mail", email)
                .findFirst();
    }

    public void getUserByMailAndPass(String mLogin, String mPass, LoginView mListenerLoginView) {
        long count = realm.where(UserLibr.class)
                .equalTo("mail", mLogin)
                .equalTo("password", mPass)
                .count();
        mListenerLoginView.userExist(count > 0);
    }


    public void checkEmail(String mEmail, LoginView mListenerLoginView) {
        long count = realm.where(UserLibr.class)
                .equalTo("mail", mEmail)
                .count();
        mListenerLoginView.changePassUserExist((count > 0), mEmail);
    }


    public void getStateUser(StateListener mProfileViewt) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        mProfileViewt.isSave(userLibr.getState());
    }


//    ===============================================================
//    END GET
//    ===============================================================
//    START PUT
//    ===============================================================

    public void putUser(String email, String password, RegisterView mListener) {
        UserLibr userLibr = new UserLibr();
        UserLibr userLibr1 = null;
        userLibr.mail = email;
        userLibr.password = password;
        realm.beginTransaction();
        userLibr1 = realm.copyToRealmOrUpdate(userLibr);
        realm.commitTransaction();
        String mail = userLibr1.getMail();
        if (mListener != null && mail != null) {
            mListener.isUserSaveLogin(true, 2);
        }
    }

    public void addUserFromFacebook(final RegisterFacebook.UserFacebook mUser, final int mRegKey,
                                    final RealmListener mListener) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                UserLibr userLibr = new UserLibr();
//                UserLibr userLibr = bgRealm.copyToRealmOrUpdate(object);
                userLibr.setMail(mUser.getE_mail());
                userLibr.setFaceboolId(mUser.getId());
                userLibr.setBirthday(mUser.getBirth());
                bgRealm.copyToRealmOrUpdate(userLibr);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                sendAnswer(mRegKey, true, mListener);

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                sendAnswer(mRegKey, false, mListener);
                Log.i(LOG_REALM, error.getMessage());
            }
        });
    }

    private void sendAnswer(int mRegKey, boolean mIsSave, RealmListener mListener) {
        mListener.isUserSaveLogin(mIsSave, mRegKey);
    }

//    ===============================================================
//    END PUT
//    ===============================================================
//    update
//    ===============================================================


    public void checkAndChangePassword(String mMailUser, String mPasswordTextOld,
                                       String mPasswordText, ChangePasswordView mPasswordView) {
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", mMailUser)
                .equalTo("password", mPasswordTextOld)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            userLibr.setPassword(mPasswordText);
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
            mPasswordView.showMessageOkChangePass();
        } else {
            mPasswordView.showMessageNotFoundUser();
        }
    }

    public void setStateUser(String mUserName, int mState, StateListener mListener) {
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", mUserName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            userLibr.setState(mState);
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
            mListener.isSave(mState);
        } else {
            mListener.isSave(-1);
        }
    }


//    ===============================================================
//    END update
//    ===============================================================
//    LISTENER
//    ===============================================================

    public interface RealmListener {
        void isUserSaveLogin(boolean isSave, int mRegKey);
    }

    public interface StateListener {
        void isSave(int state);
    }


}
