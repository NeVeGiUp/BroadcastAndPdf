package com.itc.suppaperless.channels.common;

public interface CommandListener {
    void onComplete(int strType,String jsonData);
}