/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListView
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Date
 *  java.util.List
 *  java.util.Locale
 */
package com.lefu.es.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.MyTextView3;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordDetailAdaptor
extends BaseAdapter {
    Context cont;
    private LayoutInflater inflater;
    ListView listView;
    Locale local = Locale.getDefault();
    private int resource;
    String scaleT = "";
    public int selectedPosition = -1;
    public float selectedPositionY = 0.0f;
    private List<Records> users;
    private int weightType = 1;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public RecordDetailAdaptor(Context context, List<Records> list, ListView listView, int n) {
        this.listView = listView;
        this.users = list;
        this.resource = n;
        this.inflater = (LayoutInflater)context.getSystemService("layout_inflater");
        this.cont = context;
        Log.v((String)"tag", (String)("lsw:" + UtilConstants.CHOICE_KG));
        if (list == null || list.size() <= 0) return;
        this.scaleT = ((Records)list.get(0)).getScaleType();
        if (this.scaleT.equals((Object)UtilConstants.KITCHEN_SCALE)) {
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB)) {
                this.weightType = 6;
                return;
            }
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                this.weightType = 5;
                return;
            }
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                this.weightType = 1;
                return;
            }
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML)) {
                this.weightType = 7;
                return;
            }
            if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML2)) {
                this.weightType = 8;
                return;
            }
            this.weightType = 1;
            return;
        }
        if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_KG)) {
            this.weightType = 1;
            return;
        }
        if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_LB)) {
            if (this.scaleT.equals((Object)UtilConstants.BODY_SCALE) || this.scaleT.equals((Object)UtilConstants.BATHROOM_SCALE)) {
                this.weightType = 2;
                return;
            }
            if (this.scaleT.equals((Object)UtilConstants.BABY_SCALE)) {
                this.weightType = 9;
                return;
            }
            this.weightType = 5;
            return;
        }
        if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_ST)) {
            if (this.scaleT.equals((Object)UtilConstants.BODY_SCALE)) {
                this.weightType = 3;
                return;
            }
            if (this.scaleT.equals((Object)UtilConstants.BATHROOM_SCALE)) {
                this.weightType = 4;
                return;
            }
            if (this.scaleT.equals((Object)UtilConstants.BABY_SCALE)) {
                this.weightType = 4;
                return;
            }
            this.weightType = 5;
            return;
        }
        if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_FATLB)) {
            this.weightType = 5;
            return;
        }
        this.weightType = 1;
    }

    public int getCount() {
        return this.users.size();
    }

    public Object getItem(int n) {
        return this.users.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n, View view, ViewGroup viewGroup) {
        Date date;
        TextView textView;
        MyTextView3 myTextView3;
        LinearLayout linearLayout;
        Records records;
        ImageView imageView;
        TextView textView2;
        if (view == null) {
            view = this.inflater.inflate(this.resource, null);
            textView = (TextView)view.findViewById(2131296520);
            myTextView3 = (MyTextView3)view.findViewById(2131296521);
            textView2 = (TextView)view.findViewById(2131296522);
            linearLayout = (LinearLayout)view.findViewById(2131296519);
            imageView = (ImageView)view.findViewById(2131296523);
            ViewCache viewCache = new ViewCache();
            viewCache.groupView = textView;
            viewCache.nameView = myTextView3;
            viewCache.bmiView = textView2;
            viewCache.rlayout = linearLayout;
            viewCache.photoView = imageView;
            view.setTag((Object)viewCache);
        } else {
            ViewCache viewCache = (ViewCache)view.getTag();
            textView = viewCache.groupView;
            myTextView3 = viewCache.nameView;
            textView2 = viewCache.bmiView;
            linearLayout = viewCache.rlayout;
            imageView = viewCache.photoView;
        }
        if ((records = (Records)this.users.get(n)).getRecordTime() == null || "".equals((Object)records.getRecordTime().trim())) {
            records.setRecordTime("No Date");
        }
        if (this.selectedPosition == n) {
            textView.setSelected(true);
            myTextView3.setPressed(true);
            textView2.setPressed(true);
            textView.setTextColor(this.cont.getResources().getColor(2131165191));
            myTextView3.setTextWhiteColor();
            textView2.setTextColor(this.cont.getResources().getColor(2131165191));
            linearLayout.setBackgroundDrawable(this.cont.getResources().getDrawable(2130837614));
        } else {
            textView.setSelected(false);
            myTextView3.setPressed(false);
            textView2.setPressed(false);
            textView.setTextColor(this.cont.getResources().getColor(2131165196));
            myTextView3.setTexBlackColor();
            textView2.setTextColor(this.cont.getResources().getColor(2131165196));
            linearLayout.setBackgroundColor(-1);
        }
        if ((date = UtilTooth.stringToTime(records.getRecordTime())) != null) {
            textView.setText((CharSequence)StringUtils.getDateString(date, 5));
        }
        if (records.getRphoto() != null && records.getRphoto().length() > 3) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(4);
        }
        switch (this.weightType) {
            case 1: {
                String string2 = this.scaleT.equals((Object)UtilConstants.BABY_SCALE) ? String.valueOf((double)UtilTooth.round(records.getRweight(), 2)) : String.valueOf((float)records.getRweight());
                if (this.selectedPosition != n) {
                    if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                        myTextView3.setTexts(String.valueOf((Object)string2) + (Object)this.cont.getText(2131361899), null);
                        break;
                    }
                    myTextView3.setTexts(String.valueOf((Object)string2) + (Object)this.cont.getText(2131361891), null);
                    break;
                }
                if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                    myTextView3.setTexts(String.valueOf((Object)string2) + (Object)this.cont.getText(2131361899), null, true);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)string2) + (Object)this.cont.getText(2131361891), null, true);
                break;
            }
            case 2: {
                String string3 = UtilTooth.onePoint(UtilTooth.kgToLB_ForFatScale3(records.getRweight()));
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(String.valueOf((Object)string3) + (Object)this.cont.getText(2131361892), null);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)string3) + (Object)this.cont.getText(2131361892), null, true);
                break;
            }
            case 3: {
                String[] arrstring = UtilTooth.kgToStLbForScaleFat2(records.getRweight());
                if (arrstring[1] != null && arrstring[1].indexOf("/") > 0) {
                    if (this.selectedPosition != n) {
                        myTextView3.setTexts(arrstring[0], arrstring[1]);
                        break;
                    }
                    myTextView3.setTexts(arrstring[0], arrstring[1], true);
                    break;
                }
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(String.valueOf((Object)arrstring[0]) + (Object)this.cont.getText(2131361894), null);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)arrstring[0]) + (Object)this.cont.getText(2131361894), null, true);
                break;
            }
            case 4: {
                String string4 = UtilTooth.kgToStLb(records.getRweight());
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(String.valueOf((Object)string4) + (Object)this.cont.getText(2131361894), null);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)string4) + (Object)this.cont.getText(2131361894), null, true);
                break;
            }
            case 5: {
                if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                    String string5 = UtilTooth.kgToFloz(records.getRweight());
                    if (this.selectedPosition != n) {
                        myTextView3.setTexts(String.valueOf((Object)string5) + (Object)this.cont.getText(2131361902), null);
                        break;
                    }
                    myTextView3.setTexts(String.valueOf((Object)string5) + (Object)this.cont.getText(2131361902), null, true);
                    break;
                }
                String string6 = UtilTooth.kgToLbForScaleBaby(records.getRweight());
                if (string6 != null && string6.indexOf(":") > 0) {
                    String string7 = String.valueOf((Object)string6.substring(0, string6.indexOf(":"))) + (Object)this.cont.getText(2131361892) + ":" + string6.substring(1 + string6.indexOf(":"), string6.length()) + (Object)this.cont.getText(2131361893);
                    if (this.selectedPosition != n) {
                        myTextView3.setTexts(string7, null);
                        break;
                    }
                    myTextView3.setTexts(string7, null, true);
                    break;
                }
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(string6, null);
                    break;
                }
                myTextView3.setTexts(string6, null, true);
                break;
            }
            case 6: {
                String string8 = UtilTooth.kgToLBoz(records.getRweight());
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(String.valueOf((Object)string8) + (Object)this.cont.getText(2131361895), null);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)string8) + (Object)this.cont.getText(2131361895), null, true);
                break;
            }
            case 7: {
                String string9 = String.valueOf((float)records.getRweight());
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(String.valueOf((Object)string9) + (Object)this.cont.getText(2131361901), null);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)string9) + (Object)this.cont.getText(2131361901), null, true);
                break;
            }
            case 8: {
                String string10 = String.valueOf((int)UtilTooth.kgToML(records.getRweight()));
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(String.valueOf((Object)string10) + (Object)this.cont.getText(2131361903), null);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)string10) + (Object)this.cont.getText(2131361903), null, true);
                break;
            }
            case 9: {
                String string11 = String.valueOf((Object)UtilTooth.kgToLB_new(records.getRweight()));
                if (this.selectedPosition != n) {
                    myTextView3.setTexts(String.valueOf((Object)string11) + (Object)this.cont.getText(2131361892), null);
                    break;
                }
                myTextView3.setTexts(String.valueOf((Object)string11) + (Object)this.cont.getText(2131361892), null, true);
            }
        }
        if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
            textView2.setText((CharSequence)records.getRphoto());
            return view;
        }
        textView2.setText((CharSequence)String.valueOf((float)UtilTooth.myround(UtilTooth.countBMI2(records.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0f))));
        return view;
    }

    public void setSelectedPosition(int n) {
        this.selectedPosition = n;
    }

}

