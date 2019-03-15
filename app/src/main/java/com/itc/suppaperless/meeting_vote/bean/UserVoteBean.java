package com.itc.suppaperless.meeting_vote.bean;

import java.util.List;

/**
 * Created by cong on 19-3-9.
 */

public class UserVoteBean {
    /**
     * iCmdEnum : 236
     * iVoteID : 9
     * strVoteName :
     * iTotalUserNum : 47
     * iFinishVoteNum : 1
     * iNotVoteNum : 46
     * lstOption : [{"iOptionID":19,"iNum":1,"aiVotedUserID":[100002]},{"iOptionID":20,"iNum":0,"aiVotedUserID":[]}]
     */

    private int iCmdEnum;
    private int iVoteID;
    private String strVoteName;
    private int iTotalUserNum;
    private int iFinishVoteNum;
    private int iNotVoteNum;
    private List<MeetingVoteBean.LstVoteBean.LstOptionBean> lstOption;

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

    public String getStrVoteName() {
        return strVoteName;
    }

    public void setStrVoteName(String strVoteName) {
        this.strVoteName = strVoteName;
    }

    public int getITotalUserNum() {
        return iTotalUserNum;
    }

    public void setITotalUserNum(int iTotalUserNum) {
        this.iTotalUserNum = iTotalUserNum;
    }

    public int getIFinishVoteNum() {
        return iFinishVoteNum;
    }

    public void setIFinishVoteNum(int iFinishVoteNum) {
        this.iFinishVoteNum = iFinishVoteNum;
    }

    public int getINotVoteNum() {
        return iNotVoteNum;
    }

    public void setINotVoteNum(int iNotVoteNum) {
        this.iNotVoteNum = iNotVoteNum;
    }

    public List<MeetingVoteBean.LstVoteBean.LstOptionBean> getLstOption() {
        return lstOption;
    }

    public void setLstOption(List<MeetingVoteBean.LstVoteBean.LstOptionBean> lstOption) {
        this.lstOption = lstOption;
    }

}
