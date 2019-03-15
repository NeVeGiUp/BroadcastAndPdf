package com.itc.suppaperless.pdfmodule.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.media.VolumeShaper;

import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.pdfmodule.bean.PointsBean;
import com.itc.suppaperless.pdfmodule.configure.PdfConfigure;
import com.itc.suppaperless.pdfmodule.ui.PdfBrowseActivity;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPage;

import java.io.File;
import java.util.List;

/**
 * Create by zhengwp on 19-2-17.
 */
public class PdfUtil {
    /**
     * To judge which type of the file.
     *
     * @param name file name.
     * @return
     */
    public static int getFileType(String name) {
        String suffix = StringUtil.getsuffix(name).trim().toLowerCase();
        if (("pdf").equals(suffix)) {
            return PdfConfigure.pdfmode;
        } else if (("jpg").equals(suffix) || ("bmp").equals(suffix) || ("png").equals(suffix) ||
                ("jpeg").equals(suffix)) {
            return PdfConfigure.imagemode;
        }
        return 0;
    }

    //To Judge the user who apply document presentation, 'return true' means 'this terminal', 'false' means not.
    public static boolean checkPresentationUser(){
        if (PaperlessApplication.getGlobalConstantsBean().getIsSpeakerStatus() == Config.On_Off.HighLevel &&
                PaperlessApplication.getGlobalConstantsBean().getiSpeakId() == (AppDataCache.getInstance().getInt(Config.USER_ID)))
            return true;
        else
            return false;
    }

    /**
     * 检查是否有人在主讲
     * @return
     */
    public static boolean checkIsSpeaking(){
        if (PaperlessApplication.getGlobalConstantsBean().getIsSpeakerStatus() == Config.On_Off.HighLevel)
            return true;
        else
            return false;
    }

    public static void setIsSpeaking(boolean status, int mUserId, String mUsername){
        if (status){
            PaperlessApplication.getGlobalConstantsBean().setIsSpeakerStatus(Config.On_Off.HighLevel);
        }else {
            PaperlessApplication.getGlobalConstantsBean().setIsSpeakerStatus(Config.On_Off.LowLevel);
        }
        PaperlessApplication.getGlobalConstantsBean().setiSpeakId(mUserId);
        PaperlessApplication.getGlobalConstantsBean().setiSpeakName(mUsername);
    }

    /**
     * 检查是否在跟进主讲
     * @return
     */
    public static boolean checkTrackSpeak(){
        if (PaperlessApplication.getGlobalConstantsBean().getIsTrackSpeaker() == Config.On_Off.HighLevel)
            return true;
        else
            return false;
    }

    public static void setTrackSpeak(boolean status){
        if (status){
            PaperlessApplication.getGlobalConstantsBean().setIsTrackSpeaker(Config.On_Off.HighLevel);
        }else{
            PaperlessApplication.getGlobalConstantsBean().setIsTrackSpeaker(Config.On_Off.LowLevel);
        }
    }

    /**
     * 检查pdfActivity是否在开启
     * @return
     */
    public static boolean checkPdfActivityOpen(){
        if (PaperlessApplication.getGlobalConstantsBean().getIsPdfActivityOpen() == Config.On_Off.HighLevel)
            return true;
        else
            return false;
    }

    public static void setPdfActivityOpen(boolean status){
        if (status){
            PaperlessApplication.getGlobalConstantsBean().setIsPdfActivityOpen(Config.On_Off.HighLevel);
        }else{
            PaperlessApplication.getGlobalConstantsBean().setIsPdfActivityOpen(Config.On_Off.LowLevel);
        }
    }

    public static String getSpeakName(){
        return PaperlessApplication.getGlobalConstantsBean().getiSpeakName();
    }

    /**
     * @param fileId  fileId
     * @param fileSysName File name in storage.
     */
    public static boolean checkFileExistInStorage(int fileId, String fileSysName){
        File file = new File(Config.downloadpath + StringUtil.strSplit(AppDataCache.getInstance().getString(Config.IP_MEETING)) + AppDataCache.getInstance().getInt(Config.PORT_MEETING)
                + "/" + AppDataCache.getInstance().getInt(Config.MEETING_ID) + "/" + fileId + "/" + fileSysName);
        return file == null ? false : true;
    }


    /**
     * @param cenX the center point.
     * @param cenY the center point.
     * @return pointBean  the center point bean.
     */
    public static PointsBean getOwnCenterPoint(Context mcontext, float cenX, float cenY, float screenWid){
        float multiScale = screenWid / ScreenUtil.getScreenWidth(mcontext);
        float centerX = cenX / multiScale;
        float centerY = cenY / multiScale;
        return new PointsBean(centerX, centerY);
    }

    /**
     *
     * @param mcontext
     * @param cenX
     * @return The unsignal offset x
     */
    public static float getOriginX(Context mcontext, float cenX){
        return cenX - ScreenUtil.getScreenWidth(mcontext) / 2;
    }

    /**
     *
     * @param mcontext
     * @param cenY
     * @return The unsignal offset y
     */
    public static float getOriginY(Context mcontext, float cenY){
        return cenY - ScreenUtil.getScreenHeight(mcontext) / 2;
    }
}
