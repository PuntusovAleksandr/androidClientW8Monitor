package com.lucertech.w8monitor.android.d_base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lucertech.w8monitor.android.App;
import com.lucertech.w8monitor.android.activity.interfaces.views.AlarmView;
import com.lucertech.w8monitor.android.activity.interfaces.views.ChangePasswordView;
import com.lucertech.w8monitor.android.activity.interfaces.views.LoginView;
import com.lucertech.w8monitor.android.activity.interfaces.views.MainView;
import com.lucertech.w8monitor.android.activity.interfaces.views.RegisterView;
import com.lucertech.w8monitor.android.api.event.UpdateUiEvent;
import com.lucertech.w8monitor.android.api.model.ProfileApi;
import com.lucertech.w8monitor.android.api.model.UserApi;
import com.lucertech.w8monitor.android.api.model.UserApiData;
import com.lucertech.w8monitor.android.d_base.model.AlarmModel;
import com.lucertech.w8monitor.android.d_base.model.ParamsBody;
import com.lucertech.w8monitor.android.d_base.model.Profile;
import com.lucertech.w8monitor.android.d_base.model.StringRealm;
import com.lucertech.w8monitor.android.d_base.model.UserLibr;
import com.lucertech.w8monitor.android.fragments.main.CircleGraphView;
import com.lucertech.w8monitor.android.fragments.main.LinerGraphView;
import com.lucertech.w8monitor.android.utils.STATICS_PARAMS;
import com.lucertech.w8monitor.android.utils.SettingsApp;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.lucertech.w8monitor.android.utils.LoggerApp.saveAllLogs;


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
        UserLibr userLibr = null;
        try {
            userLibr = realm.where(UserLibr.class)
                    .equalTo("email", email)
                    .findFirst();
        } catch (Exception mE) {
            mE.printStackTrace();
            return null;
        }
        return userLibr;
    }

    public void getUserByMailAndPass(final String mLogin, final String mPass,
                                     final LoginView mListenerLoginView,
                                     UpdateUiEvent mEvent) {
        UserLibr userByMail = getUserByMail(mLogin);

        final UserApi userApi = (UserApi) mEvent.getData();
        final UserApiData userApiData = userApi.getUser();
        final List<ProfileApi> profileApis = userApiData.getProfileApis();

        final UserLibr userLibr = new UserLibr();
        userLibr.setEmail(userApiData.getEmail());
        userLibr.setPassword(mPass);
        userLibr.setToken(userApi.getToken());
        userLibr.setId_server(userApiData.getId());
        userLibr.setCreated_at(userApiData.getCreated_at());
        userLibr.setUpdated_at(userApiData.getUpdated_at());
        userLibr.setIs_imperial(userApiData.is_imperial());
        userLibr.setKeep_login(userApiData.isKeep_login());
        userLibr.setTheme(userApiData.getTheme());
        userLibr.setProfileBLE(SettingsApp.getInstance().getProfileBLE());
        userLibr.setLanguage(userApiData.getLanguage());
        userLibr.setFullProfile(userByMail == null ? false : userByMail.isFullProfile());

        RealmList<Profile> profiles = userLibr.getProfiles();
        if (profiles != null) {
            profiles.clear();
        } else {
            userLibr.setProfiles(new RealmList<Profile>());
        }

        for (int i = 0; i < profileApis.size(); i++) {
            ProfileApi profileApi = profileApis.get(i);
            Profile profile = new Profile();
            profile.setId(profileApi.getId());
            profile.setUser_id(profileApi.getUser_id());
            profile.setActivity_type(profileApi.getActivity_type());
            profile.setHeight(profileApi.getHeight());
            profile.setGender(profileApi.getGender());
            profile.setAge(profileApi.getBirthday());
            profile.setCreated_at(profileApi.getCreated_at());
            profile.setUpdated_at(profileApi.getUpdated_at());
            profile.setNumber(profileApi.getNumber());
            profile.setIs_current(profileApi.is_current());

            userLibr.getProfiles().add(profile);
        }

        realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(userLibr);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        SettingsApp.getInstance().setUserName(mLogin);
                        SettingsApp.getInstance().setUserPassword(mPass);
                        mListenerLoginView.userExist(true, userLibr);
                        setAlarmByUserByLogin(mLogin, userApiData.getAlarms());
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        mListenerLoginView.userExist(false, null);
                    }
                });
    }


    public void checkEmail(String mEmail, LoginView mListenerLoginView) {
        long count = realm.where(UserLibr.class)
                .equalTo("email", mEmail)
                .count();
        mListenerLoginView.changePassUserExist((count > 0), mEmail);
    }


    public void getStateUser(StateListener mProfileViewt) {
        String userName = SettingsApp.getInstance().getUserName();
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();

        int state = -1;
        if (userLibr != null) {
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    state = profiles.get(i).getGender();
                    if (state == 0) {
                        state = 1;
                    }
                }
            }
        }
        mProfileViewt.isSave(state);
    }


    public void getBodyUser(BodyListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();
        int body = -1;
        if (userLibr != null) {
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    body = profiles.get(i).getActivity_type();
                    if (body == 0) {
                        body = 2;
                    }
                }
            }
        }
        mListener.isBody(body);
    }


    public void getProfileBle(ProfileBLeListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();
        int prfileBle = -1;
        if (userLibr != null) {
            prfileBle = userLibr.getProfileBLE();
        }
        mListener.isProfileBLE(prfileBle);
    }


    public void getDayBith(BirthDayListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();
        String date = "";
        if (userLibr != null) {
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    date = profiles.get(i).getAge() + "";
                    if (date.equals("0")) {
                        date = "25";
                    }
                }
            }
        }
        mListener.isBirthDay(date);
    }


    public void getHeight(HeightListener mListener) {
        String userName = SettingsApp.getInstance().getUserName();
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();
        String height = "";
        if (userLibr != null) {
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    height = profiles.get(i).getHeight() + "";
                    if (height.equals("0")) {
                        height = "170";
                    }
                }
            }
        }
        mListener.isHeight(height);
    }


    public RealmResults<AlarmModel> getAlarmFromDb(String mUserName) {
        return realm.where(AlarmModel.class)
                .equalTo("email", SettingsApp.getInstance().getUserName())
                .findAll();
    }

    public void getAlarmFromDb(AlarmView mAlarmView) {
        RealmResults<AlarmModel> alarmItems =
                realm.where(AlarmModel.class)
                        .equalTo("email", SettingsApp.getInstance().getUserName())
                        .findAll();

        mAlarmView.updateAlarms();

        mAlarmView.setAlarmItem(alarmItems);
    }

    public void getAlarmFromDb(StringBuilder mMsgStr, AlarmListener mListener) {
        mListener.getAllAlarm(realm.where(AlarmModel.class)
                .findAll(), mMsgStr);
    }


    public UserLibr getUserForConnectBLE() {
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
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

        int id = 0;
        UserLibr userByMail = getUserByMail(SettingsApp.getInstance().getUserName());
        RealmList<Profile> profiles = userByMail.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);
            if (profile.is_current() &&
                    profile.getNumber() == SettingsApp.getInstance().getProfileBLE()) {
                id = profile.getId();
            }
        }

        mLinerGraphView.getDataForLineChart(
                realm.where(ParamsBody.class)
                        .equalTo("userName_id", SettingsApp.getInstance().getUserName())
                        .equalTo("profile_id", id)
                        .between("date_time", timeStart, timeNow)
                        .findAllSorted("date_time", Sort.ASCENDING),
                mPickerBottomValue);

    }


    public void getUserForSettings(GetUserForSettings mGetUserForSettings) {
        String userName = SettingsApp.getInstance().getUserName();
        mGetUserForSettings.getUserForSettings(
                realm.where(UserLibr.class)
                        .equalTo("email", userName)
                        .findFirst());
    }


    public ParamsBody getLastBodyParam(String mUserName, long mTime) {
        return realm.where(ParamsBody.class)
                .equalTo("userName_id", mUserName)
                .equalTo("date_time", mTime)
                .findFirst();
    }


    public RealmResults<ParamsBody> getAllNoSyncParamBodies(int mIdProfileNow) {

        return realm.where(ParamsBody.class)
                .equalTo("profile_id", mIdProfileNow)
                .equalTo("synced", false)
                .findAll();
    }


    public void getLastBodyParamsByServer(MainView mMainView) {
        mMainView.getAllMeasurementsFromServer(
                realm.where(ParamsBody.class)
                        .equalTo("userName_id", SettingsApp.getInstance().getUserName())
                        .findAllSorted("date_time", Sort.ASCENDING));
    }

    public ParamsBody getLastParamsBody() {

        int id = 0;
        UserLibr userByMail = getUserByMail(SettingsApp.getInstance().getUserName());
        RealmList<Profile> profiles = userByMail.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);
            if (profile.is_current() &&
                    profile.getNumber() == SettingsApp.getInstance().getProfileBLE()) {
                id = profile.getId();
            }
        }

        RealmResults<ParamsBody> allSorted = realm.where(ParamsBody.class)
                .equalTo("userName_id", SettingsApp.getInstance().getUserName())
                .equalTo("profile_id", id)
                .findAllSorted("date_time", Sort.DESCENDING);
        return allSorted.get(0);
    }


    public RealmResults<ParamsBody> getDataUserForGoogleFit(int mIdProfile) {
        RealmResults<ParamsBody> allSorted = realm.where(ParamsBody.class)
                .equalTo("userName_id", SettingsApp.getInstance().getUserName())
                .equalTo("profile_id", mIdProfile)
                .findAllSorted("date_time", Sort.DESCENDING);
        return allSorted;
    }


