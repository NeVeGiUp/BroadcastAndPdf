package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;

import java.util.List;

/**
 * Created by xiaogf on 19-1-23.
 *
 * 插入一层pdf主讲数据接收mvp模式（依赖模式）
 */

public interface CailiaoContract {
    interface View extends SpeakDataRecContract.SpeakDataView{
        void getUpdateFile(List<CommentUploadListInfo.LstFileBean> lstFiles);

    }
    interface Model extends SpeakDataRecContract.SpeakDataModel{

    }
    interface Presenter extends IBaseXPresenter{

    }
}
