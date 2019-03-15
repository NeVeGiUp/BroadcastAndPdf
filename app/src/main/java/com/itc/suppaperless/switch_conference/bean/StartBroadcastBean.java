package com.itc.suppaperless.switch_conference.bean;

/**
 * Created by cong on 19-2-17.
 */

public class StartBroadcastBean {
    /**
     * iCmdEnum : 222
     * iUserID : 100000
     * iForceCast : 0
     */

    private int iCmdEnum;
    private int iUserID;
    private int iForceCast;

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

    public int getIForceCast() {
        return iForceCast;
    }

    public void setIForceCast(int iForceCast) {
        this.iForceCast = iForceCast;
    }
}
