package com.itc.suppaperless.meeting_vote.presenter;

import com.google.gson.Gson;
import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.UserVoteBean;
import com.itc.suppaperless.meeting_vote.bean.VotingControlBean;
import com.itc.suppaperless.meeting_vote.contract.MeetingVoteContract;
import com.itc.suppaperless.meeting_vote.contract.UserVoteContract;
import com.itc.suppaperless.meeting_vote.event.MeetingVoteEvent;
import com.itc.suppaperless.meeting_vote.event.UserVoteEvent;
import com.itc.suppaperless.meeting_vote.event.VotingControlEvent;
import com.itc.suppaperless.meeting_vote.model.MeetingVoteModel;
import com.itc.suppaperless.meeting_vote.model.UserVoteModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.itc.suppaperless.global.Config.MEETING_ID;

/**
 * Created by cong on 19-3-7.
 */

public class UserVotePresenter extends BasePresenter<UserVoteContract.UserVoteUI,UserVoteContract.UserVoteMdl> implements
        UserVoteContract.UserVotePresenter{
    public UserVotePresenter(UserVoteContract.UserVoteUI view) {
        super(view);
        mModel = new UserVoteModel();
        EventBus.getDefault().register(this);
    }
    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void userVote(int iUserID, int iVoteID, String strComment, int[] aiOptionID) {
        mModel.userVote(iUserID,iVoteID,strComment,aiOptionID);
    }
    /**
     * 会议投票实时状态
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserVoteEvent(UserVoteEvent userVoteEvent) {
        UserVoteBean mUserVoteBean = new Gson().fromJson(userVoteEvent.getJsonData(), UserVoteBean.class);
        mModel.getMeetingVote(AppDataCache.getInstance().getInt(MEETING_ID));
        getView().userVoteFinish(mUserVoteBean);
    }
    /**
     * 投票控制
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVotingControlEvent(VotingControlEvent votingControlEvent) {
        VotingControlBean mVotingControlBean = new Gson().fromJson(votingControlEvent.getJsonData(), VotingControlBean.class);
        if (mVotingControlBean.getIControlType() == 2){
            getView().getVotingControl();
        }
    }
}
