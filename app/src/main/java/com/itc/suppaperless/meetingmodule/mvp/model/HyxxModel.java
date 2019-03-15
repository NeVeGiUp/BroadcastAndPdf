package com.itc.suppaperless.meetingmodule.mvp.model;

import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.SendMeetingFileList;
import com.itc.suppaperless.meetingmodule.bean.SendMeetingIssueList;
import com.itc.suppaperless.meetingmodule.mvp.contract.HyxxContract;

/**
 * Created by xiaogf on 19-1-23.
 */

public class HyxxModel implements HyxxContract.Model {

    @Override
    public void sendMeetingFileList() {
        SendMeetingFileList sendMeetingFileList=new SendMeetingFileList();
        sendMeetingFileList.setiCmdEnum(212);
        NettyTcpCommonClient.getInstance().sendPackage(sendMeetingFileList);
    }

    @Override
    public void sendMeetingIssueList() {
        SendMeetingIssueList sendMeetingIssueList=new SendMeetingIssueList();
        sendMeetingIssueList.setiCmdEnum(214);
        NettyTcpCommonClient.getInstance().sendPackage(sendMeetingIssueList);
    }

    @Override
    public void sendMeetingUser() {
        SendMeetingIssueList sendMeetingIssueList=new SendMeetingIssueList();
        sendMeetingIssueList.setiCmdEnum(210);
        NettyTcpCommonClient.getInstance().sendPackage(sendMeetingIssueList);
    }
}
