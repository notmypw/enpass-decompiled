package com.github.clans.fab;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import net.sqlcipher.database.SQLiteDatabase;

public class FloatingActionButton extends ImageButton {
    private static final int BAR_MAX_LENGTH = 270;
    private static final double BAR_SPIN_CYCLE_TIME = 500.0d;
    private static final long PAUSE_GROWING_TIME = 200;
    private static final Xfermode PORTER_DUFF_CLEAR = new PorterDuffXfermode(Mode.CLEAR);
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    private boolean mAnimateProgress;
    private Drawable mBackgroundDrawable;
    private Paint mBackgroundPaint;
    private float mBarExtraLength;
    private boolean mBarGrowingFromFront;
    private int mBarLength;
    private boolean mButtonPositionSaved;
    private OnClickListener mClickListener;
    private int mColorDisabled;
    private int mColorNormal;
    private int mColorPressed;
    private int mColorRipple;
    private float mCurrentProgress;
    int mFabSize;
    GestureDetector mGestureDetector;
    private Animation mHideAnimation;
    private Drawable mIcon;
    private int mIconSize;
    private String mLabelText;
    private long mLastTimeAnimated;
    private float mOriginalX;
    private float mOriginalY;
    private long mPausedTimeWithoutGrowing;
    private int mProgress;
    private int mProgressBackgroundColor;
    private boolean mProgressBarEnabled;
    private RectF mProgressCircleBounds;
    private int mProgressColor;
    private boolean mProgressIndeterminate;
    private int mProgressMax;
    private Paint mProgressPaint;
    private int mProgressWidth;
    int mShadowColor;
    int mShadowRadius;
    int mShadowXOffset;
    int mShadowYOffset;
    private boolean mShouldProgressIndeterminate;
    private boolean mShouldSetProgress;
    private boolean mShouldUpdateButtonPosition;
    private Animation mShowAnimation;
    private boolean mShowProgressBackground;
    boolean mShowShadow;
    private float mSpinSpeed;
    private float mTargetProgress;
    private double mTimeStartGrowing;
    private boolean mUsingElevation;
    private boolean mUsingElevationCompat;

    private class CircleDrawable extends ShapeDrawable {
        private int circleInsetHorizontal;
        private int circleInsetVertical;

        private CircleDrawable() {
        }

        private CircleDrawable(FloatingActionButton floatingActionButton, Shape s) {
            int i = 0;
            FloatingActionButton.this = floatingActionButton;
            super(s);
            this.circleInsetHorizontal = floatingActionButton.hasShadow() ? floatingActionButton.mShadowRadius + Math.abs(floatingActionButton.mShadowXOffset) : 0;
            if (floatingActionButton.hasShadow()) {
                i = Math.abs(floatingActionButton.mShadowYOffset) + floatingActionButton.mShadowRadius;
            }
            this.circleInsetVertical = i;
            if (floatingActionButton.mProgressBarEnabled) {
                this.circleInsetHorizontal += floatingActionButton.mProgressWidth;
                this.circleInsetVertical += floatingActionButton.mProgressWidth;
            }
        }

        public void draw(Canvas canvas) {
            setBounds(this.circleInsetHorizontal, this.circleInsetVertical, FloatingActionButton.this.calculateMeasuredWidth() - this.circleInsetHorizontal, FloatingActionButton.this.calculateMeasuredHeight() - this.circleInsetVertical);
            super.draw(canvas);
        }
    }

    static class ProgressSavedState extends BaseSavedState {
        public static final Creator<ProgressSavedState> CREATOR = new Creator<ProgressSavedState>() {
            public ProgressSavedState createFromParcel(Parcel in) {
                return new ProgressSavedState(in);
            }

            public ProgressSavedState[] newArray(int size) {
                return new ProgressSavedState[size];
            }
        };
        boolean mAnimateProgress;
        float mCurrentProgress;
        int mProgress;
        int mProgressBackgroundColor;
        boolean mProgressBarEnabled;
        boolean mProgressBarVisibilityChanged;
        int mProgressColor;
        boolean mProgressIndeterminate;
        int mProgressWidth;
        boolean mShouldProgressIndeterminate;
        boolean mShouldSetProgress;
        boolean mShowProgressBackground;
        float mSpinSpeed;
        float mTargetProgress;

        ProgressSavedState(Parcelable superState) {
            super(superState);
        }

        private ProgressSavedState(Parcel in) {
            boolean z;
            boolean z2 = true;
            super(in);
            this.mCurrentProgress = in.readFloat();
            this.mTargetProgress = in.readFloat();
            this.mProgressBarEnabled = in.readInt() != 0;
            this.mSpinSpeed = in.readFloat();
            this.mProgress = in.readInt();
            this.mProgressWidth = in.readInt();
            this.mProgressColor = in.readInt();
            this.mProgressBackgroundColor = in.readInt();
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.mProgressBarVisibilityChanged = z;
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.mProgressIndeterminate = z;
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.mShouldProgressIndeterminate = z;
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.mShouldSetProgress = z;
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.mAnimateProgress = z;
            if (in.readInt() == 0) {
                z2 = false;
            }
            this.mShowProgressBackground = z2;
        }

