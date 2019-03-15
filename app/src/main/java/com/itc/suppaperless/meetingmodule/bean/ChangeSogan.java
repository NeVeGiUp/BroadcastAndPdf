package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by xiaogf on 19-3-7.
 */

public class ChangeSogan extends BaseBean{
    private int iCurSloganID;
    private int iStatus;

    public int getiCurSloganID() {
        return iCurSloganID;
    }

    public void setiCurSloganID(int iCurSloganID) {
        this.iCurSloganID = iCurSloganID;
    }

    public int getiStatus() {
        return iStatus;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }
}
