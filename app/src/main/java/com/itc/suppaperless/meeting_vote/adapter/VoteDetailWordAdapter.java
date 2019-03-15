package com.itc.suppaperless.meeting_vote.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meeting_vote.bean.VoteUserNameBean;

/**
 * Created by xiaogf on 19-3-9.
 */

public class VoteDetailWordAdapter extends BaseQuickAdapter<VoteUserNameBean,BaseViewHolder>{
    public VoteDetailWordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteUserNameBean item) {
        helper.setText(R.id.vote_details_item_title,item.getOption());
        helper.setText(R.id.vote_details_item_name,item.getUserName());
    }
}
