package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by xiaogf on 19-3-7.
 */

public class SoganList {
    private int iCmdEnum;
    private int iUpdateType;
    private int iSloganID;
    private int iOrderNo;
    private String strSloganName;
    private String strUrl;
    private boolean isHas;

    public boolean isHas() {
        return isHas;
    }

    public void setHas(boolean has) {
        isHas = has;
    }

    public int getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getiUpdateType() {
        return iUpdateType;
    }

    public void setiUpdateType(int iUpdateType) {
        this.iUpdateType = iUpdateType;
    }

    public int getiSloganID() {
        return iSloganID;
    }

    public void setiSloganID(int iSloganID) {
        this.iSloganID = iSloganID;
    }

    public int getiOrderNo() {
        return iOrderNo;
    }

    public void setiOrderNo(int iOrderNo) {
        this.iOrderNo = iOrderNo;
    }

    public String getStrSloganName() {
        return strSloganName;
    }

    public void setStrSloganName(String strSloganName) {
        this.strSloganName = strSloganName;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }
}
