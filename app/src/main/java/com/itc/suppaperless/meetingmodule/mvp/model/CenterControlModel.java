package com.itc.suppaperless.meetingmodule.mvp.model;

import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.CenterControl;
import com.itc.suppaperless.meetingmodule.mvp.contract.CenterControlContract;

/**
 * Created by xiaogf on 19-1-23.
 */

public class CenterControlModel implements CenterControlContract.Model{
    @Override
    public void centerControl(int nControlType) {
        CenterControl centerControl=new CenterControl();
        centerControl.setiCmdEnum(260);
        centerControl.setiControlType(nControlType);
        NettyTcpCommonClient.sendPackage(centerControl);

    }
}
