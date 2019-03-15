package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.DoLive;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.LiveEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.CenterControlContract;
import com.itc.suppaperless.meetingmodule.mvp.contract.VideoServiceContract;
import com.itc.suppaperless.meetingmodule.mvp.model.CenterControlModel;
import com.itc.suppaperless.meetingmodule.mvp.model.VideoServiceModel;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class VideoServicePresenter extends BasePresenter<VideoServiceContract.View,VideoServiceContract.Model> implements VideoServiceContract.Presenter{
    public VideoServicePresenter(VideoServiceContract.View view) {
        super(view);
        mModel=new VideoServiceModel();
        EventBus.getDefault().register(this);
    }
    //文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FileUpdateEvent event) {//文件更新
        getView().getUpdateFile(GsonUtil.getJsonObject(event.getData(), CommentUploadListInfo.class).getLstFile());
    }
    //直播流狀態
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLive(LiveEvent event) {
        getView().doLive(GsonUtil.getJsonObject(event.getStr(), DoLive.class));
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
