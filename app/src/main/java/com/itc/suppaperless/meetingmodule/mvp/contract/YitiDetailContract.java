package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;
import com.itc.suppaperless.screen_record.contract.ScreenRecordContract;

import java.util.List;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface YitiDetailContract {
    interface View extends ScreenRecordContract.ScreenRecordUI{
        void setcurrentFil(List<CommentUploadListInfo.LstFileBean> lstfiles);
        void setIssueState(YitichangeInfo yitichangeInfo);
        void setIssueUpdate(IssueInfo.LstIssue issues);
        void changeTrackStatus(boolean isSpeaker);
    }
    interface Model extends ScreenRecordContract.ScreenRecordMdl{

    }
    interface Presenter extends IBaseXPresenter{
        void setRightNavigation();
    }
}
