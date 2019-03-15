package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.bean.MeetingSloganInfo;
import com.itc.suppaperless.meetingmodule.bean.Sogan;
import com.itc.suppaperless.meetingmodule.bean.SoganList;
import com.itc.suppaperless.meetingmodule.eventbean.ConferenceSoganEvent;
import com.itc.suppaperless.meetingmodule.eventbean.SoganAddOrDEvent;
import com.itc.suppaperless.meetingmodule.eventbean.SoganListEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.ConferenceSoganContract;
import com.itc.suppaperless.meetingmodule.mvp.model.ConferenceSoganModel;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class ConferenceSoganPresenter extends BasePresenter<ConferenceSoganContract.View,ConferenceSoganContract.Model> implements ConferenceSoganContract.Presenter{
    public ConferenceSoganPresenter(ConferenceSoganContract.View view) {
        super(view);
        mModel=new ConferenceSoganModel();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSogan(ConferenceSoganEvent conferenceSoganEvent){//切换标语返回信息
        Sogan sogan= GsonUtil.getJsonObject(conferenceSoganEvent.getStr(),Sogan.class);
            getView().getSogan(sogan);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSoganList(SoganListEvent soganListEvent){//标语略表
        MeetingSloganInfo sogan= GsonUtil.getJsonObject(soganListEvent.getStr(),MeetingSloganInfo.class);
        getView().getSoganList(sogan);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSoganAddOrD(SoganAddOrDEvent soganAddOrDEvent){//标语增删改
        SoganList sogan= GsonUtil.getJsonObject(soganAddOrDEvent.getStr(),SoganList.class);
        getView().getSoganAddOrD(sogan);

    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void sendSogan() {
        mModel.sendsogan();
    }

    @Override
    public void changeSogan(int iCurSloganID, int iTerminalStatus) {
        mModel.changeSogan(iCurSloganID,iTerminalStatus);
    }

    @Override
    public void exitSogan(int type) {
        mModel.exitSogan(type);
    }
}
