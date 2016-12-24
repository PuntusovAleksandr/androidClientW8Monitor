package com.w8.w8monitor.android.tutorial.view.interfacea;

import android.graphics.Point;
import android.view.View;

import com.w8.w8monitor.android.tutorial.view.TutorialView;


/**
 * Created by AleksandrP on 24.12.2016.
 */

public interface AnimationFactory {

    void fadeInView(View target, long duration, AnimationStartListener listener);

    void fadeOutView(View target, long duration, AnimationEndListener listener);

    void animateTargetToPoint(TutorialView showcaseView, Point point);

    interface AnimationStartListener {
        void onAnimationStart();
    }

    interface AnimationEndListener {
        void onAnimationEnd();
    }
}
