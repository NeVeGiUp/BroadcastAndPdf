package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.mvp.contract.CailiaoContract;
import com.itc.suppaperless.meetingmodule.mvp.contract.CenterControlContract;
import com.itc.suppaperless.meetingmodule.mvp.model.CenterControlModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xiaogf on 19-1-23.
 */

public class CenterControlPresenter extends BasePresenter<CenterControlContract.View,CenterControlContract.Model> implements CenterControlContract.Presenter{
    public CenterControlPresenter(CenterControlContract.View view) {
        super(view);
        mModel=new CenterControlModel();
//        EventBus.getDefault().register(this);
    }

    @Override
    public void centerControl(int nControlType) {
        mModel.centerControl(nControlType);

    }
//    @Override
//    public void detachView() {
//        super.detachView();
//        EventBus.getDefault().unregister(this);
//    }
}
