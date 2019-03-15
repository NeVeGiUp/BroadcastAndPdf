package com.itc.suppaperless.pdfmodule.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.pdfmodule.bean.GsonDocOperationBean;
import com.itc.suppaperless.pdfmodule.bean.PointsBean;
import com.itc.suppaperless.pdfmodule.bean.SpeakDataTransfer;
import com.itc.suppaperless.pdfmodule.configure.PdfConfigure;
import com.itc.suppaperless.pdfmodule.mvp.contract.PdfBrowseContract;
import com.itc.suppaperless.pdfmodule.mvp.presenter.PdfBrowsePresenter;
import com.itc.suppaperless.pdfmodule.utils.PdfUtil;
import com.itc.suppaperless.switch_conference.widget.RightNavigationPopView;
import com.itc.suppaperless.utils.ActivityManageUtil;
import com.itc.suppaperless.utils.GsonUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.SoftKeyboardUtils;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.CommonToolBar;
import com.itc.suppaperless.widget.DialogNewInterface;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.github.barteksc.pdfviewer.util.FitPolicy.WIDTH;

/**
 * Create by zhengwp on 19-1-23.
 *
 * pdf文件与图片都在这里打开
 */
public class PdfBrowseActivity extends BaseActivity implements PdfBrowseContract.PdfBrowseView {
    @BindView(R.id.rl_parentView)
    RelativeLayout rl_parentView;
    @BindView(R.id.pdfview_browser)
    PDFView pdfView;
    @BindView(R.id.image_browser)
    PhotoView imager_browse;
    @BindView(R.id.toolbar)
    CommonToolBar toolBar;
    @BindView(R.id.ll_pdfmodule_bottom)
    LinearLayout bottomLayout;
    @BindView(R.id.tv_pdfmodule_pagenum)
    TextView pagenum;
    @BindView(R.id.rv_pdfmodule_preview)
    RecyclerView pdf_preview;
    @BindView(R.id.tv_jump_page)
    TextView tv_jump_page;
    @BindView(R.id.ll_jumppage_dialog)
    LinearLayout ll_jumppage_dialog;
    @BindView(R.id.et_page_input)
    EditText et_page_input;
    @BindView(R.id.tv_goto_page)
    TextView tv_goto_page;
    @BindView(R.id.ll_zoom)
    LinearLayout ll_zoom;
    @BindView(R.id.iv_pdf_zoomin)
    ImageView iv_pdf_zoomin;
    @BindView(R.id.iv_pdf_zoomout)
    ImageView iv_pdf_zoomout;
    @BindView(R.id.iv_navigation)
    ImageView iv_navigation;

