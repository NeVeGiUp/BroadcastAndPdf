package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by xiaogf on 19-3-7.
 */

public class Sogan {
    private int iCmdEnum;
    private int iCurSloganID;
    private  int iStatus;
    private String strUrl;

    public int getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getiCurSloganID() {
        return iCurSloganID;
    }

    public void setiCurSloganID(int iCurSloganID) {
        this.iCurSloganID = iCurSloganID;
    }

    public int getiStatus() {
        return iStatus;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }
}
