package com.itc.suppaperless.meeting_vote.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meeting_vote.contract.VoteManageDetailContract;
import com.itc.suppaperless.meeting_vote.model.VoteManageDetailModel;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.utils.GsonUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

/**
 * Created by cong on 19-3-7.
 */

public class VoteManageDetailPresenter extends BasePresenter<VoteManageDetailContract.VoteManageDetailUI,
        VoteManageDetailContract.VoteManageDetailMdl> implements VoteManageDetailContract.VoteManageDetailPresenter{

    public VoteManageDetailPresenter(VoteManageDetailContract.VoteManageDetailUI view) {
        super(view);
        mModel = new VoteManageDetailModel();
        EventBus.getDefault().register(this);
    }
    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getUserList() {
        mModel.getUserList();
    }

    /**
     * 投票控制
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJiaoLiuUserEvent(JiaoLiuUserEvent jiaoLiuUserEvent) {
        JiaoLiuUserInfo jiaoLiuUserInfo = GsonUtil.getJsonObject(jiaoLiuUserEvent.getData(), JiaoLiuUserInfo.class);
        List<JiaoLiuUserInfo.LstUserBean> lstUser = jiaoLiuUserInfo.getLstUser();
        getView().getUserListSuccess(lstUser);
    }
}
