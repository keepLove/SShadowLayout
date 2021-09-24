package com.s.android.shadowlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.s.android.shadowlayout.helper.ShadowLayoutHelper;

public class SShadowFrameLayout extends FrameLayout implements ILayout {

    private ShadowLayoutHelper mLayoutHelper;

    public SShadowFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public SShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public SShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mLayoutHelper = ShadowLayoutHelper.getHelper(context, attrs, defStyleAttr, defStyleRes, this);
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
