package com.w8.w8monitor.android.tutorial.view.interfacea;

import android.view.MotionEvent;

import com.w8.w8monitor.android.tutorial.view.TutorialView;

/**
 * Created by AleksandrP on 24.12.2016.
 */

public interface OnShowcaseEventListener {

    /**
     * Called when the ShowcaseView has been told to hide. Use {@link #onShowcaseViewDidHide(import com.github.amlcurran.showcaseview.ShowcaseView;
    )}
     * if you want to know when the ShowcaseView has been fully hidden.
     */
    void onShowcaseViewHide(TutorialView showcaseView);

    /**
     * Called when the animation hiding the ShowcaseView has finished, and it is no longer visible on the screen.
     */
    void onShowcaseViewDidHide(TutorialView showcaseView);

    /**
     * Called when the ShowcaseView is shown.
     */
    void onShowcaseViewShow(TutorialView showcaseView);

    /**
     * Called when the user has touched on the ShowcaseView, but the touch was blocked
     * @param motionEvent the blocked event
     */
    void onShowcaseViewTouchBlocked(MotionEvent motionEvent);

    /**
     * Empty implementation of OnShowcaseViewEventListener such that null
     * checks aren't needed
     */
    OnShowcaseEventListener NONE = new OnShowcaseEventListener() {
        @Override
        public void onShowcaseViewHide(TutorialView showcaseView) {

        }

        @Override
        public void onShowcaseViewDidHide(TutorialView showcaseView) {

        }

        @Override
        public void onShowcaseViewShow(TutorialView showcaseView) {

        }

        @Override
        public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

        }
    };
}

