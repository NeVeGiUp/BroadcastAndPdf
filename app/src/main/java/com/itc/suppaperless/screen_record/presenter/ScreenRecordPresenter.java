package com.itc.suppaperless.screen_record.presenter;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.channels.RegisterMediaEvent;
import com.itc.suppaperless.channels.RegisterTerminalEvent;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.screen_record.ScreenReceiveActivity;
import com.itc.suppaperless.screen_record.contract.ScreenRecordContract;
import com.itc.suppaperless.screen_record.event.DecodingScreentEvent;
import com.itc.suppaperless.screen_record.model.ScreenRecordModel;
import com.itc.suppaperless.switch_conference.bean.GetUserInformationBean;
import com.itc.suppaperless.switch_conference.bean.RegisterServerBean;
import com.itc.suppaperless.switch_conference.bean.StartBroadcastBean;
import com.itc.suppaperless.switch_conference.contract.EnterMeetingContract;
import com.itc.suppaperless.switch_conference.event.ApplicationScreenBroadcastEvent;
import com.itc.suppaperless.switch_conference.event.ChangeFragmentEvent;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;
import com.itc.suppaperless.switch_conference.event.GetUserInformationEvent;
import com.itc.suppaperless.switch_conference.event.RegisterServerEvent;
import com.itc.suppaperless.switch_conference.event.ScreenBroadcastReceptionEvent;
import com.itc.suppaperless.switch_conference.event.StartBroadcastEvent;
import com.itc.suppaperless.switch_conference.event.StopBroadcastEvent;
import com.itc.suppaperless.switch_conference.model.EnterMeetingModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.itc.suppaperless.screen_record.ScreenReceiveActivity.mDecodeBuffers;
import static com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent.SERVER_DISCONNECT;
import static com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent.WIFI_DISCONNECT;
import static com.itc.suppaperless.utils.AppUtils.createVideoConfig;

public class ScreenRecordPresenter<V extends ScreenRecordContract.ScreenRecordUI, M extends ScreenRecordContract
        .ScreenRecordMdl> extends BasePresenter<V, M> implements ScreenRecordContract.ScreenRecordPresenter{
    //用于判断屏幕广播是否申请了
    public static boolean isApplication = false;
    private int mUserID;

    public ScreenRecordPresenter(V view) {
        super(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void startRecorder(MediaProjection mediaProjection) {
        mModel.startRecorder(mediaProjection);
    }

    @Override
    public void applicationScreenBroadcast(int iForceCast, int iToAll,int mUserID) {
        this.mUserID = mUserID;
        mModel.applicationScreenBroadcast(mUserID,iForceCast,iToAll);
    }
    /**
     * 申请屏幕广播
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onApplicationScreenBroadcastEvent(ApplicationScreenBroadcastEvent mApplicationScreenBroadcastEvent) {
        isApplication = true;
    }
    /**
     * 获取屏幕广播byte数据
     */
    private boolean isFirst;//是否做好缓冲区的标志位, 开始为true
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onScreenBroadcastReceptionEvent(ScreenBroadcastReceptionEvent mScreenBroadcastReceptionEvent) {
        byte[] data = mScreenBroadcastReceptionEvent.getContentByte();
        int value = data[4] & 0x0f;
        int size = mDecodeBuffers.size();
        if (isFirst) {
            Log.e("pds", data.length + "   size:  " + size);
            if (size == 0 && value != 7)
                return;
            if (size < 3) {//sps pps  以及   关键帧的保存    //把一开始的3条数据统一保存起来做缓冲区
                mDecodeBuffers.add(data);
            } else {
                isFirst = false;
            }
        } else {
            if (value == 5) {//更新缓冲区的关键帧
                mDecodeBuffers.set(1, data);
                mDecodeBuffers.set(2, data);
            }
        }
        EventBus.getDefault().post(new DecodingScreentEvent(data));
    }
    /**
     * 开始屏幕广播
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartBroadcastEvent(StartBroadcastEvent mStartBroadcastEvent) {
        isFirst = true;
        screenBroadcastStatus(mStartBroadcastEvent.getJsonData(),true);
    }
    /**
     * 停止屏幕广播
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopBroadcastEvent(StopBroadcastEvent mStopBroadcastEvent) {
        screenBroadcastStatus(mStopBroadcastEvent.getJsonData(),false);
    }

    public void screenBroadcastStatus(String jsonData,boolean isBroadcasting){
        Activity activity = getView().getActivity();
        StartBroadcastBean mStartBroadcastBean = new Gson().fromJson(jsonData,StartBroadcastBean.class);
        if (mStartBroadcastBean.getIUserID() != mUserID){
            getView().changeScreenBroadcastStatus(isBroadcasting);
            if (isBroadcasting) activity.startActivity(new Intent(activity, ScreenReceiveActivity.class));
        }
    }
}
