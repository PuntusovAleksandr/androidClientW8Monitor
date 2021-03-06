package com.w8.w8monitor.android.tutorial;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.LoginActivity;
import com.w8.w8monitor.android.activity.MainActivity;
import com.w8.w8monitor.android.tutorial.view.TutorialView;
import com.w8.w8monitor.android.tutorial.view.interfacea.ShowcaseDrawer;
import com.w8.w8monitor.android.tutorial.view.showcaseview.targets.ViewTarget;
import com.w8.w8monitor.android.utils.SettingsApp;

/**
 * Created by AleksandrP on 22.12.2016.
 */

public class ShowTutorial implements TutorialView.ClickTutorial {

    private TutorialView mTutorial;
    private Activity mActivity;

    private View mView;

    private View mIb_register;
    private View mLl_log_in;
    private View mIb_facebook;
    private View mIb_login;
    private View mCirclePageIndicator;

    private int counterLogin = 1;
    private int counterMain = 1;
    private int oldMargin = 36;

    public ShowTutorial() {
    }

    public void tutorialForMain(MainActivity mActivity,
                                final View mIb_register,
                                View mLl_log_in,
                                View mIb_facebook,
                                View mIb_login,
                                View mCirclePageIndicator) {
        this.mActivity = mActivity;

        this.mIb_register = mIb_register;
        this.mLl_log_in = mLl_log_in;
        this.mIb_facebook = mIb_facebook;
        this.mIb_login = mIb_login;
        this.mCirclePageIndicator = mCirclePageIndicator;

        mTutorial = new TutorialView.Builder(mActivity)
                .setTarget(new ViewTarget(mIb_register))
                .withHoloShowcase()
//                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(listenerMain)
                .blockAllTouches()
                .setOnClickListenerStart(listenerMainStart)
                .build();
        mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
        mTutorial.setClickListener(this, false);
        mTutorial.setButtonStartText(mActivity.getString(R.string.prev));
        mTutorial.hideButtonStart();
        mTutorial.setBacgroundButtons(Color.TRANSPARENT);
        mTutorial.setTextDescription(mActivity.getString(R.string.notification_text_tutoriaal));
        mTutorial.setTextInCenter(true);
        setDefaultParamsLogin();
        mTutorial.setOnClickListenerClose(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterMain = 5;
                showNextTutorialMain();
            }
        });
    }

    public void tutorialForLogin(LoginActivity mActivity,
                                 final LinearLayout mIb_register,
                                 LinearLayout mLl_log_in,
                                 LinearLayout mIb_facebook,
                                 TextView mIb_login) {
        this.mActivity = mActivity;

        this.mIb_register = mIb_register;
        this.mLl_log_in = mLl_log_in;
        this.mIb_facebook = mIb_facebook;
        this.mIb_login = mIb_login;
        this.mView = mIb_register;

        mTutorial = new TutorialView.Builder(mActivity)
                .setTarget(new ViewTarget(mIb_register))
                .setShowcaseDrawer(showcaseDrawerLoginRegister)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(listenerLogin)
                .blockAllTouches()
                .setOnClickListenerStart(listenerLoginStart)
                .build();
        mTutorial.setClickListener(this, true);
        mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
        mTutorial.setButtonStartText(mActivity.getString(R.string.prev));
        mTutorial.hideButtonStart();
        mTutorial.setBacgroundButtons(Color.TRANSPARENT);
        mTutorial.setTextDescription(mActivity.getString(R.string.register_text_tutorial));
        setDefaultParamsLogin();
        mTutorial.setOnClickListenerClose(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterLogin = 4;
                showNextTutorialLogin();
            }
        });
    }

    View.OnClickListener listenerMainStart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (counterMain > 1) {
                counterMain -= 2;
            }
            showNextTutorialMain();
        }
    };

    View.OnClickListener listenerMain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showNextTutorialMain();
        }
    };

    private void showNextTutorialMain() {
        setDefaultParamsLogin();
        mTutorial.setTextInCenter(true);
        mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
        RelativeLayout.LayoutParams lps;
        RelativeLayout.LayoutParams lpsStart;
        int margin;
        switch (counterMain) {
            case 0:
                mView = mIb_register;
                mTutorial.setShowcase(new ViewTarget(mIb_register), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.notification_text_tutoriaal));
                mTutorial.hideButtonStart();
                break;
            case 1:
                mView = mLl_log_in;
                mTutorial.setShowcase(new ViewTarget(mLl_log_in), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.graph_text_tutorial));
                mTutorial.showButtonStart();
                break;

            case 2:
                mView = mIb_facebook;
                mTutorial.setShowcase(new ViewTarget(mIb_facebook), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.settings_text_tutorial));
                break;

            case 3:
                mView = mIb_login;
                mTutorial.setTextInCenter(false);
                mTutorial.setShowcase(new ViewTarget(mIb_login), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.wheel_text_tutorial));
                break;

            case 4:
                mView = mCirclePageIndicator;
                mTutorial.setShowcase(new ViewTarget(mCirclePageIndicator), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.swipe_text));

                lps = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lps.addRule(RelativeLayout.CENTER_VERTICAL);
                lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                margin = ((Number) (mActivity.getResources().getDisplayMetrics().density * 64)).intValue();
                lps.setMargins(oldMargin, 0, oldMargin, margin * 2);
                mTutorial.setButtonPosition(lps);

                lpsStart = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lpsStart.addRule(RelativeLayout.CENTER_VERTICAL);
                lpsStart.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lpsStart.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                margin = ((Number) (mActivity.getResources().getDisplayMetrics().density * 64)).intValue();
                lpsStart.setMargins(oldMargin, 0, oldMargin, margin * 2);
                mTutorial.setButtonStartPosition(lpsStart);
                mTutorial.removeIconEnd();
                mTutorial.setButtonText(mActivity.getString(R.string.close));
                break;

            case 5:
                SettingsApp.getInstance().setShowMainTutorial(false);
                mTutorial.hide();
                break;
        }
        counterMain++;
    }


    View.OnClickListener listenerLoginStart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (counterLogin > 1) {
                counterLogin -= 2;
            }
            showNextTutorialLogin();
        }
    };

    View.OnClickListener listenerLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showNextTutorialLogin();
        }
    };

    private void showNextTutorialLogin() {
        setDefaultParamsLogin();
        RelativeLayout.LayoutParams lps;
        RelativeLayout.LayoutParams lpsStart;

        int margin;
        switch (counterLogin) {
            case 0:
                mTutorial.hideButtonStart();
                mView = mIb_register;
                mTutorial.setTextDescription(mActivity.getString(R.string.register_text_tutorial));
                mTutorial.setShowcase(new ViewTarget(mIb_register), true);
                mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
                break;
            case 1:
                mView = mLl_log_in;
                mTutorial.setShowcase(new ViewTarget(mLl_log_in), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.login_txt1_tutorial));
                mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
                mTutorial.showButtonStart();
                break;

            case 2:
                mView = mIb_facebook;
                mTutorial.setShowcase(new ViewTarget(mIb_facebook), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.login_txt2_tutorial));
                mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
                break;

            case 3:
                mView = mIb_login;
                mTutorial.setShowcase(new ViewTarget(mIb_login), true);
                mTutorial.setTextDescription(mActivity.getString(R.string.register_later_text_tutorial));

                lps = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lps.addRule(RelativeLayout.CENTER_VERTICAL);
                lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lpsStart = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lpsStart.addRule(RelativeLayout.CENTER_VERTICAL);
                lpsStart.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lpsStart.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                margin = ((Number) (mActivity.getResources().getDisplayMetrics().density * 24)).intValue();
                lps.setMargins(oldMargin, 0, margin, margin * 3);
                lpsStart.setMargins(oldMargin, 0, margin, margin * 3);
                mTutorial.setButtonPosition(lps);
                mTutorial.setButtonStartPosition(lpsStart);
                mTutorial.setButtonText(mActivity.getString(R.string.close));
                mTutorial.removeIconEnd();
                break;

            case 4:
                SettingsApp.getInstance().setShowLoginTutorial(false);
                mTutorial.hide();
                break;
        }
        counterLogin++;
    }

    private void setDefaultParamsLogin() {
        RelativeLayout.LayoutParams lps, lpsStart;
        lps = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.CENTER_VERTICAL);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.setMargins(oldMargin, 0, oldMargin, oldMargin);

        lpsStart = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lpsStart.addRule(RelativeLayout.CENTER_VERTICAL);
        lpsStart.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpsStart.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpsStart.setMargins(oldMargin, 0, oldMargin, oldMargin);
        mTutorial.setButtonPosition(lps);
        mTutorial.setButtonStartPosition(lpsStart);
        mTutorial.setIconEnd();
    }


    //    ===============================================================
