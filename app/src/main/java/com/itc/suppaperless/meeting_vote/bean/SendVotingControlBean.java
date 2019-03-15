package com.itc.suppaperless.meeting_vote.bean;


import com.itc.suppaperless.base.BaseBean;

/**
 * Created by cong on 19-1-17.
 */

public class SendVotingControlBean extends BaseBean {
    private int iVoteID;

    private int iControlType;

    public int getiVoteID() {
        return iVoteID;
    }

    public void setiVoteID(int iVoteID) {
        this.iVoteID = iVoteID;
    }

    public int getiControlType() {
        return iControlType;
    }

    public void setiControlType(int iControlType) {
        this.iControlType = iControlType;
    }
}
