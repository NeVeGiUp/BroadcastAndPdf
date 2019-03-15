package com.itc.suppaperless.loginmodule.event;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-22 下午2:17
 * @ desc   : eventbus:注册终端json回调
 */
public class RegisterTerminalJsonEvent {

    private String mJsonData;

    public RegisterTerminalJsonEvent(String jsonData) {
        this.mJsonData = jsonData;
    }

    public String getmJsonData() {
        return mJsonData;
    }
}
