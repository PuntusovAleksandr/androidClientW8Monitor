package com.w8.w8monitor.android.tutorial.view.anime;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import android.text.style.TextAppearanceSpan;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.tutorial.view.TutorialView;

/**
 * Created by AleksandrP on 24.12.2016.
 */

public class TextDrawer {
    private static final int INDEX_TEXT_START_X = 0;
    private static final int INDEX_TEXT_START_Y = 1;
    private static final int INDEX_TEXT_WIDTH = 2;

    private final TextPaint titlePaint;
    private final TextPaint textPaint;
    private final Context context;
    private final float padding;
    private final float actionBarOffset;

    private Layout.Alignment textAlignment = Layout.Alignment.ALIGN_NORMAL;
    private SpannableString textString;
    private DynamicLayout textLayout;
    private MetricAffectingSpan textSpan;

    private Layout.Alignment titleAlignment = Layout.Alignment.ALIGN_NORMAL;
    private SpannableString titleString;
    private DynamicLayout titleLayout;
    private MetricAffectingSpan titleSpan;

    private float[] mBestTextPosition = new float[3];
    private boolean hasRecalculated;
    @TutorialView.TextPosition
    private int forcedTextPosition = TutorialView.UNDEFINED;

    public TextDrawer(Resources resources, Context context) {
        padding = resources.getDimension(R.dimen.text_padding);
        actionBarOffset = resources.getDimension(R.dimen.action_bar_offset);

        this.context = context;

        titlePaint = new TextPaint();
        titlePaint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        if (shouldDrawText()) {
            float[] textPosition = getBestTextPosition();
            int width = Math.max(0, (int) mBestTextPosition[INDEX_TEXT_WIDTH]);

            if (!TextUtils.isEmpty(titleString)) {
                canvas.save();
                if (hasRecalculated) {
                    titleLayout = new DynamicLayout(titleString, titlePaint,
                            width, titleAlignment, 1.0f, 1.0f, true);
                }
                if (titleLayout != null) {
                    canvas.translate(textPosition[INDEX_TEXT_START_X], textPosition[INDEX_TEXT_START_Y]);
                    titleLayout.draw(canvas);
                    canvas.restore();
                }
            }

            if (!TextUtils.isEmpty(textString)) {
                canvas.save();
                if (hasRecalculated) {
                    textLayout = new DynamicLayout(textString, textPaint,
                            width, textAlignment, 1.2f, 1.0f, true);
                }
                float offsetForTitle = titleLayout != null ? titleLayout.getHeight() : 0;
                if (textLayout != null) {
                    canvas.translate(textPosition[INDEX_TEXT_START_X], textPosition[INDEX_TEXT_START_Y] + offsetForTitle);
                    textLayout.draw(canvas);
                    canvas.restore();
                }

            }
        }
        hasRecalculated = false;
    }

    public void setContentText(CharSequence details) {
        if (details != null) {
            SpannableString ssbDetail = new SpannableString(details);
            ssbDetail.setSpan(textSpan, 0, ssbDetail.length(), 0);
            textString = ssbDetail;
            hasRecalculated = true;
        }
    }

    public void setContentTitle(CharSequence title) {
        if (title != null) {
            SpannableString ssbTitle = new SpannableString(title);
            ssbTitle.setSpan(titleSpan, 0, ssbTitle.length(), 0);
            titleString = ssbTitle;
            hasRecalculated = true;
        }
    }

