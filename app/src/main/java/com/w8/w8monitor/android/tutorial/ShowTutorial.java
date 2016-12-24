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
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseDrawer;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.LoginActivity;
import com.w8.w8monitor.android.activity.MainActivity;
import com.w8.w8monitor.android.utils.SettingsApp;

/**
 * Created by AleksandrP on 22.12.2016.
 */

public class ShowTutorial {

    private ShowcaseView mTutorial;
    private Activity mActivity;
    private TextPaint textPaintTitle;
    private TextPaint textPaint;

    private View mView;

    private View mIb_register;
    private View mLl_log_in;
    private View mIb_facebook;
    private View mIb_login;

    private int counterLogin = 1;
    private int counterMain = 1;

    public ShowTutorial() {

        textPaintTitle = new TextPaint();
        textPaint = new TextPaint();

    }

    public void tutorialForMain(MainActivity mActivity,
                                 final View mIb_register,
                                View mLl_log_in,
                                View mIb_facebook,
                                View mIb_login) {
        this.mActivity = mActivity;

        this.mIb_register = mIb_register;
        this.mLl_log_in = mLl_log_in;
        this.mIb_facebook = mIb_facebook;
        this.mIb_login = mIb_login;

//        setParamsText();


        mTutorial = new ShowcaseView.Builder(mActivity)
                .setTarget(new ViewTarget(mIb_register))
                .withHoloShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
//                .setContentTitle(mActivity.getString(R.string.notification_tutoriaal))
                .setContentText(mActivity.getString(R.string.notification_text_tutoriaal))
//                .setContentTitlePaint(textPaintTitle)
//                .setContentTextPaint(textPaint)
                .setOnClickListener(listenerMain)
                .build();
        mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
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

//        setParamsText();


        mTutorial = new ShowcaseView.Builder(mActivity)
                .setTarget(new ViewTarget(mIb_register))
                .setShowcaseDrawer(showcaseDrawerLoginRegister)
                .setStyle(R.style.CustomShowcaseTheme2)
//                .setContentTitle(mActivity.getString(R.string.register_tutorial))
                .setContentText(mActivity.getString(R.string.register_text_tutorial))
//                .setContentTitlePaint(textPaintTitle)
//                .setContentTextPaint(textPaint)
                .setOnClickListener(listenerLogin)
                .build();
        mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
    }

    private void setParamsText() {
        float textSizeTitle =
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        mActivity.getResources().getDimension(R.dimen._20sp),
                        mActivity.getResources().getDisplayMetrics());

        float textSize =
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        mActivity.getResources().getDimension(R.dimen._8sp),
                        mActivity.getResources().getDisplayMetrics());

        textPaintTitle.setTextSize(textSizeTitle);
        textPaintTitle.setColor(Color.WHITE);

        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.LTGRAY);
    }


    View.OnClickListener listenerMain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout.LayoutParams lps;
            int margin;
            switch (counterMain) {
                case 1:
                    mTutorial.setShowcase(new ViewTarget(mLl_log_in), true);
//                    mTutorial.setContentTitle(mActivity.getString(R.string.graph_tutorial));
                    mTutorial.setContentText(mActivity.getString(R.string.graph_text_tutorial));
                    mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));

                    break;

                case 2:
                    mIb_register = mIb_facebook;

                    mTutorial.setShowcase(new ViewTarget(mIb_facebook), true);
                    mTutorial.setContentTitle(mActivity.getString(R.string.settings_tutorial));
                    mTutorial.setContentText(mActivity.getString(R.string.settings_text_tutorial));
                    mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
                    break;

                case 3:
                    mIb_register = mIb_login;

                    mTutorial.setShowcase(new ViewTarget(mIb_login), true);
//                    mTutorial.setContentTitle(mActivity.getString(R.string.wheel_tutorial));
                    mTutorial.setContentText(mActivity.getString(R.string.wheel_text_tutorial));
                    mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
                    break;

                case 4:
                    SettingsApp.getInstance().setShowMainTutorial(false);
                    mTutorial.hide();
                    break;
            }
            counterMain++;
        }
    };



    View.OnClickListener listenerLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout.LayoutParams lps;
            int margin;
            switch (counterLogin) {
                case 1:
                    mTutorial.setShowcase(new ViewTarget(mLl_log_in), true);
//                    mTutorial.setContentTitle(mActivity.getString(R.string.logint_tutorial));
                    mTutorial.setContentText(mActivity.getString(R.string.login_txt1_tutorial));
                    mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));

                    break;

                case 2:
                    mIb_register = mIb_facebook;

                    mTutorial.setShowcase(new ViewTarget(mIb_facebook), true);
//                    mTutorial.setContentTitle(mActivity.getString(R.string.logint_tutorial));
                    mTutorial.setContentText(mActivity.getString(R.string.login_txt2_tutorial));
                    mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));
                    break;

                case 3:
                    mIb_register = mIb_login;

                    mTutorial.setShowcase(new ViewTarget(mIb_login), true);
//                    mTutorial.setContentTitle(mActivity.getString(R.string.register_later_tutorial));
                    mTutorial.setContentText(mActivity.getString(R.string.register_later_text_tutorial));
                    mTutorial.setButtonText(mActivity.getString(R.string.next_tutorial));

                    lps = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lps.addRule(RelativeLayout.CENTER_VERTICAL);
                    lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    margin = ((Number) (mActivity.getResources().getDisplayMetrics().density * 24)).intValue();
                    lps.setMargins(0, 0, margin, margin * 3);
                    mTutorial.setButtonPosition(lps);
                    break;

                case 4:
                    SettingsApp.getInstance().setShowLoginTutorial(false);
                    mTutorial.hide();
                    break;
            }
            counterLogin++;
        }
    };


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
            radius = (mIb_register.getWidth() / 2);
            eraserPaint.setColor(0xFFFFFF);
            eraserPaint.setAlpha(0);
            eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            eraserPaint.setAntiAlias(true);
//                        bufferCanvas.drawCircle(x, y, radius, eraserPaint);
            RectF rectF = new RectF(
                    (float) (x - mIb_register.getWidth() / 1.8),
                    (float) (y - mIb_register.getHeight() / 1.5),
                    (float) (x + mIb_register.getWidth() / 1.8),
                    (float) (y + mIb_register.getHeight() / 1.5));
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
}
