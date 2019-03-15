package com.itc.suppaperless.meeting_vote.contract;


import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.VotingControlBean;

import java.util.List;

public interface MeetingVoteContract {
    /**
     * view 层接口
     */
    interface MeetingVoteUI extends BaseView{
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
    interface MeetingVotePresenter extends IBaseXPresenter {
        //获取会议投票信息
        void getMeetingVote(int iMeetingID);
    }
    /**
     * model 层接口
     */
    interface MeetingVoteMdl{
        //获取会议投票信息
        void getMeetingVote(int iMeetingID);
    }
}