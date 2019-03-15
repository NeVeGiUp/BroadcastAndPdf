package com.itc.suppaperless.meetingmodule.eventbean;

import com.lzy.okgo.model.Progress;

/**
 * Created by ghost on 2016/10/24.
 */

public class DownloadEvent {
    private Progress progress;
    public DownloadEvent(Progress progress){
        this.progress = progress;
    }
    public Progress getData(){
        return progress;
    }
}
