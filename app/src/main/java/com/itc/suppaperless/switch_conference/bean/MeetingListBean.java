package com.itc.suppaperless.switch_conference.bean;

import java.util.List;

/**
 * Created by cong on 19-1-18.
 */

public class MeetingListBean {

    /**
     * iCmdEnum : 608
     * iResult : 200
     * strErrorMsg :
     * lstMeeting : [{"iMeetingID":100008,"iMeetingRoomID":1,"strMeetingRoomName":"测试会议室","strServerIP":"172.16.12.123","iServerPort":7350,"strMeetingName":"啊啊啊啊啊啊啊灌灌灌灌灌过斤斤计较军军军军军军经济技术啦啦啦啦啦绿绿绿绿绿绿绿凄","strMeetingDiscribe":"大家聚聚过或或付付付付付付付付付付付付付付付付付付付付付付哈卡扩扩扩扩ID号塑化剂的好成绩的京东方和等级划分说的就是拉大锯空间撒点卡时间段老司机掉萨芬是拿到客户经理非叫我IE偶然附近的开放式基金储蓄卡绝对是离开了地面上的到交付考虑到法律的方法歌姬计划的好帮手的汉武帝Nike还想着呢我回家当时记得是程度就回房间的复合地基好解放后的基金会的健康复合地基思考接电话绝地反击额度打防结合多军错多多多多多多","iTerminalID":0,"strStartTime":"2019-01-07 16:50:01","strEndTime":"2019-01-07 16:50:02","iStatus":1,"iUserID":100009,"strUserName":"丹青生","iLogonMode":1,"strUserAccount":"test10"},{"iMeetingID":100004,"iMeetingRoomID":2,"strMeetingRoomName":"chenkk","strServerIP":"172.16.12.123","iServerPort":7351,"strMeetingName":"chenkk人民代表高级大会~","strMeetingDiscribe":"人民的希望，祖国的花朵！","iTerminalID":0,"strStartTime":"2019-01-04 15:11:00","strEndTime":"2019-01-04 15:11:01","iStatus":1,"iUserID":100009,"strUserName":"丹青生","iLogonMode":1,"strUserAccount":"test10"}]
     */

    private int iCmdEnum;
    private int iResult;
    private String strErrorMsg;
    private List<LstMeetingBean> lstMeeting;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIResult() {
        return iResult;
    }

    public void setIResult(int iResult) {
        this.iResult = iResult;
    }

    public String getStrErrorMsg() {
        return strErrorMsg;
    }

    public void setStrErrorMsg(String strErrorMsg) {
        this.strErrorMsg = strErrorMsg;
    }

    public List<LstMeetingBean> getLstMeeting() {
        return lstMeeting;
    }

    public void setLstMeeting(List<LstMeetingBean> lstMeeting) {
        this.lstMeeting = lstMeeting;
    }

    public static class LstMeetingBean {
        /**
         * iMeetingID : 100008
         * iMeetingRoomID : 1
         * strMeetingRoomName : 测试会议室
         * strServerIP : 172.16.12.123
         * iServerPort : 7350
         * strMeetingName : 啊啊啊啊啊啊啊灌灌灌灌灌过斤斤计较军军军军军军经济技术啦啦啦啦啦绿绿绿绿绿绿绿凄
         * strMeetingDiscribe : 大家聚聚过或或付付付付付付付付付付付付付付付付付付付付付付哈卡扩扩扩扩ID号塑化剂的好成绩的京东方和等级划分说的就是拉大锯空间撒点卡时间段老司机掉萨芬是拿到客户经理非叫我IE偶然附近的开放式基金储蓄卡绝对是离开了地面上的到交付考虑到法律的方法歌姬计划的好帮手的汉武帝Nike还想着呢我回家当时记得是程度就回房间的复合地基好解放后的基金会的健康复合地基思考接电话绝地反击额度打防结合多军错多多多多多多
         * iTerminalID : 0
         * strStartTime : 2019-01-07 16:50:01
         * strEndTime : 2019-01-07 16:50:02
         * iStatus : 1
         * iUserID : 100009
         * strUserName : 丹青生
         * iLogonMode : 1
         * strUserAccount : test10
         */

        private int iMeetingID;
        private int iMeetingRoomID;
        private String strMeetingRoomName;
        private String strServerIP;
        private int iServerPort;
        private String strMeetingName;
        private String strMeetingDiscribe;
        private int iTerminalID;
        private String strStartTime;
        private String strEndTime;
        private int iStatus;
        private int iUserID;
        private String strUserName;
        private int iLogonMode;
        private String strUserAccount;

        public int getIMeetingID() {
            return iMeetingID;
        }

        public void setIMeetingID(int iMeetingID) {
            this.iMeetingID = iMeetingID;
        }

        public int getIMeetingRoomID() {
            return iMeetingRoomID;
        }

        public void setIMeetingRoomID(int iMeetingRoomID) {
            this.iMeetingRoomID = iMeetingRoomID;
        }

        public String getStrMeetingRoomName() {
            return strMeetingRoomName;
        }

        public void setStrMeetingRoomName(String strMeetingRoomName) {
            this.strMeetingRoomName = strMeetingRoomName;
        }

        public String getStrServerIP() {
            return strServerIP;
        }

        public void setStrServerIP(String strServerIP) {
            this.strServerIP = strServerIP;
        }

        public int getIServerPort() {
            return iServerPort;
        }

        public void setIServerPort(int iServerPort) {
            this.iServerPort = iServerPort;
        }

        public String getStrMeetingName() {
            return strMeetingName;
        }

        public void setStrMeetingName(String strMeetingName) {
            this.strMeetingName = strMeetingName;
        }

        public String getStrMeetingDiscribe() {
            return strMeetingDiscribe;
        }

        public void setStrMeetingDiscribe(String strMeetingDiscribe) {
            this.strMeetingDiscribe = strMeetingDiscribe;
        }

        public int getITerminalID() {
            return iTerminalID;
        }

        public void setITerminalID(int iTerminalID) {
            this.iTerminalID = iTerminalID;
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

        public int getIStatus() {
            return iStatus;
        }

        public void setIStatus(int iStatus) {
            this.iStatus = iStatus;
        }

        public int getIUserID() {
            return iUserID;
        }

        public void setIUserID(int iUserID) {
            this.iUserID = iUserID;
        }

        public String getStrUserName() {
            return strUserName;
        }

        public void setStrUserName(String strUserName) {
            this.strUserName = strUserName;
        }

        public int getILogonMode() {
            return iLogonMode;
        }

        public void setILogonMode(int iLogonMode) {
            this.iLogonMode = iLogonMode;
        }

        public String getStrUserAccount() {
            return strUserAccount;
        }

        public void setStrUserAccount(String strUserAccount) {
            this.strUserAccount = strUserAccount;
        }
    }
}