        public void writeToParcel(Parcel out, int flags) {
            int i = FloatingActionButton.SIZE_MINI;
            super.writeToParcel(out, flags);
            out.writeFloat(this.mCurrentProgress);
            out.writeFloat(this.mTargetProgress);
            out.writeInt(this.mProgressBarEnabled ? FloatingActionButton.SIZE_MINI : 0);
            out.writeFloat(this.mSpinSpeed);
            out.writeInt(this.mProgress);
            out.writeInt(this.mProgressWidth);
            out.writeInt(this.mProgressColor);
            out.writeInt(this.mProgressBackgroundColor);
            out.writeInt(this.mProgressBarVisibilityChanged ? FloatingActionButton.SIZE_MINI : 0);
            out.writeInt(this.mProgressIndeterminate ? FloatingActionButton.SIZE_MINI : 0);
            out.writeInt(this.mShouldProgressIndeterminate ? FloatingActionButton.SIZE_MINI : 0);
            out.writeInt(this.mShouldSetProgress ? FloatingActionButton.SIZE_MINI : 0);
            out.writeInt(this.mAnimateProgress ? FloatingActionButton.SIZE_MINI : 0);
            if (!this.mShowProgressBackground) {
                i = 0;
            }
            out.writeInt(i);
        }
    }

    private class Shadow extends Drawable {
        private Paint mErase;
        private Paint mPaint;
        private float mRadius;

        private Shadow() {
            this.mPaint = new Paint(FloatingActionButton.SIZE_MINI);
            this.mErase = new Paint(FloatingActionButton.SIZE_MINI);
            init();
        }

        private void init() {
            FloatingActionButton.this.setLayerType(FloatingActionButton.SIZE_MINI, null);
            this.mPaint.setStyle(Style.FILL);
            this.mPaint.setColor(FloatingActionButton.this.mColorNormal);
            this.mErase.setXfermode(FloatingActionButton.PORTER_DUFF_CLEAR);
            if (!FloatingActionButton.this.isInEditMode()) {
                this.mPaint.setShadowLayer((float) FloatingActionButton.this.mShadowRadius, (float) FloatingActionButton.this.mShadowXOffset, (float) FloatingActionButton.this.mShadowYOffset, FloatingActionButton.this.mShadowColor);
            }
            this.mRadius = (float) (FloatingActionButton.this.getCircleSize() / 2);
            if (FloatingActionButton.this.mProgressBarEnabled && FloatingActionButton.this.mShowProgressBackground) {
                this.mRadius += (float) FloatingActionButton.this.mProgressWidth;
            }
        }

        public void draw(Canvas canvas) {
            canvas.drawCircle(FloatingActionButton.this.calculateCenterX(), FloatingActionButton.this.calculateCenterY(), this.mRadius, this.mPaint);
            canvas.drawCircle(FloatingActionButton.this.calculateCenterX(), FloatingActionButton.this.calculateCenterY(), this.mRadius, this.mErase);
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter cf) {
        }

        public int getOpacity() {
            return 0;
        }
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShadowRadius = Util.dpToPx(getContext(), 4.0f);
        this.mShadowXOffset = Util.dpToPx(getContext(), 1.0f);
        this.mShadowYOffset = Util.dpToPx(getContext(), 3.0f);
        this.mIconSize = Util.dpToPx(getContext(), 24.0f);
        this.mProgressWidth = Util.dpToPx(getContext(), 6.0f);
        this.mOriginalX = -1.0f;
        this.mOriginalY = -1.0f;
        this.mProgressCircleBounds = new RectF();
        this.mBackgroundPaint = new Paint(SIZE_MINI);
        this.mProgressPaint = new Paint(SIZE_MINI);
        this.mSpinSpeed = 195.0f;
        this.mPausedTimeWithoutGrowing = 0;
        this.mBarGrowingFromFront = true;
        this.mBarLength = 16;
        this.mProgressMax = 100;
        this.mGestureDetector = new GestureDetector(getContext(), new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                Label label = (Label) FloatingActionButton.this.getTag(R.id.fab_label);
                if (label != null) {
                    label.onActionDown();
                }
                FloatingActionButton.this.onActionDown();
                return super.onDown(e);
            }

