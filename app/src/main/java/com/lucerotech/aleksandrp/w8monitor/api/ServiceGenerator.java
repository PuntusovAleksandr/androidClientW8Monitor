package com.lucerotech.aleksandrp.w8monitor.api;

import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.App;
import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.api.constant.ApiConstants;
import com.lucerotech.aleksandrp.w8monitor.api.event.NetworkResponseEvent;
import com.lucerotech.aleksandrp.w8monitor.api.model.Measurement;
import com.lucerotech.aleksandrp.w8monitor.api.model.ProfileApi;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApiData;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.ParamsBody;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.Profile;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.logger;
import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.loggerE;

/**
 * Created by AleksandrP on 16.11.2016.
 */

public class ServiceGenerator {


    public static final String API_BASE_URL = "https://w8.rockettaxi.ru/v1/";


    private String userName = SettingsApp.getInstance().getUserName();
    private String userPassword = SettingsApp.getInstance().getUserPassword();
    private int profileBLE = SettingsApp.getInstance().getProfileBLE();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private CallBackServiceGenerator mCallBackServiceGenerator;
    private NetworkResponseEvent event;

    private static String authToken;

    public ServiceGenerator(CallBackServiceGenerator mCallBackServiceGenerator) {
        this.mCallBackServiceGenerator = mCallBackServiceGenerator;
    }

