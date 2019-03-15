package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.MeetingSloganInfo;
import com.itc.suppaperless.meetingmodule.bean.Sogan;
import com.itc.suppaperless.meetingmodule.bean.SoganList;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface ConferenceSoganContract {
    interface View extends BaseView{
        void getSogan(Sogan sogan);
        void getSoganList(MeetingSloganInfo meetingSloganInfo);
        void getSoganAddOrD(SoganList soganList);

    }
    interface Model{
        void sendsogan();
        void changeSogan(int iCurSloganID,int iTerminalStatus);
        void exitSogan(int type);

    }
    interface Presenter extends IBaseXPresenter{
        void sendSogan();
        void changeSogan(int iCurSloganID,int iTerminalStatus);
        void exitSogan(int type);
    }
}
