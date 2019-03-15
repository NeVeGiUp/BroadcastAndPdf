package com.itc.suppaperless.switch_conference.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by cong on 19-2-16.
 */

public class ApplicationScreenBroadcast extends BaseBean {
    private int iUserID;
    private int iForceCast;
    private int iToAll;

    public int getiUserID() {
        return iUserID;
    }

    public void setiUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public int getiForceCast() {
        return iForceCast;
    }

    public void setiForceCast(int iForceCast) {
        this.iForceCast = iForceCast;
    }

    public int getiToAll() {
        return iToAll;
    }

    public void setiToAll(int iToAll) {
        this.iToAll = iToAll;
    }
}
