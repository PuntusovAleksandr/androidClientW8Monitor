package com.w8.w8monitor.android.tutorial.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.tutorial.view.anime.AnimatorAnimationFactory;
import com.w8.w8monitor.android.tutorial.view.anime.NewShowcaseDrawer;
import com.w8.w8monitor.android.tutorial.view.anime.NoAnimationFactory;
import com.w8.w8monitor.android.tutorial.view.anime.ShowcaseAreaCalculator;
import com.w8.w8monitor.android.tutorial.view.anime.StandardShowcaseDrawer;
import com.w8.w8monitor.android.tutorial.view.anime.TextDrawer;
import com.w8.w8monitor.android.tutorial.view.interfacea.AnimationFactory;
import com.w8.w8monitor.android.tutorial.view.interfacea.OnShowcaseEventListener;
import com.w8.w8monitor.android.tutorial.view.interfacea.ShowcaseDrawer;
import com.w8.w8monitor.android.tutorial.view.interfacea.ShowcaseViewApi;
import com.w8.w8monitor.android.tutorial.view.showcaseview.MaterialShowcaseDrawer;
import com.w8.w8monitor.android.tutorial.view.showcaseview.targets.Target;
import com.w8.w8monitor.android.tutorial.view.utils.ApiUtils;
import com.w8.w8monitor.android.tutorial.view.utils.ShotStateStore;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by AleksandrP on 24.12.2016.
 */

