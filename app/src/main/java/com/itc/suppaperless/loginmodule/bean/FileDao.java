package com.itc.suppaperless.loginmodule.bean;

import android.support.annotation.NonNull;

import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.utils.TimeUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;


@Entity
public class FileDao {
    @Id(autoincrement = true)
    private Long id; //自定义数据库表的key
    private String filePath; //文件下载路径
    private String fileName; //文件名
    private boolean isDown;  //是否下载成功
    private boolean isTranscoding; //是否转码成功
    private boolean isPermission; //是否有权限
    private boolean isPDF; //是否是pdf文件
    private int fileType; //文件类型 1.议程文件 2.议题文件 4.资料文件  8.直播 9.点播
    private int  fileId; //文件id
    private String lstFileBean;//文件信息的json
    private String ipAndPort;
    @Generated(hash = 461924285)
    public FileDao(Long id, String filePath, String fileName, boolean isDown,
            boolean isTranscoding, boolean isPermission, boolean isPDF,
            int fileType, int fileId, String lstFileBean, String ipAndPort) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.isDown = isDown;
        this.isTranscoding = isTranscoding;
        this.isPermission = isPermission;
        this.isPDF = isPDF;
        this.fileType = fileType;
        this.fileId = fileId;
        this.lstFileBean = lstFileBean;
        this.ipAndPort = ipAndPort;
    }
    @Generated(hash = 1872466715)
    public FileDao() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFilePath() {
        return this.filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public boolean getIsDown() {
        return this.isDown;
    }
    public void setIsDown(boolean isDown) {
        this.isDown = isDown;
    }
    public boolean getIsTranscoding() {
        return this.isTranscoding;
    }
    public void setIsTranscoding(boolean isTranscoding) {
        this.isTranscoding = isTranscoding;
    }
    public int getFileId() {
        return this.fileId;
    }
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    public boolean getIsPDF() {
        return this.isPDF;
    }
    public void setIsPDF(boolean isPDF) {
        this.isPDF = isPDF;
    }
    public int getFileType() {
        return this.fileType;
    }
    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
    public boolean getIsPermission() {
        return this.isPermission;
    }
    public void setIsPermission(boolean isPermission) {
        this.isPermission = isPermission;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public boolean isTranscoding() {
        return isTranscoding;
    }

    public void setTranscoding(boolean transcoding) {
        isTranscoding = transcoding;
    }

    public boolean isPermission() {
        return isPermission;
    }

    public void setPermission(boolean permission) {
        isPermission = permission;
    }

    public boolean isPDF() {
        return isPDF;
    }

    public void setPDF(boolean PDF) {
        isPDF = PDF;
    }
    public String getLstFileBean() {
        return this.lstFileBean;
    }
    public void setLstFileBean(String lstFileBean) {
        this.lstFileBean = lstFileBean;
    }
    public String getIpAndPort() {
        return this.ipAndPort;
    }
    public void setIpAndPort(String ipAndPort) {
        this.ipAndPort = ipAndPort;
    }


}
