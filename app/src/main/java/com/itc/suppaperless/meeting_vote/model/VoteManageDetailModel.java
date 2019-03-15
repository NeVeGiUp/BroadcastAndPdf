package com.itc.suppaperless.meeting_vote.model;



import com.itc.suppaperless.base.BaseBean;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meeting_vote.bean.SendMeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.SendVotingControlBean;
import com.itc.suppaperless.meeting_vote.contract.VoteManageContract;
import com.itc.suppaperless.meeting_vote.contract.VoteManageDetailContract;

public class VoteManageDetailModel implements VoteManageDetailContract.VoteManageDetailMdl{
    @Override
    public void getUserList() {
        BaseBean mBaseBean = new BaseBean();
        mBaseBean.setiCmdEnum(210);
        NettyTcpCommonClient.sendPackage(mBaseBean);
    }
}
