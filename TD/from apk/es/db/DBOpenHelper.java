/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  java.lang.String
 */
package com.lefu.es.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper
extends SQLiteOpenHelper {
    private static final String name = "lefu.db";
    private static final int version = 5;

    public DBOpenHelper(Context context) {
        super(context, "lefu.db", null, 5);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS userrecord");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user (id integer primary key autoincrement, username varchar(30),ugroup varchar(10),sex varchar(2),level varchar(10),bheigth real, ageyear INTEGER,agemonth INTEGER,number INTEGER,scaletype varchar(10),uniqueid varchar(10),birth varchar(20),per_photo varchar(200),targweight real ,danwei varchar(10))");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS userrecord (id integer primary key autoincrement, useid INTEGER,scaleType varchar(10) ,ugroup varchar(10),recordtime DATETIME DEFAULT CURRENT_TIMESTAMP,comparerecord varchar(50),rweight real,rbmi real,rbone real,rbodyfat real,rmuscle real,rbodywater real,rvisceralfat real,rbmr real,photo varchar(200),bodyage real)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        this.onCreate(sQLiteDatabase);
    }
}

