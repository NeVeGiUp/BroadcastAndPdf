package com.itc.suppaperless.pdfmodule.mvp.presenter;

import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.utils.FileDaoUtil;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by zhengwp on 19-2-26.
 */
public class SwitchFileP {

    public static List<CommentUploadListInfo.LstFileBean> getListsByFileId(int fId) {
        int fileId = fId;
        List<CommentUploadListInfo.LstFileBean> itemData = new ArrayList<>();
        CommentUploadListInfo.LstFileBean bean = FileDaoUtil.getFileData(fileId);
        //Consider the exception of the null pointer.
        if (bean == null){
            return itemData;

        } else {
            int fileType = bean.getiType();
            int yitiId = bean.getiModuleID();
            /** Get all file data from sql(greendao);*/
            List<CommentUploadListInfo.LstFileBean> allDataInSQL = FileDaoUtil.getAllFileData(fileType);
            for (int i = 0; i < allDataInSQL.size(); i++) {
                if (fileType == allDataInSQL.get(i).getiType() && fileId != allDataInSQL.get(i).getiID()
                        && Config.SWITCHFILETYPE.contains(StringUtil.getsuffix( FileOpenUtil.getfileSystemName(allDataInSQL.get(i).getiID()) ).trim().toLowerCase() ) ) {
                    switch (fileType) {
                        //Meeting file.
                        case Config.DocTemporaryFileType:
                        case Config.DocAnnotationType:
                        case Config.HandwriteAnnotationType:
                        case Config.ElectronAnnotationType:
                            itemData.add(allDataInSQL.get(i));
                            break;
                        //Yiti file.
                        case Config.DocYitiFileType:
                            if (yitiId == allDataInSQL.get(i).getiModuleID()) {
                                itemData.add(allDataInSQL.get(i));
                            }
                            break;
                    }
                }
            }
            return itemData;
        }
    }

}
