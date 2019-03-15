package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.CailiaoContract;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class CailiaoPresenter extends SpeakDataRecPresenter<CailiaoContract.View, CailiaoContract.Model> implements CailiaoContract.Presenter{
    public CailiaoPresenter(CailiaoContract.View view) {
        super(view);
    }

    //文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FileUpdateEvent event) {//文件更新
        getView().getUpdateFile(GsonUtil.getJsonObject(event.getData(), CommentUploadListInfo.class).getLstFile());
    }
}
