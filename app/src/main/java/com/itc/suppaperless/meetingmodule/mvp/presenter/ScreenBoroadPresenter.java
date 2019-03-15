package com.itc.suppaperless.meetingmodule.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.bean.BroadCatstMsg;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.ScreenBroadBean;
import com.itc.suppaperless.meetingmodule.eventbean.BraodCastEvent;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.ScreenBroadContract;
import com.itc.suppaperless.meetingmodule.mvp.model.ScreenBroadModel;
import com.itc.suppaperless.utils.GsonUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class ScreenBoroadPresenter extends BasePresenter<ScreenBroadContract.View,ScreenBroadContract.Model> implements ScreenBroadContract.Presenter{
    public ScreenBoroadPresenter(ScreenBroadContract.View view) {
        super(view);
        mModel=new ScreenBroadModel();
        EventBus.getDefault().register(this);
    }
    //文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FileUpdateEvent event) {//文件更新
        getView().getUpdateFile(GsonUtil.getJsonObject(event.getData(), CommentUploadListInfo.class).getLstFile());
    }

    @Override
    public void startPlay(String videoUrl, int fileId) {
        mModel.startPlay(videoUrl,fileId);
    }

    @Override
    public void stopPlay() {
        mModel.stopPlay();

    }

    @Override
    public void setPlayProgress(double progress) {
        mModel.setPlayProgress(progress);
    }

    @Override
    public void suspendPlay() {
        mModel.suspendPlay();
    }

    @Override
    public void setVolume(int volume) {
        mModel.setVolume(volume);
    }

    @Override
    public void broadCastToAll(boolean force) {
        mModel.broadCastToAll(force);
    }

    @Override
    public void stopBroadCastToAll() {
        mModel.stopBroadCastToAll();
    }

    @Override
    public void queryState() {
        mModel.queryState();
    }

    @Override
    public void continuePlay() {
        mModel.continuePlay();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBroadCast(BraodCastEvent braodCastEvent){
        ScreenBroadBean screenBroadBean=JSON.parseObject(braodCastEvent.getStr(), ScreenBroadBean.class);
        String msg=screenBroadBean.getStrCmdMsg();
        JSONObject object=JSON.parseObject(msg);
        Log.i("sssssssssssssss",object.toString());
        BroadCatstMsg strCmdMsg=JSON.parseObject(object.toString(),BroadCatstMsg.class);
        getView().getBroadCasst(strCmdMsg);

    }

}
