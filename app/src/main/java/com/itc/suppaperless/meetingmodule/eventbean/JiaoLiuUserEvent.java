package com.itc.suppaperless.meetingmodule.eventbean;

/**
 * Created by ghost on 2016/9/22.
 */
public class JiaoLiuUserEvent {
    private String str;
    private int code;   //iCmdEnum 803
    public JiaoLiuUserEvent(String str){
        this.str = str;
    }

    public String getData(){
        return str;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}