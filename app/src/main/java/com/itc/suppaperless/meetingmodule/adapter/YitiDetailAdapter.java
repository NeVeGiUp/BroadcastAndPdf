package com.itc.suppaperless.meetingmodule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.mvp.contract.YitiDetailContract;
import com.itc.suppaperless.utils.LayoutUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.UiUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by xiaogf on 19-1-17.
 */

public class YitiDetailAdapter extends BaseQuickAdapter<CommentUploadListInfo.LstFileBean,BaseViewHolder> {

    public YitiDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentUploadListInfo.LstFileBean item) {
        helper.setText(R.id.tv_upload_title,item.getStrName());
        ImageView iv_upload=helper.getView(R.id.iv_upload);
        TextView tv_top=helper.getView(R.id.tv_top);
        TextView tv_load=helper.getView(R.id.tv_load);
        RelativeLayout rl_fat=helper.getView(R.id.rl_fat);
        if (item.getiIsDir()==1){
            UiUtil.showCurFileImage(mContext,iv_upload,true);
            tv_top.setVisibility(View.GONE);
            if (item.getFileNum()!=0){
                tv_load.setVisibility(View.VISIBLE);
                tv_load.setText("("+item.getFileNum()+")");
            }else {
                tv_load.setVisibility(View.GONE);
            }
        }else {
            tv_top.setVisibility(View.VISIBLE);
            UiUtil.showCurFileImage(mContext,iv_upload,item.getStrName());
            switch (item.getIsDown()){
                case 0:
                    tv_load.setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_load,"等待下载");
                    break;
                case 1:
                    tv_load.setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_load,"下载中...");
                    if (item.getTotelprogress() != 0 ){
                        LayoutUtil.SetLayoutWidth(tv_top, ScreenUtil.getScreenWidth(mContext) * item.getCurrentprogress()/item.getTotelprogress());
                    }
                    break;
                case 2:
                    tv_load.setVisibility(View.GONE);
                    LayoutUtil.SetLayoutWidth(tv_top, 0);
                    break;
                case 3:
                    tv_load.setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_load,"下载失败");
                    LayoutUtil.SetLayoutWidth(tv_top, 0);
                    break;
            }
        }
    }
}
