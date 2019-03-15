package com.itc.suppaperless.meetingmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.MyAdapterItemListener;

import java.util.List;

/**
 * Created by huangsm on 2017/6/6.
 */

public class FileRecycleViewAdapter extends RecyclerView.Adapter<FileRecycleViewAdapter.MyViewHolder> {
    private Context context;
    private List<String> formatList;
    private MyAdapterItemListener listener;

    public FileRecycleViewAdapter(Context context, List<String> formatList) {
        this.context = context;
        this.formatList = formatList;
    }

    public void setAdapterItemListener(MyAdapterItemListener listener){
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gridview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textFileName.setText(formatList.get(position));
        if(formatList.size() == 1){
            holder.textFileName.setBackgroundResource(R.mipmap.file04);
        }else if(formatList.size() == 2){
            if(position == 0){
                holder.textFileName.setBackgroundResource(R.mipmap.file01);
            }else {
                holder.textFileName.setBackgroundResource(R.mipmap.file03);
            }
        }else if(formatList.size() >= 3){
            if(position == 0){
                holder.textFileName.setBackgroundResource(R.mipmap.file01);
            }else if(position == (formatList.size() - 1)){
                holder.textFileName.setBackgroundResource(R.mipmap.file03);
            }else {
                holder.textFileName.setBackgroundResource(R.mipmap.file02);
            }
        }
        holder.textFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.myItemListener(position);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return formatList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textFileName;

        MyViewHolder(View itemView) {
            super(itemView);
            textFileName = (TextView) itemView.findViewById(R.id.text_fileName);
        }
    }

}
