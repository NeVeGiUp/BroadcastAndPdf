package com.itc.suppaperless.switch_conference.model;



import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.loginmodule.bean.RegisterTermital;
import com.itc.suppaperless.switch_conference.bean.SendMeetingListBean;
import com.itc.suppaperless.switch_conference.contract.MeetingListContract;

public class MeetingListModel implements MeetingListContract.MeetingListMdl{
    @Override
    public void sendMeetingList(String userID) {
        //发送网络请求以及回调数据给p层
        SendMeetingListBean mSendMeetingListBean = new SendMeetingListBean();
        mSendMeetingListBean.setiCmdEnum(607);
        mSendMeetingListBean.setiUserID(userID);
        NettyTcpCommonClient.sendPackage(mSendMeetingListBean);
    }

    @Override
    public void loginChannelConnection(String mIPAddress, int mPortAddress, int mUserID) {
        NettyTcpCommonClient.getInstance().connServer(mIPAddress,mPortAddress,1);
    }

    @Override
    public void registerLoginServer() {
        RegisterTermital data = new RegisterTermital();
        data.setiCmdEnum(601);
        data.setiTerminalID(1);
        data.setiRoomID(0);
        data.setiTerminalType(2);
        data.setStrGUID(0);
        NettyTcpCommonClient.sendPackage(data);
    }
}