    private static <S> S createService(Class<S> serviceClass, final boolean mNeedToken) {
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder().addInterceptor(
                new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request;
                        if (!mNeedToken) {
                            request = chain.request().newBuilder()
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                        } else {
                            request = chain.request().newBuilder()
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("Authorization", "Bearer " + authToken)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = builder
                .client(defaultHttpClient)
                .build();

        return retrofit.create(serviceClass);
    }


    /**
     * login
     *
     * @param mMail
     * @param mPass
     */
    public void loginToServer(final String mMail, final String mPass) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, false);
        Call<UserApi> call = downloadService.login(mMail, mPass);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                UserApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.LOGIN);
                } else {
                    //200
                    UserApiData user = body.getUser();
                    SettingsApp.getInstance().setAutoLogin(user.isKeep_login());
                    SettingsApp.getInstance().setMetric(!user.is_imperial());
                    SettingsApp.getInstance().setLanguages(user.getLanguage());
                    SettingsApp.getInstance().setThemeDark(user.getTheme() == 1 ? true : false);

                    List<ProfileApi> profileApis = user.getProfileApis();
                    for (int i = 0; i < profileApis.size(); i++) {
                        ProfileApi profileApi = profileApis.get(i);
                        if (profileApi.is_current()) {
                            SettingsApp.getInstance().setProfileBLE(profileApi.getNumber());
                        }
                    }

                    SettingsApp.getInstance().setUserName(mMail);
                    SettingsApp.getInstance().setUserPassword(mPass);
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.LOGIN);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.LOGIN);
            }
        });
    }


    /**
     * login witch social network
     *
     * @param mMail
     * @param idSocialNetwork
     */
    public void loginSocialToServer(final String mMail, final String idSocialNetwork) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, false);
        Call<UserApi> call = downloadService.loginSocial(mMail, idSocialNetwork);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                UserApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.LOGIN_SOCIAL);

                } else {
                    //200

                    UserApiData user = body.getUser();

                    try {
                        SettingsApp.getInstance().setAutoLogin(user.isKeep_login());
                        SettingsApp.getInstance().setMetric(!user.is_imperial());
                        SettingsApp.getInstance().setLanguages(user.getLanguage());
                        SettingsApp.getInstance().setThemeDark(user.getTheme() == 1 ? true : false);
                    } catch (Exception mE) {
                        logger("loginSocialToServer " + mE.getMessage());
                    }

                    List<ProfileApi> profileApis = user.getProfileApis();
                    for (int i = 0; i < profileApis.size(); i++) {
                        ProfileApi profileApi = profileApis.get(i);
                        if (profileApi.is_current()) {
                            SettingsApp.getInstance().setProfileBLE(profileApi.getNumber());
                        }
                    }
                    SettingsApp.getInstance().setUserName(mMail);
                    SettingsApp.getInstance().setUserPassword(idSocialNetwork);
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.LOGIN_SOCIAL);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.LOGIN_SOCIAL);
            }
        });
    }

    /**
     * register
     *
     * @param mMail
     * @param mPass
     */
    public void registerToServer(final String mMail, final String mPass) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, false);
        Call<UserApi> call = downloadService.register(mMail, mPass);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                UserApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.REGISTER);

                } else {
                    //200
                    UserApiData user = body.getUser();
                    user.setKeep_login(SettingsApp.getInstance().getAutoLogin());
                    user.setIs_imperial(!SettingsApp.getInstance().getMetric());
                    user.setLanguage(SettingsApp.getInstance().getLanguages());
                    user.setTheme(SettingsApp.getInstance().isThemeDark() ? 1 : 0);
                    SettingsApp.getInstance().setProfileBLE(1);
                    SettingsApp.getInstance().setUserName(mMail);
                    SettingsApp.getInstance().setUserPassword(mPass);
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.REGISTER);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.REGISTER);
            }
        });
    }

    /**
     * create profile
     */
    public void profileCreateToServer() {
        Profile mProfileApi = null;

        mProfileApi = getProfile(mProfileApi);

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, true);
        Call<ProfileApi> call = downloadService.profileCreate(
                mProfileApi.getId(),
                mProfileApi.getActivity_type(),
                mProfileApi.getHeight(),
                true,
                mProfileApi.getAge(),
                mProfileApi.getGender()
        );
        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                ProfileApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.PROFILE);

                } else {
                    //200
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.PROFILE);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.PROFILE);
            }
        });
    }


    public void sendMeasurementsToServer() {

        Measurement mMeasurement = new Measurement();

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, true);
        Call<Measurement> call = downloadService.measurements(
                mMeasurement.getBmi(),
                mMeasurement.getBody_water(),
                mMeasurement.getBone_mass(),
                mMeasurement.getCalories(),
                mMeasurement.getFat(),
                mMeasurement.getFat_level(),
                mMeasurement.getMuscle_mass(),
                mMeasurement.getFloat_weight(),
                mMeasurement.getCreated_at()
        );
        call.enqueue(new Callback<Measurement>() {
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                Measurement body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.MESSUREMENTS);
                } else {
                    //200
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.MESSUREMENTS);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.MESSUREMENTS);
            }
        });
    }


    public void measurements_mass() {

        ParamsBody[] paramsBodies = getParamsBodies();
        if (paramsBodies.length > 0) {

            ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, true);
            Call<ArrayList<Measurement>> call = downloadService.measurements_mass(paramsBodies);
            call.enqueue(new Callback<ArrayList<Measurement>>() {
                @Override
                public void onResponse(Call<ArrayList<Measurement>> call, Response<ArrayList<Measurement>> response) {
                    ArrayList<Measurement> body = response.body();
                    if (body == null) {
                        //404 or the response cannot be converted to User.
                        String textError = "Error data";
                        ResponseBody responseBody = response.errorBody();
                        if (responseBody != null) {
                            loggerE("error loginToServer " + responseBody.toString());
                            textError = getTextMessage(responseBody);
                        }
                        showMessage(call, textError, ApiConstants.MESSUREMENTS_MASS);
                    } else {
                        //200
                        event = new NetworkResponseEvent();
                        event.setData(body);
                        event.setId(ApiConstants.MESSUREMENTS_MASS);
                        event.setSucess(true);
                        mCallBackServiceGenerator.requestCallBack(event);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Measurement>> call, Throwable t) {
                    showMessageFailure(call, t, ApiConstants.MESSUREMENTS_MASS);
                }
            });
        } else {
            showMessage(null, "", ApiConstants.MESSUREMENTS_MASS);
        }
    }


    public void changePassword(String oldPass, String newPass, String newPass_2) {
        // for get authToken
        getProfile(new Profile());

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, true);
        Call<UserApi> call = downloadService.password(newPass, oldPass, newPass_2);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                UserApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.CHANGE_PASS);

                } else {
                    //200
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.CHANGE_PASS);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.CHANGE_PASS);
            }
        });
    }


    public void updateProfile() {
        Profile mProfileApi = null;

        mProfileApi = getProfile(mProfileApi);

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, true);
        Call<ProfileApi> call = downloadService.updateProfile(
                mProfileApi.getId(),
                mProfileApi.getUser_id(),
                mProfileApi.getActivity_type(),
                mProfileApi.getHeight(),
                mProfileApi.getGender(),
                mProfileApi.getAge(),
                mProfileApi.getCreated_at(),
                mProfileApi.getUpdated_at()
        );
        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                ProfileApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.UPDATE_PROFILE);
                } else {
                    //200
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.UPDATE_PROFILE);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.UPDATE_PROFILE);
            }
        });
    }


    public void profileSync() {

        UserLibr userLibr = getUserLibr();

        String data = "data[";
        Map<String, Object> productMap = new LinkedHashMap<>();
        for (int j = 0; j < userLibr.getProfiles().size(); j++) {
            Profile profile = userLibr.getProfiles().get(j);
            productMap.put(data + Integer.toString(j) + "][id]", profile.getId());
            productMap.put(data + Integer.toString(j) + "][user_id]", profile.getUser_id());
            productMap.put(data + Integer.toString(j) + "][activity_type]", profile.getActivity_type() == 0 ? 1 : profile.getActivity_type());
            productMap.put(data + Integer.toString(j) + "][height]", profile.getHeight() == 0 ? 170 : profile.getHeight());
            productMap.put(data + Integer.toString(j) + "][gender]", profile.getGender() == 0 ? 1 : profile.getGender());
            productMap.put(data + Integer.toString(j) + "][age]", profile.getAge() == 0 ? 25 : profile.getAge());
            productMap.put(data + Integer.toString(j) + "][created_at]", profile.getCreated_at());
            productMap.put(data + Integer.toString(j) + "][updated_at]", profile.getUpdated_at());
            productMap.put(data + Integer.toString(j) + "][number]", profile.getNumber());
            productMap.put(data + Integer.toString(j) + "][is_current]", profile.is_current() ? 1 : 0);
        }

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, true);
        Call<UserLibr> call = downloadService.profileSync(
                SettingsApp.getInstance().getMetric() ? 0 : 1,
                SettingsApp.getInstance().getAutoLogin() ? 1 : 0,
                SettingsApp.getInstance().isThemeDark() ? 1 : 0,
                SettingsApp.getInstance().getLanguages(),
                SettingsApp.getInstance().getProfileBLE(),
                productMap
        );
        call.enqueue(new Callback<UserLibr>() {
            @Override
            public void onResponse(Call<UserLibr> call, Response<UserLibr> response) {
                UserLibr body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    String textError = "Error data";
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                        textError = getTextMessage(responseBody);
                    }
                    showMessage(call, textError, ApiConstants.USER_SUNS);
                } else {
                    //200
                    event = new NetworkResponseEvent();
                    event.setData(body);
                    event.setId(ApiConstants.USER_SUNS);
                    event.setSucess(true);
                    mCallBackServiceGenerator.requestCallBack(event);
                }
            }

            @Override
            public void onFailure(Call<UserLibr> call, Throwable t) {
                showMessageFailure(call, t, ApiConstants.USER_SUNS);
            }
        });
    }

    public void updateAlarm() {

        // TODO: 17.11.2016 надо сделать обновлегние будильника
    }

