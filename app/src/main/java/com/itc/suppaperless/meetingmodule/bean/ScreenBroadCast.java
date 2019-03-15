package com.itc.suppaperless.meetingmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Created by xiaogf on 19-3-12.
 */

public class ScreenBroadCast extends BaseBean{
    private int vodCtrlType;
    private double pos;
    private boolean loop;
    private int volume;
    private boolean force;
    private String videoUrl;
    private int fileId;
    private int userId;

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

    public int getVodCtrlType() {
        return vodCtrlType;
    }

    public void setVodCtrlType(int vodCtrlType) {
        this.vodCtrlType = vodCtrlType;
    }

    public double getPos() {
        return pos;
    }

    public void setPos(double pos) {
        this.pos = pos;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
}
