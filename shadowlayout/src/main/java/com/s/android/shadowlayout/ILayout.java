package com.s.android.shadowlayout;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

public interface ILayout {

    /**
     * See {@link android.view.View#setElevation(float)}
     */
    void setShadowElevation(int elevation);

    int getShadowElevation();

    /**
     * set the outline alpha, which will change the shadow
     */
    void setShadowAlpha(float shadowAlpha);

    /**
     * get the outline alpha we set
     */
    float getShadowAlpha();

    /**
     * set the layout radius
     */
    void setShadowRadius(int radius);

    /**
     * get the layout radius
     */
    int getShadowRadius();

    /**
     * set the layout radius
     */
    void setShadowRadius(int topLeft, int topRight, int bottomLeft, int bottomRight);

    void setShadowBackground(Drawable drawable);

    void setShadowBackground(@ColorInt int color);

    void addShadowFlag(int flag);

    void setShadowFlags(int flag);

    int getShadowFlags();

}
