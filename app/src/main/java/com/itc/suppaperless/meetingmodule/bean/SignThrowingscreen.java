package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by xiaogf on 19-3-6.
 */

public class SignThrowingscreen extends BaseBean{
    private int iType;
    private int iID;
    private String  strUrl;

    public int getiType() {
        return iType;
    }

    public void setiType(int iType) {
        this.iType = iType;
    }

    public int getiID() {
        return iID;
    }

    public void setiID(int iID) {
        this.iID = iID;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }
}
