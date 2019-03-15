package com.itc.suppaperless.loginmodule.bean;

/**
 * Created by xiaogf on 19-2-16.
 */

public class FingerLoginResult {
    private int iResult;
    private String strMsg;
    private int iUserID;
    private int strGUID;

    public int getiResult() {
        return iResult;
    }

    public void setiResult(int iResult) {
        this.iResult = iResult;
    }

    public String getStrMsg() {
        return strMsg;
    }

    public void setStrMsg(String strMsg) {
        this.strMsg = strMsg;
    }

    public int getiUserID() {
        return iUserID;
    }

    public void setiUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public int getStrGUID() {
        return strGUID;
    }

    public void setStrGUID(int strGUID) {
        this.strGUID = strGUID;
    }
}
