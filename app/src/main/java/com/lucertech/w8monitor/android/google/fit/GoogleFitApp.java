package com.lucertech.w8monitor.android.google.fit;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.lucertech.w8monitor.android.activity.MainActivity;
import com.lucertech.w8monitor.android.d_base.RealmObj;
import com.lucertech.w8monitor.android.d_base.model.ParamsBody;
import com.lucertech.w8monitor.android.d_base.model.Profile;
import com.lucertech.w8monitor.android.d_base.model.UserLibr;
import com.lucertech.w8monitor.android.utils.SettingsApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;
import io.realm.RealmResults;

import static com.lucertech.w8monitor.android.activity.MainActivity.TAG_GOOGLE_FIT;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.REQUEST_OAUTH;

/**
 * Created by AleksandrP on 25.11.2016.
 */

public class GoogleFitApp implements SendDataGoogleFitService.UpdateData {

    private Context mContext;
    private MainActivity mActivity;

    private SendDataGoogleFitService service;

    private GoogleApiClient mClient;
    private boolean authInProgress;

    private long endTime, startTime;


    public GoogleFitApp(Context mContext) {
        this.mContext = mContext;
        this.mActivity = (MainActivity) mContext;
    }


    // [START auth_build_googleapiclient_beginning]
    // This ensures that if the user denies the permissions then uses Settings to re-enable
    // them, the app will start working.

    /**
     * Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     * to connect to Fitness APIs. The scopes included should match the scopes your app needs
     * (see documentation for details). Authentication will occasionally fail intentionally,
     * and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     * can address. Examples of this include the user never having signed in before, or having
     * multiple accounts on the device and needing to specify which account to use, etc.
     */
    public void buildFitnessClient() {
        if (mClient == null) {
            makeTime();
            mClient = new GoogleApiClient.Builder(mContext)
                    .addApi(Fitness.HISTORY_API)
                    .addApi(Fitness.CONFIG_API)
                    .addApi(Fitness.SESSIONS_API)
                    .addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                    .addScope(Fitness.SCOPE_BODY_READ_WRITE)
                    .addScope(Fitness.SCOPE_LOCATION_READ_WRITE)
                    .addScope(Fitness.SCOPE_NUTRITION_READ_WRITE)
                    .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                    .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
                    .addConnectionCallbacks(
                            new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    Log.i(TAG_GOOGLE_FIT, "Connected!!!");
                                    // Now you can make calls to the Fitness APIs.

                                    // delete all data from googleFit
                                    deleteAllParams();

                                    setConfigParams();
                                    findFitnessDataSources();
                                    sendDataFromDB();
                                }

                                @Override
                                public void onConnectionSuspended(int i) {
                                    // If your connection to the sensor gets lost at some point,
                                    // you'll be able to determine the reason and react to it here.
                                    if (i ==
                                            GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                        Log.i(TAG_GOOGLE_FIT, "Connection lost.  Cause: Network Lost.");
                                    } else if (i ==
                                            GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                        Log.i(TAG_GOOGLE_FIT,
                                                "Connection lost.  Reason: Service Disconnected");
                                    }
                                }
                            }
                    )
                    .enableAutoManage(mActivity, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.i(TAG_GOOGLE_FIT, "Google Play services connection failed. Cause: " +
                                    result.toString());

                            if (!result.hasResolution()) {
                                GooglePlayServicesUtil.getErrorDialog(
                                        result.getErrorCode(), mActivity, 0).show();
                                return;
                            }

                            if (!authInProgress) {
                                try {
                                    authInProgress = true;
                                    result.startResolutionForResult(mActivity, REQUEST_OAUTH);
                                } catch (IntentSender.SendIntentException e) {
                                    Log.i(TAG_GOOGLE_FIT, "IntentSender.SendIntentException " + e.getMessage());
                                }
                            }
                        }
                    })
                    .build();
        }
    }
    // [END auth_build_googleapiclient_beginning]

    private void setConfigParams() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long startTime = cal.getTimeInMillis();


        DataSource dataSource = new DataSource.Builder()
                .setAppPackageName(mActivity)
                .setDataType(DataType.TYPE_HEIGHT)
                .setType(DataSource.TYPE_RAW)
                .build();

        DataSet heightDataSet = new DataSet(dataSource);
        DataPoint dataPoint = heightDataSet
                .createDataPoint()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        final Profile profile = getProfile();

        dataPoint.getValue(Field.FIELD_HEIGHT).getFormat();
        dataPoint = dataPoint.setFloatValues(profile.getHeight() / 100f);

        heightDataSet.add(dataPoint);
        Fitness.HistoryApi.insertData(mClient, heightDataSet).
                setResultCallback(
                        new ResolvingResultCallbacks<Status>(mActivity, 0) {
                            @Override
                            public void onSuccess(Status status) {
                                if (status.isSuccess()) {
                                    Log.i(TAG_GOOGLE_FIT, "MAKE SET HEIGHT = " + profile.getHeight() / 100f);
                                }
                            }

                            @Override
                            public void onUnresolvableFailure(Status status) {

                            }
                        }
                );
    }


    private void makeTime() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        startTime = cal.getTimeInMillis();
    }

    /**
     * Find available data sources and attempt to register on a specific {@link DataType}.
     * If the application cares about a data type but doesn't care about the source of the data,
     * this can be skipped entirely, instead calling
     * {@link com.google.android.gms.fitness.HistoryApi
     * #register(GoogleApiClient, SensorRequest, DataSourceListener)},
     * where the {@link HistoryApi} contains the desired data type.
     */
    private void findFitnessDataSources() {
        // [START find_data_sources]
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_BODY_FAT_PERCENTAGE, DataType.AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY)
                .aggregate(DataType.TYPE_WEIGHT, DataType.AGGREGATE_WEIGHT_SUMMARY)
                .aggregate(DataType.TYPE_BASAL_METABOLIC_RATE, DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY)
                .aggregate(DataType.TYPE_HYDRATION, DataType.AGGREGATE_HYDRATION)
                .bucketByTime(365, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .enableServerQueries()      // for read global data
                .build();
        long startTime_ = 1;
        DataReadRequest readHeightRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_HEIGHT)
                .setTimeRange(startTime_, endTime, TimeUnit.MILLISECONDS)
                .setLimit(1)
                .enableServerQueries()      // for read global data
                .build();

