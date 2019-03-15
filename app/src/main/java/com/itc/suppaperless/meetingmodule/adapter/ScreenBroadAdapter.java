package com.itc.suppaperless.meetingmodule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;

/**
 * Created by xiaogf on 19-3-11.
 */

public class ScreenBroadAdapter extends BaseQuickAdapter<CommentUploadListInfo.LstFileBean, BaseViewHolder> {
    public ScreenBroadAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentUploadListInfo.LstFileBean item) {
        helper.setText(R.id.text_live_name,item.getStrName());
        if (item.getIsDown()==0){
            helper.getConvertView().setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            helper.getConvertView().setBackgroundColor(mContext.getResources().getColor(R.color.line_bg_light));
        }
    }
}
