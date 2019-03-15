package com.itc.suppaperless.switch_conference.contract;


import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.screen_record.contract.ScreenRecordContract;
import com.itc.suppaperless.switch_conference.bean.GetUserInformationBean;

public interface EnterMeetingContract {
    /**
     * view 层接口
     */
    interface EnterMeetingUI extends ScreenRecordContract.ScreenRecordUI {

        void getUserInformationSuccess(GetUserInformationBean getUserInformationBean);

        void changeFunctionFragment(int strID);

        void receivingNetworkState();
        void getCenterControlType(int iControlType);
        void getJiaoliuUserInfo(JiaoLiuUserInfo jiaoLiuUserInfo);
        void getMeetingMessage(String strContent);
    }
    /**
     * presenter 层接口
     */
    interface EnterMeetingPresenter extends IBaseXPresenter {
        //进入会议
        void enterMeeting(String mIPAddress,int mPortAddress,int mUserID,int mMeetingID,int mMeetingRoomID);
    }

    /**
     * model 层接口
     */
    interface EnterMeetingMdl extends ScreenRecordContract.ScreenRecordMdl{
        //连接会议通道
        void meetingChannelConnection(String mIPAddress,int mPortAddress,int mUserID);
        //注册后台命令Server与终端
        void registerServer(int mUserID,int mMeetingID,int mMeetingRoomID);
        //获取用户信息
        void getUserInformation(int mUserID);
        //注册后台媒体Server与终端
        void registerMediaServer(int mUserID,int mMeetingID,int mMeetingRoomID);
    }
}