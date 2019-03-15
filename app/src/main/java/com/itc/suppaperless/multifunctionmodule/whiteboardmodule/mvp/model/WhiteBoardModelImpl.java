package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.model;

import com.itc.suppaperless.channels.common.NettyTcpCommonClient;

import java.io.File;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-11 下午3:41
 * @ desc   : WhiteBoardModelImpl data request
 */
public class WhiteBoardModelImpl implements WhiteBoardModel {

    @Override
    public void uploadScreenshot(String fileName, String tag, String body, int type) {
        NettyTcpCommonClient.getInstance().UploadFile(fileName, body, new File(tag).length(), type);
    }
}
