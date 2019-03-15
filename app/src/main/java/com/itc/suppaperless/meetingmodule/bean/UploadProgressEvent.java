package com.itc.suppaperless.meetingmodule.bean;

import com.lzy.okgo.model.Progress;

/**
 * Created by ghost on 2016/11/1.
 */

public class UploadProgressEvent {
    private Progress progress;
    public UploadProgressEvent(Progress progress){
        this.progress = progress;
    }
    public Progress getData(){
        return progress;
    }
}