//    ShowcaseDrawer
//    ===============================================================
//    ===============================================================
    ShowcaseDrawer showcaseDrawerLoginRegister = new ShowcaseDrawer() {
        int radius;
        int backgroundColor;
        Paint basicPaint = new Paint();
        Paint eraserPaint = new Paint();

        @Override
        public void setShowcaseColour(@ColorInt int color) {

        }

        @Override
        public void drawShowcase(Bitmap buffer, float x, float y, float scaleMultiplier) {
            Canvas bufferCanvas = new Canvas(buffer);
            radius = (mView.getWidth() / 2);
            eraserPaint.setColor(0xFFFFFF);
            eraserPaint.setAlpha(0);
            eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            eraserPaint.setAntiAlias(true);
//                        bufferCanvas.drawCircle(x, y, radius, eraserPaint);
            RectF rectF = new RectF(
                    (float) (x - mView.getWidth() / 1.8),
                    (float) (y - mView.getHeight() / 1.5),
                    (float) (x + mView.getWidth() / 1.8),
                    (float) (y + mView.getHeight() / 1.5));
            bufferCanvas.drawRect(rectF, eraserPaint);
        }

        @Override
        public int getShowcaseWidth() {
            return 0;
        }

        @Override
        public int getShowcaseHeight() {
            return 0;
        }

        @Override
        public float getBlockedRadius() {
            return 0;
        }

        @Override
        public void setBackgroundColour(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        @Override
        public void erase(Bitmap bitmapBuffer) {
            bitmapBuffer.eraseColor(backgroundColor);
        }

        @Override
        public void drawToCanvas(Canvas canvas, Bitmap bitmapBuffer) {
            canvas.drawBitmap(bitmapBuffer, 0, 0, basicPaint);
        }
    };

    @Override
    public void click(boolean mFromLogin) {
        if (mFromLogin) {
            showNextTutorialLogin();
        } else {
            showNextTutorialMain();
        }
    }
}
