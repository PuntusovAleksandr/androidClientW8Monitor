/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.lefu.es.progressbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.lefu.es.progressbar.OnProgressBarListener;
import com.lefu.iwellness.newes.system.R;

public class NumberProgressBar
extends View {
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final int PROGRESS_TEXT_VISIBLE;
    private final float default_progress_text_offset;
    private final float default_reached_bar_height;
    private final int default_reached_color = Color.rgb((int)66, (int)145, (int)241);
    private final int default_text_color = Color.rgb((int)66, (int)145, (int)241);
    private final float default_text_size;
    private final float default_unreached_bar_height;
    private final int default_unreached_color = Color.rgb((int)204, (int)204, (int)204);
    private String mCurrentDrawText;
    private int mCurrentProgress = 0;
    private boolean mDrawReachedBar = true;
    private float mDrawTextEnd;
    private float mDrawTextStart;
    private float mDrawTextWidth;
    private boolean mDrawUnreachedBar = true;
    private boolean mIfDrawText = true;
    private OnProgressBarListener mListener;
    private int mMaxProgress = 100;
    private float mOffset;
    private String mPrefix = "";
    private int mReachedBarColor;
    private float mReachedBarHeight;
    private Paint mReachedBarPaint;
    private RectF mReachedRectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    private String mSuffix = "%";
    private int mTextColor;
    private Paint mTextPaint;
    private float mTextSize;
    private int mUnreachedBarColor;
    private float mUnreachedBarHeight;
    private Paint mUnreachedBarPaint;
    private RectF mUnreachedRectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 2130771981);
    }

    public NumberProgressBar(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.default_reached_bar_height = this.dp2px(1.5f);
        this.default_unreached_bar_height = this.dp2px(1.0f);
        this.default_text_size = this.sp2px(10.0f);
        this.default_progress_text_offset = this.dp2px(3.0f);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NumberProgressBar, n, 0);
        this.mReachedBarColor = typedArray.getColor(3, this.default_reached_color);
        this.mUnreachedBarColor = typedArray.getColor(2, this.default_unreached_color);
        this.mTextColor = typedArray.getColor(7, this.default_text_color);
        this.mTextSize = typedArray.getDimension(6, this.default_text_size);
        this.mReachedBarHeight = typedArray.getDimension(4, this.default_reached_bar_height);
        this.mUnreachedBarHeight = typedArray.getDimension(5, this.default_unreached_bar_height);
        this.mOffset = typedArray.getDimension(8, this.default_progress_text_offset);
        if (typedArray.getInt(9, 0) != 0) {
            this.mIfDrawText = false;
        }
        this.setProgress(typedArray.getInt(0, 0));
        this.setMax(typedArray.getInt(1, 100));
        typedArray.recycle();
        this.initializePainters();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateDrawRectF() {
        float f;
        Object[] arrobject = new Object[]{100 * this.getProgress() / this.getMax()};
        this.mCurrentDrawText = String.format((String)"%d", (Object[])arrobject);
        this.mCurrentDrawText = String.valueOf((Object)this.mPrefix) + this.mCurrentDrawText + this.mSuffix;
        this.mDrawTextWidth = this.mTextPaint.measureText(this.mCurrentDrawText);
        if (this.getProgress() == 0) {
            this.mDrawReachedBar = false;
            this.mDrawTextStart = this.getPaddingLeft();
        } else {
            this.mDrawReachedBar = true;
            this.mReachedRectF.left = this.getPaddingLeft();
            this.mReachedRectF.top = (float)this.getHeight() / 2.0f - this.mReachedBarHeight / 2.0f;
            this.mReachedRectF.right = (float)(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()) / (1.0f * (float)this.getMax()) * (float)this.getProgress() - this.mOffset + (float)this.getPaddingLeft();
            this.mReachedRectF.bottom = (float)this.getHeight() / 2.0f + this.mReachedBarHeight / 2.0f;
            this.mDrawTextStart = this.mReachedRectF.right + this.mOffset;
        }
        this.mDrawTextEnd = (int)((float)this.getHeight() / 2.0f - (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f);
        if (this.mDrawTextStart + this.mDrawTextWidth >= (float)(this.getWidth() - this.getPaddingRight())) {
            this.mDrawTextStart = (float)(this.getWidth() - this.getPaddingRight()) - this.mDrawTextWidth;
            this.mReachedRectF.right = this.mDrawTextStart - this.mOffset;
        }
        if ((f = this.mDrawTextStart + this.mDrawTextWidth + this.mOffset) >= (float)(this.getWidth() - this.getPaddingRight())) {
            this.mDrawUnreachedBar = false;
            return;
        }
        this.mDrawUnreachedBar = true;
        this.mUnreachedRectF.left = f;
        this.mUnreachedRectF.right = this.getWidth() - this.getPaddingRight();
        this.mUnreachedRectF.top = (float)this.getHeight() / 2.0f + (- this.mUnreachedBarHeight) / 2.0f;
        this.mUnreachedRectF.bottom = (float)this.getHeight() / 2.0f + this.mUnreachedBarHeight / 2.0f;
    }

    private void calculateDrawRectFWithoutProgressText() {
        this.mReachedRectF.left = this.getPaddingLeft();
        this.mReachedRectF.top = (float)this.getHeight() / 2.0f - this.mReachedBarHeight / 2.0f;
        this.mReachedRectF.right = (float)(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()) / (1.0f * (float)this.getMax()) * (float)this.getProgress() + (float)this.getPaddingLeft();
        this.mReachedRectF.bottom = (float)this.getHeight() / 2.0f + this.mReachedBarHeight / 2.0f;
        this.mUnreachedRectF.left = this.mReachedRectF.right;
        this.mUnreachedRectF.right = this.getWidth() - this.getPaddingRight();
        this.mUnreachedRectF.top = (float)this.getHeight() / 2.0f + (- this.mUnreachedBarHeight) / 2.0f;
        this.mUnreachedRectF.bottom = (float)this.getHeight() / 2.0f + this.mUnreachedBarHeight / 2.0f;
    }

    private void initializePainters() {
        this.mReachedBarPaint = new Paint(1);
        this.mReachedBarPaint.setColor(this.mReachedBarColor);
        this.mUnreachedBarPaint = new Paint(1);
        this.mUnreachedBarPaint.setColor(this.mUnreachedBarColor);
        this.mTextPaint = new Paint(1);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextSize(this.mTextSize);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int measure(int n, boolean bl) {
        int n2 = View.MeasureSpec.getMode((int)n);
        int n3 = View.MeasureSpec.getSize((int)n);
        int n4 = bl ? this.getPaddingLeft() + this.getPaddingRight() : this.getPaddingTop() + this.getPaddingBottom();
        if (n2 == 1073741824) {
            return n3;
        }
        int n5 = bl ? this.getSuggestedMinimumWidth() : this.getSuggestedMinimumHeight();
        int n6 = n5 + n4;
        if (n2 != Integer.MIN_VALUE) return n6;
        if (!bl) return Math.min((int)n6, (int)n3);
        return Math.max((int)n6, (int)n3);
    }

    public float dp2px(float f) {
        return 0.5f + f * this.getResources().getDisplayMetrics().density;
    }

    public int getMax() {
        return this.mMaxProgress;
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    public int getProgress() {
        return this.mCurrentProgress;
    }

    public float getProgressTextSize() {
        return this.mTextSize;
    }

    public boolean getProgressTextVisibility() {
        return this.mIfDrawText;
    }

    public int getReachedBarColor() {
        return this.mReachedBarColor;
    }

    public float getReachedBarHeight() {
        return this.mReachedBarHeight;
    }

    public String getSuffix() {
        return this.mSuffix;
    }

    protected int getSuggestedMinimumHeight() {
        return Math.max((int)((int)this.mTextSize), (int)Math.max((int)((int)this.mReachedBarHeight), (int)((int)this.mUnreachedBarHeight)));
    }

    protected int getSuggestedMinimumWidth() {
        return (int)this.mTextSize;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public int getUnreachedBarColor() {
        return this.mUnreachedBarColor;
    }

    public float getUnreachedBarHeight() {
        return this.mUnreachedBarHeight;
    }

    public void incrementProgressBy(int n) {
        if (n > 0) {
            this.setProgress(n + this.getProgress());
        }
        if (this.mListener != null) {
            this.mListener.onProgressChange(this.getProgress(), this.getMax());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        if (this.mIfDrawText) {
            this.calculateDrawRectF();
        } else {
            this.calculateDrawRectFWithoutProgressText();
        }
        if (this.mDrawReachedBar) {
            canvas.drawRect(this.mReachedRectF, this.mReachedBarPaint);
        }
        if (this.mDrawUnreachedBar) {
            canvas.drawRect(this.mUnreachedRectF, this.mUnreachedBarPaint);
        }
        if (this.mIfDrawText) {
            canvas.drawText(this.mCurrentDrawText, this.mDrawTextStart, this.mDrawTextEnd, this.mTextPaint);
        }
    }

    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(this.measure(n, true), this.measure(n2, false));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle)parcelable;
        this.mTextColor = bundle.getInt("text_color");
        this.mTextSize = bundle.getFloat("text_size");
        this.mReachedBarHeight = bundle.getFloat("reached_bar_height");
        this.mUnreachedBarHeight = bundle.getFloat("unreached_bar_height");
        this.mReachedBarColor = bundle.getInt("reached_bar_color");
        this.mUnreachedBarColor = bundle.getInt("unreached_bar_color");
        this.initializePainters();
        this.setMax(bundle.getInt("max"));
        this.setProgress(bundle.getInt("progress"));
        this.setPrefix(bundle.getString("prefix"));
        this.setSuffix(bundle.getString("suffix"));
        ProgressTextVisibility progressTextVisibility = bundle.getBoolean("text_visibility") ? ProgressTextVisibility.Visible : ProgressTextVisibility.Invisible;
        this.setProgressTextVisibility(progressTextVisibility);
        super.onRestoreInstanceState(bundle.getParcelable("saved_instance"));
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("saved_instance", super.onSaveInstanceState());
        bundle.putInt("text_color", this.getTextColor());
        bundle.putFloat("text_size", this.getProgressTextSize());
        bundle.putFloat("reached_bar_height", this.getReachedBarHeight());
        bundle.putFloat("unreached_bar_height", this.getUnreachedBarHeight());
        bundle.putInt("reached_bar_color", this.getReachedBarColor());
        bundle.putInt("unreached_bar_color", this.getUnreachedBarColor());
        bundle.putInt("max", this.getMax());
        bundle.putInt("progress", this.getProgress());
        bundle.putString("suffix", this.getSuffix());
        bundle.putString("prefix", this.getPrefix());
        bundle.putBoolean("text_visibility", this.getProgressTextVisibility());
        return bundle;
    }

    public void setMax(int n) {
        if (n > 0) {
            this.mMaxProgress = n;
            this.invalidate();
        }
    }

    public void setOnProgressBarListener(OnProgressBarListener onProgressBarListener) {
        this.mListener = onProgressBarListener;
    }

    public void setPrefix(String string2) {
        if (string2 == null) {
            this.mPrefix = "";
            return;
        }
        this.mPrefix = string2;
    }

    public void setProgress(int n) {
        if (n <= this.getMax() && n >= 0) {
            this.mCurrentProgress = n;
            this.invalidate();
        }
    }

    public void setProgressTextColor(int n) {
        this.mTextColor = n;
        this.mTextPaint.setColor(this.mTextColor);
        this.invalidate();
    }

    public void setProgressTextSize(float f) {
        this.mTextSize = f;
        this.mTextPaint.setTextSize(this.mTextSize);
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setProgressTextVisibility(ProgressTextVisibility progressTextVisibility) {
        boolean bl = progressTextVisibility == ProgressTextVisibility.Visible;
        this.mIfDrawText = bl;
        this.invalidate();
    }

    public void setReachedBarColor(int n) {
        this.mReachedBarColor = n;
        this.mReachedBarPaint.setColor(this.mReachedBarColor);
        this.invalidate();
    }

    public void setReachedBarHeight(float f) {
        this.mReachedBarHeight = f;
    }

    public void setSuffix(String string2) {
        if (string2 == null) {
            this.mSuffix = "";
            return;
        }
        this.mSuffix = string2;
    }

    public void setUnreachedBarColor(int n) {
        this.mUnreachedBarColor = n;
        this.mUnreachedBarPaint.setColor(this.mReachedBarColor);
        this.invalidate();
    }

    public void setUnreachedBarHeight(float f) {
        this.mUnreachedBarHeight = f;
    }

    public float sp2px(float f) {
        return f * this.getResources().getDisplayMetrics().scaledDensity;
    }

    public static final class ProgressTextVisibility
    extends Enum<ProgressTextVisibility> {
        private static final /* synthetic */ ProgressTextVisibility[] ENUM$VALUES;
        public static final /* enum */ ProgressTextVisibility Invisible;
        public static final /* enum */ ProgressTextVisibility Visible;

        static {
            Visible = new ProgressTextVisibility("Visible", 0);
            Invisible = new ProgressTextVisibility("Invisible", 1);
            ProgressTextVisibility[] arrprogressTextVisibility = new ProgressTextVisibility[]{Visible, Invisible};
            ENUM$VALUES = arrprogressTextVisibility;
        }

        private ProgressTextVisibility(String string3, int n2) {
            super(string2, n);
        }

        public static ProgressTextVisibility valueOf(String string2) {
            return (ProgressTextVisibility)Enum.valueOf((Class)ProgressTextVisibility.class, (String)string2);
        }

        public static ProgressTextVisibility[] values() {
            ProgressTextVisibility[] arrprogressTextVisibility = ENUM$VALUES;
            int n = arrprogressTextVisibility.length;
            ProgressTextVisibility[] arrprogressTextVisibility2 = new ProgressTextVisibility[n];
            System.arraycopy((Object)arrprogressTextVisibility, (int)0, (Object)arrprogressTextVisibility2, (int)0, (int)n);
            return arrprogressTextVisibility2;
        }
    }

}

