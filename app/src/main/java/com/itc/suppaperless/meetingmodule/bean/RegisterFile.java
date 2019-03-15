package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by xiaogf on 19-1-18.
 */

public class RegisterFile extends BaseBean {
    private int iTerminalID;
    private int iTerminalType;
    public int iUserID;
    private int iMeetingID;
    private int iMeetingRoomID;
    private int iChannelType=1;

    public int getiTerminalID() {
        return iTerminalID;
    }

    public void setiTerminalID(int iTerminalID) {
        this.iTerminalID = iTerminalID;
    }

    public int getiTerminalType() {
        return iTerminalType;
    }

    public void setiTerminalType(int iTerminalType) {
        this.iTerminalType = iTerminalType;
    }

    public int getiUserID() {
        return iUserID;
    }

    public void setiUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public int getiMeetingID() {
        return iMeetingID;
    }

    public void setiMeetingID(int iMeetingID) {
        this.iMeetingID = iMeetingID;
    }

    public int getiMeetingRoomID() {
        return iMeetingRoomID;
    }

    public void setiMeetingRoomID(int iMeetingRoomID) {
        this.iMeetingRoomID = iMeetingRoomID;
    }

    public int getiChannelType() {
        return iChannelType;
    }

    public void setiChannelType(int iChannelType) {
        this.iChannelType = iChannelType;
    }
}
