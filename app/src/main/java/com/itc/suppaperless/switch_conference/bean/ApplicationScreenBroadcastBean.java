package com.itc.suppaperless.switch_conference.bean;

/**
 * Created by cong on 19-2-16.
 */

public class ApplicationScreenBroadcastBean {
    /**
     * iCmdEnum : 220
     * iUserID : 100001
     * iResult : 0
     * strResultInfo :
     */

    private int iCmdEnum;
    private int iUserID;
    private int iResult;
    private String strResultInfo;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIUserID() {
        return iUserID;
    }

    public void setIUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public int getIResult() {
        return iResult;
    }

    public void setIResult(int iResult) {
        this.iResult = iResult;
    }

    public String getStrResultInfo() {
        return strResultInfo;
    }

    public void setStrResultInfo(String strResultInfo) {
        this.strResultInfo = strResultInfo;
    }
}
