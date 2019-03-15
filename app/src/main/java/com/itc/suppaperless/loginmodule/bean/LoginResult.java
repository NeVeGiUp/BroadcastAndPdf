package com.itc.suppaperless.loginmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-21 下午2:25
 * @ desc   : 登录结果信息
 */
public class LoginResult extends BaseBean {


    /**
     * iResult : 200
     * strErrorMsg :
     * strErrorMsgEn :
     * iUserID : 100009
     * iMeetingID : 0
     */

    private int iResult;
    private String strErrorMsg;
    private String strErrorMsgEn;
    private int iUserID;
    private int iMeetingID;

    public int getIResult() {
        return iResult;
    }

    public void setIResult(int iResult) {
        this.iResult = iResult;
    }

    public String getStrErrorMsg() {
        return strErrorMsg;
    }

    public void setStrErrorMsg(String strErrorMsg) {
        this.strErrorMsg = strErrorMsg;
    }

    public String getStrErrorMsgEn() {
        return strErrorMsgEn;
    }

    public void setStrErrorMsgEn(String strErrorMsgEn) {
        this.strErrorMsgEn = strErrorMsgEn;
    }

    public int getIUserID() {
        return iUserID;
    }

    public void setIUserID(int iUserID) {
        this.iUserID = iUserID;
    }

    public int getIMeetingID() {
        return iMeetingID;
    }

    public void setIMeetingID(int iMeetingID) {
        this.iMeetingID = iMeetingID;
    }
}
