package com.itc.suppaperless.meetingmodule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.itc.suppaperless.R;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.meetingmodule.bean.FileUploadDao;
import com.itc.suppaperless.utils.AppUtils;
import com.itc.suppaperless.utils.FileUtil;
import com.itc.suppaperless.utils.LayoutUtil;
import com.itc.suppaperless.utils.UiUtil;

import java.util.List;

/**
 * 文件上传适配器
 */

public class FileUploadAdapter extends BaseAdapter {

    private Context context;
    private List<FileUploadDao> pathList;

    public FileUploadAdapter(Context context, List<FileUploadDao> pathList) {
        this.context = context;
        this.pathList = pathList;
    }

    private IAdapterClickListener adapterClickListener;

    public void setAdapterClickListener(IAdapterClickListener adapterClickListener){
        this.adapterClickListener = adapterClickListener;
    }

    @Override
    public int getCount() {
        return pathList.size();
    }

    @Override
    public Object getItem(int position) {
        return pathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder hodler;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.dialog_file_upload_list_item, null);
            hodler = new ViewHolder(convertView);
            convertView.setTag(hodler);
        }else{
            hodler = (ViewHolder) convertView.getTag();
        }

        FileUploadDao dao = pathList.get(position);
        String pathName = dao.getFileName();
        String fileName = FileUtil.getFileName(pathName);
        hodler.tv_upload_title.setText(fileName);

        if(AppUtils.isIPad(context)){
            hodler.tv_upload_title.setMaxEms(28);
            hodler.tv_upload_title.setEllipsize(TextUtils.TruncateAt.END);
            hodler.tv_upload_title.setSingleLine(true);
        }else {
            hodler.tv_upload_title.setMaxEms(14);
            hodler.tv_upload_title.setEllipsize(TextUtils.TruncateAt.END);
            hodler.tv_upload_title.setSingleLine(true);
        }

        UiUtil.showCurFileImage(context, hodler.iv_upload, pathName);

        if(dao.getUploadState() == 0){//等待上传
            hodler.download_text.setVisibility(View.VISIBLE);
            hodler.imageView_upload_state.setVisibility(View.GONE);
            hodler.download_text.setText(context.getResources().getString(R.string.wait_upload));
        }else if(dao.getUploadState() == 1){//上传失败
            hodler.download_text.setVisibility(View.GONE);
            hodler.imageView_upload_state.setVisibility(View.VISIBLE);
            if(AppUtils.isIPad(context)){//平板
                hodler.imageView_upload_state.setImageResource(R.mipmap.ic_ls_shuaxin);
            }else{//手机
                hodler.imageView_upload_state.setImageResource(R.mipmap.ic_is_shuaxini_mobile);
            }
        }else if(dao.getUploadState() == 2){//正在上传
//            hodler.download_text.setVisibility(View.GONE);
//            hodler.imageView_upload_state.setVisibility(View.VISIBLE);
//            if(AppUtil.isIPad(context)){//平板
//                hodler.imageView_upload_state.setImageResource(R.mipmap.ic_ls_quxiao);
//            }else{//手机
//                hodler.imageView_upload_state.setImageResource(R.mipmap.ic_is_quxiaoi_mobile);
//            }
            hodler.download_text.setVisibility(View.VISIBLE);
            hodler.imageView_upload_state.setVisibility(View.GONE);
            hodler.download_text.setText(context.getResources().getString(R.string.uploading_file));
        }else if(dao.getUploadState() == 3){//上传成功
            hodler.download_text.setVisibility(View.VISIBLE);
            hodler.imageView_upload_state.setVisibility(View.GONE);
            hodler.download_text.setText(context.getResources().getString(R.string.upload_success));
        }else if(dao.getUploadState() == 4){//文件失效
            hodler.download_text.setVisibility(View.VISIBLE);
            hodler.imageView_upload_state.setVisibility(View.GONE);
            hodler.download_text.setText(context.getResources().getString(R.string.upload_file_lose));
        }

        if(hodler.imageView_upload_state.getVisibility() == View.VISIBLE){
            hodler.imageView_upload_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterClickListener.adapterClick(v.getId(), position);
                }
            });
        }

        if (dao.getTotelProgress() != 0){
            LayoutUtil.SetLayoutWidth(hodler.tv_top, parent.getMeasuredWidth() * dao.getCurrentProgress() / dao.getTotelProgress());
            if (dao.getUploadState() == 1 || dao.getUploadState() == 3 || dao.getUploadState() == 4){
                LayoutUtil.SetLayoutWidth(hodler.tv_top, 0);
            }
        }

        return convertView;
    }

    public class ViewHolder{
        private TextView tv_top;
        private ImageView iv_upload;
        private TextView tv_upload_title;
        private TextView download_text;
        private ImageView imageView_upload_state;
        public ViewHolder(View view){
            tv_top = (TextView) view.findViewById(R.id.tv_top);
            iv_upload = (ImageView) view.findViewById(R.id.iv_upload);
            tv_upload_title = (TextView) view.findViewById(R.id.tv_upload_title);
            download_text = (TextView) view.findViewById(R.id.download_text);
            imageView_upload_state = (ImageView) view.findViewById(R.id.imageView_upload_state);
        }
    }

}
