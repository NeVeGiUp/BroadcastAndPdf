package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.ServiceSendMeetingInfo;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.meetingmodule.eventbean.MeetingInfoEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.HyxxContract;
import com.itc.suppaperless.meetingmodule.mvp.model.HyxxModel;
import com.itc.suppaperless.utils.GsonUtil;
import com.lzy.okserver.OkDownload;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class HyxxPresenter extends BasePresenter<HyxxContract.View,HyxxContract.Model> implements HyxxContract.Presenter {
    public HyxxPresenter(HyxxContract.View view) {
        super(view);
        mModel=new HyxxModel();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MeetingInfoEvent data) {//会议信息
        OkDownload.getInstance().removeAll();
        ServiceSendMeetingInfo info= GsonUtil.getJsonObject(data.getData(), ServiceSendMeetingInfo.class);
        mModel.sendMeetingFileList();
        mModel.sendMeetingIssueList();
        mModel.sendMeetingUser();
        getView().getMeetingInfo(info);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FileUpdateEvent data) {//241文件更新
        getView().getFileUpdate(GsonUtil.getJsonObject(data.getData(), CommentUploadListInfo.class));

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JiaoLiuUserEvent event) {//更新主持人
        AppDataCache.getInstance().putString(Config.SAVE_USER, event.getData());
        getView().getJiaoliuUserInfo(GsonUtil.getJsonObject(event.getData(), JiaoLiuUserInfo.class));

    }
    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }
}

