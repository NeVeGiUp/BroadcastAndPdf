package com.itc.suppaperless.meetingmodule.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzl on 2016/9/26.
 */
public class JiaoLiuUserInfo implements Serializable {

    private int iCmdEnum;
    private int iUserNum;
    /**
     * iUserID : 1
     * iTerminalID : 11
     * strTerminalName : 11
     * strUserName : 系统管理员
     * strCompany : 广州保伦电子科技有限公司
     * strPost : 小领导
     * iUserStatus : 0
     */

    private List<LstUserBean> lstUser = new ArrayList<>();

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIUserNum() {
        return iUserNum;
    }

    public void setIUserNum(int iUserNum) {
        this.iUserNum = iUserNum;
    }

    public List<LstUserBean> getLstUser() {
        return lstUser;
    }

    public void setLstUser(List<LstUserBean> lstUser) {
        this.lstUser = lstUser;
    }

    public static class LstUserBean implements Comparable<LstUserBean>, Serializable {

        public LstUserBean(int iUserID, int iTerminalID, String strTerminalName, String strUserName, String strCompany
                           , String strPost, int iUserStatus, String strSignTime, boolean isCheckContactsSymbol, int iIsChairMan,
                           int iIsSecretary
        ){
         this.iUserID = iUserID;
         this.iTerminalID = iTerminalID;
         this.strUserName = strUserName;
         this.strCompany = strCompany;
         this.strPost = strPost;
         this.iUserStatus = iUserStatus;
         this.strSignTime = strSignTime;
         this.isCheckContactsSymbol = isCheckContactsSymbol;
         this.iIsChairMan = iIsChairMan;
         this.iIsSecretary =iIsSecretary;

        }

        private int iUserID;
        private int iTerminalID;
        private int iTerminalType;
        private String strUserName;
        private String strCompany;
        private String strPost;
        private int iUserStatus;
        private String strSignTime;
        private boolean isCheckContactsSymbol;
        private int iIsChairMan;
        private int iIsSecretary;
        private int iSpeakstatus;
        private int iScreenBroadcast;
        private boolean isSeclet;

        public boolean isSeclet() {
            return isSeclet;
        }

        public void setSeclet(boolean seclet) {
            isSeclet = seclet;
        }

        public int getiScreenBroadcast() {
            return iScreenBroadcast;
        }


        public void setiScreenBroadcast(int iScreenBroadcast) {
            this.iScreenBroadcast = iScreenBroadcast;
        }

        public int getiSpeakstatus() {
            return iSpeakstatus;
        }

        public void setiSpeakstatus(int iSpeakstatus) {
            this.iSpeakstatus = iSpeakstatus;
        }

        public String getStrSignTime() {
            return strSignTime;
        }

        public void setStrSignTime(String strSignTime) {
            this.strSignTime = strSignTime;
        }

        public LstUserBean(int iUserID, String strUserName) {
            this.iUserID = iUserID;
            this.strUserName = strUserName;
        }

        public int getIUserID() {
            return iUserID;
        }

        public void setIUserID(int iUserID) {
            this.iUserID = iUserID;
        }

        public int getITerminalID() {
            return iTerminalID;
        }

        public void setITerminalID(int iTerminalID) {
            this.iTerminalID = iTerminalID;
        }

        public int getiTerminalType() {
            return iTerminalType;
        }

        public void setiTerminalType(int iTerminalType) {
            this.iTerminalType = iTerminalType;
        }

        public String getStrUserName() {
            return strUserName;
        }

        public void setStrUserName(String strUserName) {
            this.strUserName = strUserName;
        }

        public boolean getCheckContactsSymbol() {
            return isCheckContactsSymbol;
        }

        public void setCheckContactsSymbol(boolean checkContactsSymbol) {
            isCheckContactsSymbol = checkContactsSymbol;
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

        public int getIUserStatus() {
            return iUserStatus;
        }

        public void setIUserStatus(int iUserStatus) {
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

        @Override

        public int compareTo(LstUserBean o){
            if(this.iTerminalID < o.iTerminalID){
                return -1;
            }else if(this.iTerminalID == o.iTerminalID){
                return 0;
            }else {
                return 1;
            }
        }
    }
}