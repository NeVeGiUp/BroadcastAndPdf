package com.itc.suppaperless.meetingmodule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.utils.LayoutUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.UiUtil;


/**
 * Created by xiaogf on 19-1-17.
 */

public class CailiaoAdapter extends BaseQuickAdapter<CommentUploadListInfo.LstFileBean, BaseViewHolder> {

    public CailiaoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentUploadListInfo.LstFileBean item) {
        helper.setText(R.id.tv_upload_title, item.getStrName());
        ImageView iv_upload = helper.getView(R.id.iv_upload);
        TextView tv_top = helper.getView(R.id.tv_top);
        RelativeLayout rl_fat = helper.getView(R.id.rl_fat);
        TextView download_text = helper.getView(R.id.download_text);
        if (item.getiIsDir() == 1) {
            UiUtil.showCurFileImage(mContext, iv_upload, true);
            tv_top.setVisibility(View.GONE);
            if (item.getFileNum() != 0) {
                download_text.setVisibility(View.VISIBLE);
                download_text.setText("(" + item.getFileNum() + ")");
            } else {
                download_text.setVisibility(View.GONE);
            }
        } else {
            UiUtil.showCurFileImage(mContext, iv_upload, item.getStrName());
            tv_top.setVisibility(View.VISIBLE);
            switch (item.getIsDown()) {
                case 0:
                    download_text.setVisibility(View.VISIBLE);
                    download_text.setText("等待下载");
                    break;
                case 1:
                    download_text.setVisibility(View.VISIBLE);
                    download_text.setText("下载中...");
                    if (item.getTotelprogress() != 0 && item.getiIsShowProgress()) {
                        LayoutUtil.SetLayoutWidth(tv_top, ScreenUtil.getScreenWidth(mContext) * item.getCurrentprogress()/item.getTotelprogress());
                    }
                    break;
                case 2:
                    download_text.setVisibility(View.GONE);
                    LayoutUtil.SetLayoutWidth(tv_top, 0);
                    break;
                case 3:
                    download_text.setVisibility(View.VISIBLE);
                    download_text.setText("下载失败");
                    LayoutUtil.SetLayoutWidth(tv_top, 0);
                    break;
            }
        }
    }
}
