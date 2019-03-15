package com.itc.suppaperless.pdfmodule.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.eventbean.DeleteOrModifyEvent;
import com.itc.suppaperless.pdfmodule.bean.GsonDocOperationBean;
import com.itc.suppaperless.pdfmodule.bean.PointsBean;
import com.itc.suppaperless.pdfmodule.configure.PdfConfigure;
import com.itc.suppaperless.pdfmodule.eventbean.ApplyDocSpeakerEvent;
import com.itc.suppaperless.pdfmodule.eventbean.ChangeFileEvent;
import com.itc.suppaperless.pdfmodule.eventbean.DocSpeakEvent;
import com.itc.suppaperless.pdfmodule.mvp.contract.PdfBrowseContract;
import com.itc.suppaperless.pdfmodule.mvp.model.PdfBrowseModel;
import com.itc.suppaperless.pdfmodule.ui.PdfBrowseActivity;
import com.itc.suppaperless.pdfmodule.utils.PdfUtil;
import com.itc.suppaperless.screen_record.presenter.ScreenRecordPresenter;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;
import com.itc.suppaperless.switch_conference.event.ToastMsgEvent;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.GsonUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.DialogNewInterface;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

/**
 * Create by zhengwp on 19-1-27.
 */
public class PdfBrowsePresenter extends ScreenRecordPresenter<PdfBrowseContract.PdfBrowseView, PdfBrowseContract.PdfBrowseModel> implements PdfBrowseContract.PdfBrowsePresenter {
    private Context mContext;
    /** The loading type : '11' means load pdf file, and '12' means image file. */
    private int loadType;
    private int fId;
    private String fName;
    private String fSysName;
    private String fDownPath;
    private int isAgenda;
    private int currentPage;
    private float currentScale;
    /** Own center coordinates after algorithm conversion. */
    private PointsBean centerPoint;
    /** the status which represent that the file is loading now, and then then loading is blocking other operations. */
    private boolean fileLoading;
    /** Record the operation when user trigger the 'page-up' operation in scrolling up. */
    private boolean pdfPageUp;

    public PdfBrowsePresenter(PdfBrowseContract.PdfBrowseView view) {
        super(view);
        this.fileLoading = true;
        /** Initialize the data to avoid error handling. */
        this.pdfPageUp = false;
        /** Init the point to avoid the null poiner. */
        this.centerPoint = new PointsBean(0.0f, 0.0f);
        this.currentPage = PdfConfigure.initpage;
        this.currentScale = PdfConfigure.initscale;
        mModel = new PdfBrowseModel();
        mContext = (PdfBrowseActivity) (getView().getActivity());
    }

    @Override
    public void loadFile(int loadType, int fId, String fName, String fSysName, String fDownPath, int isAgenda){
        this.loadType = loadType;
        this.fId = fId;
        this.fName = fName;
        this.fSysName = fSysName;
        this.fDownPath = fDownPath;
        this.isAgenda = isAgenda;
        //Reset the toolbar text.
        ((PdfBrowseActivity) (mContext)).getToolBar().setTitleText(this.fName);

        if (loadType == PdfConfigure.pdfmode){
            getView().loadPdf(FileOpenUtil.getfilePrefixPath(fId) + "/" + fSysName );
            getView().initPageTurnLayout(true);
            getView().show_hideZoomLayout(true);
        }else if (loadType == PdfConfigure.imagemode){
            getView().loadImage(FileOpenUtil.getfilePrefixPath(fId) + "/" + fSysName );
            getView().initPageTurnLayout(false);
            getView().show_hideZoomLayout(true);
        }

        if (isAgenda == Config.On_Off.HighLevel){
            getView().showSwitchFile(false);
        } else {
            getView().showSwitchFile(true);
        }

        if (!PdfUtil.checkPresentationUser() && PdfUtil.checkTrackSpeak()){
            getView().setTrackViewClickEnable(this.loadType, false);
        }
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
    }

    @Override
    public void viewZoom(float scale){
        this.currentScale = scale;

        if (loadType == PdfConfigure.pdfmode){
            getView().pdfZoom(scale);
        }else if (loadType == PdfConfigure.imagemode){
            getView().imageZoom(scale);
        }
        //Send document presentation data if possible.
        sendSpeakData(true, PdfConfigure.OptCode.Zoom, 0, 0);
    }

    @Override
    public void viewScroll(float x, float y) {
        if (loadType == PdfConfigure.pdfmode){
            getView().pdfScroll(x, y);
        }else if (loadType == PdfConfigure.imagemode){
            getView().imageScroll(Float.valueOf(x).intValue(), Float.valueOf(y).intValue());
        }
    }

    @Override
    public void jumpPage(int targetPage) {
        pdfPageTurnListener(targetPage);
        getView().pdfPageJump(targetPage);
    }

