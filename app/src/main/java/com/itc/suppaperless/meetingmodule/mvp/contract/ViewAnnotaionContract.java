package com.itc.suppaperless.meetingmodule.mvp.contract;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;

import java.util.List;

/**
 * Create by zhengwp on 19-3-7.
 */
public interface ViewAnnotaionContract {

    interface annotationView extends BaseView{
        void changeFileAdapter(List<CommentUploadListInfo.LstFileBean> data);
        void notifyFileDataOnly(List<CommentUploadListInfo.LstFileBean> data);
        void changeImageAdapter(List<CommentUploadListInfo.LstFileBean> data);
        void notifyImageDataOnly(List<CommentUploadListInfo.LstFileBean> data);
    }

    interface annotationModel {
    }

    interface annotationPresenter extends IBaseXPresenter{
        void generateFileItem(int fileType);
        void notifyAdapter();
        void clickCallback(int position);
    }

}
