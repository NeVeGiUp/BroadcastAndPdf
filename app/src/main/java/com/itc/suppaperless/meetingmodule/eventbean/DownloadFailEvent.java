package com.itc.suppaperless.meetingmodule.eventbean;

/**
 * Created by huangsm on 2017/5/22.
 */

public class DownloadFailEvent {
    private String str;
    public DownloadFailEvent(String str){
        this.str = str;
    }
    public String getData(){
        return str;
    }
}
