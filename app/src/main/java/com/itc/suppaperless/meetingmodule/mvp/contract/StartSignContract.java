package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface StartSignContract {
    interface View extends BaseView{
       void getJiaoliuUserInfo(JiaoLiuUserInfo jiaoLiuUserInfo);
       void signSucceeful();
    }
    interface Model{
        void sign(int userId,String  userName,String pFilePath, String pFileName);


    }
    interface Presenter extends IBaseXPresenter{
    }
}
