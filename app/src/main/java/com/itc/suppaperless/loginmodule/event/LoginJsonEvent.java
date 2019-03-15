package com.itc.suppaperless.loginmodule.event;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-22 下午2:17
 * @ desc   : eventbus:登录json回调
 */
public class LoginJsonEvent {

    private String mJsonData;

    public LoginJsonEvent(String jsonData) {
        this.mJsonData = jsonData;
    }

    public String getmJsonData() {
        return mJsonData;
    }
}
