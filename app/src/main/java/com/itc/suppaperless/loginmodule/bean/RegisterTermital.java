package com.itc.suppaperless.loginmodule.bean;

import com.itc.suppaperless.base.BaseBean;


/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-14 下午7:00
 * @ desc   : 注册终端信息
 */
public class RegisterTermital extends BaseBean {

    private int iTerminalID;
    private int iRoomID;
    private int iTerminalType;
    private int strGUID;

    public int getiTerminalID() {
        return iTerminalID;
    }

    public void setiTerminalID(int iTerminalID) {
        this.iTerminalID = iTerminalID;
    }

    public int getiRoomID() {
        return iRoomID;
    }

    public void setiRoomID(int iRoomID) {
        this.iRoomID = iRoomID;
    }

    public int getiTerminalType() {
        return iTerminalType;
    }

    public void setiTerminalType(int iTerminalType) {
        this.iTerminalType = iTerminalType;
    }

    public int getStrGUID() {
        return strGUID;
    }

    public void setStrGUID(int strGUID) {
        this.strGUID = strGUID;
    }

}
