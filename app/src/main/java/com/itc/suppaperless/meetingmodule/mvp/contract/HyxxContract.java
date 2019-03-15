package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.ServiceSendMeetingInfo;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface HyxxContract {
    interface View extends BaseView{
        void getMeetingInfo(ServiceSendMeetingInfo info);
        void getFileUpdate(CommentUploadListInfo info);
        void getJiaoliuUserInfo(JiaoLiuUserInfo info);
    }
    interface Model {
        void sendMeetingFileList();
        void sendMeetingIssueList();
        void sendMeetingUser();

    }
    interface Presenter extends IBaseXPresenter {

    }
}