//============================

    private void showMessageFailure(Call mCall, Throwable t, int mAppiConstant) {
        event = new NetworkResponseEvent();
        event.setSucess(false);
        event.setId(mAppiConstant);
        event.setData(t.getMessage());
        if (t instanceof UnknownHostException) {
            Toast.makeText(App.getContext(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            event.setData(App.getContext().getString(R.string.check_internet));
        }
        logger("Throwable ::: " + t.getMessage());
        logger("Call ::: " + mCall.toString());

        mCallBackServiceGenerator.requestFailed(event);
    }

    private void showMessage(Call mCall, String text, int mApiConstant) {
        event = new NetworkResponseEvent();
        event.setSucess(false);
        event.setId(mApiConstant);
//            Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT).show();
        event.setData(text);

        logger("Call ::: " + mCall.toString());

        mCallBackServiceGenerator.requestFailed(event);
    }


    private String getTextMessage(ResponseBody mResponseBody) {
        String textError = mResponseBody.toString();
        try {
            textError = mResponseBody.string();
        } catch (IOException mE) {
            mE.printStackTrace();
        }
        if (textError.contains("message")) {
            textError = textError.substring(textError.lastIndexOf(":") + 1);
            int last = textError.length() - 1;
            textError = textError.substring(0, last);
        }
        return textError;
    }
//============================
//{"status":"fail","code":422,"message":"The data.0.is_current field must be true or false.","data":{"is_imperial":"false","keep_login":"true","theme":"0","language":"en","profile_number":"1","data":[{"id":"75","user_id":"46","activity_type":"1","height":"231","gender":"1","age":"19","created_at":"1479706076","updated_at":"1479709874","number":"1","is_current":"true"},{"id":"76","user_id":"46","activity_type":"1","height":"170","gender":"1","age":"25","created_at":"1479706076","updated_at":"1479706076","number":"2","is_current":"false"}]}}

    private Profile getProfile(Profile mMProfileApi) {
        UserLibr userByMail = RealmObj.getInstance().getUserByMail(SettingsApp.getInstance().getUserName());
        authToken = userByMail.getToken();
        RealmList<Profile> profiles = userByMail.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);
            if (profile.getNumber() == SettingsApp.getInstance().getProfileBLE()) {
                mMProfileApi = profile;
            }
        }
        return mMProfileApi;
    }


    private UserLibr getUserLibr() {
        UserLibr userByMail = RealmObj.getInstance().getUserByMail(SettingsApp.getInstance().getUserName());
        authToken = userByMail.getToken();

        return userByMail;
    }


    private ParamsBody[] getParamsBodies() {
        return new ParamsBody[0];
    }


//============================

    public interface CallBackServiceGenerator {

        void requestCallBack(NetworkResponseEvent event);

        void requestFailed(NetworkResponseEvent event);

    }
//===============================
}

