package com.itc.suppaperless.meeting_vote.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.meeting_vote.adapter.VoteManageAdapter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.VotingControlBean;
import com.itc.suppaperless.meeting_vote.contract.VoteManageContract;
import com.itc.suppaperless.meeting_vote.presenter.VoteManagePresenter;
import com.itc.suppaperless.widget.DialogNewInterface;
import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.itc.suppaperless.global.Config.MEETING_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoteManageFragment extends BaseFragment implements VoteManageContract.VoteManageUI{
    @BindView(R.id.rv_vote_manage)
    RecyclerView rvVoteManage;
    @BindView(R.id.MultiStateView)
    com.kennyc.view.MultiStateView mMultiStateView;

    private VoteManageAdapter mVoteManageAdapter;
    private VoteManagePresenter mVoteManagePresenter;
    private boolean isHidden;
    private List<MeetingVoteBean.LstVoteBean> mLstVote;

    public VoteManageFragment() {
        isHidden = true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_vote_manage;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new VoteManagePresenter(this);
    }

    @Override
    public void init() {
        DialogNewInterface mDialogNewInterface = new DialogNewInterface(getActivity());
        mVoteManagePresenter = (VoteManagePresenter) getPresenter();
        mLstVote = new ArrayList<>();

        mVoteManageAdapter = new VoteManageAdapter(R.layout.item_meeting_vote,mVoteManagePresenter,mDialogNewInterface);
        rvVoteManage.setAdapter(mVoteManageAdapter);
        rvVoteManage.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        isHidden = hidden;
        if (!hidden){
            mVoteManagePresenter.getMeetingVote(AppDataCache.getInstance().getInt(MEETING_ID));
        }
    }

    @Override
    public void getMeetingVoteSuccess(List<MeetingVoteBean.LstVoteBean> lstVote) {
        mLstVote.clear();
        mLstVote.addAll(lstVote);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mVoteManageAdapter.setNewData(lstVote);
    }

    @Override
    public void getVotingControl(VotingControlBean votingControlBean) {
        int mVotingControlType = votingControlBean.getIControlType();
        int mVoteID = votingControlBean.getIVoteID();
        for (MeetingVoteBean.LstVoteBean mLstVoteBean: mLstVote){
            if (mLstVoteBean.getIVoteID() == mVoteID){
                mLstVoteBean.setIStatus(mVotingControlType);
            }
        }
        mVoteManageAdapter.setNewData(mLstVote);
    }

    @Override
    public void votingUpdate(MeetingVoteBean.LstVoteBean lstVote) {
        if (!isHidden){
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
            if (mLstVote.size() == 0){
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }else {
                mVoteManageAdapter.setNewData(mLstVote);
            }
        }
    }
}
