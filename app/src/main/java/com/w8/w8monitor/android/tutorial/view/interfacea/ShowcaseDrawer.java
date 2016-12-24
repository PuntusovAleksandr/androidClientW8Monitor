package com.w8.w8monitor.android.tutorial.view.interfacea;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;


/**
 * Created by AleksandrP on 24.12.2016.
 */

public interface ShowcaseDrawer {



    /**
     * Sets the value of the showcase color from themes. What this does is dependent on
     * your implementation of {@link #drawShowcase(Bitmap, float, float, float)}
     * @param color the color supplied in the theme
     */
    void setShowcaseColour(@ColorInt int color);

    /**
     * Draw the showcase. How this is performed is up to you!
     * @param buffer the bitmap to draw onto
     * @param x the x position of the point to showcase
     * @param y the y position of the point to showcase
     * @param scaleMultiplier a scale factor. Currently unused
     */
    void drawShowcase(Bitmap buffer, float x, float y, float scaleMultiplier);

    /**
     * @return the width of the showcase, used to calculate where to place text
     */
    int getShowcaseWidth();

    /**
     * @return the height of the showcase, used to calculate where to place text
     */
    int getShowcaseHeight();

    /**
     * @return the radius to block touches outside of, if
     * {@link ShowcaseView.Builder#setBlocksTouches(boolean)} is set
     */
    float getBlockedRadius();

    /**
     * Set the background color of the showcase. What this means is up to your implementation,
     * but typically this should be the color used to draw in {@link #erase(Bitmap)}
     */
    void setBackgroundColour(@ColorInt int backgroundColor);

    /**
     * Remove all drawing on the bitmap. Typically, this would do a color fill of the background
     * color. See {@link StandardShowcaseDrawer} for an example
     * @param bitmapBuffer the Bitmap to erase drawing from
     */
    void erase(Bitmap bitmapBuffer);

    /**
     * Draw the commands drawn to the canvas. Typically this is a single implementation, see
     * {@link StandardShowcaseDrawer}.
     * @param canvas canvas to draw to
     * @param bitmapBuffer bitmap to draw
     */
    void drawToCanvas(Canvas canvas, Bitmap bitmapBuffer);
}
