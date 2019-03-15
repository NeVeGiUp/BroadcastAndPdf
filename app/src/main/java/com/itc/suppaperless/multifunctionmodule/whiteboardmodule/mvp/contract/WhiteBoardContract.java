package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.contract;


import android.content.Context;
import android.graphics.Bitmap;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.BaseEventPresenter;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.model.WhiteBoardModelImpl;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.widget.WhiteBoardView;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-11 下午3:37
 * @ desc   : whiteboard module contract interface
 */
public interface WhiteBoardContract {

    interface View extends BaseView {
        /** callback */
        void screenshotSaveSuccess();
        void screenshotSaveFailed();
        void screenshotUploadSuccess();
        void screenshotUploadFailed();

    }

    abstract class Presenter extends BaseEventPresenter<View, WhiteBoardModelImpl> {

        public Presenter(View view) {
            super(view);
        }

        /** save screenshot */
        public abstract void saveScreenshot(Context context,String type,Bitmap bitmap);

        /** upload screenshot */
        public abstract void uploadScreenshot(Context context, Bitmap bitmap,String filePath);

        public abstract Bitmap openBgSketchpad(Context context, String path, WhiteBoardView whiteBoardView);
    }

}
