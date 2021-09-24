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

import com.s.android.shadowlayout.ILayout;


/**
 * 阴影帮助类
 */
class ShadowLayoutPaddingHelper extends ShadowLayoutHelper implements ILayout {

    private final Paint shadowPaint;
    private final RectF shadowRect;
    private final Rect backgroundRect;
    private final Path shadowPath;

    ShadowLayoutPaddingHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        super(context, attrs, defAttr, owner);
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
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        owner.invalidate();
    }

    @Override
    public void drawShadow(Canvas canvas) {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        int shadowColor = mShadowColor;
        if (Color.alpha(shadowColor) == 255) {
            if (mShadowAlpha != 1) {
                shadowColor = Color.argb((int) (mShadowAlpha * 255.0f + 0.5f), Color.red(mShadowColor), Color.green(mShadowColor), Color.blue(mShadowColor));
            }
        }
        shadowPaint.setColor(shadowColor);
        shadowPaint.setShadowLayer(mShadowElevation, 0, 0, shadowColor);

        shadowRect.left = backgroundRect.left = mShadowElevation;
        shadowRect.top = backgroundRect.top = mShadowElevation;
        shadowRect.right = backgroundRect.right = owner.getWidth() - mShadowElevation;
        shadowRect.bottom = backgroundRect.bottom = owner.getHeight() - mShadowElevation;
        shadowPath.reset();
        shadowPath.addRoundRect(shadowRect, new float[]{
                mRadius, mRadius,
                mRadius, mRadius,
                mRadius, mRadius,
                mRadius, mRadius
        }, Path.Direction.CW);
        canvas.drawPath(shadowPath, shadowPaint);
        // 背景
        if (mShadowBackground != null) {
            int count = canvas.save();
            canvas.clipPath(shadowPath);
            mShadowBackground.setBounds(backgroundRect);
            mShadowBackground.draw(canvas);
            canvas.restoreToCount(count);
        }
    }

}
