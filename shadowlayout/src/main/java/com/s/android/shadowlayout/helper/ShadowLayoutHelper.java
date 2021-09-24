package com.s.android.shadowlayout.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
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

    public static ShadowLayoutHelper getHelper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        if (!useFeature()) {
            return new ShadowLayoutPaddingHelper(context, attrs, defStyleAttr, defStyleRes, owner);
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SShadowLayout, defStyleAttr, defStyleRes);
        int type = typedArray.getInt(R.styleable.SShadowLayout_ssl_shadowType, SHADOW_TYPE_SYSTEM);
        typedArray.recycle();
        if (type == SHADOW_TYPE_PADDING) {
            return new ShadowLayoutPaddingHelper(context, attrs, defStyleAttr, defStyleRes, owner);
        }
        return new ShadowLayoutSystemHelper(context, attrs, defStyleAttr, defStyleRes, owner);
    }

    @SuppressLint({"AnnotateVersionCheck", "ObsoleteSdkInt"})
    protected static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }

    private final WeakReference<View> mOwner;

    /**
     * 阴影偏移量
     */
    protected int mShadowElevation = 0;
    /**
     * 阴影透明度
     */
    protected float mShadowAlpha = 0f;
    /**
     * 圆角半径
     */
    protected int mRadius;
    /**
     * 阴影颜色
     */
    protected int mShadowColor = 0;
    /**
     * 内容区域背景
     */
    @Nullable
    protected Drawable mShadowBackground;

    protected ShadowLayoutHelper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        mOwner = new WeakReference<>(owner);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SShadowLayout, defStyleAttr, defStyleRes);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_radius, 0);
        mShadowElevation = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_shadowElevation, mShadowElevation);
        mShadowAlpha = typedArray.getFloat(R.styleable.SShadowLayout_ssl_shadowAlpha, mShadowAlpha);
        mShadowColor = typedArray.getColor(R.styleable.SShadowLayout_ssl_shadowColor, mShadowColor);
        mShadowBackground = typedArray.getDrawable(R.styleable.SShadowLayout_ssl_background);
        typedArray.recycle();
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

    @Nullable
    protected View getOwnerView() {
        return mOwner.get();
    }

    public abstract void invalidate();

    public abstract void drawShadow(Canvas canvas);

}
