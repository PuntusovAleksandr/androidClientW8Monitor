/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Iterator
 *  java.util.List
 *  org.achartengine.ChartFactory
 *  org.achartengine.GraphicalView
 *  org.achartengine.chart.PointStyle
 *  org.achartengine.chart.TimeChart
 *  org.achartengine.chart.TimeChart$DateChangeCallback
 *  org.achartengine.chart.XYChart
 *  org.achartengine.model.SeriesSelection
 *  org.achartengine.model.TimeSeries
 *  org.achartengine.model.XYMultipleSeriesDataset
 *  org.achartengine.model.XYSeries
 *  org.achartengine.renderer.SimpleSeriesRenderer
 *  org.achartengine.renderer.XYMultipleSeriesRenderer
 *  org.achartengine.renderer.XYSeriesRenderer
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lefu.es.adapter.RecordDetailAdaptor;
import com.lefu.es.cache.CacheHelper;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
import com.lefu.es.entity.Records;
import com.lefu.es.service.RecordService;
import com.lefu.es.system.RecordListItemActivity;
import com.lefu.es.util.Image;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.GuideView;
import com.lefu.es.view.guideview.HighLightGuideView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.chart.XYChart;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SuppressLint(value={"SimpleDateFormat"})
public class RecordKitchenListActivity
extends Activity
implements View.OnClickListener,
TimeChart.DateChangeCallback {
    private TextView back_tv;
    private LinearLayout charcontainer;
    private LinearLayout chartContainer;
    private int dateType = 5;
    private ImageView delallImg;
    private ImageView deleteImg;
    private LinearLayout delist;
    private TextView graph_tv;
    private GuideView guideView2;
    private Handler handler;
    private ImageView headImage;
    View.OnClickListener imgOnClickListener;
    private AdapterView.OnItemClickListener itemClickListener;
    private Records lastRecod;
    private LinearLayout linebg;
    private TextView list_tv;
    private ListView lv;
    private GraphicalView mChart;
    private RecordDetailAdaptor recordAdaptor;
    private RecordService recordService;
    private int recordid = 0;
    private int selectedPosition = -1;
    private ImageView shareImage;
    private int type = 0;
    private TextView username_tv;

    public RecordKitchenListActivity() {
        this.itemClickListener = new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                RecordKitchenListActivity.access$0(RecordKitchenListActivity.this, n);
                ListView listView = (ListView)adapterView;
                RecordKitchenListActivity.access$1(RecordKitchenListActivity.this, (Records)listView.getItemAtPosition(n));
                RecordKitchenListActivity.this.recordAdaptor.setSelectedPosition(n);
                RecordKitchenListActivity.this.recordAdaptor.notifyDataSetInvalidated();
                RecordKitchenListActivity.this.handler.sendEmptyMessage(1);
                Intent intent = new Intent();
                intent.setClass((Context)RecordKitchenListActivity.this, (Class)RecordListItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", (Serializable)RecordKitchenListActivity.this.lastRecod);
                intent.putExtras(bundle);
                RecordKitchenListActivity.this.startActivity(intent);
            }
        };
        this.imgOnClickListener = new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                switch (view.getId()) {
                    default: {
                        return;
                    }
                    case 2131296344: {
                        RecordKitchenListActivity.this.finish();
                        return;
                    }
                    case 2131296346: {
                        RecordKitchenListActivity.this.linebg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837611));
                        RecordKitchenListActivity.this.delist.setVisibility(8);
                        RecordKitchenListActivity.this.lv.setVisibility(8);
                        RecordKitchenListActivity.this.charcontainer.setVisibility(0);
                        return;
                    }
                    case 2131296347: {
                        RecordKitchenListActivity.this.linebg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837612));
                        RecordKitchenListActivity.this.delist.setVisibility(0);
                        RecordKitchenListActivity.this.lv.setVisibility(0);
                        RecordKitchenListActivity.this.charcontainer.setVisibility(8);
                        return;
                    }
                    case 2131296355: {
                        RecordKitchenListActivity.this.deleteImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837556));
                        RecordKitchenListActivity.this.delallImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837554));
                        if (RecordKitchenListActivity.this.selectedPosition == -1) {
                            Toast.makeText((Context)RecordKitchenListActivity.this, (CharSequence)RecordKitchenListActivity.this.getString(2131361916), (int)1);
                            return;
                        }
                        RecordKitchenListActivity.this.dialog(RecordKitchenListActivity.this.getString(2131361847), view.getId());
                        return;
                    }
                    case 2131296356: {
                        RecordKitchenListActivity.this.deleteImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837553));
                        RecordKitchenListActivity.this.delallImg.setBackgroundDrawable(RecordKitchenListActivity.this.getResources().getDrawable(2130837555));
                        RecordKitchenListActivity.this.dialog(RecordKitchenListActivity.this.getString(2131361849), view.getId());
                        return;
                    }
                    case 2131296345: 
                }
                if (RecordKitchenListActivity.this.lastRecod == null) {
                    Toast.makeText((Context)RecordKitchenListActivity.this, (CharSequence)RecordKitchenListActivity.this.getString(2131361916), (int)0).show();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                if (UtilConstants.CURRENT_USER != null) {
                    stringBuffer.append(UtilConstants.CURRENT_USER.getUserName());
                    stringBuffer.append("\n");
                }
                if (RecordKitchenListActivity.this.lastRecod != null) {
                    stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361921)) + StringUtils.getDateShareString(RecordKitchenListActivity.this.lastRecod.getRecordTime(), 6));
                    stringBuffer.append("\n");
                    stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361920)) + RecordKitchenListActivity.this.lastRecod.getRphoto());
                    stringBuffer.append("\n");
                }
                if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB)) {
                    stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361922)) + UtilTooth.kgToLBoz(RecordKitchenListActivity.this.lastRecod.getRweight()));
                    stringBuffer.append(RecordKitchenListActivity.this.getText(2131361895));
                } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                    stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361922)) + UtilTooth.kgToFloz(RecordKitchenListActivity.this.lastRecod.getRweight()));
                    stringBuffer.append(RecordKitchenListActivity.this.getText(2131361902));
                } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_G)) {
                    stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361922)) + RecordKitchenListActivity.this.lastRecod.getRweight());
                    stringBuffer.append(RecordKitchenListActivity.this.getText(2131361899));
                } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML)) {
                    stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361922)) + RecordKitchenListActivity.this.lastRecod.getRweight());
                    stringBuffer.append(RecordKitchenListActivity.this.getText(2131361901));
                } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML2)) {
                    stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361922)) + UtilTooth.kgToML(RecordKitchenListActivity.this.lastRecod.getRweight()));
                    stringBuffer.append(RecordKitchenListActivity.this.getText(2131361903));
                }
                stringBuffer.append("\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361963)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRmuscle()) + "g\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361964)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbodywater()) + "kcal\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361965)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbodyfat()) + "g\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361966)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbone()) + "g\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361967)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbmr()) + "mg\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361968)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRvisceralfat()) + " g\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361969)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getRbmi()) + "mg\n");
                stringBuffer.append(String.valueOf((Object)RecordKitchenListActivity.this.getString(2131361970)) + UtilTooth.keep2Point(RecordKitchenListActivity.this.lastRecod.getBodyAge()) + "mg\n");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", stringBuffer.toString());
                intent.setType("text/plain");
                RecordKitchenListActivity.this.startActivity(intent);
            }
        };
    }

    static /* synthetic */ void access$0(RecordKitchenListActivity recordKitchenListActivity, int n) {
        recordKitchenListActivity.selectedPosition = n;
    }

    static /* synthetic */ void access$1(RecordKitchenListActivity recordKitchenListActivity, Records records) {
        recordKitchenListActivity.lastRecod = records;
    }

    static /* synthetic */ RecordService access$14(RecordKitchenListActivity recordKitchenListActivity) {
        return recordKitchenListActivity.recordService;
    }

    static /* synthetic */ void access$15(RecordKitchenListActivity recordKitchenListActivity, int n) {
        recordKitchenListActivity.recordid = n;
    }

    static /* synthetic */ void access$16(RecordKitchenListActivity recordKitchenListActivity) {
        recordKitchenListActivity.loadData();
    }

    private void chartClick() {
        new SimpleDateFormat("dd-MMM-yyyy");
        SeriesSelection seriesSelection = this.mChart.getCurrentSeriesAndPoint();
        if (seriesSelection != null) {
            this.mChart.invalidate();
            int n = seriesSelection.getPointIndex();
            if (CacheHelper.recordListDesc != null && n >= 0 && CacheHelper.recordListDesc.size() > n) {
                this.lastRecod = (Records)CacheHelper.recordListDesc.get(n);
                this.selectedPosition = this.getSelectIndex(this.lastRecod.getId());
                this.handler.sendEmptyMessage(1);
            }
        }
    }

    private XYMultipleSeriesRenderer getDemoRenderer() {
        XYMultipleSeriesRenderer xYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
        xYMultipleSeriesRenderer.setAxisTitleTextSize(16.0f);
        xYMultipleSeriesRenderer.setChartTitleTextSize(20.0f);
        xYMultipleSeriesRenderer.setLabelsTextSize(15.0f);
        xYMultipleSeriesRenderer.setLegendTextSize(15.0f);
        xYMultipleSeriesRenderer.setPointSize(6.5f);
        xYMultipleSeriesRenderer.setXLabels(4);
        xYMultipleSeriesRenderer.setXTitle(this.getText(2131361887).toString());
        xYMultipleSeriesRenderer.setXLabelsAlign(Paint.Align.LEFT);
        xYMultipleSeriesRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        xYMultipleSeriesRenderer.setZoomEnabled(true, false);
        xYMultipleSeriesRenderer.setPanEnabled(true, false);
        xYMultipleSeriesRenderer.setAxesColor(-3355444);
        xYMultipleSeriesRenderer.setLabelsColor(-1);
        xYMultipleSeriesRenderer.setApplyBackgroundColor(true);
        xYMultipleSeriesRenderer.setMarginsColor(this.getResources().getColor(2131165219));
        int[] arrn = new int[4];
        arrn[1] = 30;
        arrn[2] = 10;
        xYMultipleSeriesRenderer.setMargins(arrn);
        xYMultipleSeriesRenderer.setClickEnabled(true);
        xYMultipleSeriesRenderer.setSelectableBuffer(40);
        xYMultipleSeriesRenderer.setShowLegend(false);
        xYMultipleSeriesRenderer.setBackgroundColor(this.getResources().getColor(2131165218));
        XYSeriesRenderer xYSeriesRenderer = new XYSeriesRenderer();
        xYSeriesRenderer.setColor(this.getResources().getColor(2131165220));
        xYSeriesRenderer.setLineWidth(5.0f);
        xYSeriesRenderer.setPointStyle(PointStyle.CIRCLE);
        xYSeriesRenderer.setFillBelowLine(true);
        xYSeriesRenderer.setFillBelowLineColor(0);
        xYSeriesRenderer.setFillPoints(true);
        xYSeriesRenderer.setDisplayChartValues(true);
        xYSeriesRenderer.setChartValuesTextSize(20.0f);
        xYMultipleSeriesRenderer.addSeriesRenderer((SimpleSeriesRenderer)xYSeriesRenderer);
        return xYMultipleSeriesRenderer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getSelectIndex(int n) {
        List<Records> list = CacheHelper.recordList;
        int n2 = 0;
        if (list != null) {
            int n3 = CacheHelper.recordList.size();
            n2 = 0;
            if (n3 > 0) {
                Iterator iterator = CacheHelper.recordList.iterator();
                while (iterator.hasNext() && ((Records)iterator.next()).getId() != n) {
                    ++n2;
                }
            }
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getchartSelectIndex(int n) {
        List<Records> list = CacheHelper.recordListDesc;
        int n2 = 0;
        if (list != null) {
            int n3 = CacheHelper.recordListDesc.size();
            n2 = 0;
            if (n3 > 0) {
                Iterator iterator = CacheHelper.recordListDesc.iterator();
                while (iterator.hasNext() && ((Records)iterator.next()).getId() != n) {
                    ++n2;
                }
            }
        }
        return n2;
    }

    private void initView() {
        this.recordService = new RecordService((Context)this);
        this.username_tv = (TextView)this.findViewById(2131296343);
        this.headImage = (ImageView)this.findViewById(2131296298);
        if (UtilConstants.CURRENT_USER != null) {
            this.username_tv.setText((CharSequence)UtilConstants.CURRENT_USER.getUserName());
            if (UtilConstants.CURRENT_USER.getPer_photo() != null && !"".equals((Object)UtilConstants.CURRENT_USER.getPer_photo()) && !UtilConstants.CURRENT_USER.getPer_photo().equals((Object)"null")) {
                Bitmap bitmap = Image.toRoundCorner(new imageUtil().getBitmapTodifferencePath(String.valueOf((Object)UtilConstants.CURRENT_USER.getPer_photo()), (Context)this), 8);
                this.headImage.setImageBitmap(bitmap);
            }
        }
        this.back_tv = (TextView)this.findViewById(2131296344);
        this.back_tv.setOnClickListener(this.imgOnClickListener);
        this.graph_tv = (TextView)this.findViewById(2131296346);
        this.list_tv = (TextView)this.findViewById(2131296347);
        this.list_tv.setOnClickListener(this.imgOnClickListener);
        this.linebg = (LinearLayout)this.findViewById(2131296348);
        this.charcontainer = (LinearLayout)this.findViewById(2131296354);
        this.delist = (LinearLayout)this.findViewById(2131296349);
        this.deleteImg = (ImageView)this.findViewById(2131296355);
        this.delallImg = (ImageView)this.findViewById(2131296356);
        this.shareImage = (ImageView)this.findViewById(2131296345);
        this.deleteImg.setOnClickListener(this.imgOnClickListener);
        this.delallImg.setOnClickListener(this.imgOnClickListener);
        this.shareImage.setOnClickListener(this.imgOnClickListener);
        this.lv = (ListView)this.findViewById(2131296353);
        this.lv.setOnItemClickListener(this.itemClickListener);
        this.lv.setSelector(2130837616);
        this.chartContainer = (LinearLayout)this.findViewById(2131296354);
        this.loadData();
        if (TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_INSTALL_SHARE) && CacheHelper.recordList.size() > 0) {
            this.showTipMask2();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void intiListView(List<Records> var1_1) {
        if (var1_1 != null && var1_1.size() > 0) {
            this.recordAdaptor = new RecordDetailAdaptor(this.getApplicationContext(), var1_1, this.lv, 2130903076);
            if (this.selectedPosition >= var1_1.size()) {
                this.selectedPosition = -1 + var1_1.size();
            }
        } else {
            var2_4 = new ArrayList();
            this.selectedPosition = -1;
            this.recordAdaptor = new RecordDetailAdaptor(this.getApplicationContext(), (List<Records>)var2_4, this.lv, 2130903076);
            this.lv.setAdapter((ListAdapter)this.recordAdaptor);
            return;
        }
        var3_2 = 0;
        var4_3 = var1_1.iterator();
        do {
            if (!var4_3.hasNext()) ** GOTO lbl17
            if (((Records)var4_3.next()).getId() == this.recordid) {
                this.selectedPosition = var3_2;
lbl17: // 2 sources:
                this.selectedPosition = var3_2;
                this.recordAdaptor.setSelectedPosition(this.selectedPosition);
                this.lv.setAdapter((ListAdapter)this.recordAdaptor);
                this.handler.sendEmptyMessage(2);
                return;
            }
            ++var3_2;
        } while (true);
    }

    private void loadData() {
        try {
            if (UtilConstants.CURRENT_USER != null) {
                CacheHelper.recordList = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), 167.0f);
                this.intiListView(CacheHelper.recordList);
            }
            return;
        }
        catch (Exception var1_1) {
            var1_1.printStackTrace();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void openChart(Date[] var1_1, double[] var2_2) {
        if (var1_1 == null) return;
        if (var2_2 == null) return;
        var3_3 = new TimeSeries("Views");
        var4_4 = 500000.0;
        var6_5 = 0.0;
        var8_6 = 0;
        var10_7 = 8888888888888888L;
        var12_8 = 1;
        var13_9 = UtilConstants.CURRENT_SCALE;
        if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_KG)) {
            var12_8 = 1;
        } else if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_LB)) {
            var12_8 = var13_9.equals((Object)UtilConstants.BODY_SCALE) || var13_9.equals((Object)UtilConstants.BATHROOM_SCALE) ? 2 : 5;
        } else if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_ST)) {
            var12_8 = var13_9.equals((Object)UtilConstants.BODY_SCALE) ? 3 : (var13_9.equals((Object)UtilConstants.BATHROOM_SCALE) ? 4 : 5);
        } else if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_FATLB)) {
            var12_8 = 5;
        }
        var14_10 = 0;
        do {
            if (var14_10 >= var1_1.length) {
                var15_14 = new XYMultipleSeriesDataset();
                var15_14.addSeries((XYSeries)var3_3);
                var16_15 = this.getDemoRenderer();
                var17_16 = var4_4 - 10.0 >= 0.0 ? var4_4 - 10.0 : 0.0;
            }
            var28_12 = var1_1[var14_10].getTime();
            if (var28_12 > var8_6) {
                var8_6 = var28_12;
            }
            if (var28_12 < var10_7) {
                var10_7 = var28_12;
            }
            var30_13 = UtilTooth.round(var2_2[var14_10], 2);
            var32_11 = (float)var2_2[var14_10];
            if (!UtilConstants.isWeight) ** GOTO lbl54
            switch (var12_8) {
                case 1: {
                    if (UtilConstants.CURRENT_SCALE.equals((Object)UtilConstants.BABY_SCALE)) {
                        var30_13 = UtilTooth.round(var2_2[var14_10], 2);
                        ** break;
                    }
                    ** GOTO lbl50
                }
                case 2: {
                    var30_13 = UtilTooth.round(UtilTooth.kgToLB_F((float)var30_13), 2);
                    ** break;
                }
                case 3: {
                    var30_13 = UtilTooth.kgToStLb_F((float)var30_13);
                    ** break;
                }
                case 4: {
                    var30_13 = UtilTooth.kgToStLb_B((float)var30_13);
                }
lbl50: // 6 sources:
                default: {
                    ** GOTO lbl54
                }
                case 5: 
            }
            var30_13 = UtilTooth.lbToLBOZ_F(var32_11);
lbl54: // 3 sources:
            if (var30_13 > 0.0) {
                var3_3.add(var1_1[var14_10], var30_13);
                if (var30_13 > var6_5) {
                    var6_5 = var30_13;
                }
                if (var30_13 < var4_4) {
                    var4_4 = var30_13;
                }
            }
            ++var14_10;
        } while (true);
        var16_15.setYAxisMin(var17_16);
        var16_15.setYAxisMax(10.0 + var6_5);
        var19_17 = var8_6;
        var21_18 = Double.parseDouble((String)String.valueOf((long)(var19_17 - 31536000)));
        var23_19 = Double.parseDouble((String)String.valueOf((long)(31536000 + var19_17)));
        var16_15.setXAxisMin(var21_18);
        var16_15.setXAxisMax(var23_19);
        switch (this.type) {
            case 0: {
                if (UtilConstants.UNIT_KG.equals((Object)UtilConstants.CHOICE_KG)) {
                    var16_15.setYTitle(this.getText(2131361818).toString());
                    break;
                }
                if (UtilConstants.UNIT_LB.equals((Object)UtilConstants.CHOICE_KG)) {
                    var16_15.setYTitle(this.getText(2131361820).toString());
                    break;
                }
                if (UtilConstants.UNIT_FATLB.equals((Object)UtilConstants.CHOICE_KG)) {
                    var16_15.setYTitle(this.getText(2131361819).toString());
                    break;
                }
                var16_15.setYTitle(this.getText(2131361821).toString());
                break;
            }
            case 6: {
                var16_15.setYTitle(this.getText(2131361829).toString());
                break;
            }
            case 7: {
                var16_15.setYTitle(this.getText(2131361828).toString());
                break;
            }
            case 2: {
                var16_15.setYTitle(this.getText(2131361824).toString());
                break;
            }
            case 4: {
                var16_15.setYTitle("   " + this.getText(2131361826).toString());
                break;
            }
            case 1: {
                var16_15.setYTitle(this.getText(2131361823).toString());
                break;
            }
            case 3: {
                var16_15.setYTitle("       " + this.getText(2131361825).toString());
                break;
            }
            case 5: {
                var16_15.setYTitle(this.getText(2131361827).toString());
            }
        }
        this.mChart = ChartFactory.getTimeChartView((Context)this.getBaseContext(), (boolean)UtilConstants.isWeight, (XYMultipleSeriesDataset)var15_14, (XYMultipleSeriesRenderer)var16_15, (String)null, (TimeChart.DateChangeCallback)this);
        if (CacheHelper.recordListDesc != null) ** GOTO lbl106
        this.selectedPosition = -1 + var1_1.length;
        ** GOTO lbl113
