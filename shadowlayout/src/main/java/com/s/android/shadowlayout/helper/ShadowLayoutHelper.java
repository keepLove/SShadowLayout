package com.s.android.shadowlayout.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.s.android.shadowlayout.ILayout;
import com.s.android.shadowlayout.R;

import java.lang.ref.WeakReference;


/**
 * 阴影帮助类
 */
public abstract class ShadowLayoutHelper implements ILayout {

    private static final int SHADOW_TYPE_SYSTEM = 0;
    private static final int SHADOW_TYPE_PADDING = 1;

    public static ShadowLayoutHelper getHelper(Context context, @Nullable AttributeSet attrs, int defAttr, View owner) {
        if (!useFeature() || attrs == null) {
            return new ShadowLayoutPaddingHelper(context, attrs, defAttr, owner);
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SShadowLayout, defAttr, 0);
        int type = typedArray.getInt(R.styleable.SShadowLayout_ssl_shadowType, SHADOW_TYPE_SYSTEM);
        typedArray.recycle();
        if (type == SHADOW_TYPE_PADDING) {
            return new ShadowLayoutPaddingHelper(context, attrs, defAttr, owner);
        }
        return new ShadowLayoutSystemHelper(context, attrs, defAttr, owner);
    }

    @SuppressLint({"AnnotateVersionCheck", "ObsoleteSdkInt"})
    protected static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }

    // size
    protected int mWidthLimit = 0;
    protected int mHeightLimit = 0;
    protected int mWidthMini = 0;
    protected int mHeightMini = 0;

    protected final WeakReference<View> mOwner;

    protected int mShadowElevation = 0;
    protected float mShadowAlpha = 0f;
    protected int mRadius;
    protected int mShadowColor = 0;
    @Nullable
    protected Drawable mShadowBackground = null;

    protected ShadowLayoutHelper(Context context, @Nullable AttributeSet attrs, int defAttr, View owner) {
        mOwner = new WeakReference<>(owner);
        int radius = 0, shadow = 0;
        if (null != attrs || defAttr != 0) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SShadowLayout, defAttr, 0);
            int count = typedArray.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int index = typedArray.getIndex(i);
                if (index == R.styleable.SShadowLayout_android_maxWidth) {
                    mWidthLimit = typedArray.getDimensionPixelSize(index, mWidthLimit);
                } else if (index == R.styleable.SShadowLayout_android_maxHeight) {
                    mHeightLimit = typedArray.getDimensionPixelSize(index, mHeightLimit);
                } else if (index == R.styleable.SShadowLayout_android_minWidth) {
                    mWidthMini = typedArray.getDimensionPixelSize(index, mWidthMini);
                } else if (index == R.styleable.SShadowLayout_android_minHeight) {
                    mHeightMini = typedArray.getDimensionPixelSize(index, mHeightMini);
                } else if (index == R.styleable.SShadowLayout_ssl_radius) {
                    radius = typedArray.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SShadowLayout_ssl_shadowElevation) {
                    shadow = typedArray.getDimensionPixelSize(index, shadow);
                } else if (index == R.styleable.SShadowLayout_ssl_shadowAlpha) {
                    mShadowAlpha = typedArray.getFloat(index, mShadowAlpha);
                } else if (index == R.styleable.SShadowLayout_ssl_shadowColor) {
                    mShadowColor = typedArray.getColor(index, mShadowColor);
                } else if (index == R.styleable.SShadowLayout_ssl_background) {
                    mShadowBackground = typedArray.getDrawable(index);
                }
            }
            typedArray.recycle();
        }
        setRadiusAndShadow(radius, shadow, mShadowAlpha);
    }

    @Override
    public boolean setWidthLimit(int widthLimit) {
        if (mWidthLimit != widthLimit) {
            mWidthLimit = widthLimit;
            return true;
        }
        return false;
    }

    @Override
    public boolean setHeightLimit(int heightLimit) {
        if (mHeightLimit != heightLimit) {
            mHeightLimit = heightLimit;
            return true;
        }
        return false;
    }

    @Override
    public void setShadowElevation(int elevation) {
        if (mShadowElevation == elevation) {
            return;
        }
        mShadowElevation = elevation;
        invalidate();
    }

    @Override
    public int getShadowElevation() {
        return mShadowElevation;
    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {
        if (mShadowAlpha == shadowAlpha) {
            return;
        }
        mShadowAlpha = shadowAlpha;
        invalidate();
    }

    @Override
    public float getShadowAlpha() {
        return mShadowAlpha;
    }

    @Override
    public void setRadius(int radius) {
        if (mRadius == radius) {
            return;
        }
        mRadius = radius;
        invalidate();
    }

    @Override
    public int getRadius() {
        return mRadius;
    }

    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha) {
        setRadius(radius);
        setShadowAlpha(shadowAlpha);
        setShadowElevation(shadowElevation);
    }

    public abstract void invalidate();

    public int handleMiniWidth(int widthMeasureSpec, int measuredWidth) {
        if (View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredWidth < mWidthMini) {
            return View.MeasureSpec.makeMeasureSpec(mWidthMini, View.MeasureSpec.EXACTLY);
        }
        return widthMeasureSpec;
    }

    public int handleMiniHeight(int heightMeasureSpec, int measuredHeight) {
        if (View.MeasureSpec.getMode(heightMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredHeight < mHeightMini) {
            return View.MeasureSpec.makeMeasureSpec(mHeightMini, View.MeasureSpec.EXACTLY);
        }
        return heightMeasureSpec;
    }

    public int getMeasuredWidthSpec(int widthMeasureSpec) {
        if (mWidthLimit > 0) {
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            if (size > mWidthLimit) {
                int mode = View.MeasureSpec.getMode(widthMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }

            }
        }
        return widthMeasureSpec;
    }

    public int getMeasuredHeightSpec(int heightMeasureSpec) {
        if (mHeightLimit > 0) {
            int size = View.MeasureSpec.getSize(heightMeasureSpec);
            if (size > mHeightLimit) {
                int mode = View.MeasureSpec.getMode(heightMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }
            }
        }
        return heightMeasureSpec;
    }

    public abstract void drawShadow(Canvas canvas);

}
