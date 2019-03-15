package com.itc.suppaperless.meeting_vote.contract;


import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.UserVoteBean;

public interface UserVoteContract {
    /**
     * view 层接口
     */
    interface UserVoteUI extends BaseView{
        //用户投票完成
        void userVoteFinish(UserVoteBean mUserVoteBean);
        //投票控制
        void getVotingControl();
    }
    /**
     * presenter 层接口
     */
    interface UserVotePresenter extends IBaseXPresenter {
        //用户投票选项
        void userVote(int iUserID, int iVoteID, String strComment,int[] aiOptionID);
    }

    /**
     * model 层接口
     */
    interface UserVoteMdl{
        //用户投票选项
        void userVote(int iUserID, int iVoteID, String strComment,int[] aiOptionID);
        //获取会议投票信息
        void getMeetingVote(int iMeetingID);
    }
}