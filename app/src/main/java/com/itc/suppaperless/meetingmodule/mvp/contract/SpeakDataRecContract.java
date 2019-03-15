package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.BaseModel;

/**
 * Create by zhengwp on 19-2-22.
 *
 * 文档主讲接收数据 mvp
 * 构建在MainActivity层
 */
public interface SpeakDataRecContract {
    interface SpeakDataView extends BaseView{
    }

    interface SpeakDataModel{
    }

    interface SpeakDataPresenter extends IBaseXPresenter{
    }
}
