package com.w8.w8monitor.android.tutorial.view.anime;

import android.graphics.Point;
import android.view.View;

import com.w8.w8monitor.android.tutorial.view.TutorialView;
import com.w8.w8monitor.android.tutorial.view.interfacea.AnimationFactory;

/**
 * Created by AleksandrP on 24.12.2016.
 */

public class NoAnimationFactory implements AnimationFactory {
    @Override
    public void fadeInView(View target, long duration, AnimationStartListener listener) {
        listener.onAnimationStart();
    }

    @Override
    public void fadeOutView(View target, long duration, AnimationEndListener listener) {
        listener.onAnimationEnd();
    }

    @Override
    public void animateTargetToPoint(TutorialView showcaseView, Point point) {
        showcaseView.setShowcasePosition(point.x, point.y);
    }
}
