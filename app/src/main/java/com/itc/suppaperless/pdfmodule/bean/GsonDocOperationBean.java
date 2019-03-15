package com.itc.suppaperless.pdfmodule.bean;

/**
 * Create by zhengwp on 3/1/19.
 */
public class GsonDocOperationBean extends GsonOpenDocBean{
    private int optCode;

    private int page;
    private float scale;
    private float scaleX;
    private float scaleY;
    private float screenWid;
    private float centerX;
    private float centerY;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOptCode() {
        return optCode;
    }

    public void setOptCode(int optCode) {
        this.optCode = optCode;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScreenWid() {
        return screenWid;
    }

    public void setScreenWid(float screenWid) {
        this.screenWid = screenWid;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }
}
