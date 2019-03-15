package com.itc.suppaperless.meeting_vote.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.VoteUpdateBean;
import com.itc.suppaperless.meeting_vote.bean.VotingControlBean;
import com.itc.suppaperless.meeting_vote.contract.MeetingVoteContract;
import com.itc.suppaperless.meeting_vote.event.MeetingVoteEvent;
import com.itc.suppaperless.meeting_vote.event.VotingControlEvent;
import com.itc.suppaperless.meeting_vote.event.VotingUpdateEvent;
import com.itc.suppaperless.meeting_vote.model.MeetingVoteModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by cong on 19-3-7.
 */

public class MeetingVotePresenter extends BasePresenter<MeetingVoteContract.MeetingVoteUI,MeetingVoteContract.MeetingVoteMdl> implements
        MeetingVoteContract.MeetingVotePresenter{
    public MeetingVotePresenter(MeetingVoteContract.MeetingVoteUI view) {
        super(view);
        mModel = new MeetingVoteModel();
        EventBus.getDefault().register(this);
    }

    @Override
    public void getMeetingVote(int iMeetingID) {
        mModel.getMeetingVote(iMeetingID);
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 获取会议投票信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeetingVoteEvent(MeetingVoteEvent mMeetingVoteEvent) {
        MeetingVoteBean mMeetingVoteBean = new Gson().fromJson(mMeetingVoteEvent.getJsonData(), MeetingVoteBean.class);
        List<MeetingVoteBean.LstVoteBean> lstVote = mMeetingVoteBean.getLstVote();
        getView().getMeetingVoteSuccess(lstVote);
    }
    /**
     * 投票控制
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVotingControlEvent(VotingControlEvent votingControlEvent) {
        VotingControlBean mVotingControlBean = new Gson().fromJson(votingControlEvent.getJsonData(), VotingControlBean.class);
        getView().getVotingControl(mVotingControlBean);
    }
    /**
     * 投票列表更新
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVotingUpdateEvent(VotingUpdateEvent votingUpdateEvent) {
        MeetingVoteBean.LstVoteBean lstVoteBean = new Gson().fromJson(votingUpdateEvent.getJsonData(), MeetingVoteBean.LstVoteBean.class);
        getView().votingUpdate(lstVoteBean);
    }
}
