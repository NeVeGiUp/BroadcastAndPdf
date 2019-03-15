package com.itc.suppaperless.meeting_vote.model;



import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.loginmodule.bean.RegisterTermital;
import com.itc.suppaperless.meeting_vote.bean.SendMeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.SendUserVoteBean;
import com.itc.suppaperless.meeting_vote.contract.MeetingVoteContract;
import com.itc.suppaperless.switch_conference.bean.SendMeetingListBean;
import com.itc.suppaperless.switch_conference.contract.MeetingListContract;

public class MeetingVoteModel implements MeetingVoteContract.MeetingVoteMdl{
    @Override
    public void getMeetingVote(int iMeetingID) {
        SendMeetingVoteBean mSendMeetingVoteBean = new SendMeetingVoteBean();
        mSendMeetingVoteBean.setiCmdEnum(216);
        mSendMeetingVoteBean.setiMeetingID(iMeetingID);
        NettyTcpCommonClient.sendPackage(mSendMeetingVoteBean);
    }
}
