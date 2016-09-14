package com.lucerotech.aleksandrp.w8monitor.d_base;

import android.content.Context;

import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class RealmObj {

    private static RealmObj sRealmObj;
    private Context context;
    private Realm realm;
    private int allNameTypeCalendarsByUser;


    public static RealmObj getInstance(Context context) {
        if (sRealmObj == null) {
            sRealmObj = new RealmObj(context);
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


    private RealmObj(Context context) {
        this.context = context;
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

//    ===============================================================
//    END PUT
//    ===============================================================


}
