package com.itc.suppaperless.switch_conference.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by cong on 19-1-17.
 */

public class SendGetUserInformationBean extends BaseBean{
    private int iUserID;
    private int iTerminalID;

    public int getiUserID() {
        return iUserID;
    }

    public void setiUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public int getiTerminalID() {
        return iTerminalID;
    }

    public void setiTerminalID(int iTerminalID) {
        this.iTerminalID = iTerminalID;
    }

}
