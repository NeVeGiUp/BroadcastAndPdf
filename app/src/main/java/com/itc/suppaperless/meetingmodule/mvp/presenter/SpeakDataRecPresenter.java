package com.itc.suppaperless.meetingmodule.mvp.presenter;


import android.content.Context;
import android.util.Log;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.SpeakDataRecContract;
import com.itc.suppaperless.pdfmodule.bean.GsonDocOperationBean;
import com.itc.suppaperless.pdfmodule.bean.SpeakDataTransfer;
import com.itc.suppaperless.pdfmodule.configure.PdfConfigure;
import com.itc.suppaperless.pdfmodule.eventbean.DocSpeakEvent;
import com.itc.suppaperless.pdfmodule.eventbean.RightBarTrackSpeakEvent;
import com.itc.suppaperless.pdfmodule.eventbean.SpeakData2QueueEvent;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;
import com.itc.suppaperless.switch_conference.event.ToastMsgEvent;
import com.itc.suppaperless.pdfmodule.utils.PdfUtil;
import com.itc.suppaperless.switch_conference.ui.MainActivity;
import com.itc.suppaperless.utils.DownloadFileUtil;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.GsonUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.itc.suppaperless.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Create by zhengwp on 19-2-22.
 */
public class SpeakDataRecPresenter<V extends SpeakDataRecContract.SpeakDataView, M extends SpeakDataRecContract.SpeakDataModel>
                extends BasePresenter<V, M> implements SpeakDataRecContract.SpeakDataPresenter {
    private Context mContext;
    /** This configure means whether the first time when receive the presentation-data when changing file. */
    private boolean firstReceiveNewFile;
    /** This configure means whether the first time when receive the presentation-data at one time. */
    private boolean firstReceivedata;
    /** Auto back to track document presentation, also means the first time receive the operation of 'open-file'. */
    private boolean autoBack2Track;
    /** The object of data. */
    private String speakData;
    private GsonDocOperationBean operationBean;
    /** The queue is defined to set down the message of document annotation. */
    private Queue<String> annotationQueue;

    public SpeakDataRecPresenter(V view) {
        super(view);
        this.annotationQueue = new LinkedList<String>();
        this.mContext = getView().getActivity();
        this.firstReceiveNewFile = true;
        this.firstReceivedata = true;
        autoBack2Track = true;
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void DocSpeakDataReceiveEvent(SpeakData2QueueEvent event){
        /**
         * when Asynchronous browsing.
         * when the pdf activity change the status from off-line to on-line,
         * you should first judge that whether the queue contains two 2001(operation-code) operations?
         * which means changing to another file in document speaking.
         * If it exists , make a filter and get which one is correct.
         */
        this.speakData = event.getStr();
        //Get the operation code and handle it.(Jump into 'PdfConfigure.class' to see the describtion about operation code.)
        int optCode = 0;
        try {
            optCode = (new JSONObject(this.speakData)).getInt(PdfConfigure.optCodeStr);
            /** Firstly compare with the old-data to suppose the status (firstReceiveNewFile). */
            if ( !firstReceivedata ){
                firstReceiveNewFile = ( (new JSONObject(this.speakData)).getInt(PdfConfigure.fid) ) == this.operationBean.getfId() ? false : true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** If we send the object which extends the GsonOpenDocBean,
          * So we can get spart of the values and then give to the bean.
          */
        switch (optCode) {
            case PdfConfigure.OptCode.OpenFile:
            case PdfConfigure.OptCode.Zoom:
            case PdfConfigure.OptCode.Scroll:
            case PdfConfigure.OptCode.TurnPage:
                this.operationBean = GsonUtil.getJsonObject(this.speakData, GsonDocOperationBean.class);
                //Set the global data when user receive the first presentation-data.
                if (firstReceivedata){
                    ToastUtil.show(mContext, operationBean.getSName() + " " + mContext.getResources().getString(R.string.is_speak));
                    PdfUtil.setIsSpeaking(true, operationBean.getSId(), operationBean.getSName());
                    firstReceivedata = false;
                }
                /** The judgement about the exist of file which store in stroage. */
                if ( PdfUtil.checkFileExistInStorage(operationBean.getfId(), operationBean.getfSysname()) ) {
                    if ( firstReceiveNewFile ){
                        clearQueue();
                        firstReceiveNewFile = false;
                    }

                    //Reset the right navigation in this method.
                    ((MainActivity) mContext).getmRightNavigationPopView().setTrackStatus(true);

                    if (PdfUtil.checkTrackSpeak()){
                        EventBus.getDefault().post(new DocSpeakEvent(this.speakData));
                    }else {
                        if (optCode == PdfConfigure.OptCode.OpenFile && autoBack2Track){
                            autoBack2Track = false;
                            PdfUtil.setTrackSpeak(true);
                            if ( PdfUtil.checkPdfActivityOpen() ){
                                EventBus.getDefault().post(new DocSpeakEvent(this.speakData));
                            }else {
                                FileOpenUtil.OpenFile(mContext, operationBean.getfId(), operationBean.getFname(),
                                        operationBean.getfSysname(), operationBean.getfDownPath(), operationBean.getIsAgenda(), new SpeakDataTransfer(speakData, annotationQueue));
                            }
                        }else {
//                            annotationQueue.offer(this.speakData);
                        }
                    }
                }else {
                    //It means changing file but this user didnt receive the 'open new file' operation when 'firstReceiveNewFile' equals true;
                    if (firstReceiveNewFile){
                        clearQueue();
                        firstReceiveNewFile = false;
                        /** Whether tracking presentation */
                        if (PdfUtil.checkTrackSpeak()){
                            EventBus.getDefault().post(new ToastMsgEvent(Config.ActivityManage.PdfBrowseActivity, mContext.getResources().getString(R.string.pdf_tracking_null_file)));
                            PdfUtil.setTrackSpeak(false);
                            this.autoBack2Track = true;
                        }else {
                            ToastUtil.show(mContext, mContext.getResources().getString(R.string.pdf_null_file));
                        }
                        //Reset the right navigation in this method.
                        ((MainActivity) mContext).getmRightNavigationPopView().setTrackStatus(false);
                        //Start to download file.
                        DownloadFileUtil.getInstance().setDownFile(operationBean.getfDownPath(), operationBean.getfId());
                    }else{
                        /** Store the message of annotation*/
//                        annotationQueue.offer(this.speakData);
                    }
                }
                break;
            case PdfConfigure.OptCode.CloseSpeaker:
                if (PdfUtil.checkTrackSpeak()){
                    EventBus.getDefault().post(new DocSpeakEvent(this.speakData));
                }else{
                    /** If dont track presentation equals to the activity is not open.*/
                    ToastUtil.show(mContext, mContext.getResources().getString(R.string.ending_speak));
                }
                //Reset the right navigation in this method.
                ((MainActivity) mContext).getmRightNavigationPopView().setTrackStatus(false);
                PdfUtil.setIsSpeaking(false, 0, "");
                this.firstReceivedata = true;
                this.firstReceiveNewFile = true;
                this.autoBack2Track = true;
                break;
            default:
                break;
        }
    }

    //Downloading finish.
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnDownFileFinishEvent(DownloadEvent event){
        if (PdfUtil.checkIsSpeaking() && !PdfUtil.checkPresentationUser() && operationBean != null ){
            if (Integer.parseInt(event.getData().tag) == (operationBean.getfId())){
                /** Reset the right navigation in this method. */
                ((MainActivity) mContext).getmRightNavigationPopView().setTrackStatus(true);

                openPdfAct2TrackSpeak(true);
            }
        }
    }

    /**
     * On receive the tracking event by the button which named '跟踪主讲/异步浏览' in two Activity.
     * But in this presentation, the operation leads to open tracking of document.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnTrackSpeakEvent(RightBarTrackSpeakEvent event){
        /**
         * Firstly make a judgement about the presentaton-user who is speanking now.
         * And then execute the right method, also has two situations about the open/close of Activity;
         */
        if (PdfUtil.checkIsSpeaking() &&  !PdfUtil.checkPresentationUser() ){
            if ( !PdfUtil.checkTrackSpeak() ) {
                //Set the tracking status from false to true;
                PdfUtil.setTrackSpeak(true);
                if (!PdfUtil.checkPdfActivityOpen()) {
                    FileOpenUtil.OpenFile(mContext, operationBean.getfId(), operationBean.getFname(),
                            operationBean.getfSysname(), operationBean.getfDownPath(), operationBean.getIsAgenda(), new SpeakDataTransfer(speakData, annotationQueue));
                } else {
                    // The situation that Activity is opening but not track the presentation which means the user is looking other document now.
                    // So we cant generate another new activity because of the memory management.
                    EventBus.getDefault().post(new DocSpeakEvent(this.speakData));
//                    while (annotationQueue.size() > 0) {
//                        EventBus.getDefault().post(new DocSpeakEvent(annotationQueue.poll()));
//                    }
                }
            }else {
                EventBus.getDefault().post(new ToastMsgEvent(Config.ActivityManage.PdfBrowseActivity, mContext.getResources().getString(R.string.close_doc_track)));
            }
        }
        ((MainActivity) getView().getActivity()).getmRightNavigationPopView().dismiss();
    }

    /** Connection status.*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ConnectionStatusEvent event) {
        switch (event.getConnectionStatus()){
            case 1001:
            case 1002:
                if (!PdfUtil.checkPresentationUser() && PdfUtil.checkIsSpeaking()) {
                    if (! PdfUtil.checkPdfActivityOpen()){
                        ToastUtil.show(mContext, mContext.getResources().getString(R.string.null_network) + "," + mContext.getResources().getString(R.string.ending_speak));
                        PdfUtil.setIsSpeaking(false, 0, "");
                    }
                    //Reset the right navigation in this method.
                    ((MainActivity) mContext).getmRightNavigationPopView().setTrackStatus(false);
                    PdfUtil.setIsSpeaking(false, 0, "");
                    this.firstReceivedata = true;
                    this.firstReceiveNewFile = true;
                }
                break;
            case 1003:
                break;
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    //Open Activity to track document presentation.
    private void openPdfAct2TrackSpeak(boolean isdownload){
        /** Activity open? */
        if ( PdfUtil.checkPdfActivityOpen() ){
            //force user to open track if possible;
            if (PdfConfigure.forceTrackSpeak || autoBack2Track){
                PdfUtil.setTrackSpeak(true);
                if (autoBack2Track){
                    autoBack2Track = false;
                }
                EventBus.getDefault().post(new DocSpeakEvent(this.speakData));
                if (isdownload){
                    EventBus.getDefault().post(new ToastMsgEvent(Config.ActivityManage.PdfBrowseActivity, mContext.getResources().getString(R.string.pdf_file_ready_forceTrack)));
                }else {
//                    EventBus.getDefault().post(new ToastMsgEvent(Config.ActivityManage.PdfBrowseActivity, mContext.getResources().getString(R.string.pdf_file_ready_forceTrack)));
                }
            }else {
                EventBus.getDefault().post(new ToastMsgEvent(Config.ActivityManage.PdfBrowseActivity, mContext.getResources().getString(R.string.pdf_file_ready)));
            }
        }else {
            //force user to open track if possible;
            if (PdfConfigure.forceTrackSpeak || autoBack2Track){
                if (autoBack2Track){
                    autoBack2Track = false;
                }
                ToastUtil.show(mContext, mContext.getResources().getString(R.string.pdf_file_ready_forceTrack));
                PdfUtil.setTrackSpeak(true);
                FileOpenUtil.OpenFile(mContext, operationBean.getfId(), operationBean.getFname(),
                        operationBean.getfSysname(), operationBean.getfDownPath(), operationBean.getIsAgenda(), new SpeakDataTransfer(speakData, annotationQueue));
            }else {
                ToastUtil.show(mContext, mContext.getResources().getString(R.string.pdf_file_ready));
            }
        }
    }

    private void clearQueue(){
        this.annotationQueue.clear();
    }

}
