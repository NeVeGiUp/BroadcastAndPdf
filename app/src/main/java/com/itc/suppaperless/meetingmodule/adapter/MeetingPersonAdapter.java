package com.itc.suppaperless.meetingmodule.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.mvp.contract.MeetingPersonContract;

/**
 * Created by xiaogf on 19-3-4.
 */

public class MeetingPersonAdapter extends BaseQuickAdapter<JiaoLiuUserInfo.LstUserBean,BaseViewHolder>{

    public MeetingPersonAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, JiaoLiuUserInfo.LstUserBean item) {
        helper.setText(R.id.tv_person_num,""+(helper.getAdapterPosition()+1));
        helper.setText(R.id.tv_person_name,item.getStrUserName());
        helper.setText(R.id.tv_person_role,"普通");
        if (item.getiIsChairMan()==1){
            helper.setText(R.id.tv_person_role,"主席");
        }
        if (item.getiIsSecretary()==1){
            helper.setText(R.id.tv_person_role,"秘书");
        }
        helper.setText(R.id.tv_person_unit,item.getStrCompany());
        helper.setText(R.id.tv_person_department,item.getStrCompany());
        helper.setText(R.id.tv_person_position,item.getStrPost());
        if (item.getIUserID()== AppDataCache.getInstance().getInt(Config.USER_ID)){
            helper.getView(R.id.iv_personal_my).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.iv_personal_my).setVisibility(View.INVISIBLE);
        }
    }

}
