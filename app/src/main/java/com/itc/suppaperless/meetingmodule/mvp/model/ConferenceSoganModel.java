package com.itc.suppaperless.meetingmodule.mvp.model;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.CenterControl;
import com.itc.suppaperless.meetingmodule.bean.ChangeSogan;
import com.itc.suppaperless.meetingmodule.bean.SendSogan;
import com.itc.suppaperless.meetingmodule.mvp.contract.ConferenceSoganContract;
import static com.itc.suppaperless.global.Config.MEETING_ID;

/**
 * Created by xiaogf on 19-1-23.
 */

public class ConferenceSoganModel implements ConferenceSoganContract.Model{
    @Override
    public void sendsogan(){
        SendSogan sendSogan=new SendSogan();
        sendSogan.setiCmdEnum(552);
        sendSogan.setiMeetingID(AppDataCache.getInstance().getInt(MEETING_ID));
        NettyTcpCommonClient.sendPackage(sendSogan);
    }

    @Override
    public void changeSogan(int iCurSloganID, int iTerminalStatus) {
        ChangeSogan changeSogan=new ChangeSogan();
        changeSogan.setiCmdEnum(555);
        changeSogan.setiCurSloganID(iCurSloganID);
        changeSogan.setiStatus(iTerminalStatus);
        NettyTcpCommonClient.sendPackage(changeSogan);
    }

    @Override
    public void exitSogan(int type) {
        CenterControl centerControl=new CenterControl();
        centerControl.setiCmdEnum(260);
        centerControl.setiControlType(type);
        NettyTcpCommonClient.sendPackage(centerControl);
    }

}
