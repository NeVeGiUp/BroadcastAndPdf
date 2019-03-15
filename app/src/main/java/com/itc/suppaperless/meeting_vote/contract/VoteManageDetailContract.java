package com.itc.suppaperless.meeting_vote.contract;


import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;

import java.util.List;

public interface VoteManageDetailContract {
    /**
     * view 层接口
     */
    interface VoteManageDetailUI extends BaseView{
        //获取用户列表成功
        void getUserListSuccess(List<JiaoLiuUserInfo.LstUserBean> lstUser);
    }
    /**
     * presenter 层接口
     */
    interface VoteManageDetailPresenter extends IBaseXPresenter {
        //终端获取用户列表
        void getUserList();
    }
    /**
     * model 层接口
     */
    interface VoteManageDetailMdl{
        //终端获取用户列表
        void getUserList();
    }
}