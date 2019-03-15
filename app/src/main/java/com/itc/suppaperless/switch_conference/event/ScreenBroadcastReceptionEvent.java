package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-2-16.
 */

public class ScreenBroadcastReceptionEvent {
    public byte[] contentByte;

    public ScreenBroadcastReceptionEvent(byte[] jsonData) {
        this.contentByte = jsonData;
    }

    public byte[] getContentByte() {
        return contentByte;
    }
}
