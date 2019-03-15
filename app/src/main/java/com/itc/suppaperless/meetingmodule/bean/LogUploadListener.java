/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.utils.StringUtil;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2017/6/7
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LogUploadListener<T> implements UploadProgressListener {



    @Override
    public void onStart(Progress progress) {
        System.out.println("onStart: " + progress);
    }

    @Override
    public void onProgress(Progress progress) {
        EventBus.getDefault().post(new UploadProgressEvent(progress));
    }

    @Override
    public void onError(Progress progress) {
        EventBus.getDefault().post(new UploadFailEvent(progress.tag));
        System.out.println("onError: " + progress);
        progress.exception.printStackTrace();
    }

    @Override
    public void onFinish(Response response, Progress progress, int type) {

        try {
            String body = response.getRawResponse().body().string();
            EventBus.getDefault().post(new UploadSuccessEvent(progress.tag,body));
           if (type!=1){
                NettyTcpCommonClient.getInstance().UploadFile(StringUtil.getallfilename(progress.tag), body,new File(progress.tag).length(),type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemove(Progress progress) {
        System.out.println("onRemove: " + progress);
    }
}
