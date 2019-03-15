package com.itc.suppaperless.switch_conference.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by cong on 19-1-17.
 */

public class SendRegisterServerBean extends BaseBean{
    private int iUserID;
    private int iMeetingID;
    private int iMeetingRoomID;
    private int iChannelType;
    private int iTerminalType;
    private int iTerminalID;

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

    public int getiTerminalType() {
        return iTerminalType;
    }

    public void setiTerminalType(int iTerminalType) {
        this.iTerminalType = iTerminalType;
    }

    public int getiTerminalID() {
        return iTerminalID;
    }

    public void setiTerminalID(int iTerminalID) {
        this.iTerminalID = iTerminalID;
    }

}
