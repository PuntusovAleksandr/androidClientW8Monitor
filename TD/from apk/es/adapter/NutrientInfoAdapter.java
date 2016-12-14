/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.List
 */
package com.lefu.es.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lefu.es.entity.NutrientBo;

import java.util.List;

public class NutrientInfoAdapter
extends BaseAdapter {
    private List<NutrientBo> atrrArray;
    private Context context;

    public NutrientInfoAdapter(Context context, List<NutrientBo> list) {
        this.atrrArray = list;
        this.context = context;
    }

    public void appendList(List<NutrientBo> list) {
        if (!this.atrrArray.containsAll(list) && list != null && list.size() > 0) {
            this.atrrArray.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    public void clear() {
        this.atrrArray.clear();
        this.notifyDataSetChanged();
    }

    public void clearList(List<NutrientBo> list) {
        if (this.atrrArray != null) {
            this.atrrArray.clear();
            this.atrrArray.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    public int getCount() {
        if (this.atrrArray == null || this.atrrArray.size() == 0) {
            return 0;
        }
        return this.atrrArray.size();
    }

    public Object getItem(int n) {
        if (this.atrrArray == null || this.atrrArray.size() == 0) {
            return null;
        }
        return this.atrrArray.get(n);
    }

    public long getItemId(int n) {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate((Context)this.context, (int)2130903074, (ViewGroup)null);
            viewHolder.atrrName = (TextView)view.findViewById(2131296507);
            viewHolder.excel_Carbohydrt = (TextView)view.findViewById(2131296508);
            viewHolder.excel_Energ = (TextView)view.findViewById(2131296509);
            viewHolder.excel_Protein = (TextView)view.findViewById(2131296510);
            viewHolder.excel_Lipid = (TextView)view.findViewById(2131296511);
            viewHolder.excel_VitC = (TextView)view.findViewById(2131296512);
            viewHolder.excel_Fiber = (TextView)view.findViewById(2131296513);
            viewHolder.excel_Cholesterol = (TextView)view.findViewById(2131296514);
            viewHolder.excel_Calcium = (TextView)view.findViewById(2131296515);
            view.setTag((Object)viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.atrrName.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientDesc());
        viewHolder.excel_Carbohydrt.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientCarbohydrt());
        viewHolder.excel_Energ.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientEnerg());
        viewHolder.excel_Protein.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientProtein());
        viewHolder.excel_Lipid.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientLipidTot());
        viewHolder.excel_VitC.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientVitc());
        viewHolder.excel_Fiber.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientFiberTD());
        viewHolder.excel_Cholesterol.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientCholestrl());
        viewHolder.excel_Calcium.setText((CharSequence)((NutrientBo)this.atrrArray.get(n)).getNutrientCalcium());
        return view;
    }

    class ViewHolder {
        TextView atrrName;
        TextView excel_Calcium;
        TextView excel_Carbohydrt;
        TextView excel_Cholesterol;
        TextView excel_Energ;
        TextView excel_Fiber;
        TextView excel_Lipid;
        TextView excel_Protein;
        TextView excel_VitC;

        ViewHolder() {
        }
    }

}

