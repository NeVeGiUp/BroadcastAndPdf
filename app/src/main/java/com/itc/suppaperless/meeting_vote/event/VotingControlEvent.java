package com.itc.suppaperless.meeting_vote.event;

/**
 * Created by cong on 19-3-6.
 */

public class VotingControlEvent {
    private String JsonData;

    public VotingControlEvent(String jsonData) {
        this.JsonData = jsonData;
    }

    public String getJsonData() {
        return JsonData;
    }
}
