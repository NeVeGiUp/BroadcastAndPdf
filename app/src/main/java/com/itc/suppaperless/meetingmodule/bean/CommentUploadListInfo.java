package com.itc.suppaperless.meetingmodule.bean;

import android.support.annotation.NonNull;


import com.itc.suppaperless.utils.TimeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangzl on 2016/9/23.
 */
public class CommentUploadListInfo implements Serializable {

    private int iCmdEnum;

    private int iFileNum;

    private List<LstFileBean> lstFileInfo = new ArrayList<>();
//    private List<LstFileBean> lstFile = new ArrayList<>();

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIFileNum() {
        return iFileNum;
    }

    public void setIFileNum(int iFileNum) {
        this.iFileNum = iFileNum;
    }

    public List<LstFileBean> getLstFile() {
        return lstFileInfo;
    }

    public void setLstFile(List<LstFileBean> lstFileInfo) {
        this.lstFileInfo = lstFileInfo;
    }

    public static class LstFileBean implements Comparable<LstFileBean>, Serializable {

        public LstFileBean(int iUpdateType, int iID, int iOrderNo, String strName,
                           String strPath, String strPathEx, String strTime, int iSize, int iOwnID,
                           int iType, int iModuleID, int iAllowDownload, int iParentDirID, int iIsDir,
                           int isDown, long totelprogress, long currentprogress, int iStartDatum, int[] aiUserID,int fileNum) {
            this.iUpdateType = iUpdateType;
            this.iID = iID;
            this.iOrderNo = iOrderNo;
            this.strName = strName;
            this.strPath = strPath;
            this.strPathEx = strPathEx;
            this.strTime = strTime;
            this.iSize = iSize;
            this.iOwnID = iOwnID;
            this.iType = iType;
            this.iModuleID = iModuleID;
            this.iAllowDownload = iAllowDownload;
            this.iParentDirID = iParentDirID;
            this.iIsDir = iIsDir;
            this.isDown = isDown;
            this.totelprogress = totelprogress;
            this.currentprogress = currentprogress;
            this.aiUserID = aiUserID;
            this.iStartDatum = iStartDatum;
            this.fileNum=fileNum;
        }

        private int iUpdateType;
        private int iID;
        private int iOrderNo;
        private String strName;
        private String strPath;
        private String strPathEx;
        private String strTime;
        private int iSize;
        private int iOwnID;
        private int iType;
        private int iModuleID; //归属的议题
        private int iAllowDownload;
        private int iStartDatum;
        private int iParentDirID;
        private int iIsDir;
        private int[] aiUserID;
        private int isDown;
        private long totelprogress;
        private long currentprogress;
        private boolean iIsShowProgress;
        private int fileNum;
        private int meetingID;
        private String ipAndPort;

        public int getMeetingID() {
            return meetingID;
        }

        public void setMeetingID(int meetingID) {
            this.meetingID = meetingID;
        }

        public String getIpAndPort() {
            return ipAndPort;
        }

        public void setIpAndPort(String ipAndPort) {
            this.ipAndPort = ipAndPort;
        }

        public int getFileNum() {
            return fileNum;
        }

        public void setFileNum(int fileNum) {
            this.fileNum = fileNum;
        }

        public long getTotelprogress() {
            return totelprogress;
        }

        public void setTotelprogress(long totelprogress) {
            this.totelprogress = totelprogress;
        }

        public long getCurrentprogress() {
            return currentprogress;
        }

        public void setCurrentprogress(long currentprogress) {
            this.currentprogress = currentprogress;
        }

        public boolean getiIsShowProgress() {
            return iIsShowProgress;
        }

        public void setiIsShowProgress(boolean iIsShowProgress) {
            this.iIsShowProgress = iIsShowProgress;
        }

        public int getiUpdateType() {
            return iUpdateType;
        }

        public void setiUpdateType(int iUpdateType) {
            this.iUpdateType = iUpdateType;
        }


        public int getiID() {
            return iID;
        }

        public void setiID(int iID) {
            this.iID = iID;
        }

