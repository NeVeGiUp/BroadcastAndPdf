package com.itc.suppaperless.meeting_vote.model;



import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meeting_vote.bean.SendMeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.SendVotingControlBean;
import com.itc.suppaperless.meeting_vote.contract.MeetingVoteContract;
import com.itc.suppaperless.meeting_vote.contract.VoteManageContract;

public class VoteManageModel implements VoteManageContract.VoteManageMdl{
    @Override
    public void getMeetingVote(int iMeetingID) {
        SendMeetingVoteBean mSendMeetingVoteBean = new SendMeetingVoteBean();
        mSendMeetingVoteBean.setiCmdEnum(216);
        mSendMeetingVoteBean.setiMeetingID(iMeetingID);
        NettyTcpCommonClient.sendPackage(mSendMeetingVoteBean);
    }

    @Override
    public void setVotingControl(int iVoteID, int controlType) {
        SendVotingControlBean sendVotingControlBean = new SendVotingControlBean();
        sendVotingControlBean.setiCmdEnum(232);
        sendVotingControlBean.setiVoteID(iVoteID);
        sendVotingControlBean.setiControlType(controlType);
        NettyTcpCommonClient.sendPackage(sendVotingControlBean);
    }
}
