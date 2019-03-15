package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.DoLive;

import java.util.List;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface VideoServiceContract {
    interface View extends BaseView{
        void getUpdateFile(List<CommentUploadListInfo.LstFileBean> lstFiles);
        void doLive(DoLive live);
    }
    interface Model{

    }
    interface Presenter extends IBaseXPresenter{
    }
}