lbl106: // 1 sources:
        var26_20 = 0;
        var27_21 = CacheHelper.recordListDesc.iterator();
        do {
            if (!var27_21.hasNext()) ** GOTO lbl112
            if (((Records)var27_21.next()).getId() == this.recordid) {
                this.selectedPosition = var26_20;
lbl112: // 2 sources:
                this.selectedPosition = var26_20;
lbl113: // 2 sources:
                this.mChart.setSeriesSelection(this.selectedPosition);
                this.mChart.setSoundEffectsEnabled(false);
                this.mChart.invalidate();
                XYChart.weightType = var12_8;
                this.mChart.setBackgroundColor(0);
                this.mChart.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        RecordKitchenListActivity.this.chartClick();
                    }
                });
                this.chartContainer.removeAllViewsInLayout();
                this.chartContainer.addView((View)this.mChart);
                this.handler.sendEmptyMessage(1);
                return;
            }
            ++var26_20;
        } while (true);
    }

    private void showTipMask() {
        TextView textView = new TextView((Context)this);
        textView.setText((CharSequence)this.getResources().getString(2131361800));
        textView.setTextColor(this.getResources().getColor(2131165191));
        textView.setTextSize(16.0f);
        textView.setGravity(17);
        this.guideView2 = GuideView.Builder.newInstance((Context)this).setTargetView((View)this.shareImage).setCustomGuideView((View)textView).setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.CIRCULAR).setBgColor(this.getResources().getColor(2131165184)).setOnclickListener(new GuideView.OnClickCallback(){

            @Override
            public void onClickedGuideView() {
                RecordKitchenListActivity.this.guideView2.hide();
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)RecordKitchenListActivity.this);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_share", "1");
                UtilConstants.FIRST_INSTALL_SHARE = "1";
            }
        }).build();
        this.guideView2.show();
    }

    private void showTipMask2() {
        HighLightGuideView.builder(this).setText(this.getString(2131361794)).addNoHighLightGuidView(2130837592).addHighLightGuidView((View)this.shareImage, 0, 1.0f, 1).setTouchOutsideDismiss(false).setOnDismissListener(new HighLightGuideView.OnDismissListener(){

            @Override
            public void onDismiss() {
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)RecordKitchenListActivity.this);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_share", "1");
                UtilConstants.FIRST_INSTALL_SHARE = "1";
            }
        }).show();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dateChangeCallback(int n) {
        Date date;
        try {
            Date date2;
            date = date2 = UtilTooth.stringToTime(this.lastRecod.getRecordTime());
        }
        catch (Exception var2_4) {
            date = null;
        }
        switch (n) {
            case 0: {
                GraphicalView.mZoomInEnable = false;
                return;
            }
            case 1: {
                GraphicalView.mZoomInEnable = true;
                GraphicalView.mZoomOutEnable = true;
                return;
            }
            case 2: {
                if (date == null) return;
                {
                    this.dateType = 5;
                    return;
                }
            }
            case 3: {
                if (date == null) return;
                {
                    this.dateType = 5;
                    return;
                }
            }
            case 4: {
                GraphicalView.mZoomInEnable = true;
                GraphicalView.mZoomOutEnable = true;
                if (date == null) return;
                {
                    this.dateType = 5;
                    return;
                }
            }
            default: {
                return;
            }
            case 5: 
        }
        GraphicalView.mZoomOutEnable = false;
        if (date == null) return;
        {
            this.dateType = 5;
            return;
        }
    }

    protected void dialog(String string2, final int n) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setMessage((CharSequence)string2);
        builder.setNegativeButton(2131361855, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(2131361854, new DialogInterface.OnClickListener(){

            /*
             * Exception decompiling
             */
            public void onClick(DialogInterface var1_1, int var2_2) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CASE]], but top level block is 1[TRYBLOCK]
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
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
        builder.create().show();
    }

    public void onClick(View view) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903045);
        this.handler = new Handler(){

            /*
             * Enabled aggressive block sorting
             */
            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        break;
                    }
                    case 2: {
                        if (RecordKitchenListActivity.this.lv == null || RecordKitchenListActivity.this.recordAdaptor == null || RecordKitchenListActivity.access$2((RecordKitchenListActivity)RecordKitchenListActivity.this).selectedPosition < 0) break;
                        RecordKitchenListActivity.this.lv.setSelection(RecordKitchenListActivity.access$2((RecordKitchenListActivity)RecordKitchenListActivity.this).selectedPosition);
                    }
                }
                super.handleMessage(message);
            }
        };
        Bundle bundle2 = this.getIntent().getExtras();
        this.type = bundle2.getInt("type");
        this.recordid = bundle2.getInt("id");
        this.initView();
    }

}