    /**
     * Calculates the best place to position text
     *
     * @param canvasW          width of the screen
     * @param canvasH          height of the screen
     * @param shouldCentreText
     * @param showcase
     */
    public void calculateTextPosition(int canvasW, int canvasH, boolean shouldCentreText, Rect showcase) {

        int[] areas = new int[4]; //left, top, right, bottom
        areas[TutorialView.LEFT_OF_SHOWCASE] = showcase.left * canvasH;
        areas[TutorialView.ABOVE_SHOWCASE] = showcase.top * canvasW;
        areas[TutorialView.RIGHT_OF_SHOWCASE] = (canvasW - showcase.right) * canvasH;
        areas[TutorialView.BELOW_SHOWCASE] = (canvasH - showcase.bottom) * canvasW;

        int largest = 0;
        for (int i = 1; i < areas.length; i++) {
            if (areas[i] > areas[largest])
                largest = i;
        }

        if (forcedTextPosition != TutorialView.UNDEFINED) {
            largest = forcedTextPosition;
        }

        // Position text in largest area
        switch (largest) {
            case TutorialView.LEFT_OF_SHOWCASE:
                mBestTextPosition[INDEX_TEXT_START_X] = padding;
                mBestTextPosition[INDEX_TEXT_START_Y] = padding;
                mBestTextPosition[INDEX_TEXT_WIDTH] = showcase.left - 2 * padding;
                break;
            case TutorialView.ABOVE_SHOWCASE:
                mBestTextPosition[INDEX_TEXT_START_X] = padding;
                mBestTextPosition[INDEX_TEXT_START_Y] = padding + actionBarOffset;
                mBestTextPosition[INDEX_TEXT_WIDTH] = canvasW - 2 * padding;
                break;
            case TutorialView.RIGHT_OF_SHOWCASE:
                mBestTextPosition[INDEX_TEXT_START_X] = showcase.right + padding;
                mBestTextPosition[INDEX_TEXT_START_Y] = padding;
                mBestTextPosition[INDEX_TEXT_WIDTH] = (canvasW - showcase.right) - 2 * padding;
                break;
            case TutorialView.BELOW_SHOWCASE:
                mBestTextPosition[INDEX_TEXT_START_X] = padding;
                mBestTextPosition[INDEX_TEXT_START_Y] = showcase.bottom + padding;
                mBestTextPosition[INDEX_TEXT_WIDTH] = canvasW - 2 * padding;
                break;
        }
        if (shouldCentreText) {
            // Center text vertically or horizontally
            switch (largest) {
                case TutorialView.LEFT_OF_SHOWCASE:
                case TutorialView.RIGHT_OF_SHOWCASE:
                    mBestTextPosition[INDEX_TEXT_START_Y] += canvasH / 4;
                    break;
                case TutorialView.ABOVE_SHOWCASE:
                case TutorialView.BELOW_SHOWCASE:
                    mBestTextPosition[INDEX_TEXT_WIDTH] /= 2;
                    mBestTextPosition[INDEX_TEXT_START_X] += canvasW / 4;
                    break;
            }
        } else {
            // As text is not centered add actionbar padding if the text is left or right
            switch (largest) {
                case TutorialView.LEFT_OF_SHOWCASE:
                case TutorialView.RIGHT_OF_SHOWCASE:
                    mBestTextPosition[INDEX_TEXT_START_Y] += actionBarOffset;
                    break;
            }
        }

        hasRecalculated = true;
    }

    public void setTitleStyling(int styleId) {
        titleSpan = new TextAppearanceSpan(this.context, styleId);
        setContentTitle(titleString);
    }

    public void setDetailStyling(int styleId) {
        textSpan = new TextAppearanceSpan(this.context, styleId);
        setContentText(textString);
    }

    public float[] getBestTextPosition() {
        return mBestTextPosition;
    }

    public boolean shouldDrawText() {
        return !TextUtils.isEmpty(titleString) || !TextUtils.isEmpty(textString);
    }

    public void setContentPaint(TextPaint contentPaint) {
        textPaint.set(contentPaint);
        if (textString != null) {
            textString.removeSpan(textSpan);
        }
        textSpan = new NoOpSpan();
        setContentText(textString);
    }

    public void setTitlePaint(TextPaint textPaint) {
        titlePaint.set(textPaint);
        if (titleString != null) {
            titleString.removeSpan(titleSpan);
        }
        titleSpan = new NoOpSpan();
        setContentTitle(titleString);
    }

    public void setDetailTextAlignment(Layout.Alignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public void setTitleTextAlignment(Layout.Alignment titleTextAlignment) {
        this.titleAlignment = titleTextAlignment;
    }

    public void forceTextPosition(@TutorialView.TextPosition int textPosition) {
        if (textPosition > TutorialView.BELOW_SHOWCASE || textPosition < TutorialView.UNDEFINED) {
            throw new IllegalArgumentException("TutorialView text was forced with an invalid position");
        }
        forcedTextPosition = textPosition;
    }

    private static class NoOpSpan extends MetricAffectingSpan {
        @Override
        public void updateDrawState(TextPaint tp) {
            // No-op
        }

        @Override
        public void updateMeasureState(TextPaint p) {
            // No-op
        }
    }
}
