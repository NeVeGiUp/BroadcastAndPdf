package com.itc.suppaperless.meetingmodule.mvp.model;

import android.util.Log;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.TopicStatus;
import com.itc.suppaperless.meetingmodule.mvp.contract.TopicManagementContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class TopicManagementModel implements TopicManagementContract.Model{

    @Override
    public void setTopicStatus(int topicId,int type) {
        TopicStatus topicStatus = new TopicStatus();
        topicStatus.setiCmdEnum(271);
        topicStatus.setiIssueID(topicId);
        topicStatus.setiControlType(type);
        NettyTcpCommonClient.sendPackage(topicStatus);
    }

    @Override
    public void setTopicReady(int topicId, int time) {
        OkGo.<String>post("http://" + AppDataCache.getInstance().getString(Config.IP_ADDRESS)
                + "/api/system/update_datum_status")
                .params("datum_id", topicId)
                .params("status", 3)
                .params("time",time)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //这里已经是在主线程了
                        String data = response.body();//这个就是返回来的结果
                        Log.e( "json: ", data);
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


}
