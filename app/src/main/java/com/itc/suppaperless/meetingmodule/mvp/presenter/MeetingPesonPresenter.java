package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.MeetingPersonContract;
import com.itc.suppaperless.meetingmodule.mvp.model.MeetringPersonModel;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class MeetingPesonPresenter extends BasePresenter<MeetingPersonContract.View,MeetingPersonContract.Model> implements MeetingPersonContract.Presenter{
    public MeetingPesonPresenter(MeetingPersonContract.View view) {
        super(view);
        mModel=new MeetringPersonModel();
        EventBus.getDefault().register(this);
    }

    @Override
    public void getPerson() {
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JiaoLiuUserEvent event) {//用户列表
        JiaoLiuUserInfo jiaoLiuUserInfo= GsonUtil.getJsonObject(event.getData(), JiaoLiuUserInfo.class);
            getView().getPerson(jiaoLiuUserInfo);
    }
    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }
}
