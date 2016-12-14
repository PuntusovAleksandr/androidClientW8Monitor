/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.widget.RelativeLayout
 */
package com.lefu.es.system;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class BaseView
extends RelativeLayout {
    public Context context;

    public BaseView(Context context) {
        super(context);
        this.context = context;
    }

    public BaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
    }

    public BaseView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.context = context;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

