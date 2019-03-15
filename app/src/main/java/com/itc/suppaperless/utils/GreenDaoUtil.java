package com.itc.suppaperless.utils;

import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.meetingmodule.bean.DaoSession;
import com.itc.suppaperless.meetingmodule.bean.DownLoadFileDao;
import com.itc.suppaperless.meetingmodule.bean.FileUploadDao;
import com.itc.suppaperless.meetingmodule.bean.FileUploadDaoDao;
import com.itc.suppaperless.meetingmodule.bean.SignInfoDao;
import com.itc.suppaperless.meetingmodule.bean.SignInfoDaoDao;
import com.itc.suppaperless.meetingmodule.bean.UpLoadFileDao;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 数据库帮助类
 */

public class GreenDaoUtil {

    /**
     * 插入数据
     */
    public static void insertInfo(Object object){
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        daoSession.insert(object);
    }

    /**
     * 检测数据
     */
    public static int insertnoInfo(Object object){
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        List<SignInfoDao> userList = daoSession.getSignInfoDaoDao().queryBuilder()
                .where(SignInfoDaoDao.Properties.Id.eq(object))
                .build().list();
//        daoSession.insert(object);
        return userList.size();
    }
    /**
     * 删除数据
     */
    public static void deleteInfo(FileUploadDao uploadDao){
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        daoSession.delete(uploadDao);
    }

    /**
     * 删除本地文件上次列表所有数据
     */
    public static void deleteAllInfo(){
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        FileUploadDaoDao fileUploadDaoDao = daoSession.getFileUploadDaoDao();
        if(fileUploadDaoDao != null){
            fileUploadDaoDao.deleteAll();
        }
    }

    /**
     * 删除文件上传下载列表数据
     */
    public static void deleteAllFileSend(){
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        DownLoadFileDao downLoadFileDao = daoSession.getDownLoadFileDao();
        UpLoadFileDao upLoadFileDao = daoSession.getUpLoadFileDao();
        if(downLoadFileDao != null&&upLoadFileDao!=null){
            downLoadFileDao.deleteAll();
            upLoadFileDao.deleteAll();
        }
    }

    /**
     * 删除所有数据
     */
    public static void deleteAllSignInfo(){
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        SignInfoDaoDao signInfoDaoDao = daoSession.getSignInfoDaoDao();
        if(signInfoDaoDao != null){
            signInfoDaoDao.deleteAll();
        }
    }

    /**
     * 更新数据
     */
    public static void updateInfo(Object object){
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        daoSession.update(object);
    }

    /**
     * 查询保存上传文件数据
     */
    public static List<FileUploadDao> queryUserList() {
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        FileUploadDaoDao fileUploadDaoDao = daoSession.getFileUploadDaoDao();
        QueryBuilder<FileUploadDao> qb = fileUploadDaoDao.queryBuilder();
        return qb.list();
    }

    /**
     * 查询签到状态的消息数据
     */
    public static List<SignInfoDao> querySignList() {
        DaoSession daoSession = PaperlessApplication.getDaoSession();
        SignInfoDaoDao fileUploadDaoDao = daoSession.getSignInfoDaoDao();
        QueryBuilder<SignInfoDao> qb = fileUploadDaoDao.queryBuilder();
        return qb.list();
    }


}