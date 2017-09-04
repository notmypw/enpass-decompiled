package com.github.clans.fab;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.widget.TextView;
import net.sqlcipher.database.SQLiteDatabase;

public class Label extends TextView {
    private static final Xfermode PORTER_DUFF_CLEAR = new PorterDuffXfermode(Mode.CLEAR);
    private Drawable mBackgroundDrawable;
    private int mColorNormal;
    private int mColorPressed;
    private int mColorRipple;
    private int mCornerRadius;
    private FloatingActionButton mFab;
    GestureDetector mGestureDetector = new GestureDetector(getContext(), new SimpleOnGestureListener() {
        public boolean onDown(MotionEvent e) {
            Label.this.onActionDown();
            if (Label.this.mFab != null) {
                Label.this.mFab.onActionDown();
            }
            return super.onDown(e);
        }

        public boolean onSingleTapUp(MotionEvent e) {
            Label.this.onActionUp();
            if (Label.this.mFab != null) {
                Label.this.mFab.onActionUp();
            }
            return super.onSingleTapUp(e);
        }
    });
    private boolean mHandleVisibilityChanges = true;
    private Animation mHideAnimation;
    private int mRawHeight;
    private int mRawWidth;
    private int mShadowColor;
    private int mShadowRadius;
    private int mShadowXOffset;
    private int mShadowYOffset;
    private Animation mShowAnimation;
    private boolean mShowShadow = true;
    private boolean mUsingStyle;

    private class Shadow extends Drawable {
        private Paint mErase;
        private Paint mPaint;

        private Shadow() {
            this.mPaint = new Paint(1);
            this.mErase = new Paint(1);
            init();
        }

        private void init() {
            Label.this.setLayerType(1, null);
            this.mPaint.setStyle(Style.FILL);
            this.mPaint.setColor(Label.this.mColorNormal);
            this.mErase.setXfermode(Label.PORTER_DUFF_CLEAR);
            if (!Label.this.isInEditMode()) {
                this.mPaint.setShadowLayer((float) Label.this.mShadowRadius, (float) Label.this.mShadowXOffset, (float) Label.this.mShadowYOffset, Label.this.mShadowColor);
            }
        }

        public void draw(Canvas canvas) {
            RectF shadowRect = new RectF((float) (Label.this.mShadowRadius + Math.abs(Label.this.mShadowXOffset)), (float) (Label.this.mShadowRadius + Math.abs(Label.this.mShadowYOffset)), (float) Label.this.mRawWidth, (float) Label.this.mRawHeight);
            canvas.drawRoundRect(shadowRect, (float) Label.this.mCornerRadius, (float) Label.this.mCornerRadius, this.mPaint);
            canvas.drawRoundRect(shadowRect, (float) Label.this.mCornerRadius, (float) Label.this.mCornerRadius, this.mErase);
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter cf) {
        }

        public int getOpacity() {
            return 0;
        }
    }

    public Label(Context context) {
        super(context);
    }

    public Label(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Label(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight());
    }

    private int calculateMeasuredWidth() {
        if (this.mRawWidth == 0) {
            this.mRawWidth = getMeasuredWidth();
        }
        return getMeasuredWidth() + calculateShadowWidth();
    }

    private int calculateMeasuredHeight() {
        if (this.mRawHeight == 0) {
            this.mRawHeight = getMeasuredHeight();
        }
        return getMeasuredHeight() + calculateShadowHeight();
    }

    int calculateShadowWidth() {
        return this.mShowShadow ? this.mShadowRadius + Math.abs(this.mShadowXOffset) : 0;
    }

    int calculateShadowHeight() {
        return this.mShowShadow ? this.mShadowRadius + Math.abs(this.mShadowYOffset) : 0;
    }

    void updateBackground() {
        LayerDrawable layerDrawable;
        if (this.mShowShadow) {
            layerDrawable = new LayerDrawable(new Drawable[]{new Shadow(), createFillDrawable()});
            layerDrawable.setLayerInset(1, this.mShadowRadius + Math.abs(this.mShadowXOffset), this.mShadowRadius + Math.abs(this.mShadowYOffset), this.mShadowRadius + Math.abs(this.mShadowXOffset), this.mShadowRadius + Math.abs(this.mShadowYOffset));
        } else {
            layerDrawable = new LayerDrawable(new Drawable[]{createFillDrawable()});
        }
        setBackgroundCompat(layerDrawable);
    }

