package com.itc.suppaperless.meetingmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.FileItemInfo;

import java.util.ArrayList;

/**
 * Created by huangsm on 2017/6/29.
 */

public class SearchFileAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FileItemInfo> newFileItemInfos;

    public SearchFileAdapter(Context context, ArrayList<FileItemInfo> newFileItemInfos) {
        this.context = context;
        this.newFileItemInfos = newFileItemInfos;
    }

    @Override
    public int getCount() {
        return newFileItemInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return newFileItemInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.myfile_listitem_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        FileItemInfo fileItem = newFileItemInfos.get(position);
        holder.img.setImageResource(fileItem.getIcon());
        holder.title.setText(fileItem.getTitle());
        holder.info.setText(fileItem.getInfo());
        holder.checkBox.setVisibility(View.GONE);
        holder.image_select_file.setVisibility(View.GONE);
        return convertView;
    }

    public class ViewHolder{
        private ImageView img;
        private TextView title;
        private TextView info;
        private CheckBox checkBox;
        private ImageView image_select_file;

        public ViewHolder(View view){
            img = (ImageView)view.findViewById(R.id.img_fileIcon);
            title = (TextView)view.findViewById(R.id.label_fileTitle);
            info = (TextView)view.findViewById(R.id.label_fileMemo);
            checkBox = (CheckBox)view.findViewById(R.id.chbox_filecheck);
            image_select_file = (ImageView) view.findViewById(R.id.image_select_file);
        }
    }

}
