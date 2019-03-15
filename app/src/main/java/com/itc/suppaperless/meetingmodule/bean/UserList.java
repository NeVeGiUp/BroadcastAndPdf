package com.itc.suppaperless.meetingmodule.bean;

import java.util.List;

/**
 * Created by lik on 2018/8/20.
 * 参会人员列表
 */

public class UserList {

    private int iCmdEnum;
    private int iUserNum;
    private List<LstUser> lstUser;

    public int getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getiUserNum() {
        return iUserNum;
    }

    public void setiUserNum(int iUserNum) {
        this.iUserNum = iUserNum;
    }

    public List<LstUser> getLstUser() {
        return lstUser;
    }

    public void setLstUser(List<LstUser> lstUser) {
        this.lstUser = lstUser;
    }

    public class LstUser {
        private int iUserID;
        private int iTerminalID;
        private int iTerminalType;
        private int iUserStatus;
        private int iIsChairMan;
        private int iIsSecretary;
        private String strTerminalName;
        private String strUserName;
        private String strCompany;
        private String strPost;

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

        public int getiTerminalType() {
            return iTerminalType;
        }

        public void setiTerminalType(int iTerminalType) {
            this.iTerminalType = iTerminalType;
        }

        public int getiUserStatus() {
            return iUserStatus;
        }

        public void setiUserStatus(int iUserStatus) {
            this.iUserStatus = iUserStatus;
        }

        public int getiIsChairMan() {
            return iIsChairMan;
        }

        public void setiIsChairMan(int iIsChairMan) {
            this.iIsChairMan = iIsChairMan;
        }

        public int getiIsSecretary() {
            return iIsSecretary;
        }

        public void setiIsSecretary(int iIsSecretary) {
            this.iIsSecretary = iIsSecretary;
        }

        public String getStrTerminalName() {
            return strTerminalName;
        }

        public void setStrTerminalName(String strTerminalName) {
            this.strTerminalName = strTerminalName;
        }

        public String getStrUserName() {
            return strUserName;
        }

        public void setStrUserName(String strUserName) {
            this.strUserName = strUserName;
        }

        public String getStrCompany() {
            return strCompany;
        }

        public void setStrCompany(String strCompany) {
            this.strCompany = strCompany;
        }

        public String getStrPost() {
            return strPost;
        }

        public void setStrPost(String strPost) {
            this.strPost = strPost;
        }
    }

}
