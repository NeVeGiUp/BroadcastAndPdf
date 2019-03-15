package com.itc.suppaperless.meetingmodule.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zhangwd on 2017/6/22.
 * 上传文件
 */
@Entity
public class UpLoadFile {
    @Id(autoincrement = true)
    private Long id;
    //本地文件路径
    private String localPath;
    //文件名字
    private String fileName;
    //文件类型   4 临时文件　5 批注文件
    private int fileType;
    @Generated(hash = 1650271499)
    public UpLoadFile(Long id, String localPath, String fileName, int fileType) {
        this.id = id;
        this.localPath = localPath;
        this.fileName = fileName;
        this.fileType = fileType;
    }
    @Generated(hash = 395370162)
    public UpLoadFile() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLocalPath() {
        return this.localPath;
    }
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public int getFileType() {
        return this.fileType;
    }
    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

}