    private RightNavigationPopView mRightNavigationPopView;
    private MediaProjectionManager mMediaProjectionManager;//录屏
    private PdfBrowsePresenter pdfPresenter;
    private SwitchFilePopupWindow switchFile;
    private boolean pageTurningVisible = false;
    private boolean toolbarVisible = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf_browse;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new PdfBrowsePresenter(this);
    }

    /**
     * 页面按钮事件传递
     * @param view
     */
    @OnClick({R.id.tv_jump_page, R.id.tv_goto_page, R.id.iv_pdf_zoomin, R.id.iv_pdf_zoomout,
                R.id.iv_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_jump_page:
                showJumpDialog();
                pageTurningVisible = true;
                break;
            case R.id.tv_goto_page:
                if (et_page_input.getText().toString() != null
                        && !et_page_input.getText().toString().equals("")){
                    int page = (Integer.parseInt(et_page_input.getText().toString())) ;
                    if (page > pdfView.getPageCount()){
                        ToastUtil.show(PdfBrowseActivity.this, getResources().getString(R.string.pdf_input_right_page) + pdfView.getPageCount(), Toast.LENGTH_SHORT);
                        return;
                    }
                    if ( (page-1) == pdfPresenter.getCurrentPage()){
                        /** The target page which you want to go is showing at screen now. */
                    }else {
                        pdfPresenter.jumpPage(page - 1);
                    }
                    et_page_input.clearFocus();
                    /** Alternative handler with the jump view. */
//                    hideJumpDialog();
                    SoftKeyboardUtils.hideSoftKeyboard(PdfBrowseActivity.this);
                }else{
                    ToastUtil.show(PdfBrowseActivity.this, getResources().getString(R.string.pdf_input_page), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.iv_pdf_zoomin:
                if (pdfPresenter.getCurrentScale() == 1){
                    pdfPresenter.viewZoom(PdfConfigure.ZoomScale.midScale);
                }else if (pdfPresenter.getCurrentScale() == 1.75f){
                    pdfPresenter.viewZoom(PdfConfigure.ZoomScale.maxScale);
                }else if(pdfPresenter.getCurrentScale() == 3){

                }
                break;
            case R.id.iv_pdf_zoomout:
                if (pdfPresenter.getCurrentScale() == 1){

                }else if (pdfPresenter.getCurrentScale() == 1.75f){
                    pdfPresenter.viewZoom(PdfConfigure.ZoomScale.minScale);
                }else if(pdfPresenter.getCurrentScale() == 3){
                    pdfPresenter.viewZoom(PdfConfigure.ZoomScale.midScale);
                }
                break;
            case R.id.iv_navigation:
                this.setRightNavigationStatus();
                mRightNavigationPopView.showAtLocation(rl_parentView, Gravity.RIGHT, 0, 500);
                break;
        }
    }

    @Override
    public void init() {
        //初始化 P
        pdfPresenter = (PdfBrowsePresenter) getPresenter();
        ////////////   初始化屏幕广播相应控件   ////////////
        mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
        mRightNavigationPopView = new RightNavigationPopView(PdfBrowseActivity.this, mMediaProjectionManager,getWindow().getDecorView());
        //Reset Activity Ready Flag
        PdfUtil.setPdfActivityOpen(true);

        //init the file data
        int fId = getIntent().getIntExtra(PdfConfigure.fileId, Config.On_Off.LowLevel);
        String  fName = getIntent().getStringExtra(PdfConfigure.filename);
        String fSysName = getIntent().getStringExtra(PdfConfigure.filesystemname);
        String fDownPath = getIntent().getStringExtra(PdfConfigure.filedownpath);
        int isAgenda = getIntent().getIntExtra(PdfConfigure.isAgenda, Config.On_Off.LowLevel);

        /** Data from start activity which to handle the blocking process. */
        SpeakDataTransfer bean = (SpeakDataTransfer) getIntent().getSerializableExtra(PdfConfigure.speakbean);
        if (bean != null){
            GsonDocOperationBean optbean = GsonUtil.getJsonObject(bean.getOptStr(), GsonDocOperationBean.class);
            pdfPresenter.setCurrentPage(optbean.getPage());
            pdfPresenter.setCurrentScale(optbean.getScale());
            pdfPresenter.setCenterPointOnly(PdfUtil.getOwnCenterPoint(PdfBrowseActivity.this, optbean.getCenterX(), optbean.getCenterY(), optbean.getScreenWid()));
        }

        int loadType = getIntent().getIntExtra(PdfConfigure.loadtype, Config.On_Off.LowLevel);

        //标题栏设置按钮的点击事件
        toolBar.setClickListener(
                //返回键
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backButtonPressed();
                    }
                },
                //切换文档
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (switchFile == null){
                            switchFile = new SwitchFilePopupWindow(PdfBrowseActivity.this);
                        }
                        switchFile.setAdapterData(pdfPresenter.getSwitchFileItem());
                        switchFile.showAtLocation(rl_parentView, Gravity.CENTER, 0 , 0);
                    }
                });
        /**
         * Put the object method 'setTitleText(fName)' into 'loadView()',
         * because of the openning operation by others such as document presentation.
         */
        pdfPresenter.loadFile(loadType, fId, fName, fSysName, fDownPath, isAgenda);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public CommonToolBar getToolBar() {
        return toolBar;
    }

    public LinearLayout getLl_zoom() {
        return ll_zoom;
    }

    public TextView getTv_jump_page() {
        return tv_jump_page;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PdfUtil.setPdfActivityOpen(false);
        /** It means the tracking-operation is breaken when destroy activity anyway. */
        PdfUtil.setTrackSpeak(false);
    }

    /** Dispatch of touch event. */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            if (pdfPresenter.getLoadType() == PdfConfigure.pdfmode){
                float centerX = pdfView.getCurrentXOffset() + pdfView.getPivotX();
                float centerY = -pdfView.getCurrentYOffset() + pdfView.getPivotY();
                if (centerX != pdfPresenter.getCenterPoint().getPointX() || centerY != pdfPresenter.getCenterPoint().getPointY()){
                    pdfPresenter.setCenterPoint( new PointsBean(centerX, centerY) );
                }
            }else{  /** Image type */

            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN){
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
        }

        if (event.getPointerCount() >= 2){
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    /** Dispatch of key event. */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            this.backButtonPressed();
            return  true;
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void showSwitchFile(boolean isShow) {
        if (isShow){
            this.toolBar.getmRightBtn().setVisibility(View.VISIBLE);
        }else {
            this.toolBar.getmRightBtn().setVisibility(View.GONE);
        }
    }

    @Override
    public void setTrackViewClickEnable(int filetype, boolean enable){
        /** In this method, make sure the status of document-presentation trance, and then comfirm the visiable and enable(clickable) of some-view and. */
        if (filetype == PdfConfigure.pdfmode) {
            pdfView.setEnabled(enable);
            initPageTurnLayout(enable);
        } else {
            imager_browse.setEnabled(enable);
        }
        show_hideZoomLayout(enable);
        if(enable){
            toolBar.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
        }else {
            toolBar.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
        }
    }

    ///////////  OnActivityResult and right navigation.//////////

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 101:  //屏幕广播申请
                if (resultCode == RESULT_OK){
                    pdfPresenter.applicationScreenBroadcast(0,1, AppDataCache.getInstance().getInt(Config.USER_ID));
                    MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
                    pdfPresenter.startRecorder(mediaProjection);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void changeScreenBroadcastStatus(boolean isBroadcasting) {
        mRightNavigationPopView.setBroadcasting(isBroadcasting);
    }

    @Override
    public void changeSpeakerReceiveStatus(boolean isSpeaker) {
        mRightNavigationPopView.setReceiveDocSpeakerView(isSpeaker);
    }

    @Override
    public void changeSpeakerSendStatus(boolean isSpeaker) {
        mRightNavigationPopView.setSendDocSpeakerView(isSpeaker);
    }

    @Override
    public void changeTrackStatus(boolean isSpeaker){
        mRightNavigationPopView.setTrackStatus(isSpeaker);
    }

    @Override
    public void changeIsTrackingStatus(boolean isSpeaker){
        mRightNavigationPopView.setisTracking(isSpeaker);
    }

    public RightNavigationPopView getmRightNavigationPopView() {
        return mRightNavigationPopView;
    }

    /** Reset the navigation status according to the global status. */
    public void setRightNavigationStatus(){
        /** Nobody is open document speaking, which means also cant track presentation.  */
        if (!PdfUtil.checkIsSpeaking()) {
            this.changeSpeakerSendStatus(false);
            this.changeIsTrackingStatus(false);
            this.changeTrackStatus(false);
        } else {
            if (!PdfUtil.checkPresentationUser()){
                this.changeSpeakerReceiveStatus(true);

                if (PdfUtil.checkTrackSpeak()) {
                    this.changeIsTrackingStatus(true);
                } else {
                    this.changeIsTrackingStatus(false);
                }
            }
        }
    }

    /////////////////////////  重写View层方法  /////////////////////////
    @Override
    public void loadImage(String fileAbsoutelyPath) {
        //切换相应的View.
        if (pdfView != null){
            pdfView.recycle();
        }
        pdfView.setVisibility(View.GONE);
        pdfPresenter.setFileLoading(false);
        imager_browse.setVisibility(View.VISIBLE);
        imager_browse.setImageURI(Uri.fromFile(new File(fileAbsoutelyPath)));
        imager_browse.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                switchOtherView();
            }
        });
    }

    @Override
    public void loadPdf(String path){
        if (imager_browse != null){

        }
        imager_browse.setVisibility(View.GONE);
        pdfView.setVisibility(View.VISIBLE);
        pdfView.fromFile(new File(path))
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .enableDoubletap(false)
                //只在页面单击时调用，移动跟抬起操作不调用该方法
                .onTap(new OnTapListener() {
                    @Override
                    public boolean onTap(MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            // 处理输入的按下事件,显示/隐藏
                            switchOtherView();
                        }
                        return true;
                    }
                })
                //页面变更时调用
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        /** To solve the conflick of 'jumping to target page', or it will find many times. */
                        if (pdfPresenter.getCurrentPage() != page){
                            pagenum.setText( (page+1) + "/" + pageCount);
                            pdfPresenter.pdfPageTurnListener(page);
                        }else {
                        }

                        /** The situation which handle 'page-up' operation when this terminal is tracking presentation. .
                          * The page turn finished and then call on page listener. */
                        if (!PdfUtil.checkPresentationUser() && PdfUtil.checkTrackSpeak() && pdfPresenter.isPdfPageUp()){
                            if (pdfPresenter.getCurrentScale() == PdfConfigure.initscale){
                                pdfView.moveRelativeTo(0, ScreenUtil.getScreenHeight(PdfBrowseActivity.this) - pdfView.getPageSize(page).getHeight() );
                            }else {
                                pdfView.moveRelativeTo(PdfUtil.getOriginX(PdfBrowseActivity.this, pdfPresenter.getCenterPoint().getPointX()), ScreenUtil.getScreenHeight(PdfBrowseActivity.this) - pdfView.getPageSize(page).getHeight() );
                            }
                            pdfPresenter.setPdfPageUp(false);
                        }
                    }
                })
                //是否呈现pdf注解，pdf文档上的批注，颜色或者样式
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                //宽度适配
                .nightMode(PdfConfigure.nightMode)
                .pageFitPolicy(WIDTH)
                .defaultPage(pdfPresenter.getCurrentPage())
                /**
                 * 文档加载完毕后调用
                 * @paras nbPages: Page Count
                 */
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        /** Set the text page tips */
                        pagenum.setText( (pdfPresenter.getCurrentPage()+1) + "/" + nbPages);
                        /** set hint of the view. 设置edittext输入提示 */
                        setEtPageHint(et_page_input, 1, nbPages);
                        /** When pdf is loading finished, we can make some change ofthe view. */
                        /** Scroll to the target point, and then call onPageListener. */
                        pdfPresenter.setFileLoading(false);
                        if (pdfPresenter.getCurrentScale() != PdfConfigure.initscale){
                            pdfPresenter.viewZoom(pdfPresenter.getCurrentScale());
                            if ( PdfUtil.getOriginX(PdfBrowseActivity.this, pdfPresenter.getCenterPoint().getPointX()) != pdfView.getCurrentXOffset()
                                    || -PdfUtil.getOriginY(PdfBrowseActivity.this, pdfPresenter.getCenterPoint().getPointY()) != pdfView.getCurrentYOffset() ){
                                pdfPresenter.viewScroll(PdfUtil.getOriginX(PdfBrowseActivity.this, pdfPresenter.getCenterPoint().getPointX()),
                                        -PdfUtil.getOriginY(PdfBrowseActivity.this, pdfPresenter.getCenterPoint().getPointY()) );
                            }
                        }else {
                            if ( PdfUtil.getOriginY(PdfBrowseActivity.this, pdfPresenter.getCenterPoint().getPointY()) != pdfView.getCurrentYOffset() ){
                                pdfPresenter.viewScroll(0, -PdfUtil.getOriginY(PdfBrowseActivity.this, pdfPresenter.getCenterPoint().getPointY()));
                            }
                        }
                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {
                        /** page listener from 'moveto' when the pdf view is not redraw in receive document presentation. */
                        if ( !PdfUtil.checkPresentationUser() && PdfUtil.checkTrackSpeak() ){
                            pdfView.loadPages();
                        }
                    }
                })
                .load();
    }

    @Override
    public void switchOtherView() {
        //隐藏小键盘
        if (SoftKeyboardUtils.isSoftShowing(PdfBrowseActivity.this)){
            SoftKeyboardUtils.hideSoftKeyboard(PdfBrowseActivity.this);
            return;
        }
        //隐藏 跳页 的对话框, 并且显示跳页的指示按钮
        if (pageTurningVisible){
            hideJumpDialog();
            pageTurningVisible= false;
            return;
        }
        //隐藏 /显示 toolbar 跟 页码提示 控件
        if (toolbarVisible) {
            showHideToolBar(false);
            toolbarVisible = false;
        } else {
            showHideToolBar(true);
            toolbarVisible = true;
        }
    }

    @Override
    public void initPageTurnLayout(boolean status){
        if (status) {
            tv_jump_page.setY(ScreenUtil.getScreenHeight(PdfBrowseActivity.this) / 4);
            ll_jumppage_dialog.setY(ScreenUtil.getScreenHeight(PdfBrowseActivity.this) / 4);

            ll_jumppage_dialog.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ObjectAnimator.ofFloat(ll_jumppage_dialog, "translationX", 0, -ll_jumppage_dialog.getMeasuredWidth()).setDuration(300).start();
                    //移除观察者
                    if (Build.VERSION.SDK_INT < 16) {
                        ll_jumppage_dialog.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        ll_jumppage_dialog.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
            et_page_input.setHint("1~" + pdfView.getPageCount());
            //显示View
            tv_jump_page.setVisibility(View.VISIBLE);
            ll_jumppage_dialog.setVisibility(View.VISIBLE);
        }else {
            tv_jump_page.setVisibility(View.GONE);
            ll_jumppage_dialog.setVisibility(View.GONE);
        }
    }

    @Override
    public void show_hideZoomLayout(boolean show){
        ll_zoom.setY((float) (ScreenUtil.getScreenHeight(PdfBrowseActivity.this) / 2.6));
        if (show){
            ll_zoom.setVisibility(View.VISIBLE);
        }else{
            ll_zoom.setVisibility(View.GONE);
        }
    }

    @Override
    public void pdfPageJump(int targetPage){
        pdfPageJump(targetPage, false);
    }

    @Override
    public void pdfPageJump(int targetPage, boolean withAnimation){
        pdfView.jumpTo(targetPage, withAnimation);
        pagenum.setText( (targetPage+1) + "/" + pdfView.getPageCount());
    }

    @Override
    public void pdfZoom(float scale){
        pdfView.zoomWithAnimation(scale);
    }

    @Override
    public void pdfZoomWithPoint(float centerX, float centerY, float scale){
        pdfView.zoomWithAnimation(centerX, centerY, scale);
    }

    @Override
    public void imageZoom(float scale){
        imager_browse.setScale(scale);
    }

    @Override
    public void pdfScroll(float x, float y) {
        pdfView.moveTo(x, y);
    }

    @Override
    public void imageScroll(int x, int y) {
        imager_browse.scrollTo(x,y);
    }

    @Override
    public void setEtPageHint(EditText view, int startNum, int pageCount){
        view.setHint(startNum + "~" + pageCount);
    }

    @Override
    public void showJumpDialog(){
        ObjectAnimator.ofFloat(tv_jump_page,"translationX", 0, -tv_jump_page.getMeasuredWidth()).setDuration(300).start();
        ObjectAnimator.ofFloat(ll_jumppage_dialog,"translationX", -ll_jumppage_dialog.getMeasuredWidth(), 0).setDuration(300).start();
    }

    @Override
    public void hideJumpDialog(){
        et_page_input.setText("");
        ObjectAnimator.ofFloat(ll_jumppage_dialog,"translationX", 0, -ll_jumppage_dialog.getMeasuredWidth()).setDuration(300).start();
        ObjectAnimator.ofFloat(tv_jump_page,"translationX", -tv_jump_page.getMeasuredWidth(), 0).setDuration(300).start();
    }

    @Override
    public void showHideToolBar(boolean show){
        if (show){
            ObjectAnimator.ofFloat(bottomLayout, "translationY", bottomLayout.getMeasuredHeight(), 0).setDuration(300).start();
            ObjectAnimator.ofFloat(toolBar, "translationY", -toolBar.getMeasuredHeight(), 0).setDuration(300).start();
        }else {
            ObjectAnimator.ofFloat(bottomLayout, "translationY", 0, bottomLayout.getMeasuredHeight()).setDuration(300).start();
            ObjectAnimator.ofFloat(toolBar, "translationY", 0, -toolBar.getMeasuredHeight()).setDuration(300).start();
        }
    }

    ////////////////// private method ////////////////
    private void backButtonPressed(){
        if (PdfUtil.checkPresentationUser()){
            new DialogNewInterface(PdfBrowseActivity.this).setText(getResources().getString(R.string.close_doc_speak)).setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                @Override
                public void onClick() {
                    //Close the document presentation.
                    pdfPresenter.setApplyDocSpeakStatus(false);
                    //Finally finish it.
                    PdfBrowseActivity.this.finish();
                }
            }).show();
        }else {
            if (PdfUtil.checkTrackSpeak()){
                new DialogNewInterface(PdfBrowseActivity.this).setText(getResources().getString(R.string.close_doc_track)).setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        //Finally finish it.
                        PdfBrowseActivity.this.finish();
                    }
                }).show();
            }else {
                //Finally finish it.
                PdfBrowseActivity.this.finish();
            }
        }
    }

}
