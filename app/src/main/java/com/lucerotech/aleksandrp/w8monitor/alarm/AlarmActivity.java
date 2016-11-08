package com.lucerotech.aleksandrp.w8monitor.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.alarm.impl.AlarmActivityPresenterImpl;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.AlarmModel;
import com.lucerotech.aleksandrp.w8monitor.dialog.DeleteAlarmDialog;
import com.lucerotech.aleksandrp.w8monitor.utils.SetThemeDark;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

import static com.lucerotech.aleksandrp.w8monitor.utils.FontsTextView.getFontRobotoLight;

public class AlarmActivity extends AppCompatActivity implements AlarmView,
        DeleteAlarmDialog.DeleteOk {

    private static final float SWIPE_MIN_DISTANCE = 10;
    private static final float SWIPE_THRESHOLD_VELOCITY = 10;

    private AlarmActivityPresenter mPresenter;

    private int mHours, mMinutes, countAlarms;
    private boolean isAn;
    private boolean is24;

    // block items
    @Bind(R.id.item_1)
    RelativeLayout item_1;
    @Bind(R.id.item_2)
    RelativeLayout item_2;
    @Bind(R.id.item_3)
    RelativeLayout item_3;
//    @Bind(R.id.item_4)
//    RelativeLayout item_4;
//    @Bind(R.id.item_5)
//    RelativeLayout item_5;

    // block
    @Bind(R.id.rl_item_1)
    RelativeLayout rl_item_1;
    @Bind(R.id.rl_item_2)
    RelativeLayout rl_item_2;
    @Bind(R.id.rl_item_3)
    RelativeLayout rl_item_3;
//    @Bind(R.id.rl_item_4)
//    RelativeLayout rl_item_4;
//    @Bind(R.id.rl_item_5)
//    RelativeLayout rl_item_5;

    @Bind(R.id.iv_alarm_set)
    ImageView iv_alarm_set;
    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;

    @Bind(R.id.time_picker)
    TimePicker time_picker;

    @Bind(R.id.tv_swipe)
    TextView tv_swipe;

    private RealmResults<AlarmModel> mAlarmItems;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private View onTouchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        mPresenter = new AlarmActivityPresenterImpl(this);

        setDefaultTime();

        getAlarmFromDb();

        time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHours = hourOfDay;
                mMinutes = minute;
            }
        });

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                onTouchView = v;
                return gestureDetector.onTouchEvent(event);
            }
        };

        rl_item_1.setOnTouchListener(gestureListener);
        rl_item_2.setOnTouchListener(gestureListener);
        rl_item_3.setOnTouchListener(gestureListener);

    }

    // get params from db and set it
    private void getAlarmFromDb() {
        mPresenter.getAlarmFromDb(this);
    }

    private void setDefaultTime() {
        mHours = time_picker.getCurrentHour();
        mMinutes = time_picker.getCurrentMinute();
        is24 = time_picker.is24HourView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO: 30.09.2016 нужно записать в базу анные будильника
    }

    //    ==========================================================
