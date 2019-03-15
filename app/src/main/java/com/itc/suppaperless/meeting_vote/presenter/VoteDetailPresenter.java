package com.itc.suppaperless.meeting_vote.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meeting_vote.contract.VoteDetailContract;
import com.itc.suppaperless.meeting_vote.model.VoteDetailModel;

/**
 * Created by xiaogf on 19-1-23.
 */

public class VoteDetailPresenter extends BasePresenter<VoteDetailContract.View,VoteDetailContract.Model> implements VoteDetailContract.Presenter{
    public VoteDetailPresenter(VoteDetailContract.View view) {
        super(view);
        mModel=new VoteDetailModel();
//        EventBus.getDefault().register(this);
    }

}
