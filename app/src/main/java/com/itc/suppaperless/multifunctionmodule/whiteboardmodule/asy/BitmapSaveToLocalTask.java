package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.asy;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.itc.suppaperless.utils.BitmapUtil;
import com.itc.suppaperless.widget.CommonProgressDialog;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener.BitmapSaveResult;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-8 下午4:58
 * @ desc   : 异步任务：保存bitmap到本地
 */
public class BitmapSaveToLocalTask extends AsyncTask {


    private Context mContext;
    private Bitmap mBitmap;
    private String mFilePath;
    private BitmapSaveResult mOnPostExecute;
    private boolean mIsDismissProgress;
    private CommonProgressDialog mCommonProgressDialog;


    /**
     * 异步保存图片到本地
     *
     * @param context
     * @param bitmap
     * @param filePath
     */
    public BitmapSaveToLocalTask(Context context, Bitmap bitmap, String filePath) {
        this.mBitmap = bitmap;
        this.mFilePath = filePath;
        this.mContext = context;
    }

    /**
     * 异步保存图片到本地
     *
     * @param context
     * @param bitmap
     * @param filePath          保存bitmap本地路径
     * @param onPostExecute     保存bitmap回调
     * @param isDismissProgress 是否消除进度条
     */
    public BitmapSaveToLocalTask(Context context, Bitmap bitmap, String filePath, BitmapSaveResult onPostExecute, boolean isDismissProgress) {
        this.mContext = context;
        this.mBitmap = bitmap;
        this.mFilePath = filePath;
        this.mOnPostExecute = onPostExecute;
        this.mIsDismissProgress = isDismissProgress;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCommonProgressDialog = new CommonProgressDialog(mContext);
        mCommonProgressDialog.show();
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        BitmapUtil.saveBitmapToSDCard(mBitmap, mFilePath);
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
        if (mIsDismissProgress)
            dismissProgress();

        if (mOnPostExecute != null)
            mOnPostExecute.onPostExecute();
    }


    private void dismissProgress() {
        if (mContext != null)
            mCommonProgressDialog.dismiss();
    }
}
