/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.text.TextUtils
 *  android.util.Log
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.Collections
 *  java.util.Comparator
 *  java.util.Iterator
 *  java.util.List
 */
package com.lefu.es.cache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.lefu.es.entity.BlueDevice;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.UserService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CacheHelper {
    private static final String TAG = "CacheHelper";
    public static List<BlueDevice> devicesList;
    public static List<NutrientBo> nutrientList;
    public static List<String> nutrientNameList;
    public static List<NutrientBo> nutrientTempList;
    public static List<String> nutrientTempNameList;
    public static List<Records> recordLastList;
    public static List<Records> recordList;
    public static List<Records> recordListDesc;
    private static RecordService recordService;
    public static List<UserModel> userList;
    private static UserService userService;

    static {
        userList = new ArrayList();
        recordList = new ArrayList();
        recordListDesc = new ArrayList();
        recordLastList = new ArrayList();
        devicesList = new ArrayList();
        nutrientList = new ArrayList();
        nutrientNameList = new ArrayList();
        nutrientTempNameList = new ArrayList();
        nutrientTempList = new ArrayList();
    }

    public static void cacheAllNutrientsDate(Context context, SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase != null) {
            CacheHelper.queryAllTempNutrients(context, sQLiteDatabase);
            CacheHelper.queryAllNutrients(context, sQLiteDatabase);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ArrayList<NutrientBo> findNutrientByName(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) return null;
        if (nutrientList == null) return null;
        if (nutrientList.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator iterator = nutrientList.iterator();
        while (iterator.hasNext()) {
            NutrientBo nutrientBo = (NutrientBo)iterator.next();
            if (TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientDesc()) || !nutrientBo.getNutrientDesc().contains((CharSequence)string2)) continue;
            arrayList.add((Object)nutrientBo);
        }
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Records getRecordLast(int n) {
        Records records;
        if (recordListDesc == null || recordListDesc.size() <= 0) return null;
        Iterator iterator = recordListDesc.iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while ((records = (Records)iterator.next()).getId() != n);
        return records;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static List<Records> getRecordLastListByScaleType(String string2) {
        List<Records> list = recordLastList;
        ArrayList arrayList = null;
        if (list == null) return arrayList;
        int n = recordLastList.size();
        arrayList = null;
        if (n <= 0) return arrayList;
        arrayList = new ArrayList();
        Iterator iterator = arrayList.iterator();
        do {
            if (!iterator.hasNext()) {
                Collections.sort((List)arrayList, (Comparator)new SortByName());
                return arrayList;
            }
            Records records = (Records)iterator.next();
            if (!records.getScaleType().equals((Object)string2)) continue;
            arrayList.add((Object)records);
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static UserModel getUserByGroandScaleType(String string2, String string3) {
        UserModel userModel;
        if (userList == null || userList.size() <= 0 || string2 == null || string3 == null) return null;
        Iterator iterator = userList.iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (!string2.equals((Object)(userModel = (UserModel)iterator.next()).getGroup()) || !string3.equals((Object)userModel.getScaleType()));
        return userModel;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static UserModel getUserById(int n) {
        UserModel userModel;
        if (userList == null || userList.size() <= 0 || n == 0) return null;
        Iterator iterator = userList.iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while ((userModel = (UserModel)iterator.next()).getId() != n);
        return userModel;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void intiCaches() {
        if (userList == null) {
            userList = new ArrayList();
        } else {
            userList.clear();
        }
        if (recordList == null) {
            recordList = new ArrayList();
        } else {
            recordList.clear();
        }
        if (recordLastList == null) {
            recordLastList = new ArrayList();
        } else {
            recordLastList.clear();
        }
        if (devicesList == null) {
            devicesList = new ArrayList();
        } else {
            devicesList.clear();
        }
        try {
            userList = userService.getAllDatas();
            recordList = recordService.getAllDatas();
            recordLastList = recordService.findLastRecords();
            return;
        }
        catch (Exception var0) {
            Log.e((String)"CacheHelper", (String)var0.getMessage());
            var0.printStackTrace();
            return;
        }
    }

    public static void main(String[] arrstring) {
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object)"P6");
        arrayList.add((Object)"P1");
        arrayList.add((Object)"P3");
        arrayList.add((Object)"P5");
        arrayList.add((Object)"P4");
        arrayList.add((Object)"P2");
        Collections.sort((List)arrayList);
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            System.out.println(string2);
        }
        return;
    }

    private static void queryAllNutrients(Context context, SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase != null) {
            try {
                Cursor cursor = sQLiteDatabase.rawQuery("select * from base_nutrient", null);
                do {
                    if (!cursor.moveToNext()) {
                        cursor.close();
                        Log.e((String)"queryAllNutrients*****", (String)("\u83b7\u53d6\u5230\u8425\u517b\u7f13\u5b58\u6570\u636e\u5171: " + nutrientList.size()));
                        return;
                    }
                    NutrientBo nutrientBo = new NutrientBo();
                    nutrientBo.setNutrientNo(cursor.getString(cursor.getColumnIndex("nutrientNo")));
                    nutrientBo.setNutrientDesc(cursor.getString(cursor.getColumnIndex("nutrientDesc")));
                    nutrientBo.setNutrientWater(cursor.getString(cursor.getColumnIndex("nutrientWater")));
                    nutrientBo.setNutrientEnerg(cursor.getString(cursor.getColumnIndex("nutrientEnerg")));
                    nutrientBo.setNutrientProtein(cursor.getString(cursor.getColumnIndex("nutrientProtein")));
                    nutrientBo.setNutrientLipidTot(cursor.getString(cursor.getColumnIndex("nutrientLipidTot")));
                    nutrientBo.setNutrientAsh(cursor.getString(cursor.getColumnIndex("nutrientAsh")));
                    nutrientBo.setNutrientCarbohydrt(cursor.getString(cursor.getColumnIndex("nutrientCarbohydrt")));
                    nutrientBo.setNutrientFiberTD(cursor.getString(cursor.getColumnIndex("nutrientFiberTD")));
                    nutrientBo.setNutrientSugarTot(cursor.getString(cursor.getColumnIndex("nutrientSugarTot")));
                    nutrientBo.setNutrientCalcium(cursor.getString(cursor.getColumnIndex("nutrientCalcium")));
                    nutrientBo.setNutrientIron(cursor.getString(cursor.getColumnIndex("nutrientIron")));
                    nutrientBo.setNutrientMagnesium(cursor.getString(cursor.getColumnIndex("nutrientMagnesium")));
                    nutrientBo.setNutrientPhosphorus(cursor.getString(cursor.getColumnIndex("nutrientPhosphorus")));
                    nutrientBo.setNutrientPotassium(cursor.getString(cursor.getColumnIndex("nutrientPotassium")));
                    nutrientBo.setNutrientSodium(cursor.getString(cursor.getColumnIndex("nutrientSodium")));
                    nutrientBo.setNutrientZinc(cursor.getString(cursor.getColumnIndex("nutrientZinc")));
                    nutrientBo.setNutrientCopper(cursor.getString(cursor.getColumnIndex("nutrientCopper")));
                    nutrientBo.setNutrientManganese(cursor.getString(cursor.getColumnIndex("nutrientManganese")));
                    nutrientBo.setNutrientSelenium(cursor.getString(cursor.getColumnIndex("nutrientSelenium")));
                    nutrientBo.setNutrientVitc(cursor.getString(cursor.getColumnIndex("nutrientVitc")));
                    nutrientBo.setNutrientThiamin(cursor.getString(cursor.getColumnIndex("nutrientThiamin")));
                    nutrientBo.setNutrientRiboflavin(cursor.getString(cursor.getColumnIndex("nutrientRiboflavin")));
                    nutrientBo.setNutrientNiacin(cursor.getString(cursor.getColumnIndex("nutrientNiacin")));
                    nutrientBo.setNutrientPantoAcid(cursor.getString(cursor.getColumnIndex("nutrientPantoAcid")));
                    nutrientBo.setNutrientVitB6(cursor.getString(cursor.getColumnIndex("nutrientVitB6")));
                    nutrientBo.setNutrientFolateTot(cursor.getString(cursor.getColumnIndex("nutrientFolateTot")));
                    nutrientBo.setNutrientFolicAcid(cursor.getString(cursor.getColumnIndex("nutrientFolicAcid")));
                    nutrientBo.setNutrientFoodFolate(cursor.getString(cursor.getColumnIndex("nutrientFoodFolate")));
                    nutrientBo.setNutrientFolateDfe(cursor.getString(cursor.getColumnIndex("nutrientFolateDfe")));
                    nutrientBo.setNutrientCholineTot(cursor.getString(cursor.getColumnIndex("nutrientCholineTot")));
                    nutrientBo.setNutrientVitB12(cursor.getString(cursor.getColumnIndex("nutrientVitB12")));
                    nutrientBo.setNutrientVitAiu(cursor.getString(cursor.getColumnIndex("nutrientVitAiu")));
                    nutrientBo.setNutrientVitArae(cursor.getString(cursor.getColumnIndex("nutrientVitArae")));
                    nutrientBo.setNutrientRetinol(cursor.getString(cursor.getColumnIndex("nutrientRetinol")));
                    nutrientBo.setNutrientAlphaCarot(cursor.getString(cursor.getColumnIndex("nutrientAlphaCarot")));
                    nutrientBo.setNutrientBetaCarot(cursor.getString(cursor.getColumnIndex("nutrientBetaCarot")));
                    nutrientBo.setNutrientBetaCrypt(cursor.getString(cursor.getColumnIndex("nutrientBetaCrypt")));
                    nutrientBo.setNutrientLycopene(cursor.getString(cursor.getColumnIndex("nutrientLycopene")));
                    nutrientBo.setNutrientLutZea(cursor.getString(cursor.getColumnIndex("nutrientLutZea")));
                    nutrientBo.setNutrientVite(cursor.getString(cursor.getColumnIndex("nutrientVite")));
                    nutrientBo.setNutrientVitd(cursor.getString(cursor.getColumnIndex("nutrientVitd")));
                    nutrientBo.setNutrientVitDiu(cursor.getString(cursor.getColumnIndex("nutrientVitDiu")));
                    nutrientBo.setNutrientVitK(cursor.getString(cursor.getColumnIndex("nutrientVitK")));
                    nutrientBo.setNutrientFaSat(cursor.getString(cursor.getColumnIndex("nutrientFaSat")));
                    nutrientBo.setNutrientFaMono(cursor.getString(cursor.getColumnIndex("nutrientFaMono")));
                    nutrientBo.setNutrientFaPoly(cursor.getString(cursor.getColumnIndex("nutrientFaPoly")));
                    nutrientBo.setNutrientCholestrl(cursor.getString(cursor.getColumnIndex("nutrientCholestrl")));
                    nutrientBo.setNutrientGmWt1(cursor.getString(cursor.getColumnIndex("nutrientGmWt1")));
                    nutrientBo.setNutrientGmWtDesc1(cursor.getString(cursor.getColumnIndex("nutrientGmWtDesc1")));
                    nutrientBo.setNutrientGmWt2(cursor.getString(cursor.getColumnIndex("nutrientGmWt2")));
                    nutrientBo.setNutrientGmWtDesc2(cursor.getString(cursor.getColumnIndex("nutrientGmWtDesc2")));
                    nutrientBo.setNutrientRefusePct(cursor.getString(cursor.getColumnIndex("nutrientRefusePct")));
                    if (TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientDesc())) continue;
                    nutrientNameList.add((Object)nutrientBo.getNutrientDesc());
                    nutrientList.add((Object)nutrientBo);
                } while (true);
            }
            catch (Exception var2_4) {
                var2_4.printStackTrace();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static List<NutrientBo> queryAllNutrientsByName(String string2) {
        List<NutrientBo> list = nutrientList;
        ArrayList arrayList = null;
        if (list == null) return arrayList;
        int n = nutrientList.size();
        arrayList = null;
        if (n <= 0) return arrayList;
        arrayList = new ArrayList();
        Iterator iterator = nutrientList.iterator();
        while (iterator.hasNext()) {
            NutrientBo nutrientBo = (NutrientBo)iterator.next();
            if (TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientDesc()) || !nutrientBo.getNutrientDesc().contains((CharSequence)string2)) continue;
            arrayList.add((Object)nutrientBo);
        }
        return arrayList;
    }

    public static List<NutrientBo> queryAllNutrientsByPager(int n) {
        if (nutrientList != null && nutrientList.size() > 0) {
            int n2;
            int n3;
            int n4 = n - 1;
            if (n4 < 0) {
                n4 = 0;
            }
            if ((n2 = (n3 = n4 * 30) + 30) >= -1 + nutrientList.size()) {
                n2 = nutrientList.size();
            }
            return nutrientList.subList(n3, n2);
        }
        return null;
    }

    private static void queryAllTempNutrients(Context context, SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase != null) {
            try {
                Cursor cursor = sQLiteDatabase.rawQuery("select * from base_nutrient limit 0,100", null);
                do {
                    if (!cursor.moveToNext()) {
                        cursor.close();
                        Log.e((String)"queryAllTempNutrients*****", (String)("\u83b7\u53d6\u5230\u8425\u517b\u4e34\u65f6\u7f13\u5b58\u6570\u636e\u5171: " + nutrientTempList.size()));
                        return;
                    }
                    NutrientBo nutrientBo = new NutrientBo();
                    nutrientBo.setNutrientNo(cursor.getString(cursor.getColumnIndex("nutrientNo")));
                    nutrientBo.setNutrientDesc(cursor.getString(cursor.getColumnIndex("nutrientDesc")));
                    nutrientBo.setNutrientWater(cursor.getString(cursor.getColumnIndex("nutrientWater")));
                    nutrientBo.setNutrientEnerg(cursor.getString(cursor.getColumnIndex("nutrientEnerg")));
                    nutrientBo.setNutrientProtein(cursor.getString(cursor.getColumnIndex("nutrientProtein")));
                    nutrientBo.setNutrientLipidTot(cursor.getString(cursor.getColumnIndex("nutrientLipidTot")));
                    nutrientBo.setNutrientAsh(cursor.getString(cursor.getColumnIndex("nutrientAsh")));
                    nutrientBo.setNutrientCarbohydrt(cursor.getString(cursor.getColumnIndex("nutrientCarbohydrt")));
                    nutrientBo.setNutrientFiberTD(cursor.getString(cursor.getColumnIndex("nutrientFiberTD")));
                    nutrientBo.setNutrientSugarTot(cursor.getString(cursor.getColumnIndex("nutrientSugarTot")));
                    nutrientBo.setNutrientCalcium(cursor.getString(cursor.getColumnIndex("nutrientCalcium")));
                    nutrientBo.setNutrientIron(cursor.getString(cursor.getColumnIndex("nutrientIron")));
                    nutrientBo.setNutrientMagnesium(cursor.getString(cursor.getColumnIndex("nutrientMagnesium")));
                    nutrientBo.setNutrientPhosphorus(cursor.getString(cursor.getColumnIndex("nutrientPhosphorus")));
                    nutrientBo.setNutrientPotassium(cursor.getString(cursor.getColumnIndex("nutrientPotassium")));
                    nutrientBo.setNutrientSodium(cursor.getString(cursor.getColumnIndex("nutrientSodium")));
                    nutrientBo.setNutrientZinc(cursor.getString(cursor.getColumnIndex("nutrientZinc")));
                    nutrientBo.setNutrientCopper(cursor.getString(cursor.getColumnIndex("nutrientCopper")));
                    nutrientBo.setNutrientManganese(cursor.getString(cursor.getColumnIndex("nutrientManganese")));
                    nutrientBo.setNutrientSelenium(cursor.getString(cursor.getColumnIndex("nutrientSelenium")));
                    nutrientBo.setNutrientVitc(cursor.getString(cursor.getColumnIndex("nutrientVitc")));
                    nutrientBo.setNutrientThiamin(cursor.getString(cursor.getColumnIndex("nutrientThiamin")));
                    nutrientBo.setNutrientRiboflavin(cursor.getString(cursor.getColumnIndex("nutrientRiboflavin")));
                    nutrientBo.setNutrientNiacin(cursor.getString(cursor.getColumnIndex("nutrientNiacin")));
                    nutrientBo.setNutrientPantoAcid(cursor.getString(cursor.getColumnIndex("nutrientPantoAcid")));
                    nutrientBo.setNutrientVitB6(cursor.getString(cursor.getColumnIndex("nutrientVitB6")));
                    nutrientBo.setNutrientFolateTot(cursor.getString(cursor.getColumnIndex("nutrientFolateTot")));
                    nutrientBo.setNutrientFolicAcid(cursor.getString(cursor.getColumnIndex("nutrientFolicAcid")));
                    nutrientBo.setNutrientFoodFolate(cursor.getString(cursor.getColumnIndex("nutrientFoodFolate")));
                    nutrientBo.setNutrientFolateDfe(cursor.getString(cursor.getColumnIndex("nutrientFolateDfe")));
                    nutrientBo.setNutrientCholineTot(cursor.getString(cursor.getColumnIndex("nutrientCholineTot")));
                    nutrientBo.setNutrientVitB12(cursor.getString(cursor.getColumnIndex("nutrientVitB12")));
                    nutrientBo.setNutrientVitAiu(cursor.getString(cursor.getColumnIndex("nutrientVitAiu")));
                    nutrientBo.setNutrientVitArae(cursor.getString(cursor.getColumnIndex("nutrientVitArae")));
                    nutrientBo.setNutrientRetinol(cursor.getString(cursor.getColumnIndex("nutrientRetinol")));
                    nutrientBo.setNutrientAlphaCarot(cursor.getString(cursor.getColumnIndex("nutrientAlphaCarot")));
                    nutrientBo.setNutrientBetaCarot(cursor.getString(cursor.getColumnIndex("nutrientBetaCarot")));
                    nutrientBo.setNutrientBetaCrypt(cursor.getString(cursor.getColumnIndex("nutrientBetaCrypt")));
                    nutrientBo.setNutrientLycopene(cursor.getString(cursor.getColumnIndex("nutrientLycopene")));
                    nutrientBo.setNutrientLutZea(cursor.getString(cursor.getColumnIndex("nutrientLutZea")));
                    nutrientBo.setNutrientVite(cursor.getString(cursor.getColumnIndex("nutrientVite")));
                    nutrientBo.setNutrientVitd(cursor.getString(cursor.getColumnIndex("nutrientVitd")));
                    nutrientBo.setNutrientVitDiu(cursor.getString(cursor.getColumnIndex("nutrientVitDiu")));
                    nutrientBo.setNutrientVitK(cursor.getString(cursor.getColumnIndex("nutrientVitK")));
                    nutrientBo.setNutrientFaSat(cursor.getString(cursor.getColumnIndex("nutrientFaSat")));
                    nutrientBo.setNutrientFaMono(cursor.getString(cursor.getColumnIndex("nutrientFaMono")));
                    nutrientBo.setNutrientFaPoly(cursor.getString(cursor.getColumnIndex("nutrientFaPoly")));
                    nutrientBo.setNutrientCholestrl(cursor.getString(cursor.getColumnIndex("nutrientCholestrl")));
                    nutrientBo.setNutrientGmWt1(cursor.getString(cursor.getColumnIndex("nutrientGmWt1")));
                    nutrientBo.setNutrientGmWtDesc1(cursor.getString(cursor.getColumnIndex("nutrientGmWtDesc1")));
                    nutrientBo.setNutrientGmWt2(cursor.getString(cursor.getColumnIndex("nutrientGmWt2")));
                    nutrientBo.setNutrientGmWtDesc2(cursor.getString(cursor.getColumnIndex("nutrientGmWtDesc2")));
                    nutrientBo.setNutrientRefusePct(cursor.getString(cursor.getColumnIndex("nutrientRefusePct")));
                    if (TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientDesc())) continue;
                    nutrientTempNameList.add((Object)nutrientBo.getNutrientDesc());
                    nutrientTempList.add((Object)nutrientBo);
                } while (true);
            }
            catch (Exception var2_4) {
                var2_4.printStackTrace();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static NutrientBo queryNutrientsByName(String string2) {
        NutrientBo nutrientBo;
        if (TextUtils.isEmpty((CharSequence)string2) || nutrientList == null || nutrientList.size() <= 0) return null;
        Iterator iterator = nutrientList.iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (TextUtils.isEmpty((CharSequence)(nutrientBo = (NutrientBo)iterator.next()).getNutrientDesc()) || !nutrientBo.getNutrientDesc().equals((Object)string2));
        return nutrientBo;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean updateUserById(UserModel userModel) {
        UserModel userModel2;
        if (userList == null || userList.size() <= 0 || userModel == null) return false;
        Iterator iterator = userList.iterator();
        do {
            if (iterator.hasNext()) continue;
            return false;
        } while ((userModel2 = (UserModel)iterator.next()).getId() != userModel.getId());
        userList.remove((Object)userModel2);
        userList.add((Object)userModel);
        return true;
    }

    static class SortByName
    implements Comparator {
        SortByName() {
        }

        public int compare(Object object, Object object2) {
            Records records = (Records)object;
            Records records2 = (Records)object2;
            return records.getUgroup().compareTo(records2.getUgroup());
        }
    }

}

