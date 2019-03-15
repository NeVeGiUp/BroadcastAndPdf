package com.itc.suppaperless.pdfmodule.mvp.contract;

import android.widget.EditText;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.screen_record.contract.ScreenRecordContract;

/**
 * Create by zhengwp on 19-1-27.
 */
public interface PdfBrowseContract {
    interface PdfBrowseModel extends ScreenRecordContract.ScreenRecordMdl {
        void sendDocOperateData(int optCode, int isAgenda, int sId, String sName, int fId, String fname, String fSysname, String fDownPath,
                                int isAllSend, int page, float scale, float scaleX, float scaleY, float screenwid, float centerX, float centerY);
        void sendCloseSpeakData();
    }

    interface PdfBrowseView extends ScreenRecordContract.ScreenRecordUI {
        void loadImage(String path);
        void loadPdf(String path);
        //点击pdf显示/隐藏头尾标题跟预览页面
        void switchOtherView();
        //初始化隐藏 跳页 对话框
        void initPageTurnLayout(boolean status);
        //显示/隐藏 放大缩小Bar
        void show_hideZoomLayout(boolean show);
        //pdf跳页
        void pdfPageJump(int targetPage);
        void pdfPageJump(int targetPage, boolean withAnimation);
        //pdfView放大 缩小
        void pdfZoom(float scale);
        void pdfZoomWithPoint(float centerX, float centerY, float scale);
        void imageZoom(float scale);
        void pdfScroll(float x, float y);
        void imageScroll(int x, int y);
        void setEtPageHint(EditText view, int startNum, int pageCount);
        //显示跳页对话框，隐藏跳页提示圈圈
        void showJumpDialog();
        //隐藏跳页对话框，显示跳页的提示圈圈
        void hideJumpDialog();
        //显示ToorBar
        void showHideToolBar(boolean show);
        /** Show or hide the 'switch-file' button whether the file is agender. */
        void showSwitchFile(boolean isAgenda);
        /** Set the visible and clickable of some view whether the operation of tracking presentation.*/
        void setTrackViewClickEnable(int filetype, boolean enable);
        //修改接收端RightBar的图标颜色, 检测是否有人在主讲
        void changeSpeakerReceiveStatus(boolean isSpeaker);
        //修改接收端RightBar的图标颜色、文字， 退出主讲
        void changeSpeakerSendStatus(boolean isSpeaker);
        /** 是否可以跟踪主讲. */
        void changeTrackStatus(boolean isSpeaker);
        /** 是否在跟踪主讲.. */
        void changeIsTrackingStatus(boolean isSpeaker);

    }

    interface PdfBrowsePresenter extends IBaseXPresenter {
        //加载View
        void loadFile(int loadType, int fId, String fName, String fSysName, String fDownPath, int isAgenda);
        void viewZoom(float scale);
        void viewScroll(float x, float y);
        void jumpPage(int targetPage);
//        void sendpageturndata(int targetPage);
        //Cotroll the open/close operations of document-speak.
        void setApplyDocSpeakStatus(boolean status);
        //Send the speak(presenter)
        void sendSpeakData(boolean speakStatus, int optCode, float scaleX, float scaleY);

    }
}