//    ===============================================================
//    END GET
//    ===============================================================
//    START PUT
//    ===============================================================

    // from register activity
    public void putUser(String mLogin, String mPass, final RegisterView mListener,
                        UpdateUiEvent mEvent) {
        UserLibr userByMail = getUserByMail(mLogin);

        final UserApi userApi = (UserApi) mEvent.getData();
        final UserApiData userApiData = userApi.getUser();
        final List<ProfileApi> profileApis = userApiData.getProfileApis();

        final UserLibr userLibr = new UserLibr();
        userLibr.setEmail(userApiData.getEmail());
        userLibr.setPassword(mPass);
        userLibr.setToken(userApi.getToken());
        userLibr.setId_server(userApiData.getId());
        userLibr.setCreated_at(userApiData.getCreated_at());
        userLibr.setUpdated_at(userApiData.getUpdated_at());
        userLibr.setIs_imperial(userApiData.is_imperial());
        userLibr.setKeep_login(userApiData.isKeep_login());
        userLibr.setTheme(userApiData.getTheme());
        userLibr.setProfileBLE(SettingsApp.getInstance().getProfileBLE());
        userLibr.setLanguage(userApiData.getLanguage());
        userLibr.setFullProfile(userByMail == null ? false : userByMail.isFullProfile());

        RealmList<Profile> profiles = userLibr.getProfiles();
        if (profiles != null) {
            profiles.clear();
        } else {
            userLibr.setProfiles(new RealmList<Profile>());
        }

        for (int i = 0; i < profileApis.size(); i++) {
            ProfileApi profileApi = profileApis.get(i);
            Profile profile = new Profile();
            profile.setId(profileApi.getId());
            profile.setUser_id(profileApi.getUser_id());
            profile.setActivity_type(profileApi.getActivity_type());
            profile.setHeight(profileApi.getHeight());
            profile.setGender(profileApi.getGender());
            profile.setAge(profileApi.getBirthday());
            profile.setCreated_at(profileApi.getCreated_at());
            profile.setUpdated_at(profileApi.getUpdated_at());
            profile.setNumber(profileApi.getNumber());
            profile.setIs_current(profileApi.is_current());

            userLibr.getProfiles().add(profile);
        }

        realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(userLibr);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        mListener.isUserSaveLogin(true, userLibr);
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        mListener.isUserSaveLogin(true, null);
                    }
                });
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
        UserLibr userLibr = getUserByMail(STATICS_PARAMS.TEST_USER);

        if (userLibr != null) {
            realm.beginTransaction();
            userLibr.setKeep_login(SettingsApp.getInstance().getAutoLogin());
            userLibr.setTheme(SettingsApp.getInstance().isThemeDark() ? 1 : 0);
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
            return userLibr;
        } else {
            String data = new Date().getTime() + "";
            userLibr = new UserLibr();
            userLibr.setEmail(email);
            userLibr.setPassword(password);
            userLibr.setToken("");            // default
            userLibr.setId_server(0);            // default
            userLibr.setCreated_at(data);            // default
            userLibr.setUpdated_at(data);            // default
            userLibr.setIs_imperial(!SettingsApp.getInstance().getMetric());            // default
            userLibr.setKeep_login(SettingsApp.getInstance().getAutoLogin());            // default
            userLibr.setTheme(SettingsApp.getInstance().isThemeDark() ? 1 : 0);            // default
            userLibr.setProfileBLE(SettingsApp.getInstance().getProfileBLE());            // default
            userLibr.setLanguage(SettingsApp.getInstance().getLanguages());
            userLibr.setFullProfile(false);


            RealmList<Profile> profiles = new RealmList<>();
            for (int i = 0; i < 2; i++) {
                Profile profile = new Profile();
                profile.setId(i);
                profile.setUser_id(userLibr.getId_server());
                profile.setActivity_type(2);
                profile.setHeight(170);
                profile.setGender(1);
                profile.setAge(25);
                profile.setCreated_at(data);
                profile.setUpdated_at(data);
                profile.setNumber(i + 1);

                profiles.add(profile);
            }

            userLibr.setProfiles(profiles);

        }
        return userLibr;
    }

    private RealmResults<ParamsBody> getParamsBodyByEmail(String mEmail) {
        return realm.where(ParamsBody.class)
                .equalTo("userName_id", mEmail)
                .findAll();
    }

    // from login (test) activity
    public void putUser(final String email, final String password, final LoginView mListener) {

        final UserLibr userLibr = getDefoultUser(email, password);

        realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(userLibr);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        SettingsApp.getInstance().setUserName(email);
                        SettingsApp.getInstance().setUserPassword(password);
                        mListener.userExist(true, userLibr);
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        mListener.userExist(true, userLibr);
                    }
                });
    }


    public void setAlarmInDb(boolean isAmPicker, String mTime, AlarmView mAlarmView) {
        AlarmModel model = new AlarmModel();
        model.setAm(isAmPicker);
        model.setTime(mTime);
        model.setEmail(SettingsApp.getInstance().getUserName());

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
                              final float mEmr, final float mAgeBody, final float bmi, final long time,
                              final CircleGraphView mCircleGraphView, final boolean sync) {
        final String userName = SettingsApp.getInstance().getUserName();

        int id_profile = 0;
        UserLibr userByMail = getUserByMail(SettingsApp.getInstance().getUserName());
        RealmList<Profile> profiles = userByMail.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);
            if (profile.is_current() &&
                    profile.getNumber() == SettingsApp.getInstance().getProfileBLE()) {
                id_profile = profile.getId();
            }
        }

        final int finalId_profile = id_profile;
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
                paramsBody.setProfile_id(finalId_profile);
                paramsBody.setSynced(sync);
                paramsBody.setProfileBLE(SettingsApp.getInstance().getProfileBLE());

                bgRealm.copyToRealmOrUpdate(paramsBody);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
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
                mCircleGraphView.showParams(mMassParams, time, sync);
                saveAllLogs("addParamsBody onSuccess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
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


    private void setAlarmByUserByLogin(String mLogin, List<String> mAlarms) {
        if (mAlarms == null ||
                (mAlarms != null && mAlarms.size() == 1 && mAlarms.get(0).equalsIgnoreCase("*"))) {
            return;
        }
        RealmResults<AlarmModel> alarmFromDb = getAlarmFromDb(mLogin);

        if (alarmFromDb.size() > 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<AlarmModel> alarmModels = realm.where(AlarmModel.class)
                            .equalTo("email", SettingsApp.getInstance().getUserName())
                            .findAll();
                    alarmModels.deleteAllFromRealm();
                }
            });

        }
        for (int i = 0; i < mAlarms.size(); i++) {
            String alarmText = mAlarms.get(i);
            String[] split = alarmText.split("/");
            boolean isAmPicker = split[2].equalsIgnoreCase("am");
            StringBuilder builder = new StringBuilder();
            builder = builder.append(split[0]);
            builder = builder.append(":");
            builder = builder.append(split[1]);

            AlarmModel model = new AlarmModel();
            model.setAm(isAmPicker);
            model.setTime(builder.toString());
            model.setEmail(mLogin);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(model);
            realm.commitTransaction();
        }

    }

