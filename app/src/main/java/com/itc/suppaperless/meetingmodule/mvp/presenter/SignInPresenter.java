package com.itc.suppaperless.meetingmodule.mvp.presenter;

import android.support.annotation.MainThread;

import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.meetingmodule.bean.GetStartOrEndSign;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.PresideSignInfo;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.meetingmodule.eventbean.QiandaoControlEvent;
import com.itc.suppaperless.meetingmodule.eventbean.StartOrEndSignEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.SignInContract;
import com.itc.suppaperless.meetingmodule.mvp.model.SignInModel;
import com.itc.suppaperless.utils.GsonUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class SignInPresenter extends BasePresenter<SignInContract.View,SignInContract.Model> implements SignInContract.Presenter{
    public SignInPresenter(SignInContract.View view) {
        super(view);
        mModel=new SignInModel();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSignMessage(QiandaoControlEvent event){//收到签到控制消息
        PresideSignInfo info= GsonUtil.getJsonObject(event.getData(),PresideSignInfo.class);
        getView().getSignMessage(info);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JiaoLiuUserEvent event) {//用户列表
        JiaoLiuUserInfo jiaoLiuUserInfo= GsonUtil.getJsonObject(event.getData(), JiaoLiuUserInfo.class);
        getView().getPerson(jiaoLiuUserInfo);
    }

    @Override
    public void unifySign(int[] aiUserID, String strTime) {
        mModel.unifySign(aiUserID,strTime);

    }

    @Override
    public void startOrEndSign(int tyle) {
        mModel.startOrEndSign(tyle);

    }
    @Subscribe(threadMode=ThreadMode.MAIN)
    public void getStartOrEndSign(StartOrEndSignEvent startOrEndSignEvent){
        GetStartOrEndSign getStartOrEndSign=GsonUtil.getJsonObject(startOrEndSignEvent.getData(),GetStartOrEndSign.class);
        getView().getStartOrEndSign(getStartOrEndSign);
    }
    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void signScreen(int type) {
        mModel.signScreen(type);
    }

}
