package com.itc.suppaperless.channels.common;

import com.itc.suppaperless.base.BaseBean;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-11 下午5:35
 * @ desc   : 数据model
 */
public class CommandData extends BaseBean {

    public String HEAD_TAG;
    public byte strVer;
    public byte strCode;
    public short strType;
    public int iMsgLength;
    public int iSessionID;
    public int iSenderID;
    public short cSeq;
    public short cHCRC;
    public byte[] content;

    public CommandData() {
    }

    public CommandData(short strType, int iSenderID, int contentLength, byte[] content) {
        this.iMsgLength = contentLength;
        this.content = content;
        this.strType = strType;
        this.iSenderID = iSenderID;
    }

    public short getStrType() {
        return strType;
    }

    public void setStrType(short strType) {
        this.strType = strType;
    }

    public short getcSeq() {
        return cSeq;
    }

    public void setcSeq(short cSeq) {
        this.cSeq = cSeq;
    }

    public short getcHCRC() {
        return cHCRC;
    }

    public void setcHCRC(short cHCRC) {
        this.cHCRC = cHCRC;
    }

    public String getHEAD_TAG() {
        return HEAD_TAG;
    }

    public void setHEAD_TAG(String HEAD_TAG) {
        this.HEAD_TAG = HEAD_TAG;
    }

    public byte getStrVer() {
        return strVer;
    }

    public void setStrVer(byte strVer) {
        this.strVer = strVer;
    }

    public byte getStrCode() {
        return strCode;
    }

    public void setStrCode(byte strCode) {
        this.strCode = strCode;
    }

    public int getiMsgLength() {
        return iMsgLength;
    }

    public void setiMsgLength(int iMsgLength) {
        this.iMsgLength = iMsgLength;
    }

    public int getiSessionID() {
        return iSessionID;
    }

    public void setiSessionID(int iSessionID) {
        this.iSessionID = iSessionID;
    }

    public int getiSenderID() {
        return iSenderID;
    }

    public void setiSenderID(int iSenderID) {
        this.iSenderID = iSenderID;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
