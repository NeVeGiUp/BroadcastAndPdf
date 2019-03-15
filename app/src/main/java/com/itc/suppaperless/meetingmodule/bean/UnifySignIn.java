package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by xiaogf on 19-3-5.
 */

public class UnifySignIn extends BaseBean {
    private int[] aiUserID;
    private String strTime;

    public int[] getAiUserID() {
        return aiUserID;
    }

    public void setAiUserID(int[] aiUserID) {
        this.aiUserID = aiUserID;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }
}
