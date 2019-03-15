package com.itc.suppaperless.multifunctionmodule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.multifunctionmodule.bean.MultiFunctionItemBean;

/**
 * Create by zhengwp on 19-2-25.
 */
public class MultiFunctionAdapter extends BaseQuickAdapter<MultiFunctionItemBean, BaseViewHolder> {

    public MultiFunctionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiFunctionItemBean item) {
        helper.setImageResource(R.id.iv_multifun_item, item.getImageId());
        helper.setText(R.id.tv_multifun_item_text, item.getTitle());
    }

}
