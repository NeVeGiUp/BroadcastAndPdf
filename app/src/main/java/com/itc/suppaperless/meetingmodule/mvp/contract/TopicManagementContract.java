package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;

import java.util.List;


public interface TopicManagementContract {
    interface View extends BaseView {
        void setTopicStatus(YitichangeInfo yitichangeInfo);

        void setTopicUpdate(IssueInfo.LstIssue issue);

        void setTopic(List<IssueInfo.LstIssue> lstIssue);

    }

    interface Model {
        void setTopicStatus(int topicId, int type);//设置议题属性
        void setTopicReady(int topicId,int time);//设置议题为预备状态
    }

    interface Presenter extends IBaseXPresenter {
        void setTopicStart(int topicId); //议题开始

        void setTopicStop(int topicId);  //议题结束

        void sendTopicInform(int topicId,int time); //发送议题准备开始通知

        void setTopicRestart(int topicId);//议题重新开始

    }
}