    @TargetApi(21)
    private Drawable createFillDrawable() {
        Drawable drawable = new StateListDrawable();
        drawable.addState(new int[]{16842919}, createRectDrawable(this.mColorPressed));
        drawable.addState(new int[0], createRectDrawable(this.mColorNormal));
        if (Util.hasLollipop()) {
            RippleDrawable ripple = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{this.mColorRipple}), drawable, null);
            setOutlineProvider(new ViewOutlineProvider() {
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            });
            setClipToOutline(true);
            this.mBackgroundDrawable = ripple;
            return ripple;
        }
        this.mBackgroundDrawable = drawable;
        return drawable;
    }

    private Drawable createRectDrawable(int color) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{(float) this.mCornerRadius, (float) this.mCornerRadius, (float) this.mCornerRadius, (float) this.mCornerRadius, (float) this.mCornerRadius, (float) this.mCornerRadius, (float) this.mCornerRadius, (float) this.mCornerRadius}, null, null));
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    private void setShadow(FloatingActionButton fab) {
        this.mShadowColor = fab.getShadowColor();
        this.mShadowRadius = fab.getShadowRadius();
        this.mShadowXOffset = fab.getShadowXOffset();
        this.mShadowYOffset = fab.getShadowYOffset();
        this.mShowShadow = fab.hasShadow();
    }

    @TargetApi(21)
    private void setBackgroundCompat(Drawable drawable) {
        if (Util.hasJellyBean()) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void playShowAnimation() {
        if (this.mShowAnimation != null) {
            this.mHideAnimation.cancel();
            startAnimation(this.mShowAnimation);
        }
    }

    private void playHideAnimation() {
        if (this.mHideAnimation != null) {
            this.mShowAnimation.cancel();
            startAnimation(this.mHideAnimation);
        }
    }

    @TargetApi(21)
    void onActionDown() {
        if (this.mUsingStyle) {
            this.mBackgroundDrawable = getBackground();
        }
        if (this.mBackgroundDrawable instanceof StateListDrawable) {
            this.mBackgroundDrawable.setState(new int[]{16842919});
        } else if (Util.hasLollipop() && (this.mBackgroundDrawable instanceof RippleDrawable)) {
            RippleDrawable ripple = this.mBackgroundDrawable;
            ripple.setState(new int[]{16842910, 16842919});
            ripple.setHotspot((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2));
            ripple.setVisible(true, true);
        }
        setPressed(true);
    }

    @TargetApi(21)
    void onActionUp() {
        if (this.mUsingStyle) {
            this.mBackgroundDrawable = getBackground();
        }
        if (this.mBackgroundDrawable instanceof StateListDrawable) {
            this.mBackgroundDrawable.setState(new int[0]);
        } else if (Util.hasLollipop() && (this.mBackgroundDrawable instanceof RippleDrawable)) {
            RippleDrawable ripple = this.mBackgroundDrawable;
            ripple.setState(new int[0]);
            ripple.setHotspot((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2));
            ripple.setVisible(true, true);
        }
        setPressed(false);
    }

    void setFab(FloatingActionButton fab) {
        this.mFab = fab;
        setShadow(fab);
    }

    void setShowShadow(boolean show) {
        this.mShowShadow = show;
    }

    void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
    }

    void setColors(int colorNormal, int colorPressed, int colorRipple) {
        this.mColorNormal = colorNormal;
        this.mColorPressed = colorPressed;
        this.mColorRipple = colorRipple;
    }

    void show(boolean animate) {
        if (animate) {
            playShowAnimation();
        }
        setVisibility(0);
    }

    void hide(boolean animate) {
        if (animate) {
            playHideAnimation();
        }
        setVisibility(4);
    }

    void setShowAnimation(Animation showAnimation) {
        this.mShowAnimation = showAnimation;
    }

    void setHideAnimation(Animation hideAnimation) {
        this.mHideAnimation = hideAnimation;
    }

    void setUsingStyle(boolean usingStyle) {
        this.mUsingStyle = usingStyle;
    }

    void setHandleVisibilityChanges(boolean handle) {
        this.mHandleVisibilityChanges = handle;
    }

    boolean isHandleVisibilityChanges() {
        return this.mHandleVisibilityChanges;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mFab == null || this.mFab.getOnClickListener() == null || !this.mFab.isEnabled()) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                onActionUp();
                this.mFab.onActionUp();
                break;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                onActionUp();
                this.mFab.onActionUp();
                break;
        }
        this.mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
