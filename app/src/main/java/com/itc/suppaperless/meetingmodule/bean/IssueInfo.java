package com.itc.suppaperless.meetingmodule.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ghost on 2016/9/24.
 */
public class IssueInfo {
    private String iCmdEnum;
    private String iIssueNum;
    private List<LstIssue> lstIssue = new ArrayList<>();

    public String getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(String iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public String getiIssueNum() {
        return iIssueNum;
    }

    public void setiIssueNum(String iIssueNum) {
        this.iIssueNum = iIssueNum;
    }

    public List<LstIssue> getLstIssue() {
        return lstIssue;
    }

    public void setLstIssue(List<LstIssue> lstIssue) {
        this.lstIssue = lstIssue;
    }

    public static class LstIssue {
        private int iIssueId;
        private String strName;
        private String iStatus;
        private int iUpdateType;
        private int iOrderNo; //序号，排序
        private String strReporter; //汇报人名字
        private int [] aiUserID;
        private int filenum;
        private int iStartDatum;

        public int getiStartDatum() {
            return iStartDatum;
        }

        public void setiStartDatum(int iStartDatum) {
            this.iStartDatum = iStartDatum;
        }

        public int getiOrderNo() {
            return iOrderNo;
        }

        public void setiOrderNo(int iOrderNo) {
            this.iOrderNo = iOrderNo;
        }

        private List<CommentUploadListInfo.LstFileBean> lstFile = new ArrayList<>();

        public int getiIssueId() {
            return iIssueId;
        }

        public void setiIssueId(int iIssueId) {
            this.iIssueId = iIssueId;
        }

        public int getiUpdateType() {
            return iUpdateType;
        }

        public void setiUpdateType(int iUpdateType) {
            this.iUpdateType = iUpdateType;
        }

        public int[] getAiUserID() {
            return aiUserID;
        }

        public void setAiUserID(int[] aiUserID) {
            this.aiUserID = aiUserID;
        }

        public int getiUpdataType() {
            return iUpdateType;
        }

        public void setiUpdataType(int iUpdateType) {
            this.iUpdateType = iUpdateType;
        }

        public int getiOderNo() {
            return iOrderNo;
        }

        public void setiOderNo(int iOderNo) {
            this.iOrderNo = iOderNo;
        }

        public String getStrReporter() {
            return strReporter;
        }

        public void setStrReporter(String strReporter) {
            this.strReporter = strReporter;
        }

        public String getStrName() {
            return strName;
        }

        public void setStrName(String strName) {
            this.strName = strName;
        }

        public String getiStatus() {
            return iStatus;
        }

        public void setiStatus(String iStatus) {
            this.iStatus = iStatus;
        }

        public int getFilenum() {
            return filenum;
        }

        public void setFilenum(int filenum) {
            this.filenum = filenum;
        }

        public List<CommentUploadListInfo.LstFileBean> getLstFile() {
            return lstFile;
        }

        public void setLstFile(List<CommentUploadListInfo.LstFileBean> lsFile) {
            for (int i=0;i<lsFile.size();i++){
                this.lstFile.add(lsFile.get(i));

            }
        }

        public int getIssueId() {
            return iIssueId;
        }

        public void setIssueId(int issueId) {
            iIssueId = issueId;
        }

        @Override
        public String toString() {
            return "LstIssue{" +
                    "iIssueId=" + iIssueId +
                    ", strName='" + strName + '\'' +
                    ", iStatus='" + iStatus + '\'' +
                    ", iUpdateType=" + iUpdateType +
                    ", iOrderNo=" + iOrderNo +
                    ", strReporter='" + strReporter + '\'' +
                    ", aiUserID=" + Arrays.toString(aiUserID) +
                    ", filenum=" + filenum +
                    ", lstFile=" + lstFile +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "IssueInfo{" +
                "iCmdEnum='" + iCmdEnum + '\'' +
                ", iIssueNum='" + iIssueNum + '\'' +
                ", lstIssue=" + lstIssue +
                '}';
    }
}
