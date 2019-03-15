package com.itc.suppaperless.meeting_vote.bean;

import java.util.List;

/**
 * Created by cong on 19-3-10.
 */

public class VoteUpdateBean {
    /**
     * iCmdEnum : 231
     * iVoteID : 31
     * iUpdateType : 1
     * iStatus : 0
     * iIssueID : 0
     * iScreenMode : 0
     * strIssueName :
     * strComment :
     * strVoteTitle : 测试
     * strVoteName : 111111111
     * strStartTime : 0000-00-00 00:00:00
     * strEndTime : 0000-00-00 00:00:00
     * iCheckbox : 0
     * iRealName : 1
     * iIsSign : 0
     * iIsComment : 0
     * iIsPassingRate : 0
     * iPassingRate : 0
     * iPROptionID : 0
     * iIsTimeLimit : 0
     * strTimeLimit : 00:00:00
     * iIsVote : 0
     * iVotingAuthentication : 0
     * aiUserID : [100000,100001,100002,100003,100004,100005,100006,100007,100008,100009,100010,100011,100012,100013,100014,100015,100016,100017,100018,100019,100020,100021,100022,100023,100024,100025,100026,100027,100028,100029,100030,100031,100032,100033,100034,100035,100036,100037,100038,100039,100040,100041,100042,100043,100044,100045,100046]
     * lstOption : [{"iOptionID":71,"strOptionName":"赞成","iNum":0,"aiVotedUserID":[]},{"iOptionID":72,"strOptionName":"1","iNum":0,"aiVotedUserID":[]}]
     * iScoreType : 0
     * iScoreScore :
     */

    private int iCmdEnum;
    private int iVoteID;
    private int iUpdateType;
    private int iStatus;
    private int iIssueID;
    private int iScreenMode;
    private String strIssueName;
    private String strComment;
    private String strVoteTitle;
    private String strVoteName;
    private String strStartTime;
    private String strEndTime;
    private int iCheckbox;
    private int iRealName;
    private int iIsSign;
    private int iIsComment;
    private int iIsPassingRate;
    private int iPassingRate;
    private int iPROptionID;
    private int iIsTimeLimit;
    private String strTimeLimit;
    private int iIsVote;
    private int iVotingAuthentication;
    private int iScoreType;
    private String iScoreScore;
    private List<Integer> aiUserID;
    private List<MeetingVoteBean.LstVoteBean> lstOption;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIVoteID() {
        return iVoteID;
    }

    public void setIVoteID(int iVoteID) {
        this.iVoteID = iVoteID;
    }

    public int getIUpdateType() {
        return iUpdateType;
    }

    public void setIUpdateType(int iUpdateType) {
        this.iUpdateType = iUpdateType;
    }

    public int getIStatus() {
        return iStatus;
    }

    public void setIStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public int getIIssueID() {
        return iIssueID;
    }

    public void setIIssueID(int iIssueID) {
        this.iIssueID = iIssueID;
    }

    public int getIScreenMode() {
        return iScreenMode;
    }

    public void setIScreenMode(int iScreenMode) {
        this.iScreenMode = iScreenMode;
    }

    public String getStrIssueName() {
        return strIssueName;
    }

    public void setStrIssueName(String strIssueName) {
        this.strIssueName = strIssueName;
    }

    public String getStrComment() {
        return strComment;
    }

    public void setStrComment(String strComment) {
        this.strComment = strComment;
    }

    public String getStrVoteTitle() {
        return strVoteTitle;
    }

    public void setStrVoteTitle(String strVoteTitle) {
        this.strVoteTitle = strVoteTitle;
    }

    public String getStrVoteName() {
        return strVoteName;
    }

    public void setStrVoteName(String strVoteName) {
        this.strVoteName = strVoteName;
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

    public int getICheckbox() {
        return iCheckbox;
    }

    public void setICheckbox(int iCheckbox) {
        this.iCheckbox = iCheckbox;
    }

    public int getIRealName() {
        return iRealName;
    }

    public void setIRealName(int iRealName) {
        this.iRealName = iRealName;
    }

    public int getIIsSign() {
        return iIsSign;
    }

    public void setIIsSign(int iIsSign) {
        this.iIsSign = iIsSign;
    }

    public int getIIsComment() {
        return iIsComment;
    }

    public void setIIsComment(int iIsComment) {
        this.iIsComment = iIsComment;
    }

    public int getIIsPassingRate() {
        return iIsPassingRate;
    }

    public void setIIsPassingRate(int iIsPassingRate) {
        this.iIsPassingRate = iIsPassingRate;
    }

    public int getIPassingRate() {
        return iPassingRate;
    }

    public void setIPassingRate(int iPassingRate) {
        this.iPassingRate = iPassingRate;
    }

    public int getIPROptionID() {
        return iPROptionID;
    }

    public void setIPROptionID(int iPROptionID) {
        this.iPROptionID = iPROptionID;
    }

    public int getIIsTimeLimit() {
        return iIsTimeLimit;
    }

    public void setIIsTimeLimit(int iIsTimeLimit) {
        this.iIsTimeLimit = iIsTimeLimit;
    }

    public String getStrTimeLimit() {
        return strTimeLimit;
    }

    public void setStrTimeLimit(String strTimeLimit) {
        this.strTimeLimit = strTimeLimit;
    }

    public int getIIsVote() {
        return iIsVote;
    }

    public void setIIsVote(int iIsVote) {
        this.iIsVote = iIsVote;
    }

    public int getIVotingAuthentication() {
        return iVotingAuthentication;
    }

    public void setIVotingAuthentication(int iVotingAuthentication) {
        this.iVotingAuthentication = iVotingAuthentication;
    }

    public int getIScoreType() {
        return iScoreType;
    }

    public void setIScoreType(int iScoreType) {
        this.iScoreType = iScoreType;
    }

    public String getIScoreScore() {
        return iScoreScore;
    }

    public void setIScoreScore(String iScoreScore) {
        this.iScoreScore = iScoreScore;
    }

    public List<Integer> getAiUserID() {
        return aiUserID;
    }

    public void setAiUserID(List<Integer> aiUserID) {
        this.aiUserID = aiUserID;
    }

    public List<MeetingVoteBean.LstVoteBean> getLstOption() {
        return lstOption;
    }

    public void setLstOption(List<MeetingVoteBean.LstVoteBean> lstOption) {
        this.lstOption = lstOption;
    }
}
