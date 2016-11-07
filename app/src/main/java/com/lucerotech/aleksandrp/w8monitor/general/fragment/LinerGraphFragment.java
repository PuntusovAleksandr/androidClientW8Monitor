package com.lucerotech.aleksandrp.w8monitor.general.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.adapter.CirclePagerAdapterMain;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.ParamsBody;
import com.lucerotech.aleksandrp.w8monitor.general.MainActivity;
import com.lucerotech.aleksandrp.w8monitor.general.MainActivityPresenter;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.view.MyMarkerView;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.view.ViewPagerCustomDuration;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lucerotech.aleksandrp.w8monitor.R.color.color_light_text;
import static com.lucerotech.aleksandrp.w8monitor.R.color.color_light_text_light;
import static com.lucerotech.aleksandrp.w8monitor.R.color.color_light_text_transparent_light;
import static com.lucerotech.aleksandrp.w8monitor.R.color.pageColor_light;
import static com.lucerotech.aleksandrp.w8monitor.R.id.rl_view;
import static com.lucerotech.aleksandrp.w8monitor.utils.FontsTextView.getFontRoboLight;
import static com.lucerotech.aleksandrp.w8monitor.utils.FontsTextView.getFontRobotoLight;
import static com.lucerotech.aleksandrp.w8monitor.utils.GetSizeWindow.getSizeWindow;
import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.logger;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.MAX_VALUE_PICKER;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_BONE_MASS;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_CALORIES;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_DAY;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_DAY_;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_FAT;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_FAT_LEVEL;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_MONTH;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_MONTH_;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_MUSCLE_MASS;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_WATER;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_WEEK;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_WEEK_;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_WEIGHT;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_YEAR;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_YEAR_;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.TIME_IN_DAY;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.TIME_IN_MONTH;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.TIME_IN_WEEK;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.TIME_IN_YEAR;
import static com.lucerotech.aleksandrp.w8monitor.utils.TranslateToMetric.translateToMetricFloat;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public class LinerGraphFragment extends Fragment implements LinerGraphView {

    private Context mContext;
    private MainActivityPresenter mPresenter;
    private MainActivity mActivity;

    private CirclePagerAdapterMain mAdapter, mAdapterTop;

    private String nameLabes;

    private int mValue;

    protected Typeface mTfLight;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView mBack;

    @Bind(R.id.tv_context_param)
    TextView tv_context_param;

    @Bind(R.id.lc_chart)
    LineChart mChart;

    @Bind(R.id.walk_through_view_pager_main)
    ViewPagerCustomDuration mViewPager;
    @Bind(R.id.walk_through_view_pager_main_top)
    ViewPagerCustomDuration mViewPagerTop;

    @Bind(R.id.circle_pager_indicator_main)
    CirclePageIndicator circlePageIndicator;
    @Bind(R.id.circle_pager_indicator_main_top)
    CirclePageIndicator circlePageIndicatorTop;

    @Bind(rl_view)
    RelativeLayout rl_view_liner_BUTTON;
    @Bind(R.id.rl_view_liner)
    RelativeLayout rl_view_liner_TOP;

    private String[] stringArrayContext;
    private SimpleDateFormat sdfs = null;
    //    private SimpleDateFormat sdfs2 = null;
    private float addMinX = 0;
    private int showCountValue = 2;

    private float MINUTE_COUNT = 1000 * 60;
    private float HOUR_COUNT = MINUTE_COUNT * 60;
    private float DAY_COUNT = HOUR_COUNT * 24;
    private float WEEK_COUNT = DAY_COUNT * 7;
    private float MONTH_COUNT = DAY_COUNT * 31;

    private String clorCircle = "#FFE8E7E9";

    private ArrayList<String> yVals1;

    public LinerGraphFragment() {
    }

    public LinerGraphFragment(Context mContext, MainActivityPresenter mPresenter, int mValue) {
        this.mContext = mContext;
        this.mPresenter = mPresenter;
        this.mActivity = (MainActivity) mContext;
        this.mValue = mValue;
        if (SettingsApp.getInstance().isThemeDark()) {
            clorCircle = "#FF071653";
        }
        this.yVals1 = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_line_graph, container, false);
        ButterKnife.bind(this, view);
        mTfLight = getFontRoboLight();

        stringArrayContext = getResources().getStringArray(R.array.select_weight_params_context);

        setViewPagerBottom();
        setViewPagerTop();

        setChart();

        tv_context_param.setTypeface(getFontRobotoLight());

        getDataFromDb();

        return view;
    }

    private void getDataFromDb() {
        long timeNow = new Date().getTime();
        long timeStart = timeNow;


        switch (mViewPagerTop.getCurrentItem() + 1) {
            case PICKER_DAY:
            case PICKER_DAY_:
                timeStart = timeNow - TIME_IN_DAY;
                sdfs = new SimpleDateFormat("HH:mm");
//                sdfs2 = new SimpleDateFormat("HH:mm");
                mChart.getXAxis().setTextSize(16f);
                addMinX = HOUR_COUNT;
                showCountValue = 4;
                break;
            case PICKER_WEEK:
            case PICKER_WEEK_:
                timeStart = timeNow - TIME_IN_WEEK;
                sdfs = new SimpleDateFormat("E");
//                sdfs2 = new SimpleDateFormat("E");
                mChart.getXAxis().setTextSize(16f);
                addMinX = HOUR_COUNT * 12;
                showCountValue = 6;
                break;
            case PICKER_MONTH:
            case PICKER_MONTH_:
                timeStart = timeNow - TIME_IN_MONTH;
                sdfs = new SimpleDateFormat("dd.M");
//                sdfs2 = new SimpleDateFormat("dd.M");
                mChart.getXAxis().setTextSize(15f);
                addMinX = DAY_COUNT;
                showCountValue = 5;
                break;
            case PICKER_YEAR:
            case PICKER_YEAR_:
                timeStart = timeNow - TIME_IN_YEAR;
                sdfs = new SimpleDateFormat("dd.M.yy");
//                sdfs2 = new SimpleDateFormat("dd.M.yy");
                mChart.getXAxis().setTextSize(10f);
                addMinX = WEEK_COUNT;
                showCountValue = 6;
                break;
        }

        mPresenter.getDataForLineChart(timeNow, timeStart, mViewPager.getCurrentItem() + 1, this);
    }

    private void setTextDescription(int mPickerBottomValue) {
        tv_context_param.setText(stringArrayContext[mPickerBottomValue - 1]);
    }

    // formatter value in axis Y
    private IAxisValueFormatter formatterY = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return String.valueOf((int) value + nameLabes);
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    };

    // formatter value in axis X
    private IAxisValueFormatter formatterX = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String textReturn = "";
            if (value > 0) {

                try {
                    int index = ((int) value) - 1;
                    if (index < yVals1.size()) {
                        textReturn = yVals1.get(index);
                    }
//                long timeValueLong = (long) value;
//                Date date = new Date(timeValueLong);
                } catch (ArrayIndexOutOfBoundsException mE) {
                    mE.printStackTrace();
                }
            }
            logger("getFormattedValue; value::: " + value);
            return textReturn;
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    };

    private void setChart() {

        mChart.setBackgroundColor(Color.TRANSPARENT);
//        mChart.fitScreen();

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(false);

        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxVisibleValueCount(100);
        mChart.setDrawBorders(false);

        mChart.getAxisRight().setEnabled(false);
        mChart.animateXY(2000, 2000);

        // легенда необходима для показа цифр по оси x
        Legend legend = mChart.getLegend();
        legend.setTextSize(0f);
        legend.setTextColor(Color.TRANSPARENT);
        legend.setForm(Legend.LegendForm.EMPTY);        // скрываем легенду


        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart


        XAxis x = mChart.getXAxis();
        x.setTypeface(mTfLight);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(true);
        x.setTextSize(16f);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setCenterAxisLabels(true);
        x.setDrawLabels(true);
        x.setValueFormatter(formatterX);        // форматирование теста


        YAxis y = mChart.getAxisLeft();
        y.setTypeface(mTfLight);
        y.setLabelCount(3, false);
        y.setTextSize(12f);
//        y.setAxisMinValue(-10);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.TRANSPARENT);
        y.setValueFormatter(formatterY);        // форматирование теста


        setColorFromTheme(x, y);
    }

    private void setColorFromTheme(XAxis mX, YAxis mY) {
        if (SettingsApp.getInstance().isThemeDark()) {
            mX.setTextColor(getActivity().getResources().getColor(R.color.color_light_text));
            mX.setAxisLineColor(getActivity().getResources().getColor(R.color.color_select_tab));
            mY.setTextColor(getActivity().getResources().getColor(R.color.color_light_text));
            mY.setGridColor(getActivity().getResources().getColor(R.color.color_default_tab));
        } else {
            mX.setTextColor(getActivity().getResources().getColor(R.color.color_light_text_light));
            mX.setAxisLineColor(getActivity().getResources().getColor(R.color.color_select_tab_light));
            mY.setTextColor(getActivity().getResources().getColor(R.color.color_light_text_light));
            mY.setGridColor(getActivity().getResources().getColor(R.color.color_select_tab_light));
        }
    }

    private void setColorChart(LineDataSet mSet1, LineDataSet mSet2) {
        if (SettingsApp.getInstance().isThemeDark()) {
//            mSet1.setCircleColor(getActivity().getResources().getColor(color_light_text_transparent));
            mSet1.setCircleColor(Color.parseColor("#4bf7f9"));
            mSet1.setFillColor(Color.parseColor("#4bf7f9"));

            mSet1.setHighLightColor(getActivity().getResources().getColor(color_light_text));
            mSet1.setColor(getActivity().getResources().getColor(color_light_text));


//            mSet2.setCircleColor(getActivity().getResources().getColor(color_light_text_transparent));
//            mSet2.setHighLightColor(getActivity().getResources().getColor(color_light_text));
            mSet2.setColor(getActivity().getResources().getColor(color_light_text_transparent_light));
        } else {
//            mSet1.setCircleColor(getActivity().getResources().getColor(color_light_text_transparent_light));
            mSet1.setCircleColor(Color.parseColor("#000000"));
            mSet1.setFillColor(Color.parseColor("#000000"));

            mSet1.setHighLightColor(getActivity().getResources().getColor(color_light_text_light));
            mSet1.setColor(getActivity().getResources().getColor(color_light_text_light));


//            mSet2.setCircleColor(getActivity().getResources().getColor(color_light_text_transparent_light));
//            mSet2.setHighLightColor(getActivity().getResources().getColor(color_light_text_light));
            mSet2.setColor(getActivity().getResources().getColor(pageColor_light));
        }
    }

    private void setData(final ArrayList<Entry> yVals, final ArrayList<Entry> yVals2) {

        // основная линия
        LineDataSet set1;
        // дополнительная (тень) лингия
        final LineDataSet set2;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Main");
        set2 = new LineDataSet(yVals2, "Main2");

        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
//        set1.setDrawFilled(true);
        set1.setDrawFilled(false);

//        if (yVals.size() == 1) {
            set1.setDrawCircles(true);
//            set1.setDrawFilled(true);
            set1.setDrawCircleHole(false);      // чтобы круг был без отверстия
//        } else {
//            set1.setDrawCircles(false);
//        }
        set1.setLineWidth(5.0f);
        set1.setCircleRadius(2f);
        set1.setFillAlpha(255);
        set1.setValueTextColor(Color.TRANSPARENT);
        set1.setDrawVerticalHighlightIndicator(false);
        set1.setDrawHorizontalHighlightIndicator(false);

        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setCubicIntensity(0.2f);
        set2.setDrawFilled(true);
        set2.setDrawCircles(false);
        set2.setLineWidth(6.0f);
        set2.setCircleRadius(4f);
        set2.setFillColor(Color.TRANSPARENT);
        set2.setFillAlpha(0);
        set2.setValueTextColor(Color.TRANSPARENT);
        set2.setDrawVerticalHighlightIndicator(false);
        set2.setDrawHorizontalHighlightIndicator(false);

        setColorChart(set1, set2);

        final ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        if (yVals2.size() > 3)  // убираем тень если мало показаний
        dataSets.add(set2); // add the datasets
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        data.setDrawValues(false);

        // FIXED BUG THEN :
        //7. На графику лучше спрятать штуку где значение пишется в черной хуйне.
        // Когда только одно значение она показывает не туда куда надо и в целом
        // мешает только, на iOS нет ее.
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<Entry> entriesForXValue = yVals;
//
//                if (entriesForXValue != null && entriesForXValue.size() > 0) {
//                    Entry entry = entriesForXValue.get(entriesForXValue.size() - 1);
//                    float x = entry.getX();
//                    float y = entry.getY();
//                    mChart.highlightValue(x, y, 1);
//                    mChart.invalidate();
//                }
//            }
//        }, 20);

        // set data
        mChart.setData(data);
        mChart.invalidate();
    }

    private void setViewPagerBottom() {
        mViewPager.setScrollDurationFactor(1); // make the animation twice as slow
        mAdapter = new CirclePagerAdapterMain(getActivity().getSupportFragmentManager(), MAX_VALUE_PICKER);
        mViewPager.setAdapter(mAdapter);
        circlePageIndicator.setViewPager(mViewPager, mValue - 1);


//        mViewPager.setPageTransformer(false, mAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        mViewPager.setCurrentItem(mValue);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        mViewPager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        mViewPager.setPageMargin(getSizeWindow(getActivity()));


        final GestureDetector gestureDetectorButton =
                new GestureDetector(getActivity(), new MyGestureDetectorButton());
        View.OnTouchListener gestureListenerButton = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                setTouchValueButton(event);
                return gestureDetectorButton.onTouchEvent(event);
            }
        };

        final GestureDetector gestureDetectorTOP =
                new GestureDetector(getActivity(), new MyGestureDetectorTOP());
        View.OnTouchListener gestureListenerTOP = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                setToushValueTOP(event);
                return gestureDetectorTOP.onTouchEvent(event);
            }
        };

        rl_view_liner_TOP.setOnTouchListener(gestureListenerTOP);
        rl_view_liner_BUTTON.setOnTouchListener(gestureListenerButton);
    }


    private boolean scrollBoolean;
    private boolean scrollBooleanTOP;

    private void setTouchValueButton(final MotionEvent event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = rl_view_liner_BUTTON.getWidth();
                int height = rl_view_liner_BUTTON.getHeight();
                float x = event.getX();
                float y = event.getRawY();

                if (event.getAction() == MotionEvent.ACTION_UP &&
                        !scrollBoolean) {
                    scrollBoolean = true;
                    if (x < width / 2 &&
                            y > height) {
                        setValuePicker(true);
//                        Toast.makeText(getActivity(), "Left", Toast.LENGTH_SHORT).show();
                    } else if (x > width / 2 &&
                            y > height) {
                        setValuePicker(false);
//                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, 50);
    }

    public void setValuePicker(boolean left) {
        int oldValue = mViewPager.getCurrentItem();
        if (left) {
            if (oldValue + 1 > PICKER_WATER) {
                mViewPager.setCurrentItem(oldValue - 1);
            }
        } else if (oldValue + 1 < PICKER_FAT) {
            mViewPager.setCurrentItem(oldValue + 1);
        } else return;

        oldValue = mViewPager.getCurrentItem();
        circlePageIndicator.setViewPager(mViewPager, oldValue);
        getDataFromDb();
        setScrollHandler();
    }


    private void setViewPagerTop() {
        mViewPagerTop.setScrollDurationFactor(1); // make the animation twice as slow
        mAdapterTop = new CirclePagerAdapterMain(getActivity().getSupportFragmentManager(), 4);
        mViewPagerTop.setAdapter(mAdapterTop);
        circlePageIndicatorTop.setViewPager(mViewPagerTop, 0);

//        mViewPagerTop.setPageTransformer(false, mAdapterTop);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        mViewPagerTop.setCurrentItem(0);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        mViewPagerTop.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        mViewPagerTop.setPageMargin(getSizeWindow(getActivity()));

        mViewPagerTop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos = position;
//                if (position == 1 || position == 5) {
//                    pos = 0;
//                } else if (position == 2 || position == 6) {
//                    pos = 1;
//                } else if (position == 3 || position == 7) {
//                    pos = 2;
//                } else if (position == 4 || position == 8) {
//                    pos = 3;
//                }
                getDataFromDb();
//                mChart.invalidate();
                circlePageIndicatorTop.setViewPager(mViewPagerTop, pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //    ====================================================
//            onClick
//    ====================================================
    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBack() {
        mActivity.setCircleFragment();
    }

    //    ====================================================================
    //     END        onClick
    //    ====================================================================
    //            from LinerGraphView
    //    ====================================================================

    @Override
    public void getDataForLineChart(List<ParamsBody> mAllSorted, int mPickerBottomValue) {
        setTextDescription(mPickerBottomValue);

        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<Entry> yVals2 = new ArrayList<>();
        float val = 0;

        int count = 1;
        if (yVals1 != null) {
            yVals1.clear();
        }

        nameLabes = getString(R.string.kg);
        if (!SettingsApp.getInstance().getMetric()) {
            nameLabes = getString(R.string.lb);
        }

        for (int i = 0; i < mAllSorted.size(); i++) {
            if (i % 2 == 0) {
                continue;
            }
            ParamsBody body = mAllSorted.get(i);
            switch (mPickerBottomValue) {
                case PICKER_WATER:
                    nameLabes = getString(R.string.persent);
                    val = body.getWater();
                    break;
                case PICKER_CALORIES:
                    nameLabes = "";
                    val = body.getEmr();
                    break;
                case PICKER_WEIGHT:
                    val = translateToMetricFloat(body.getWeight());
                    break;
                case PICKER_FAT_LEVEL:
                    nameLabes = "";
                    val = body.getVisceralFat();
                    break;
                case PICKER_MUSCLE_MASS:
                    val = translateToMetricFloat(body.getMuscle());
                    break;
                case PICKER_BONE_MASS:
                    val = body.getBody();
                    break;
                case PICKER_FAT:
                    nameLabes = getString(R.string.persent);
                    val = body.getFat();
                    break;
            }

            float add = 0;

            if (val <= 0) {
                continue;
            }

            if (val < 15) {
                add = val * 0.06f;
            }else if (val < 80) {
                add = val * 0.04f;
            } else if (val < 150) {
                add = val * 0.02f;
            } else {
                add = val * 0.008f;
            }

//            float iFloat = i;
            long date_time = body.getDate_time();

//            Date date = new Date(date_time);
//            String formatDate = sdfs.format(date);
//            long parseDate = 0;
//            try {
//                parseDate = sdfs.parse(formatDate).getTime();
//            } catch (ParseException mE) {
//                mE.printStackTrace();
//            }
//            String formatPicker = sdfs.format(parseDate);
//            System.out.println("formatPicker " + formatPicker);
//            logger("formatPicker " + formatPicker);
//
//            String toString = Long.toString(parseDate);
//
//            float iFloat = Float.parseFloat(toString);

            List<Object> objects = new ArrayList<>();
            objects.add(0, nameLabes);
            objects.add(0, date_time);
            objects.add(0, i);

            yVals.add(new Entry(count, val, objects));
//            yVals2.add(new Entry(count - 0.025f, val - add, objects));
            count++;
            Date date = new Date(date_time);
            String formatDate = sdfs.format(date);
            yVals1.add(formatDate);
        }
        seetMaxMinValueAxix(yVals);
        setData(yVals, yVals2);
    }

    // this method set minimal and maximal value in axis
    private void seetMaxMinValueAxix(ArrayList<Entry> mYVals) {
        float minValueX = Float.MAX_VALUE, maxValueX = Float.MIN_VALUE,
                minValue = Float.MAX_VALUE, maxValue = Float.MIN_VALUE;
        for (Entry entry : mYVals) {
            float x = entry.getX();
            float y = entry.getY();
            if (maxValueX < x) {
                maxValueX = x;
            }
            if (minValueX > x) {
                minValueX = x;
            }
            if (minValue > y) {
                minValue = y;
            }
            if (maxValue < y) {
                maxValue = y;
            }
        }
        if (minValue < 1) {
            minValue = -2f;
        } else if (minValue < 10) {
            minValue = minValue - 5f;
        } else if (minValue < 150) {
            minValue = minValue - 30f;
        } else {
            minValue = minValue * 0.99f;
        }

        if (maxValue < 40) {
            maxValue = maxValue + 5f;
        } else if (maxValue < 100) {
            maxValue = maxValue + 15f;
        } else if (maxValue < 150) {
            maxValue = maxValue + 30f;
        } else {
            maxValue = maxValue * 1.1f;
        }

        mChart.getAxisLeft().setAxisMinimum(minValue);
        mChart.getAxisLeft().setAxisMaximum(maxValue);

        mChart.getXAxis().setAxisMinimum(0);
        mChart.getXAxis().setAxisMaximum(mYVals.size() + 1);

        mChart.getXAxis().setLabelCount(
                ((mYVals.size()) <= showCountValue) ? (mYVals.size()) : showCountValue,
                false);
    }


    private static final float SWIPE_MIN_DISTANCE = 10;
    private static final float SWIPE_THRESHOLD_VELOCITY = 10;

    public void setToushValueTOP(final MotionEvent event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = rl_view_liner_TOP.getWidth();
                int height = rl_view_liner_TOP.getHeight();
                float x = event.getX();
                float y = event.getRawY();

                if (event.getAction() == MotionEvent.ACTION_UP &&
                        !scrollBooleanTOP) {
                    scrollBooleanTOP = true;
                    if (x < width / 2 &&
                            y > height) {
                        setValuePickerTOP(true);
//                        Toast.makeText(getActivity(), "Left", Toast.LENGTH_SHORT).show();
                    } else if (x > width / 2 &&
                            y > height) {
                        setValuePickerTOP(false);
//                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, 50);
    }

    public void setValuePickerTOP(boolean left) {
        int oldValue = mViewPagerTop.getCurrentItem();
        if (left) {
            if (oldValue + 1 > PICKER_WATER) {
                mViewPagerTop.setCurrentItem(oldValue - 1);
            }
        } else if (oldValue + 1 < PICKER_FAT_LEVEL) {
            mViewPagerTop.setCurrentItem(oldValue + 1);
        } else return;

        oldValue = mViewPagerTop.getCurrentItem();
        circlePageIndicatorTop.setViewPager(mViewPagerTop, oldValue);
        getDataFromDb();
        setScrollHandlerTOP();
    }


    // this class for delete alarm swipe
    class MyGestureDetectorButton extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int value = mViewPager.getCurrentItem() + 1;
            int width = mViewPager.getWidth();
            int height = mViewPager.getHeight();
            scrollBoolean = true;
            try {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
                        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if (value < PICKER_FAT) {
                        mViewPager.setCurrentItem(value);
                    } else {
                        return false;
                    }
//                    Toast.makeText(getActivity(), "Left left", Toast.LENGTH_SHORT).show();
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if (value > PICKER_WATER) {
                        mViewPager.setCurrentItem(value - 2);
                    }
//                    Toast.makeText(getActivity(), "Right right", Toast.LENGTH_SHORT).show();
                } else {
                    return false;
                }
            } catch (Exception e) {
                // nothing
            }
            value = mViewPager.getCurrentItem();
            circlePageIndicator.setViewPager(mViewPager, value);
            getDataFromDb();
            setScrollHandler();
            return false;
        }
    }

    // this class for delete alarm swipe
    class MyGestureDetectorTOP extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int value = mViewPagerTop.getCurrentItem() + 1;
            int width = mViewPagerTop.getWidth();
            int height = mViewPagerTop.getHeight();
            scrollBooleanTOP = true;
            try {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
                        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if (value < PICKER_FAT_LEVEL) {
                        mViewPagerTop.setCurrentItem(value);
                    } else {
                        return false;
                    }
//                    Toast.makeText(getActivity(), "Left left", Toast.LENGTH_SHORT).show();
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if (value > PICKER_WATER) {
                        mViewPagerTop.setCurrentItem(value - 2);
                    }
//                    Toast.makeText(getActivity(), "Right right", Toast.LENGTH_SHORT).show();
                } else {
                    return false;
                }
            } catch (Exception e) {
                // nothing
            }
            value = mViewPagerTop.getCurrentItem();
            circlePageIndicatorTop.setViewPager(mViewPagerTop, value);
            getDataFromDb();
            setScrollHandlerTOP();
            return false;
        }
    }

    private void setScrollHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollBoolean = false;
            }
        }, 75);
    }

    private void setScrollHandlerTOP() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollBooleanTOP = false;
            }
        }, 75);
    }
}

