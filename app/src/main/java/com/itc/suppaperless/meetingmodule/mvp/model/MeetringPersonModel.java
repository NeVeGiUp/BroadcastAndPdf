package com.itc.suppaperless.meetingmodule.mvp.model;

import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.CenterControl;
import com.itc.suppaperless.meetingmodule.bean.MeetingPerson;
import com.itc.suppaperless.meetingmodule.mvp.contract.CenterControlContract;
import com.itc.suppaperless.meetingmodule.mvp.contract.MeetingPersonContract;

/**
 * Created by xiaogf on 19-1-23.
 */

public class MeetringPersonModel implements MeetingPersonContract.Model{

    @Override
    public void getPerson() {
        MeetingPerson person =new MeetingPerson();
        person.setiCmdEnum(210);
        NettyTcpCommonClient.getInstance().sendPackage(person);

    }
}
