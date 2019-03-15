package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;

import java.util.List;

/**
 * Created by xiaogf on 19-1-23.
 */

public interface CenterControlContract {
    interface View extends BaseView{

    }
    interface Model{
       void centerControl(int nControlType);

    }
    interface Presenter extends IBaseXPresenter{
        void centerControl(int nControlType);

    }
}
