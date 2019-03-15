package com.itc.suppaperless.switch_conference.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;

/**
 * Created by cong on 19-1-15.
 */
public class FeaturesTwoAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {


    public FeaturesTwoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        switch (item){
            case R.string.sign_in_management:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.sign_in_management));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_qdgl_n);
                break;
            case R.string.voting_management:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.voting_management));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_tpgl_n);
                break;
            case R.string.topic_management:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.topic_management));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_ytgl_n);
                break;
            case R.string.meeting_slogan:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.meeting_slogan));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_hyby_n);
                break;
            case R.string.centralized_control:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.centralized_control));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_jzkz_n);
                break;
            case R.string.screen_broad:
                helper.setText(R.id.tv_features_one,mContext.getString(R.string.screen_broad));
                helper.setImageResource(R.id.iv_features_one,R.mipmap.but_dpdb_n);
                break;
        }
    }
}