//    ===============================================================
//    END PUT
//    ===============================================================
//    update
//    ===============================================================


    public void checkAndChangePassword(String mMailUser, String mPasswordTextOld,
                                       String mPasswordText, ChangePasswordView mPasswordView,
                                       UpdateUiEvent mEvent) {
        UserLibr userLibr_ = transformUpdateEventUserApi(mEvent);

        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", mMailUser)
//                .equalTo("password", mPasswordTextOld)
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
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", mUserName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    profiles.get(i).setGender(mState);
                }
            }
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
            mListener.isSave(mState);
        } else {
            mListener.isSave(-1);
        }
    }

    public void setBodyUser(int mBodyType, BodyListener mBodyListener) {
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    profiles.get(i).setActivity_type(mBodyType);
                }
            }
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
                .equalTo("email", userName)
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
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    profiles.get(i).setAge(Integer.parseInt(mDate));
                }
            }
            realm.copyToRealmOrUpdate(userLibr);
            realm.commitTransaction();
        }
        mListener.isBirthDay(mDate);
    }

    public void saveHeight(String mHeight, HeightListener mListener) {
        int profileBLE = SettingsApp.getInstance().getProfileBLE();
        String userName = SettingsApp.getInstance().getUserName();
        UserLibr userLibr = realm.where(UserLibr.class)
                .equalTo("email", userName)
                .findFirst();
        if (userLibr != null) {
            realm.beginTransaction();
            RealmList<Profile> profiles = userLibr.getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getNumber() == profileBLE) {
                    profiles.get(i).setHeight((int) Float.parseFloat(mHeight));
                }
            }
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
                        .equalTo("email", SettingsApp.getInstance().getUserName())
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
                        .equalTo("email", userName)
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
                        .equalTo("email", userName)
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
        final int profileBLE = SettingsApp.getInstance().getProfileBLE();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserLibr userLibr = realm.where(UserLibr.class)
                        .equalTo("email", userName)
                        .findFirst();
                RealmList<Profile> profiles = userLibr.getProfiles();
                for (int i = 0; i < profiles.size(); i++) {
                    if (profiles.get(i).getNumber() == profileBLE) {
                        profiles.get(i).setGender(mRes);
                    }
                }
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
        final int profileBLE = SettingsApp.getInstance().getProfileBLE();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserLibr userLibr = realm.where(UserLibr.class)
                        .equalTo("email", userName)
                        .findFirst();
                RealmList<Profile> profiles = userLibr.getProfiles();
                for (int i = 0; i < profiles.size(); i++) {
                    if (profiles.get(i).getNumber() == profileBLE) {
                        profiles.get(i).setActivity_type(mRes);
                    }
                }
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

    public void updateUserDb(final MainView mGraphView, UserApi mEvent) {
        final UserApiData userApiData = mEvent.getUser();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserLibr userLibr = realm.where(UserLibr.class)
                        .equalTo("email", userApiData.getEmail())
                        .findFirst();

                userLibr.setUpdated_at(userApiData.getUpdated_at());
                RealmList<StringRealm> userLibrAlarms = userLibr.getAlarms();
                if (userLibrAlarms != null) {
                    userLibrAlarms.clear();
                } else {
                    userLibrAlarms = new RealmList<StringRealm>();
                }
                List<String> alarms = userApiData.getAlarms();
                if (alarms != null) {
                    for (String str : alarms) {
                        StringRealm stringRealm = new StringRealm();
                        stringRealm.text = str;
                        userLibrAlarms.add(stringRealm);
                    }

                    userLibr.setAlarms(userLibrAlarms);
                }
                realm.copyToRealmOrUpdate(userLibr);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mGraphView.makeRequestUpdateMeasurement();
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
//==============================================================


    private UserLibr transformUpdateEventUserApi(UpdateUiEvent mEvent) {

        UserLibr userLibr = new UserLibr();
        RealmList<Profile> profiles = new RealmList<>();

        UserApi userApi = (UserApi) mEvent.getData();
        UserApiData userApiData = userApi.getUser();
        List<ProfileApi> profileApis = userApiData.getProfileApis();

        for (int i = 0; i < profileApis.size(); i++) {
            ProfileApi profileApi = profileApis.get(i);
            Profile profile = new Profile();
            profile.setId(profileApi.getId());
            profile.setUser_id(profileApi.getUser_id());
            profile.setActivity_type(profileApi.getActivity_type());
            profile.setHeight(profileApi.getHeight());
            profile.setGender(profileApi.getGender());
            profile.setAge(profileApi.getBirthday());
            profile.setCreated_at(profileApi.getCreated_at());
            profile.setUpdated_at(profileApi.getUpdated_at());
            profile.setNumber(profileApi.getNumber());

            profiles.add(profile);
        }
        userLibr.setEmail(userApiData.getEmail());
        userLibr.setPassword(SettingsApp.getInstance().getUserPassword());
        userLibr.setToken(userApi.getToken());
        userLibr.setId_server(userApiData.getId());
        userLibr.setCreated_at(userApiData.getCreated_at());
        userLibr.setUpdated_at(userApiData.getUpdated_at());
        userLibr.setIs_imperial(!SettingsApp.getInstance().getMetric());
        userLibr.setKeep_login(SettingsApp.getInstance().getAutoLogin());
        userLibr.setTheme(userApiData.getTheme());
        userLibr.setLanguage(userApiData.getLanguage());
        userLibr.setProfileBLE(SettingsApp.getInstance().getProfileBLE());
        userLibr.setProfiles(profiles);


        return userLibr;
    }


//==============================================================


}
