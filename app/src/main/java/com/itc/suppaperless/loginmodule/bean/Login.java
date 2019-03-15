package com.itc.suppaperless.loginmodule.bean;

import com.itc.suppaperless.base.BaseBean;



/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-14 下午7:16
 * @ desc   : 登录信息
 */
public class Login extends BaseBean {

    private String strUserName;
    private String strPassWord;
    private int iConferenceID;

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrPassWord() {
        return strPassWord;
    }

    public void setStrPassWord(String strPassWord) {
        this.strPassWord = strPassWord;
    }

    public int getiConferenceID() {
        return iConferenceID;
    }

    public void setiConferenceID(int iConferenceID) {
        this.iConferenceID = iConferenceID;
    }
}
