package com.s.android.shadowlayout;

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
    void setRadius(int radius);

    /**
     * get the layout radius
     */
    int getRadius();

}
