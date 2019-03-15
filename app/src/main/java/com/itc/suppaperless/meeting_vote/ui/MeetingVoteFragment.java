package com.itc.suppaperless.meeting_vote.ui;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.meeting_vote.adapter.VoteAdapter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.VotingControlBean;
import com.itc.suppaperless.meeting_vote.contract.MeetingVoteContract;
import com.itc.suppaperless.meeting_vote.presenter.MeetingVotePresenter;
import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.itc.suppaperless.global.Config.IS_COMPLETE;
import static com.itc.suppaperless.global.Config.LST_VOTE;
import static com.itc.suppaperless.global.Config.MEETING_ID;

public class MeetingVoteFragment extends BaseFragment implements MeetingVoteContract.MeetingVoteUI{
    @BindView(R.id.rv_vote)
    RecyclerView rvVote;
    @BindView(R.id.MultiStateView)
    MultiStateView mMultiStateView;

    private VoteAdapter mVoteAdapter;
    private MeetingVotePresenter mMeetingVotePresenter;
    private List<MeetingVoteBean.LstVoteBean> mLstVote;
    private int mVotingControlType = 0,mVoteID;

    public MeetingVoteFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_meeting_vote;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new MeetingVotePresenter(this);
    }

    @Override
    public void init() {
        mLstVote = new ArrayList<>();
        mMeetingVotePresenter = (MeetingVotePresenter) getPresenter();

        mVoteAdapter = new VoteAdapter(R.layout.item_meeting_vote);
        rvVote.setAdapter(mVoteAdapter);
        rvVote.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            mMeetingVotePresenter.getMeetingVote(AppDataCache.getInstance().getInt(MEETING_ID));
        }
    }

    @Override
    public void getMeetingVoteSuccess(List<MeetingVoteBean.LstVoteBean> lstVote) {
        mLstVote = lstVote;
        isEmptyList(lstVote);
        switch (mVotingControlType){
            case 1:
                openActivity(mLstVote,VoteActivity.class);
                mVotingControlType = 0;
                break;
            case 2:
                openActivity(mLstVote,VotingManagerDetailActivity.class);
                mVotingControlType = 0;
                break;
        }
    }

    @Override
    public void getVotingControl(VotingControlBean votingControlBean) {
        mVotingControlType = votingControlBean.getIControlType();
        mVoteID = votingControlBean.getIVoteID();
        if (mLstVote.size() == 0){
            mMeetingVotePresenter.getMeetingVote(AppDataCache.getInstance().getInt(MEETING_ID));
        }else {
            for (MeetingVoteBean.LstVoteBean mLstVoteBean: mLstVote){
                if (mLstVoteBean.getIVoteID() == mVoteID){
                    mLstVoteBean.setIStatus(mVotingControlType);
                }
            }
            if (mVotingControlType == 1){
                openActivity(mLstVote,VoteActivity.class);
            }else {
                openActivity(mLstVote,VotingManagerDetailActivity.class);
            }
            mVotingControlType = 0;
            setLstVotes();
        }
    }

    @Override
    public void votingUpdate(MeetingVoteBean.LstVoteBean lstVote) {
        if (lstVote.getiUpdateType() == 3){
            for (int i = 0;i < mLstVote.size();i++){
                MeetingVoteBean.LstVoteBean mLstVoteBean = mLstVote.get(i);
                if (mLstVoteBean.getIVoteID() == lstVote.getIVoteID()){
                    mLstVote.remove(mLstVoteBean);
                    i -= 1;
                }
            }
        }else {
            mLstVote.add(lstVote);
        }
        setLstVotes();
    }
    /**
     *未启用的删除，不显示
     */
    private List<MeetingVoteBean.LstVoteBean> removeNotEnabled(List<MeetingVoteBean.LstVoteBean> lstVote){
        for (int i = 0;i < lstVote.size();i++){
            MeetingVoteBean.LstVoteBean mLstVoteBean = lstVote.get(i);
            if (mLstVoteBean.getIStatus() == 0){
                lstVote.remove(mLstVoteBean);
                i -= 1;
            }
        }
        return lstVote;
    }
    /**
     * 判断list是否为空
     */
    private void isEmptyList(List<MeetingVoteBean.LstVoteBean> lstVote){
        List<MeetingVoteBean.LstVoteBean> lstVoteBeans = removeNotEnabled(lstVote);
        if (lstVoteBeans.size() != 0){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            mVoteAdapter.setNewData(lstVoteBeans);
        }
    }

    private void setLstVotes(){
        List<MeetingVoteBean.LstVoteBean> lstVoteList = new ArrayList<>();
        lstVoteList.addAll(mLstVote);
        isEmptyList(lstVoteList);
    }
    /**
     * 打开投票页面
     */
    private void openActivity(List<MeetingVoteBean.LstVoteBean> lstVote, Class<?> mClass){
        MeetingVoteBean.LstVoteBean item = null;
        for (MeetingVoteBean.LstVoteBean lstVoteBean : lstVote){
            if (lstVoteBean.getIVoteID() == mVoteID){
                item = lstVoteBean;
            }
        }
        if (item != null){
            Intent intent = new Intent(getActivity(),mClass);
            String lstVoteStr = new Gson().toJson(item);
            intent.putExtra(LST_VOTE,lstVoteStr);
            intent.putExtra(IS_COMPLETE,false);
            startActivity(intent);
        }
    }
}
