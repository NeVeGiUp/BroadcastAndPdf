package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by xiaogf on 19-3-5.
 */

public class GetStartOrEndSign {
    private int iCmdEnum;
    private int iControlType;
    private String strTime;

    public int getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getiControlType() {
        return iControlType;
    }

    public void setiControlType(int iControlType) {
        this.iControlType = iControlType;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }
}
