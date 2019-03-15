package com.itc.suppaperless.pdfmodule.bean;

/**
 * Create by zhengwp on 19-3-4.
 * The object of point which the type is 'double', but the target is 'float' we wished.
 * Java自带的 Point 数据为双精度, 目标类型float.
 */
public class PointsBean {
    private float pointX;
    private float pointY;

    public PointsBean(float pointX, float pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public float getPointX() {
        return pointX;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }
}
