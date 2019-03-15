package com.itc.suppaperless.meetingmodule.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by huangsm on 2017/6/23.
 */
@Entity
public class FileUploadDao {
    @Id(autoincrement = true)
    private Long id; // 自定义数据库表的key
    private String fileName; // 文件的路径
    private int uploadState; // 上传文件的状态
    private boolean isHasUploadFile; // 文件是否上传过的标记
    private long totelProgress; // 文件上传的总进度
    private long currentProgress; // 文件上传的当前进度
    private long iFileID;
    @Generated(hash = 2031291277)
    public FileUploadDao(Long id, String fileName, int uploadState,
                         boolean isHasUploadFile, long totelProgress, long currentProgress,
                         long iFileID) {
        this.id = id;
        this.fileName = fileName;
        this.uploadState = uploadState;
        this.isHasUploadFile = isHasUploadFile;
        this.totelProgress = totelProgress;
        this.currentProgress = currentProgress;
        this.iFileID = iFileID;
    }
    @Generated(hash = 1487823772)
    public FileUploadDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public int getUploadState() {
        return this.uploadState;
    }
    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }
    public boolean getIsHasUploadFile() {
        return this.isHasUploadFile;
    }
    public void setIsHasUploadFile(boolean isHasUploadFile) {
        this.isHasUploadFile = isHasUploadFile;
    }
    public long getTotelProgress() {
        return this.totelProgress;
    }
    public void setTotelProgress(long totelProgress) {
        this.totelProgress = totelProgress;
    }
    public long getCurrentProgress() {
        return this.currentProgress;
    }
    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
    }
    public long getIFileID() {
        return this.iFileID;
    }
    public void setIFileID(long iFileID) {
        this.iFileID = iFileID;
    }
}
