package com.itc.suppaperless.meetingmodule.mvp.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.ScreenBroadBean;
import com.itc.suppaperless.meetingmodule.bean.ScreenBroadCast;
import com.itc.suppaperless.meetingmodule.mvp.contract.ScreenBroadContract;

/**
 * Created by xiaogf on 19-1-23.
 */

public class ScreenBroadModel implements ScreenBroadContract.Model {

    @Override
    public void startPlay(String videoUrl, int fileId) {//播放
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        strCmdMsg.setVideoUrl(videoUrl);
        strCmdMsg.setFileId(fileId);
        screenBroadCast.setiCmdEnum(800);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }

    @Override
    public void suspendPlay() {//暂停播放
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        screenBroadCast.setiCmdEnum(800);
        strCmdMsg.setVodCtrlType(1);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }

    @Override
    public void continuePlay() {//继续播放
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        screenBroadCast.setiCmdEnum(800);
        strCmdMsg.setVodCtrlType(2);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }

    @Override
    public void stopPlay() {//停止播放
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        screenBroadCast.setiCmdEnum(800);
        strCmdMsg.setVodCtrlType(3);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }

    @Override
    public void setPlayProgress(double progress) {//前进、后退、拉进度条
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        screenBroadCast.setiCmdEnum(800);
        strCmdMsg.setVodCtrlType(4);
        strCmdMsg.setPos(progress);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }


    @Override
    public void setVolume(int volume) {//音量
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        screenBroadCast.setiCmdEnum(800);
        strCmdMsg.setVodCtrlType(6);
        strCmdMsg.setVolume(volume);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }

    @Override
    public void broadCastToAll(boolean force) {//广播到终端
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        screenBroadCast.setiCmdEnum(800);
        strCmdMsg.setVodCtrlType(9);
        strCmdMsg.setForce(force);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }

    @Override
    public void stopBroadCastToAll() {//停止广播到终端
        ScreenBroadBean screenBroadCast = new ScreenBroadBean();
        ScreenBroadBean.StrCmdMsg strCmdMsg = screenBroadCast.new StrCmdMsg();
        screenBroadCast.setiCmdEnum(800);
        strCmdMsg.setVodCtrlType(10);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(strCmdMsg);
        screenBroadCast.setStrCmdMsg(jsonObject.toString());
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }

    @Override
    public void queryState() {
        ScreenBroadCast screenBroadCast = new ScreenBroadCast();
        screenBroadCast.setiCmdEnum(800);
        screenBroadCast.setVodCtrlType(13);
        NettyTcpCommonClient.sendPackage(screenBroadCast);
    }
}
