package com.itc.suppaperless.meetingmodule.bean;

import java.util.List;

/**
 * Created by huangsm on 2017/7/7.
 */

public class MeetingSloganInfo extends BaseModel{

    /**
     * iCmdEnum : 553
     * iSloganNum : 1
     * iCurSloganID : 3
     * iTerminalStatus : 0
     * lstSlogan : [{"iSloganID":3,"iOrderNo":100,"strSloganName":"测试会议","strUrl":"http://172.16.9.217/public/htmlbanner/3/3.png"}]
     */

    private int iCmdEnum;
    private int iSloganNum;
    private int iCurSloganID;
    private int iTerminalStatus;
    private List<SoganList> lstSlogan;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getISloganNum() {
        return iSloganNum;
    }

    public void setISloganNum(int iSloganNum) {
        this.iSloganNum = iSloganNum;
    }

    public int getICurSloganID() {
        return iCurSloganID;
    }

    public void setICurSloganID(int iCurSloganID) {
        this.iCurSloganID = iCurSloganID;
    }

    public int getITerminalStatus() {
        return iTerminalStatus;
    }

    public void setITerminalStatus(int iTerminalStatus) {
        this.iTerminalStatus = iTerminalStatus;
    }

    public List<SoganList> getLstSlogan() {
        return lstSlogan;
    }

    public void setLstSlogan(List<SoganList> lstSlogan) {
        this.lstSlogan = lstSlogan;
    }

}
