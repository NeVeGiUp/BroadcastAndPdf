package com.itc.suppaperless.meeting_vote.contract;


import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.VotingControlBean;

import java.util.List;

public interface VoteManageContract {
    /**
     * view 层接口
     */
    interface VoteManageUI extends BaseView{
        //获取会议投票信息成功
        void getMeetingVoteSuccess(List<MeetingVoteBean.LstVoteBean> lstVote);
        //获取投票控制
        void getVotingControl(VotingControlBean votingControlBean);
        //投票列表更新
        void votingUpdate(MeetingVoteBean.LstVoteBean lstVote);
    }
    /**
     * presenter 层接口
     */
    interface VoteManagePresenter extends IBaseXPresenter {
        //获取会议投票信息
        void getMeetingVote(int iMeetingID);
        //投票控制
        void setVotingControl(int iVoteID,int controlType);
    }
    /**
     * model 层接口
     */
    interface VoteManageMdl{
        //获取会议投票信息
        void getMeetingVote(int iMeetingID);
        //投票控制
        void setVotingControl(int iVoteID,int controlType);
    }
}