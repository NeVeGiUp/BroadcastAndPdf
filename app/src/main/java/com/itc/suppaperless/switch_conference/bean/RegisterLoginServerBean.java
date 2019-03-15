package com.itc.suppaperless.switch_conference.bean;

/**
 * Created by cong on 19-1-23.
 */

public class RegisterLoginServerBean {
    /**
     * iCmdEnum : 602
     * iResult : 200
     * strErrorMsg :
     * strErrorMsgEn :
     * iTerminalID : 1
     * iMeetingRoomID : 0
     * strServerIP :
     * iServerPort : 0
     */

    private int iCmdEnum;
    private int iResult;
    private String strErrorMsg;
    private String strErrorMsgEn;
    private int iTerminalID;
    private int iMeetingRoomID;
    private String strServerIP;
    private int iServerPort;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
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

    public String getStrErrorMsgEn() {
        return strErrorMsgEn;
    }

    public void setStrErrorMsgEn(String strErrorMsgEn) {
        this.strErrorMsgEn = strErrorMsgEn;
    }

    public int getITerminalID() {
        return iTerminalID;
    }

    public void setITerminalID(int iTerminalID) {
        this.iTerminalID = iTerminalID;
    }

    public int getIMeetingRoomID() {
        return iMeetingRoomID;
    }

    public void setIMeetingRoomID(int iMeetingRoomID) {
        this.iMeetingRoomID = iMeetingRoomID;
    }

    public String getStrServerIP() {
        return strServerIP;
    }

    public void setStrServerIP(String strServerIP) {
        this.strServerIP = strServerIP;
    }

    public int getIServerPort() {
        return iServerPort;
    }

    public void setIServerPort(int iServerPort) {
        this.iServerPort = iServerPort;
    }
}
