package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.GetStartOrEndSign;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.PresideSignInfo;
import com.itc.suppaperless.meetingmodule.eventbean.QiandaoControlEvent;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface SignInContract {
    interface View extends BaseView{
        void getSignMessage(PresideSignInfo presideSignInfo);
        void getPerson(JiaoLiuUserInfo jiaoLiuUserInfo);
        void getStartOrEndSign(GetStartOrEndSign startOrEndSign);

    }
    interface Model{
        void unifySign(int[] aiUserID,String strTime);//统一签到
        void startOrEndSign(int tyle); //1 开始签到 2 结束签到
        void signScreen(int type);//投屏跟取消投屏
    }
    interface Presenter extends IBaseXPresenter{
        void unifySign(int[] aiUserID,String strTime);//统一签到
        void startOrEndSign(int tyle); //1 开始签到 2 结束签到
        void signScreen(int type);
    }
}
