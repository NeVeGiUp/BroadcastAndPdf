package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by xiaogf on 19-3-6.
 */

public class StartSignPost extends BaseBean{
    private int iTerminalID;
    private int iUserID;
    private String strUserName;
    private String strFilePath;

    public int getiTerminalID() {
        return iTerminalID;
    }

    public void setiTerminalID(int iTerminalID) {
        this.iTerminalID = iTerminalID;
    }

    public int getiUserID() {
        return iUserID;
    }

    public void setiUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrFilePath() {
        return strFilePath;
    }

    public void setStrFilePath(String strFilePath) {
        this.strFilePath = strFilePath;
    }
}
