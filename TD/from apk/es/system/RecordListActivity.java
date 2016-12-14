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
 *  android.util.Log
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
 *  java.lang.Float
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Iterator
 *  java.util.List
 *  org.achartengine.GraphicalView
 *  org.achartengine.chart.PointStyle
 *  org.achartengine.chart.TimeChart
 *  org.achartengine.chart.TimeChart$DateChangeCallback
 *  org.achartengine.model.SeriesSelection
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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.RecordService;
import com.lefu.es.system.RecordListItemActivity;
import com.lefu.es.util.Image;
import com.lefu.es.util.SharedPreferencesUtil;
import com.lefu.es.util.StringUtils;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.guideview.HighLightGuideView;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

@SuppressLint(value={"SimpleDateFormat"})
public class RecordListActivity
extends Activity
implements View.OnClickListener,
TimeChart.DateChangeCallback {
    private static final String TAG = "RecordListActivity";
    private TextView back_tv;
    private LinearLayout charcontainer;
    private LinearLayout chartContainer;
    private int dateType = 5;
    private ImageView delallImg;
    private ImageView deleteImg;
    private LinearLayout delist;
    private TextView graph_tv;
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

    public RecordListActivity() {
        this.itemClickListener = new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                RecordListActivity.access$0(RecordListActivity.this, n);
                ListView listView = (ListView)adapterView;
                RecordListActivity.access$1(RecordListActivity.this, (Records)listView.getItemAtPosition(n));
                RecordListActivity.this.mChart.setSeriesSelection(RecordListActivity.this.getchartSelectIndex(RecordListActivity.this.lastRecod.getId()));
                RecordListActivity.this.mChart.invalidate();
                RecordListActivity.this.recordAdaptor.setSelectedPosition(n);
                RecordListActivity.this.recordAdaptor.notifyDataSetInvalidated();
                RecordListActivity.this.handler.sendEmptyMessage(1);
                Intent intent = new Intent();
                intent.setClass((Context)RecordListActivity.this, (Class)RecordListItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", (Serializable)RecordListActivity.this.lastRecod);
                intent.putExtras(bundle);
                RecordListActivity.this.startActivity(intent);
            }
        };
        this.imgOnClickListener = new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                switch (view.getId()) {
                    case 2131296344: {
                        RecordListActivity.this.finish();
                        return;
                    }
                    case 2131296346: {
                        RecordListActivity.this.linebg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837611));
                        RecordListActivity.this.delist.setVisibility(8);
                        RecordListActivity.this.lv.setVisibility(8);
                        RecordListActivity.this.charcontainer.setVisibility(0);
                        return;
                    }
                    case 2131296347: {
                        RecordListActivity.this.linebg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837612));
                        RecordListActivity.this.delist.setVisibility(0);
                        RecordListActivity.this.lv.setVisibility(0);
                        RecordListActivity.this.charcontainer.setVisibility(8);
                        if (TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_INSTALL_SHARE) && CacheHelper.recordListDesc.size() == 1) {
                            RecordListActivity.this.showTipMask2();
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 2131296355: {
                        RecordListActivity.this.deleteImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837556));
                        RecordListActivity.this.delallImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837554));
                        if (RecordListActivity.this.selectedPosition == -1) {
                            Toast.makeText((Context)RecordListActivity.this, (CharSequence)RecordListActivity.this.getString(2131361916), (int)1);
                            return;
                        }
                        RecordListActivity.this.dialog(RecordListActivity.this.getString(2131361847), view.getId());
                        return;
                    }
                    case 2131296356: {
                        RecordListActivity.this.deleteImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837553));
                        RecordListActivity.this.delallImg.setBackgroundDrawable(RecordListActivity.this.getResources().getDrawable(2130837555));
                        RecordListActivity.this.dialog(RecordListActivity.this.getString(2131361849), view.getId());
                        return;
                    }
                    case 2131296345: 
                }
                if (RecordListActivity.this.lastRecod == null) {
                    Toast.makeText((Context)RecordListActivity.this, (CharSequence)RecordListActivity.this.getString(2131361916), (int)0).show();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                if (UtilConstants.CURRENT_SCALE.equals((Object)UtilConstants.BABY_SCALE)) {
                    if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361922)) + RecordListActivity.this.lastRecod.getRweight());
                        stringBuffer.append(RecordListActivity.this.getText(2131361891));
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361922)) + UtilTooth.kgToLB_ForFatScale(Math.abs((float)Float.parseFloat((String)new StringBuilder(String.valueOf((float)RecordListActivity.this.lastRecod.getRweight())).toString()))));
                        stringBuffer.append(RecordListActivity.this.getText(2131361892));
                    }
                    stringBuffer.append("\n");
                    float f = UtilTooth.myround(UtilTooth.countBMI2(RecordListActivity.this.lastRecod.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0f));
                    stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361926)) + f + "\n");
                } else {
                    if (UtilConstants.CURRENT_USER != null) {
                        stringBuffer.append(UtilConstants.CURRENT_USER.getUserName());
                        stringBuffer.append("\n");
                    }
                    stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361921)) + StringUtils.getDateShareString(RecordListActivity.this.lastRecod.getRecordTime(), 6));
                    stringBuffer.append("\n");
                    if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361922)) + RecordListActivity.this.lastRecod.getRweight());
                        stringBuffer.append(RecordListActivity.this.getText(2131361891));
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361922)) + UtilTooth.kgToLB_ForFatScale(Math.abs((float)Float.parseFloat((String)new StringBuilder(String.valueOf((float)RecordListActivity.this.lastRecod.getRweight())).toString()))));
                        stringBuffer.append(RecordListActivity.this.getText(2131361892));
                    }
                    stringBuffer.append("\n");
                    if (!UtilConstants.CURRENT_SCALE.equals((Object)UtilConstants.BATHROOM_SCALE)) {
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361923)) + RecordListActivity.this.lastRecod.getRbodywater() + "%\n");
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361924)) + RecordListActivity.this.lastRecod.getRbodyfat() + "%\n");
                        if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                            stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361925)) + RecordListActivity.this.lastRecod.getRbone());
                            stringBuffer.append((Object)RecordListActivity.this.getText(2131361891) + "\n");
                        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                            stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361925)) + UtilTooth.kgToLB(Math.abs((float)Float.parseFloat((String)new StringBuilder(String.valueOf((float)RecordListActivity.this.lastRecod.getRbone())).toString()))));
                            stringBuffer.append((Object)RecordListActivity.this.getText(2131361892) + "\n");
                        } else {
                            stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361925)) + RecordListActivity.this.lastRecod.getRbone());
                            stringBuffer.append((Object)RecordListActivity.this.getText(2131361891) + "\n");
                        }
                    }
                    float f = UtilTooth.myround(UtilTooth.countBMI2(RecordListActivity.this.lastRecod.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0f));
                    stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361926)) + f + "\n");
                    if (!UtilConstants.CURRENT_SCALE.equals((Object)UtilConstants.BATHROOM_SCALE)) {
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361927)) + RecordListActivity.this.lastRecod.getRvisceralfat() + "\n");
                        stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361928)) + RecordListActivity.this.lastRecod.getRbmr() + " kcal\n");
                        if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                            stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361929)) + RecordListActivity.this.lastRecod.getRmuscle());
                            stringBuffer.append((Object)RecordListActivity.this.getText(2131361891) + "\n");
                        } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                            stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361929)) + UtilTooth.kgToLB(Math.abs((float)Float.parseFloat((String)new StringBuilder(String.valueOf((float)RecordListActivity.this.lastRecod.getRmuscle())).toString()))));
                            stringBuffer.append((Object)RecordListActivity.this.getText(2131361892) + "\n");
                        } else {
                            stringBuffer.append(String.valueOf((Object)RecordListActivity.this.getString(2131361929)) + RecordListActivity.this.lastRecod.getRmuscle());
                            stringBuffer.append((Object)RecordListActivity.this.getText(2131361891) + "\n");
                        }
                    }
                }
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", stringBuffer.toString());
                intent.setType("text/plain");
                RecordListActivity.this.startActivity(intent);
            }
        };
    }

    static /* synthetic */ void access$0(RecordListActivity recordListActivity, int n) {
        recordListActivity.selectedPosition = n;
    }

    static /* synthetic */ void access$1(RecordListActivity recordListActivity, Records records) {
        recordListActivity.lastRecod = records;
    }

    static /* synthetic */ RecordService access$16(RecordListActivity recordListActivity) {
        return recordListActivity.recordService;
    }

    static /* synthetic */ void access$17(RecordListActivity recordListActivity, int n) {
        recordListActivity.recordid = n;
    }

    static /* synthetic */ void access$18(RecordListActivity recordListActivity) {
        recordListActivity.loadData();
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
        this.graph_tv.setOnClickListener(this.imgOnClickListener);
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
        if (TextUtils.isEmpty((CharSequence)UtilConstants.FIRST_INSTALL_DETAIL) && CacheHelper.recordListDesc.size() == 1) {
            this.showTipMask();
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadData() {
        try {
            if (UtilConstants.CURRENT_USER != null) {
                CacheHelper.recordListDesc = this.recordService.getAllDatasByScaleAndIDDesc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), 167.0f);
                CacheHelper.recordList = this.recordService.getAllDatasByScaleAndIDAsc(UtilConstants.CURRENT_SCALE, UtilConstants.CURRENT_USER.getId(), 167.0f);
            }
        }
        catch (Exception var1_1) {
            var1_1.printStackTrace();
        }
        if (CacheHelper.recordList == null) return;
        {
            Date[] arrdate = new Date[CacheHelper.recordList.size()];
            double[] arrd = new double[CacheHelper.recordList.size()];
            this.intiListView(CacheHelper.recordListDesc);
            if (CacheHelper.recordList != null && CacheHelper.recordList.size() > 0) {
                this.lastRecod = (Records)CacheHelper.recordList.get(0);
            }
            int n = 0;
            do {
                if (n >= CacheHelper.recordList.size()) {
                    this.openChart(arrdate, arrd);
                    return;
                }
                Records records = (Records)CacheHelper.recordList.get(n);
                Log.i((String)"RecordListActivity", (String)("record time: " + String.valueOf((Object)records.getRecordTime())));
                arrdate[n] = UtilTooth.stringToTime(records.getRecordTime());
                UtilConstants.isWeight = this.type == 0;
                if (this.type == 0) {
                    arrd[n] = records.getScaleType().contains((CharSequence)UtilConstants.BABY_SCALE) ? (double)UtilTooth.myround2(records.getRweight()) : (double)records.getRweight();
                } else if (this.type == 6) {
                    arrd[n] = records.getRbmi();
                } else if (this.type == 7) {
                    arrd[n] = UtilTooth.myround(records.getRbmr());
                } else if (this.type == 2) {
                    arrd[n] = UtilTooth.myround(records.getRbodyfat());
                } else if (this.type == 4) {
                    arrd[n] = UtilTooth.myround(records.getRbodywater());
                } else if (this.type == 1) {
                    arrd[n] = UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG) ? (double)UtilTooth.myround(records.getRbone()) : (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB) ? Double.parseDouble((String)UtilTooth.kgToLB(records.getRbone())) : (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST) ? Double.parseDouble((String)UtilTooth.kgToLB(records.getRbone())) : (double)UtilTooth.myround(records.getRbone())));
                } else if (this.type == 3) {
                    arrd[n] = UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG) ? (double)UtilTooth.myround(records.getRmuscle()) : (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB) ? Double.parseDouble((String)UtilTooth.kgToLB(records.getRmuscle())) : (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST) ? Double.parseDouble((String)UtilTooth.kgToLB(records.getRmuscle())) : (double)UtilTooth.myround(records.getRmuscle())));
                } else if (this.type == 5) {
                    arrd[n] = UtilTooth.myround(records.getRvisceralfat());
                }
                ++n;
            } while (true);
        }
    }

    /*
     * Exception decompiling
     */
    private void openChart(Date[] var1_1, double[] var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
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

    private void showTipMask() {
        HighLightGuideView.builder(this).setText(this.getString(2131361793)).addNoHighLightGuidView(2130837592).addHighLightGuidView((View)this.list_tv, 0, 1.0f, 1).setTouchOutsideDismiss(false).setOnDismissListener(new HighLightGuideView.OnDismissListener(){

            @Override
            public void onDismiss() {
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)RecordListActivity.this);
                }
                UtilConstants.su.editSharedPreferences("lefuconfig", "first_install_detail", "1");
                UtilConstants.FIRST_INSTALL_DETAIL = "1";
            }
        }).show();
    }

    private void showTipMask2() {
        HighLightGuideView.builder(this).setText(this.getString(2131361794)).addNoHighLightGuidView(2130837592).addHighLightGuidView((View)this.shareImage, 0, 1.0f, 1).setTouchOutsideDismiss(false).setOnDismissListener(new HighLightGuideView.OnDismissListener(){

            @Override
            public void onDismiss() {
                if (UtilConstants.su == null) {
                    UtilConstants.su = new SharedPreferencesUtil((Context)RecordListActivity.this);
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
        this.setContentView(2130903046);
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
                        if (RecordListActivity.this.lv == null || RecordListActivity.this.recordAdaptor == null || RecordListActivity.access$5((RecordListActivity)RecordListActivity.this).selectedPosition < 0) break;
                        RecordListActivity.this.lv.setSelection(RecordListActivity.access$5((RecordListActivity)RecordListActivity.this).selectedPosition);
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

