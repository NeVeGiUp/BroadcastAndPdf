package com.itc.suppaperless.pdfmodule.mvp.model;

import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.pdfmodule.bean.GsonDocOperationBean;
import com.itc.suppaperless.pdfmodule.bean.GsonOpenDocBean;
import com.itc.suppaperless.pdfmodule.configure.PdfConfigure;
import com.itc.suppaperless.pdfmodule.mvp.contract.PdfBrowseContract;
import com.itc.suppaperless.screen_record.model.ScreenRecordModel;

/**
 * Create by zhengwp on 19-1-27.
 */
public class PdfBrowseModel extends ScreenRecordModel implements PdfBrowseContract.PdfBrowseModel{

    @Override
    public void sendDocOperateData(int optCode, int isAgenda, int sId, String sName, int fId, String fname, String fSysname, String fDownPath,
                                   int isAllSend, int page, float scale, float scaleX, float scaleY, float screenwid, float centerX, float centerY) {
        GsonDocOperationBean bean = new GsonDocOperationBean();
        bean.setiCmdEnum(PdfConfigure.channelNum);
        bean.setOptCode(optCode);

        bean.setIsAgenda(isAgenda);
        bean.setSId(sId);
        bean.setSName(sName);
        bean.setfId(fId);
        bean.setFname(fname);
        bean.setfSysname(fSysname);
        bean.setfDownPath(fDownPath);
        bean.setIsAllSend(isAllSend);
        bean.setPage(page);
        bean.setScale(scale);
        bean.setScaleX(scaleX);
        bean.setScaleY(scaleY);
        bean.setScreenWid(screenwid);
        bean.setCenterX(centerX);
        bean.setCenterY(centerY);

        NettyTcpCommonClient.sendPackage(bean);
    }

    @Override
    public void sendCloseSpeakData(){
        GsonDocOperationBean bean = new GsonDocOperationBean();
        bean.setiCmdEnum(PdfConfigure.channelNum);
        bean.setOptCode(PdfConfigure.OptCode.CloseSpeaker);
        NettyTcpCommonClient.sendPackage(bean);
    }

}
