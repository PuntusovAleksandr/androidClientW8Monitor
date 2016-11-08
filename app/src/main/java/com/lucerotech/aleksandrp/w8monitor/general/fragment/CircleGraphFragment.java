package com.lucerotech.aleksandrp.w8monitor.general.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.adapter.CirclePagerAdapterMain;
import com.lucerotech.aleksandrp.w8monitor.alarm.AlarmActivity;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.ParamsBody;
import com.lucerotech.aleksandrp.w8monitor.general.MainActivity;
import com.lucerotech.aleksandrp.w8monitor.general.MainActivityPresenter;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.view.CircleBackground;
import com.lucerotech.aleksandrp.w8monitor.general.fragment.view.ViewPagerCustomDuration;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lucerotech.aleksandrp.w8monitor.utils.FontsTextView.getFontRoboLight;
import static com.lucerotech.aleksandrp.w8monitor.utils.GetSizeWindow.getSizeWindow;
import static com.lucerotech.aleksandrp.w8monitor.utils.LoggerApp.logger;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.MAX_VALUE_PICKER;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_BMI;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_BONE_MASS;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_CALORIES;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_FAT;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_FAT_LEVEL;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_MUSCLE_MASS;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_WATER;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.PICKER_WEIGHT;
import static com.lucerotech.aleksandrp.w8monitor.utils.TranslateToMetric.translateToMetricFloat;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public class CircleGraphFragment extends Fragment implements
        CircleGraphView {
//        ContextDialogListBtDevices.RefreshInterface {

    private Context mContext;
    private MainActivity mActivity;
    private MainActivityPresenter mPresenter;

    private CirclePagerAdapterMain mAdapter;

    @Bind(R.id.bt_go_line)
    CircleBackground mButton;

    @Bind(R.id.ib_time)
    ImageView ib_time;
    @Bind(R.id.ib_line_chart)
    ImageView ib_line_chart;
    @Bind(R.id.ib_settings)
    ImageView ib_settings;

    @Bind(R.id.tv_turn_on_bluetooth)
    TextView tv_turn_on_bluetooth;
    @Bind(R.id.tv_main_value_height)
    TextView tv_main_value_height;
    @Bind(R.id.tv_main_value_height_to_right)
    TextView tv_main_value_height_to_right;
    @Bind(R.id.tv_main_value_height_to_below)
    TextView tv_main_value_height_to_below;
    @Bind(R.id.tv_main_value_height_to_below_right)
    TextView tv_main_value_height_to_below_right;
    @Bind(R.id.tv_result_mass)
    TextView tv_result_mass;
    @Bind(R.id.tv_result_param)
    TextView tv_result_param;
    @Bind(R.id.tv_firm_surface)
    TextView tv_firm_surface;

    @Bind(R.id.walk_through_view_pager_main)
    ViewPagerCustomDuration mViewPager;

    @Bind(R.id.circle_pager_indicator_main)
    CirclePageIndicator circlePageIndicator;

    @Bind(R.id.rl_view)
    RelativeLayout rl_view;

    // init value
    private int centerValue;
    private int prevValue;
    private int resultValue;

    private String[] mMassParams = new String[8];

    private boolean isMetricSystem;
    private int count = 0;      // how match show firm surface
    private Handler showFirm;

    private MainActivity activity;

    public CircleGraphFragment() {
    }

    public CircleGraphFragment(Context mContext, MainActivityPresenter mPresenter) {
        this.mContext = mContext;
        this.mPresenter = mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getContext();
        mActivity.initListenerCircleFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_circle_graph, container, false);
        ButterKnife.bind(this, view);

        setViewPager();

        activity = (MainActivity) getActivity();

        ib_line_chart.setEnabled(false);
        ib_line_chart.setAlpha(0.5f);
        setShowValues(mViewPager.getCurrentItem());

        setDefaultUI();

        tv_main_value_height_to_right.setTypeface(getFontRoboLight());
        tv_main_value_height_to_below_right.setTypeface(getFontRoboLight());
        tv_result_param.setTypeface(getFontRoboLight());
        // values
        tv_main_value_height.setTextScaleX(0.7f);
        tv_main_value_height_to_below.setTextScaleX(0.7f);
        tv_result_mass.setTextScaleX(0.7f);
        // params
        tv_result_param.setTextScaleX(0.7f);
        tv_main_value_height_to_below_right.setTextScaleX(0.7f);
        tv_main_value_height_to_right.setTextScaleX(0.7f);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setShowValues(mViewPager.getCurrentItem());
    }

    private void setShowValues(int mValue) {
        mPresenter.getDataForCircle(mValue, this);
    }

    private void setMetrikString(String mKg) {
        tv_main_value_height_to_right.setText(mKg);
        tv_main_value_height_to_below_right.setText(mKg);
        tv_result_param.setText(mKg);
    }

    //    ===========================================
