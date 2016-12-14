/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package com.lefu.es.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lefu.es.entity.NutrientAtrrBo;
import com.lefu.es.util.Tool;
import com.lefu.es.util.UtilTooth;

import java.util.List;

public class NutrientDetailAdapter
extends BaseAdapter {
    private List<NutrientAtrrBo> atrrArray;
    private Context context;
    private float mWeight;

    public NutrientDetailAdapter(Context context, List<NutrientAtrrBo> list, float f) {
        this.atrrArray = list;
        this.context = context;
        this.mWeight = f;
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
            view = View.inflate((Context)this.context, (int)2130903079, (ViewGroup)null);
            viewHolder.atrrName = (TextView)view.findViewById(2131296530);
            viewHolder.atrrValue = (TextView)view.findViewById(2131296531);
            view.setTag((Object)viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.atrrName.setText((CharSequence)((NutrientAtrrBo)this.atrrArray.get(n)).getAttrName());
        if (!TextUtils.isEmpty((CharSequence)((NutrientAtrrBo)this.atrrArray.get(n)).getAttrValue()) && Tool.isDigitsOnly(((NutrientAtrrBo)this.atrrArray.get(n)).getAttrValue())) {
            float f = 0.01f * (Float.parseFloat((String)((NutrientAtrrBo)this.atrrArray.get(n)).getAttrValue()) * this.mWeight);
            viewHolder.atrrValue.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(f)));
            return view;
        }
        viewHolder.atrrValue.setText((CharSequence)"0.0");
        return view;
    }

    class ViewHolder {
        TextView atrrName;
        TextView atrrValue;

        ViewHolder() {
        }
    }

}

