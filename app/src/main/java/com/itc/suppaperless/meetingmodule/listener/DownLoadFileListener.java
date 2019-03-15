package com.itc.suppaperless.meetingmodule.listener;

import com.itc.suppaperless.meetingmodule.eventbean.DownloadEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadFailEvent;
import com.itc.suppaperless.meetingmodule.eventbean.ProgressEvent;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by xiaogf on 19-1-21.
 */

public class DownLoadFileListener extends DownloadListener {
    public DownLoadFileListener() {
        super("DownLoadFileListener");
    }

    @Override
    public void onStart(Progress progress) {

    }

    @Override
    public void onProgress(Progress progress) {
        EventBus.getDefault().post(new ProgressEvent(progress));

    }

    @Override
    public void onError(Progress progress) {
        EventBus.getDefault().post(new DownloadFailEvent(progress.tag));

    }

    @Override
    public void onFinish(File file, Progress progress) {
        EventBus.getDefault().post(new DownloadEvent(progress));
    }

    @Override
    public void onRemove(Progress progress) {

    }
}
