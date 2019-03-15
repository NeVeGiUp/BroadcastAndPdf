package com.itc.suppaperless.pdfmodule.bean;

import com.itc.suppaperless.base.BaseBean;

/**
 * Create by zhengwp on 19-2-20.
 *
 * @param SId  Speaker Id
 * @param SName  Speaker name
 */
public class GsonOpenDocBean extends BaseBean {
    private int sId;
    private String sName;
    private int fId;
    private String fname;
    private String fSysname;
    private String fDownPath;
    private int isAgenda;
    private int isAllSend;

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getfSysname() {
        return fSysname;
    }

    public void setfSysname(String fSysname) {
        this.fSysname = fSysname;
    }

    public String getfDownPath() {
        return fDownPath;
    }

    public void setfDownPath(String fDownPath) {
        this.fDownPath = fDownPath;
    }

    public int getIsAgenda() {
        return isAgenda;
    }

    public void setIsAgenda(int isAgenda) {
        this.isAgenda = isAgenda;
    }

    public int getIsAllSend() {
        return isAllSend;
    }

    public void setIsAllSend(int isAllSend) {
        this.isAllSend = isAllSend;
    }
}
