/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.NotificationManager
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.Spinner
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.entity.NutrientBo;
import com.lefu.es.system.fragment.SearchDialogFragment;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.kmpautotextview.KMPAutoComplTextView;
import com.lefu.es.view.spinner.HintAdapter;
import com.lefu.es.view.spinner.HintSpinner;
import java.util.ArrayList;
import java.util.List;

public class KitchenInfoActivity
extends Activity
implements SearchDialogFragment.NatureSelectListener {
    private TextView Alpha_Carot_excel_title;
    private TextView Ash_excel_title;
    private TextView Beta_Carot_excel_title;
    private TextView Beta_Crypt_excel_title;
    private TextView Calcium_excel_title;
    private TextView Carbohydrt_excel_title;
    private TextView Cholesterol_excel_title;
    private TextView Choline_Tot_excel_title;
    private TextView Copper_excel_title;
    private TextView Energ_excel_title;
    private TextView FA_Mono_excel_title;
    private TextView FA_Poly_excel_title;
    private TextView FA_Sat_excel_title;
    private TextView Fiber_excel_title;
    private TextView Folate_DFE_excel_title;
    private TextView Folate_Tot_excel_title;
    private TextView Folic_Acid_excel_title;
    private TextView Food_Folate_excel_title;
    private TextView GmWt_1_excel_title;
    private TextView GmWt_2_excel_title;
    private TextView Iron_excel_title;
    private TextView Lipid_excel_title;
    private TextView LutZea_excel_title;
    private TextView Lycopene_excel_title;
    private TextView Magnesium_excel_title;
    private TextView Manganese_excel_title;
    private TextView Niacin_excel_title;
    private TextView Panto_Acid_excel_title;
    private TextView Phosphorus_excel_title;
    private TextView Potassium_excel_title;
    private TextView Protein_excel_title;
    private TextView Refuse_Pct_excel_title;
    private TextView Retinol_excel_title;
    private TextView Riboflavin_excel_title;
    private TextView Selenium_excel_title;
    private TextView Sodium_excel_title;
    private TextView Sugar_excel_title;
    private TextView Thiamin_excel_title;
    private TextView Vit_A_IU_excel_title;
    private TextView Vit_A_RAE_excel_title;
    private TextView Vit_B12_excel_title;
    private TextView Vit_B6_excel_title;
    private TextView Vit_C_excel_title;
    private TextView Vit_D_IU_excel_title;
    private TextView Vit_D_excel_title;
    private TextView Vit_E_excel_title;
    private TextView Vit_K_excel_title;
    private TextView Water_excel_title;
    private TextView Zinc_excel_title;
    View.OnClickListener btnOnClickListener;
    private TextView btn_mback;
    int currentPage = 0;
    private String currentUnit = null;
    private HintSpinner<String> defaultHintSpinner;
    private List<String> defaults;
    private float lastWeight = 0.0f;
    private boolean needChage = true;
    private TextView search_btn;
    private KMPAutoComplTextView search_et;
    private NutrientBo selectNutrient = null;
    private AutoCompleteTextView weight_et;

    public KitchenInfoActivity() {
        this.btnOnClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 2131296390: {
                        if (TextUtils.isEmpty((CharSequence)KitchenInfoActivity.this.search_et.getText().toString())) {
                            Toast.makeText((Context)KitchenInfoActivity.this, (CharSequence)"Please input something", (int)0).show();
                            return;
                        }
                        NutrientBo nutrientBo = CacheHelper.queryNutrientsByName(KitchenInfoActivity.this.search_et.getText().toString());
                        if (nutrientBo == null) {
                            Toast.makeText((Context)KitchenInfoActivity.this, (CharSequence)"No Data had been found", (int)0).show();
                            return;
                        }
                        KitchenInfoActivity.this.search_et.setText((CharSequence)nutrientBo.getNutrientDesc());
                        KitchenInfoActivity.access$1(KitchenInfoActivity.this, nutrientBo);
                        KitchenInfoActivity.this.setViewDate(nutrientBo, 0.0f);
                        return;
                    }
                    case 2131296266: 
                }
                KitchenInfoActivity.this.finish();
            }
        };
    }

    static /* synthetic */ void access$1(KitchenInfoActivity kitchenInfoActivity, NutrientBo nutrientBo) {
        kitchenInfoActivity.selectNutrient = nutrientBo;
    }

    static /* synthetic */ void access$10(KitchenInfoActivity kitchenInfoActivity, boolean bl) {
        kitchenInfoActivity.needChage = bl;
    }

    static /* synthetic */ String access$4(KitchenInfoActivity kitchenInfoActivity) {
        return kitchenInfoActivity.currentUnit;
    }

    static /* synthetic */ boolean access$5(KitchenInfoActivity kitchenInfoActivity) {
        return kitchenInfoActivity.needChage;
    }

    static /* synthetic */ AutoCompleteTextView access$6(KitchenInfoActivity kitchenInfoActivity) {
        return kitchenInfoActivity.weight_et;
    }

    static /* synthetic */ void access$7(KitchenInfoActivity kitchenInfoActivity, float f) {
        kitchenInfoActivity.lastWeight = f;
    }

    static /* synthetic */ NutrientBo access$8(KitchenInfoActivity kitchenInfoActivity) {
        return kitchenInfoActivity.selectNutrient;
    }

    static /* synthetic */ float access$9(KitchenInfoActivity kitchenInfoActivity) {
        return kitchenInfoActivity.lastWeight;
    }

    private void initView() {
        this.currentUnit = this.getResources().getString(2131361899);
        this.search_et = (KMPAutoComplTextView)this.findViewById(2131296385);
        this.weight_et = (AutoCompleteTextView)this.findViewById(2131296391);
        this.search_btn = (TextView)this.findViewById(2131296390);
        this.btn_mback = (TextView)this.findViewById(2131296266);
        this.search_btn.setOnClickListener(this.btnOnClickListener);
        this.btn_mback.setOnClickListener(this.btnOnClickListener);
        this.Water_excel_title = (TextView)this.findViewById(2131296394);
        this.Energ_excel_title = (TextView)this.findViewById(2131296395);
        this.Protein_excel_title = (TextView)this.findViewById(2131296396);
        this.Lipid_excel_title = (TextView)this.findViewById(2131296397);
        this.Ash_excel_title = (TextView)this.findViewById(2131296398);
        this.Carbohydrt_excel_title = (TextView)this.findViewById(2131296399);
        this.Fiber_excel_title = (TextView)this.findViewById(2131296400);
        this.Sugar_excel_title = (TextView)this.findViewById(2131296401);
        this.Calcium_excel_title = (TextView)this.findViewById(2131296402);
        this.Iron_excel_title = (TextView)this.findViewById(2131296403);
        this.Magnesium_excel_title = (TextView)this.findViewById(2131296404);
        this.Phosphorus_excel_title = (TextView)this.findViewById(2131296405);
        this.Potassium_excel_title = (TextView)this.findViewById(2131296406);
        this.Sodium_excel_title = (TextView)this.findViewById(2131296407);
        this.Zinc_excel_title = (TextView)this.findViewById(2131296408);
        this.Copper_excel_title = (TextView)this.findViewById(2131296409);
        this.Manganese_excel_title = (TextView)this.findViewById(2131296410);
        this.Selenium_excel_title = (TextView)this.findViewById(2131296411);
        this.Vit_C_excel_title = (TextView)this.findViewById(2131296412);
        this.Thiamin_excel_title = (TextView)this.findViewById(2131296413);
        this.Riboflavin_excel_title = (TextView)this.findViewById(2131296414);
        this.Niacin_excel_title = (TextView)this.findViewById(2131296415);
        this.Panto_Acid_excel_title = (TextView)this.findViewById(2131296416);
        this.Vit_B6_excel_title = (TextView)this.findViewById(2131296417);
        this.Folate_Tot_excel_title = (TextView)this.findViewById(2131296418);
        this.Folic_Acid_excel_title = (TextView)this.findViewById(2131296419);
        this.Food_Folate_excel_title = (TextView)this.findViewById(2131296420);
        this.Folate_DFE_excel_title = (TextView)this.findViewById(2131296421);
        this.Choline_Tot_excel_title = (TextView)this.findViewById(2131296422);
        this.Vit_B12_excel_title = (TextView)this.findViewById(2131296423);
        this.Vit_A_IU_excel_title = (TextView)this.findViewById(2131296424);
        this.Vit_A_RAE_excel_title = (TextView)this.findViewById(2131296425);
        this.Retinol_excel_title = (TextView)this.findViewById(2131296426);
        this.Alpha_Carot_excel_title = (TextView)this.findViewById(2131296427);
        this.Beta_Carot_excel_title = (TextView)this.findViewById(2131296428);
        this.Beta_Crypt_excel_title = (TextView)this.findViewById(2131296429);
        this.Lycopene_excel_title = (TextView)this.findViewById(2131296430);
        this.LutZea_excel_title = (TextView)this.findViewById(2131296431);
        this.Vit_E_excel_title = (TextView)this.findViewById(2131296432);
        this.Vit_D_excel_title = (TextView)this.findViewById(2131296433);
        this.Vit_D_IU_excel_title = (TextView)this.findViewById(2131296434);
        this.Vit_K_excel_title = (TextView)this.findViewById(2131296435);
        this.FA_Sat_excel_title = (TextView)this.findViewById(2131296436);
        this.FA_Mono_excel_title = (TextView)this.findViewById(2131296437);
        this.FA_Poly_excel_title = (TextView)this.findViewById(2131296438);
        this.Cholesterol_excel_title = (TextView)this.findViewById(2131296439);
        this.GmWt_1_excel_title = (TextView)this.findViewById(2131296440);
        this.GmWt_2_excel_title = (TextView)this.findViewById(2131296441);
        this.Refuse_Pct_excel_title = (TextView)this.findViewById(2131296442);
        this.search_et.setDatas(CacheHelper.nutrientNameList);
        this.search_et.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener(){

            @Override
            public void onPopupItemClick(CharSequence charSequence) {
                if (TextUtils.isEmpty((CharSequence)charSequence)) {
                    return;
                }
                NutrientBo nutrientBo = CacheHelper.queryNutrientsByName(charSequence.toString());
                if (nutrientBo == null) {
                    Toast.makeText((Context)KitchenInfoActivity.this, (CharSequence)"No Data had been found", (int)0).show();
                    return;
                }
                KitchenInfoActivity.this.search_et.setText((CharSequence)nutrientBo.getNutrientDesc());
                KitchenInfoActivity.access$1(KitchenInfoActivity.this, nutrientBo);
                KitchenInfoActivity.this.setViewDate(nutrientBo, 0.0f);
            }
        });
        this.defaults = new ArrayList();
        this.defaults.add((Object)this.getResources().getString(2131361899));
        this.defaults.add((Object)this.getResources().getString(2131361895));
        this.defaults.add((Object)this.getResources().getString(2131361901));
        this.defaults.add((Object)this.getResources().getString(2131361902));
        this.defaults.add((Object)this.getResources().getString(2131361903));
        this.defaultHintSpinner = new HintSpinner<String>((Spinner)this.findViewById(2131296392), new HintAdapter<String>((Context)this, 2131362071, this.defaults), new HintSpinner.Callback<String>(){

            @Override
            public void onItemSelected(int n, String string2) {
                KitchenInfoActivity.this.showSelectedItem(string2);
            }
        });
        this.defaultHintSpinner.init();
        this.weight_et.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            /*
             * Exception decompiling
             */
            public void onTextChanged(CharSequence var1_1, int var2_2, int var3_3, int var4_4) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // java.util.ConcurrentModificationException
                // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
                // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:485)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
                // org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredIf.transformStructuredChildren(StructuredIf.java:82)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:487)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
                // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.transformStructuredChildren(Block.java:378)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:487)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:649)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:816)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
                // org.benf.cfr.reader.Main.doJar(Main.java:128)
                // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
                // java.lang.Thread.run(Thread.java:841)
                throw new IllegalStateException("Decompilation failed");
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setViewDate(NutrientBo nutrientBo, float f) {
        if (nutrientBo != null) {
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientWater())) {
                if (f == 0.0f) {
                    this.Water_excel_title.setText((CharSequence)nutrientBo.getNutrientWater());
                } else {
                    this.Water_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientWater()))));
                }
            } else {
                this.Water_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientEnerg())) {
                if (f == 0.0f) {
                    this.Energ_excel_title.setText((CharSequence)nutrientBo.getNutrientEnerg());
                } else {
                    this.Energ_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientEnerg()))));
                }
            } else {
                this.Energ_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientProtein())) {
                if (f == 0.0f) {
                    this.Protein_excel_title.setText((CharSequence)nutrientBo.getNutrientProtein());
                } else {
                    this.Protein_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientProtein()))));
                }
            } else {
                this.Protein_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientLipidTot())) {
                if (f == 0.0f) {
                    this.Lipid_excel_title.setText((CharSequence)nutrientBo.getNutrientLipidTot());
                } else {
                    this.Lipid_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientLipidTot()))));
                }
            } else {
                this.Lipid_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientAsh())) {
                if (f == 0.0f) {
                    this.Ash_excel_title.setText((CharSequence)nutrientBo.getNutrientAsh());
                } else {
                    this.Ash_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientAsh()))));
                }
            } else {
                this.Ash_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCarbohydrt())) {
                if (f == 0.0f) {
                    this.Carbohydrt_excel_title.setText((CharSequence)nutrientBo.getNutrientCarbohydrt());
                } else {
                    this.Carbohydrt_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientCarbohydrt()))));
                }
            } else {
                this.Carbohydrt_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFiberTD())) {
                if (f == 0.0f) {
                    this.Fiber_excel_title.setText((CharSequence)nutrientBo.getNutrientFiberTD());
                } else {
                    this.Fiber_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFiberTD()))));
                }
            } else {
                this.Fiber_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientSugarTot())) {
                if (f == 0.0f) {
                    this.Sugar_excel_title.setText((CharSequence)nutrientBo.getNutrientSugarTot());
                } else {
                    this.Sugar_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientSugarTot()))));
                }
            } else {
                this.Sugar_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCalcium())) {
                if (f == 0.0f) {
                    this.Calcium_excel_title.setText((CharSequence)nutrientBo.getNutrientCalcium());
                } else {
                    this.Calcium_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientCalcium()))));
                }
            } else {
                this.Calcium_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientIron())) {
                if (f == 0.0f) {
                    this.Iron_excel_title.setText((CharSequence)nutrientBo.getNutrientIron());
                } else {
                    this.Iron_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientIron()))));
                }
            } else {
                this.Iron_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientMagnesium())) {
                if (f == 0.0f) {
                    this.Magnesium_excel_title.setText((CharSequence)nutrientBo.getNutrientMagnesium());
                } else {
                    this.Magnesium_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientMagnesium()))));
                }
            } else {
                this.Magnesium_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientPhosphorus())) {
                if (f == 0.0f) {
                    this.Phosphorus_excel_title.setText((CharSequence)nutrientBo.getNutrientPhosphorus());
                } else {
                    this.Phosphorus_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientPhosphorus()))));
                }
            } else {
                this.Phosphorus_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientPotassium())) {
                if (f == 0.0f) {
                    this.Potassium_excel_title.setText((CharSequence)nutrientBo.getNutrientPotassium());
                } else {
                    this.Potassium_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientPotassium()))));
                }
            } else {
                this.Potassium_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientSodium())) {
                if (f == 0.0f) {
                    this.Sodium_excel_title.setText((CharSequence)nutrientBo.getNutrientSodium());
                } else {
                    this.Sodium_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientSodium()))));
                }
            } else {
                this.Sodium_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientZinc())) {
                if (f == 0.0f) {
                    this.Zinc_excel_title.setText((CharSequence)nutrientBo.getNutrientZinc());
                } else {
                    this.Zinc_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientZinc()))));
                }
            } else {
                this.Zinc_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCopper())) {
                if (f == 0.0f) {
                    this.Copper_excel_title.setText((CharSequence)nutrientBo.getNutrientCopper());
                } else {
                    this.Copper_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientCopper()))));
                }
            } else {
                this.Copper_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientManganese())) {
                if (f == 0.0f) {
                    this.Manganese_excel_title.setText((CharSequence)nutrientBo.getNutrientManganese());
                } else {
                    this.Manganese_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientManganese()))));
                }
            } else {
                this.Manganese_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientSelenium())) {
                if (f == 0.0f) {
                    this.Selenium_excel_title.setText((CharSequence)nutrientBo.getNutrientSelenium());
                } else {
                    this.Selenium_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientSelenium()))));
                }
            } else {
                this.Selenium_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitc())) {
                if (f == 0.0f) {
                    this.Vit_C_excel_title.setText((CharSequence)nutrientBo.getNutrientVitc());
                } else {
                    this.Vit_C_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitc()))));
                }
            } else {
                this.Vit_C_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientThiamin())) {
                if (f == 0.0f) {
                    this.Thiamin_excel_title.setText((CharSequence)nutrientBo.getNutrientThiamin());
                } else {
                    this.Thiamin_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientThiamin()))));
                }
            } else {
                this.Thiamin_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientRiboflavin())) {
                if (f == 0.0f) {
                    this.Riboflavin_excel_title.setText((CharSequence)nutrientBo.getNutrientRiboflavin());
                } else {
                    this.Riboflavin_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientRiboflavin()))));
                }
            } else {
                this.Riboflavin_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientNiacin())) {
                if (f == 0.0f) {
                    this.Niacin_excel_title.setText((CharSequence)nutrientBo.getNutrientNiacin());
                } else {
                    this.Niacin_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientNiacin()))));
                }
            } else {
                this.Niacin_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientPantoAcid())) {
                if (f == 0.0f) {
                    this.Panto_Acid_excel_title.setText((CharSequence)nutrientBo.getNutrientPantoAcid());
                } else {
                    this.Panto_Acid_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientPantoAcid()))));
                }
            } else {
                this.Panto_Acid_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitB6())) {
                if (f == 0.0f) {
                    this.Vit_B6_excel_title.setText((CharSequence)nutrientBo.getNutrientVitB6());
                } else {
                    this.Vit_B6_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitB6()))));
                }
            } else {
                this.Vit_B6_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFolateTot())) {
                if (f == 0.0f) {
                    this.Folate_Tot_excel_title.setText((CharSequence)nutrientBo.getNutrientFolateTot());
                } else {
                    this.Folate_Tot_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFolateTot()))));
                }
            } else {
                this.Folate_Tot_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFolicAcid())) {
                if (f == 0.0f) {
                    this.Folic_Acid_excel_title.setText((CharSequence)nutrientBo.getNutrientFolicAcid());
                } else {
                    this.Folic_Acid_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFolicAcid()))));
                }
            } else {
                this.Folic_Acid_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFoodFolate())) {
                if (f == 0.0f) {
                    this.Food_Folate_excel_title.setText((CharSequence)nutrientBo.getNutrientFoodFolate());
                } else {
                    this.Food_Folate_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFoodFolate()))));
                }
            } else {
                this.Food_Folate_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFolateDfe())) {
                if (f == 0.0f) {
                    this.Folate_DFE_excel_title.setText((CharSequence)nutrientBo.getNutrientFolateDfe());
                } else {
                    this.Folate_DFE_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFolateDfe()))));
                }
            } else {
                this.Folate_DFE_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCholineTot())) {
                if (f == 0.0f) {
                    this.Choline_Tot_excel_title.setText((CharSequence)nutrientBo.getNutrientCholineTot());
                } else {
                    this.Choline_Tot_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientCholineTot()))));
                }
            } else {
                this.Choline_Tot_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitB12())) {
                if (f == 0.0f) {
                    this.Vit_B12_excel_title.setText((CharSequence)nutrientBo.getNutrientVitB12());
                } else {
                    this.Vit_B12_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitB12()))));
                }
            } else {
                this.Vit_B12_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitAiu())) {
                if (f == 0.0f) {
                    this.Vit_A_IU_excel_title.setText((CharSequence)nutrientBo.getNutrientVitAiu());
                } else {
                    this.Vit_A_IU_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitAiu()))));
                }
            } else {
                this.Vit_A_IU_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitArae())) {
                if (f == 0.0f) {
                    this.Vit_A_RAE_excel_title.setText((CharSequence)nutrientBo.getNutrientVitArae());
                } else {
                    this.Vit_A_RAE_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitArae()))));
                }
            } else {
                this.Vit_A_RAE_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientRetinol())) {
                if (f == 0.0f) {
                    this.Retinol_excel_title.setText((CharSequence)nutrientBo.getNutrientRetinol());
                } else {
                    this.Retinol_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientRetinol()))));
                }
            } else {
                this.Retinol_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientAlphaCarot())) {
                if (f == 0.0f) {
                    this.Alpha_Carot_excel_title.setText((CharSequence)nutrientBo.getNutrientAlphaCarot());
                } else {
                    this.Alpha_Carot_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientAlphaCarot()))));
                }
            } else {
                this.Alpha_Carot_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientBetaCarot())) {
                if (f == 0.0f) {
                    this.Beta_Carot_excel_title.setText((CharSequence)nutrientBo.getNutrientBetaCarot());
                } else {
                    this.Beta_Carot_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientBetaCarot()))));
                }
            } else {
                this.Beta_Carot_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientBetaCrypt())) {
                if (f == 0.0f) {
                    this.Beta_Crypt_excel_title.setText((CharSequence)nutrientBo.getNutrientBetaCrypt());
                } else {
                    this.Beta_Crypt_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientBetaCrypt()))));
                }
            } else {
                this.Beta_Crypt_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientLycopene())) {
                if (f == 0.0f) {
                    this.Lycopene_excel_title.setText((CharSequence)nutrientBo.getNutrientLycopene());
                } else {
                    this.Lycopene_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientLycopene()))));
                }
            } else {
                this.Lycopene_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientLutZea())) {
                if (f == 0.0f) {
                    this.LutZea_excel_title.setText((CharSequence)nutrientBo.getNutrientLutZea());
                } else {
                    this.LutZea_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientLutZea()))));
                }
            } else {
                this.LutZea_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVite())) {
                if (f == 0.0f) {
                    this.Vit_E_excel_title.setText((CharSequence)nutrientBo.getNutrientVite());
                } else {
                    this.Vit_E_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVite()))));
                }
            } else {
                this.Vit_E_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitd())) {
                if (f == 0.0f) {
                    this.Vit_D_excel_title.setText((CharSequence)nutrientBo.getNutrientVitd());
                } else {
                    this.Vit_D_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitd()))));
                }
            } else {
                this.Vit_D_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitDiu())) {
                if (f == 0.0f) {
                    this.Vit_D_IU_excel_title.setText((CharSequence)nutrientBo.getNutrientVitDiu());
                } else {
                    this.Vit_D_IU_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitDiu()))));
                }
            } else {
                this.Vit_D_IU_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientVitK())) {
                if (f == 0.0f) {
                    this.Vit_K_excel_title.setText((CharSequence)nutrientBo.getNutrientVitK());
                } else {
                    this.Vit_K_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientVitK()))));
                }
            } else {
                this.Vit_K_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFaSat())) {
                if (f == 0.0f) {
                    this.FA_Sat_excel_title.setText((CharSequence)nutrientBo.getNutrientFaSat());
                } else {
                    this.FA_Sat_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFaSat()))));
                }
            } else {
                this.FA_Sat_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFaMono())) {
                if (f == 0.0f) {
                    this.FA_Mono_excel_title.setText((CharSequence)nutrientBo.getNutrientFaMono());
                } else {
                    this.FA_Mono_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFaMono()))));
                }
            } else {
                this.FA_Mono_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientFaPoly())) {
                if (f == 0.0f) {
                    this.FA_Poly_excel_title.setText((CharSequence)nutrientBo.getNutrientFaPoly());
                } else {
                    this.FA_Poly_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientFaPoly()))));
                }
            } else {
                this.FA_Poly_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientCholestrl())) {
                if (f == 0.0f) {
                    this.Cholesterol_excel_title.setText((CharSequence)nutrientBo.getNutrientCholestrl());
                } else {
                    this.Cholesterol_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientCholestrl()))));
                }
            } else {
                this.Cholesterol_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientGmWt1())) {
                if (f == 0.0f) {
                    this.GmWt_1_excel_title.setText((CharSequence)nutrientBo.getNutrientGmWt1());
                } else {
                    this.GmWt_1_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientGmWt1()))));
                }
            } else {
                this.GmWt_1_excel_title.setText((CharSequence)"0.0");
            }
            if (!TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientGmWt2())) {
                if (f == 0.0f) {
                    this.GmWt_2_excel_title.setText((CharSequence)nutrientBo.getNutrientGmWt2());
                } else {
                    this.GmWt_2_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientGmWt2()))));
                }
            } else {
                this.GmWt_2_excel_title.setText((CharSequence)"0.0");
            }
            if (TextUtils.isEmpty((CharSequence)nutrientBo.getNutrientRefusePct())) {
                this.Refuse_Pct_excel_title.setText((CharSequence)"0.0");
                return;
            }
            if (f != 0.0f) {
                this.Refuse_Pct_excel_title.setText((CharSequence)UtilTooth.keep2Point(0.01f * (f * Float.parseFloat((String)nutrientBo.getNutrientRefusePct()))));
                return;
            }
            this.Refuse_Pct_excel_title.setText((CharSequence)nutrientBo.getNutrientRefusePct());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showSelectedItem(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2) || string2.equals((Object)this.currentUnit)) {
            return;
        }
        if (this.lastWeight > 0.0f) {
            this.needChage = false;
            if (string2.equals((Object)this.getResources().getString(2131361902))) {
                this.weight_et.setText((CharSequence)UtilTooth.kgToFloz(this.lastWeight));
            } else if (string2.equals((Object)this.getResources().getString(2131361895))) {
                this.weight_et.setText((CharSequence)UtilTooth.kgToLBoz(this.lastWeight));
            } else {
                this.weight_et.setText((CharSequence)UtilTooth.gTogS(this.lastWeight));
            }
        }
        this.currentUnit = string2;
    }

    @Override
    public void natureSelectComplete(NutrientBo nutrientBo) {
        if (nutrientBo != null) {
            this.search_et.setText((CharSequence)nutrientBo.getNutrientDesc());
            this.selectNutrient = nutrientBo;
            this.setViewDate(this.selectNutrient, 0.0f);
        }
    }

    public void onClickBack(View view) {
        this.finish();
    }

    @SuppressLint(value={"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.setContentView(2130903056);
        this.initView();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 3) {
            ((NotificationManager)this.getSystemService("notification")).cancel(0);
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onResume() {
        super.onResume();
    }

}

