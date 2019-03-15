package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by ghost on 2016/10/24.
 */

public class UploadSuccessEvent {
    private String str;
    private String response;
    public UploadSuccessEvent(String str,String response){
        this.str = str;
        this.response=response;
    }
    public String getData(){
        return str;
    }
    public String getResponse(){
        return response;
    }
}
