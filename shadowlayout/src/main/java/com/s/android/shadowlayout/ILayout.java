package com.s.android.shadowlayout;

public interface ILayout {

    /**
     * limit the width of a layout
     */
    boolean setWidthLimit(int widthLimit);

    /**
     * limit the height of a layout
     */
    boolean setHeightLimit(int heightLimit);

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

    /**
     * this method will determine the radius and shadow.
     */
    void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha);

}
