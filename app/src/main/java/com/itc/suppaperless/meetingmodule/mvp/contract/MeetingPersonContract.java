package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface MeetingPersonContract {
    interface View extends BaseView{
        void getPerson(JiaoLiuUserInfo jiaoLiuUserInfo);
    }
    interface Model{
       void getPerson();

    }
    interface Presenter extends IBaseXPresenter{
        void getPerson();

    }
}
