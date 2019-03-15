package com.itc.suppaperless.meetingmodule.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by xiaogf on 19-1-17.
 */

public class PersonInfoAdatper extends BaseQuickAdapter<String,BaseViewHolder> {
    public PersonInfoAdatper(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public PersonInfoAdatper(@Nullable List<String> data) {
        super(data);
    }

    public PersonInfoAdatper(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
