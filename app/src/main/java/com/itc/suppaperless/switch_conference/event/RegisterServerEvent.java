package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-1-22.
 */

public class RegisterServerEvent {
    private String JsonData;

    public RegisterServerEvent(String jsonData) {
        this.JsonData = jsonData;
    }

    public String getJsonData() {
        return JsonData;
    }
}
