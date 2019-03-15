package com.itc.suppaperless.meetingmodule.eventbean;

public class MeetingInfoEvent {
    private String str;
    public MeetingInfoEvent(String str){
        this.str = str;
    }
    public String getData(){
        return str;
    }
}
