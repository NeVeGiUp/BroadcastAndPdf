package com.itc.suppaperless.utils;

import com.itc.suppaperless.meetingmodule.bean.UploadProgressListener;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.ProgressRequestBody;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.HttpUtils;

import okhttp3.Call;


public class UploadThread<T> implements Runnable {

    public Progress progress;
    UploadProgressListener listener;
    public int type;

    public UploadThread(String tag, Request<T, ? extends Request> request, int type) {
        HttpUtils.checkNotNull(tag, "tag == null");
        progress = new Progress();
        progress.tag = tag;
        progress.url = request.getBaseUrl();
        progress.status = Progress.NONE;
        progress.totalSize = -1;
        progress.request = request;
       this.type = type;

    }


    public UploadThread<T> register(UploadProgressListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void run() {
        progress.status = Progress.LOADING;
        postLoading(progress);
        final Response response;
        try {
            //noinspection unchecked
            Request<T, ? extends Request> request = (Request<T, ? extends Request>) progress.request;
            final Call rawCall = request.getRawCall();
            request.uploadInterceptor(new ProgressRequestBody.UploadInterceptor() {
                @Override
                public void uploadProgress(Progress innerProgress) {
                    if (rawCall.isCanceled()) return;
                    if (progress.status != Progress.LOADING) {
                        rawCall.cancel();
                        return;
                    }
                    progress.from(innerProgress);
                    postLoading(progress);
                }
            });
            response = request.adapt().execute();
        } catch (Exception e) {
            postOnError(progress, e);
            return;
        }

        if (response.isSuccessful()) {
            postOnFinish(progress, response,type);
        } else {
            postOnError(progress, response.getException());
        }
    }

    private void postLoading(final Progress progress) {
        HttpUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    listener.onProgress(progress);
            }
        });
    }

    private void postOnError(final Progress progress, final Throwable throwable) {
        progress.speed = 0;
        progress.status = Progress.ERROR;
        progress.exception = throwable;

        HttpUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    listener.onProgress(progress);
                    listener.onError(progress);
            }
        });
    }

    private void postOnFinish(final Progress progress, final Response response, final int type) {
        progress.speed = 0;
        progress.fraction = 1.0f;
        progress.status = Progress.FINISH;

        HttpUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    listener.onProgress(progress);
                    listener.onFinish(response, progress,type);
            }
        });
    }


}
