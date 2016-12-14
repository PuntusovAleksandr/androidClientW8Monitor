/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.os.AsyncTask
 *  android.util.Log
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package com.lefu.es.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.lefu.es.cache.CacheHelper;
import com.lefu.es.db.DatabaseCreatOrUpdateHelper;

public class IwellnessApplication
extends Application {
    public static IwellnessApplication app = null;

    private void initCacheDates() {
        if (CacheHelper.nutrientList == null || CacheHelper.nutrientList.size() == 0) {
            SQLiteDatabase sQLiteDatabase = DatabaseCreatOrUpdateHelper.createOrUpdateDB((Context)this);
            CacheHelper.cacheAllNutrientsDate((Context)this, sQLiteDatabase);
            if (sQLiteDatabase != null) {
                sQLiteDatabase.close();
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        app = this;
        new initAsyncTask().execute((Object[])new Integer[0]);
    }

    class initAsyncTask
    extends AsyncTask<Integer, Integer, Integer> {
        initAsyncTask() {
        }

        protected /* varargs */ Integer doInBackground(Integer ... arrinteger) {
            IwellnessApplication.this.initCacheDates();
            return 0;
        }

        protected void onPostExecute(Integer n) {
            Log.i((String)"Application*****", (String)"\u7f13\u5b58\u521d\u59cb\u5316\u5b8c\u6210");
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected /* varargs */ void onProgressUpdate(Integer ... arrinteger) {
        }
    }

}

