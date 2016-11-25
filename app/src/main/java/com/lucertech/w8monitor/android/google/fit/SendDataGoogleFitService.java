package com.lucertech.w8monitor.android.google.fit;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.lucertech.w8monitor.android.d_base.model.ParamsBody;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import io.realm.RealmResults;

/**
 * Created by AleksandrP on 25.11.2016.
 */

public class SendDataGoogleFitService {

    private GoogleApiClient mClient;
    private Context mContext;
    private DataSource dataSource;
    private RealmResults<ParamsBody> mDataUserForGoogleFit;

    private UpdateData mListener;

    public SendDataGoogleFitService(Context mContext, GoogleApiClient mClient,
                                    RealmResults<ParamsBody> mDataUserForGoogleFit,
                                    UpdateData mListener) {
        this.mClient = mClient;
        this.mContext = mContext;
        this.mDataUserForGoogleFit = mDataUserForGoogleFit;
        this.mListener = mListener;
    }

    public void sendWeight() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);

        long startTime = cal.getTimeInMillis();
        dataSource = new DataSource.Builder()
                .setAppPackageName(mContext)
                .setDataType(DataType.TYPE_WEIGHT)
                .setName("WEIGHT w8m")
                .setType(DataSource.TYPE_RAW)
                .build();
        final DataSet dataSetWeight = DataSet.create(dataSource);

        for (int i = 0; i < mDataUserForGoogleFit.size(); i++) {
            ParamsBody paramsBody = mDataUserForGoogleFit.get(i);
            final long endTimeData = paramsBody.getDate_time() * 1000 - 1;
            final float weightSet = paramsBody.getWeight();

            DataPoint point = dataSetWeight
                    .createDataPoint()
                    .setTimeInterval(endTimeData, new Date().getTime(), TimeUnit.MILLISECONDS);
            point.getValue(Field.FIELD_WEIGHT).setFloat(weightSet);
            dataSetWeight.add(point);
        }
        if (dataSetWeight.getDataPoints().size() > 0) {
            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... mObjects) {
                    Fitness.HistoryApi.insertData(mClient, dataSetWeight).await(1, TimeUnit.MINUTES);
                    return null;
                }

                @Override
                protected void onPostExecute(Object mO) {
                    super.onPostExecute(mO);
                     mListener.needMakeUpdateData();
                }
            }.execute();
        } else {
            // need delete all data w8m
            final DataDeleteRequest request = new DataDeleteRequest.Builder()
                    .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                    .addDataType(DataType.TYPE_WEIGHT)
                    .build();

            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... mObjects) {
                    Fitness.HistoryApi.deleteData(mClient, request).await(1, TimeUnit.MINUTES);
                    return null;
                }

                @Override
                protected void onPostExecute(Object mO) {
                    super.onPostExecute(mO);
                    mListener.needMakeUpdateData();
                }
            }.execute();
        }
    }

    public void sendHeight(int mHeight) {
        dataSource = new DataSource.Builder()
                .setAppPackageName(mContext)
                .setDataType(DataType.TYPE_HEIGHT)
                .setName("TYPE_HEIGHT w8m")
                .setType(DataSource.TYPE_RAW)
                .build();

        final float weightSet = mHeight / 100f;
        final DataSet dataSetWeight = DataSet.create(dataSource);

        DataPoint point = dataSetWeight
                .createDataPoint()
                .setTimeInterval(1, 1, TimeUnit.MILLISECONDS);
        point.getValue(Field.FIELD_HEIGHT).setFloat(weightSet);
        dataSetWeight.add(point);

        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... mObjects) {
                Fitness.HistoryApi.insertData(mClient, dataSetWeight).await(1, TimeUnit.MINUTES);
                return null;
            }

            @Override
            protected void onPostExecute(Object mO) {
                super.onPostExecute(mO);
                mListener.needMakeUpdateData();
            }
        }.execute();
    }


    public interface UpdateData {
        void needMakeUpdateData();
    }
}
