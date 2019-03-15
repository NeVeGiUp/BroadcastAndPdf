package com.itc.suppaperless.loginmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by xiaogf on 19-2-16.
 */

public class FingerLogin extends BaseBean {
    private int iBusiType;
    private String strFingerData;

    public int getiBusiType() {
        return iBusiType;
    }

    public void setiBusiType(int iBusiType) {
        this.iBusiType = iBusiType;
    }

    public String getStrFingerData() {
        return strFingerData;
    }

    public void setStrFingerData(String strFingerData) {
        this.strFingerData = strFingerData;
    }
}
