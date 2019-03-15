package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by xiaogf on 19-3-8.
 */

public class DoLive {
    private int iCmdEnum;
    private String strUrl;
    private int iStatus;

    public int getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }

    public int getiStatus() {
        return iStatus;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }
}
