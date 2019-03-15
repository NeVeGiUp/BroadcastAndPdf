package com.itc.suppaperless.switch_conference.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.channels.common.CommandListener;
import com.itc.suppaperless.switch_conference.bean.MeetingListBean;

public interface MeetingListContract {
    /**
     * view 层接口
     */
    interface MeetingListUI extends BaseView {

        void getMeetingListSuccess(MeetingListBean data);
        void getMeetingStatusChange();
    }
    /**
     * presenter 层接口
     */
    interface MeetingListPresenter extends IBaseXPresenter {
        void sendMeetingList(String userID,String mIPAddress,int mPortAddress,boolean isReconnect);
    }

    /**
     * model 层接口
     */
    interface MeetingListMdl {
        //发送
        void sendMeetingList(String userID);
        //连接登录通道
        void loginChannelConnection(String mIPAddress,int mPortAddress,int mUserID);
        //注册后台LoginServer与终端
        void registerLoginServer();
    }
}