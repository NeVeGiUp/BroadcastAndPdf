package com.itc.suppaperless.switch_conference.presenter;


import android.util.Log;

import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.RegisterMediaEvent;
import com.itc.suppaperless.channels.RegisterTerminalEvent;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.meetingmodule.eventbean.JizhongControlEvent;
import com.itc.suppaperless.screen_record.presenter.ScreenRecordPresenter;
import com.itc.suppaperless.switch_conference.bean.GetUserInformationBean;
import com.itc.suppaperless.switch_conference.bean.MeetingMessageBean;
import com.itc.suppaperless.switch_conference.bean.RegisterServerBean;
import com.itc.suppaperless.switch_conference.contract.EnterMeetingContract;
import com.itc.suppaperless.switch_conference.event.ChangeFragmentEvent;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;
import com.itc.suppaperless.switch_conference.event.GetUserInformationEvent;
import com.itc.suppaperless.switch_conference.event.MeetingMessageEvent;
import com.itc.suppaperless.switch_conference.event.RegisterMediaServerEvent;
import com.itc.suppaperless.switch_conference.event.RegisterServerEvent;
import com.itc.suppaperless.switch_conference.model.EnterMeetingModel;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.itc.suppaperless.global.Config.IS_CONNECTED;
import static com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent.SERVER_DISCONNECT;
import static com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent.WIFI_DISCONNECT;

