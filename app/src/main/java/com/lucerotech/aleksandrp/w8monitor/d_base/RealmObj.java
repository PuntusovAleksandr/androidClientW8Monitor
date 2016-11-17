package com.lucerotech.aleksandrp.w8monitor.d_base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lucerotech.aleksandrp.w8monitor.App;
import com.lucerotech.aleksandrp.w8monitor.alarm.AlarmView;
import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;
import com.lucerotech.aleksandrp.w8monitor.change_pass.ChangePasswordView;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.AlarmModel;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.ParamsBody;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.facebook.RegisterFacebook;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.CircleGraphView;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.LinerGraphView;
import com.lucerotech.aleksandrp.w8monitor.login.LoginView;
import com.lucerotech.aleksandrp.w8monitor.register.RegisterView;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.saveAllLogs;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class RealmObj {

    public static final String LOG_REALM = "LOG_REALM";

    private static RealmObj sRealmObj;
    private Context context;
    private Realm realm;
    private int allNameTypeCalendarsByUser;


    public synchronized static RealmObj getInstance() {
        if (sRealmObj == null) {
            sRealmObj = new RealmObj();
        }
        return sRealmObj;
    }

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

    private void setRealmData(Context context) {
        String nameDB = RealmObj.class.getName();
        realm = Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name(nameDB)
                        .deleteRealmIfMigrationNeeded()
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

    public void getUserByMailAndPass(String mLogin, String mPass,
                                     LoginView mListenerLoginView,
                                     UpdateUiEvent mEvent) {
        UserApi userApi = (UserApi) mEvent.getData();
        long count = realm.where(UserLibr.class)
                .equalTo("mail", mLogin)
                .equalTo("password", mPass)
                .count();
        boolean check = count > 0;
        UserLibr user = null;
        if (check) {
            user = realm.where(UserLibr.class)
                    .equalTo("mail", mLogin)
                    .equalTo("password", mPass)
                    .findFirst();
        }
        mListenerLoginView.userExist(check, user);
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
        int state = -1;
        if (userLibr != null) {
            state = userLibr.getState();
        }
        mProfileViewt.isSave(state);
    }


    public void getBodyUser(BodyListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        int body = -1;
        if (userLibr != null) {
            body = userLibr.getTypeBody();
        }
        mListener.isBody(body);
    }


    public void getProfileBle(ProfileBLeListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        int prfileBle = -1;
        if (userLibr != null) {
            prfileBle = userLibr.getProfileBLE();
        }
        mListener.isProfileBLE(prfileBle);
    }


    public void getDayBith(BirthDayListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        String date = "";
        if (userLibr != null) {
            date = userLibr.getBirthday();
        }
        mListener.isBirthDay(date);
    }


    public void getHeight(HeightListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        String height = "";
        if (userLibr != null) {
            height = userLibr.getHeight();
        }
        mListener.isHeight(height);
    }


    public void getAlarmFromDb(AlarmView mAlarmView) {
        mAlarmView.setAlarmItem(realm.where(AlarmModel.class)
                .findAll());
    }

    public void getAlarmFromDb(StringBuilder mMsgStr, AlarmListener mListener) {
        mListener.getAllAlarm(realm.where(AlarmModel.class)
                .findAll(), mMsgStr);
    }


    public UserLibr getUserForConnectBLE() {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        return userLibr;
    }


    public void getDataForCircle(final int mI, final CircleGraphView mCircleGraphView) {

        float weight_max = 0, weight_min = Float.MAX_VALUE,
                body_max = 0, body_min = Float.MAX_VALUE,
                fat_max = 0, fat_min = Float.MAX_VALUE,
                muscle_max = 0, muscle_min = Float.MAX_VALUE,
                water_max = 0, water_min = Float.MAX_VALUE,
                visceralFat_max = 0, visceralFat_min = Float.MAX_VALUE,
                emr_max = 0, emr_min = Float.MAX_VALUE,
                bmi_max = 0, bmi_min = Float.MAX_VALUE;

        RealmResults<ParamsBody> allSorted =
                realm.where(ParamsBody.class)
                        .equalTo("userName_id", SettingsApp.getInstance().getUserName())
                        .equalTo("profileBLE", SettingsApp.getInstance().getProfileBLE())
                        .findAllSorted("date_time", Sort.DESCENDING);
        ParamsBody last = new ParamsBody();
        ParamsBody preLast = new ParamsBody();
        if (allSorted != null && allSorted.size() > 0) {
            last = allSorted.get(0);
            if (allSorted.size() < 3) {
                preLast.setWeight(0f);
                preLast.setBody(0f);
                preLast.setFat(0f);
                preLast.setMuscle(0f);
                preLast.setWater(0f);
                preLast.setVisceralFat(0f);
                preLast.setEmr(0f);
                preLast.setBodyAge(0f);
                preLast.setDate_time(last.getDate_time());
                preLast.setBmi(0f);
                preLast.setProfileBLE(SettingsApp.getInstance().getProfileBLE());
            } else {
                preLast = allSorted.get(2);
            }
            for (int i = 0; i < allSorted.size(); i++) {
                ParamsBody body = allSorted.get(i);
                if (body.getWeight() > weight_max) {
                    weight_max = body.getWeight();
                }
                if (body.getWeight() < weight_min) {
                    weight_min = body.getWeight();
                }

                if (body.getBody() > body_max) {
                    body_max = body.getBody();
                }
                if (body.getBody() < body_min) {
                    body_min = body.getBody();
                }

                if (body.getFat() > fat_max) {
                    fat_max = body.getFat();
                }
                if (body.getFat() < fat_min) {
                    fat_min = body.getFat();
                }

                if (body.getMuscle() > muscle_max) {
                    muscle_max = body.getMuscle();
                }
                if (body.getMuscle() < muscle_min) {
                    muscle_min = body.getMuscle();
                }

                if (body.getWater() > water_max) {
                    water_max = body.getWater();
                }
                if (body.getWater() < water_min) {
                    water_min = body.getWater();
                }

                if (body.getVisceralFat() > visceralFat_max) {
                    visceralFat_max = body.getVisceralFat();
                }
                if (body.getVisceralFat() < visceralFat_min) {
                    visceralFat_min = body.getVisceralFat();
                }

                if (body.getEmr() > emr_max) {
                    emr_max = body.getEmr();
                }
                if (body.getEmr() < emr_min) {
                    emr_min = body.getEmr();
                }

                if (body.getBmi() > bmi_max) {
                    bmi_max = body.getBmi();
                }
                if (body.getBmi() < bmi_min) {
                    bmi_min = body.getBmi();
                }
            }
        } else {
            last = null;
            preLast = null;
        }
        float[] massParams = new float[]{
                weight_max, weight_min,
                body_max, body_min,
                fat_max, fat_min,
                muscle_max, muscle_min,
                water_max, water_min,
                visceralFat_max, visceralFat_min,
                emr_max, emr_min,
                bmi_max, bmi_min};
        mCircleGraphView.showDataCircle(mI, last, preLast, massParams);
    }


    public void getDataForLineChart(long timeNow,
                                    long timeStart,
                                    int mPickerBottomValue,
                                    LinerGraphView mLinerGraphView) {
        mLinerGraphView.getDataForLineChart(
                realm.where(ParamsBody.class)
                        .equalTo("userName_id", SettingsApp.getInstance().getUserName())
                        .equalTo("profileBLE", SettingsApp.getInstance().getProfileBLE())
                        .between("date_time", timeStart, timeNow)
                        .findAllSorted("date_time", Sort.ASCENDING),
                mPickerBottomValue);

    }


    public void getUserForSettings(GetUserForSettings mGetUserForSettings) {
        String userName = SettingsApp.getInstance().getUserName();
        mGetUserForSettings.getUserForSettings(
                realm.where(UserLibr.class)
                        .equalTo("mail", userName)
                        .findFirst());
    }

//    ===============================================================
//    END GET
//    ===============================================================
//    START PUT
//    ===============================================================

    // from register activity
    public void putUser(String email, String password, RegisterView mListener) {
        UserLibr userLibr1 = null;
        UserLibr userLibr = getDefoultUser(email, password);
        realm.beginTransaction();
        userLibr1 = realm.copyToRealmOrUpdate(userLibr);
        realm.commitTransaction();
        String mail = userLibr1.getMail();
        if (mListener != null && mail != null) {
            SettingsApp.getInstance().setUserName(email);
            SettingsApp.getInstance().setUserPassword(password);
            mListener.isUserSaveLogin(true, 2);
        }
    }

    /**
     * get defoult user for saving in db
     *
     * @param email
     * @param password
     * @return
     */
    @NonNull
    private UserLibr getDefoultUser(final String email, String password) {
        UserLibr userByMail = getUserByMail(STATICS_PARAMS.TEST_USER);
        if (!email.equalsIgnoreCase(STATICS_PARAMS.TEST_USER) && userByMail != null) {
            UserLibr user = new UserLibr();
            user.mail = email;
            user.password = password;
            user.state = userByMail.getState();
            user.typeBody = userByMail.getTypeBody();
            user.themeApp = userByMail.getThemeApp();
            user.profileBLE = userByMail.getProfileBLE();
            user.height = userByMail.getHeight();
            user.birthday = userByMail.getBirthday();

            // получаем все данные тестового юзера
             final RealmResults<ParamsBody> paramsBodies = getParamsBodyByEmail(STATICS_PARAMS.TEST_USER);
            for (ParamsBody body : paramsBodies) {
                addParamsBody(email, body.getWeight(), body.getBody(), body.getFat(),
                        body.getMuscle(), body.getWater(), body.getVisceralFat(),
                        body.getEmr(), body.getBodyAge(), body.getDate_time());
            }

            // удаляем все параметры тестового юзера из базы
           realm.executeTransaction(new Realm.Transaction() {
               @Override
               public void execute(Realm realm) {
                   RealmResults<ParamsBody> all = realm.where(ParamsBody.class)
                           .equalTo("userName_id", STATICS_PARAMS.TEST_USER)
                           .findAll();
                   all.deleteAllFromRealm();
               }
           });
            return user;
        }

        UserLibr userLibr = new UserLibr();
        userLibr.mail = email;
        userLibr.password = password;
        userLibr.state = 1;             // default
        userLibr.typeBody = 2;             // default
        userLibr.themeApp = 1;             // default
        userLibr.profileBLE = 1;             // default
        userLibr.height = "170";             // default
        userLibr.birthday = "25";             // default
//        userLibr.birthday = "01/01/2000";             // default
        return userLibr;
    }

    private RealmResults<ParamsBody> getParamsBodyByEmail(String mEmail) {
        return realm.where(ParamsBody.class)
                .equalTo("userName_id", mEmail)
                .findAll();
    }

    // from login (test) activity
    public void putUser(String email, String password, LoginView mListener) {
        UserLibr userLibr1 = null;
        UserLibr userLibr = getDefoultUser(email, password);
        realm.beginTransaction();
        userLibr1 = realm.copyToRealmOrUpdate(userLibr);
        realm.commitTransaction();
        String mail = userLibr1.getMail();
        if (mListener != null && mail != null) {
            mListener.goToProfile();
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
                saveAllLogs("ERROR in db addUserFromFacebook =" + error.getMessage());
            }
        });
    }

    private void sendAnswer(int mRegKey, boolean mIsSave, RealmListener mListener) {
        mListener.isUserSaveLogin(mIsSave, mRegKey);
    }


    public void setAlarmInDb(boolean isAmPicker, String mTime, AlarmView mAlarmView) {
        AlarmModel model = new AlarmModel();
        model.setAm(isAmPicker);
        model.setTime(mTime);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();

        // set alarm in system device
        mAlarmView.setAlarmInSystem(isAmPicker, mTime);

        // get data from db
        getAlarmFromDb(mAlarmView);
    }


    public void addParamsBody(final float mWeightBody, final float mBody, final float mFat,
                              final float mMuscul, final float mWaterBody, final float mFatVis,
                              final float mEmr, final float mAgeBody, final float bmi,
                              final CircleGraphView mCircleGraphView) {
        final String userName = SettingsApp.getInstance().getUserName();
        final long time = new Date().getTime();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ParamsBody paramsBody = new ParamsBody();
                paramsBody.setUserName_id(userName);
                paramsBody.setDate_time(time);
                paramsBody.setWeight(mWeightBody);
                paramsBody.setBody(mBody);
                paramsBody.setFat(mFat);
                paramsBody.setMuscle(mMuscul);
                paramsBody.setWater(mWaterBody);
                paramsBody.setVisceralFat(mFatVis);
                paramsBody.setEmr(mEmr);
                paramsBody.setBodyAge(mAgeBody);
                paramsBody.setBmi(bmi);
                paramsBody.setProfileBLE(SettingsApp.getInstance().getProfileBLE());

                bgRealm.copyToRealm(paramsBody);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // TODO: 15.10.2016 нужно отвтетить через интерфейс в соответствующие фрагменты
                float[] mMassParams = new float[9];
                mMassParams[0] = mWeightBody;
                mMassParams[1] = mBody;
                mMassParams[2] = mFat;
                mMassParams[3] = mMuscul;
                mMassParams[4] = mWaterBody;
                mMassParams[5] = mFatVis;
                mMassParams[6] = mEmr;
                mMassParams[7] = mAgeBody;
                mMassParams[8] = bmi;
                mCircleGraphView.showParams(mMassParams);
                saveAllLogs("addParamsBody onSuccess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // TODO: 15.10.2016 нужно отвтетить через интерфейс в соответствующие фрагменты
                mCircleGraphView.showMessageError();
                saveAllLogs("ERROR in db addUserFromFacebook =" + error.getMessage());
            }
        });

    }

    public void addParamsBody(final String email,
                              final float mWeightBody, final float mBody, final float mFat,
                              final float mMuscul, final float mWaterBody, final float mFatVis,
                              final float mEmr, final float mAgeBody, final long time) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ParamsBody paramsBody = new ParamsBody();
                paramsBody.setUserName_id(email);
                paramsBody.setDate_time(time);
                paramsBody.setWeight(mWeightBody);
                paramsBody.setBody(mBody);
                paramsBody.setFat(mFat);
                paramsBody.setMuscle(mMuscul);
                paramsBody.setWater(mWaterBody);
                paramsBody.setVisceralFat(mFatVis);
                paramsBody.setEmr(mEmr);
                paramsBody.setBodyAge(mAgeBody);
                paramsBody.setProfileBLE(SettingsApp.getInstance().getProfileBLE());

                bgRealm.copyToRealm(paramsBody);
            }
        });

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

    public void setBodyUser(int mBodyType, BodyListener mBodyListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            userLibr.setTypeBody(mBodyType);
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
            mBodyListener.isBody(mBodyType);
        } else {
            mBodyListener.isBody(-1);
        }
    }


    public void setProfileBLE(int mProfile, ProfileBLeListener mListener) {
        SettingsApp.getInstance().setProfileBLE(mProfile);
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            userLibr.setProfileBLE(mProfile);
            userLibr.setFullProfile(true);
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
            mListener.isProfileBLE(mProfile);
        } else {
            mListener.isProfileBLE(-1);
        }
    }

    public void setDayBith(String mDate, BirthDayListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            userLibr.setBirthday(mDate);
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
        }
        mListener.isBirthDay(mDate);
    }

    public void saveHeight(String mHeight, HeightListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("mail", userName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            userLibr.setHeight(mHeight);
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
        }
        mListener.isHeight(mHeight);
    }

    public void deleteAlarmFromDn(final String mTimeText, final AlarmView mAlarmView) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<AlarmModel> result = realm.where(AlarmModel.class)
                        .equalTo("time", mTimeText)
                        .findAll();
                result.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getAlarmFromDb(mAlarmView);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                saveAllLogs("ERROR in db deleteAlarmFromDn =" + error.getMessage());
            }
        });
    }

    public void setFullSettings(final ProfileBLeListener mBLeListener) {
        final String userName = SettingsApp.getInstance().getUserName();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserLibr userLibr = realm.where(UserLibr.class)
                        .equalTo("mail", userName)
                        .findFirst();
                userLibr.setFullProfile(true);
                realm.copyToRealmOrUpdate(userLibr);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mBLeListener.isOkFullSettings(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                saveAllLogs("ERROR in db setFullSettings =" + error.getMessage());
                mBLeListener.isOkFullSettings(false);
            }
        });
    }

    public void setFullFirsStartSettings(final ProfileFirstStartBLeListener mBLeListener) {
        final String userName = SettingsApp.getInstance().getUserName();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserLibr userLibr = realm.where(UserLibr.class)
                        .equalTo("mail", userName)
                        .findFirst();
                userLibr.setFullProfile(true);
                realm.copyToRealmOrUpdate(userLibr);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mBLeListener.isOkFullSettings(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                saveAllLogs("ERROR in db setFullSettings =" + error.getMessage());
                mBLeListener.isOkFullSettings(false);
            }
        });
    }

    public void setStateUserFromSettings(final int mRes, final GetUserForSettings mGetUserForSettings) {
        final String userName = SettingsApp.getInstance().getUserName();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserLibr userLibr = realm.where(UserLibr.class)
                        .equalTo("mail", userName)
                        .findFirst();
                userLibr.setState(mRes);
                realm.copyToRealmOrUpdate(userLibr);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getUserForSettings(mGetUserForSettings);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                saveAllLogs("ERROR in db setStateUserFromSettings =" + error.getMessage());
            }
        });
    }

    public void setTypeProfile(final int mRes, final GetUserForSettings mGetUserForSettings) {
        final String userName = SettingsApp.getInstance().getUserName();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserLibr userLibr = realm.where(UserLibr.class)
                        .equalTo("mail", userName)
                        .findFirst();
                userLibr.setTypeBody(mRes);
                realm.copyToRealmOrUpdate(userLibr);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getUserForSettings(mGetUserForSettings);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                saveAllLogs("ERROR in db setTypeProfile =" + error.getMessage());
            }
        });
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

    public interface BodyListener {
        void isBody(int body);
    }

    public interface BirthDayListener {
        void isBirthDay(String date);
    }

    public interface HeightListener {
        void isHeight(String height);
    }

    public interface ProfileFirstStartBLeListener {
        void isOkFullSettings(boolean mIsOkFullSettings);

    }

    public interface ProfileBLeListener {
        void isProfileBLE(int prfileBle);

        void isOkFullSettings(boolean mIsOkFullSettings);

    }

    public interface AlarmListener {
        void getAllAlarm(RealmResults<AlarmModel> mAll, StringBuilder mMsgStr);
    }

    public interface GetUserForSettings {

        void getUserForSettings(UserLibr mUserLibr);

    }


}
