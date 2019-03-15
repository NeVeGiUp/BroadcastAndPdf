package com.itc.suppaperless.screen_record.event;

/**
 * Created by pengs on 2017/6/30.
 */

public class DecodingScreentEvent {
    private byte[] bytes;
    public DecodingScreentEvent(byte[] bytes){
        this.bytes = bytes;
    }
    public byte[] getData(){
        return bytes;
    }
}
