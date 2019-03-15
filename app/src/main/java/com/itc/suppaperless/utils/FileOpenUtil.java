package com.itc.suppaperless.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.pdfmodule.bean.SpeakDataTransfer;
import com.itc.suppaperless.pdfmodule.configure.PdfConfigure;
import com.itc.suppaperless.pdfmodule.ui.PdfBrowseActivity;
import com.itc.suppaperless.player.FFmpegPlayActivity;

import org.greenrobot.greendao.annotation.Id;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import static com.itc.suppaperless.global.Config.FILE_NAME;

/**
 * Create by zhengwp on 19-2-14.
 *
 * 打开文件辅助类
 */
public class FileOpenUtil {

    /**
     * Get the file's prefix path.
     * 获取文件在内存系统的前缀路径
     * @param fId  文件id
     * @return
     */
    public static String getfilePrefixPath(int fId) {
        return Config.downloadpath + StringUtil.strSplit(AppDataCache.getInstance().getString(Config.IP_MEETING))
                + AppDataCache.getInstance().getInt(Config.PORT_MEETING) + "/" + AppDataCache.getInstance().getInt(Config.MEETING_ID) + "/" + fId;
    }

    /**
     * Get the file's prefix path.
     * 获取文件在内存系统的前缀路径
     * @param path  folder relative path.
     * @return
     */
    public static String getfilePrefixPath(String path) {
        return Config.downloadpath + StringUtil.strSplit(AppDataCache.getInstance().getString(Config.IP_MEETING))
                + AppDataCache.getInstance().getInt(Config.PORT_MEETING) + "/" + AppDataCache.getInstance().getInt(Config.MEETING_ID) + "/" + path;
    }

    /**
     * Get the file name in the system scard.
     *
     * @param fId
     * @return
     */
    public static String getfileSystemName(int fId) {
        File file = new File(Config.downloadpath + StringUtil.strSplit(AppDataCache.getInstance().getString(Config.IP_MEETING))
                + AppDataCache.getInstance().getInt(Config.PORT_MEETING) + "/" + AppDataCache.getInstance().getInt(Config.MEETING_ID) + "/" + fId);
        String[] filelist = file.list();
        if (filelist == null || filelist.length <= 0) {
            return "";
        } else {
            return filelist[0];
        }
    }

    /**
     * 打开文件
     *
     * @param fileId 文件id
     * @param fileName 文件名
     * @param fileSystemName 文件存在本地存储的名字
     *
     * 文件存储路径 = Config.downloadpath + fileId + "/" + fileSystemName
     */
    public static void OpenFile(Context context, int fileId, String fileName, String fileSystemName, String fileDownPath, int isAgenda) {
        OpenFile(context, fileId, fileName, fileSystemName, fileDownPath, isAgenda, null);
    }

    /**
     *
     * @param context
     * @param fileId
     * @param fileName
     * @param fileSystemName
     * @param fileDownPath
     * @param bean To solve the problem that activity is not ready to handle the message from eventbus.
     */
    public static void OpenFile(Context context, int fileId, String fileName, String fileSystemName, String fileDownPath, int isAgenda, SpeakDataTransfer bean) {
        if (StringUtil.getsuffix(fileName).equals("apk")) {
            String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
            int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
            String apkPath = Config.downloadpath +StringUtil.strSplit(ip)+port+"/"+AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/"+ fileId + "/" + fileSystemName;
            if (!StringUtil.isEmpty(apkPath)) {
                openApkFile(context, fileSystemName, Config.downloadpath + StringUtil.strSplit(ip)+port+"/"+AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/"+ fileId);
            }
        } else {
            String suffix = StringUtil.getsuffix(fileSystemName).trim().toLowerCase();  //文件名后缀强行大写转小写
            switch (suffix) {
                //文件格式
                case "pdf": {
                    Intent intent = new Intent(context, PdfBrowseActivity.class);
                    intent.putExtra(PdfConfigure.loadtype, PdfConfigure.pdfmode);
                    intent.putExtra(PdfConfigure.fileId, fileId);
                    intent.putExtra(PdfConfigure.filename, fileName);
                    intent.putExtra(PdfConfigure.filesystemname, fileSystemName);
                    intent.putExtra(PdfConfigure.filedownpath, fileDownPath);
                    intent.putExtra(PdfConfigure.isAgenda, isAgenda);
                    if (bean != null){
                        intent.putExtra(PdfConfigure.speakbean, bean);
                    }
                    context.startActivity(intent);
                    break;
                }

                //图片
                case "jpg":
                case "bmp":
                case "png":
                case "jpeg": {
                    Intent intent = new Intent(context, PdfBrowseActivity.class);
                    intent.putExtra(PdfConfigure.loadtype, PdfConfigure.imagemode);
                    intent.putExtra(PdfConfigure.fileId, fileId);
                    intent.putExtra(PdfConfigure.filename, fileName);
                    intent.putExtra(PdfConfigure.filesystemname, fileSystemName);
                    intent.putExtra(PdfConfigure.filedownpath, fileDownPath);
                    intent.putExtra(PdfConfigure.isAgenda, isAgenda);
                    if (bean != null){
                        intent.putExtra(PdfConfigure.speakbean, bean);
                    }
                    context.startActivity(intent);
                    break;
                }

                //媒体
                case "mp4":
                case "avi":
                case "mov":
                case "flv":
                case "mkv":
                case "rmvb": {
                    String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
                    int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
                    String path=Config.downloadpath +StringUtil.strSplit(ip)+port+"/"+AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/"+ fileId + "/" + fileSystemName;
                    if (!StringUtil.isEmpty(path)) {
                        Intent intent = new Intent(context, FFmpegPlayActivity.class);
                        intent.putExtra(Config.FILE_PATH, path);
                        intent.putExtra(Config.FILE_NAME,fileName);
                        context.startActivity(intent);
                    }


                    break;
                }
                default:
                    ToastUtil.show(context, "不支持打开此类文件");
                    break;
            }
        }
    }

    /**
     * 打开APK文件
     *
     * @param context 上下文
     * @param fileName 文件名
     * @param folderPath 文件夹路径
     */
    public static void openApkFile(Context context, final String fileName, final String folderPath) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File filePath = new File(folderPath + "/" + fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context,
                    "com.meeting.itc.paperless.fileprovider", filePath);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }else{
            Uri uri = Uri.fromFile(filePath);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}

