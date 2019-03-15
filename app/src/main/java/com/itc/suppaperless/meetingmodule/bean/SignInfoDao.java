package com.itc.suppaperless.meetingmodule.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by huangsm on 2017/7/4.
 */

@Entity
public class SignInfoDao {
    @Id(autoincrement = true)
    private long id; //自定义数据库表的key
    private String strUserName;//终端名称
    private String strSignTime;//签到时间
    @Generated(hash = 2036378654)
    public SignInfoDao(long id, String strUserName, String strSignTime) {
        this.id = id;
        this.strUserName = strUserName;
        this.strSignTime = strSignTime;
    }
    @Generated(hash = 1833939411)
    public SignInfoDao() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getStrUserName() {
        return this.strUserName;
    }
    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }
    public String getStrSignTime() {
        return this.strSignTime;
    }
    public void setStrSignTime(String strSignTime) {
        this.strSignTime = strSignTime;
    }
}

