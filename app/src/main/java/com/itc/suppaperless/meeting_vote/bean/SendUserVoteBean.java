package com.itc.suppaperless.meeting_vote.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by cong on 19-3-8.
 */

public class SendUserVoteBean  extends BaseBean {
    private int iUserID;
    private int iVoteID;
    private String strComment;
    private int[] aiOptionID;

    public int[] getAiOptionID() {
        return aiOptionID;
    }

    public void setAiOptionID(int[] aiOptionID) {
        this.aiOptionID = aiOptionID;
    }

    public int getiUserID() {
        return iUserID;
    }

    public void setiUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public int getiVoteID() {
        return iVoteID;
    }

    public void setiVoteID(int iVoteID) {
        this.iVoteID = iVoteID;
    }

    public String getStrComment() {
        return strComment;
    }

    public void setStrComment(String strComment) {
        this.strComment = strComment;
    }
}