//create call back
        ResultCallback<DataReadResult> resultCallback = new ResultCallback<DataReadResult>() {
            @Override
            public void onResult(@NonNull DataReadResult dataReadResult) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
                if (dataReadResult.getBuckets().size() > 0) {
                    Log.i(TAG_GOOGLE_FIT, "DataSet.size(): "
                            + dataReadResult.getBuckets().size());
                    for (Bucket bucket : dataReadResult.getBuckets()) {
                        List<DataSet> dataSets = bucket.getDataSets();
                        for (DataSet dataSet : dataSets) {
                            Log.i(TAG_GOOGLE_FIT, "dataSet.dataType: " + dataSet.getDataType().getName());

                            for (DataPoint dp : dataSet.getDataPoints()) {
                                describeDataPoint(dp, dateFormat);
                            }
                        }
                    }
                } else if (dataReadResult.getDataSets().size() > 0) {
                    Log.i(TAG_GOOGLE_FIT, "dataSet.size(): " + dataReadResult.getDataSets().size());
                    for (DataSet dataSet : dataReadResult.getDataSets()) {
                        Log.i(TAG_GOOGLE_FIT, "dataType: " + dataSet.getDataType().getName());

                        for (DataPoint dp : dataSet.getDataPoints()) {
                            describeDataPoint(dp, dateFormat);
                        }
                    }
                }
            }
        };

//set call back
        Fitness.HistoryApi.readData(mClient, readHeightRequest).setResultCallback(resultCallback);
        Fitness.HistoryApi.readData(mClient, readRequest).setResultCallback(resultCallback);

        // [END find_data_sources]
    }

    public void describeDataPoint(DataPoint dp, DateFormat dateFormat) {
        String msg = "dataPoint__" + "\n"
                + "\ttype: " + dp.getDataType().getName() + "\n"
                + ",\t range: [" + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + " - " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)) + "]\n"
                + ",\t fields: [";

        for (Field field : dp.getDataType().getFields()) {
            msg += field.getName() + " value = " + dp.getValue(field) + " ";
        }

        msg += "]";
        Log.i(TAG_GOOGLE_FIT, msg);
    }


    private void describeDataType(DataType mCustomType) {

        String msg = "dataPoint__" + "\n"
                + "type: " + mCustomType.getName() + "\n"
                + ", fields: [";

        for (Field field : mCustomType.getFields()) {
            msg += field.getName() + " = " + field.toString() + " ";
        }

        msg += "]";
        Log.i(TAG_GOOGLE_FIT, msg);

    }


    public void onDestroy() {
        if (mClient.isConnected()) {
            mClient.disconnect();
        }
    }

    /**
     * Answer from onActivityResult
     *
     * @param mRequestCode
     */
    public void requestOauth(int mRequestCode) {
        authInProgress = false;
//            make connect GOOGLE FIT
        Log.i(TAG_GOOGLE_FIT, "onActivityResult: REQUEST_OAUTH");
        if (mRequestCode == Activity.RESULT_OK) {
            // Make sure the app is not already connected or attempting to connect
            if (!mClient.isConnecting() && !mClient.isConnected()) {
                Log.i(TAG_GOOGLE_FIT, "onActivityResult: client.connect()");
                mClient.connect();
            }
        }
    }


    // call synchronization with fGOOGLE_FIT
    public void connect() {
        if (!authInProgress && mClient != null)
            mClient.connect();
    }


    /**
     * delete data from google fit from db
     */
    public void deleteAllParams() {
        Profile profile = getProfile();
        RealmResults<ParamsBody> dataUserForGoogleFit = getUserData(profile.getId());
        service =
                new SendDataGoogleFitService(mContext, mClient, dataUserForGoogleFit, this);

        service.deleteWeight();
        service.deleteAllCalories();
        service.deleteAllWater();
        service.deleteAllFat();
    }

    /**
     * send data to google fit from db
     */
    public void sendDataFromDB() {
        Profile profile = getProfile();
        RealmResults<ParamsBody> dataUserForGoogleFit = getUserData(profile.getId());
        service =
                new SendDataGoogleFitService(mContext, mClient, dataUserForGoogleFit, this);

        service.sendWeight();
        service.sendCalories();
        service.sendWater();
        service.sendFat();
    }

    private RealmResults<ParamsBody> getUserData(int mIdProfile) {
        return RealmObj.getInstance().getDataUserForGoogleFit(mIdProfile);
    }

    private Profile getProfile() {
        String userName = SettingsApp.getInstance().getUserName();
        boolean metric = SettingsApp.getInstance().getMetric();
        int profileBLE = SettingsApp.getInstance().getProfileBLE();

        UserLibr userByMail = RealmObj.getInstance().getUserByMail(userName);
        RealmList<Profile> profiles = userByMail.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);
            if (profile.getNumber() == profileBLE) {
                return profile;
            }
        }
        return null;
    }


    //    ===============================================
//            from UpdateData
//    ===============================================
    @Override
    public void needMakeUpdateData() {

    }
}
