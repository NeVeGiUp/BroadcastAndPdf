package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-3-6.
 */

public class MeetingMessageEvent {
    private String JsonData;

    public MeetingMessageEvent(String jsonData) {
        this.JsonData = jsonData;
    }

    public String getJsonData() {
        return JsonData;
    }
}
