package com.itc.suppaperless.meetingmodule.bean;

/**
 * Created by huangsm on 2017/6/8.
 */

public class MuiltFileUploadEvent {

    private String[] paths;
    public MuiltFileUploadEvent(String[] paths){
        this.paths = paths;
    }
    public String[] getData(){
        return paths;
    }
}
