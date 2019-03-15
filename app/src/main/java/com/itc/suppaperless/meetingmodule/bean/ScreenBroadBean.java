package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;

import java.util.Arrays;

/**
 * Created by xiaogf on 19-3-12.
 */

public class ScreenBroadBean extends BaseBean{

    private String strCmdMsg;
    private int[] aiToUserID=new int[1];
    private int iToUserFlag;
    private int iUserID=AppDataCache.getInstance().getInt(Config.USER_ID);

    public String getStrCmdMsg() {
        return strCmdMsg;
    }

    public void setStrCmdMsg(String strCmdMsg) {
        this.strCmdMsg = strCmdMsg;
    }

    public int getiToUserFlag() {
        return iToUserFlag;
    }

    public void setiToUserFlag(int iToUserFlag) {
        this.iToUserFlag = iToUserFlag;
    }

    public int getiUserID() {
        return iUserID;
    }

    public void setiUserID(int iUserID) {
        this.iUserID = iUserID;
    }


    public int[] getAiToUserID() {
        return aiToUserID;
    }

    public void setAiToUserID(int[] aiToUserID) {
        this.aiToUserID = aiToUserID;
    }

    @Override
    public String toString() {
        return "ScreenBroadBean{" +
                "strCmdMsg='" + strCmdMsg + '\'' +
                ", aiToUserID=" + Arrays.toString(aiToUserID) +
                ", iToUserFlag=" + iToUserFlag +
                ", iUserID=" + iUserID +
                '}';
    }

    public class StrCmdMsg{
        private int vodCtrlType;
        private String videoUrl;
        private int fileId=4;
        private int userId= AppDataCache.getInstance().getInt(Config.USER_ID);
        private int volume;
        private boolean loop;
        private double pos;
        private  int currentTime;
        private int mediaLen;
        private boolean force;
        private int status;

        public int getVodCtrlType() {
            return vodCtrlType;
        }

        public void setVodCtrlType(int vodCtrlType) {
            this.vodCtrlType = vodCtrlType;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getFileId() {
            return fileId;
        }

        public void setFileId(int fileId) {
            this.fileId = fileId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public double getPos() {
            return pos;
        }

        public void setPos(double pos) {
            this.pos = pos;
        }

        public int getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(int currentTime) {
            this.currentTime = currentTime;
        }

        public int getMediaLen() {
            return mediaLen;
        }

        public void setMediaLen(int mediaLen) {
            this.mediaLen = mediaLen;
        }

        public boolean isForce() {
            return force;
        }

        public void setForce(boolean force) {
            this.force = force;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
   }


}
