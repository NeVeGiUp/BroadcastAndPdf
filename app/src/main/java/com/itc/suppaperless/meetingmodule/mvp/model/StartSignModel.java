package com.itc.suppaperless.meetingmodule.mvp.model;


import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.StartSignPost;
import com.itc.suppaperless.meetingmodule.mvp.contract.StartSignContract;

/**
 * Created by xiaogf on 19-1-23.
 */

public class StartSignModel implements StartSignContract.Model{

    @Override
    public void sign(int userId, String userName, String pFilePath, String pFileName) {
        StartSignPost startSignPost=new StartSignPost();
        startSignPost.setiCmdEnum(218);
        startSignPost.setiTerminalID(userId);
        startSignPost.setiUserID(userId);
        startSignPost.setStrUserName(userName);
        startSignPost.setStrFilePath(pFilePath);
        startSignPost.setStrUserName(pFileName);
        NettyTcpCommonClient.sendPackage(startSignPost);

    }


}
