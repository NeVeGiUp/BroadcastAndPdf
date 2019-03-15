package com.itc.suppaperless.meetingmodule.mvp.presenter;

import com.google.gson.Gson;
import com.itc.suppaperless.base.mvp.BasePresenter;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadFailEvent;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.ProgressEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.ViewAnnotaionContract;
import com.itc.suppaperless.utils.DownloadFileUtil;
import com.itc.suppaperless.utils.FileDaoUtil;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.GsonUtil;
import com.lzy.okgo.model.Progress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by zhengwp on 19-3-7.
 */
public class ViewAnnotaionPresenter extends BasePresenter<ViewAnnotaionContract.annotationView, ViewAnnotaionContract.annotationModel>
        implements ViewAnnotaionContract.annotationPresenter{
    /** the list which used in store all of the temporary data..*/
    private List<CommentUploadListInfo.LstFileBean> fileitems;
    /** the list which used in showing.*/
    private List<CommentUploadListInfo.LstFileBean> showitems;
    /** Store temporary of the type which radio-button is checked.*/
    private int type;

    public ViewAnnotaionPresenter(ViewAnnotaionContract.annotationView view) {
        super(view);
        EventBus.getDefault().register(this);
        fileitems = new ArrayList<>();
        showitems = new ArrayList<>();
        this.type = Config.On_Off.LowLevel;
    }

    @Override
    public void detachView() {
        EventBus.getDefault().unregister(this);
        super.detachView();
    }

    ////////////////////// Override the method from interface. //////////////////////
    @Override
    public void generateFileItem(int type) {
        this.type = type;
        if (showitems == null){
            showitems = new ArrayList<>();
        }else {
            showitems.clear();
        }
        for (int i = 0; i < fileitems.size(); i++){
            if (fileitems.get(i).getiType() == this.type){
                showitems.add(fileitems.get(i));
            }
        }
        switch (type){
            case Config.DocAnnotationType:
                getView().changeFileAdapter(showitems);
                break;
            case Config.HandwriteAnnotationType:
            case Config.ElectronAnnotationType:
                getView().changeImageAdapter(showitems);
            default:
                break;
        }
    }

    @Override
    public void notifyAdapter() {
        switch (this.type){
            case Config.DocAnnotationType:
                getView().notifyFileDataOnly(showitems);
                break;
            case Config.HandwriteAnnotationType:
            case Config.ElectronAnnotationType:
                getView().notifyImageDataOnly(showitems);
            default:
                break;
        }
    }

    @Override
    public void clickCallback(int position) {
        /** Some necessary judgement of the target file.*/
        if (this.showitems.get(position).getIsDown() == 2) {
            if ( FileOpenUtil.getfileSystemName(this.showitems.get(position).getiID()).equals("") ){
                showitems.get(position).setIsDown(0);
                DownloadFileUtil.getInstance().setDownFile(showitems.get(position).getStrPath(), showitems.get(position).getiID());
            }else {
                FileOpenUtil.OpenFile(getView().getActivity(), this.showitems.get(position).getiID(), this.showitems.get(position).getStrName(),
                        FileOpenUtil.getfileSystemName(this.showitems.get(position).getiID()), this.showitems.get(position).getStrPath(), 0);
            }
        }
    }

    ////////////////////// On eventbus receive //////////////////////
    /** file updating.*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FileUpdateEvent event) {//文件更新
        List<CommentUploadListInfo.LstFileBean> beanArray = (GsonUtil.getJsonObject(event.getData(), CommentUploadListInfo.class).getLstFile());
        for (int index = 0; index < beanArray.size(); index++){
            if (beanArray.get(index).getiType() == Config.DocAnnotationType || beanArray.get(index).getiType() == Config.HandwriteAnnotationType
                    || beanArray.get(index).getiType() == Config.ElectronAnnotationType){
                fileitems.add(beanArray.get(index));
                /** Download the file if not exist.*/
                FileDaoUtil.getAllFileData(5);
                FileDaoUtil.getAllFileData(12);
                FileDaoUtil.getAllFileData(13);
                if (!FileDaoUtil.getIsDown(beanArray.get(index).getiID())){
                    DownloadFileUtil.getInstance().setDownFile(beanArray.get(index).getStrPath(), beanArray.get(index).getiID());
                }else {
                    fileitems.get(fileitems.size() - 1).setIsDown(2);
                }
            }
        }
        /** Delete the duplicate data that may exist in the list. */
        for (int i = 0; i < fileitems.size(); i++) {
            for (int j = 0; j < fileitems.size(); j++) {
                if (i != j) {
                    if (fileitems.get(i).getiID() == fileitems.get(j).getiID()) {
                        fileitems.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        this.generateFileItem(this.type);
    }

    //下载进度通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final ProgressEvent event) {
        Progress progress = event.getData();
        setProress(progress);
    }

    //下载完成通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DownloadEvent event) {
        Progress progresss = event.getData();
        setLoadFinish(progresss);
    }

    //下载失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DownloadFailEvent event) {
        String fail = event.getData();
        setLoadFail(fail);
    }

    //设置下载进度
    private void setProress(Progress progress) {
        for (int i = 0; i < showitems.size(); i++) {
            if (showitems.get(i).getiID()==Integer.parseInt(progress.tag)){
                if (showitems.get(i).getIsDown() != 2) {
                    if (showitems.get(i).getiID()==Integer.parseInt(progress.tag)) {
                        showitems.get(i).setIsDown(1);
                        showitems.get(i).setCurrentprogress(progress.currentSize);
                        showitems.get(i).setTotelprogress(progress.totalSize);
                        notifyAdapter();
                    }
                }
            }
        }
    }

    //下载完成
    private void setLoadFinish(Progress progress) {
        for (int i = 0; i < fileitems.size(); i++) {
            if (fileitems.get(i).getiID()==Integer.parseInt(progress.tag)){
                if (fileitems.get(i).getIsDown() != 2) {
                    fileitems.get(i).setIsDown(2);
                    FileDaoUtil.insertFile(fileitems.get(i).getiID(), fileitems.get(i).getStrPath(), fileitems.get(i).getiType(), new Gson().toJson(fileitems.get(i))); //文件数据添加到数据库
                    FileDaoUtil.setIsDown(fileitems.get(i).getiID());
                    notifyAdapter();
                }
            }
        }
    }

    //下载失败
    private void setLoadFail(String obj) {
        for (int i = 0; i < showitems.size(); i++) {
            if (showitems.get(i).getiID()==Integer.parseInt(obj)){
                if (showitems.get(i).getiID()==Integer.parseInt(obj)) {
                    showitems.get(i).setIsDown(3);
                    notifyAdapter();
                }
            }
        }
    }
}
