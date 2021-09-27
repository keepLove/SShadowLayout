package com.s.android.shadowlayout.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.s.android.shadowlayout.ILayout;
import com.s.android.shadowlayout.R;

import java.lang.ref.WeakReference;


/**
 * 阴影帮助类
 */
public abstract class ShadowLayoutHelper implements ILayout {

    /**
     * Outline实现方式
     */
    private static final int SHADOW_TYPE_OUTLINE = 0;
    /**
     * padding实现方式
     */
    private static final int SHADOW_TYPE_PADDING = 1;

    /**
     * 阴影显示
     */
    public static final int FLAG_SHOWDIRECTION_MASK = 0x00001111;
    /**
     * 阴影显示，不显示
     */
    public static final int FLAG_SHOWDIRECTION_NO = 0x00000000;
    /**
     * 阴影显示，左边显示
     */
    public static final int FLAG_SHOWDIRECTION_LEFT = 0x00000001;
    /**
     * 阴影显示，上边显示
     */
    public static final int FLAG_SHOWDIRECTION_TOP = 0x00000010;
    /**
     * 阴影显示，右边显示
     */
    public static final int FLAG_SHOWDIRECTION_RIGHT = 0x00000100;
    /**
     * 阴影显示，下边显示
     */
    public static final int FLAG_SHOWDIRECTION_BOTTOM = 0x00001000;

    /**
     * 获取实现方式。
     * SDK小于21时，自动使用{@link ShadowLayoutPaddingHelper}
     * 其它根据showType判断
     */
    public static ShadowLayoutHelper getHelper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        if (!useFeature()) {
            return new ShadowLayoutPaddingHelper(context, attrs, defStyleAttr, defStyleRes, owner);
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SShadowLayout, defStyleAttr, defStyleRes);
        int type = typedArray.getInt(R.styleable.SShadowLayout_ssl_shadowType, SHADOW_TYPE_OUTLINE);
        typedArray.recycle();
        if (type == SHADOW_TYPE_PADDING) {
            return new ShadowLayoutPaddingHelper(context, attrs, defStyleAttr, defStyleRes, owner);
        }
        return new ShadowLayoutOutlineHelper(context, attrs, defStyleAttr, defStyleRes, owner);
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
    protected float mShadowAlpha = 1f;
    /**
     * 圆角半径
     */
    protected int mRadius;
    protected int mRadiusTopLeft;
    protected int mRadiusTopRight;
    protected int mRadiusBottomLeft;
    protected int mRadiusBottomRight;
    /**
     * 阴影颜色
     */
    protected int mShadowColor = 0;
    /**
     * 内容区域背景
     */
    @Nullable
    protected Drawable mShadowBackground;
    /**
     * 标识位
     */
    protected int flags = FLAG_SHOWDIRECTION_LEFT | FLAG_SHOWDIRECTION_TOP | FLAG_SHOWDIRECTION_RIGHT | FLAG_SHOWDIRECTION_BOTTOM;

    protected ShadowLayoutHelper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        mOwner = new WeakReference<>(owner);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SShadowLayout, defStyleAttr, defStyleRes);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_radius, 0);
        mRadiusTopLeft = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_radius_topLeft, mRadius);
        mRadiusTopRight = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_radius_topRight, mRadius);
        mRadiusBottomLeft = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_radius_bottomLeft, mRadius);
        mRadiusBottomRight = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_radius_bottomRight, mRadius);
        mShadowElevation = typedArray.getDimensionPixelSize(R.styleable.SShadowLayout_ssl_shadowElevation, mShadowElevation);
        mShadowAlpha = typedArray.getFloat(R.styleable.SShadowLayout_ssl_shadowAlpha, mShadowAlpha);
        mShadowColor = typedArray.getColor(R.styleable.SShadowLayout_ssl_shadowColor, mShadowColor);
        mShadowBackground = typedArray.getDrawable(R.styleable.SShadowLayout_ssl_background);
        flags = typedArray.getInt(R.styleable.SShadowLayout_ssl_showDirection, flags);
        typedArray.recycle();
        invalidate();
    }

    /**
     * 是否显示
     *
     * @param flag {@link #FLAG_SHOWDIRECTION_LEFT} {@link #FLAG_SHOWDIRECTION_TOP}
     *             {@link #FLAG_SHOWDIRECTION_RIGHT} {@link #FLAG_SHOWDIRECTION_BOTTOM}
     */
    public boolean isShowDirection(int flag) {
        return (flags & FLAG_SHOWDIRECTION_MASK & flag) != 0;
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
    public void setShadowRadius(int radius) {
        if (mRadius == radius) {
            return;
        }
        mRadius = radius;
        invalidate();
    }

    @Override
    public int getShadowRadius() {
        return mRadius;
    }

    @Override
    public void setShadowRadius(int topLeft, int topRight, int bottomLeft, int bottomRight) {
        mRadiusTopLeft = topLeft;
        mRadiusTopRight = topRight;
        mRadiusBottomLeft = bottomLeft;
        mRadiusBottomRight = bottomRight;
        invalidate();
    }

    @Override
    public void setShadowBackground(Drawable drawable) {
        mShadowBackground = drawable;
        invalidate();
    }

    @Override
    public void setShadowBackground(@ColorInt int color) {
        mShadowBackground = new ColorDrawable(color);
        invalidate();
    }

    @Override
    public void addShadowFlag(int flag) {
        flags |= flag;
        invalidate();
    }

    @Override
    public void setShadowFlags(int flags) {
        this.flags = flags;
        invalidate();
    }

    @Override
    public int getShadowFlags() {
        return flags;
    }

    @Nullable
    protected View getOwnerView() {
        return mOwner.get();
    }

    public abstract void invalidate();

    public abstract void drawShadow(Canvas canvas);

}
