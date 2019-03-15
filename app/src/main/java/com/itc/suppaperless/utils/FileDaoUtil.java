package com.itc.suppaperless.utils;

import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.loginmodule.bean.FileDao;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.DaoSession;
import com.itc.suppaperless.meetingmodule.bean.FileDaoDao;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;

import org.greenrobot.greendao.query.LazyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaogf on 19-1-14.
 */

public class FileDaoUtil {

    /**
     * 根据路径查询文件信息
     */
    public static List<FileDao> queryFileList(int id) {
        List<FileDao> qb = null;
        String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
        int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
        try {
            DaoSession daoSession = PaperlessApplication.getDaoSession();
            FileDaoDao fileDaoDao = daoSession.getFileDaoDao();
            qb = fileDaoDao.queryBuilder().where(FileDaoDao.Properties.FileId.eq(id)).where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).list();
        } catch (Exception e) {

        }
        return qb;
    }

    /**
     * 插入数据
     */
    public synchronized static void insertFile(int id, String path, int type, String lstFileBean) {
        try {
            DaoSession daoSession = PaperlessApplication.getDaoSession();
            FileDaoDao fileDaoDao1 = daoSession.getFileDaoDao();
            String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
            int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
            List<FileDao> qb = fileDaoDao1.queryBuilder().where(FileDaoDao.Properties.FileId.eq(id)).where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).list();
//            if (qb.size()>0){
//                for (int i=0;i<qb.size();i++){
//                    fileDaoDao1.delete(qb.get(i));
//                }
//            }
            if (qb.size()==0){
                FileDaoDao fileDaoDao = daoSession.getFileDaoDao();
                FileDao fileDao = new FileDao();
                fileDao.setFileId(id);
                fileDao.setFilePath(path);
                fileDao.setFileType(type);
                fileDao.setIpAndPort(StringUtil.strSplit(ip)+port);
                fileDao.setIsDown(false);
                fileDao.setIsPermission(true);
                fileDao.setLstFileBean(lstFileBean);
//                fileDaoDao.insert(fileDao);
                fileDaoDao.insertOrReplace(fileDao);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询是否下载完成
     *
     * @param id
     */
    public static boolean getIsDown(int id) {
        try {
            List<FileDao> qb = queryFileList(id);
            if (qb.size()==0){
                return false;
            }else {
                return qb.get(0).getIsDown();
            }
        }catch (Exception e){

        }
        return false;
    }

    /**
     * 设置下载完成
     *
     * @param id
     */
    public static void setIsDown(int id) {
        String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
        int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
        try {
            FileDaoDao fileDaoDao = PaperlessApplication.getDaoSession().getFileDaoDao();
            LazyList<FileDao> fileDaos = fileDaoDao.queryBuilder().where(FileDaoDao.Properties.FileId.eq(id)).where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).listLazy();
            if (fileDaos.size() > 0){
                FileDao fileDao = fileDaos.get(0);
                fileDao.setIsDown(true);
                fileDaoDao.update(fileDao);
            }
        } catch (Exception e) {
        }

    }

    /**
     * 得到文件权限
     *
     * @param id
     */
    public static boolean getPermission(int id) {
        try {
            List<FileDao> qb = queryFileList(id);
            if (qb.size() == 0) {
                return false;
            } else {
                return qb.get(0).getIsPermission();
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 设置文件名
     *
     * @param progress
     */
    public static void setFileName(Progress progress) {
        String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
        int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
        try {
            FileDaoDao fileDaoDao = PaperlessApplication.getDaoSession().getFileDaoDao();
            List<FileDao> qb = fileDaoDao.queryBuilder().where(FileDaoDao.Properties.FileId.eq(progress.tag)).where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).list();
            if (qb.size() > 0) {
                FileDao fileDao = qb.get(0);
                fileDao.setFileName(progress.fileName);
                fileDaoDao.update(fileDao);
            }
        } catch (Exception e) {

        }

    }

    /**
     * 获取真正文件名
     *
     * @param id
     */
    public static String getFileName(int id) {
        String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
        int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
        try {
            FileDaoDao fileDaoDao = PaperlessApplication.getDaoSession().getFileDaoDao();
            List<FileDao> qb = fileDaoDao.queryBuilder().where(FileDaoDao.Properties.FileId.eq(id)).where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).list();
            if (qb.size() > 0) {
                FileDao fileDao = qb.get(0);
                return fileDao.getFileName();
            } else {
                return "";
            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 删除文件数据
     *
     * @param id
     */
    public static void deleteDownFile(int id) {
        String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
        int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
        try {
            FileDaoDao fileDaoDao = PaperlessApplication.getDaoSession().getFileDaoDao();
            List<FileDao> qb = fileDaoDao.queryBuilder().where(FileDaoDao.Properties.FileId.eq(id)).where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).list();
            if (qb.size() > 0) {
                for (int i = 0; i < qb.size(); i++) {
                    OkDownload.getInstance().removeTask("" + qb.get(i).getId());
                    FileDao fileDao = qb.get(i);
                    fileDaoDao.delete(fileDao);
                }
            }
        } catch (Exception e) {

        }

    }

    /**
     * 根据ID得到数据库数据。lstFileBean为空的时候就是议程
     *
     * @param id
     */
    public static CommentUploadListInfo.LstFileBean getFileData(int id) {
        String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
        int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
        CommentUploadListInfo.LstFileBean lstFileBean = null;
        FileDaoDao fileDaoDao = PaperlessApplication.getDaoSession().getFileDaoDao();
        List<FileDao> qb = fileDaoDao.queryBuilder().where(FileDaoDao.Properties.FileId.eq(id)).where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).list();
        if (qb.size() > 0) {
            for (int i = 0; i < qb.size(); i++) {
                CommentUploadListInfo.LstFileBean bean = GsonUtil.getJsonObject(qb.get(i).getLstFileBean(), CommentUploadListInfo.LstFileBean.class);
                    return bean;
            }
        }

        return lstFileBean;

    }

    /**
     * 得到数据库数据,
     *
     * @param
     */
    public static List<CommentUploadListInfo.LstFileBean> getAllFileData(int type) {
        String ip = AppDataCache.getInstance().getString(Config.IP_MEETING);
        int port = AppDataCache.getInstance().getInt(Config.PORT_MEETING);
        List<CommentUploadListInfo.LstFileBean> list = new ArrayList<>();
        FileDaoDao fileDaoDao = PaperlessApplication.getDaoSession().getFileDaoDao();
        List<FileDao> qb = fileDaoDao.queryBuilder().where(FileDaoDao.Properties.IpAndPort.eq(StringUtil.strSplit(ip)+port)).list();
        if (qb.size() > 0) {
            for (int i = 0; i < qb.size(); i++) {
                CommentUploadListInfo.LstFileBean lstFileBean = GsonUtil.getJsonObject(qb.get(i).getLstFileBean(), CommentUploadListInfo.LstFileBean.class);
                    if (lstFileBean.getiType() == type) {
                        list.add(lstFileBean);
                    }
            }
        }
        return list;

    }


    /**
     * 删除所有文件数据
     */
    public static void deleteAll() {
        FileDaoDao fileDaoDao = PaperlessApplication.getDaoSession().getFileDaoDao();
        fileDaoDao.deleteAll();
    }
}
