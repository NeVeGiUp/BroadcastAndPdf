package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.MeetingIssueList;
import com.itc.suppaperless.meetingmodule.eventbean.YitiEvent;
import com.itc.suppaperless.meetingmodule.eventbean.YitiupdataEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.YitiContract;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class YitiPresenter extends BasePresenter<YitiContract.View,YitiContract.Model> implements YitiContract.Presenter {
    public YitiPresenter(YitiContract.View view) {
        super(view);
        EventBus.getDefault().register(this);
    }
    //241文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FileUpdateEvent event) {
       getView().setcurrentFil(GsonUtil.getJsonObject(event.getData(), CommentUploadListInfo.class).getLstFile());
    }
    //下发议题
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MeetingIssueList event) {
        getView().setIssue(GsonUtil.getJsonObject(event.getData(), IssueInfo.class).getLstIssue());

    }
    //议题状态通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YitiEvent event) {
        getView().setIssueState(GsonUtil.getJsonObject(event.getData(), YitichangeInfo.class));

    }
    //议题文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YitiupdataEvent event) {
        getView().setIssueUpdate(GsonUtil.getJsonObject(event.getData(),IssueInfo.LstIssue.class));

    }
    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }
}
