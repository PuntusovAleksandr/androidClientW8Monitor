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
import com.google.android.gms.fitness.request.DataUpdateRequest;
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

//    ================================================
//            SEND
//    ================================================

    public void sendWeight() {
        dataSource = new DataSource.Builder()
                .setAppPackageName(mContext)
                .setDataType(DataType.TYPE_WEIGHT)
                .setName("WEIGHT w8m")
                .setType(DataSource.TYPE_RAW)
                .build();

        for (int i = 0; i < mDataUserForGoogleFit.size(); i++) {
            ParamsBody paramsBody = mDataUserForGoogleFit.get(i);
            final long endTimeData = paramsBody.getDate_time() * 1000;

            Calendar cal = Calendar.getInstance();
            Date now = new Date(endTimeData);
            cal.setTime(now);
            cal.add(Calendar.MINUTE, 0);
            final long endTime = cal.getTimeInMillis();
            cal.add(Calendar.MINUTE, -50);
            final long startTime = cal.getTimeInMillis();

            final float weightSet = paramsBody.getWeight();
            final DataSet dataSetWeight = DataSet.create(dataSource);
            DataPoint point = dataSetWeight
                    .createDataPoint()
                    .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
            point.getValue(Field.FIELD_WEIGHT).setFloat(weightSet);
            dataSetWeight.add(point);

            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... mObjects) {

                    DataUpdateRequest request = new DataUpdateRequest.Builder()
                            .setDataSet(dataSetWeight)
                            .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                            .build();
                    Fitness.HistoryApi.updateData(mClient, request).await(1, TimeUnit.MINUTES);

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


    /**
     * send calories to google fit
     */
    public void sendCalories() {
        dataSource = new DataSource.Builder()
                .setAppPackageName(mContext)
                .setDataType(DataType.TYPE_CALORIES_EXPENDED)
                .setName("CALORIES_EXPENDED w8m")
                .setType(DataSource.TYPE_RAW)
                .build();

        if (mDataUserForGoogleFit.size() > 1) {
            for (int i = 0; i < mDataUserForGoogleFit.size(); i++) {
                if (i == 0) continue;

                ParamsBody paramsBody = mDataUserForGoogleFit.get(i);
                ParamsBody preParamsBody = mDataUserForGoogleFit.get(i - 1);
                final long endTimeData = paramsBody.getDate_time() * 1000;
                final float calories = (paramsBody.getEmr() - preParamsBody.getEmr() * 1f);

                Calendar cal = Calendar.getInstance();
                Date now = new Date(endTimeData);
                cal.setTime(now);
                cal.add(Calendar.MINUTE, 0);
                final long endTime = cal.getTimeInMillis();
                now = new Date(preParamsBody.getDate_time());
                cal.setTime(now);
                final long startTime = cal.getTimeInMillis();

                final DataSet dataSetWeight = DataSet.create(dataSource);
                DataPoint point = dataSetWeight
                        .createDataPoint()
                        .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                point.getValue(Field.FIELD_CALORIES).setFloat(calories);
                try {
                    dataSetWeight.add(point);
                } catch (IllegalArgumentException mE) {
                    mE.printStackTrace();
                    continue;
                }
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... mObjects) {
                        DataUpdateRequest request = new DataUpdateRequest.Builder()
                                .setDataSet(dataSetWeight)
                                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                                .build();
                        Fitness.HistoryApi.updateData(mClient, request).await(1, TimeUnit.MINUTES);
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
    }

    /**
     * SEND water to google fit
     */
    public void sendWater() {
        dataSource = new DataSource.Builder()
                .setAppPackageName(mContext)
                .setDataType(DataType.TYPE_HYDRATION)
                .setName("CALORIES_HYDRATION w8m")
                .setType(DataSource.TYPE_RAW)
                .build();
        if (mDataUserForGoogleFit.size() > 1) {
            for (int i = 0; i < mDataUserForGoogleFit.size(); i++) {
                if (i == 0) continue;

                ParamsBody paramsBody = mDataUserForGoogleFit.get(i);
                ParamsBody preParamsBody = mDataUserForGoogleFit.get(i - 1);
                final long endTimeData = paramsBody.getDate_time() * 1000;
                final float water = paramsBody.getWater() - preParamsBody.getWater();

                Calendar cal = Calendar.getInstance();
                Date now = new Date(endTimeData);
                cal.setTime(now);
                cal.add(Calendar.MINUTE, 0);
                final long endTime = cal.getTimeInMillis();
                now = new Date(preParamsBody.getDate_time());
                cal.setTime(now);
                final long startTime = cal.getTimeInMillis();

                final DataSet dataSetWeight = DataSet.create(dataSource);

                DataPoint point = dataSetWeight
                        .createDataPoint()
                        .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                point.getValue(Field.FIELD_VOLUME).setFloat(water);
                dataSetWeight.add(point);
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... mObjects) {
                        DataUpdateRequest request = new DataUpdateRequest.Builder()
                                .setDataSet(dataSetWeight)
                                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                                .build();
                        Fitness.HistoryApi.updateData(mClient, request).await(1, TimeUnit.MINUTES);
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
    }

    /**
     * Send fat to google fit
     */
    public void sendFat() {
        dataSource = new DataSource.Builder()
                .setAppPackageName(mContext)
                .setDataType(DataType.TYPE_BODY_FAT_PERCENTAGE)
                .setName("FAT_PERCENTAGE w8m")
                .setType(DataSource.TYPE_RAW)
                .build();
        if (mDataUserForGoogleFit.size() > 0) {
            for (int i = 0; i < mDataUserForGoogleFit.size(); i++) {
                if (i == 0) continue;

                ParamsBody paramsBody = mDataUserForGoogleFit.get(i);
                ParamsBody preParamsBody = mDataUserForGoogleFit.get(i - 1);
                final long endTimeData = paramsBody.getDate_time() * 1000 ;
                final float fat = paramsBody.getFat() * 1f;

                Calendar cal = Calendar.getInstance();
                Date now = new Date(endTimeData);
                cal.setTime(now);
                cal.add(Calendar.MINUTE, 0);
                final long endTime = cal.getTimeInMillis();
                now = new Date(preParamsBody.getDate_time());
                cal.setTime(now);
                final long startTime = cal.getTimeInMillis();

                final DataSet dataSetWeight = DataSet.create(dataSource);
                DataPoint point = dataSetWeight
                        .createDataPoint()
                        .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                point.setFloatValues(fat);
                try {
                    dataSetWeight.add(point);
                } catch (IllegalArgumentException mE) {
                    mE.printStackTrace();
                    continue;
                }
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... mObjects) {
                        DataUpdateRequest request = new DataUpdateRequest.Builder()
                                .setDataSet(dataSetWeight)
                                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                                .build();
                        Fitness.HistoryApi.updateData(mClient, request).await(1, TimeUnit.MINUTES);
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
    }
    //    ================================================
//            DELETE
//    ================================================

    /**
     * delete all weight user
     */
    public void deleteWeight() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long mEndTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long mStartTime = cal.getTimeInMillis();

        // need delete all data w8m
        final DataDeleteRequest request = new DataDeleteRequest.Builder()
                .setTimeInterval(mStartTime, mEndTime, TimeUnit.MILLISECONDS)
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


    /**
     * delete all calories from google fit
     */
    public void deleteAllCalories() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long mEndTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long mStartTime = cal.getTimeInMillis();

        // need delete all data w8m
        final DataDeleteRequest request = new DataDeleteRequest.Builder()
                .setTimeInterval(mStartTime, mEndTime, TimeUnit.MILLISECONDS)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED)
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

    /**
     * delete all params water from google fit
     */
    public void deleteAllWater() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long mEndTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long mStartTime = cal.getTimeInMillis();

        // need delete all data w8m
        final DataDeleteRequest request = new DataDeleteRequest.Builder()
                .setTimeInterval(mStartTime, mEndTime, TimeUnit.MILLISECONDS)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED)
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


    /**
     * delete all params fats from google fit
     */
    public void deleteAllFat() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long startTime = cal.getTimeInMillis();
        // need delete all data w8m
        final DataDeleteRequest request = new DataDeleteRequest.Builder()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .addDataType(DataType.TYPE_BODY_FAT_PERCENTAGE)
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


    public interface UpdateData {
        void needMakeUpdateData();
    }
}
