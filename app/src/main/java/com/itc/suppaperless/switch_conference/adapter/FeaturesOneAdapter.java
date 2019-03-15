package com.itc.suppaperless.switch_conference.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by cong on 19-1-15.
 */
public class FeaturesOneAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {


    public FeaturesOneAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        switch (item){
            case R.string.jizhong_message:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.jizhong_message));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_hyxx_n);
                break;
            case R.string.issue_material:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.issue_material));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_ytcl_n);
                break;
                case R.string.meeting_person:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.meeting_person));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_chmd_n);
                break;
            case R.string.meeting_material:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.meeting_material));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_lswj_n);
                break;
            case R.string.meeting_voting:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.meeting_voting));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_hytp_n);
                break;
            case R.string.meeting_service:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.meeting_service));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_hyfw_n);
                break;
            case R.string.view_comments:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.view_comments));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_ckpz_n);
                break;
            case R.string.more_features:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.more_features));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_qtgn_n);
                break;
        }
    }
}
