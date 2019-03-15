package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-2-17.
 */

public class StartBroadcastEvent {
    private String JsonData;

    public StartBroadcastEvent(String jsonData) {
        this.JsonData = jsonData;
    }

    public String getJsonData() {
        return JsonData;
    }
}
