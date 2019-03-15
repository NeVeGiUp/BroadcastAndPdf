package com.itc.suppaperless.screen_record.model;

import android.media.projection.MediaProjection;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.itc.suppaperless.channels.common.MediaNettyTcpCommonClient;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.screen_record.ScreenRecord;
import com.itc.suppaperless.screen_record.VideoEncodeConfig;
import com.itc.suppaperless.screen_record.contract.ScreenRecordContract;
import com.itc.suppaperless.switch_conference.bean.ApplicationScreenBroadcast;
import com.itc.suppaperless.switch_conference.bean.SendGetUserInformationBean;
import com.itc.suppaperless.switch_conference.bean.SendRegisterServerBean;
import com.itc.suppaperless.switch_conference.contract.EnterMeetingContract;

import static com.itc.suppaperless.utils.AppUtils.createVideoConfig;


/**
 * Created by cong on 19-1-22.
 */

public class ScreenRecordModel implements ScreenRecordContract.ScreenRecordMdl{
    /**
     * @param mUserID
     * @param iForceCast 0 非强制　1 强制
     * @param iToAll 1: 所有用户  0: 部分用户
     */
    @Override
    public void applicationScreenBroadcast(int mUserID, int iForceCast, int iToAll) {
        ApplicationScreenBroadcast mApplicationScreenBroadcast = new ApplicationScreenBroadcast();
        mApplicationScreenBroadcast.setiCmdEnum(219);
        mApplicationScreenBroadcast.setiUserID(mUserID);
        mApplicationScreenBroadcast.setiForceCast(iForceCast);
        mApplicationScreenBroadcast.setiToAll(iToAll);
        MediaNettyTcpCommonClient.getInstance().sendPackage(mApplicationScreenBroadcast);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startRecorder(MediaProjection mediaProjection) {
        VideoEncodeConfig video = createVideoConfig();
        ScreenRecord.getInstance().createDisplay(video,mediaProjection);
    }
}
