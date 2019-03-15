package com.itc.suppaperless.switch_conference.presenter;


import com.google.gson.Gson;
import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.channels.RegisterTerminalEvent;
import com.itc.suppaperless.loginmodule.event.RegisterTerminalJsonEvent;
import com.itc.suppaperless.switch_conference.bean.MeetingListBean;
import com.itc.suppaperless.switch_conference.bean.RegisterLoginServerBean;
import com.itc.suppaperless.switch_conference.contract.MeetingListContract;
import com.itc.suppaperless.switch_conference.event.GetMeetingListEvent;
import com.itc.suppaperless.switch_conference.event.MeetingStatusChangeEvent;
import com.itc.suppaperless.switch_conference.model.MeetingListModel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-8 上午8:59
 * @ desc   : presenter层,逻辑处理层
 */
public class MeetingListPresenter extends BasePresenter<MeetingListContract.MeetingListUI,MeetingListContract.MeetingListMdl> implements
        MeetingListContract.MeetingListPresenter {

    private String userID;

    public MeetingListPresenter(MeetingListContract.MeetingListUI view) {
        super(view);
        mModel = new MeetingListModel();
        EventBus.getDefault().register(this);
    }

    @Override
    public void sendMeetingList(String userID, String mIPAddress, int mPortAddress,boolean isReconnect) {
        this.userID = userID;
        if (isReconnect){
            mModel.loginChannelConnection(mIPAddress,mPortAddress,1);
        }else {
            mModel.sendMeetingList(userID);
        }
    }
    /**
     * 网络请求成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMeetingListEvent(GetMeetingListEvent getMeetingListEvent) {
        if (isViewAttach()){
            MeetingListBean meetingListBean = new Gson().fromJson(getMeetingListEvent.getJsonData(), MeetingListBean.class);
            if (meetingListBean.getIResult() == 200){
                getView().getMeetingListSuccess(meetingListBean);
            }
        }
    }
    /**
     * 连接通道成功
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRegisterTerminalEvent(RegisterTerminalEvent registerTerminalEvent) {
        mModel.registerLoginServer();
    }
    /**
     * 注册登录服务成功
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRegisterTerminalJsonEvent(RegisterTerminalJsonEvent registerTerminalJsonEvent) {
        RegisterLoginServerBean registerLoginServerBean = new Gson().fromJson(registerTerminalJsonEvent
                .getmJsonData(), RegisterLoginServerBean.class);
        if (registerLoginServerBean.getIResult() == 200){
            mModel.sendMeetingList(userID);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeetingStatusChangeEvent(MeetingStatusChangeEvent meetingStatusChangeEvent) {
        getView().getMeetingStatusChange();
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }
}
