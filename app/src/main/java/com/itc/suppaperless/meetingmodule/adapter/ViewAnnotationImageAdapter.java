package com.itc.suppaperless.meetingmodule.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.LayoutUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.UiUtil;

import java.io.File;
import java.util.List;

/**
 * Create by zhengwp on 19-3-8.
 */
public class ViewAnnotationImageAdapter extends RecyclerView.Adapter<ViewAnnotationImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<CommentUploadListInfo.LstFileBean> fileitems;
    private IAdapterClickListener clickListener;

    public ViewAnnotationImageAdapter(Context mContext, List<CommentUploadListInfo.LstFileBean> fileitems) {
        this.mContext = mContext;
        this.fileitems = fileitems;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_annotation_image, parent, false);
        return new ImageViewHolder(view);
    }

    public void setAdpterClick(IAdapterClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setItemData(List<CommentUploadListInfo.LstFileBean> itemData) {
        this.fileitems = itemData;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int positions) {
        UiUtil.showCurFileImage(mContext, holder.ivAnnotationImage, fileitems.get(positions).getStrName());
        /** Override the size of the item.*/
        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams( (int)(ScreenUtil.getScreenWidth(mContext) / 3), (int)(ScreenUtil.getScreenHeight(mContext) / 3)) );

        holder.tvProgress.setVisibility(View.VISIBLE);

        switch (fileitems.get(positions).getIsDown()) {
            case 0:/** Waiting for download.*/
                break;
            case 1:/** Downloading.*/
                if (fileitems.get(positions).getTotelprogress() != 0 && fileitems.get(positions).getiIsShowProgress()) {
                    LayoutUtil.SetLayoutWidth(holder.tvProgress, holder.ivAnnotationImage.getMeasuredWidth() * fileitems.get(positions).getCurrentprogress()/fileitems.get(positions).getTotelprogress());
                }
                break;
            case 2:/** Download finished.*/
                holder.tvProgress.setVisibility(View.GONE);
                holder.ivAnnotationImage.setImageURI( Uri.fromFile(new File(
                        FileOpenUtil.getfilePrefixPath(fileitems.get(positions).getiID()) + "/" + FileOpenUtil.getfileSystemName(fileitems.get(positions).getiID())
                                  )));
                break;
            case 3:/** Download fail.*/
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.adapterClick(v.getId(), positions);
            }
        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                holder.ivAnnotationDel.setVisibility(View.VISIBLE);
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return fileitems== null ? 0 : fileitems.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProgress;
        private ImageView ivAnnotationImage;
        private ImageView ivAnnotationDel;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ivAnnotationImage = itemView.findViewById(R.id.iv_annotation_image);
            ivAnnotationDel = itemView.findViewById(R.id.iv_annotation_del);
            tvProgress = itemView.findViewById(R.id.tv_progress);
        }
    }
}