            public boolean onSingleTapUp(MotionEvent e) {
                Label label = (Label) FloatingActionButton.this.getTag(R.id.fab_label);
                if (label != null) {
                    label.onActionUp();
                }
                FloatingActionButton.this.onActionUp();
                return super.onSingleTapUp(e);
            }
        });
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mShadowRadius = Util.dpToPx(getContext(), 4.0f);
        this.mShadowXOffset = Util.dpToPx(getContext(), 1.0f);
        this.mShadowYOffset = Util.dpToPx(getContext(), 3.0f);
        this.mIconSize = Util.dpToPx(getContext(), 24.0f);
        this.mProgressWidth = Util.dpToPx(getContext(), 6.0f);
        this.mOriginalX = -1.0f;
        this.mOriginalY = -1.0f;
        this.mProgressCircleBounds = new RectF();
        this.mBackgroundPaint = new Paint(SIZE_MINI);
        this.mProgressPaint = new Paint(SIZE_MINI);
        this.mSpinSpeed = 195.0f;
        this.mPausedTimeWithoutGrowing = 0;
        this.mBarGrowingFromFront = true;
        this.mBarLength = 16;
        this.mProgressMax = 100;
        this.mGestureDetector = new GestureDetector(getContext(), /* anonymous class already generated */);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, defStyleAttr, 0);
        this.mColorNormal = attr.getColor(R.styleable.FloatingActionButton_fab_colorNormal, -2473162);
        this.mColorPressed = attr.getColor(R.styleable.FloatingActionButton_fab_colorPressed, -1617853);
        this.mColorDisabled = attr.getColor(R.styleable.FloatingActionButton_fab_colorDisabled, -5592406);
        this.mColorRipple = attr.getColor(R.styleable.FloatingActionButton_fab_colorRipple, -1711276033);
        this.mShowShadow = attr.getBoolean(R.styleable.FloatingActionButton_fab_showShadow, true);
        this.mShadowColor = attr.getColor(R.styleable.FloatingActionButton_fab_shadowColor, 1711276032);
        this.mShadowRadius = attr.getDimensionPixelSize(R.styleable.FloatingActionButton_fab_shadowRadius, this.mShadowRadius);
        this.mShadowXOffset = attr.getDimensionPixelSize(R.styleable.FloatingActionButton_fab_shadowXOffset, this.mShadowXOffset);
        this.mShadowYOffset = attr.getDimensionPixelSize(R.styleable.FloatingActionButton_fab_shadowYOffset, this.mShadowYOffset);
        this.mFabSize = attr.getInt(R.styleable.FloatingActionButton_fab_size, 0);
        this.mLabelText = attr.getString(R.styleable.FloatingActionButton_fab_label);
        this.mShouldProgressIndeterminate = attr.getBoolean(R.styleable.FloatingActionButton_fab_progress_indeterminate, false);
        this.mProgressColor = attr.getColor(R.styleable.FloatingActionButton_fab_progress_color, -16738680);
        this.mProgressBackgroundColor = attr.getColor(R.styleable.FloatingActionButton_fab_progress_backgroundColor, 1291845632);
        this.mProgressMax = attr.getInt(R.styleable.FloatingActionButton_fab_progress_max, this.mProgressMax);
        this.mShowProgressBackground = attr.getBoolean(R.styleable.FloatingActionButton_fab_progress_showBackground, true);
        if (attr.hasValue(R.styleable.FloatingActionButton_fab_progress)) {
            this.mProgress = attr.getInt(R.styleable.FloatingActionButton_fab_progress, 0);
            this.mShouldSetProgress = true;
        }
        if (attr.hasValue(R.styleable.FloatingActionButton_fab_elevationCompat)) {
            float elevation = (float) attr.getDimensionPixelOffset(R.styleable.FloatingActionButton_fab_elevationCompat, 0);
            if (isInEditMode()) {
                setElevation(elevation);
            } else {
                setElevationCompat(elevation);
            }
        }
        initShowAnimation(attr);
        initHideAnimation(attr);
        attr.recycle();
        if (isInEditMode()) {
            if (this.mShouldProgressIndeterminate) {
                setIndeterminate(true);
            } else if (this.mShouldSetProgress) {
                saveButtonOriginalPosition();
                setProgress(this.mProgress, false);
            }
        }
        setClickable(true);
    }

    private void initShowAnimation(TypedArray attr) {
        this.mShowAnimation = AnimationUtils.loadAnimation(getContext(), attr.getResourceId(R.styleable.FloatingActionButton_fab_showAnimation, R.anim.fab_scale_up));
    }

    private void initHideAnimation(TypedArray attr) {
        this.mHideAnimation = AnimationUtils.loadAnimation(getContext(), attr.getResourceId(R.styleable.FloatingActionButton_fab_hideAnimation, R.anim.fab_scale_down));
    }

    private int getCircleSize() {
        return getResources().getDimensionPixelSize(this.mFabSize == 0 ? R.dimen.fab_size_normal : R.dimen.fab_size_mini);
    }

    private int calculateMeasuredWidth() {
        int width = getCircleSize() + calculateShadowWidth();
        if (this.mProgressBarEnabled) {
            return width + (this.mProgressWidth * 2);
        }
        return width;
    }

    private int calculateMeasuredHeight() {
        int height = getCircleSize() + calculateShadowHeight();
        if (this.mProgressBarEnabled) {
            return height + (this.mProgressWidth * 2);
        }
        return height;
    }

    int calculateShadowWidth() {
        return hasShadow() ? getShadowX() * 2 : 0;
    }

    int calculateShadowHeight() {
        return hasShadow() ? getShadowY() * 2 : 0;
    }

    private int getShadowX() {
        return this.mShadowRadius + Math.abs(this.mShadowXOffset);
    }

    private int getShadowY() {
        return this.mShadowRadius + Math.abs(this.mShadowYOffset);
    }

    private float calculateCenterX() {
        return (float) (getMeasuredWidth() / 2);
    }

    private float calculateCenterY() {
        return (float) (getMeasuredHeight() / 2);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mProgressBarEnabled) {
            if (this.mShowProgressBackground) {
                canvas.drawArc(this.mProgressCircleBounds, 360.0f, 360.0f, false, this.mBackgroundPaint);
            }
            boolean shouldInvalidate = false;
            float deltaNormalized;
            if (this.mProgressIndeterminate) {
                shouldInvalidate = true;
                long deltaTime = SystemClock.uptimeMillis() - this.mLastTimeAnimated;
                deltaNormalized = (((float) deltaTime) * this.mSpinSpeed) / 1000.0f;
                updateProgressLength(deltaTime);
                this.mCurrentProgress += deltaNormalized;
                if (this.mCurrentProgress > 360.0f) {
                    this.mCurrentProgress -= 360.0f;
                }
                this.mLastTimeAnimated = SystemClock.uptimeMillis();
                float from = this.mCurrentProgress - 90.0f;
                float to = ((float) this.mBarLength) + this.mBarExtraLength;
                if (isInEditMode()) {
                    from = 0.0f;
                    to = 135.0f;
                }
                canvas.drawArc(this.mProgressCircleBounds, from, to, false, this.mProgressPaint);
            } else {
                if (this.mCurrentProgress != this.mTargetProgress) {
                    shouldInvalidate = true;
                    deltaNormalized = (((float) (SystemClock.uptimeMillis() - this.mLastTimeAnimated)) / 1000.0f) * this.mSpinSpeed;
                    if (this.mCurrentProgress > this.mTargetProgress) {
                        this.mCurrentProgress = Math.max(this.mCurrentProgress - deltaNormalized, this.mTargetProgress);
                    } else {
                        this.mCurrentProgress = Math.min(this.mCurrentProgress + deltaNormalized, this.mTargetProgress);
                    }
                    this.mLastTimeAnimated = SystemClock.uptimeMillis();
                }
                canvas.drawArc(this.mProgressCircleBounds, -90.0f, this.mCurrentProgress, false, this.mProgressPaint);
            }
            if (shouldInvalidate) {
                invalidate();
            }
        }
    }

    private void updateProgressLength(long deltaTimeInMillis) {
        if (this.mPausedTimeWithoutGrowing >= PAUSE_GROWING_TIME) {
            this.mTimeStartGrowing += (double) deltaTimeInMillis;
            if (this.mTimeStartGrowing > BAR_SPIN_CYCLE_TIME) {
                this.mTimeStartGrowing -= BAR_SPIN_CYCLE_TIME;
                this.mPausedTimeWithoutGrowing = 0;
                this.mBarGrowingFromFront = !this.mBarGrowingFromFront;
            }
            float distance = (((float) Math.cos(((this.mTimeStartGrowing / BAR_SPIN_CYCLE_TIME) + 1.0d) * 3.141592653589793d)) / 2.0f) + 0.5f;
            float length = (float) (270 - this.mBarLength);
            if (this.mBarGrowingFromFront) {
                this.mBarExtraLength = distance * length;
                return;
            }
            float newLength = length * (1.0f - distance);
            this.mCurrentProgress += this.mBarExtraLength - newLength;
            this.mBarExtraLength = newLength;
            return;
        }
        this.mPausedTimeWithoutGrowing += deltaTimeInMillis;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        saveButtonOriginalPosition();
        if (this.mShouldProgressIndeterminate) {
            setIndeterminate(true);
            this.mShouldProgressIndeterminate = false;
        } else if (this.mShouldSetProgress) {
            setProgress(this.mProgress, this.mAnimateProgress);
            this.mShouldSetProgress = false;
        } else if (this.mShouldUpdateButtonPosition) {
            updateButtonPosition();
            this.mShouldUpdateButtonPosition = false;
        }
        super.onSizeChanged(w, h, oldw, oldh);
        setupProgressBounds();
        setupProgressBarPaints();
        updateBackground();
    }

    @TargetApi(21)
    public void setLayoutParams(LayoutParams params) {
        if ((params instanceof MarginLayoutParams) && this.mUsingElevationCompat) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) params;
            marginLayoutParams.leftMargin += getShadowX();
            marginLayoutParams = (MarginLayoutParams) params;
            marginLayoutParams.topMargin += getShadowY();
            marginLayoutParams = (MarginLayoutParams) params;
            marginLayoutParams.rightMargin += getShadowX();
            marginLayoutParams = (MarginLayoutParams) params;
            marginLayoutParams.bottomMargin += getShadowY();
        }
        super.setLayoutParams(params);
    }

    void updateBackground() {
        LayerDrawable layerDrawable;
        int circleInsetHorizontal;
        int circleInsetVertical;
        if (hasShadow()) {
            layerDrawable = new LayerDrawable(new Drawable[]{new Shadow(), createFillDrawable(), getIconDrawable()});
        } else {
            layerDrawable = new LayerDrawable(new Drawable[]{createFillDrawable(), getIconDrawable()});
        }
        int iconSize = -1;
        if (getIconDrawable() != null) {
            iconSize = Math.max(getIconDrawable().getIntrinsicWidth(), getIconDrawable().getIntrinsicHeight());
        }
        int circleSize = getCircleSize();
        if (iconSize <= 0) {
            iconSize = this.mIconSize;
        }
        int iconOffset = (circleSize - iconSize) / 2;
        if (hasShadow()) {
            circleInsetHorizontal = this.mShadowRadius + Math.abs(this.mShadowXOffset);
        } else {
            circleInsetHorizontal = 0;
        }
        if (hasShadow()) {
            circleInsetVertical = this.mShadowRadius + Math.abs(this.mShadowYOffset);
        } else {
            circleInsetVertical = 0;
        }
        if (this.mProgressBarEnabled) {
            circleInsetHorizontal += this.mProgressWidth;
            circleInsetVertical += this.mProgressWidth;
        }
        layerDrawable.setLayerInset(hasShadow() ? 2 : SIZE_MINI, circleInsetHorizontal + iconOffset, circleInsetVertical + iconOffset, circleInsetHorizontal + iconOffset, circleInsetVertical + iconOffset);
        setBackgroundCompat(layerDrawable);
    }

    protected Drawable getIconDrawable() {
        if (this.mIcon != null) {
            return this.mIcon;
        }
        return new ColorDrawable(0);
    }

    @TargetApi(21)
    private Drawable createFillDrawable() {
        Drawable drawable = new StateListDrawable();
        int[] iArr = new int[SIZE_MINI];
        iArr[0] = -16842910;
        drawable.addState(iArr, createCircleDrawable(this.mColorDisabled));
        iArr = new int[SIZE_MINI];
        iArr[0] = 16842919;
        drawable.addState(iArr, createCircleDrawable(this.mColorPressed));
        drawable.addState(new int[0], createCircleDrawable(this.mColorNormal));
        if (Util.hasLollipop()) {
            int[][] iArr2 = new int[SIZE_MINI][];
            iArr2[0] = new int[0];
            int[] iArr3 = new int[SIZE_MINI];
            iArr3[0] = this.mColorRipple;
            RippleDrawable ripple = new RippleDrawable(new ColorStateList(iArr2, iArr3), drawable, null);
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

    private Drawable createCircleDrawable(int color) {
        CircleDrawable shapeDrawable = new CircleDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    @TargetApi(16)
    private void setBackgroundCompat(Drawable drawable) {
        if (Util.hasJellyBean()) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void saveButtonOriginalPosition() {
        if (!this.mButtonPositionSaved) {
            if (this.mOriginalX == -1.0f) {
                this.mOriginalX = getX();
            }
            if (this.mOriginalY == -1.0f) {
                this.mOriginalY = getY();
            }
            this.mButtonPositionSaved = true;
        }
    }

    private void updateButtonPosition() {
        float x;
        float y;
        if (this.mProgressBarEnabled) {
            x = this.mOriginalX > getX() ? getX() + ((float) this.mProgressWidth) : getX() - ((float) this.mProgressWidth);
            y = this.mOriginalY > getY() ? getY() + ((float) this.mProgressWidth) : getY() - ((float) this.mProgressWidth);
        } else {
            x = this.mOriginalX;
            y = this.mOriginalY;
        }
        setX(x);
        setY(y);
    }

    private void setupProgressBarPaints() {
        this.mBackgroundPaint.setColor(this.mProgressBackgroundColor);
        this.mBackgroundPaint.setStyle(Style.STROKE);
        this.mBackgroundPaint.setStrokeWidth((float) this.mProgressWidth);
        this.mProgressPaint.setColor(this.mProgressColor);
        this.mProgressPaint.setStyle(Style.STROKE);
        this.mProgressPaint.setStrokeWidth((float) this.mProgressWidth);
    }

    private void setupProgressBounds() {
        int circleInsetHorizontal;
        int circleInsetVertical;
        if (hasShadow()) {
            circleInsetHorizontal = getShadowX();
        } else {
            circleInsetHorizontal = 0;
        }
        if (hasShadow()) {
            circleInsetVertical = getShadowY();
        } else {
            circleInsetVertical = 0;
        }
        this.mProgressCircleBounds = new RectF((float) ((this.mProgressWidth / 2) + circleInsetHorizontal), (float) ((this.mProgressWidth / 2) + circleInsetVertical), (float) ((calculateMeasuredWidth() - circleInsetHorizontal) - (this.mProgressWidth / 2)), (float) ((calculateMeasuredHeight() - circleInsetVertical) - (this.mProgressWidth / 2)));
    }

    Animation getShowAnimation() {
        return this.mShowAnimation;
    }

    Animation getHideAnimation() {
        return this.mHideAnimation;
    }

    void playShowAnimation() {
        this.mHideAnimation.cancel();
        startAnimation(this.mShowAnimation);
    }

    void playHideAnimation() {
        this.mShowAnimation.cancel();
        startAnimation(this.mHideAnimation);
    }

    OnClickListener getOnClickListener() {
        return this.mClickListener;
    }

    Label getLabelView() {
        return (Label) getTag(R.id.fab_label);
    }

    void setColors(int colorNormal, int colorPressed, int colorRipple) {
        this.mColorNormal = colorNormal;
        this.mColorPressed = colorPressed;
        this.mColorRipple = colorRipple;
    }

    @TargetApi(21)
    void onActionDown() {
        if (this.mBackgroundDrawable instanceof StateListDrawable) {
            this.mBackgroundDrawable.setState(new int[]{16842910, 16842919});
        } else if (Util.hasLollipop()) {
            RippleDrawable ripple = this.mBackgroundDrawable;
            ripple.setState(new int[]{16842910, 16842919});
            ripple.setHotspot(calculateCenterX(), calculateCenterY());
            ripple.setVisible(true, true);
        }
    }

    @TargetApi(21)
    void onActionUp() {
        int[] iArr;
        if (this.mBackgroundDrawable instanceof StateListDrawable) {
            StateListDrawable drawable = this.mBackgroundDrawable;
            iArr = new int[SIZE_MINI];
            iArr[0] = 16842910;
            drawable.setState(iArr);
        } else if (Util.hasLollipop()) {
            RippleDrawable ripple = this.mBackgroundDrawable;
            iArr = new int[SIZE_MINI];
            iArr[0] = 16842910;
            ripple.setState(iArr);
            ripple.setHotspot(calculateCenterX(), calculateCenterY());
            ripple.setVisible(true, true);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mClickListener != null && isEnabled()) {
            Label label = (Label) getTag(R.id.fab_label);
            if (label == null) {
                return super.onTouchEvent(event);
            }
            switch (event.getAction()) {
                case SIZE_MINI /*1*/:
                    if (label != null) {
                        label.onActionUp();
                    }
                    onActionUp();
                    break;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    if (label != null) {
                        label.onActionUp();
                    }
                    onActionUp();
                    break;
            }
            this.mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    public Parcelable onSaveInstanceState() {
        ProgressSavedState ss = new ProgressSavedState(super.onSaveInstanceState());
        ss.mCurrentProgress = this.mCurrentProgress;
        ss.mTargetProgress = this.mTargetProgress;
        ss.mSpinSpeed = this.mSpinSpeed;
        ss.mProgressWidth = this.mProgressWidth;
        ss.mProgressColor = this.mProgressColor;
        ss.mProgressBackgroundColor = this.mProgressBackgroundColor;
        ss.mShouldProgressIndeterminate = this.mProgressIndeterminate;
        boolean z = this.mProgressBarEnabled && this.mProgress > 0 && !this.mProgressIndeterminate;
        ss.mShouldSetProgress = z;
        ss.mProgress = this.mProgress;
        ss.mAnimateProgress = this.mAnimateProgress;
        ss.mShowProgressBackground = this.mShowProgressBackground;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof ProgressSavedState) {
            ProgressSavedState ss = (ProgressSavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            this.mCurrentProgress = ss.mCurrentProgress;
            this.mTargetProgress = ss.mTargetProgress;
            this.mSpinSpeed = ss.mSpinSpeed;
            this.mProgressWidth = ss.mProgressWidth;
            this.mProgressColor = ss.mProgressColor;
            this.mProgressBackgroundColor = ss.mProgressBackgroundColor;
            this.mShouldProgressIndeterminate = ss.mShouldProgressIndeterminate;
            this.mShouldSetProgress = ss.mShouldSetProgress;
            this.mProgress = ss.mProgress;
            this.mAnimateProgress = ss.mAnimateProgress;
            this.mShowProgressBackground = ss.mShowProgressBackground;
            this.mLastTimeAnimated = SystemClock.uptimeMillis();
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setImageDrawable(Drawable drawable) {
        if (this.mIcon != drawable) {
            this.mIcon = drawable;
            updateBackground();
        }
    }

    public void setImageResource(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        if (this.mIcon != drawable) {
            this.mIcon = drawable;
            updateBackground();
        }
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.mClickListener = l;
        View label = (View) getTag(R.id.fab_label);
        if (label != null) {
            label.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (FloatingActionButton.this.mClickListener != null) {
                        FloatingActionButton.this.mClickListener.onClick(FloatingActionButton.this);
                    }
                }
            });
        }
    }

    public void setButtonSize(int size) {
        if (size != 0 && size != SIZE_MINI) {
            throw new IllegalArgumentException("Use @FabSize constants only!");
        } else if (this.mFabSize != size) {
            this.mFabSize = size;
            updateBackground();
        }
    }

    public int getButtonSize() {
        return this.mFabSize;
    }

    public void setColorNormal(int color) {
        if (this.mColorNormal != color) {
            this.mColorNormal = color;
            updateBackground();
        }
    }

    public void setColorNormalResId(int colorResId) {
        setColorNormal(getResources().getColor(colorResId));
    }

    public int getColorNormal() {
        return this.mColorNormal;
    }

    public void setColorPressed(int color) {
        if (color != this.mColorPressed) {
            this.mColorPressed = color;
            updateBackground();
        }
    }

    public void setColorPressedResId(int colorResId) {
        setColorPressed(getResources().getColor(colorResId));
    }

    public int getColorPressed() {
        return this.mColorPressed;
    }

    public void setColorRipple(int color) {
        if (color != this.mColorRipple) {
            this.mColorRipple = color;
            updateBackground();
        }
    }

    public void setColorRippleResId(int colorResId) {
        setColorRipple(getResources().getColor(colorResId));
    }

    public int getColorRipple() {
        return this.mColorRipple;
    }

    public void setColorDisabled(int color) {
        if (color != this.mColorDisabled) {
            this.mColorDisabled = color;
            updateBackground();
        }
    }

    public void setColorDisabledResId(int colorResId) {
        setColorDisabled(getResources().getColor(colorResId));
    }

    public int getColorDisabled() {
        return this.mColorDisabled;
    }

    public void setShowShadow(boolean show) {
        if (this.mShowShadow != show) {
            this.mShowShadow = show;
            updateBackground();
        }
    }

    public boolean hasShadow() {
        return !this.mUsingElevation && this.mShowShadow;
    }

    public void setShadowRadius(int dimenResId) {
        int shadowRadius = getResources().getDimensionPixelSize(dimenResId);
        if (this.mShadowRadius != shadowRadius) {
            this.mShadowRadius = shadowRadius;
            requestLayout();
            updateBackground();
        }
    }

    public void setShadowRadius(float shadowRadiusDp) {
        this.mShadowRadius = Util.dpToPx(getContext(), shadowRadiusDp);
        requestLayout();
        updateBackground();
    }

    public int getShadowRadius() {
        return this.mShadowRadius;
    }

    public void setShadowXOffset(int dimenResId) {
        int shadowXOffset = getResources().getDimensionPixelSize(dimenResId);
        if (this.mShadowXOffset != shadowXOffset) {
            this.mShadowXOffset = shadowXOffset;
            requestLayout();
            updateBackground();
        }
    }

    public void setShadowXOffset(float shadowXOffsetDp) {
        this.mShadowXOffset = Util.dpToPx(getContext(), shadowXOffsetDp);
        requestLayout();
        updateBackground();
    }

    public int getShadowXOffset() {
        return this.mShadowXOffset;
    }

    public void setShadowYOffset(int dimenResId) {
        int shadowYOffset = getResources().getDimensionPixelSize(dimenResId);
        if (this.mShadowYOffset != shadowYOffset) {
            this.mShadowYOffset = shadowYOffset;
            requestLayout();
            updateBackground();
        }
    }

    public void setShadowYOffset(float shadowYOffsetDp) {
        this.mShadowYOffset = Util.dpToPx(getContext(), shadowYOffsetDp);
        requestLayout();
        updateBackground();
    }

    public int getShadowYOffset() {
        return this.mShadowYOffset;
    }

    public void setShadowColorResource(int colorResId) {
        int shadowColor = getResources().getColor(colorResId);
        if (this.mShadowColor != shadowColor) {
            this.mShadowColor = shadowColor;
            updateBackground();
        }
    }

    public void setShadowColor(int color) {
        if (this.mShadowColor != color) {
            this.mShadowColor = color;
            updateBackground();
        }
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public boolean isHidden() {
        return getVisibility() == 4;
    }

    public void show(boolean animate) {
        if (isHidden()) {
            if (animate) {
                playShowAnimation();
            }
            super.setVisibility(0);
        }
    }

    public void hide(boolean animate) {
        if (!isHidden()) {
            if (animate) {
                playHideAnimation();
            }
            super.setVisibility(4);
        }
    }

    public void toggle(boolean animate) {
        if (isHidden()) {
            show(animate);
        } else {
            hide(animate);
        }
    }

    public void setLabelText(String text) {
        this.mLabelText = text;
        TextView labelView = getLabelView();
        if (labelView != null) {
            labelView.setText(text);
        }
    }

    public String getLabelText() {
        return this.mLabelText;
    }

    public void setShowAnimation(Animation showAnimation) {
        this.mShowAnimation = showAnimation;
    }

    public void setHideAnimation(Animation hideAnimation) {
        this.mHideAnimation = hideAnimation;
    }

    public void setLabelVisibility(int visibility) {
        Label labelView = getLabelView();
        if (labelView != null) {
            labelView.setVisibility(visibility);
            labelView.setHandleVisibilityChanges(visibility == 0);
        }
    }

    public int getLabelVisibility() {
        TextView labelView = getLabelView();
        if (labelView != null) {
            return labelView.getVisibility();
        }
        return -1;
    }

    public void setElevation(float elevation) {
        if (Util.hasLollipop() && elevation > 0.0f) {
            super.setElevation(elevation);
            if (!isInEditMode()) {
                this.mUsingElevation = true;
                this.mShowShadow = false;
            }
            updateBackground();
        }
    }

    @TargetApi(21)
    public void setElevationCompat(float elevation) {
        this.mShadowColor = 637534208;
        this.mShadowRadius = Math.round(elevation / 2.0f);
        this.mShadowXOffset = 0;
        this.mShadowYOffset = Math.round(this.mFabSize == 0 ? elevation : elevation / 2.0f);
        if (Util.hasLollipop()) {
            super.setElevation(elevation);
            this.mUsingElevationCompat = true;
            this.mShowShadow = false;
            updateBackground();
            LayoutParams layoutParams = getLayoutParams();
            if (layoutParams != null) {
                setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        this.mShowShadow = true;
        updateBackground();
    }

    public synchronized void setIndeterminate(boolean indeterminate) {
        if (!indeterminate) {
            this.mCurrentProgress = 0.0f;
        }
        this.mProgressBarEnabled = indeterminate;
        this.mShouldUpdateButtonPosition = true;
        this.mProgressIndeterminate = indeterminate;
        this.mLastTimeAnimated = SystemClock.uptimeMillis();
        setupProgressBounds();
        saveButtonOriginalPosition();
        updateBackground();
    }

    public synchronized void setMax(int max) {
        this.mProgressMax = max;
    }

    public synchronized int getMax() {
        return this.mProgressMax;
    }

    public synchronized void setProgress(int progress, boolean animate) {
        if (!this.mProgressIndeterminate) {
            this.mProgress = progress;
            this.mAnimateProgress = animate;
            if (this.mButtonPositionSaved) {
                this.mProgressBarEnabled = true;
                this.mShouldUpdateButtonPosition = true;
                setupProgressBounds();
                saveButtonOriginalPosition();
                updateBackground();
                if (progress < 0) {
                    progress = 0;
                } else if (progress > this.mProgressMax) {
                    progress = this.mProgressMax;
                }
                if (((float) progress) != this.mTargetProgress) {
                    this.mTargetProgress = this.mProgressMax > 0 ? (((float) progress) / ((float) this.mProgressMax)) * 360.0f : 0.0f;
                    this.mLastTimeAnimated = SystemClock.uptimeMillis();
                    if (!animate) {
                        this.mCurrentProgress = this.mTargetProgress;
                    }
                    invalidate();
                }
            } else {
                this.mShouldSetProgress = true;
            }
        }
    }

    public synchronized int getProgress() {
        return this.mProgressIndeterminate ? 0 : this.mProgress;
    }

    public synchronized void hideProgress() {
        this.mProgressBarEnabled = false;
        this.mShouldUpdateButtonPosition = true;
        updateBackground();
    }

    public synchronized void setShowProgressBackground(boolean show) {
        this.mShowProgressBackground = show;
    }

    public synchronized boolean isProgressBackgroundShown() {
        return this.mShowProgressBackground;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        Label label = (Label) getTag(R.id.fab_label);
        if (label != null) {
            label.setEnabled(enabled);
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        Label label = (Label) getTag(R.id.fab_label);
        if (label != null) {
            label.setVisibility(visibility);
        }
    }

    public void hideButtonInMenu(boolean animate) {
        if (!isHidden() && getVisibility() != 8) {
            hide(animate);
            Label label = getLabelView();
            if (label != null) {
                label.hide(animate);
            }
            getHideAnimation().setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    FloatingActionButton.this.setVisibility(8);
                    FloatingActionButton.this.getHideAnimation().setAnimationListener(null);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    public void showButtonInMenu(boolean animate) {
        if (getVisibility() != 0) {
            setVisibility(4);
            show(animate);
            Label label = getLabelView();
            if (label != null) {
                label.show(animate);
            }
        }
    }
}
