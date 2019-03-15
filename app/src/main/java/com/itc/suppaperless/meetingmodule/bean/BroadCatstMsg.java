package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;

/**
 * Created by xiaogf on 19-3-13.
 */

public class BroadCatstMsg {
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
