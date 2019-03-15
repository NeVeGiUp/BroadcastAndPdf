package com.itc.suppaperless.switch_conference.bean;

/**
 * Created by cong on 19-1-22.
 */

public class RegisterServerBean {
    /**
     * iCmdEnum : 202
     * iUserID : 100009
     * iTerminalID : 100009
     * iResult : 200
     * strErrorMsg :
     */

    private int iCmdEnum;
    private int iUserID;
    private int iTerminalID;
    private int iResult;
    private String strErrorMsg;

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

    public int getITerminalID() {
        return iTerminalID;
    }

    public void setITerminalID(int iTerminalID) {
        this.iTerminalID = iTerminalID;
    }

    public int getIResult() {
        return iResult;
    }

    public void setIResult(int iResult) {
        this.iResult = iResult;
    }

    public String getStrErrorMsg() {
        return strErrorMsg;
    }

    public void setStrErrorMsg(String strErrorMsg) {
        this.strErrorMsg = strErrorMsg;
    }
}
