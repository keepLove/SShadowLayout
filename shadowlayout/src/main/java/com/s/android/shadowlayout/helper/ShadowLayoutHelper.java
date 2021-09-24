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

    protected final WeakReference<View> mOwner;

    protected int mShadowElevation = 0;
    protected float mShadowAlpha = 0f;
    protected int mRadius;
    protected int mShadowColor = 0;
    @Nullable
    protected Drawable mShadowBackground = null;

    protected ShadowLayoutHelper(Context context, @Nullable AttributeSet attrs, int defAttr, View owner) {
        mOwner = new WeakReference<>(owner);
        if (null != attrs || defAttr != 0) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SShadowLayout, defAttr, 0);
            mRadius = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_radius, 0);
            mShadowElevation = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_shadowElevation, mShadowElevation);
            mShadowAlpha = typedArray.getFloat(R.styleable.SShadowLayout_ssl_shadowAlpha, mShadowAlpha);
            mShadowColor = typedArray.getColor(R.styleable.SShadowLayout_ssl_shadowColor, mShadowColor);
            mShadowBackground = typedArray.getDrawable(R.styleable.SShadowLayout_ssl_background);
            typedArray.recycle();
        }
        invalidate();
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

    public abstract void invalidate();

    public abstract void drawShadow(Canvas canvas);

}
