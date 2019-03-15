package com.itc.suppaperless.meetingmodule.eventbean;

/**
 * Created by ghost on 2016/9/22.
 */
public class FileUpdateEvent {
    private String str;
    public FileUpdateEvent(String str){
        this.str = str;
    }
    public String getData(){
        return str;
    }
}