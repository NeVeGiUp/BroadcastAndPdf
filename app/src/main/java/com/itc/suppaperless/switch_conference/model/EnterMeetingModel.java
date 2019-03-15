package com.itc.suppaperless.switch_conference.model;

import android.app.Activity;
import android.media.MediaCodecInfo;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.channels.common.MediaNettyTcpCommonClient;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.screen_record.ScreenRecord;
import com.itc.suppaperless.screen_record.VideoEncodeConfig;
import com.itc.suppaperless.screen_record.model.ScreenRecordModel;
import com.itc.suppaperless.switch_conference.bean.ApplicationScreenBroadcast;
import com.itc.suppaperless.switch_conference.bean.SendGetUserInformationBean;
import com.itc.suppaperless.switch_conference.bean.SendRegisterServerBean;
import com.itc.suppaperless.switch_conference.contract.EnterMeetingContract;
import com.itc.suppaperless.utils.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.itc.suppaperless.global.Config.VIDEO_AVC;
import static com.itc.suppaperless.utils.AppUtils.createVideoConfig;


/**
 * Created by cong on 19-1-22.
 */

public class EnterMeetingModel extends ScreenRecordModel implements EnterMeetingContract.EnterMeetingMdl{

    @Override
    public void meetingChannelConnection(String mIPAddress, int mPortAddress, int mUserID) {
        NettyTcpCommonClient.getInstance().connServer(mIPAddress, mPortAddress,mUserID);
        MediaNettyTcpCommonClient.getInstance().connServer(mIPAddress, mPortAddress,mUserID);
    }

    @Override
    public void registerServer(int mUserID, int mMeetingID, int mMeetingRoomID) {
        SendRegisterServerBean mRegisterServerBean = new SendRegisterServerBean();
        mRegisterServerBean.setiCmdEnum(201);
        mRegisterServerBean.setiTerminalType(2);
        mRegisterServerBean.setiTerminalID(mUserID);
        mRegisterServerBean.setiUserID(mUserID);
        mRegisterServerBean.setiMeetingID(mMeetingID);
        mRegisterServerBean.setiMeetingRoomID(mMeetingRoomID);
        mRegisterServerBean.setiChannelType(1);
        NettyTcpCommonClient.sendPackage(mRegisterServerBean);
    }

    @Override
    public void registerMediaServer(int mUserID, int mMeetingID, int mMeetingRoomID) {
        SendRegisterServerBean mRegisterServerBean = new SendRegisterServerBean();
        mRegisterServerBean.setiCmdEnum(201);
        mRegisterServerBean.setiTerminalType(2);
        mRegisterServerBean.setiTerminalID(mUserID);
        mRegisterServerBean.setiUserID(mUserID);
        mRegisterServerBean.setiMeetingID(mMeetingID);
        mRegisterServerBean.setiMeetingRoomID(mMeetingRoomID);
        mRegisterServerBean.setiChannelType(2);
        MediaNettyTcpCommonClient.getInstance().sendPackage(mRegisterServerBean);
    }

    @Override
    public void getUserInformation(int mUserID) {
        SendGetUserInformationBean mSendGetUserInformationBean = new SendGetUserInformationBean();
        mSendGetUserInformationBean.setiCmdEnum(208);
        mSendGetUserInformationBean.setiTerminalID(mUserID);
        mSendGetUserInformationBean.setiUserID(mUserID);
        NettyTcpCommonClient.sendPackage(mSendGetUserInformationBean);
    }
}
