package com.itc.suppaperless.pdfmodule.adapter;

import android.graphics.Bitmap;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;

/**
 * Create by zhengwp on 19-2-17.
 */
public class PdfPreviewAdapter extends BaseQuickAdapter<Bitmap, BaseViewHolder> {

    public PdfPreviewAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bitmap item) {
        helper.setImageBitmap(R.id.iv_pdf_module_previewitem, item);
    }
}
