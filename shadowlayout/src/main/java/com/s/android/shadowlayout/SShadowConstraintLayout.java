package com.s.android.shadowlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.s.android.shadowlayout.helper.ShadowLayoutHelper;

public class SShadowConstraintLayout extends ConstraintLayout implements ILayout {

    private ShadowLayoutHelper mLayoutHelper;

    public SShadowConstraintLayout(Context context) {
        this(context, null);
    }

    public SShadowConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SShadowConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mLayoutHelper = ShadowLayoutHelper.getHelper(context, attrs, defStyleAttr, this);
    }

    @Override
    public void setRadius(int radius) {
        mLayoutHelper.setRadius(radius);
    }

    @Override
    public int getRadius() {
        return mLayoutHelper.getRadius();
    }

    @Override
    public void setShadowElevation(int elevation) {
        mLayoutHelper.setShadowElevation(elevation);
    }

    @Override
    public int getShadowElevation() {
        return mLayoutHelper.getShadowElevation();
    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {
        mLayoutHelper.setShadowAlpha(shadowAlpha);
    }

    @Override
    public float getShadowAlpha() {
        return mLayoutHelper.getShadowAlpha();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLayoutHelper.drawShadow(canvas);
    }

}
