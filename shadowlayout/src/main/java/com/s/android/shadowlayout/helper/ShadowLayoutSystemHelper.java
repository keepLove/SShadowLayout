package com.s.android.shadowlayout.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.s.android.shadowlayout.ILayout;

/**
 * 阴影帮助类
 */
@TargetApi(21)
class ShadowLayoutSystemHelper extends ShadowLayoutHelper implements ILayout {

    ShadowLayoutSystemHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        super(context, attrs, defAttr, owner);
        owner.setOutlineProvider(new ViewOutlineProvider() {

            @Override
            public void getOutline(View view, Outline outline) {
                int w = view.getWidth(), h = view.getHeight();
                if (w == 0 || h == 0) {
                    return;
                }
                int top = 0, bottom = Math.max(top + 1, h), left = 0;
                outline.setAlpha(mShadowAlpha);
                if (mRadius <= 0) {
                    outline.setRect(left, top, w, bottom);
                } else {
                    outline.setRoundRect(left, top, w, bottom, mRadius);
                }
            }
        });
    }

    @Override
    public void invalidate() {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        owner.setClipToOutline(mRadius > 0);
        owner.setElevation(mShadowElevation);
        owner.invalidateOutline();
        owner.invalidate();
    }

    @Override
    public void drawShadow(Canvas canvas) {
        //
    }

}
