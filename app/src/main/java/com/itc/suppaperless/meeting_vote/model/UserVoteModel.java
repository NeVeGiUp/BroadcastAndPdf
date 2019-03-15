package com.itc.suppaperless.meeting_vote.model;



import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meeting_vote.bean.SendMeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.SendUserVoteBean;
import com.itc.suppaperless.meeting_vote.contract.MeetingVoteContract;
import com.itc.suppaperless.meeting_vote.contract.UserVoteContract;

public class UserVoteModel implements UserVoteContract.UserVoteMdl{

    @Override
    public void userVote(int iUserID, int iVoteID, String strComment,int[] aiOptionID) {
        SendUserVoteBean mSendUserVoteBean = new SendUserVoteBean();
        mSendUserVoteBean.setiCmdEnum(235);
        mSendUserVoteBean.setiUserID(iUserID);
        mSendUserVoteBean.setiVoteID(iVoteID);
        mSendUserVoteBean.setStrComment(strComment);
        mSendUserVoteBean.setAiOptionID(aiOptionID);
        NettyTcpCommonClient.sendPackage(mSendUserVoteBean);
    }

    @Override
    public void getMeetingVote(int iMeetingID) {
        SendMeetingVoteBean mSendMeetingVoteBean = new SendMeetingVoteBean();
        mSendMeetingVoteBean.setiCmdEnum(216);
        mSendMeetingVoteBean.setiMeetingID(iMeetingID);
        NettyTcpCommonClient.sendPackage(mSendMeetingVoteBean);
    }
}
