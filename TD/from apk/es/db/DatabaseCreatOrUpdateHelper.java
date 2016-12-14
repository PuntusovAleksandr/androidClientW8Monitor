/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.os.Environment
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.lefu.es.util.LogUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseCreatOrUpdateHelper {
    private static final String BASE_DATABASE_NAME = "basedb.db";
    public static final String BASE_DATABASE_PATH;
    private static final int BUFFER_SIZE = 400000;
    public static final String DATABASE_PAGE_PATH;
    public static final String PACKAGE_NAME = "com.lefu.iwellness.newes.system";
    private static String TAG;

    static {
        TAG = DatabaseCreatOrUpdateHelper.class.getSimpleName();
        DATABASE_PAGE_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + "com.lefu.iwellness.newes.system" + "/databases";
        BASE_DATABASE_PATH = String.valueOf((Object)DATABASE_PAGE_PATH) + "/" + "basedb.db";
    }

    public static SQLiteDatabase createOrUpdateDB(Context context) {
        if (!new File(BASE_DATABASE_PATH).exists()) {
            new File(DATABASE_PAGE_PATH).mkdirs();
            try {
                DatabaseCreatOrUpdateHelper.importDateBase(context);
                SQLiteDatabase sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase((String)BASE_DATABASE_PATH, (SQLiteDatabase.CursorFactory)null);
                return sQLiteDatabase;
            }
            catch (FileNotFoundException var7_2) {
                LogUtils.e(TAG, "onCreate DB failed:" + var7_2.getMessage());
                return null;
            }
            catch (IOException var6_3) {
                LogUtils.e(TAG, "onCreate DB failed:" + var6_3.getMessage());
                return null;
            }
        }
        LogUtils.e(TAG, "f.exists()");
        try {
            context.deleteDatabase(BASE_DATABASE_PATH);
            DatabaseCreatOrUpdateHelper.importDateBase(context);
            SQLiteDatabase sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase((String)BASE_DATABASE_PATH, (SQLiteDatabase.CursorFactory)null);
            return sQLiteDatabase;
        }
        catch (FileNotFoundException var2_5) {
            LogUtils.e(TAG, "onCreate DB failed:" + var2_5.getMessage());
            return null;
        }
        catch (IOException var1_6) {
            LogUtils.e(TAG, "onCreate DB failed:" + var1_6.getMessage());
            return null;
        }
    }

    public static void importDateBase(Context context) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(2131034112);
        FileOutputStream fileOutputStream = new FileOutputStream(BASE_DATABASE_PATH);
        byte[] arrby = new byte[400000];
        do {
            int n;
            if ((n = inputStream.read(arrby)) <= 0) {
                fileOutputStream.close();
                inputStream.close();
                return;
            }
            fileOutputStream.write(arrby, 0, n);
        } while (true);
    }
}

