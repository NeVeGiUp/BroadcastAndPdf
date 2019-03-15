package com.itc.suppaperless.screen_record.event;

public class IsOpenScreenEvent {
    private boolean isOpenScreen;
    private int screenSend;
    public IsOpenScreenEvent(boolean isOpenScreen, int screenSend){
        this.isOpenScreen = isOpenScreen;
        this.screenSend = screenSend;
    }
    public IsOpenScreenEvent(boolean isOpenScreen){
        this.isOpenScreen = isOpenScreen;
        this.screenSend = screenSend;
    }
    public boolean getData(){
        return isOpenScreen;
    }
    public int getScreenSend(){
        return screenSend;
    }
}
