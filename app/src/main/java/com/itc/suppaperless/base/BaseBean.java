package com.itc.suppaperless.base;

import java.io.Serializable;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-8 上午9:05
 * @ desc   :
 */
public class BaseBean implements Serializable {

    //命令value
    private int iCmdEnum;

    public int getiCmdEnum() {
        return iCmdEnum;
    }

    public void setiCmdEnum(int iCmdEnum) {
        this.iCmdEnum = iCmdEnum;
    }

}
