package com.itc.suppaperless.global;

/**
 * Create by zhengwp on 19-2-21.
 *
 * 全局变量类，非静态类可修改值
 */
public class GlobalConstantsBean {
    //The status of document presentation, 1 means someone open it, 0 means the opposite situation.
    private int isSpeakerStatus = 0;
    private int iSpeakId;
    private String iSpeakName;
    //Whether track if someone is in document presentation.
    private int isTrackSpeaker = 0;
    //PdfActivity is open?
    private int isPdfActivityOpen = 0;
    private boolean isConnect=true;//判断是否断网

    /////////////////   getter and setter   /////////////////
    public int getIsSpeakerStatus() {
        return isSpeakerStatus;
    }

    public void setIsSpeakerStatus(int isSpeakerStatus) {
        this.isSpeakerStatus = isSpeakerStatus;
    }

    public int getiSpeakId() {
        return iSpeakId;
    }

    public void setiSpeakId(int iSpeakId) {
        this.iSpeakId = iSpeakId;
    }

    public String getiSpeakName() {
        return iSpeakName;
    }

    public void setiSpeakName(String iSpeakName) {
        this.iSpeakName = iSpeakName;
    }

    public int getIsTrackSpeaker() {
        return isTrackSpeaker;
    }

    public void setIsTrackSpeaker(int isTrackSpeaker) {
        this.isTrackSpeaker = isTrackSpeaker;
    }

    public int getIsPdfActivityOpen() {
        return isPdfActivityOpen;
    }

    public void setIsPdfActivityOpen(int isPdfActivityOpen) {
        this.isPdfActivityOpen = isPdfActivityOpen;
    }

    public boolean getConnect() {
        return isConnect;
    }

    public void setConnect(boolean conect) {
        isConnect = conect;
    }

    /////////////////   other method   /////////////////



}
