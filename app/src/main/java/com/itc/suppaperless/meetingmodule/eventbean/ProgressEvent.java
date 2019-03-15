package com.itc.suppaperless.meetingmodule.eventbean;

import com.lzy.okgo.model.Progress;

/**
 * Created by ghost on 2016/11/1.
 */

public class ProgressEvent {
    private Progress progress;
    public ProgressEvent(Progress progress){
        this.progress = progress;
    }
    public Progress getData(){
        return progress;
    }
}
