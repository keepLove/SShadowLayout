package com.s.android.shadowlayout.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.s.android.shadowlayout.ILayout;


/**
 * 阴影帮助类
 */
class ShadowLayoutPaddingHelper extends ShadowLayoutHelper implements ILayout {

    private final Paint shadowPaint;
    private final RectF shadowRect;
    private final Rect backgroundRect;
    private final Path shadowPath;

    ShadowLayoutPaddingHelper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, View owner) {
        super(context, attrs, defStyleAttr, defStyleRes, owner);
        owner.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        owner.setWillNotDraw(false);

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowRect = new RectF();
        shadowPath = new Path();
        backgroundRect = new Rect();
    }

    @Override
    public void invalidate() {
        View owner = getOwnerView();
        if (owner == null) {
            return;
        }
        owner.invalidate();
    }

    @Override
    public void drawShadow(Canvas canvas) {
        View owner = getOwnerView();
        if (owner == null) {
            return;
        }
        int shadowColor = convertShadowColor();
        shadowPaint.setColor(shadowColor);
        shadowPaint.setShadowLayer(mShadowElevation, 0, 0, shadowColor);
        // 设置Rect和Path
        settingRect(owner);
        settingPath();
        // 阴影
        canvas.drawPath(shadowPath, shadowPaint);
        // 背景
        drawBackground(canvas);
    }

    /**
     * 设置Rect
     */
    private void settingRect(View owner) {
        int left, top, right, bottom;
        if (isShowDirection(FLAG_SHOWDIRECTION_NO)) {
            left = top = right = bottom = 0;
        } else {
            left = isShowDirection(FLAG_SHOWDIRECTION_LEFT) ? mShadowElevation : 0;
            top = isShowDirection(FLAG_SHOWDIRECTION_TOP) ? mShadowElevation : 0;
            right = isShowDirection(FLAG_SHOWDIRECTION_RIGHT) ? mShadowElevation : 0;
            bottom = isShowDirection(FLAG_SHOWDIRECTION_BOTTOM) ? mShadowElevation : 0;
        }
        shadowRect.left = backgroundRect.left = left;
        shadowRect.top = backgroundRect.top = top;
        shadowRect.right = backgroundRect.right = owner.getWidth() - right;
        shadowRect.bottom = backgroundRect.bottom = owner.getHeight() - bottom;
    }

    /**
     * 设置Path
     */
    private void settingPath() {
        shadowPath.reset();
        shadowPath.addRoundRect(shadowRect, new float[]{
                mRadiusTopLeft, mRadiusTopLeft, mRadiusTopRight, mRadiusTopRight,
                mRadiusBottomRight, mRadiusBottomRight, mRadiusBottomLeft, mRadiusBottomLeft
        }, Path.Direction.CW);
    }

    /**
     * 绘制背景
     */
    private void drawBackground(Canvas canvas) {
        if (mShadowBackground != null) {
            int count = canvas.save();
            canvas.clipPath(shadowPath);
            mShadowBackground.setBounds(backgroundRect);
            mShadowBackground.draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    /**
     * 获取阴影颜色。
     * 1，如果阴影颜色有透明度，直接使用。
     * 2，如果没有透明度，则使用mShadowAlpha透明度
     */
    private int convertShadowColor() {
        int shadowColor = mShadowColor;
        if (Color.alpha(shadowColor) == 255) {
            if (mShadowAlpha != 1) {
                shadowColor = Color.argb((int) (mShadowAlpha * 255.0f + 0.5f), Color.red(mShadowColor), Color.green(mShadowColor), Color.blue(mShadowColor));
            }
        }
        return shadowColor;
    }

}
