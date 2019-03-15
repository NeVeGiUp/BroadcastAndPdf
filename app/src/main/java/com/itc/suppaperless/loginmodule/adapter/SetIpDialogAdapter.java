package com.itc.suppaperless.loginmodule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.itc.suppaperless.R;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


public class SetIpDialogAdapter extends BaseAdapter {
    private Context context;
    private List<String> listData = new ArrayList<>();
    private IAdapterClickListener listener;

    public void setIAdapterClickListener(IAdapterClickListener listener){
        this.listener = listener;
    }

    public SetIpDialogAdapter(Context context, List<String> listData){
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
            return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView= View.inflate(context, R.layout.set_dialog_popuwindow_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        if(listData.size() > 0){
            if(!TextUtils.isEmpty(listData.get(position))){
                holder.set_dialog_select_item_text.setText(listData.get(position));
                holder.set_dialog_select_item_delete.setImageResource(AppUtils.isIPad(context) ? R.mipmap.icon_qingchu_nz : R.mipmap.icon_qingchu_n_m);
                holder.set_dialog_select_item_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listData.remove(position);
                        notifyDataSetChanged();
                        listener.adapterClick(v.getId(), position);
                    }
                });
            }
        }
        return convertView;
    }

    public class ViewHolder{
        private TextView set_dialog_select_item_text;
        private ImageView set_dialog_select_item_delete;
        public ViewHolder(View view){
            set_dialog_select_item_text = (TextView) view.findViewById(R.id.set_dialog_select_item_text);
            set_dialog_select_item_delete = (ImageView) view.findViewById(R.id.set_dialog_select_item_delete);
        }
    }
}
