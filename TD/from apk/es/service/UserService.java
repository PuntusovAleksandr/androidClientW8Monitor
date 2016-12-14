/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.List
 */
package com.lefu.es.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lefu.es.db.DBOpenHelper;
import com.lefu.es.entity.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserService {
    private DBOpenHelper dbHelper;
    private SQLiteDatabase dbs;
    List<String> gros = new ArrayList((Collection)Arrays.asList((Object[])new String[]{"P0", "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9"}));

    public UserService(Context context) {
        this.dbHelper = new DBOpenHelper(context);
    }

    public void closeDB() {
        if (this.dbHelper != null) {
            this.dbHelper.close();
            this.dbHelper = null;
        }
    }

    public void delete(int n) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getWritableDatabase();
        Object[] arrobject = new Object[]{n};
        sQLiteDatabase.execSQL("delete from user where id=?", arrobject);
        this.dbs.close();
    }

    public UserModel find(int n) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.rawQuery("select * from user where id=?", arrstring);
        UserModel userModel = null;
        if (cursor != null) {
            boolean bl = cursor.moveToFirst();
            userModel = null;
            if (bl) {
                userModel = new UserModel(n, cursor.getString(cursor.getColumnIndex("username")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("sex")), cursor.getString(cursor.getColumnIndex("level")), cursor.getFloat(cursor.getColumnIndex("bheigth")), cursor.getInt(cursor.getColumnIndex("ageyear")), cursor.getInt(cursor.getColumnIndex("agemonth")), cursor.getInt(cursor.getColumnIndex("number")), cursor.getString(cursor.getColumnIndex("scaletype")), cursor.getString(cursor.getColumnIndex("uniqueid")), cursor.getString(cursor.getColumnIndex("birth")), cursor.getString(cursor.getColumnIndex("per_photo")), cursor.getFloat(cursor.getColumnIndex("targweight")), cursor.getString(cursor.getColumnIndex("danwei")));
            }
        }
        cursor.close();
        this.dbs.close();
        return userModel;
    }

    public UserModel findUserByGupandScale(String string2, String string3) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select * from user where ugroup=? and scaletype=?", new String[]{string2, string3});
        boolean bl = cursor.moveToFirst();
        UserModel userModel = null;
        if (bl) {
            userModel = new UserModel(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("username")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("sex")), cursor.getString(cursor.getColumnIndex("level")), cursor.getFloat(cursor.getColumnIndex("bheigth")), cursor.getInt(cursor.getColumnIndex("ageyear")), cursor.getInt(cursor.getColumnIndex("agemonth")), cursor.getInt(cursor.getColumnIndex("number")), cursor.getString(cursor.getColumnIndex("scaletype")), cursor.getString(cursor.getColumnIndex("uniqueid")), cursor.getString(cursor.getColumnIndex("birth")), cursor.getString(cursor.getColumnIndex("per_photo")), cursor.getFloat(cursor.getColumnIndex("targweight")), cursor.getString(cursor.getColumnIndex("danwei")));
        }
        cursor.close();
        this.dbs.close();
        return userModel;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getAddUserGroup(String string2) {
        try {
            boolean bl;
            String string3;
            List<String> list = this.getAllUserGroupByScaleType(string2);
            Iterator iterator = this.gros.iterator();
            do {
                if (iterator.hasNext()) continue;
                return "";
            } while (bl = list.contains((Object)(string3 = (String)iterator.next())));
            return string3;
        }
        catch (Exception var2_6) {
            var2_6.printStackTrace();
            return "";
        }
    }

    public List<UserModel> getAllDatas() throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select * from user ", null);
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            arrayList.add((Object)new UserModel(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("username")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("sex")), cursor.getString(cursor.getColumnIndex("level")), cursor.getFloat(cursor.getColumnIndex("bheigth")), cursor.getInt(cursor.getColumnIndex("ageyear")), cursor.getInt(cursor.getColumnIndex("agemonth")), cursor.getInt(cursor.getColumnIndex("number")), cursor.getString(cursor.getColumnIndex("scaletype")), cursor.getString(cursor.getColumnIndex("uniqueid")), cursor.getString(cursor.getColumnIndex("birth")), cursor.getString(cursor.getColumnIndex("per_photo")), cursor.getFloat(cursor.getColumnIndex("targweight")), cursor.getString(cursor.getColumnIndex("danwei"))));
        } while (true);
    }

    public List<UserModel> getAllUserByScaleType() throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select * from user", null);
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            int n = cursor.getInt(cursor.getColumnIndex("id"));
            String string2 = cursor.getString(cursor.getColumnIndex("username"));
            String string3 = cursor.getString(cursor.getColumnIndex("ugroup"));
            String string4 = cursor.getString(cursor.getColumnIndex("sex"));
            String string5 = cursor.getString(cursor.getColumnIndex("level"));
            float f = cursor.getFloat(cursor.getColumnIndex("bheigth"));
            int n2 = cursor.getInt(cursor.getColumnIndex("ageyear"));
            int n3 = cursor.getInt(cursor.getColumnIndex("agemonth"));
            int n4 = cursor.getInt(cursor.getColumnIndex("number"));
            String string6 = cursor.getString(cursor.getColumnIndex("uniqueid"));
            String string7 = cursor.getString(cursor.getColumnIndex("birth"));
            String string8 = cursor.getString(cursor.getColumnIndex("per_photo"));
            float f2 = cursor.getFloat(cursor.getColumnIndex("targweight"));
            String string9 = cursor.getString(cursor.getColumnIndex("danwei"));
            arrayList.add((Object)new UserModel(n, string2, string3, string4, string5, f, n2, n3, n4, cursor.getString(cursor.getColumnIndex("scaletype")), string6, string7, string8, f2, string9));
        } while (true);
    }

    public List<String> getAllUserGroupByScaleType(String string2) throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select * from user where scaletype = ? order by ugroup ", new String[]{string2});
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            arrayList.add((Object)cursor.getString(cursor.getColumnIndex("ugroup")));
        } while (true);
    }

    public Long getCount() throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select count(*) from user", null);
        cursor.moveToFirst();
        Long l = cursor.getLong(0);
        cursor.close();
        return l;
    }

    /*
     * Exception decompiling
     */
    public int getMaxGroup() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.util.ConcurrentModificationException
        // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
        // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:485)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:649)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:816)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
        // java.lang.Thread.run(Thread.java:841)
        throw new IllegalStateException("Decompilation failed");
    }

    public List<UserModel> getScrollData(int n, int n2) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n), String.valueOf((int)n2)};
        Cursor cursor = sQLiteDatabase.rawQuery("select * from user limit ?,? ", arrstring);
        ArrayList arrayList = new ArrayList();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                this.dbs.close();
                return arrayList;
            }
            arrayList.add((Object)new UserModel(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("username")), cursor.getString(cursor.getColumnIndex("ugroup")), cursor.getString(cursor.getColumnIndex("sex")), cursor.getString(cursor.getColumnIndex("level")), cursor.getFloat(cursor.getColumnIndex("bheigth")), cursor.getInt(cursor.getColumnIndex("ageyear")), cursor.getInt(cursor.getColumnIndex("agemonth")), cursor.getInt(cursor.getColumnIndex("number")), cursor.getString(cursor.getColumnIndex("scaletype")), "", cursor.getString(cursor.getColumnIndex("birth")), cursor.getString(cursor.getColumnIndex("per_photo")), cursor.getFloat(cursor.getColumnIndex("targweight")), cursor.getString(cursor.getColumnIndex("danwei"))));
        } while (true);
    }

    public int maxid() throws Exception {
        this.dbs = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.dbs.rawQuery("select max(id) from user", null);
        cursor.moveToFirst();
        int n = cursor.getInt(0);
        cursor.close();
        return n;
    }

    public void save(UserModel userModel) throws Exception {
        SQLiteDatabase sQLiteDatabase = this.dbs = this.dbHelper.getWritableDatabase();
        Object[] arrobject = new Object[]{userModel.getUserName(), userModel.getGroup(), userModel.getSex(), userModel.getLevel(), Float.valueOf((float)userModel.getBheigth()), userModel.getAgeYear(), userModel.getAgeMonth(), userModel.getNumber(), userModel.getScaleType(), userModel.getUniqueID(), userModel.getBirth(), userModel.getPer_photo(), Float.valueOf((float)userModel.getTargweight()), userModel.getDanwei()};
        sQLiteDatabase.execSQL("insert into user(username,ugroup,sex,level,bheigth,ageyear,agemonth,number,scaletype,uniqueid,birth,per_photo,targweight,danwei) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrobject);
        this.dbs.close();
    }

    public void update(UserModel userModel) throws Exception {
        this.dbs = this.dbHelper.getWritableDatabase();
        this.dbs.beginTransaction();
        SQLiteDatabase sQLiteDatabase = this.dbs;
        Object[] arrobject = new Object[]{userModel.getUserName(), userModel.getGroup(), userModel.getSex(), userModel.getLevel(), Float.valueOf((float)userModel.getBheigth()), userModel.getAgeYear(), userModel.getAgeMonth(), userModel.getNumber(), userModel.getScaleType(), userModel.getUniqueID(), userModel.getBirth(), userModel.getPer_photo(), Float.valueOf((float)userModel.getTargweight()), userModel.getDanwei(), userModel.getId()};
        sQLiteDatabase.execSQL("update  user set username=?,ugroup=?,sex=?,level=?,bheigth=?,ageyear=?,agemonth=?,number=?,scaletype=?,uniqueid=?,birth=?,per_photo=?,targweight=?,danwei=? where id=? ", arrobject);
        this.dbs.setTransactionSuccessful();
        this.dbs.endTransaction();
    }

    public void update2(UserModel userModel) throws Exception {
        this.dbs = this.dbHelper.getWritableDatabase();
        this.dbs.beginTransaction();
        SQLiteDatabase sQLiteDatabase = this.dbs;
        Object[] arrobject = new Object[]{userModel.getSex(), userModel.getLevel(), Float.valueOf((float)userModel.getBheigth()), userModel.getAgeYear(), userModel.getUniqueID(), Float.valueOf((float)userModel.getTargweight()), userModel.getDanwei(), userModel.getId()};
        sQLiteDatabase.execSQL("update  user set sex=?,level=?,bheigth=?,ageyear=? ,uniqueid=? ,targweight=?,danwei=? where id=? ", arrobject);
        this.dbs.setTransactionSuccessful();
        this.dbs.endTransaction();
    }
}

