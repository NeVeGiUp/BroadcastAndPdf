package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by zhangzl on 2016/10/19.
 */

public class PresideSignInfo extends BaseModel{
    private int iCmdEnum;
    private int iSysID;
    private int[] aiUserID;
//    private String strUserName;
    private String strTime;

    public int getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int[] getiUserID() {
        return aiUserID;
    }

    public void setiUserID(int[] aiUserID) {
        this.aiUserID = aiUserID;
    }

//    public String getStrUserName() {
//        return strUserName;
//    }
//
//    public void setStrUserName(String strUserName) {
//        this.strUserName = strUserName;
//    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }
}
