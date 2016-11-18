package com.lucerotech.aleksandrp.w8monitor.api;

import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.App;
import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.api.constant.ApiConstants;
import com.lucerotech.aleksandrp.w8monitor.api.event.NetworkResponseEvent;
import com.lucerotech.aleksandrp.w8monitor.api.model.Measurement;
import com.lucerotech.aleksandrp.w8monitor.api.model.ProfileApi;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.Profile;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import java.io.IOException;
import java.net.UnknownHostException;
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
                mProfileApi.getBirthday(),
                mProfileApi.getBirthday(),
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


    public void sendMeasurementsToServer(Measurement mMeasurement) {

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

        // TODO: 17.11.2016 здесь нужно сделать массив
    }


    public void changePassword(String oldPass, String newPass, String newPass_2) {
        // for get authToken
        getProfile(new Profile());

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class, true);
        Call<Object> call = downloadService.password(newPass, oldPass, newPass_2);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Object body = response.body();
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
            public void onFailure(Call<Object> call, Throwable t) {
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
                mProfileApi.getBirthday(),
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


//============================

    public interface CallBackServiceGenerator {

        void requestCallBack(NetworkResponseEvent event);

        void requestFailed(NetworkResponseEvent event);

    }
//===============================
}

