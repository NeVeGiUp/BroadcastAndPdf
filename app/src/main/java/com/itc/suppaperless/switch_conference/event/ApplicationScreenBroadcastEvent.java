package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-2-16.
 */

public class ApplicationScreenBroadcastEvent {
    private String JsonData;

    public ApplicationScreenBroadcastEvent(String jsonData) {
        this.JsonData = jsonData;
    }

    public String getJsonData() {
        return JsonData;
    }
}
