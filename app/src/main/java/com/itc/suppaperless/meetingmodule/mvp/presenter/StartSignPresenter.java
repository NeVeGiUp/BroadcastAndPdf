package com.itc.suppaperless.meetingmodule.mvp.presenter;

import android.view.View;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.UploadSuccessEvent;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.StartSignContract;
import com.itc.suppaperless.meetingmodule.mvp.model.StartSignModel;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class StartSignPresenter extends BasePresenter<StartSignContract.View,StartSignContract.Model> implements StartSignContract.Presenter{
    public StartSignPresenter(StartSignContract.View view) {
        super(view);
        mModel=new StartSignModel();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JiaoLiuUserEvent event) {//更新主持人
        AppDataCache.getInstance().putString(Config.SAVE_USER, event.getData());
        getView().getJiaoliuUserInfo(GsonUtil.getJsonObject(event.getData(), JiaoLiuUserInfo.class));

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUpFileSucceeful(UploadSuccessEvent uploadSuccessEvent){
        mModel.sign(AppDataCache.getInstance().getInt(Config.USER_ID),AppDataCache.getInstance().getString(Config.USER_NAME),
                uploadSuccessEvent.getResponse(),Config.SIGNIFILEMAGE);
        getView().signSucceeful();

    }
    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }


}
