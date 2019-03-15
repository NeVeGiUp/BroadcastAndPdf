package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.UploadSuccessEvent;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.asy.BitmapSaveToLocalTask;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener.BitmapSaveResult;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.contract.WhiteBoardContract;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.model.WhiteBoardModelImpl;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.widget.WhiteBoardView;
import com.itc.suppaperless.utils.BitmapUtil;
import com.itc.suppaperless.utils.FileUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.TimeUtil;
import com.itc.suppaperless.utils.UploadFileUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.itc.suppaperless.global.Config.IP_ADDRESS;
import static com.itc.suppaperless.global.Config.PORT_ADDRESS;
import static com.itc.suppaperless.global.Config.USER_ID;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-11 上午10:47
 * @ desc   : whiteboard module presenter (data processing)
 */
public class WhiteBoardPresenter extends WhiteBoardContract.Presenter {


    private String filePath;  /** annotation/whiteboard save folder */

    private String fileName;  /** annotation/whiteboard save name */

    private int uploadType;  /** uploadType */


    public WhiteBoardPresenter(WhiteBoardContract.View whiteBoardView) {
        super(whiteBoardView);
        mModel = new WhiteBoardModelImpl();
    }


    /**
     * open the sketchpad from the handwriting annotataion
     *
     * @param path
     * @param whiteBoardView
     * @return
     */
    @Override
    public Bitmap openBgSketchpad(Context context, String path, WhiteBoardView whiteBoardView) {
        Bitmap bgBitmap = null;
        if (path.length() > 1 && null != path) {
            bgBitmap = BitmapUtil.loadBitmapFromSDCard(path, ScreenUtil.getScreenWidth(context), ScreenUtil.getScreenHeight(context))
                    .copy(Bitmap.Config.ARGB_8888, true);
            if (bgBitmap != null)
                whiteBoardView.setBkBitmap(bgBitmap);
        }
        return bgBitmap;
    }


    /**
     * file upload ccallback
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadEvent(UploadSuccessEvent event) {
        if (null != event) {
            mModel.uploadScreenshot(fileName, event.getData(), event.getResponse(), uploadType);
        }
    }

    /**
     * saveScreenshot
     *
     * @param context
     * @param type
     * @param bitmap
     */
    @Override
    public void saveScreenshot(Context context, String type, Bitmap bitmap) {

        String userId = AppDataCache.getInstance().getString(IP_ADDRESS).replace("//.", "") + "/" +
                AppDataCache.getInstance().getString(PORT_ADDRESS) + "/" + AppDataCache.getInstance().getInt(USER_ID) + "/";

        if ("handWritingAnnotation".equals(type)) {  /** annotation  filepath */
            filePath = FileUtil.getStrokeFilePath(context, Config.imageFileLocationSnapshot + userId);
        } else if ("personalWhiteBoard".equals(type)) {  /** personal whiteboard filepath */
            filePath = FileUtil.getStrokeFilePath(context, Config.imageFileLocation + userId);
        } else if ("".equals(type)) {  /**  interactive whiteboard filepath */
            filePath = FileUtil.getStrokeFilePath(context, Config.imageMutualFileLocation + userId);
        }

        fileName = TimeUtil.getCurrentFileNameTime() + Config.imagePngFomat;
        if (null != bitmap) {
            /** Local save successfully uploaded to server */
            new BitmapSaveToLocalTask(context, bitmap, filePath + fileName, new BitmapSaveResult() {
                @Override
                public void onPostExecute() {
                    getView().screenshotSaveSuccess();
                    if ("handWritingAnnotation".equals(type))  /** handwriting annotation upload type */
                        uploadType = 12;
                    else if ("".equals(type)) {  /** document annotation upload type */
                        uploadType = 5;
                    } else if ("personalWhiteBoard".equals(type)) {  /** personal/interactive whiteboard annotation upload type */
                        uploadType = 13;
                    }
                    UploadFileUtil.getInstance().UploadFile(filePath + fileName, 1);  /** upload to server,and send commands to the server */
                }
            }, true).execute();
        } else {
            getView().screenshotSaveFailed();
        }

    }


    /**
     * uploadScreenshot
     *
     * @param context
     * @param bitmap
     * @param filePath
     */
    @Override
    public void uploadScreenshot(Context context, Bitmap bitmap, String filePath) {

    }

}
