package com.itc.suppaperless.meetingmodule.mvp.presenter;

import android.util.Log;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.MeetingIssueList;
import com.itc.suppaperless.meetingmodule.eventbean.YitiEvent;
import com.itc.suppaperless.meetingmodule.eventbean.YitiupdataEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.TopicManagementContract;
import com.itc.suppaperless.meetingmodule.mvp.model.TopicManagementModel;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class TopicManagementPresenter extends BasePresenter<TopicManagementContract.View, TopicManagementContract.Model> implements TopicManagementContract.Presenter {
    public TopicManagementPresenter(TopicManagementContract.View view) {
        super(view);
        mModel = new TopicManagementModel();
        EventBus.getDefault().register(this);
    }


    //下发议题
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MeetingIssueList event) {
        Log.e("------","-----------TopicManagementPresenter setIssue");

        getView().setTopic(GsonUtil.getJsonObject(event.getData(), IssueInfo.class).getLstIssue());

    }

    //议题状态通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YitiEvent event) {
        getView().setTopicStatus(GsonUtil.getJsonObject(event.getData(), YitichangeInfo.class));

    }

    //议题文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YitiupdataEvent event) {
        getView().setTopicUpdate(GsonUtil.getJsonObject(event.getData(), IssueInfo.LstIssue.class));

    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setTopicStart(int topicId) {
        mModel.setTopicStatus(topicId, 1);
    }

    @Override
    public void setTopicStop(int topicId) {
        mModel.setTopicStatus(topicId, 2);
    }

    @Override
    public void sendTopicInform(int topicId,int time) {
        mModel.setTopicReady(topicId,time);
    }

    @Override
    public void setTopicRestart(int topicId) {
        mModel.setTopicStatus(topicId, 1);
    }
}
