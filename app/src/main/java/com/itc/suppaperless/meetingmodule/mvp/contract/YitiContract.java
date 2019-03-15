package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;

import java.util.List;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface YitiContract {
    interface View extends BaseView{
        void setIssueState(YitichangeInfo yitichangeInfo);
        void setIssueUpdate(IssueInfo.LstIssue issue);
        void setcurrentFil(List<CommentUploadListInfo.LstFileBean> lstfiles);
        void setIssue(List<IssueInfo.LstIssue> lstIssue);

    }
    interface Model{

    }
    interface Presenter extends IBaseXPresenter{

    }
}
