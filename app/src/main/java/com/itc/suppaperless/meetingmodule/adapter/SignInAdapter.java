package com.itc.suppaperless.meetingmodule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;

/**
 * Created by xiaogf on 19-3-4.
 */

public class SignInAdapter extends BaseQuickAdapter<JiaoLiuUserInfo.LstUserBean,BaseViewHolder> {
    BaseViewHolder helper;
    public SignInAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, JiaoLiuUserInfo.LstUserBean item) {
        this.helper=helper;
        helper.setText(R.id.tv_name,item.getStrUserName());
        helper.setText(R.id.tv_unit,item.getStrCompany());
        helper.setText(R.id.tv_position,item.getStrPost());
        helper.addOnClickListener(R.id.tv_position);
        if (item.getStrSignTime()==null||item.getStrSignTime().isEmpty()){
            helper.setText(R.id.tv_sign_time,"未签到");
        }else {
            helper.setText(R.id.tv_sign_time,item.getStrSignTime());

        }
        CheckBox checkBox= helper.getView(R.id.cb_selecr_item);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_unit=helper.getView(R.id.tv_unit);
        TextView tv_position=helper.getView(R.id.tv_position);
        TextView tv_sign_time=helper.getView(R.id.tv_sign_time);
        if (item.isSeclet()){
            checkBox.setChecked(true);
            tv_name.setTextColor(Color.parseColor("#1792ff"));
            tv_unit.setTextColor(Color.parseColor("#1792ff"));
            tv_position.setTextColor(Color.parseColor("#1792ff"));
            tv_sign_time.setTextColor(Color.parseColor("#1792ff"));
        }else {
            checkBox.setChecked(false);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            tv_unit.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            tv_position.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            tv_sign_time.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }

    }

}
