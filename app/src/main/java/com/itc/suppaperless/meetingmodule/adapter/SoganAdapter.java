package com.itc.suppaperless.meetingmodule.adapter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.SoganList;

/**
 * Created by xiaogf on 19-3-7.
 */

public class SoganAdapter extends BaseQuickAdapter<SoganList, BaseViewHolder> {
    private int position;
    RelativeLayout rl_view;
    BaseViewHolder helper;
    private boolean isUpdateAllData = true;

    public SoganAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SoganList item) {
        position = helper.getPosition();
        Log.i("ddddddddddffff", position + "");
        this.helper = helper;
        rl_view = helper.getView(R.id.rl_view);
        if (isUpdateAllData) {
            helper.setText(R.id.tv_sogan, item.getStrSloganName());
            ImageView imageView = helper.getView(R.id.iv_sogan);
            Glide.with(mContext).load(item.getStrUrl()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);//
        }
        if (item.isHas()) {
            rl_view.setBackgroundResource(R.drawable.bg_sogan);
        } else {
            rl_view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }
    public void setIsUpdateAllData(boolean isUpdateAllData){
        this.isUpdateAllData=isUpdateAllData;
        notifyDataSetChanged();

    }

}
