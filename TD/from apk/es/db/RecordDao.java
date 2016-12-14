/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Date
 */
package com.lefu.es.db;

import android.content.Context;
import android.text.TextUtils;
import com.lefu.es.application.IwellnessApplication;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;
import com.lefu.es.service.UserService;
import com.lefu.es.util.MyUtil;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UtilTooth;
import java.util.Date;

public class RecordDao {
    private static UserService uservice;

    public static void dueDate(RecordService recordService, String string2) {
        RecordDao.handleData(recordService, MyUtil.parseMeaage(recordService, string2), string2);
    }

    public static void dueKitchenDate(RecordService recordService, String string2, NutrientBo nutrientBo) {
        RecordDao.handleKitchenData(recordService, MyUtil.parseMeaage(recordService, string2), nutrientBo);
    }

    /*
     * Exception decompiling
     */
    public static void handleData(RecordService var0, Records var1_1, String var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [10[UNCONDITIONALDOLOOP]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Records handleKitchenData(RecordService var0, Records var1_1, NutrientBo var2_2) {
        if (var1_1 == null) return var1_1;
        if (var0 == null) return var1_1;
        RecordDao.uservice = new UserService((Context)IwellnessApplication.app);
        var3_3 = UtilConstants.CURRENT_USER;
        if (var3_3 != null) {
            if (var1_1.getUnitType() == 0) {
                var3_3.setDanwei(UtilConstants.UNIT_G);
            } else if (var1_1.getUnitType() == 1) {
                var3_3.setDanwei(UtilConstants.UNIT_LB);
            } else if (var1_1.getUnitType() == 2) {
                var3_3.setDanwei(UtilConstants.UNIT_ML);
            } else if (var1_1.getUnitType() == 4) {
                var3_3.setDanwei(UtilConstants.UNIT_ML2);
            } else if (var1_1.getUnitType() == 3) {
                var3_3.setDanwei(UtilConstants.UNIT_FATLB);
            }
            var1_1.setUseId(var3_3.getId());
            var1_1.setUgroup(var3_3.getGroup());
        }
        var1_1.setScaleType(UtilConstants.KITCHEN_SCALE);
        var1_1.setRecordTime(UtilTooth.dateTimeChange(new Date()));
        if (var2_2 != null) {
            var5_4 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientCalcium());
            var6_5 = 0.0f;
            if (!var5_4) {
                var28_6 = Tool.isDigitsOnly(var2_2.getNutrientCalcium());
                var6_5 = 0.0f;
                if (var28_6) {
                    var6_5 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientCalcium()) * var1_1.getRweight());
                }
            }
            var7_7 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientProtein());
            var8_8 = 0.0f;
            if (!var7_7) {
                var27_9 = Tool.isDigitsOnly(var2_2.getNutrientProtein());
                var8_8 = 0.0f;
                if (var27_9) {
                    var8_8 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientProtein()) * var1_1.getRweight());
                }
            }
            var9_10 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientEnerg());
            var10_11 = 0.0f;
            if (!var9_10) {
                var26_12 = Tool.isDigitsOnly(var2_2.getNutrientEnerg());
                var10_11 = 0.0f;
                if (var26_12) {
                    var10_11 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientEnerg()) * var1_1.getRweight());
                }
            }
            var11_13 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientCarbohydrt());
            var12_14 = 0.0f;
            if (!var11_13) {
                var25_15 = Tool.isDigitsOnly(var2_2.getNutrientCarbohydrt());
                var12_14 = 0.0f;
                if (var25_15) {
                    var12_14 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientCarbohydrt()) * var1_1.getRweight());
                }
            }
            var13_16 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientLipidTot());
            var14_17 = 0.0f;
            if (!var13_16) {
                var24_18 = Tool.isDigitsOnly(var2_2.getNutrientLipidTot());
                var14_17 = 0.0f;
                if (var24_18) {
                    var14_17 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientLipidTot()) * var1_1.getRweight());
                }
            }
            var15_19 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientFiberTD());
            var16_20 = 0.0f;
            if (!var15_19) {
                var23_21 = Tool.isDigitsOnly(var2_2.getNutrientFiberTD());
                var16_20 = 0.0f;
                if (var23_21) {
                    var16_20 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientFiberTD()) * var1_1.getRweight());
                }
            }
            var17_22 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientCholestrl());
            var18_23 = 0.0f;
            if (!var17_22) {
                var22_24 = Tool.isDigitsOnly(var2_2.getNutrientCholestrl());
                var18_23 = 0.0f;
                if (var22_24) {
                    var18_23 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientCholestrl()) * var1_1.getRweight());
                }
            }
            var19_25 = TextUtils.isEmpty((CharSequence)var2_2.getNutrientVitB6());
            var20_26 = 0.0f;
            if (!var19_25) {
                var21_27 = Tool.isDigitsOnly(var2_2.getNutrientVitB6());
                var20_26 = 0.0f;
                if (var21_27) {
                    var20_26 = 0.01f * (Float.parseFloat((String)var2_2.getNutrientVitB6()) * var1_1.getRweight());
                }
            }
            var1_1.setRphoto(var2_2.getNutrientDesc());
            var1_1.setRbodywater(var10_11);
            var1_1.setRbodyfat(var8_8);
            var1_1.setRbone(var14_17);
            var1_1.setRmuscle(var12_14);
            var1_1.setRvisceralfat(var16_20);
            var1_1.setRbmi(var18_23);
            var1_1.setRbmr(var20_26);
            var1_1.setBodyAge(var6_5);
        } else {
            var1_1.setRbodywater(0.0f);
            var1_1.setRbodyfat(0.0f);
            var1_1.setRbone(0.0f);
            var1_1.setRmuscle(0.0f);
            var1_1.setRvisceralfat(0.0f);
            var1_1.setRbmi(0.0f);
            var1_1.setRbmr(0.0f);
            var1_1.setBodyAge(0.0f);
        }
        if (var3_3 == null) ** GOTO lbl103
        try {
            if (RecordDao.uservice != null) {
                RecordDao.uservice.update(var3_3);
            }
lbl103: // 4 sources:
            var0.save(var1_1);
            return var1_1;
        }
        catch (Exception var4_28) {
            return var1_1;
        }
    }
}

