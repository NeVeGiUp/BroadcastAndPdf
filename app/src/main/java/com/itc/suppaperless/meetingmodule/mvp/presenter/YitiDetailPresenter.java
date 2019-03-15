package com.itc.suppaperless.meetingmodule.mvp.presenter;

import android.content.Context;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.YitiEvent;
import com.itc.suppaperless.meetingmodule.eventbean.YitiupdataEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.YitiDetailContract;
import com.itc.suppaperless.meetingmodule.mvp.model.YitiDetailModel;
import com.itc.suppaperless.meetingmodule.ui.MeetingIssueDetailActivity;
import com.itc.suppaperless.pdfmodule.utils.PdfUtil;
import com.itc.suppaperless.screen_record.presenter.ScreenRecordPresenter;
import com.itc.suppaperless.utils.GsonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaogf on 19-1-23.
 */

public class YitiDetailPresenter extends ScreenRecordPresenter<YitiDetailContract.View,YitiDetailContract.Model> implements YitiDetailContract.Presenter{
    private Context mContext;

    public YitiDetailPresenter(YitiDetailContract.View view) {
        super(view);
        mModel = new YitiDetailModel();
        mContext = (MeetingIssueDetailActivity) (getView().getActivity());
    }

    @Override
    public void setRightNavigation() {
        if (PdfUtil.checkIsSpeaking() && !PdfUtil.checkPresentationUser()){
            getView().changeTrackStatus(true);
        }else {
            getView().changeTrackStatus(false);
        }
    }

    //241文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FileUpdateEvent event) {
        getView().setcurrentFil(GsonUtil.getJsonObject(event.getData(), CommentUploadListInfo.class).getLstFile());

    }
    //议题状态通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YitiEvent event) {
       getView().setIssueState(GsonUtil.getJsonObject(event.getData(), YitichangeInfo.class));

    }
    //议题文件更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YitiupdataEvent event) {
       getView().setIssueUpdate(GsonUtil.getJsonObject(event.getData(),IssueInfo.LstIssue.class));

    }
}