public class EnterMeetingPresenter extends ScreenRecordPresenter<EnterMeetingContract.EnterMeetingUI,
        EnterMeetingContract.EnterMeetingMdl> implements EnterMeetingContract.EnterMeetingPresenter {

    private int mUserID, mMeetingID, mMeetingRoomID, mPortAddress;
    private String mIPAddress;
    private boolean isIn = true;//第一次创建timer
    private Timer timer;

    public EnterMeetingPresenter(EnterMeetingContract.EnterMeetingUI view) {
        super(view);
        mModel = new EnterMeetingModel();
    }

    @Override
    public void enterMeeting(String mIPAddress, int mPortAddress, int mUserID, int mMeetingID, int mMeetingRoomID) {
        mModel.meetingChannelConnection(mIPAddress, mPortAddress, mUserID);
        this.mIPAddress = mIPAddress;
        this.mPortAddress = mPortAddress;
        this.mUserID = mUserID;
        this.mMeetingID = mMeetingID;
        this.mMeetingRoomID = mMeetingRoomID;
    }

    /**
     * 连接命名通道成功
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRegisterTerminalEvent(RegisterTerminalEvent registerTerminalEvent) {
        mModel.registerServer(mUserID, mMeetingID, mMeetingRoomID);
    }

    /**
     * 连接媒体通道成功
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRegisterMediaEvent(RegisterMediaEvent registerMediaEvent) {
        mModel.registerMediaServer(mUserID, mMeetingID, mMeetingRoomID);
    }

    /**
     * 注册命令通道成功
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRegisterServerEvent(RegisterServerEvent registerServerEvent) {
        RegisterServerBean registerServerBean = new Gson().fromJson(registerServerEvent.getJsonData(), RegisterServerBean.class);
        Log.i("registerServerBeans", registerServerEvent.getJsonData());
        if (registerServerBean.getIResult() == 200) {
            Log.i("ddddddddddd", "200");

            mModel.getUserInformation(mUserID);
            if (timer != null) {
                timer.cancel();
            }
        } else if (registerServerBean.getIResult() == 409) {
            if (isIn) {
                isIn = false;
                timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Log.i("ddddddddddd", "400");
                        mModel.registerMediaServer(mUserID, mMeetingID, mMeetingRoomID);
                    }
                };
                timer.schedule(timerTask, 4000, 2000);
            }
        }
    }

    /**
     * 注册媒体通道成功
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRegisterMediaServerEvent(RegisterMediaServerEvent registerMediaServerEvent) {
        RegisterServerBean mRegisterServerBean = new Gson().fromJson(registerMediaServerEvent.getJsonData(), RegisterServerBean.class);
        mRegisterServerBean.getIResult();
    }

    /**
     * 获取用户信息成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUserInformationEvent(GetUserInformationEvent getUserInformationEvent) {
        PaperlessApplication.getGlobalConstantsBean().setConnect(true);
        GetUserInformationBean getUserInformationBean = new Gson().fromJson(getUserInformationEvent.getJsonData(), GetUserInformationBean.class);
        getView().getUserInformationSuccess(getUserInformationBean);
    }

    /**
     * 用于接收切换fragment
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeFragmentEvent(ChangeFragmentEvent changeFragmentEvent) {
        int strID = -1;
        int position = changeFragmentEvent.getPosition();
        switch (position) {
            case 0:
                strID = R.string.jizhong_message;
                break;
            case 1://议题材料
                strID = R.string.issue_material;
                break;
            case 2://参会名单
                strID = R.string.meeting_person;
                break;
            case 3://临时资料
                strID = R.string.meeting_material;
                break;
            case 4://会议投票
                strID = R.string.meeting_voting;
                break;
            case 5://会议服务
                strID = R.string.meeting_service;
                break;
            case 6://查看批注
                strID = R.string.view_comments;
                break;
            case 7://其他功能
                strID = R.string.more_features;
                break;
            case 8://签到管理
                strID = R.string.sign_in_management;
                break;
            case 9://投票管理
                strID = R.string.voting_management;
                break;
            case 10://议题管理
                strID=R.string.topic_management;
                break;
            case 11://会议标语
                strID = R.string.meeting_slogan;
                break;
            case 12://集中控制
                strID = R.string.centralized_control;
                break;
            case 13://大屏广播
                strID = R.string.screen_broad;
                break;
            case 14://电子白板
                strID = R.string.electronic_whiteboard;
                break;
            case 15://网络浏览
                strID = R.string.web_browsing;
                break;
            case 16://视频服务
                strID = R.string.video_service;
                break;
            case 17://个人中心
                strID = R.string.history_meeting;
                break;

        }
        getView().changeFunctionFragment(strID);
    }

    /**
     * 网络断开
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionStatusEvent(ConnectionStatusEvent connectionStatusEvent) {
        switch (connectionStatusEvent.getConnectionStatus()) {
            case SERVER_DISCONNECT://服务器断开
                PaperlessApplication.getGlobalConstantsBean().setConnect(false);
                getView().receivingNetworkState();
                break;
            case WIFI_DISCONNECT://网络断开
//                PaperlessApplication.getGlobalConstantsBean().setConnect(false);
//                AppDataCache.getInstance().putBoolean(IS_CONNECTED,false);
//                getView().receivingNetworkState();
                break;
            case ConnectionStatusEvent.ISDISCONNECT://网络连接
                mModel.meetingChannelConnection(mIPAddress, mPortAddress, mUserID);
                break;
            case ConnectionStatusEvent.SERVER_ISDISCONNECT://服务器成功
                PaperlessApplication.getGlobalConstantsBean().setConnect(true);
                break;
        }
    }

    /**
     * 集中控制
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetCenterControlEvent(JizhongControlEvent jizhongControlEvent) {
        String str = jizhongControlEvent.getString();
        try {
            JSONObject jsonSlogan = new JSONObject(str);
            int iControlType = jsonSlogan.getInt("iControlType");
            getView().getCenterControlType(iControlType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 重置人员
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JiaoLiuUserEvent event) {
        JiaoLiuUserInfo jiaoLiuUserInfo = GsonUtil.getJsonObject(event.getData(), JiaoLiuUserInfo.class);
        if (jiaoLiuUserInfo.getICmdEnum() == 803) {
            getView().getJiaoliuUserInfo(jiaoLiuUserInfo);
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeetingMessageEvent(MeetingMessageEvent meetingMessageEvent) {
        MeetingMessageBean mMeetingMessageBean = new Gson().fromJson(meetingMessageEvent.getJsonData(), MeetingMessageBean.class);
        getView().getMeetingMessage(mMeetingMessageBean.getStrContent());
    }

}