//            from CircleGraphView
//    ===========================================

    @Override
    public void hideTextEnableBLE() {
        tv_turn_on_bluetooth.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTextConnectionBLE() {
        tv_turn_on_bluetooth.setVisibility(View.VISIBLE);
        tv_turn_on_bluetooth.setText(R.string.please_stay_still);
    }

    @Override
    public void setDefaultUI() {
        tv_turn_on_bluetooth.setText(R.string.turn_on_bluetooth);
        tv_turn_on_bluetooth.setVisibility(View.VISIBLE);
    }

    @Override
    public void showParams(float[] mMassParams) {
        setShowValues(mViewPager.getCurrentItem());
    }

    @Override
    public void showMessageError() {
        Toast.makeText(mContext, "Error save data in data base", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDataCircle(final int mI_, ParamsBody mLast, ParamsBody mPreLast, float[] mMassParams) {
        final int mI = mI_ + 1;
        if (mMassParams[0] > 0) {
            ib_line_chart.setEnabled(true);
            ib_line_chart.setAlpha(1.0f);
        }
        String[] stringArray = getResources().getStringArray(R.array.select_weight_params);
        try {
            System.out.println("NAME PICKER = " + mI + " __ " + stringArray[mI - 1]);
            logger("NAME PICKER = " + mI + " __ " + stringArray[mI - 1]);
        } catch (IllegalStateException mE) {
            logger("showDataCircle " + mE.getMessage());
            mE.printStackTrace();
        }

        boolean emptyPreLast = false;
        float valueWeight = 0, valuePreWeight = 0, valueResult = 0;
        String paramValue = getString(R.string.kg);
        if (!SettingsApp.getInstance().getMetric()) {
            paramValue = getString(R.string.lb);
        }

        float valueNow = valueWeight;
        float valueMax = valueWeight;
        float valueMin = valueWeight;

        if (mLast != null && mPreLast != null) {

            emptyPreLast = mLast.getDate_time() == mPreLast.getDate_time();

            switch (mI) {
                case PICKER_WATER:
                    valueWeight = mLast.getWater();
                    valuePreWeight = mPreLast.getWater();
                    paramValue = getString(R.string.persent);
                    valueMax = mMassParams[8];
                    valueMin = mMassParams[9];
                    System.out.println("NAME PICKER  PICKER_WATER = " + mI + " __ " + stringArray[mI - 1]);
                    break;

                case PICKER_CALORIES:
                    valueWeight = mLast.getEmr();
                    valuePreWeight = mPreLast.getEmr();
                    paramValue = "";
                    valueMax = mMassParams[12];
                    valueMin = mMassParams[13];
                    System.out.println("NAME PICKER  PICKER_RATE = " + mI + " __ " + stringArray[mI - 1]);
                    break;

                case PICKER_WEIGHT:
                    valueWeight = translateToMetricFloat(mLast.getWeight());
                    valuePreWeight = translateToMetricFloat(mPreLast.getWeight());
                    valueMax = mMassParams[0];
                    valueMin = mMassParams[1];
                    System.out.println("NAME PICKER  PICKER_WEIGHT = " + mI + " __ " + stringArray[mI - 1]);
                    break;

                case PICKER_FAT_LEVEL:
                    valueWeight = mLast.getVisceralFat();
                    valuePreWeight = mPreLast.getVisceralFat();
                    paramValue = "";
                    valueMax = mMassParams[10];
                    valueMin = mMassParams[11];
                    System.out.println("NAME PICKER  PICKER_FAT_LEVEL = " + mI + " __ " + stringArray[mI - 1]);
                    break;

                case PICKER_MUSCLE_MASS:
                    valueWeight = translateToMetricFloat(mLast.getMuscle());
                    valuePreWeight = translateToMetricFloat(mPreLast.getMuscle());
                    valueMax = mMassParams[6];
                    valueMin = mMassParams[7];
                    System.out.println("NAME PICKER  PICKER_MUSCLE_MASS = " + mI + " __ " + stringArray[mI - 1]);
                    break;

                case PICKER_BONE_MASS:
                    valueWeight = translateToMetricFloat(mLast.getBody());
                    valuePreWeight = translateToMetricFloat(mPreLast.getBody());
                    valueMax = mMassParams[2];
                    valueMin = mMassParams[3];
                    System.out.println("NAME PICKER  PICKER_BONE_MASS = " + mI + " __ " + stringArray[mI - 1]);
                    break;

                case PICKER_FAT:
                    valueWeight = mLast.getFat();
                    valuePreWeight = mPreLast.getFat();
                    paramValue = getString(R.string.persent);
                    valueMax = mMassParams[4];
                    valueMin = mMassParams[5];
                    System.out.println("NAME PICKER  PICKER_FAT = " + mI + " __ " + stringArray[mI - 1]);
                    break;

                case PICKER_BMI:
                    valueWeight = mLast.getBmi();
                    valuePreWeight = mPreLast.getBmi();
                    paramValue = "";
                    valueMax = mMassParams[14];
                    valueMin = mMassParams[15];
                    System.out.println("NAME PICKER  PICKER_BMI = " + mI + " __ " + stringArray[mI - 1]);
                    break;
            }
        } else {

            switch (mI) {
                case PICKER_WATER:
                case PICKER_FAT:
                    paramValue = getString(R.string.persent);
                    break;

                case PICKER_CALORIES:
                case PICKER_FAT_LEVEL:
                case PICKER_BMI:
                    paramValue = "";
                    break;
            }
        }

        System.out.println("NAME PICKER valueMax = " + valueMax + " _valueMin = " + valueMin);
        valueNow = valueWeight;


        valueResult = (valueWeight - valuePreWeight);
        if (emptyPreLast) {
            valuePreWeight = 0;
            valueResult = 0;
        }
        final float finalValueNow = valueNow;
        final float finalValueMax = valueMax;
        final float finalValueMin = valueMin;
        final float finalValueWeight = valueWeight;
        final float finalValuePreWeight = valuePreWeight;
        final float finalValueResult = valueResult;
        final String finalParamValue = paramValue;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mButton.setDataInChart(finalValueNow, finalValueMax, finalValueMin, mI);

                String main = String.valueOf(finalValueWeight);
                String second = String.valueOf(finalValuePreWeight);
                String count = String.format("%.1f", finalValueResult);

                if (mI == PICKER_FAT_LEVEL || mI == PICKER_CALORIES) {
                    main = main.substring(0, main.length() - 2);
                    second = second.substring(0, second.length() - 2);
                    count = count.substring(0, count.length() - 2);
                }

                tv_main_value_height.setText(main);
                tv_main_value_height_to_below.setText(second);
                tv_result_mass.setText(count);

                tv_main_value_height_to_right.setText(finalParamValue);
                tv_main_value_height_to_below_right.setText(finalParamValue);
                tv_result_param.setText(finalParamValue);

            }
        });
    }


