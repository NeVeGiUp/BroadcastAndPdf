package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-1-21.
 */

public class GetMeetingListEvent {
    private String JsonData;

    public GetMeetingListEvent(String jsonData) {
        this.JsonData = jsonData;
    }

    public String getJsonData() {
        return JsonData;
    }
}
