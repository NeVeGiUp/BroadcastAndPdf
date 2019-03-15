package com.itc.suppaperless.meetingmodule.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zhangwd on 2017/6/16.
 * 文件下载列表
 */
@Entity
public class DownLoadFile {
    @Id(autoincrement = true)
    private Long id;
    //本地文件路径
    private String localPath;
    //服务器文件地址
    private String filePath;
    @Generated(hash = 772798353)
    public DownLoadFile(Long id, String localPath, String filePath) {
        this.id = id;
        this.localPath = localPath;
        this.filePath = filePath;
    }
    @Generated(hash = 1784416773)
    public DownLoadFile() {
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
    public String getFilePath() {
        return this.filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
