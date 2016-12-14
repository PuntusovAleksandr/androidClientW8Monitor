/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.util.Log
 *  java.io.File
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.lefu.es.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lefu.es.db.DBOpenHelper;
import com.lefu.es.entity.Records;
import com.lefu.es.util.UtilTooth;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordService {
    private Context context;
    private DBOpenHelper dbHelper;
    private SQLiteDatabase dbs;

    public RecordService(Context context) {
        this.dbHelper = new DBOpenHelper(context);
        this.context = context;
    }

    public void closeDB() {
        if (this.dbHelper != null) {
            this.dbHelper.close();
            this.dbHelper = null;
        }
    }

    public void delete(Records records) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getWritableDatabase();
        Object[] arrobject = new Object[]{records.getId()};
        sQLiteDatabase.execSQL("delete from userrecord where id=?", arrobject);
        this.deletePhoto(records.getRphoto());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deleteByUseridAndScale(String string2, String string3) {
        try {
            List<Records> list = this.getAllDatasByScaleAndGroup(string3, string2, 100.0f);
            if (list.size() > 0) {
                int n;
                for (int i = 0; i < (n = list.size()); ++i) {
                    this.deletePhoto(((Records)list.get(i)).getRphoto());
                }
            }
        }
        catch (Exception var3_6) {
            var3_6.printStackTrace();
        }
        this.dbs = this.dbHelper.getWritableDatabase();
        this.dbs.beginTransaction();
        this.dbs.execSQL("delete from userrecord where useid=? and scaleType=?", new Object[]{string2, string3});
        this.dbs.setTransactionSuccessful();
        this.dbs.endTransaction();
        this.dbs.close();
    }

    public void deletePhoto(String string2) {
        File file;
        if (string2 != null && string2.length() > 3 && (file = new File(string2)).exists()) {
            file.delete();
        }
    }

    public Records find(int n) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.rawQuery("select useid,scaleType,ugroup,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,photo,bodyage from userrecord where id=? order by recordtime desc  ", arrstring);
        boolean bl = cursor.moveToFirst();
        Records records = null;
        if (bl) {
            records = new Records(n, cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("datetime(recordtime,'localtime')")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string2 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string2 != null && string2.length() > 3) {
                records.setRphoto(string2);
            }
        }
        cursor.close();
        this.dbs.close();
        return records;
    }

    public Records findLastRecords(int n) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.rawQuery("select id, useid,scaleType,ugroup,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,photo,max(datetime(recordtime,'localtime')),bodyage from userrecord where useid = ? group by scaletype order by recordtime desc", arrstring);
        boolean bl = cursor.moveToFirst();
        Records records = null;
        if (bl) {
            records = new Records(cursor.getInt(cursor.getColumnIndex("id")), n, cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("datetime(recordtime,'localtime')")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string2 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string2 != null && string2.length() > 3) {
                records.setRphoto(string2);
            }
        }
        cursor.close();
        this.dbs.close();
        return records;
    }

    public List<Records> findLastRecords() throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)1)};
        Cursor cursor = sQLiteDatabase.rawQuery("select id, useid,scaleType,ugroup,recordtime,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,photo,bodyage,recordtime from userrecord where 1=?  group by scaletype,ugroup order by recordtime desc ", arrstring);
        ArrayList arrayList = new ArrayList();
        if (cursor.moveToNext()) {
            Records records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("datetime(recordtime,'localtime')")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string2 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string2 != null && string2.length() > 3) {
                records.setRphoto(string2);
            }
            arrayList.add((Object)records);
        }
        cursor.close();
        this.dbs.close();
        return arrayList;
    }

    public Records findLastRecordsByScaleType(String string2, String string3) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by useid having scaleType=? and ugroup=? order by md desc", new String[]{string2, string3});
        boolean bl = cursor.moveToFirst();
        Records records = null;
        if (bl) {
            records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("md")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")));
            String string4 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string4 != null && string4.length() > 3) {
                records.setRphoto(string4);
            }
        }
        cursor.close();
        this.dbs.close();
        return records;
    }

    public List<Records> findLastRecordsByScaleType2(String string2) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by useid having scaleType=?  order by ugroup asc,recordtime desc", new String[]{string2});
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            Records records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("md")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string3 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string3 != null && string3.length() > 3) {
                records.setRphoto(string3);
            }
            arrayList.add((Object)records);
        } while (true);
    }

    public Records findLastRecordsByScaleTypeAndUser(String string2, String string3) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord where scaleType=? and useid=? order by md desc", new String[]{string2, string3});
        boolean bl = cursor.moveToFirst();
        Records records = null;
        if (bl) {
            records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("md")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getInt(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string4 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string4 != null && string4.length() > 3) {
                records.setRphoto(string4);
            }
        }
        cursor.close();
        this.dbs.close();
        return records;
    }

    public Records findLastRecordsByUID(int n) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord where useid =? order by md desc", arrstring);
        boolean bl = cursor.moveToFirst();
        Records records = null;
        if (bl) {
            records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("md")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string2 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string2 != null && string2.length() > 3) {
                records.setRphoto(string2);
            }
        }
        cursor.close();
        this.dbs.close();
        return records;
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<Records> findLastTowRecordsByScaleType(String string2, String string3) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select * from userrecord where scaleType=? and ugroup=? order by recordtime desc limit 0,5", new String[]{string2, string3});
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) break;
            Records records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("recordtime")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string4 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string4 != null && string4.length() > 3) {
                records.setRphoto(string4);
            }
            arrayList.add((Object)records);
        } while (true);
        cursor.close();
        this.dbs.close();
        ArrayList arrayList2 = new ArrayList();
        int n = -1 + arrayList.size();
        while (n >= 0) {
            arrayList2.add((Object)((Records)arrayList.get(n)));
            --n;
        }
        return arrayList2;
    }

    public List<Records> findRecordsByScaleTypeAndFoodNameAndKg(String string2, String string3, float f) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{string2, string3, String.valueOf((float)f)};
        Cursor cursor = sQLiteDatabase.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by useid having scaleType=? and photo=? and rweight=?  order by recordtime desc", arrstring);
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            Records records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("md")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string4 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string4 != null && string4.length() > 3) {
                records.setRphoto(string4);
            }
            arrayList.add((Object)records);
        } while (true);
    }

    public List<Records> getAllDatas() throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select *,max(datetime(recordtime,'localtime')) md from userrecord group by ugroup having scaleType=? order by md desc ", new String[]{"ce"});
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            Records records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("md")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string2 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string2 != null && string2.length() > 3) {
                records.setRphoto(string2);
            }
            arrayList.add((Object)records);
        } while (true);
    }

    public List<Records> getAllDatasByScaleAndGroup(String string2, String string3, float f) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select *,datetime(recordtime,'localtime') md from userrecord where scaleType=? and ugroup=? order by recordtime desc ", new String[]{string2, string3});
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            int n = cursor.getInt(cursor.getColumnIndex("id"));
            int n2 = cursor.getInt(cursor.getColumnIndex("useid"));
            String string4 = cursor.getString(cursor.getColumnIndex("scaleType"));
            String string5 = cursor.getString(cursor.getColumnIndex("ugroup"));
            String string6 = cursor.getString(cursor.getColumnIndex("md"));
            String string7 = cursor.getString(cursor.getColumnIndex("comparerecord"));
            float f2 = cursor.getFloat(cursor.getColumnIndex("rweight"));
            Records records = new Records(n, n2, string4, string5, string6, string7, f2, UtilTooth.myround(UtilTooth.countBMI2(f2, f / 100.0f)), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string8 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string8 != null && string8.length() > 3) {
                records.setRphoto(string8);
            }
            arrayList.add((Object)records);
        } while (true);
    }

    public List<Records> getAllDatasByScaleAndGroupDesc(String string2, String string3, float f) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select *,datetime(recordtime,'localtime') md from userrecord where scaleType=? and ugroup=? order by recordtime asc ", new String[]{string2, string3});
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            int n = cursor.getInt(cursor.getColumnIndex("id"));
            int n2 = cursor.getInt(cursor.getColumnIndex("useid"));
            String string4 = cursor.getString(cursor.getColumnIndex("scaleType"));
            String string5 = cursor.getString(cursor.getColumnIndex("ugroup"));
            String string6 = cursor.getString(cursor.getColumnIndex("md"));
            String string7 = cursor.getString(cursor.getColumnIndex("comparerecord"));
            float f2 = cursor.getFloat(cursor.getColumnIndex("rweight"));
            Records records = new Records(n, n2, string4, string5, string6, string7, f2, UtilTooth.myround(UtilTooth.countBMI2(f2, f / 100.0f)), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string8 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string8 != null && string8.length() > 3) {
                records.setRphoto(string8);
            }
            arrayList.add((Object)records);
        } while (true);
    }

    public List<Records> getAllDatasByScaleAndIDAsc(String string2, int n, float f) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{string2, String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.rawQuery("select * from userrecord where scaleType=? and useid=? order by recordtime asc ", arrstring);
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            Records records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("recordtime")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string3 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string3 != null && string3.length() > 3) {
                records.setRphoto(string3);
            }
            arrayList.add((Object)records);
        } while (true);
    }

    public List<Records> getAllDatasByScaleAndIDDesc(String string2, int n, float f) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{string2, String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.rawQuery("select * from userrecord where scaleType=? and useid=? order by recordtime desc ", arrstring);
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            Records records = new Records(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("useid")), cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("recordtime")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage")));
            String string3 = cursor.getString(cursor.getColumnIndex("photo"));
            if (string3 != null && string3.length() > 3) {
                records.setRphoto(string3);
            }
            arrayList.add((Object)records);
        } while (true);
    }

    public Long getCount(int n) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.rawQuery("select count(*) from userrecord where useid=?", arrstring);
        cursor.moveToFirst();
        Long l = cursor.getLong(0);
        cursor.close();
        return l;
    }

    public List<Records> getScrollData(int n, int n2, int n3) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n3), String.valueOf((int)n), String.valueOf((int)n2)};
        Cursor cursor = sQLiteDatabase.rawQuery("select id, useid,scaleType,ugroup,recordtime,datetime(recordtime,'localtime'),comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,bodyage  from userrecord where useid=? order by recordtime desc  limit ?,?", arrstring);
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            arrayList.add((Object)new Records(cursor.getInt(cursor.getColumnIndex("id")), n3, cursor.getString(cursor.getColumnIndex("scaleType")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("datetime(recordtime,'localtime')")), cursor.getString(cursor.getColumnIndex("comparerecord")), cursor.getFloat(cursor.getColumnIndex("rweight")), cursor.getFloat(cursor.getColumnIndex("rbmi")), cursor.getFloat(cursor.getColumnIndex("rbone")), cursor.getFloat(cursor.getColumnIndex("rbodyfat")), cursor.getFloat(cursor.getColumnIndex("rmuscle")), cursor.getFloat(cursor.getColumnIndex("rbodywater")), cursor.getFloat(cursor.getColumnIndex("rvisceralfat")), cursor.getFloat(cursor.getColumnIndex("rbmr")), cursor.getFloat(cursor.getColumnIndex("bodyage"))));
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void save(Records records) {
        if (records == null) return;
        {
            try {
                if (records.getUseId() <= 0) return;
                {
                    this.dbs = this.dbHelper.getWritableDatabase();
                    if (records.getRecordTime() != null && records.getRecordTime().length() > 0) {
                        SQLiteDatabase sQLiteDatabase = this.dbs;
                        Object[] arrobject = new Object[]{records.getUseId(), records.getScaleType(), records.getUgroup(), records.getRecordTime(), records.getCompareRecord(), Float.valueOf((float)records.getRweight()), Float.valueOf((float)records.getRbmi()), Float.valueOf((float)records.getRbone()), Float.valueOf((float)records.getRbodyfat()), Float.valueOf((float)records.getRmuscle()), Float.valueOf((float)records.getRbodywater()), Float.valueOf((float)records.getRvisceralfat()), Float.valueOf((float)records.getRbmr()), Float.valueOf((float)records.getBodyAge()), records.getRphoto()};
                        sQLiteDatabase.execSQL("insert into userrecord(useid,scaleType,ugroup,recordtime,comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,bodyage,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrobject);
                    } else {
                        SQLiteDatabase sQLiteDatabase = this.dbs;
                        Object[] arrobject = new Object[]{records.getUseId(), records.getScaleType(), records.getUgroup(), records.getCompareRecord(), Float.valueOf((float)records.getRweight()), Float.valueOf((float)records.getRbmi()), Float.valueOf((float)records.getRbone()), Float.valueOf((float)records.getRbodyfat()), Float.valueOf((float)records.getRmuscle()), Float.valueOf((float)records.getRbodywater()), Float.valueOf((float)records.getRvisceralfat()), Float.valueOf((float)records.getRbmr()), Float.valueOf((float)records.getBodyAge()), records.getRphoto()};
                        sQLiteDatabase.execSQL("insert into userrecord(useid,scaleType,ugroup,comparerecord,rweight, rbmi, rbone, rbodyfat, rmuscle, rbodywater, rvisceralfat, rbmr,bodyage,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrobject);
                    }
                    this.dbs.close();
                    return;
                }
            }
            catch (Exception var2_6) {
                Log.e((String)"RecordService", (String)("save Record failed" + var2_6.getMessage()));
            }
        }
    }

    public void update(Records records) throws Exception {
        this.dbs = this.dbHelper.getWritableDatabase();
        this.dbs.beginTransaction();
        SQLiteDatabase sQLiteDatabase = this.dbs;
        Object[] arrobject = new Object[]{records.getUseId(), records.getScaleType(), records.getUgroup(), Float.valueOf((float)records.getRweight()), Float.valueOf((float)records.getRbmi()), Float.valueOf((float)records.getRbone()), Float.valueOf((float)records.getRbodyfat()), Float.valueOf((float)records.getRmuscle()), Float.valueOf((float)records.getRbodywater()), Float.valueOf((float)records.getRvisceralfat()), Float.valueOf((float)records.getRbmr()), Float.valueOf((float)records.getBodyAge()), records.getId()};
        sQLiteDatabase.execSQL("update  userrecord set useid=?,scaleType=?,ugroup=?,recordtime=now(),comparerecord=?,rweight=?, rbmi=?, rbone=?, rbodyfat=?, rmuscle=?, rbodywater=?, rvisceralfat=?, rbmr=?,bodyage=? where id=? ", arrobject);
        this.dbs.setTransactionSuccessful();
        this.dbs.endTransaction();
    }

    public void updatePhoto(int n, String string2) {
        this.dbs = this.dbHelper.getWritableDatabase();
        this.dbs.beginTransaction();
        SQLiteDatabase sQLiteDatabase = this.dbs;
        Object[] arrobject = new Object[]{string2, n};
        sQLiteDatabase.execSQL("update  userrecord set photo=? where id=? ", arrobject);
        this.dbs.setTransactionSuccessful();
        this.dbs.endTransaction();
    }
}

