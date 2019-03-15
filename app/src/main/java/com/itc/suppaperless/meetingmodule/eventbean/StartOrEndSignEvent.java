package com.itc.suppaperless.meetingmodule.eventbean;

/**
 * Created by xiaogf on 19-3-5.
 */

public class StartOrEndSignEvent {
    private String str;
    public StartOrEndSignEvent(String str){
        this.str = str;
    }
    public String getData(){
        return str;
    }
}
