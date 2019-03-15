package com.itc.suppaperless.multifunctionmodule.bean;

/**
 * Create by zhengwp on 19-2-25.
 */
public class MultiFunctionItemBean {
    private int imageId;
    private String title;

    public MultiFunctionItemBean(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
