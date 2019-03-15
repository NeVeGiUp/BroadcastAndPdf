package com.itc.suppaperless.screen_record.contract;

import android.media.projection.MediaProjection;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.switch_conference.bean.GetUserInformationBean;

public interface ScreenRecordContract {
    /**
     * view 层接口
     */
    interface ScreenRecordUI extends BaseView {
        void changeScreenBroadcastStatus(boolean isBroadcasting);
    }
    /**
     * presenter 层接口
     */
    interface ScreenRecordPresenter extends IBaseXPresenter {
        //录屏
        void startRecorder(MediaProjection mediaProjection);
        //用户申请屏幕广播
        void applicationScreenBroadcast(int iForceCast, int iToAll,int mUserID);
    }
    /**
     * model 层接口
     */
    interface ScreenRecordMdl {
        //开启录屏
        void startRecorder(MediaProjection mediaProjection);
        //用户申请屏幕广播
        void applicationScreenBroadcast(int mUserID, int iForceCast, int iToAll);
    }
}