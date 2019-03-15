package com.itc.suppaperless.utils;


import android.util.Log;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.LogUploadListener;
import com.itc.suppaperless.meetingmodule.bean.UploadProgressListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.upload.UploadThreadPool;

import java.io.File;

import static com.itc.suppaperless.global.Config.USER_ID;


/**
 * Created by zhangwd on 2018/2/1 0001.
 */

public class UploadFileUtil {

    private UploadThreadPool threadPool;                //上传的线程池

    public static UploadFileUtil getInstance() {
        return UploadFileUtil.UploadHolder.instance;
    }

    private static class UploadHolder {
        private static final UploadFileUtil instance = new UploadFileUtil();
    }

    private UploadFileUtil() {
        threadPool =  new UploadThreadPool();
    }

    public void UploadFile(String path, final int type) {
        Log.i("dffsdfdfdfdf","http://" + Config.strFileServerIP + "/b/resource/upload?id="+ AppDataCache.getInstance().getInt(USER_ID));
        PostRequest<String> postRequest = OkGo.<String>post("http://" + Config.strFileServerIP + "/b/resource/upload?id="+ AppDataCache.getInstance().getInt(USER_ID))
                .params("bJson", "1")
                .params("file", new File(path))
                .converter(new Converter<String>() {
                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        return null;
                    }
                });

       /* UploadTask task = new UploadTask(path, postRequest);
        threadPool.execute(task);*/
        UploadThread<String> task =   new UploadThread<>(path, postRequest,type).register(new LogUploadListener<String>());
        threadPool.execute(task);
    }

}