public class TutorialView extends RelativeLayout
        implements View.OnTouchListener, ShowcaseViewApi {


    private static final int HOLO_BLUE = Color.parseColor("#33B5E5");
    public static final int UNDEFINED = -1;
    public static final int LEFT_OF_SHOWCASE = 0;
    public static final int RIGHT_OF_SHOWCASE = 2;
    public static final int ABOVE_SHOWCASE = 1;
    public static final int BELOW_SHOWCASE = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UNDEFINED, LEFT_OF_SHOWCASE, RIGHT_OF_SHOWCASE, ABOVE_SHOWCASE, BELOW_SHOWCASE})
    public @interface TextPosition {
    }

    private Button mEndButton;
    private Button mStartButton;
    private final TextDrawer textDrawer;
    private ShowcaseDrawer showcaseDrawer;
    private final ShowcaseAreaCalculator showcaseAreaCalculator;
    private final AnimationFactory animationFactory;
    private final ShotStateStore shotStateStore;

    // Showcase metrics
    private int showcaseX = -1;
    private int showcaseY = -1;
    private float scaleMultiplier = 1f;

    // Touch items
    private boolean hasCustomClickListener = false;
    private boolean blockTouches = true;
    private boolean hideOnTouch = false;
    private OnShowcaseEventListener mEventListener = OnShowcaseEventListener.NONE;

    private boolean hasAlteredText = false;
    private boolean hasNoTarget = false;
    private boolean shouldCentreText;
    private Bitmap bitmapBuffer;


    // Animation items
    private long fadeInMillis;
    private long fadeOutMillis;
    private boolean isShowing;
    private int backgroundColor;
    private int showcaseColor;
    private boolean blockAllTouches;
    private final int[] positionInWindow = new int[2];


    protected TutorialView(Context context, boolean newStyle) {
        this(context, null, R.styleable.CustomTheme_showcaseViewStyle, newStyle);
    }

    protected TutorialView(Context context, AttributeSet attrs, int defStyle, boolean newStyle) {
        super(context, attrs, defStyle);

        ApiUtils apiUtils = new ApiUtils();
        if (apiUtils.isCompatWithHoneycomb()) {
            animationFactory = new AnimatorAnimationFactory();
        } else {
            animationFactory = new NoAnimationFactory();
        }
        showcaseAreaCalculator = new ShowcaseAreaCalculator();
        shotStateStore = new ShotStateStore(context);

        // Get the attributes for the ShowcaseView
        final TypedArray styled = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ShowcaseView, R.attr.showcaseViewStyle,
                        R.style.ShowcaseView);

        // Set the default animation times
        fadeInMillis = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        fadeOutMillis = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        mEndButton = (Button) LayoutInflater.from(context).inflate(R.layout.showcase_button, null);
        mStartButton = (Button) LayoutInflater.from(context).inflate(R.layout.showcase_button, null);

        mEndButton.setTextSize(18);
        mStartButton.setTextSize(18);
        setIconEnd();
        Drawable imgStart = getContext().getResources().getDrawable(R.drawable.arrow_back);
        mStartButton.setCompoundDrawablesWithIntrinsicBounds(imgStart, null, null, null);

        if (newStyle) {
            showcaseDrawer = new NewShowcaseDrawer(getResources(), context.getTheme());
        } else {
            showcaseDrawer = new StandardShowcaseDrawer(getResources(), context.getTheme());
        }
        textDrawer = new TextDrawer(getResources(), getContext());

        updateStyle(styled, false);

        init();
    }

    public int getButtonEndId() {
        return mEndButton.getId();
    }

    public int getButtonStartId() {
        return mStartButton.getId();
    }

    public void setIconEnd() {
        Drawable imgEnd = getContext().getResources().getDrawable(R.drawable.arrow_forward);
        mEndButton.setCompoundDrawablesWithIntrinsicBounds(null, null, imgEnd, null);
    }

    public void removeIconEnd() {
        Drawable imgEnd = getContext().getResources().getDrawable(R.drawable.arrow_forward);
        mEndButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

    }

    public View getButtonStartView() {
        return mStartButton;
    }

    private void init() {

        setOnTouchListener(this);

        if (mEndButton.getParent() == null) {
            int margin = (int) getResources().getDimension(R.dimen.button_margin);
            RelativeLayout.LayoutParams lps = (LayoutParams) generateDefaultLayoutParams();
            lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lps.setMargins(margin, margin, margin, margin);
            mEndButton.setLayoutParams(lps);
            mEndButton.setText(android.R.string.ok);
            if (!hasCustomClickListener) {
                mEndButton.setOnClickListener(hideOnClickListener);
            }
            addView(mEndButton);
        }
        if (mStartButton.getParent() == null) {
            int margin = (int) getResources().getDimension(R.dimen.button_margin);
            RelativeLayout.LayoutParams lps = (LayoutParams) generateDefaultLayoutParams();
            lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            lps.setMargins(margin, margin, margin, margin);
            mStartButton.setLayoutParams(lps);
            mStartButton.setText(android.R.string.ok);
            if (!hasCustomClickListener) {
                mStartButton.setOnClickListener(hideOnClickListener);
            }
            addView(mStartButton);
        }

    }

    private boolean hasShot() {
        return shotStateStore.hasShot();
    }

    public void setShowcasePosition(Point point) {
        setShowcasePosition(point.x, point.y);
    }

    public void setShowcasePosition(int x, int y) {
        if (shotStateStore.hasShot()) {
            return;
        }
        getLocationInWindow(positionInWindow);
        showcaseX = x - positionInWindow[0];
        showcaseY = y - positionInWindow[1];
        //init();
        recalculateText();
        invalidate();
    }

    public void setTarget(final Target target) {
        setShowcase(target, false);
    }

    public void setShowcase(final Target target, final boolean animate) {
        postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!shotStateStore.hasShot()) {

                    if (canUpdateBitmap()) {
                        updateBitmap();
                    }

                    Point targetPoint = target.getPoint();
                    if (targetPoint != null) {
                        hasNoTarget = false;
                        if (animate) {
                            animationFactory.animateTargetToPoint(TutorialView.this, targetPoint);
                        } else {
                            setShowcasePosition(targetPoint);
                        }
                    } else {
                        hasNoTarget = true;
                        invalidate();
                    }

                }
            }
        }, 100);
    }

    private void updateBitmap() {
        if (bitmapBuffer == null || haveBoundsChanged()) {
            if (bitmapBuffer != null) {
                bitmapBuffer.recycle();
            }
            bitmapBuffer = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        }
    }

    private boolean haveBoundsChanged() {
        return getMeasuredWidth() != bitmapBuffer.getWidth() ||
                getMeasuredHeight() != bitmapBuffer.getHeight();
    }

    public boolean hasShowcaseView() {
        return (showcaseX != 1000000 && showcaseY != 1000000) && !hasNoTarget;
    }

    public void setShowcaseX(int x) {
        setShowcasePosition(x, getShowcaseY());
    }

    public void setShowcaseY(int y) {
        setShowcasePosition(getShowcaseX(), y);
    }

    public int getShowcaseX() {
        getLocationInWindow(positionInWindow);
        return showcaseX + positionInWindow[0];
    }

    public int getShowcaseY() {
        getLocationInWindow(positionInWindow);
        return showcaseY + positionInWindow[1];
    }

    /**
     * Override the standard button click event
     *
     * @param listener Listener to listen to on click events
     */
    public void overrideButtonClick(OnClickListener listener) {
        if (shotStateStore.hasShot()) {
            return;
        }
        if (mEndButton != null) {
            if (listener != null) {
                mEndButton.setOnClickListener(listener);
            } else {
                mEndButton.setOnClickListener(hideOnClickListener);
            }
        }
        hasCustomClickListener = true;
    }

    /**
     * Override the standard button click event
     *
     * @param listener Listener to listen to on click events
     */
    public void overrideButtonStartClick(OnClickListener listener) {
        if (shotStateStore.hasShot()) {
            return;
        }
        if (mStartButton != null) {
            if (listener != null) {
                mStartButton.setOnClickListener(listener);
            } else {
                mStartButton.setOnClickListener(hideOnClickListener);
            }
        }
        hasCustomClickListener = true;
    }

    public void setOnShowcaseEventListener(OnShowcaseEventListener listener) {
        if (listener != null) {
            mEventListener = listener;
        } else {
            mEventListener = OnShowcaseEventListener.NONE;
        }
    }

    public void setButtonText(CharSequence text) {
        if (mEndButton != null) {
            mEndButton.setText(text);
        }
    }

    public void setButtonStartText(CharSequence text) {
        if (mStartButton != null) {
            mStartButton.setText(text);
        }
    }

    private void recalculateText() {
        boolean recalculatedCling = showcaseAreaCalculator.calculateShowcaseRect(showcaseX, showcaseY, showcaseDrawer);
        boolean recalculateText = recalculatedCling || hasAlteredText;
        if (recalculateText) {
            Rect rect = hasShowcaseView() ? showcaseAreaCalculator.getShowcaseRect() : new Rect();
            textDrawer.calculateTextPosition(getMeasuredWidth(), getMeasuredHeight(), shouldCentreText, rect);
        }
        hasAlteredText = false;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (showcaseX < 0 || showcaseY < 0 || shotStateStore.hasShot() || bitmapBuffer == null) {
            super.dispatchDraw(canvas);
            return;
        }

        //Draw background color
        showcaseDrawer.erase(bitmapBuffer);

        // Draw the showcase drawable
        if (!hasNoTarget) {
            showcaseDrawer.drawShowcase(bitmapBuffer, showcaseX, showcaseY, scaleMultiplier);
            showcaseDrawer.drawToCanvas(canvas, bitmapBuffer);
        }

        // Draw the text on the screen, recalculating its position if necessary
        textDrawer.draw(canvas);

        super.dispatchDraw(canvas);

    }

    @Override
    public void hide() {
        // If the type is set to one-shot, store that it has shot
        shotStateStore.storeShot();
        mEventListener.onShowcaseViewHide(this);
        fadeOutShowcase();
    }

    private void clearBitmap() {
        if (bitmapBuffer != null && !bitmapBuffer.isRecycled()) {
            bitmapBuffer.recycle();
            bitmapBuffer = null;
        }
    }

    private void fadeOutShowcase() {
        animationFactory.fadeOutView(
                this, fadeOutMillis, new AnimationFactory.AnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        setVisibility(View.GONE);
                        clearBitmap();
                        isShowing = false;
                        mEventListener.onShowcaseViewDidHide(TutorialView.this);
                    }
                }
        );
    }

    @Override
    public void show() {
        isShowing = true;
        if (canUpdateBitmap()) {
            updateBitmap();
        }
        mEventListener.onShowcaseViewShow(this);
        fadeInShowcase();
    }

    private boolean canUpdateBitmap() {
        return getMeasuredHeight() > 0 && getMeasuredWidth() > 0;
    }

    private void fadeInShowcase() {
        animationFactory.fadeInView(
                this, fadeInMillis,
                new AnimationFactory.AnimationStartListener() {
                    @Override
                    public void onAnimationStart() {
                        setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (blockAllTouches) {
            mEventListener.onShowcaseViewTouchBlocked(motionEvent);
            return true;
        }

        float xDelta = Math.abs(motionEvent.getRawX() - showcaseX);
        float yDelta = Math.abs(motionEvent.getRawY() - showcaseY);
        double distanceFromFocus = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));

        if (MotionEvent.ACTION_UP == motionEvent.getAction() &&
                hideOnTouch && distanceFromFocus > showcaseDrawer.getBlockedRadius()) {
            this.hide();
            return true;
        }

        boolean blocked = blockTouches && distanceFromFocus > showcaseDrawer.getBlockedRadius();
        if (blocked) {
            mEventListener.onShowcaseViewTouchBlocked(motionEvent);
        }
        return blocked;
    }

    private static void insertShowcaseView(TutorialView showcaseView, ViewGroup parent, int parentIndex) {
        parent.addView(showcaseView, parentIndex);
        if (!showcaseView.hasShot()) {
            showcaseView.show();
        } else {
            showcaseView.hideImmediate();
        }
    }

    private void hideImmediate() {
        isShowing = false;
        setVisibility(GONE);
    }

    @Override
    public void setContentTitle(CharSequence title) {
        textDrawer.setContentTitle(title);
        invalidate();
    }

    @Override
    public void setContentText(CharSequence text) {
        textDrawer.setContentText(text);
        invalidate();
    }

    private void setScaleMultiplier(float scaleMultiplier) {
        this.scaleMultiplier = scaleMultiplier;
    }

    public void hideButton() {
        mEndButton.setVisibility(GONE);
    }

    public void showButton() {
        mEndButton.setVisibility(VISIBLE);
    }

    public void hideButtonStart() {
        mStartButton.setVisibility(GONE);
    }

    public void showButtonStart() {
        mStartButton.setVisibility(VISIBLE);
    }

    public void setBacgroundButtons(int res) {
        mEndButton.setBackgroundColor(res);
        mStartButton.setBackgroundColor(res);
    }

    /**
     * Builder class which allows easier creation of {@link TutorialView}s.
     * It is recommended that you use this Builder class.
     */
    public static class Builder {

        private final TutorialView showcaseView;
        private final Activity activity;

        private ViewGroup parent;
        private int parentIndex;

        public Builder(Activity activity) {
            this(activity, false);
        }

        /**
         * @param useNewStyle should use "new style" showcase (see {@link #withNewStyleShowcase()}
         * @deprecated use {@link #withHoloShowcase()}, {@link #withNewStyleShowcase()}, or
         * {@link #setShowcaseDrawer(ShowcaseDrawer)}
         */
        @Deprecated
        public Builder(Activity activity, boolean useNewStyle) {
            this.activity = activity;
            this.showcaseView = new TutorialView(activity, useNewStyle);
            this.showcaseView.setTarget(Target.NONE);
            this.parent = (ViewGroup) activity.findViewById(android.R.id.content);
            this.parentIndex = parent.getChildCount();
        }

        /**
         * Create the {@link com.github.amlcurran.showcaseview.ShowcaseView} and show it.
         *
         * @return the created ShowcaseView
         */
        public TutorialView build() {
            insertShowcaseView(showcaseView, parent, parentIndex);
            return showcaseView;
        }

        /**
         * Draw a holo-style showcase. This is the default.<br/>
         * <img alt="Holo showcase example" src="../../../../../../../../example2.png" />
         */
        public Builder withHoloShowcase() {
            return setShowcaseDrawer(new StandardShowcaseDrawer(activity.getResources(), activity.getTheme()));
        }

        /**
         * Draw a new-style showcase.<br/>
         * <img alt="Holo showcase example" src="../../../../../../../../example.png" />
         */
        public Builder withNewStyleShowcase() {
            return setShowcaseDrawer(new NewShowcaseDrawer(activity.getResources(), activity.getTheme()));
        }

        /**
         * Draw a material style showcase.
         * <img alt="Material showcase" src="../../../../../../../../material.png" />
         */
        public Builder withMaterialShowcase() {
            return setShowcaseDrawer(new MaterialShowcaseDrawer(activity.getResources()));
        }

        /**
         * Set a custom showcase drawer which will be responsible for measuring and drawing the showcase
         */
        public Builder setShowcaseDrawer(ShowcaseDrawer showcaseDrawer) {
            showcaseView.setShowcaseDrawer(showcaseDrawer);
            return this;
        }

        /**
         * Set the title text shown on the ShowcaseView.
         */
        public Builder setContentTitle(int resId) {
            return setContentTitle(activity.getString(resId));
        }

        /**
         * Set the title text shown on the ShowcaseView.
         */
        public Builder setContentTitle(CharSequence title) {
            showcaseView.setContentTitle(title);
            return this;
        }

        /**
         * Set the descriptive text shown on the ShowcaseView.
         */
        public Builder setContentText(int resId) {
            return setContentText(activity.getString(resId));
        }

        /**
         * Set the descriptive text shown on the ShowcaseView.
         */
        public Builder setContentText(CharSequence text) {
            showcaseView.setContentText(text);
            return this;
        }

        /**
         * Set the target of the showcase.
         *
         * @param target a {@link com.github.amlcurran.showcaseview.targets.Target} representing
         *               the item to showcase (e.g., a button, or action item).
         */
        public Builder setTarget(Target target) {
            showcaseView.setTarget(target);
            return this;
        }

        /**
         * Set the style of the ShowcaseView. See the sample app for example styles.
         */
        public Builder setStyle(int theme) {
            showcaseView.setStyle(theme);
            return this;
        }

        /**
         * Set a listener which will override the button clicks.
         * <p/>
         * Note that you will have to manually hide the ShowcaseView
         */
        public Builder setOnClickListener(OnClickListener onClickListener) {
            showcaseView.overrideButtonClick(onClickListener);
            return this;
        }

        /**
         * Set a listener which will override the button clicks.
         * <p/>
         * Note that you will have to manually hide the ShowcaseView
         */
        public Builder setOnClickListenerStart(OnClickListener onClickListener) {
            showcaseView.overrideButtonStartClick(onClickListener);
            return this;
        }

        /**
         * Don't make the ShowcaseView block touches on itself. This doesn't
         * block touches in the showcased area.
         * <p/>
         * By default, the ShowcaseView does block touches
         */
        public Builder doNotBlockTouches() {
            showcaseView.setBlocksTouches(false);
            return this;
        }

        /**
         * Make this ShowcaseView hide when the user touches outside the showcased area.
         * This enables {@link #doNotBlockTouches()} as well.
         * <p/>
         * By default, the ShowcaseView doesn't hide on touch.
         */
        public Builder hideOnTouchOutside() {
            showcaseView.setBlocksTouches(true);
            showcaseView.setHideOnTouchOutside(true);
            return this;
        }

        /**
         * Set the ShowcaseView to only ever show once.
         *
         * @param shotId a unique identifier (<em>across the app</em>) to store
         *               whether this ShowcaseView has been shown.
         */
        public Builder singleShot(long shotId) {
            showcaseView.setSingleShot(shotId);
            return this;
        }

        public Builder setShowcaseEventListener(OnShowcaseEventListener showcaseEventListener) {
            showcaseView.setOnShowcaseEventListener(showcaseEventListener);
            return this;
        }

        public Builder setParent(ViewGroup parent, int index) {
            this.parent = parent;
            this.parentIndex = index;
            return this;
        }

        /**
         * Sets the paint that will draw the text as specified by {@link #setContentText(CharSequence)}
         * or {@link #setContentText(int)}. If you're using a TextAppearance (set by {@link #setStyle(int)},
         * then this {@link TextPaint} will override that TextAppearance.
         */
        public Builder setContentTextPaint(TextPaint textPaint) {
            showcaseView.setContentTextPaint(textPaint);
            return this;
        }

        /**
         * Sets the paint that will draw the text as specified by {@link #setContentTitle(CharSequence)}
         * or {@link #setContentTitle(int)}. If you're using a TextAppearance (set by {@link #setStyle(int)},
         * then this {@link TextPaint} will override that TextAppearance.
         */
        public Builder setContentTitlePaint(TextPaint textPaint) {
            showcaseView.setContentTitlePaint(textPaint);
            return this;
        }

        /**
         * Replace the end button with the one provided. Note that this resets any OnClickListener provided
         * by {@link #setOnClickListener(OnClickListener)}, so call this method before that one.
         */
        public Builder replaceEndButton(Button button) {
            showcaseView.setEndButton(button);
            return this;
        }

        /**
         * Replace the end button with the one provided. Note that this resets any OnClickListener provided
         * by {@link #setOnClickListener(OnClickListener)}, so call this method before that one.
         */
        public Builder replaceStartButton(Button button) {
            showcaseView.setStartButton(button);
            return this;
        }

        /**
         * Replace the end button with the one provided. Note that this resets any OnClickListener provided
         * by {@link #setOnClickListener(OnClickListener)}, so call this method before that one.
         */
        public Builder replaceEndButton(int buttonResourceId) {
            View view = LayoutInflater.from(activity).inflate(buttonResourceId, showcaseView, false);
            if (!(view instanceof Button)) {
                throw new IllegalArgumentException("Attempted to replace showcase button with a layout which isn't a button");
            }
            return replaceEndButton((Button) view);
        }

        /**
         * Replace the end button with the one provided. Note that this resets any OnClickListener provided
         * by {@link #setOnClickListener(OnClickListener)}, so call this method before that one.
         */
        public Builder replaceStartButton(int buttonResourceId) {
            View view = LayoutInflater.from(activity).inflate(buttonResourceId, showcaseView, false);
            if (!(view instanceof Button)) {
                throw new IllegalArgumentException("Attempted to replace showcase button with a layout which isn't a button");
            }
            return replaceStartButton((Button) view);
        }

        /**
         * Block any touch made on the ShowcaseView, even inside the showcase
         */
        public Builder blockAllTouches() {
            showcaseView.setBlockAllTouches(true);
            return this;
        }

        /**
         * Uses the android decor view to insert a showcase, this is not recommended
         * as then UI elements in showcase view can hide behind the nav bar
         */
        public Builder useDecorViewAsParent() {
            this.parent = ((ViewGroup) activity.getWindow().getDecorView());
            this.parentIndex = -1;
            return this;
        }
    }

    private void setEndButton(Button button) {
        LayoutParams copyParams = (LayoutParams) mEndButton.getLayoutParams();
        mEndButton.setOnClickListener(null);
        removeView(mEndButton);
        mEndButton = button;
        button.setOnClickListener(hideOnClickListener);
        button.setLayoutParams(copyParams);
        addView(button);
    }

    private void setStartButton(Button button) {
        LayoutParams copyParams = (LayoutParams) mStartButton.getLayoutParams();
        mStartButton.setOnClickListener(null);
        removeView(mStartButton);
        mStartButton = button;
        button.setOnClickListener(hideOnClickListener);
        button.setLayoutParams(copyParams);
        addView(button);
    }

    private void setShowcaseDrawer(ShowcaseDrawer showcaseDrawer) {
        this.showcaseDrawer = showcaseDrawer;
        this.showcaseDrawer.setBackgroundColour(backgroundColor);
        this.showcaseDrawer.setShowcaseColour(showcaseColor);
        hasAlteredText = true;
        invalidate();
    }

    private void setContentTitlePaint(TextPaint textPaint) {
        this.textDrawer.setTitlePaint(textPaint);
        hasAlteredText = true;
        invalidate();
    }

    private void setContentTextPaint(TextPaint paint) {
        this.textDrawer.setContentPaint(paint);
        hasAlteredText = true;
        invalidate();
    }

    /**
     * Set whether the text should be centred in the screen, or left-aligned (which is the default).
     */
    public void setShouldCentreText(boolean shouldCentreText) {
        this.shouldCentreText = shouldCentreText;
        hasAlteredText = true;
        invalidate();
    }

    /**
     * @see com.github.amlcurran.showcaseview.ShowcaseView.Builder#setSingleShot(long)
     */
    private void setSingleShot(long shotId) {
        shotStateStore.setSingleShot(shotId);
    }

    /**
     * Change the position of the ShowcaseView's button from the default bottom-right position.
     *
     * @param layoutParams a {@link android.widget.RelativeLayout.LayoutParams} representing
     *                     the new position of the button
     */
    @Override
    public void setButtonPosition(RelativeLayout.LayoutParams layoutParams) {
        mEndButton.setLayoutParams(layoutParams);
    }

    /**
     * Change the position of the ShowcaseView's button from the default bottom-right position.
     *
     * @param layoutParams a {@link android.widget.RelativeLayout.LayoutParams} representing
     *                     the new position of the button
     */
    @Override
    public void setButtonStartPosition(RelativeLayout.LayoutParams layoutParams) {
        mStartButton.setLayoutParams(layoutParams);
    }

    /**
     * Sets the text alignment of the detail text
     */
    public void setDetailTextAlignment(Layout.Alignment textAlignment) {
        textDrawer.setDetailTextAlignment(textAlignment);
        hasAlteredText = true;
        invalidate();
    }

    /**
     * Sets the text alignment of the title text
     */
    public void setTitleTextAlignment(Layout.Alignment textAlignment) {
        textDrawer.setTitleTextAlignment(textAlignment);
        hasAlteredText = true;
        invalidate();
    }

    /**
     * Set the duration of the fading in and fading out of the ShowcaseView
     */
    private void setFadeDurations(long fadeInMillis, long fadeOutMillis) {
        this.fadeInMillis = fadeInMillis;
        this.fadeOutMillis = fadeOutMillis;
    }

    public void forceTextPosition(@TextPosition int textPosition) {
        textDrawer.forceTextPosition(textPosition);
        hasAlteredText = true;
        invalidate();
    }

    /**
     * @see com.github.amlcurran.showcaseview.ShowcaseView.Builder#hideOnTouchOutside()
     */
    @Override
    public void setHideOnTouchOutside(boolean hideOnTouch) {
        this.hideOnTouch = hideOnTouch;
    }

    /**
     * @see com.github.amlcurran.showcaseview.ShowcaseView.Builder#doNotBlockTouches()
     */
    @Override
    public void setBlocksTouches(boolean blockTouches) {
        this.blockTouches = blockTouches;
    }

    private void setBlockAllTouches(boolean blockAllTouches) {
        this.blockAllTouches = blockAllTouches;
    }

    /**
     * @see com.github.amlcurran.showcaseview.ShowcaseView.Builder#setStyle(int)
     */
    @Override
    public void setStyle(int theme) {
        TypedArray array = getContext().obtainStyledAttributes(theme, R.styleable.ShowcaseView);
        updateStyle(array, true);
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    private void updateStyle(TypedArray styled, boolean invalidate) {
        backgroundColor = styled.getColor(R.styleable.ShowcaseView_sv_backgroundColor, Color.argb(128, 80, 80, 80));
        showcaseColor = styled.getColor(R.styleable.ShowcaseView_sv_showcaseColor, HOLO_BLUE);
        String buttonText = styled.getString(R.styleable.ShowcaseView_sv_buttonText);
        String buttonStartText = styled.getString(R.styleable.ShowcaseView_sv_buttonText);
        if (TextUtils.isEmpty(buttonText)) {
            buttonText = getResources().getString(android.R.string.ok);
        }
        if (TextUtils.isEmpty(buttonStartText)) {
            buttonStartText = getResources().getString(android.R.string.ok);
        }
        boolean tintButton = styled.getBoolean(R.styleable.ShowcaseView_sv_tintButtonColor, true);

        int titleTextAppearance = styled.getResourceId(R.styleable.ShowcaseView_sv_titleTextAppearance,
                R.style.TextAppearance_ShowcaseView_Title);
        int detailTextAppearance = styled.getResourceId(R.styleable.ShowcaseView_sv_detailTextAppearance,
                R.style.TextAppearance_ShowcaseView_Detail);

        styled.recycle();

        showcaseDrawer.setShowcaseColour(showcaseColor);
        showcaseDrawer.setBackgroundColour(backgroundColor);
        tintButton(showcaseColor, tintButton);
        tintButtonStart(showcaseColor, tintButton);
        mEndButton.setText(buttonText);
        mStartButton.setText(buttonStartText);
        textDrawer.setTitleStyling(titleTextAppearance);
        textDrawer.setDetailStyling(detailTextAppearance);
        hasAlteredText = true;

        if (invalidate) {
            invalidate();
        }
    }

    private void tintButton(int showcaseColor, boolean tintButton) {
        if (tintButton) {
            mEndButton.getBackground().setColorFilter(showcaseColor, PorterDuff.Mode.MULTIPLY);
        } else {
            mEndButton.getBackground().setColorFilter(HOLO_BLUE, PorterDuff.Mode.MULTIPLY);
        }
    }

    private void tintButtonStart(int showcaseColor, boolean tintButton) {
        if (tintButton) {
            mStartButton.getBackground().setColorFilter(showcaseColor, PorterDuff.Mode.MULTIPLY);
        } else {
            mStartButton.getBackground().setColorFilter(HOLO_BLUE, PorterDuff.Mode.MULTIPLY);
        }
    }

    private OnClickListener hideOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            hide();
        }
    };

}
