package com.w8.w8monitor.android.tutorial.view.interfacea;

import android.widget.RelativeLayout;

/**
 * Created by AleksandrP on 24.12.2016.
 */

public interface ShowcaseViewApi {
    void hide();

    void show();

    void setContentTitle(CharSequence title);

    void setContentText(CharSequence text);

    void setButtonPosition(RelativeLayout.LayoutParams layoutParams);

    void setButtonStartPosition(RelativeLayout.LayoutParams layoutParams);

    void setHideOnTouchOutside(boolean hideOnTouch);

    void setBlocksTouches(boolean blockTouches);

    void setStyle(int theme);

    boolean isShowing();
}