// for clear all values before weighting
    @Override
    public void deleteValuesInTextShows() {
        tv_main_value_height.setText("0.0");
        tv_main_value_height_to_below.setText("0.0");
        tv_result_mass.setText("0.0");

        mButton.setDataInChart(0, 0, 0, mViewPager.getCurrentItem());
    }
    //    ===========================================
//      END      from CircleGraphView
//    ===========================================


    public final static int PAGES = 5;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public final static int LOOPS = 1000;
    public final static int FIRST_PAGE = PAGES * LOOPS / 2;

    private void setViewPager() {
        mViewPager.setScrollDurationFactor(2); // make the animation twice as slow
        mAdapter = new CirclePagerAdapterMain(getActivity().getSupportFragmentManager(), MAX_VALUE_PICKER);
        mViewPager.setAdapter(mAdapter);
        circlePageIndicator.setViewPager(mViewPager, PICKER_WEIGHT - 1);

//        mViewPager.setPageTransformer(false, mAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        mViewPager.setCurrentItem(PICKER_WEIGHT - 1);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        mViewPager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        mViewPager.setPageMargin(getSizeWindow(getActivity()));


        final GestureDetector gestureDetector =
                new GestureDetector(getActivity(), new MyGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                setTouchValue(event);
                return gestureDetector.onTouchEvent(event);
            }
        };

        rl_view.setOnTouchListener(gestureListener);

    }


    private boolean scrollBoolean;

    private void setTouchValue(final MotionEvent event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = rl_view.getWidth();
                int height = rl_view.getHeight();
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
        setShowValues(oldValue);
        setScrollHandler();
    }

    @Override
    public void onStart() {
        super.onStart();

        // set show param for value
        isMetricSystem = SettingsApp.getInstance().getMetric();
        if (isMetricSystem) {
            setMetrikString(getActivity().getResources().getString(R.string.kg));
        } else {
            setMetrikString(getActivity().getResources().getString(R.string.lb));
        }

        activity.checkSupportBLE();
        if (showFirm != null) {
            showFirm.removeCallbacksAndMessages(null);
            showFirm = null;
        }
        showFirm = new Handler();
        showFirm.postDelayed(new Runnable() {
            @Override
            public void run() {
                count = 0;
                startShowFirmSurface();
            }
        }, 500);
    }


    private void startShowFirmSurface() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int totalCount = 5;
                final long oneSec = 1000;
                while (count < totalCount) {
                    count++;
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                tv_firm_surface.setVisibility(View.INVISIBLE);
                                AlphaAnimation animation = new AlphaAnimation(0.0f, 1.5f);
                                animation.setDuration(oneSec);
                                tv_firm_surface.setAnimation(animation);
                                tv_firm_surface.startAnimation(animation);

                            }
                        });
                    } catch (NullPointerException mE) {
                        logger("startShowFirmSurface NullPointerException: " + mE.getMessage());
                        mE.printStackTrace();
                    }

                    try {
                        Thread.sleep((long) (oneSec * 1.5f));
                    } catch (InterruptedException mE) {
                        mE.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void onStop() {
        super.onStop();
        activity.disconnectBLE(false);
    }

    //    ====================================================================
//            onClick
//    ====================================================================
    @OnClick(R.id.ib_time)
    public void clickTine() {
        Intent intent = new Intent(getActivity(), AlarmActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ib_settings)
    public void clickSettings() {
        mPresenter.goToSettingsProfile();
    }

    @OnClick(R.id.ib_line_chart)
    public void clickGo() {
        mPresenter.setLineGraph(mViewPager.getCurrentItem());
    }

    @OnClick(R.id.tv_turn_on_bluetooth)
    public void clickRefreshConnection() {
//        supportEnable = mBluetoothHandler.checkSupport();
    }


    //    ====================================================================
//     END        onClick
    //    ====================================================================


    private static final float SWIPE_MIN_DISTANCE = 10;
    private static final float SWIPE_THRESHOLD_VELOCITY = 10;


    // this class for delete alarm swipe
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
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
            setShowValues(value);
            setScrollHandler();
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
}
