package com.lucerotech.aleksandrp.w8monitor.general.fragment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by AleksandrP on 06.10.2016.
 */

public class TouchView extends View {

    private int width = 200;
    private int angleStart = 135;
    private int sweep = 270;

    private Path p;

    private Paint paint = new Paint();

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Path();
        //move into center of the circle
        p.setLastPoint(width / 2, width / 2);
        //add line from the center to arc at specified angle
        p.lineTo(width / 2 + (float) Math.cos(Math.toRadians(angleStart)) * (width / 2),
                width / 2 + (float) Math.sin(Math.toRadians(angleStart)) * (width / 2));
        //add arc from start angle with specified sweep
        p.addArc(new RectF(0, 0, width, width), angleStart, sweep);
        //from end of arc return to the center of circle
        p.lineTo(width / 2, width / 2);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, width, width, paint);
        canvas.drawPath(p, paint);
    }
}
