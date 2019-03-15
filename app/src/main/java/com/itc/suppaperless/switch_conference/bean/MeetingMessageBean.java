package com.itc.suppaperless.switch_conference.bean;

/**
 * Created by cong on 19-3-6.
 */

public class MeetingMessageBean {
    /**
     * iCmdEnum : 226
     * iSysID : 0
     * iSendUserID : 80000
     * iMsgType : 2
     * iContentType : 0
     * strContent : 11111111111111
     * strSendTime : 2019-03-05 18:40:53
     */

    private int iCmdEnum;
    private int iSysID;
    private int iSendUserID;
    private int iMsgType;
    private int iContentType;
    private String strContent;
    private String strSendTime;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getISysID() {
        return iSysID;
    }

    public void setISysID(int iSysID) {
        this.iSysID = iSysID;
    }

    public int getISendUserID() {
        return iSendUserID;
    }

    public void setISendUserID(int iSendUserID) {
        this.iSendUserID = iSendUserID;
    }

    public int getIMsgType() {
        return iMsgType;
    }

    public void setIMsgType(int iMsgType) {
        this.iMsgType = iMsgType;
    }

    public int getIContentType() {
        return iContentType;
    }

    public void setIContentType(int iContentType) {
        this.iContentType = iContentType;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public String getStrSendTime() {
        return strSendTime;
    }

    public void setStrSendTime(String strSendTime) {
        this.strSendTime = strSendTime;
    }
}
