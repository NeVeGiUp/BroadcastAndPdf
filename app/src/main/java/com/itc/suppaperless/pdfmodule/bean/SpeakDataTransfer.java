package com.itc.suppaperless.pdfmodule.bean;

import com.itc.suppaperless.base.BaseBean;
import com.itc.suppaperless.base.BaseView;

import java.util.Queue;

/**
 * Create by zhengwp on 3/3/19.
 */
public class SpeakDataTransfer extends BaseBean {
    private String optStr;
    private Queue<String> annotateBean;

    public SpeakDataTransfer(String optStr, Queue<String> annotateBean) {
        this.optStr = optStr;
        this.annotateBean = annotateBean;
    }

    public String getOptStr() {
        return optStr;
    }

    public Queue<String> getAnnotateBean() {
        return annotateBean;
    }
}
