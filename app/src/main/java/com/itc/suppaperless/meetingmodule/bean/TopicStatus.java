package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;


public class TopicStatus extends BaseBean {
    private int iIssueID;
    private int iControlType;

    public int getiIssueID() {
        return iIssueID;
    }

    public void setiIssueID(int iIssueID) {
        this.iIssueID = iIssueID;
    }

    public int getiControlType() {
        return iControlType;
    }

    public void setiControlType(int iControlType) {
        this.iControlType = iControlType;
    }
}
