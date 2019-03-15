package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by ghost on 2016/10/24.
 */

public class UploadFailEvent {
    private String str;
    public UploadFailEvent(String str){
        this.str = str;
    }
    public String getData(){
        return str;
    }
}
