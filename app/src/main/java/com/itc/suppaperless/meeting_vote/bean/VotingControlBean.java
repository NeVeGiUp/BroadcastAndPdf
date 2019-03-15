package com.itc.suppaperless.meeting_vote.bean;

/**
 * Created by cong on 19-3-10.
 */

public class VotingControlBean {
    /**
     * iCmdEnum : 232
     * iControlType : 2
     * iVoteID : 26
     * strTimeLimit :
     */

    private int iCmdEnum;
    private int iControlType;
    private int iVoteID;
    private String strTimeLimit;

    public int getICmdEnum() {
        return iCmdEnum;
    }

    public void setICmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

    public int getIControlType() {
        return iControlType;
    }

    public void setIControlType(int iControlType) {
        this.iControlType = iControlType;
    }

    public int getIVoteID() {
        return iVoteID;
    }

    public void setIVoteID(int iVoteID) {
        this.iVoteID = iVoteID;
    }

    public String getStrTimeLimit() {
        return strTimeLimit;
    }

    public void setStrTimeLimit(String strTimeLimit) {
        this.strTimeLimit = strTimeLimit;
    }
}
