package com.lucerotech.aleksandrp.w8monitor.d_base;

import android.content.Context;
import android.util.Log;

import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;

import java.util.List;

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

    private RealmListener mListener;

    public static RealmObj getInstance(Context context, RealmListener mListener) {
        if (sRealmObj == null) {
            sRealmObj = new RealmObj(context, mListener);
        }
        return sRealmObj;
    }

    /**
     * for creating (or change) data base, need reopen Realm
     * This method need calling after save data in Shared preference
     */
    public static void stopRealm(Context context) {
        if (sRealmObj != null) {
            sRealmObj.closeRealm(context);
        }
    }

    private void closeRealm(Context context) {
        if (realm != null) {
            realm.close();
            realm = null;
            setRealmData(context);
        }
    }


    private RealmObj(Context context, RealmListener mListener) {
        this.context = context;
        this. mListener = mListener;
        if (realm == null) {
            setRealmData(context);
        }
    }

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

//    ===============================================================
//    END GET
//    ===============================================================
//    START PUT
//    ===============================================================

    public boolean putUser(String email, String password) {
        List<UserLibr> userAnswer;
        UserLibr userLibr = new UserLibr();
        userLibr.mail = email;
        userLibr.password = password;
        realm.beginTransaction();
        userAnswer = (List<UserLibr>) realm.copyToRealmOrUpdate(userLibr);
        realm.commitTransaction();
        return userAnswer.size() > 0;
    }

    public void addUserFromFacebook(final RegisterFacebook.UserFacebook mUser, final int mRegKey) {

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
                sendAnswer(mRegKey, true);

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                sendAnswer(mRegKey, false);
                Log.i(LOG_REALM, error.getMessage());
            }
        });
    }

    private void sendAnswer(int mRegKey, boolean mIsSave) {
            mListener.isUserSaveLogin(mIsSave, mRegKey);
    }

//    ===============================================================
//    END PUT
//    ===============================================================
//    LISTENER
//    ===============================================================

    public interface RealmListener {
        void isUserSaveLogin(boolean isSave, int mRegKey);
    }

}