        public int getiStartDatum() {
            return iStartDatum;
        }

        public void setiStartDatum(int iStartDatum) {
            this.iStartDatum = iStartDatum;
        }

        public int[] getAiUserID() {
            return aiUserID;
        }

        public void setAiUserID(int[] aiUserID) {
            this.aiUserID = aiUserID;
        }

        public int getiOrderNo() {
            return iOrderNo;
        }

        public void setiOrderNo(int iOrderNo) {
            this.iOrderNo = iOrderNo;
        }

        public String getStrName() {
            return strName;
        }

        public void setStrName(String strName) {
            this.strName = strName;
        }

        public String getStrSourcePath() {
            return strPath == null? "" :strPath;
        }

        public void setStrSourcePath(String strPath) {
            this.strPath = strPath;
        }

        public String getStrPath() {
            return strPathEx == null?"":strPathEx;
        }

        public void setStrFilePath(String strPathEx) {
            this.strPathEx = strPathEx;
        }

        public String getStrTime() {
            return strTime;
        }

        public void setStrTime(String strTime) {
            this.strTime = strTime;
        }

        public int getiSize() {
            return iSize;
        }

        public void setiSize(int iSize) {
            this.iSize = iSize;
        }

        public int getiOwnID() {
            return iOwnID;
        }

        public void setiOwnID(int iOwnID) {
            this.iOwnID = iOwnID;
        }

        public int getiType() {
            return iType;
        }

        public void setiType(int iType) {
            this.iType = iType;
        }

        public int getiModuleID() {
            return iModuleID;
        }

        public void setiModuleID(int iModuleID) {
            this.iModuleID = iModuleID;
        }

        public int getiAllowDownload() {
            return iAllowDownload;
        }

        public void setiAllowDownload(int iAllowDownload) {
            this.iAllowDownload = iAllowDownload;
        }

        public int getiParentDirID() {
            return iParentDirID;
        }

        public void setiParentDirID(int iParentDirID) {
            this.iParentDirID = iParentDirID;
        }

        public int getiIsDir() {
            return iIsDir;
        }

        public void setiIsDir(int iIsDir) {
            this.iIsDir = iIsDir;
        }

        public int getIsDown() {
            return isDown;
        }

        public void setIsDown(int isDown) {
            this.isDown = isDown;
        }

        @Override
        public int compareTo(@NonNull LstFileBean o) {
            if(this.iOrderNo < o.iOrderNo){
                return -1;
            }else if(this.iOrderNo == o.iOrderNo){
                if(this.iType == 4 && o.iType == 4){
                    long first = TimeUtil.getFormFormatTime(this.strTime);
                    long second = TimeUtil.getFormFormatTime(o.strTime);
                    if(first < second){
                        return -1;
                    }else if(first == second){
                        return 0;
                    }else {
                        return 1;
                    }
                }else {
                    return 0;
                }
            }else{
                return 1;
            }
        }

        @Override
        public String toString() {
            return "LstFileBean{" +
                    "iUpdateType=" + iUpdateType +
                    ", iID=" + iID +
                    ", iOrderNo=" + iOrderNo +
                    ", strName='" + strName + '\'' +
                    ", strPath='" + strPath + '\'' +
                    ", strPathEx='" + strPathEx + '\'' +
                    ", strTime='" + strTime + '\'' +
                    ", iSize=" + iSize +
                    ", iOwnID=" + iOwnID +
                    ", iType=" + iType +
                    ", iModuleID=" + iModuleID +
                    ", iAllowDownload=" + iAllowDownload +
                    ", iStartDatum=" + iStartDatum +
                    ", iParentDirID=" + iParentDirID +
                    ", iIsDir=" + iIsDir +
                    ", aiUserID=" + Arrays.toString(aiUserID) +
                    ", isDown=" + isDown +
                    ", totelprogress=" + totelprogress +
                    ", currentprogress=" + currentprogress +
                    ", iIsShowProgress=" + iIsShowProgress +
                    ", fileNum=" + fileNum +
                    '}';
        }
    }


}
