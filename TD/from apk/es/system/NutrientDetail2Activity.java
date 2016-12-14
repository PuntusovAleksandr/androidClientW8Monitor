/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  java.io.Serializable
 *  java.lang.Class
 *  java.lang.String
 *  java.util.List
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lefu.es.adapter.NutrientDetailAdapter;
import com.lefu.es.entity.NutrientAtrrBo;
import com.lefu.es.entity.NutrientBo;

import java.io.Serializable;

@SuppressLint(value={"SimpleDateFormat"})
public class NutrientDetail2Activity
extends Activity
implements View.OnClickListener {
    private TextView back_tv;
    private ListView detailist_contains;
    private TextView list_textview;
    private NutrientDetailAdapter nutrientAdapter = null;
    private NutrientBo nutrientBo = null;

    public static Intent creatIntent(Context context, NutrientBo nutrientBo) {
        Intent intent = new Intent(context, (Class)NutrientDetail2Activity.class);
        intent.putExtra("nutrient", (Serializable)nutrientBo);
        return intent;
    }

    private void initDate(NutrientBo nutrientBo) {
        if (nutrientBo != null) {
            this.nutrientAdapter = new NutrientDetailAdapter((Context)this, NutrientAtrrBo.formatNutrientAtrrBo(nutrientBo), nutrientBo.getFoodWeight());
            this.detailist_contains.setAdapter((ListAdapter)this.nutrientAdapter);
        }
    }

    private void initView() {
        this.back_tv = (TextView)this.findViewById(2131296344);
        this.list_textview = (TextView)this.findViewById(2131296347);
        this.detailist_contains = (ListView)this.findViewById(2131296353);
        this.back_tv.setOnClickListener((View.OnClickListener)this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            default: {
                return;
            }
            case 2131296482: 
        }
        this.finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903058);
        this.nutrientBo = (NutrientBo)this.getIntent().getSerializableExtra("nutrient");
        this.initView();
        this.initDate(this.nutrientBo);
    }
}

