package com.w8.w8monitor.android.tutorial.view.showcaseview.targets;

import android.graphics.Point;

/**
 * Created by AleksandrP on 24.12.2016.
 */

public interface Target {

    Target NONE = new Target() {
        @Override
        public Point getPoint() {
            return new Point(1000000, 1000000);
        }
    };

    Point getPoint();
}