    public void pdfPageTurnListener(int targetPage){
        this.currentPage = targetPage;
        //Send document presentation data if possible.
        sendSpeakData(true, PdfConfigure.OptCode.TurnPage, 0,0);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    //Apply Event
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ApplyDocSpeakerEvent(ApplyDocSpeakerEvent event){
        //Open document Speaker(File presentation) and change ui into doc-speak button.
        if ( !PdfUtil.checkIsSpeaking() ){
            setApplyDocSpeakStatus(true);
        }else {  //Open file Speaker(File presentation)
            if (PdfUtil.checkPresentationUser()){
                new DialogNewInterface(mContext).setText(mContext.getResources().getString(R.string.close_doc_speak)).setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        setApplyDocSpeakStatus(false);
                    }
                }).show();
            }else {
                ToastUtil.show(mContext, PdfUtil.getSpeakName() + " " +mContext.getResources().getString(R.string.is_speak));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnReceiveDocSpeakerEvent(DocSpeakEvent event){
        GsonDocOperationBean bean = GsonUtil.getJsonObject(event.getStr(), GsonDocOperationBean.class);
        //auto set the right navigatiion under the connected network if it is showing when changing the presentation status.
        if (((PdfBrowseActivity) mContext).getmRightNavigationPopView().isShowing()) {
            ((PdfBrowseActivity) mContext).setRightNavigationStatus();
        }

        if (!PdfUtil.checkPresentationUser() && PdfUtil.checkTrackSpeak()
                && ( ((PdfBrowseActivity) mContext).getLl_zoom().getVisibility() == View.VISIBLE
                       || ((PdfBrowseActivity) mContext).getToolBar().getVisibility() == View.VISIBLE)
                       || ((PdfBrowseActivity) mContext).getTv_jump_page().getVisibility() == View.VISIBLE ){
            getView().setTrackViewClickEnable(this.loadType, false);
        }

        if (bean.getOptCode() == PdfConfigure.OptCode.CloseSpeaker){
            //Close activity or load the Current.
            ToastUtil.show(mContext, mContext.getResources().getString(R.string.ending_speak));
            if (PdfConfigure.backToLastDoc){

            }else {
//                ((Activity) mContext).finish();
                PdfUtil.setTrackSpeak(false);
                //auto set the right navigatiion under the connected network if it is showing when changing the presentation status.
                if (((PdfBrowseActivity) mContext).getmRightNavigationPopView().isShowing()) {
                    ((PdfBrowseActivity) mContext).setRightNavigationStatus();
                }
                ((PdfBrowseActivity) mContext).setTrackViewClickEnable(this.loadType, true);
            }
        }else {
            if (this.fId != bean.getfId()) {
                this.fileLoading = true;
                this.currentPage = bean.getPage();
                this.currentScale = bean.getScale();
                this.centerPoint = PdfUtil.getOwnCenterPoint(mContext, bean.getCenterX(), bean.getCenterY(), bean.getScreenWid());
                this.loadFile(PdfUtil.getFileType(bean.getfSysname()), bean.getfId(), bean.getFname(), bean.getfSysname(), bean.getfDownPath(), bean.getIsAgenda());
            }

            if (!this.fileLoading && this.currentPage != bean.getPage()) {
                /** Judge the operation which include 'page-up' and 'page-down'. */
                if (this.currentPage - bean.getPage() == 1){
                    pdfPageUp = true;
                }
                this.jumpPage(bean.getPage());
            }

            if (!this.fileLoading && this.currentScale != bean.getScale()) {
                this.viewZoom(bean.getScale());
            }

            /** Scroller */
            if (!this.fileLoading && !pdfPageUp && bean.getOptCode() == PdfConfigure.OptCode.Scroll &&
                    ( this.centerPoint.getPointX() != (PdfUtil.getOwnCenterPoint(mContext, bean.getCenterX(), bean.getCenterY(), bean.getScreenWid() )).getPointX()
                    || this.centerPoint.getPointY() != (PdfUtil.getOwnCenterPoint(mContext, bean.getCenterX(), bean.getCenterY(), bean.getScreenWid() )).getPointY() ) ){
                this.centerPoint = PdfUtil.getOwnCenterPoint(mContext, bean.getCenterX(), bean.getCenterY(), bean.getScreenWid());
                if (this.currentScale == PdfConfigure.initscale){
                    this.viewScroll(0, -PdfUtil.getOriginY(mContext, centerPoint.getPointY()));
                }else {
                    this.viewScroll(PdfUtil.getOriginX(mContext, centerPoint.getPointX()), -PdfUtil.getOriginY(mContext, centerPoint.getPointY()));
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnChangeFileEvent(ChangeFileEvent event){
        CommentUploadListInfo.LstFileBean data = event.getBean();
        if (data.getiID() != this.fId){
            String fileSysname = FileOpenUtil.getfileSystemName(data.getiID());
            int fileType = PdfUtil.getFileType(fileSysname);
            this.initPageAndScaleData();
            this.loadFile(fileType, data.getiID(), data.getStrName(), fileSysname, data.getStrPath(), Config.On_Off.LowLevel);
            //Send document presentation data if possible.
            sendSpeakData(true, PdfConfigure.OptCode.OpenFile, 0, 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnReceiveToastEvent (ToastMsgEvent event){
        if (event.getTargetActivityName().equals(Config.ActivityManage.PdfBrowseActivity)){
            //This message comes the presenter in main activity which means '异步浏览' just use in once.
            if (event.getStr().equals(mContext.getResources().getString(R.string.close_doc_track))){
//                ((Activity) mContext).finish();
                PdfUtil.setTrackSpeak(false);
                //auto set the right navigatiion under the connected network if it is showing when changing the presentation status.
                if (((PdfBrowseActivity) mContext).getmRightNavigationPopView().isShowing()) {
                    ((PdfBrowseActivity) mContext).setRightNavigationStatus();
                }
                ((PdfBrowseActivity) mContext).setTrackViewClickEnable(this.loadType, true);

            }else {
                ToastUtil.show(mContext, event.getStr());
            }
        }
    }

    /** Connection status.*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveNetworkEvent(ConnectionStatusEvent event) {
        switch (event.getConnectionStatus()){
            /** Lose connection. */
            case 1001:
            case 1002:
                if (PdfUtil.checkPresentationUser()){
                    ToastUtil.show(mContext, mContext.getResources().getString(R.string.null_network) + "," + mContext.getResources().getString(R.string.ending_speak));
                    setApplyDocSpeakStatus(false);
                }else {
                    if (PdfUtil.checkTrackSpeak()) {
                        ToastUtil.show(mContext, mContext.getResources().getString(R.string.null_network) + "," + mContext.getResources().getString(R.string.ending_track));
                        getView().setTrackViewClickEnable(this.loadType, true);
                        PdfUtil.setTrackSpeak(false);
                    }else {
                        ToastUtil.show(mContext, mContext.getResources().getString(R.string.ending_speak));
                    }

                    PdfUtil.setIsSpeaking(false, 0, "");
                    //auto set the right navigatiion under the connected network if it is showing when changing the presentation status.
                    if (((PdfBrowseActivity) mContext).getmRightNavigationPopView().isShowing()) {
                        ((PdfBrowseActivity) mContext).setRightNavigationStatus();
                    }
                }
                break;
            case 1003:
                break;
        }
    }

    @Subscribe
    public void onReceiveFileModifyEvent(DeleteOrModifyEvent event){
        if (event.getFileId() == this.fId){
            ToastUtil.show(mContext, mContext.getResources().getString(R.string.file_browse_permission));
            ((Activity) mContext).finish();
        }
    }


    //Set the status of the application of document presentation.
    @Override
    public void setApplyDocSpeakStatus(boolean status){
        if (status){
            ToastUtil.show(mContext, mContext.getResources().getString(R.string.open_doc_speak));
            getView().changeSpeakerSendStatus(true);
            PdfUtil.setIsSpeaking(true, AppDataCache.getInstance().getInt(Config.USER_ID), AppDataCache.getInstance().getString(Config.CMS_ACCOUNT));

            //Send document presentation data if possible.
            sendSpeakData(true, PdfConfigure.OptCode.OpenFile, 0, 0);
        }else {
            getView().changeSpeakerSendStatus(false);
            PdfUtil.setIsSpeaking(false, 0, "");
            //Send document presentation data if possible.
            sendSpeakData(false, PdfConfigure.OptCode.CloseSpeaker, 0, 0);
        }
    }

    //Send the speak data just at one entrance.
    @Override
    public void sendSpeakData(boolean speakStatus, int optCode, float scaleX, float scaleY) {
        if (speakStatus) {
            //Send document presentation data if the method return 'true'.
            if (PdfUtil.checkPresentationUser()) {
                mModel.sendDocOperateData(optCode, this.isAgenda, AppDataCache.getInstance().getInt(Config.USER_ID), AppDataCache.getInstance().getString(Config.CMS_ACCOUNT),
                        this.fId, this.fName, this.fSysName, this.fDownPath, PdfConfigure.isSpeakAllSend, this.currentPage, this.currentScale, scaleX, scaleY,
                        ScreenUtil.getScreenWidth(mContext), this.centerPoint.getPointX() , this.centerPoint.getPointY());
            }
        }else {
            mModel.sendCloseSpeakData();
        }
    }

    public int getfId() {
        return fId;
    }

    public List<CommentUploadListInfo.LstFileBean> getSwitchFileItem(){
        return SwitchFileP.getListsByFileId(this.fId);
    }

    public boolean isFileLoading() {
        return fileLoading;
    }

    public void setFileLoading(boolean fileLoading) {
        this.fileLoading = fileLoading;
    }

    public void initPageAndScaleData(){
        this.currentScale = PdfConfigure.initscale;
        this.currentPage = PdfConfigure.initpage;
    }

    public int getLoadType() {
        return loadType;
    }

    public PointsBean getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(PointsBean centerPoint) {
        this.centerPoint = centerPoint;
        //Send document presentation data if possible.
        this.sendSpeakData(true, PdfConfigure.OptCode.Scroll, 0, 0);
    }

    /** Solve the view-bias that occurs when the user flips the page(page-up). */
    public void setCenterPointOnly(PointsBean centerPoint) {
        this.centerPoint = centerPoint;
    }

    public boolean isPdfPageUp() {
        return pdfPageUp;
    }

    public void setPdfPageUp(boolean pdfPageUp) {
        this.pdfPageUp = pdfPageUp;
    }
}
