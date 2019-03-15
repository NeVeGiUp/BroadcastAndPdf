package com.itc.suppaperless.pdfmodule.eventbean;

import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;

/**
 * Create by zhengwp on 2/27/19.
 */
public class ChangeFileEvent {
    private CommentUploadListInfo.LstFileBean bean;

    public ChangeFileEvent(CommentUploadListInfo.LstFileBean bean) {
        this.bean = bean;
    }

    public CommentUploadListInfo.LstFileBean getBean() {
        return bean;
    }

    public void setBean(CommentUploadListInfo.LstFileBean bean) {
        this.bean = bean;
    }
}
