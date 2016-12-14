/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  java.lang.String
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lefu.es.system.BaseView;

public class SearchDevicesView
extends BaseView {
    public static final boolean D = true;
    public static final String TAG = "SearchDevicesView";
    private long TIME_DIFF = 1500;
    int[] argColor = new int[]{243, 243, 250};
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    int[] innerCircle0 = new int[]{185, 255, 255};
    int[] innerCircle1 = new int[]{223, 255, 255};
    int[] innerCircle2 = new int[]{236, 255, 255};
    private boolean isSearching = false;
    int[] lineColor = new int[]{123, 123, 123};
    private float offsetArgs = 0.0f;

    public SearchDevicesView(Context context) {
        super(context);
        this.initBitmap();
    }

    public SearchDevicesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initBitmap();
    }

    public SearchDevicesView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initBitmap();
    }

    private void initBitmap() {
        if (this.bitmap == null) {
            this.bitmap = Bitmap.createBitmap((Bitmap)BitmapFactory.decodeResource((Resources)this.context.getResources(), (int)2130837581));
        }
        if (this.bitmap1 == null) {
            this.bitmap1 = Bitmap.createBitmap((Bitmap)BitmapFactory.decodeResource((Resources)this.context.getResources(), (int)2130837617));
        }
        if (this.bitmap2 == null) {
            this.bitmap2 = Bitmap.createBitmap((Bitmap)BitmapFactory.decodeResource((Resources)this.context.getResources(), (int)2130837580));
        }
    }

    public boolean isSearching() {
        return this.isSearching;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.bitmap, (float)(this.getWidth() / 2 - this.bitmap.getWidth() / 2), (float)(this.getHeight() / 2 - this.bitmap.getHeight() / 2), null);
        if (this.isSearching) {
            Rect rect = new Rect(this.getWidth() / 2 - this.bitmap2.getWidth(), this.getHeight() / 2, this.getWidth() / 2, this.getHeight() / 2 + this.bitmap2.getHeight());
            canvas.rotate(this.offsetArgs, (float)(this.getWidth() / 2), (float)(this.getHeight() / 2));
            canvas.drawBitmap(this.bitmap2, null, rect, null);
            this.offsetArgs = 3.0f + this.offsetArgs;
        } else {
            canvas.drawBitmap(this.bitmap2, (float)(this.getWidth() / 2 - this.bitmap2.getWidth()), (float)(this.getHeight() / 2), null);
        }
        canvas.drawBitmap(this.bitmap1, (float)(this.getWidth() / 2 - this.bitmap1.getWidth() / 2), (float)(this.getHeight() / 2 - this.bitmap1.getHeight() / 2), null);
        if (this.isSearching) {
            this.invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void setSearching(boolean bl) {
        this.isSearching = bl;
        this.offsetArgs = 0.0f;
        this.invalidate();
    }
}