//    on Clicks
//    ==========================================================


    // NOT DELETE THIS METHOD !!!
    @OnClick(R.id.rl_item_1)
    public void clickBlock1() {
// NOT DELETE THIS METHOD !!!
    }

    // NOT DELETE THIS METHOD !!!
    @OnClick(R.id.rl_item_2)
    public void clickBlock2() {
// NOT DELETE THIS METHOD !!!
    }

    // NOT DELETE THIS METHOD !!!
    @OnClick(R.id.rl_item_3)
    public void clickBlock3() {
// NOT DELETE THIS METHOD !!!
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackPressed() {
        onBackPressed();
    }

    @OnClick(R.id.iv_alarm_set)
    public void setAlarm() {
        boolean isAmPicker = true;
        StringBuilder builder = new StringBuilder();
        Integer hour = time_picker.getCurrentHour();
        if (hour > 12) {
            isAmPicker = false;
        }
        String hourString = String.valueOf(hour);
        if (hour < 10) {
            hourString = "0" + hourString;
        }

        String currentMinute = String.valueOf(time_picker.getCurrentMinute());
        if (currentMinute.length() == 1) {
            currentMinute = "0" + currentMinute;
        }

        builder.append(hourString)
                .append(":")
                .append(currentMinute);
        if (countAlarms < 4) {
            mPresenter.setAlarmInDb(isAmPicker, builder.toString(), this);
        }
    }

//    ==========================================================
//   END  on Clicks
//    ==========================================================
//   from AlarmView
//    ==========================================================

    @Override
    public void setAlarmItem(RealmResults<AlarmModel> mAlarmItems) {
        this.countAlarms = mAlarmItems.size();
        this.mAlarmItems = mAlarmItems;
        if (countAlarms < 1) {
            tv_swipe.setText(R.string.swipe_up_to_remove_alarm);
        } else {
            tv_swipe.setText(R.string.set_notification);
        }
        if (countAlarms == 3) {
            iv_alarm_set.setAlpha(0.1F);
            iv_alarm_set.setEnabled(false);
        } else {
            iv_alarm_set.setAlpha(1F);
            iv_alarm_set.setEnabled(true);
        }

        rl_item_1.setVisibility(View.GONE);
        rl_item_2.setVisibility(View.GONE);
        rl_item_3.setVisibility(View.GONE);
//        rl_item_4.setVisibility(View.GONE);
//        rl_item_5.setVisibility(View.GONE);

        for (int i = 0; i < countAlarms; i++) {
            switch (i) {
                case 0:
                    initUiAlarmShow(rl_item_1, item_1, mAlarmItems.get(i));
                    break;
                case 1:
                    initUiAlarmShow(rl_item_2, item_2, mAlarmItems.get(i));
                    break;
                case 2:
                    initUiAlarmShow(rl_item_3, item_3, mAlarmItems.get(i));
                    break;
//                case 3:
//                    initUiAlarmShow(rl_item_4, item_4, mAlarmItems.get(i));
//                    break;
//                case 4:
//                    initUiAlarmShow(rl_item_5, item_5, mAlarmItems.get(i));
//                    break;

            }
        }

        // disable alarm listener
        if (countAlarms == 0) {
            mPresenter.stopAlarm();
        }
    }

    @Override
    public void setAlarmInSystem(boolean mIsAmPicker, String mTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        long time = 0;
        try {
            time = fmt.parse(mTime).getTime();
        } catch (java.text.ParseException mE) {
            mE.printStackTrace();
        }
        mPresenter.startRepeatingTimer(time);
    }

    // set UI params
    private void initUiAlarmShow(View mViewBlock, View mViewItem, AlarmModel mModel) {
        mViewBlock.setVisibility(View.VISIBLE);
        TextView time = (TextView) mViewItem.findViewById(R.id.tv_text_time);
        TextView am = (TextView) mViewItem.findViewById(R.id.tv_am);
        TextView pm = (TextView) mViewItem.findViewById(R.id.tv_pm);

        time.setTypeface(getFontRobotoLight());
        am.setTypeface(getFontRobotoLight());
        pm.setTypeface(getFontRobotoLight());

        showItem(mModel, time, am, pm);
    }

    private void showItem(AlarmModel mModel, TextView mTime, TextView mAm, TextView mPm) {
        String mModelTime = mModel.getTime();
        if (!mModel.isAm()) {

            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm");
            long time = 0;
            try {
                time = fmt.parse(mModelTime).getTime();
            } catch (java.text.ParseException mE) {
                mE.printStackTrace();
            }
            mModelTime = sdfs.format(time);
        }

        if (mModelTime.length() < 5) {
            mModelTime = "0" + mModelTime;
        }

        mTime.setText(mModelTime);
        if (mModel.isAm()) {
            mAm.setAlpha(1f);
            mPm.setAlpha(0.3f);
        } else {
            mAm.setAlpha(0.3f);
            mPm.setAlpha(1f);
        }
    }

    /**
     * DEPLECATE METHOD
     *
     * @param mItem_
     * @param mRl_item_
     */
    private void deleteAlarm(RelativeLayout mItem_, RelativeLayout mRl_item_) {
        DeleteAlarmDialog dialog = new DeleteAlarmDialog(mItem_, this, this);
        dialog.show();
    }


    //    =========================================
//            from DeleteOk
//    =========================================
    @Override
    public void deleteOk(boolean mItem, RelativeLayout mItem_) {
        if (mItem) {
            // delete this alarm
            TextView textView = (TextView) mItem_.findViewById(R.id.tv_text_time);
            TextView am = (TextView) mItem_.findViewById(R.id.tv_am);
            String timeText = textView.getText().toString();
            if (am.getAlpha() < 0.7f) {
                String[] split = timeText.split(":");
                int i = Integer.parseInt(split[0]) + 12;
                StringBuilder builder = new StringBuilder();
                builder.append(i)
                        .append(":")
                        .append(split[1]);
                timeText = builder.toString();
            }

            mPresenter.deleteAlarmFromDb(timeText, this);
        }
    }


    // this class for delete alarm swipe
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    switch (onTouchView.getId()) {
                        case R.id.rl_item_1:
                            deleteOk(true, rl_item_1);
                            break;
                        case R.id.rl_item_2:
                            deleteOk(true, rl_item_2);
                            break;
                        case R.id.rl_item_3:
                            deleteOk(true, rl_item_3);
                            break;

                    }
//                    Toast.makeText(AlarmActivity.this, "Left up", Toast.LENGTH_SHORT).show();
                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                    Toast.makeText(AlarmActivity.this, "Right down", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }
}
