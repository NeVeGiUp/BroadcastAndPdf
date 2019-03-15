package com.itc.suppaperless.utils;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.listener.DownLoadFileListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;

import java.io.File;

import static com.itc.suppaperless.global.Config.IP_ADDRESS;
import static com.itc.suppaperless.global.Config.MEETING_ID;

/**
 * Created by xiaogf on 19-1-14.
 */

public class DownloadFileUtil {
    private static DownloadFileUtil downloadFileUtil;

    private DownloadFileUtil() {
//        OkDownload.getInstance().getThreadPool().setCorePoolSize(1);
    }

    //单例模式创建DownloadFileUtil类
    public static DownloadFileUtil getInstance() {
        if (downloadFileUtil == null) {
            synchronized (DownloadFileUtil.class) {
                if (downloadFileUtil == null) {
                    downloadFileUtil = new DownloadFileUtil();
                }
            }
        }
        return downloadFileUtil;
    }

    /**
     * @param saveFilePath     保存文件路径
     * @param pServerFilePath  下载路径
     */
    public synchronized void download(String saveFilePath, String pServerFilePath,int iFileId) {
        download(saveFilePath, pServerFilePath, "",iFileId);
    }

    /**
     * 下载文件
     *
     * @param saveFilePath     保存文件路径
     * @param pServerFilePath  下载路径
     * @param saveFileDate     添加额外的数据
     */
    public synchronized void download(String saveFilePath, String pServerFilePath, String saveFileDate,int iFileId) {
        OkDownload.getInstance().setFolder(saveFilePath);
        GetRequest<File> request = OkGo.get("http://" + AppDataCache.getInstance().getString(IP_ADDRESS) + pServerFilePath);
        //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
        OkDownload.request(iFileId+"", request).extra1(saveFileDate)//extra1添加额外的数据
                .save()
                .register(new DownLoadFileListener())
                .start();
    }

    /**
     * 根据文件类型下载到不同目录
     *
     * @param strDownPath 下载路径
     * @param strFileName 显示文件名
     * @param iFileId     文件id
     */
    public void setDownFile(String strDownPath,int iFileId) {
        if (AppDataCache.getInstance().getInt(Config.MEETING_ID)!=0){
            String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
            int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
            download(Config.downloadpath +StringUtil.strSplit(ip)+port+"/"+AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/" +iFileId, strDownPath, iFileId);
        }
    }
    //文件上传
    // pFilePath	文件路径
    // pFileName	文件名称
    // nFileType	文件类型     4 临时文件　5 批注文件
    public void UpLoadFile(byte[] pFilePath, byte[] pFileName, int nFileType) {
        UploadFileUtil.getInstance().UploadFile(new String(pFilePath), nFileType);
    }
}
