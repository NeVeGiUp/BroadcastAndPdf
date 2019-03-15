package com.itc.suppaperless.meeting_vote.bean;


import com.itc.suppaperless.base.BaseBean;

/**
 * Created by cong on 19-1-17.
 */

public class SendMeetingVoteBean extends BaseBean {
    private int iMeetingID;

    public int getiMeetingID() {
        return iMeetingID;
    }

    public void setiMeetingID(int iMeetingID) {
        this.iMeetingID = iMeetingID;
    }
}
