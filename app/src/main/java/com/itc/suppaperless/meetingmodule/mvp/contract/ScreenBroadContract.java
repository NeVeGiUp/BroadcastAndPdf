package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.BroadCatstMsg;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.ScreenBroadBean;

import java.util.List;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface ScreenBroadContract {
    interface View extends BaseView{
        void getUpdateFile(List<CommentUploadListInfo.LstFileBean> lstFiles);
        void getBroadCasst( BroadCatstMsg strCmdMsg);

    }
    interface Model{
        void startPlay(String videoUrl,int fileId);
        void stopPlay();
        void continuePlay();
        void setPlayProgress(double progress);
        void suspendPlay();
        void setVolume(int volume);
        void broadCastToAll(boolean force);
        void stopBroadCastToAll();
        void queryState();


    }
    interface Presenter extends IBaseXPresenter{
        void startPlay(String videoUrl,int fileId);
        void stopPlay();
        void setPlayProgress(double progress);
        void suspendPlay();
        void setVolume(int volume);
        void broadCastToAll(boolean force);
        void stopBroadCastToAll();
        void queryState();
        void continuePlay();
    }
}
