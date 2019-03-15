package com.itc.suppaperless.loginmodule.model;



/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-8 上午9:01
 * @ desc   : 登录model层,回调网络数据给presenter层
 */
public interface LoginModel {
    //请求登录
    void loadLogin(String userName, String userPsw);
    void fingerLogin(String strFingerData);

}
