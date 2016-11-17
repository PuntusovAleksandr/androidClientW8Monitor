package com.lucerotech.aleksandrp.w8monitor.api;

import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.App;
import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.api.event.NetworkResponseEvent;
import com.lucerotech.aleksandrp.w8monitor.api.model.Measurement;
import com.lucerotech.aleksandrp.w8monitor.api.model.ProfileApi;
import com.lucerotech.aleksandrp.w8monitor.api.model.UserApi;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;

import java.net.UnknownHostException;

import okhttp3.OkHttpClient;
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


    //    public static final String API_BASE_URL = "https://w8.rockettaxi.ru/";
    public static final String API_BASE_URL = "https://w8.dev/v1/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private String userName = SettingsApp.getInstance().getUserName();
    private String userPassword = SettingsApp.getInstance().getUserPassword();
    private int profileBLE = SettingsApp.getInstance().getProfileBLE();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private CallBackServiceGenerator mCallBackServiceGenerator;

    public ServiceGenerator(CallBackServiceGenerator mCallBackServiceGenerator) {
        this.mCallBackServiceGenerator = mCallBackServiceGenerator;
    }

    private static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }


    /**
     * login
     */
    public void loginToServer() {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class);
        Call<UserApi> call = downloadService.login(userName, userPassword);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                UserApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginToServer " + responseBody.toString());
                    }
                } else {
                    //200
                    // TODO: 17.11.2016 lrkdtv дальше обработку
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                showMessage(call, t);
            }
        });
    }

    /**
     * login witch social network
     *
     * @param idSocialNetwork
     */
    public void loginSocialToServer(String idSocialNetwork) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class);
        Call<UserApi> call = downloadService.loginSocial(userName, idSocialNetwork);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                UserApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error loginSocialToServer " + responseBody.toString());
                    }
                } else {
                    //200
                    // TODO: 17.11.2016 lrkdtv дальше обработку
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                showMessage(call, t);
            }
        });
    }

    /**
     * register
     */
    public void registerToServer() {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class);
        Call<UserApi> call = downloadService.register(userName, userPassword);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                UserApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error registerToServer " + responseBody.toString());
                    }
                } else {
                    //200
                    // TODO: 17.11.2016 lrkdtv дальше обработку
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                showMessage(call, t);
            }
        });
    }

    /**
     * create profile
     *
     * @param mProfileApi
     */
    public void profileCreateToServer(ProfileApi mProfileApi) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class);
        Call<ProfileApi> call = downloadService.profileCreate(
                profileBLE,
                mProfileApi.getActivity_type(),
                mProfileApi.getHeight(),
                mProfileApi.getBirthday(),
                mProfileApi.getGender()
        );
        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                ProfileApi body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error profileCreateToServer " + responseBody.toString());
                    }
                } else {
                    //200
                    // TODO: 17.11.2016 lrkdtv дальше обработку
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                showMessage(call, t);
            }
        });
    }


    public void sendMeasurementsToServer(Measurement mMeasurement) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class);
        Call<Measurement> call = downloadService.measurements(
                mMeasurement.getBmi(),
                mMeasurement.getBody_water(),
                mMeasurement.getBone_mass(),
                mMeasurement.getCalories(),
                mMeasurement.getFat(),
                mMeasurement.getFat_level(),
                mMeasurement.getMuscule_mass(),
                mMeasurement.getFloat_weight(),
                mMeasurement.getCreated_at()
        );
        call.enqueue(new Callback<Measurement>() {
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                Measurement body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error sendMeasurementsToServer " + responseBody.toString());
                    }
                } else {
                    //200
                    // TODO: 17.11.2016 lrkdtv дальше обработку
                }
            }

            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                showMessage(call, t);
            }
        });
    }


    public void measurements_mass() {

        // TODO: 17.11.2016 здесь нужно сделать массив
    }


    public void changePassword(String newPassword) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class);
        Call<Object> call = downloadService.password(userPassword, newPassword, newPassword);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Object body = response.body();
                if (body == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error changePassword " + responseBody.toString());
                    }
                } else {
                    //200
                    // TODO: 17.11.2016 lrkdtv дальше обработку
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showMessage(call, t);
            }
        });
    }


    public void updateProfile(ProfileApi mProfileApi) {

        ServiceApi downloadService = ServiceGenerator.createService(ServiceApi.class);
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
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        loggerE("error updateProfile " + responseBody.toString());
                    }
                } else {
                    //200
                    // TODO: 17.11.2016 lrkdtv дальше обработку
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                showMessage(call, t);
            }
        });
    }

    public void updateAlarm() {

        // TODO: 17.11.2016 надо сделать обновлегние будильника
    }

//============================

    private void showMessage(Call mCall, Throwable t) {
        NetworkResponseEvent event = new NetworkResponseEvent();
        event.setData(t.getMessage());
        if (t instanceof UnknownHostException) {
            Toast.makeText(App.getContext(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            event.setData(App.getContext().getString(R.string.check_internet));
        }
        logger("Call ::: " + mCall.toString());
        logger("Throwable ::: " + t.getMessage());

        mCallBackServiceGenerator.requestFailed(event);
    }

//============================

    public interface CallBackServiceGenerator {

        void requestCallBack(NetworkResponseEvent event);

        void requestFailed(NetworkResponseEvent event);

    }

}

