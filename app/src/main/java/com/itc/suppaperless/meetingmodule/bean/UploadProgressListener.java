package com.itc.suppaperless.meetingmodule.bean;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

/**
 * Created by zhangwd on 2018/2/7 0007.
 */

public interface UploadProgressListener {
    /** 成功添加任务的回调 */
    void onStart(Progress progress);

    /** 上传进行时回调 */
    void onProgress(Progress progress);

    /** 上传出错时回调 */
    void onError(Progress progress);

    /** 上传完成时回调 */
    void onFinish(Response response, Progress progress, int type);

    /** 被移除时回调 */
    void onRemove(Progress progress);
}
