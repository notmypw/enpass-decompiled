package com.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import com.theartofdev.edmodo.cropper.BitmapLoadingWorkerTask.Result;
import com.theartofdev.edmodo.cropper.BitmapUtils.RotateBitmapResult;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class CropImageView extends FrameLayout {
    private CropImageAnimation mAnimation;
    private boolean mAutoZoomEnabled;
    private Bitmap mBitmap;
    private WeakReference<BitmapCroppingWorkerTask> mBitmapCroppingWorkerTask;
    private WeakReference<BitmapLoadingWorkerTask> mBitmapLoadingWorkerTask;
    private final CropOverlayView mCropOverlayView;
    private int mDegreesRotated;
    private final Matrix mImageInverseMatrix;
    private final Matrix mImageMatrix;
    private final float[] mImagePoints;
    private int mImageResource;
    private final ImageView mImageView;
    private int mLayoutHeight;
    private int mLayoutWidth;
    private Uri mLoadedImageUri;
    private int mLoadedSampleSize;
    private int mMaxZoom;
    private OnCropImageCompleteListener mOnCropImageCompleteListener;
    @Deprecated
    private OnGetCroppedImageCompleteListener mOnGetCroppedImageCompleteListener;
    @Deprecated
    private OnSaveCroppedImageCompleteListener mOnSaveCroppedImageCompleteListener;
    private OnSetImageUriCompleteListener mOnSetImageUriCompleteListener;
    private final ProgressBar mProgressBar;
    private RectF mRestoreCropWindowRect;
    private ScaleType mScaleType;
    private boolean mShowCropOverlay;
    private boolean mShowProgressBar;
    private boolean mSizeChanged;
    private float mZoom;
    private float mZoomOffsetX;
    private float mZoomOffsetY;

    @Deprecated
    public interface OnGetCroppedImageCompleteListener {
        void onGetCroppedImageComplete(CropImageView cropImageView, Bitmap bitmap, Exception exception);
    }

    @Deprecated
    public interface OnSaveCroppedImageCompleteListener {
        void onSaveCroppedImageComplete(CropImageView cropImageView, Uri uri, Exception exception);
    }

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mImageMatrix = new Matrix();
        this.mImageInverseMatrix = new Matrix();
        this.mImagePoints = new float[8];
        this.mShowCropOverlay = true;
        this.mShowProgressBar = true;
        this.mAutoZoomEnabled = true;
        this.mLoadedSampleSize = 1;
        this.mZoom = 1.0f;
        CropImageOptions cropImageOptions = null;
        Intent intent = context instanceof Activity ? ((Activity) context).getIntent() : null;
        if (intent != null) {
            cropImageOptions = (CropImageOptions) intent.getParcelableExtra("CROP_IMAGE_EXTRA_OPTIONS");
        }
        if (cropImageOptions == null) {
            cropImageOptions = new CropImageOptions();
            if (attrs != null) {
                TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CropImageView, 0, 0);
                try {
                    cropImageOptions.fixAspectRatio = ta.getBoolean(R.styleable.CropImageView_cropFixAspectRatio, cropImageOptions.fixAspectRatio);
                    cropImageOptions.aspectRatioX = ta.getInteger(R.styleable.CropImageView_cropAspectRatioX, cropImageOptions.aspectRatioX);
                    cropImageOptions.aspectRatioY = ta.getInteger(R.styleable.CropImageView_cropAspectRatioY, cropImageOptions.aspectRatioY);
                    cropImageOptions.scaleType = ScaleType.values()[ta.getInt(R.styleable.CropImageView_cropScaleType, cropImageOptions.scaleType.ordinal())];
                    cropImageOptions.autoZoomEnabled = ta.getBoolean(R.styleable.CropImageView_cropAutoZoomEnabled, cropImageOptions.autoZoomEnabled);
                    cropImageOptions.multiTouchEnabled = ta.getBoolean(R.styleable.CropImageView_cropMultiTouchEnabled, cropImageOptions.multiTouchEnabled);
                    cropImageOptions.maxZoom = ta.getInteger(R.styleable.CropImageView_cropMaxZoom, cropImageOptions.maxZoom);
                    cropImageOptions.cropShape = CropShape.values()[ta.getInt(R.styleable.CropImageView_cropShape, cropImageOptions.cropShape.ordinal())];
                    cropImageOptions.guidelines = Guidelines.values()[ta.getInt(R.styleable.CropImageView_cropGuidelines, cropImageOptions.guidelines.ordinal())];
                    cropImageOptions.snapRadius = ta.getDimension(R.styleable.CropImageView_cropSnapRadius, cropImageOptions.snapRadius);
                    cropImageOptions.touchRadius = ta.getDimension(R.styleable.CropImageView_cropTouchRadius, cropImageOptions.touchRadius);
                    cropImageOptions.initialCropWindowPaddingRatio = ta.getFloat(R.styleable.CropImageView_cropInitialCropWindowPaddingRatio, cropImageOptions.initialCropWindowPaddingRatio);
                    cropImageOptions.borderLineThickness = ta.getDimension(R.styleable.CropImageView_cropBorderLineThickness, cropImageOptions.borderLineThickness);
                    cropImageOptions.borderLineColor = ta.getInteger(R.styleable.CropImageView_cropBorderLineColor, cropImageOptions.borderLineColor);
                    cropImageOptions.borderCornerThickness = ta.getDimension(R.styleable.CropImageView_cropBorderCornerThickness, cropImageOptions.borderCornerThickness);
                    cropImageOptions.borderCornerOffset = ta.getDimension(R.styleable.CropImageView_cropBorderCornerOffset, cropImageOptions.borderCornerOffset);
                    cropImageOptions.borderCornerLength = ta.getDimension(R.styleable.CropImageView_cropBorderCornerLength, cropImageOptions.borderCornerLength);
                    cropImageOptions.borderCornerColor = ta.getInteger(R.styleable.CropImageView_cropBorderCornerColor, cropImageOptions.borderCornerColor);
                    cropImageOptions.guidelinesThickness = ta.getDimension(R.styleable.CropImageView_cropGuidelinesThickness, cropImageOptions.guidelinesThickness);
                    cropImageOptions.guidelinesColor = ta.getInteger(R.styleable.CropImageView_cropGuidelinesColor, cropImageOptions.guidelinesColor);
                    cropImageOptions.backgroundColor = ta.getInteger(R.styleable.CropImageView_cropBackgroundColor, cropImageOptions.backgroundColor);
                    cropImageOptions.showCropOverlay = ta.getBoolean(R.styleable.CropImageView_cropShowCropOverlay, this.mShowCropOverlay);
                    cropImageOptions.showProgressBar = ta.getBoolean(R.styleable.CropImageView_cropShowProgressBar, this.mShowProgressBar);
                    cropImageOptions.borderCornerThickness = ta.getDimension(R.styleable.CropImageView_cropBorderCornerThickness, cropImageOptions.borderCornerThickness);
                    cropImageOptions.minCropWindowWidth = (int) ta.getDimension(R.styleable.CropImageView_cropMinCropWindowWidth, (float) cropImageOptions.minCropWindowWidth);
                    cropImageOptions.minCropWindowHeight = (int) ta.getDimension(R.styleable.CropImageView_cropMinCropWindowHeight, (float) cropImageOptions.minCropWindowHeight);
                    cropImageOptions.minCropResultWidth = (int) ta.getFloat(R.styleable.CropImageView_cropMinCropResultWidthPX, (float) cropImageOptions.minCropResultWidth);
                    cropImageOptions.minCropResultHeight = (int) ta.getFloat(R.styleable.CropImageView_cropMinCropResultHeightPX, (float) cropImageOptions.minCropResultHeight);
                    cropImageOptions.maxCropResultWidth = (int) ta.getFloat(R.styleable.CropImageView_cropMaxCropResultWidthPX, (float) cropImageOptions.maxCropResultWidth);
                    cropImageOptions.maxCropResultHeight = (int) ta.getFloat(R.styleable.CropImageView_cropMaxCropResultHeightPX, (float) cropImageOptions.maxCropResultHeight);
                    if (ta.hasValue(R.styleable.CropImageView_cropAspectRatioX) && ta.hasValue(R.styleable.CropImageView_cropAspectRatioX) && !ta.hasValue(R.styleable.CropImageView_cropFixAspectRatio)) {
                        cropImageOptions.fixAspectRatio = true;
                    }
                    ta.recycle();
                } catch (Throwable th) {
                    ta.recycle();
                }
            }
        }
        cropImageOptions.validate();
        this.mScaleType = cropImageOptions.scaleType;
        this.mAutoZoomEnabled = cropImageOptions.autoZoomEnabled;
        this.mMaxZoom = cropImageOptions.maxZoom;
        this.mShowCropOverlay = cropImageOptions.showCropOverlay;
        this.mShowProgressBar = cropImageOptions.showProgressBar;
        View v = LayoutInflater.from(context).inflate(R.layout.crop_image_view, this, true);
        this.mImageView = (ImageView) v.findViewById(R.id.ImageView_image);
        this.mImageView.setScaleType(ScaleType.MATRIX);
        this.mCropOverlayView = (CropOverlayView) v.findViewById(R.id.CropOverlayView);
        this.mCropOverlayView.setCropWindowChangeListener(new 1(this));
        this.mCropOverlayView.setInitialAttributeValues(cropImageOptions);
        this.mProgressBar = (ProgressBar) v.findViewById(R.id.CropProgressBar);
        setProgressBarVisibility();
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            this.mZoom = 1.0f;
            this.mZoomOffsetY = 0.0f;
            this.mZoomOffsetX = 0.0f;
            this.mCropOverlayView.resetCropOverlayView();
            requestLayout();
        }
    }

    public CropShape getCropShape() {
        return this.mCropOverlayView.getCropShape();
    }

    public void setCropShape(CropShape cropShape) {
        this.mCropOverlayView.setCropShape(cropShape);
    }

    public boolean isAutoZoomEnabled() {
        return this.mAutoZoomEnabled;
    }

    public void setAutoZoomEnabled(boolean autoZoomEnabled) {
        if (this.mAutoZoomEnabled != autoZoomEnabled) {
            this.mAutoZoomEnabled = autoZoomEnabled;
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setMultiTouchEnabled(boolean multiTouchEnabled) {
        if (this.mCropOverlayView.setMultiTouchEnabled(multiTouchEnabled)) {
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public int getMaxZoom() {
        return this.mMaxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        if (this.mMaxZoom != maxZoom && maxZoom > 0) {
            this.mMaxZoom = maxZoom;
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setMinCropResultSize(int minCropResultWidth, int minCropResultHeight) {
        this.mCropOverlayView.setMinCropResultSize(minCropResultWidth, minCropResultHeight);
    }

    public void setMaxCropResultSize(int maxCropResultWidth, int maxCropResultHeight) {
        this.mCropOverlayView.setMaxCropResultSize(maxCropResultWidth, maxCropResultHeight);
    }

    public int getRotatedDegrees() {
        return this.mDegreesRotated;
    }

    public void setRotatedDegrees(int degrees) {
        if (this.mDegreesRotated != degrees) {
            rotateImage(degrees - this.mDegreesRotated);
        }
    }

    public boolean isFixAspectRatio() {
        return this.mCropOverlayView.isFixAspectRatio();
    }

    public void setFixedAspectRatio(boolean fixAspectRatio) {
        this.mCropOverlayView.setFixedAspectRatio(fixAspectRatio);
    }

    public Guidelines getGuidelines() {
        return this.mCropOverlayView.getGuidelines();
    }

    public void setGuidelines(Guidelines guidelines) {
        this.mCropOverlayView.setGuidelines(guidelines);
    }

    public Pair<Integer, Integer> getAspectRatio() {
        return new Pair(Integer.valueOf(this.mCropOverlayView.getAspectRatioX()), Integer.valueOf(this.mCropOverlayView.getAspectRatioY()));
    }

    public void setAspectRatio(int aspectRatioX, int aspectRatioY) {
        this.mCropOverlayView.setAspectRatioX(aspectRatioX);
        this.mCropOverlayView.setAspectRatioY(aspectRatioY);
        setFixedAspectRatio(true);
    }

    public void clearAspectRatio() {
        this.mCropOverlayView.setAspectRatioX(1);
        this.mCropOverlayView.setAspectRatioY(1);
        setFixedAspectRatio(false);
    }

    public void setSnapRadius(float snapRadius) {
        if (snapRadius >= 0.0f) {
            this.mCropOverlayView.setSnapRadius(snapRadius);
        }
    }

    public boolean isShowProgressBar() {
        return this.mShowProgressBar;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        if (this.mShowProgressBar != showProgressBar) {
            this.mShowProgressBar = showProgressBar;
            setProgressBarVisibility();
        }
    }

    public boolean isShowCropOverlay() {
        return this.mShowCropOverlay;
    }

    public void setShowCropOverlay(boolean showCropOverlay) {
        if (this.mShowCropOverlay != showCropOverlay) {
            this.mShowCropOverlay = showCropOverlay;
            setCropOverlayVisibility();
        }
    }

    public int getImageResource() {
        return this.mImageResource;
    }

    public Uri getImageUri() {
        return this.mLoadedImageUri;
    }

    public Rect getCropRect() {
        if (this.mBitmap != null) {
            return BitmapUtils.getRectFromPoints(getCropPoints(), this.mBitmap.getWidth() * this.mLoadedSampleSize, this.mBitmap.getHeight() * this.mLoadedSampleSize, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY());
        }
        return null;
    }

    public float[] getCropPoints() {
        RectF cropWindowRect = this.mCropOverlayView.getCropWindowRect();
        float[] points = new float[]{cropWindowRect.left, cropWindowRect.top, cropWindowRect.right, cropWindowRect.top, cropWindowRect.right, cropWindowRect.bottom, cropWindowRect.left, cropWindowRect.bottom};
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapPoints(points);
        for (int i = 0; i < points.length; i++) {
            points[i] = points[i] * ((float) this.mLoadedSampleSize);
        }
        return points;
    }

    public void setCropRect(Rect rect) {
        this.mCropOverlayView.setInitialCropWindowRect(rect);
    }

    public void resetCropRect() {
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mDegreesRotated = 0;
        applyImageMatrix((float) getWidth(), (float) getHeight(), false, false);
        this.mCropOverlayView.resetCropWindowRect();
    }

    public void resetCropRectWithRotation() {
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        applyImageMatrix((float) getWidth(), (float) getHeight(), false, false);
        this.mCropOverlayView.resetCropWindowRect();
    }

    public Bitmap getCroppedImage() {
        return getCroppedImage(0, 0, RequestSizeOptions.NONE);
    }

    public Bitmap getCroppedImage(int reqWidth, int reqHeight) {
        return getCroppedImage(0, 0, RequestSizeOptions.RESIZE_INSIDE);
    }

    public Bitmap getCroppedImage(int reqWidth, int reqHeight, RequestSizeOptions options) {
        if (this.mBitmap == null) {
            return null;
        }
        Bitmap croppedBitmap;
        this.mImageView.clearAnimation();
        if (options == RequestSizeOptions.NONE) {
            reqWidth = 0;
        }
        if (options == RequestSizeOptions.NONE) {
            reqHeight = 0;
        }
        if (this.mLoadedImageUri == null || (this.mLoadedSampleSize <= 1 && options != RequestSizeOptions.SAMPLING)) {
            croppedBitmap = BitmapUtils.cropBitmap(this.mBitmap, getCropPoints(), this.mDegreesRotated, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY());
        } else {
            croppedBitmap = BitmapUtils.cropBitmap(getContext(), this.mLoadedImageUri, getCropPoints(), this.mDegreesRotated, this.mBitmap.getWidth() * this.mLoadedSampleSize, this.mBitmap.getHeight() * this.mLoadedSampleSize, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), reqWidth, reqHeight).bitmap;
        }
        return BitmapUtils.resizeBitmap(croppedBitmap, reqWidth, reqHeight, options);
    }

    public void getCroppedImageAsync() {
        getCroppedImageAsync(0, 0, RequestSizeOptions.NONE);
    }

    public void getCroppedImageAsync(int reqWidth, int reqHeight) {
        getCroppedImageAsync(reqWidth, reqHeight, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void getCroppedImageAsync(int reqWidth, int reqHeight, RequestSizeOptions options) {
        if (this.mOnCropImageCompleteListener == null && this.mOnGetCroppedImageCompleteListener == null) {
            throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
        }
        startCropWorkerTask(reqWidth, reqHeight, options, null, null, 0);
    }

    public void saveCroppedImageAsync(Uri saveUri) {
        saveCroppedImageAsync(saveUri, CompressFormat.JPEG, 90, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri saveUri, CompressFormat saveCompressFormat, int saveCompressQuality) {
        saveCroppedImageAsync(saveUri, saveCompressFormat, saveCompressQuality, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri saveUri, CompressFormat saveCompressFormat, int saveCompressQuality, int reqWidth, int reqHeight) {
        saveCroppedImageAsync(saveUri, saveCompressFormat, saveCompressQuality, reqWidth, reqHeight, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void saveCroppedImageAsync(Uri saveUri, CompressFormat saveCompressFormat, int saveCompressQuality, int reqWidth, int reqHeight, RequestSizeOptions options) {
        if (this.mOnCropImageCompleteListener == null && this.mOnSaveCroppedImageCompleteListener == null) {
            throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
        }
        startCropWorkerTask(reqWidth, reqHeight, options, saveUri, saveCompressFormat, saveCompressQuality);
    }

    public void setOnSetImageUriCompleteListener(OnSetImageUriCompleteListener listener) {
        this.mOnSetImageUriCompleteListener = listener;
    }

    public void setOnCropImageCompleteListener(OnCropImageCompleteListener listener) {
        this.mOnCropImageCompleteListener = listener;
    }

    @Deprecated
    public void setOnGetCroppedImageCompleteListener(OnGetCroppedImageCompleteListener listener) {
        this.mOnGetCroppedImageCompleteListener = listener;
    }

    @Deprecated
    public void setOnSaveCroppedImageCompleteListener(OnSaveCroppedImageCompleteListener listener) {
        this.mOnSaveCroppedImageCompleteListener = listener;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mCropOverlayView.setInitialCropWindowRect(null);
        setBitmap(bitmap);
    }

    public void setImageBitmap(Bitmap bitmap, ExifInterface exif) {
        Bitmap setBitmap;
        if (bitmap == null || exif == null) {
            setBitmap = bitmap;
        } else {
            RotateBitmapResult result = BitmapUtils.rotateBitmapByExif(bitmap, exif);
            setBitmap = result.bitmap;
            this.mDegreesRotated = result.degrees;
        }
        this.mCropOverlayView.setInitialCropWindowRect(null);
        setBitmap(setBitmap);
    }

    public void setImageResource(int resId) {
        if (resId != 0) {
            this.mCropOverlayView.setInitialCropWindowRect(null);
            setBitmap(BitmapFactory.decodeResource(getResources(), resId), resId);
        }
    }

    public void setImageUriAsync(Uri uri) {
        if (uri != null) {
            BitmapLoadingWorkerTask currentTask = this.mBitmapLoadingWorkerTask != null ? (BitmapLoadingWorkerTask) this.mBitmapLoadingWorkerTask.get() : null;
            if (currentTask != null) {
                currentTask.cancel(true);
            }
            clearImageInt();
            this.mCropOverlayView.setInitialCropWindowRect(null);
            this.mBitmapLoadingWorkerTask = new WeakReference(new BitmapLoadingWorkerTask(this, uri));
            ((BitmapLoadingWorkerTask) this.mBitmapLoadingWorkerTask.get()).execute(new Void[0]);
            setProgressBarVisibility();
        }
    }

    public void clearImage() {
        clearImageInt();
        this.mCropOverlayView.setInitialCropWindowRect(null);
    }

    public void rotateImage(int degrees) {
        if (this.mBitmap != null) {
            float width;
            boolean flipAxes = (!this.mCropOverlayView.isFixAspectRatio() && degrees > 45 && degrees < 135) || (degrees > 215 && degrees < 305);
            BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
            float halfWidth = (flipAxes ? BitmapUtils.RECT.height() : BitmapUtils.RECT.width()) / 2.0f;
            if (flipAxes) {
                width = BitmapUtils.RECT.width();
            } else {
                width = BitmapUtils.RECT.height();
            }
            float halfHeight = width / 2.0f;
            this.mImageMatrix.invert(this.mImageInverseMatrix);
            BitmapUtils.POINTS[0] = BitmapUtils.RECT.centerX();
            BitmapUtils.POINTS[1] = BitmapUtils.RECT.centerY();
            BitmapUtils.POINTS[2] = 0.0f;
            BitmapUtils.POINTS[3] = 0.0f;
            BitmapUtils.POINTS[4] = 1.0f;
            BitmapUtils.POINTS[5] = 0.0f;
            this.mImageInverseMatrix.mapPoints(BitmapUtils.POINTS);
            this.mDegreesRotated += degrees;
            this.mDegreesRotated = this.mDegreesRotated >= 0 ? this.mDegreesRotated % 360 : (this.mDegreesRotated % 360) + 360;
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            this.mZoom = (float) (((double) this.mZoom) / Math.sqrt(Math.pow((double) (BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2]), 2.0d) + Math.pow((double) (BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3]), 2.0d)));
            this.mZoom = Math.max(this.mZoom, 1.0f);
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            double change = Math.sqrt(Math.pow((double) (BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2]), 2.0d) + Math.pow((double) (BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3]), 2.0d));
            halfWidth = (float) (((double) halfWidth) * change);
            halfHeight = (float) (((double) halfHeight) * change);
            BitmapUtils.RECT.set(BitmapUtils.POINTS2[0] - halfWidth, BitmapUtils.POINTS2[1] - halfHeight, BitmapUtils.POINTS2[0] + halfWidth, BitmapUtils.POINTS2[1] + halfHeight);
            this.mCropOverlayView.resetCropOverlayView();
            this.mCropOverlayView.setCropWindowRect(BitmapUtils.RECT);
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            handleCropWindowChanged(false, false);
            this.mCropOverlayView.fixCurrentCropWindowRect();
        }
    }

    void onSetImageUriAsyncComplete(Result result) {
        this.mBitmapLoadingWorkerTask = null;
        setProgressBarVisibility();
        if (result.error == null) {
            setBitmap(result.bitmap, result.uri, result.loadSampleSize, result.degreesRotated);
        }
        OnSetImageUriCompleteListener listener = this.mOnSetImageUriCompleteListener;
        if (listener != null) {
            listener.onSetImageUriComplete(this, result.uri, result.error);
        }
    }

    void onImageCroppingAsyncComplete(BitmapCroppingWorkerTask.Result result) {
        this.mBitmapCroppingWorkerTask = null;
        setProgressBarVisibility();
        OnCropImageCompleteListener listener = this.mOnCropImageCompleteListener;
        if (listener != null) {
            listener.onCropImageComplete(this, new CropResult(result.bitmap, result.uri, result.error, getCropPoints(), getCropRect(), getRotatedDegrees(), result.sampleSize));
        }
        if (result.isSave) {
            OnSaveCroppedImageCompleteListener listener2 = this.mOnSaveCroppedImageCompleteListener;
            if (listener2 != null) {
                listener2.onSaveCroppedImageComplete(this, result.uri, result.error);
                return;
            }
            return;
        }
        OnGetCroppedImageCompleteListener listener22 = this.mOnGetCroppedImageCompleteListener;
        if (listener22 != null) {
            listener22.onGetCroppedImageComplete(this, result.bitmap, result.error);
        }
    }

    private void setBitmap(Bitmap bitmap) {
        setBitmap(bitmap, 0, null, 1, 0);
    }

    private void setBitmap(Bitmap bitmap, int imageResource) {
        setBitmap(bitmap, imageResource, null, 1, 0);
    }

    private void setBitmap(Bitmap bitmap, Uri imageUri, int loadSampleSize, int degreesRotated) {
        setBitmap(bitmap, 0, imageUri, loadSampleSize, degreesRotated);
    }

    private void setBitmap(Bitmap bitmap, int imageResource, Uri imageUri, int loadSampleSize, int degreesRotated) {
        if (this.mBitmap == null || !this.mBitmap.equals(bitmap)) {
            this.mImageView.clearAnimation();
            clearImageInt();
            this.mBitmap = bitmap;
            this.mImageView.setImageBitmap(this.mBitmap);
            this.mLoadedImageUri = imageUri;
            this.mImageResource = imageResource;
            this.mLoadedSampleSize = loadSampleSize;
            this.mDegreesRotated = degreesRotated;
            applyImageMatrix((float) getWidth(), (float) getHeight(), true, false);
            if (this.mCropOverlayView != null) {
                this.mCropOverlayView.resetCropOverlayView();
                setCropOverlayVisibility();
            }
        }
    }

    private void clearImageInt() {
        if (this.mBitmap != null && (this.mImageResource > 0 || this.mLoadedImageUri != null)) {
            this.mBitmap.recycle();
        }
        this.mBitmap = null;
        this.mImageResource = 0;
        this.mLoadedImageUri = null;
        this.mLoadedSampleSize = 1;
        this.mDegreesRotated = 0;
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mImageMatrix.reset();
        this.mImageView.setImageBitmap(null);
        setCropOverlayVisibility();
    }

    public void startCropWorkerTask(int reqWidth, int reqHeight, RequestSizeOptions options, Uri saveUri, CompressFormat saveCompressFormat, int saveCompressQuality) {
        if (this.mBitmap != null) {
            this.mImageView.clearAnimation();
            BitmapCroppingWorkerTask currentTask = this.mBitmapCroppingWorkerTask != null ? (BitmapCroppingWorkerTask) this.mBitmapCroppingWorkerTask.get() : null;
            if (currentTask != null) {
                currentTask.cancel(true);
            }
            if (options == RequestSizeOptions.NONE) {
                reqWidth = 0;
            }
            if (options == RequestSizeOptions.NONE) {
                reqHeight = 0;
            }
            int orgWidth = this.mBitmap.getWidth() * this.mLoadedSampleSize;
            int orgHeight = this.mBitmap.getHeight() * this.mLoadedSampleSize;
            if (this.mLoadedImageUri == null || (this.mLoadedSampleSize <= 1 && options != RequestSizeOptions.SAMPLING)) {
                this.mBitmapCroppingWorkerTask = new WeakReference(new BitmapCroppingWorkerTask(this, this.mBitmap, getCropPoints(), this.mDegreesRotated, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), reqWidth, reqHeight, options, saveUri, saveCompressFormat, saveCompressQuality));
            } else {
                this.mBitmapCroppingWorkerTask = new WeakReference(new BitmapCroppingWorkerTask(this, this.mLoadedImageUri, getCropPoints(), this.mDegreesRotated, orgWidth, orgHeight, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), reqWidth, reqHeight, options, saveUri, saveCompressFormat, saveCompressQuality));
            }
            ((BitmapCroppingWorkerTask) this.mBitmapCroppingWorkerTask.get()).execute(new Void[0]);
            setProgressBarVisibility();
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putParcelable("LOADED_IMAGE_URI", this.mLoadedImageUri);
        bundle.putInt("LOADED_IMAGE_RESOURCE", this.mImageResource);
        if (this.mLoadedImageUri == null && this.mImageResource < 1) {
            bundle.putParcelable("SET_BITMAP", this.mBitmap);
        }
        if (!(this.mLoadedImageUri == null || this.mBitmap == null)) {
            String key = UUID.randomUUID().toString();
            BitmapUtils.mStateBitmap = new Pair(key, new WeakReference(this.mBitmap));
            bundle.putString("LOADED_IMAGE_STATE_BITMAP_KEY", key);
        }
        if (this.mBitmapLoadingWorkerTask != null) {
            BitmapLoadingWorkerTask task = (BitmapLoadingWorkerTask) this.mBitmapLoadingWorkerTask.get();
            if (task != null) {
                bundle.putParcelable("LOADING_IMAGE_URI", task.getUri());
            }
        }
        bundle.putInt("LOADED_SAMPLE_SIZE", this.mLoadedSampleSize);
        bundle.putInt("DEGREES_ROTATED", this.mDegreesRotated);
        bundle.putParcelable("INITIAL_CROP_RECT", this.mCropOverlayView.getInitialCropWindowRect());
        BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapRect(BitmapUtils.RECT);
        bundle.putParcelable("CROP_WINDOW_RECT", BitmapUtils.RECT);
        bundle.putString("CROP_SHAPE", this.mCropOverlayView.getCropShape().name());
        bundle.putBoolean("CROP_AUTO_ZOOM_ENABLED", this.mAutoZoomEnabled);
        bundle.putInt("CROP_MAX_ZOOM", this.mMaxZoom);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            if (this.mBitmapLoadingWorkerTask == null && this.mLoadedImageUri == null && this.mBitmap == null && this.mImageResource == 0) {
                Uri uri = (Uri) bundle.getParcelable("LOADED_IMAGE_URI");
                if (uri != null) {
                    String key = bundle.getString("LOADED_IMAGE_STATE_BITMAP_KEY");
                    if (key != null) {
                        Bitmap stateBitmap;
                        if (BitmapUtils.mStateBitmap == null || !((String) BitmapUtils.mStateBitmap.first).equals(key)) {
                            stateBitmap = null;
                        } else {
                            stateBitmap = (Bitmap) ((WeakReference) BitmapUtils.mStateBitmap.second).get();
                        }
                        if (!(stateBitmap == null || stateBitmap.isRecycled())) {
                            BitmapUtils.mStateBitmap = null;
                            setBitmap(stateBitmap, uri, bundle.getInt("LOADED_SAMPLE_SIZE"), 0);
                        }
                    }
                    if (this.mLoadedImageUri == null) {
                        setImageUriAsync(uri);
                    }
                } else {
                    int resId = bundle.getInt("LOADED_IMAGE_RESOURCE");
                    if (resId > 0) {
                        setImageResource(resId);
                    } else {
                        Bitmap bitmap = (Bitmap) bundle.getParcelable("SET_BITMAP");
                        if (bitmap != null) {
                            setBitmap(bitmap);
                        } else {
                            uri = (Uri) bundle.getParcelable("LOADING_IMAGE_URI");
                            if (uri != null) {
                                setImageUriAsync(uri);
                            }
                        }
                    }
                }
                this.mDegreesRotated = bundle.getInt("DEGREES_ROTATED");
                this.mCropOverlayView.setInitialCropWindowRect((Rect) bundle.getParcelable("INITIAL_CROP_RECT"));
                this.mRestoreCropWindowRect = (RectF) bundle.getParcelable("CROP_WINDOW_RECT");
                this.mCropOverlayView.setCropShape(CropShape.valueOf(bundle.getString("CROP_SHAPE")));
                this.mAutoZoomEnabled = bundle.getBoolean("CROP_AUTO_ZOOM_ENABLED");
                this.mMaxZoom = bundle.getInt("CROP_MAX_ZOOM");
            }
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (this.mBitmap != null) {
            int desiredWidth;
            int desiredHeight;
            if (heightSize == 0) {
                heightSize = this.mBitmap.getHeight();
            }
            double viewToBitmapWidthRatio = Double.POSITIVE_INFINITY;
            double viewToBitmapHeightRatio = Double.POSITIVE_INFINITY;
            if (widthSize < this.mBitmap.getWidth()) {
                viewToBitmapWidthRatio = ((double) widthSize) / ((double) this.mBitmap.getWidth());
            }
            if (heightSize < this.mBitmap.getHeight()) {
                viewToBitmapHeightRatio = ((double) heightSize) / ((double) this.mBitmap.getHeight());
            }
            if (viewToBitmapWidthRatio == Double.POSITIVE_INFINITY && viewToBitmapHeightRatio == Double.POSITIVE_INFINITY) {
                desiredWidth = this.mBitmap.getWidth();
                desiredHeight = this.mBitmap.getHeight();
            } else if (viewToBitmapWidthRatio <= viewToBitmapHeightRatio) {
                desiredWidth = widthSize;
                desiredHeight = (int) (((double) this.mBitmap.getHeight()) * viewToBitmapWidthRatio);
            } else {
                desiredHeight = heightSize;
                desiredWidth = (int) (((double) this.mBitmap.getWidth()) * viewToBitmapHeightRatio);
            }
            int width = getOnMeasureSpec(widthMode, widthSize, desiredWidth);
            int height = getOnMeasureSpec(heightMode, heightSize, desiredHeight);
            this.mLayoutWidth = width;
            this.mLayoutHeight = height;
            setMeasuredDimension(this.mLayoutWidth, this.mLayoutHeight);
            return;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mLayoutWidth <= 0 || this.mLayoutHeight <= 0) {
            updateImageBounds(true);
            return;
        }
        LayoutParams origParams = getLayoutParams();
        origParams.width = this.mLayoutWidth;
        origParams.height = this.mLayoutHeight;
        setLayoutParams(origParams);
        if (this.mBitmap != null) {
            applyImageMatrix((float) (r - l), (float) (b - t), true, false);
            if (this.mRestoreCropWindowRect != null) {
                this.mImageMatrix.mapRect(this.mRestoreCropWindowRect);
                this.mCropOverlayView.setCropWindowRect(this.mRestoreCropWindowRect);
                handleCropWindowChanged(false, false);
                this.mCropOverlayView.fixCurrentCropWindowRect();
                this.mRestoreCropWindowRect = null;
                return;
            } else if (this.mSizeChanged) {
                this.mSizeChanged = false;
                handleCropWindowChanged(false, false);
                return;
            } else {
                return;
            }
        }
        updateImageBounds(true);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        boolean z = oldw > 0 && oldh > 0;
        this.mSizeChanged = z;
    }

    private void handleCropWindowChanged(boolean inProgress, boolean animate) {
        int width = getWidth();
        int height = getHeight();
        if (this.mBitmap != null && width > 0 && height > 0) {
            RectF cropRect = this.mCropOverlayView.getCropWindowRect();
            if (inProgress) {
                if (cropRect.left < 0.0f || cropRect.top < 0.0f || cropRect.right > ((float) width) || cropRect.bottom > ((float) height)) {
                    applyImageMatrix((float) width, (float) height, false, false);
                }
            } else if (this.mAutoZoomEnabled || this.mZoom > 1.0f) {
                float newZoom = 0.0f;
                if (this.mZoom < ((float) this.mMaxZoom) && cropRect.width() < ((float) width) * 0.5f && cropRect.height() < ((float) height) * 0.5f) {
                    newZoom = Math.min((float) this.mMaxZoom, Math.min(((float) width) / ((cropRect.width() / this.mZoom) / 0.64f), ((float) height) / ((cropRect.height() / this.mZoom) / 0.64f)));
                }
                if (this.mZoom > 1.0f && (cropRect.width() > ((float) width) * 0.65f || cropRect.height() > ((float) height) * 0.65f)) {
                    newZoom = Math.max(1.0f, Math.min(((float) width) / ((cropRect.width() / this.mZoom) / 0.51f), ((float) height) / ((cropRect.height() / this.mZoom) / 0.51f)));
                }
                if (!this.mAutoZoomEnabled) {
                    newZoom = 1.0f;
                }
                if (newZoom > 0.0f && newZoom != this.mZoom) {
                    if (animate) {
                        if (this.mAnimation == null) {
                            this.mAnimation = new CropImageAnimation(this.mImageView, this.mCropOverlayView);
                        }
                        this.mAnimation.setStartState(this.mImagePoints, this.mImageMatrix);
                    }
                    this.mZoom = newZoom;
                    applyImageMatrix((float) width, (float) height, true, animate);
                }
            }
        }
    }

    private void applyImageMatrix(float width, float height, boolean center, boolean animate) {
        float f = 0.0f;
        if (this.mBitmap != null && width > 0.0f && height > 0.0f) {
            this.mImageMatrix.invert(this.mImageInverseMatrix);
            RectF cropRect = this.mCropOverlayView.getCropWindowRect();
            this.mImageInverseMatrix.mapRect(cropRect);
            this.mImageMatrix.reset();
            this.mImageMatrix.postTranslate((width - ((float) this.mBitmap.getWidth())) / 2.0f, (height - ((float) this.mBitmap.getHeight())) / 2.0f);
            mapImagePointsByImageMatrix();
            if (this.mDegreesRotated > 0) {
                this.mImageMatrix.postRotate((float) this.mDegreesRotated, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                mapImagePointsByImageMatrix();
            }
            float scale = Math.min(width / BitmapUtils.getRectWidth(this.mImagePoints), height / BitmapUtils.getRectHeight(this.mImagePoints));
            if (this.mScaleType == ScaleType.FIT_CENTER || ((this.mScaleType == ScaleType.CENTER_INSIDE && scale < 1.0f) || (scale > 1.0f && this.mAutoZoomEnabled))) {
                this.mImageMatrix.postScale(scale, scale, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                mapImagePointsByImageMatrix();
            }
            this.mImageMatrix.postScale(this.mZoom, this.mZoom, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
            mapImagePointsByImageMatrix();
            this.mImageMatrix.mapRect(cropRect);
            if (center) {
                float f2;
                if (width > BitmapUtils.getRectWidth(this.mImagePoints)) {
                    f2 = 0.0f;
                } else {
                    f2 = Math.max(Math.min((width / 2.0f) - cropRect.centerX(), -BitmapUtils.getRectLeft(this.mImagePoints)), ((float) getWidth()) - BitmapUtils.getRectRight(this.mImagePoints)) / this.mZoom;
                }
                this.mZoomOffsetX = f2;
                if (height <= BitmapUtils.getRectHeight(this.mImagePoints)) {
                    f = Math.max(Math.min((height / 2.0f) - cropRect.centerY(), -BitmapUtils.getRectTop(this.mImagePoints)), ((float) getHeight()) - BitmapUtils.getRectBottom(this.mImagePoints)) / this.mZoom;
                }
                this.mZoomOffsetY = f;
            } else {
                this.mZoomOffsetX = Math.min(Math.max(this.mZoomOffsetX * this.mZoom, -cropRect.left), (-cropRect.right) + width) / this.mZoom;
                this.mZoomOffsetY = Math.min(Math.max(this.mZoomOffsetY * this.mZoom, -cropRect.top), (-cropRect.bottom) + height) / this.mZoom;
            }
            this.mImageMatrix.postTranslate(this.mZoomOffsetX * this.mZoom, this.mZoomOffsetY * this.mZoom);
            cropRect.offset(this.mZoomOffsetX * this.mZoom, this.mZoomOffsetY * this.mZoom);
            this.mCropOverlayView.setCropWindowRect(cropRect);
            mapImagePointsByImageMatrix();
            if (animate) {
                this.mAnimation.setEndState(this.mImagePoints, this.mImageMatrix);
                this.mImageView.startAnimation(this.mAnimation);
            } else {
                this.mImageView.setImageMatrix(this.mImageMatrix);
            }
            updateImageBounds(false);
        }
    }

    private void mapImagePointsByImageMatrix() {
        this.mImagePoints[0] = 0.0f;
        this.mImagePoints[1] = 0.0f;
        this.mImagePoints[2] = (float) this.mBitmap.getWidth();
        this.mImagePoints[3] = 0.0f;
        this.mImagePoints[4] = (float) this.mBitmap.getWidth();
        this.mImagePoints[5] = (float) this.mBitmap.getHeight();
        this.mImagePoints[6] = 0.0f;
        this.mImagePoints[7] = (float) this.mBitmap.getHeight();
        this.mImageMatrix.mapPoints(this.mImagePoints);
    }

    private static int getOnMeasureSpec(int measureSpecMode, int measureSpecSize, int desiredSize) {
        if (measureSpecMode == 1073741824) {
            return measureSpecSize;
        }
        if (measureSpecMode == Integer.MIN_VALUE) {
            return Math.min(desiredSize, measureSpecSize);
        }
        return desiredSize;
    }

    private void setCropOverlayVisibility() {
        if (this.mCropOverlayView != null) {
            CropOverlayView cropOverlayView = this.mCropOverlayView;
            int i = (!this.mShowCropOverlay || this.mBitmap == null) ? 4 : 0;
            cropOverlayView.setVisibility(i);
        }
    }

    private void setProgressBarVisibility() {
        boolean visible;
        int i = 0;
        if (!this.mShowProgressBar || ((this.mBitmap != null || this.mBitmapLoadingWorkerTask == null) && this.mBitmapCroppingWorkerTask == null)) {
            visible = false;
        } else {
            visible = true;
        }
        ProgressBar progressBar = this.mProgressBar;
        if (!visible) {
            i = 4;
        }
        progressBar.setVisibility(i);
    }

    private void updateImageBounds(boolean clear) {
        if (!(this.mBitmap == null || clear)) {
            this.mCropOverlayView.setCropWindowLimits((float) getWidth(), (float) getHeight(), ((float) (this.mBitmap.getWidth() * this.mLoadedSampleSize)) / BitmapUtils.getRectWidth(this.mImagePoints), ((float) (this.mBitmap.getHeight() * this.mLoadedSampleSize)) / BitmapUtils.getRectHeight(this.mImagePoints));
        }
        this.mCropOverlayView.setBounds(clear ? null : this.mImagePoints, getWidth(), getHeight());
    }
}
