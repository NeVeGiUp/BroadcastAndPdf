package com.itc.suppaperless.meetingmodule.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzl on 2016/10/14.
 */

public class ServiceSendMeetingInfo implements Serializable {


    /**
     * iCmdEnum : 207
     * iMeetingID : 2
     * iChairmanID : 4
     * iChairmanTerminalID : 4
     * strName : 安卓测试专用会议
     * iType : 0
     * strSlogan : http://192.168.17.138/b/chart/slogan?mid=2
     * strStartTime : 2016-10-20 10:01:50
     * strEndTime : 2016-10-20 10:01:51
     * strEnableTime : 2016-10-20 10:05:03
     * strFileServerIP : 192.168.17.138
     * iFileServerPort : 8360
     * lstMeetAbility : [{"iAbilityID":1,"strAbilityName":"agenda"},{"iAbilityID":2,"strAbilityName":"vote"},{"iAbilityID":3,"strAbilityName":"interflow"},{"iAbilityID":4,"strAbilityName":"summary"},{"iAbilityID":5,"strAbilityName":"stmpfile"},{"iAbilityID":6,"strAbilityName":"service"},{"iAbilityID":7,"strAbilityName":"broadcast"},{"iAbilityID":8,"strAbilityName":"async"},{"iAbilityID":9,"strAbilityName":"whiteboard"}]
     */

    private int iCmdEnum;
    private int iMeetingID;
    private int iChairmanID;
//    private int iChairmanTerminalID;
    private String strName;
    private int iType;
    private int iMeetingGroupID; //会议组id
    private String strMeetingGroupName; //会议组name
    private String strMeetingRoomName;
    private String strSlogan;
    private String strStartTime;
    private String strEndTime;
    private String strEnableTime;
    private String strFileServerIP;
    private int iFileServerPort;
    /**
     * iAbilityID : 1
     * strAbilityName : agenda
     */

    private List<LstMeetAbilityBean> lstMeetAbility;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIMeetingID() {
        return iMeetingID;
    }

    public void setIMeetingID(int iMeetingID) {
        this.iMeetingID = iMeetingID;
    }

    public int getIChairmanID() {
        return iChairmanID;
    }

    public void setIChairmanID(int iChairmanID) {
        this.iChairmanID = iChairmanID;
    }

    public int getiMeetingGroupID() {
        return iMeetingGroupID;
    }

    public void setiMeetingGroupID(int iMeetingGroupID) {
        this.iMeetingGroupID = iMeetingGroupID;
    }

    public String getStrMeetingGroupName() {
        return strMeetingGroupName;
    }

    public void setStrMeetingGroupName(String strMeetingGroupName) {
        this.strMeetingGroupName = strMeetingGroupName;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public int getIType() {
        return iType;
    }

    public void setIType(int iType) {
        this.iType = iType;
    }

    public String getStrMeetingRoomName() {
        return strMeetingRoomName;
    }

    public void setStrMeetingRoomName(String strMeetingRoomName) {
        this.strMeetingRoomName = strMeetingRoomName;
    }

    public String getStrSlogan() {
        return strSlogan;
    }

    public void setStrSlogan(String strSlogan) {
        this.strSlogan = strSlogan;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }

    public String getStrEnableTime() {
        return strEnableTime;
    }

    public void setStrEnableTime(String strEnableTime) {
        this.strEnableTime = strEnableTime;
    }

    public String getStrFileServerIP() {
        return strFileServerIP;
    }

    public void setStrFileServerIP(String strFileServerIP) {
        this.strFileServerIP = strFileServerIP;
    }

    public int getIFileServerPort() {
        return iFileServerPort;
    }

    public void setIFileServerPort(int iFileServerPort) {
        this.iFileServerPort = iFileServerPort;
    }

    public List<LstMeetAbilityBean> getLstMeetAbility() {
        return lstMeetAbility;
    }

    public void setLstMeetAbility(List<LstMeetAbilityBean> lstMeetAbility) {
        this.lstMeetAbility = lstMeetAbility;
    }

    public static class LstMeetAbilityBean {
        private int iAbilityID;
        private String strAbilityName;

        public int getIAbilityID() {
            return iAbilityID;
        }

        public void setIAbilityID(int iAbilityID) {
            this.iAbilityID = iAbilityID;
        }

        public String getStrAbilityName() {
            return strAbilityName;
        }

        public void setStrAbilityName(String strAbilityName) {
            this.strAbilityName = strAbilityName;
        }
    }
}
