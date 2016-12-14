/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.lefu.es.entity;

import com.lefu.es.entity.NutrientBo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NutrientAtrrBo
implements Serializable {
    private static final long serialVersionUID = 1;
    private String attrName;
    private String attrValue;

    public NutrientAtrrBo(String string2, String string3) {
        this.attrName = string2;
        this.attrValue = string3;
    }

    public static List<NutrientAtrrBo> formatNutrientAtrrBo(NutrientBo nutrientBo) {
        if (nutrientBo == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        NutrientAtrrBo nutrientAtrrBo = new NutrientAtrrBo("Water(g)", nutrientBo.getNutrientWater());
        NutrientAtrrBo nutrientAtrrBo2 = new NutrientAtrrBo("Energ(kcal)", nutrientBo.getNutrientEnerg());
        NutrientAtrrBo nutrientAtrrBo3 = new NutrientAtrrBo("Protein(g)", nutrientBo.getNutrientProtein());
        NutrientAtrrBo nutrientAtrrBo4 = new NutrientAtrrBo("Ash(g)", nutrientBo.getNutrientAsh());
        NutrientAtrrBo nutrientAtrrBo5 = new NutrientAtrrBo("Carbohydrt(g)", nutrientBo.getNutrientCarbohydrt());
        NutrientAtrrBo nutrientAtrrBo6 = new NutrientAtrrBo("Fiber_TD(g)", nutrientBo.getNutrientFiberTD());
        NutrientAtrrBo nutrientAtrrBo7 = new NutrientAtrrBo("Sugar_Tot(g)", nutrientBo.getNutrientSugarTot());
        NutrientAtrrBo nutrientAtrrBo8 = new NutrientAtrrBo("Calcium(mg)", nutrientBo.getNutrientCalcium());
        NutrientAtrrBo nutrientAtrrBo9 = new NutrientAtrrBo("Iron(mg)", nutrientBo.getNutrientIron());
        NutrientAtrrBo nutrientAtrrBo10 = new NutrientAtrrBo("Magnesium(mg)", nutrientBo.getNutrientMagnesium());
        NutrientAtrrBo nutrientAtrrBo11 = new NutrientAtrrBo("Phosphorus(mg)", nutrientBo.getNutrientPhosphorus());
        NutrientAtrrBo nutrientAtrrBo12 = new NutrientAtrrBo("Potassium(mg)", nutrientBo.getNutrientPotassium());
        NutrientAtrrBo nutrientAtrrBo13 = new NutrientAtrrBo("Sodium(mg)", nutrientBo.getNutrientSodium());
        NutrientAtrrBo nutrientAtrrBo14 = new NutrientAtrrBo("Zinc(mg)", nutrientBo.getNutrientZinc());
        NutrientAtrrBo nutrientAtrrBo15 = new NutrientAtrrBo("Copper(mg)", nutrientBo.getNutrientCopper());
        NutrientAtrrBo nutrientAtrrBo16 = new NutrientAtrrBo("Manganese(mg)", nutrientBo.getNutrientManganese());
        NutrientAtrrBo nutrientAtrrBo17 = new NutrientAtrrBo("Selenium(\u00b5g)", nutrientBo.getNutrientSelenium());
        NutrientAtrrBo nutrientAtrrBo18 = new NutrientAtrrBo("Vit_C(mg)", nutrientBo.getNutrientVitc());
        NutrientAtrrBo nutrientAtrrBo19 = new NutrientAtrrBo("Thiamin(mg)", nutrientBo.getNutrientThiamin());
        NutrientAtrrBo nutrientAtrrBo20 = new NutrientAtrrBo("Riboflavin(mg)", nutrientBo.getNutrientRiboflavin());
        NutrientAtrrBo nutrientAtrrBo21 = new NutrientAtrrBo("Niacin(mg)", nutrientBo.getNutrientNiacin());
        NutrientAtrrBo nutrientAtrrBo22 = new NutrientAtrrBo("Panto_Acid(mg)", nutrientBo.getNutrientPantoAcid());
        NutrientAtrrBo nutrientAtrrBo23 = new NutrientAtrrBo("Vit_B6(mg)", nutrientBo.getNutrientVitB6());
        NutrientAtrrBo nutrientAtrrBo24 = new NutrientAtrrBo("Folate_Tot(\u00b5g)", nutrientBo.getNutrientFolateTot());
        NutrientAtrrBo nutrientAtrrBo25 = new NutrientAtrrBo("Folic_Acid(\u00b5g)", nutrientBo.getNutrientFolicAcid());
        NutrientAtrrBo nutrientAtrrBo26 = new NutrientAtrrBo("Food_Folate(\u00b5g)", nutrientBo.getNutrientFoodFolate());
        NutrientAtrrBo nutrientAtrrBo27 = new NutrientAtrrBo("Folate_DFE(\u00b5g)", nutrientBo.getNutrientFolateDfe());
        NutrientAtrrBo nutrientAtrrBo28 = new NutrientAtrrBo("Choline_Tot(\u00b5g)", nutrientBo.getNutrientCholineTot());
        NutrientAtrrBo nutrientAtrrBo29 = new NutrientAtrrBo("Vit_B12(\u00b5g)", nutrientBo.getNutrientVitB12());
        NutrientAtrrBo nutrientAtrrBo30 = new NutrientAtrrBo("Vit_A_IU(\u00b5g)", nutrientBo.getNutrientVitAiu());
        NutrientAtrrBo nutrientAtrrBo31 = new NutrientAtrrBo("Vit_A_RAE(\u00b5g)", nutrientBo.getNutrientVitArae());
        NutrientAtrrBo nutrientAtrrBo32 = new NutrientAtrrBo("Retinol(\u00b5g)", nutrientBo.getNutrientRetinol());
        NutrientAtrrBo nutrientAtrrBo33 = new NutrientAtrrBo("Alpha_Carot(\u00b5g)", nutrientBo.getNutrientAlphaCarot());
        NutrientAtrrBo nutrientAtrrBo34 = new NutrientAtrrBo("Beta_Carot(\u00b5g)", nutrientBo.getNutrientBetaCarot());
        NutrientAtrrBo nutrientAtrrBo35 = new NutrientAtrrBo("Beta_Crypt(\u00b5g)", nutrientBo.getNutrientBetaCrypt());
        NutrientAtrrBo nutrientAtrrBo36 = new NutrientAtrrBo("Lycopene(\u00b5g)", nutrientBo.getNutrientLycopene());
        NutrientAtrrBo nutrientAtrrBo37 = new NutrientAtrrBo("Lut+Zea(\u00b5g)", nutrientBo.getNutrientLutZea());
        NutrientAtrrBo nutrientAtrrBo38 = new NutrientAtrrBo("Vit_E(\u00b5g)", nutrientBo.getNutrientVite());
        NutrientAtrrBo nutrientAtrrBo39 = new NutrientAtrrBo("Vit_D(\u00b5g)", nutrientBo.getNutrientVitd());
        NutrientAtrrBo nutrientAtrrBo40 = new NutrientAtrrBo("Vit_D_IU(\u00b5g)", nutrientBo.getNutrientVitDiu());
        NutrientAtrrBo nutrientAtrrBo41 = new NutrientAtrrBo("Vit_K(\u00b5g)", nutrientBo.getNutrientVitK());
        NutrientAtrrBo nutrientAtrrBo42 = new NutrientAtrrBo("FA_Sat(g)", nutrientBo.getNutrientFaSat());
        NutrientAtrrBo nutrientAtrrBo43 = new NutrientAtrrBo("FA_Mono(g)", nutrientBo.getNutrientFaMono());
        NutrientAtrrBo nutrientAtrrBo44 = new NutrientAtrrBo("FA_Poly(g)", nutrientBo.getNutrientFaPoly());
        NutrientAtrrBo nutrientAtrrBo45 = new NutrientAtrrBo("Cholestrl(mg)", nutrientBo.getNutrientCholestrl());
        NutrientAtrrBo nutrientAtrrBo46 = new NutrientAtrrBo("GmWt_1", nutrientBo.getNutrientGmWt1());
        NutrientAtrrBo nutrientAtrrBo47 = new NutrientAtrrBo("GmWt_2", nutrientBo.getNutrientGmWt2());
        NutrientAtrrBo nutrientAtrrBo48 = new NutrientAtrrBo("Refuse_Pct", nutrientBo.getNutrientRefusePct());
        arrayList.add((Object)nutrientAtrrBo);
        arrayList.add((Object)nutrientAtrrBo2);
        arrayList.add((Object)nutrientAtrrBo3);
        arrayList.add((Object)nutrientAtrrBo4);
        arrayList.add((Object)nutrientAtrrBo5);
        arrayList.add((Object)nutrientAtrrBo6);
        arrayList.add((Object)nutrientAtrrBo7);
        arrayList.add((Object)nutrientAtrrBo8);
        arrayList.add((Object)nutrientAtrrBo9);
        arrayList.add((Object)nutrientAtrrBo10);
        arrayList.add((Object)nutrientAtrrBo11);
        arrayList.add((Object)nutrientAtrrBo12);
        arrayList.add((Object)nutrientAtrrBo13);
        arrayList.add((Object)nutrientAtrrBo14);
        arrayList.add((Object)nutrientAtrrBo15);
        arrayList.add((Object)nutrientAtrrBo16);
        arrayList.add((Object)nutrientAtrrBo17);
        arrayList.add((Object)nutrientAtrrBo18);
        arrayList.add((Object)nutrientAtrrBo19);
        arrayList.add((Object)nutrientAtrrBo20);
        arrayList.add((Object)nutrientAtrrBo21);
        arrayList.add((Object)nutrientAtrrBo22);
        arrayList.add((Object)nutrientAtrrBo23);
        arrayList.add((Object)nutrientAtrrBo24);
        arrayList.add((Object)nutrientAtrrBo25);
        arrayList.add((Object)nutrientAtrrBo26);
        arrayList.add((Object)nutrientAtrrBo27);
        arrayList.add((Object)nutrientAtrrBo28);
        arrayList.add((Object)nutrientAtrrBo29);
        arrayList.add((Object)nutrientAtrrBo30);
        arrayList.add((Object)nutrientAtrrBo31);
        arrayList.add((Object)nutrientAtrrBo32);
        arrayList.add((Object)nutrientAtrrBo33);
        arrayList.add((Object)nutrientAtrrBo34);
        arrayList.add((Object)nutrientAtrrBo35);
        arrayList.add((Object)nutrientAtrrBo36);
        arrayList.add((Object)nutrientAtrrBo37);
        arrayList.add((Object)nutrientAtrrBo38);
        arrayList.add((Object)nutrientAtrrBo39);
        arrayList.add((Object)nutrientAtrrBo40);
        arrayList.add((Object)nutrientAtrrBo41);
        arrayList.add((Object)nutrientAtrrBo42);
        arrayList.add((Object)nutrientAtrrBo43);
        arrayList.add((Object)nutrientAtrrBo44);
        arrayList.add((Object)nutrientAtrrBo45);
        arrayList.add((Object)nutrientAtrrBo46);
        arrayList.add((Object)nutrientAtrrBo47);
        arrayList.add((Object)nutrientAtrrBo48);
        return arrayList;
    }

    public String getAttrName() {
        return this.attrName;
    }

    public String getAttrValue() {
        return this.attrValue;
    }

    public void setAttrName(String string2) {
        this.attrName = string2;
    }

    public void setAttrValue(String string2) {
        this.attrValue = string2;
    }
}

