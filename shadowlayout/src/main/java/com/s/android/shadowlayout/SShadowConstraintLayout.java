package com.s.android.shadowlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.s.android.shadowlayout.helper.ShadowLayoutHelper;

public class SShadowConstraintLayout extends ConstraintLayout implements ILayout {

    private ShadowLayoutHelper mLayoutHelper;

    public SShadowConstraintLayout(@NonNull Context context) {
        this(context, null);
    }

    public SShadowConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SShadowConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public SShadowConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mLayoutHelper = ShadowLayoutHelper.getHelper(context, attrs, defStyleAttr, defStyleRes, this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLayoutHelper.drawShadow(canvas);
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
    public void setShadowRadius(int radius) {
        mLayoutHelper.setShadowRadius(radius);
    }

    @Override
    public int getShadowRadius() {
        return mLayoutHelper.getShadowRadius();
    }

    @Override
    public void setShadowRadius(int topLeft, int topRight, int bottomLeft, int bottomRight) {
        mLayoutHelper.setShadowRadius(topLeft, topRight, bottomLeft, bottomRight);
    }

    @Override
    public void setShadowBackground(Drawable drawable) {
        mLayoutHelper.setShadowBackground(drawable);
    }

    @Override
    public void setShadowBackground(@ColorInt int color) {
        mLayoutHelper.setShadowBackground(color);
    }

    @Override
    public void addShadowFlag(int flag) {
        mLayoutHelper.addShadowFlag(flag);
    }

    @Override
    public void setShadowFlags(int flag) {
        mLayoutHelper.setShadowFlags(flag);
    }

    @Override
    public int getShadowFlags() {
        return mLayoutHelper.getShadowFlags();
    }

}
