package com.w8.w8monitor.android.api.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.w8.w8monitor.android.api.ServiceGenerator;
import com.w8.w8monitor.android.api.constant.ApiConstants;
import com.w8.w8monitor.android.api.event.NetworkResponseEvent;
import com.w8.w8monitor.android.api.event.UpdateUiEvent;

import org.greenrobot.eventbus.EventBus;

import static com.w8.w8monitor.android.utils.STATICS_PARAMS.EXTRA_TIMESTAMP;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.EXTRA_TIME_CREATE;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_MAIL;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASS;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASSWORD_NEW;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASSWORD_NEW_CONFIRM;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_PASSWORD_OLS;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SERVICE_QUESTIONS;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SOCIAL_ID;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.SOCIAL_NAME;

public class ApiService extends Service implements
        ServiceGenerator.CallBackServiceGenerator {


    private int startId;
    private ServiceGenerator userInteractor;


    public ApiService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userInteractor = new ServiceGenerator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.startId = startId;
        int jobId = intent.getIntExtra(SERVICE_JOB_ID_TITLE, -1);

        String mail = intent.getStringExtra(SERVICE_MAIL);
        String pass = intent.getStringExtra(SERVICE_PASS);

        switch (jobId) {
            case ApiConstants.LOGIN:
                userInteractor.loginToServer(mail, pass);
                break;
            case ApiConstants.LOGIN_SOCIAL:
                String socialId = intent.getStringExtra(SOCIAL_ID);
                String socialName = intent.getStringExtra(SOCIAL_NAME);
                userInteractor.loginSocialToServer(mail, socialId, socialName);
                break;
            case ApiConstants.REGISTER:
                userInteractor.registerToServer(mail, pass);
                break;
            case ApiConstants.PROFILE:
                userInteractor.profileCreateToServer();
                break;
            case ApiConstants.MEASUREMENTS:
                long time = intent.getLongExtra(EXTRA_TIME_CREATE, 0);
                userInteractor.sendMeasurementsToServer(time);
                break;
            case ApiConstants.MEASUREMENTS_MASS:
                userInteractor.measurements_mass();
                break;
            case ApiConstants.ALL_MEASUREMENTS:
                userInteractor.getAlldMeasurementsFromServer();
                break;
            case ApiConstants.ALL_MEASUREMENTS_TIME:
                String timestamp = intent.getStringExtra(EXTRA_TIMESTAMP);
                userInteractor.getAlldMeasurementsFromServerTime(timestamp);
                break;
            case ApiConstants.CHANGE_PASS:
                String oldPass = intent.getStringExtra(SERVICE_PASSWORD_OLS);
                String newPass = intent.getStringExtra(SERVICE_PASSWORD_NEW);
                String newPass_2 = intent.getStringExtra(SERVICE_PASSWORD_NEW_CONFIRM);
                userInteractor.changePassword(oldPass, newPass, newPass_2);
                break;
            case ApiConstants.UPDATE_PROFILE:
                userInteractor.updateProfile();
                break;
            case ApiConstants.USER_SUNS:
                userInteractor.profileSync();
                break;
            case ApiConstants.ALARM_UPDATE:
                userInteractor.updateAlarm();
                break;
            case ApiConstants.RESET_PASSWORD:
                userInteractor.resetPassword(mail);
                break;
            case ApiConstants.SUPPORT_API:
                String description = intent.getStringExtra(SERVICE_QUESTIONS);
                userInteractor.sendQuestion(mail, description);
                break;
        }
        return START_NOT_STICKY;
    }

//================================
//        from CallBackServiceGenerator
//================================

    @Override
    public void requestCallBack(NetworkResponseEvent event) {
        UpdateUiEvent updateUiEvent = new UpdateUiEvent();
        updateUiEvent.setSucess(event.isSucess());
        switch (event.getId()) {
            case ApiConstants.LOGIN:
                updateUiEvent.setId(UpdateUiEvent.LOGIN);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.LOGIN_SOCIAL:
                updateUiEvent.setId(UpdateUiEvent.LOGIN_SOCIAL);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.REGISTER:
                updateUiEvent.setId(UpdateUiEvent.REGISTER);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.PROFILE:
                updateUiEvent.setId(UpdateUiEvent.PROFILE);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.MEASUREMENTS:
                updateUiEvent.setId(UpdateUiEvent.MEASUREMENTS);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.ALL_MEASUREMENTS:
            case ApiConstants.ALL_MEASUREMENTS_TIME:
                updateUiEvent.setId(UpdateUiEvent.ALL_MEASUREMENTS);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.MEASUREMENTS_MASS:
                updateUiEvent.setId(UpdateUiEvent.MEASUREMENTS_SUNS);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.CHANGE_PASS:
                updateUiEvent.setId(UpdateUiEvent.CHANGE_PASS);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.UPDATE_PROFILE:
                updateUiEvent.setId(UpdateUiEvent.UPDATE_PROFILE);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.ALARM_UPDATE:
                updateUiEvent.setId(UpdateUiEvent.ALARM_UPDATE);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.USER_SUNS:
                updateUiEvent.setId(UpdateUiEvent.USER_SUNS);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.MEASUREMENTS_MASS_UPDATE:
                updateUiEvent.setId(UpdateUiEvent.MEASUREMENTS_MASS_UPDATE);
                updateUiEvent.setData(event.getData());
                break;
            case ApiConstants.RESET_PASSWORD:
                updateUiEvent.setId(UpdateUiEvent.RESET_PASSWORD);
                break;
            case ApiConstants.SUPPORT_API:
                updateUiEvent.setId(UpdateUiEvent.RESPONSE_SUPPORT_API);
                break;
        }

        if (updateUiEvent != null) {
//            BusProvider.send(updateUiEvent);
            EventBus.getDefault().post(updateUiEvent);
        }
        stopSelf(startId);
    }

    @Override
    public void requestFailed(NetworkResponseEvent event) {
        System.out.println("ERROR 00000000000 " + event.getData() + " :: " + event.getId() + " ЖЖ " + event.toString());
        UpdateUiEvent networkFailEvent = new UpdateUiEvent();
        networkFailEvent.setSucess(false);
        networkFailEvent.setId(event.getId());
        networkFailEvent.setData(event.getData());
//        BusProvider.send(networkFailEvent);
        EventBus.getDefault().post(networkFailEvent);
        stopSelf(startId);
    }
}
