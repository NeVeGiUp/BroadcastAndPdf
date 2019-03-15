package com.itc.suppaperless.switch_conference.bean;


import com.itc.suppaperless.base.BaseBean;

/**
 * Created by cong on 19-1-17.
 */

public class SendMeetingListBean extends BaseBean {
    private String iUserID;

    public String getiUserID() {
        return iUserID;
    }

    public void setiUserID(String iUserID) {
        this.iUserID = iUserID;
    }
}
