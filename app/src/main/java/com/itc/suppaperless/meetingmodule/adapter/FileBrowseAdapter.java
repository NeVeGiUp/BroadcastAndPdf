package com.itc.suppaperless.meetingmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.FileItemInfo;
import com.itc.suppaperless.meetingmodule.bean.FxFileDialogArgs;
import com.itc.suppaperless.meetingmodule.bean.IFxListener;
import com.itc.suppaperless.meetingmodule.bean.MyAdapterItemListener;
import com.itc.suppaperless.utils.FxHelp;

import java.io.File;
import java.util.ArrayList;

/** 文件列表适配器 */
public class FileBrowseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private IFxListener _FxEvent;
    private FxFileDialogArgs _args;
    private ArrayList<FileItemInfo> _filelist;
    private MyAdapterItemListener listener;

    public FileBrowseAdapter(Context context, IFxListener _FxEvent, FxFileDialogArgs _args, ArrayList<FileItemInfo> _filelist){
        this.mInflater = LayoutInflater.from(context);
        this._FxEvent = _FxEvent;
        this._args = _args;
        this._filelist = _filelist;
    }

    public void setAdapterItemListener(MyAdapterItemListener listener){
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return _filelist.size();
    }

    @Override
    public Object getItem(int position) {
        return _filelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.myfile_listitem_layout, parent, false);
            holder.layout_item_file = (LinearLayout) convertView.findViewById(R.id.layout_item_file);
            holder.img = (ImageView)convertView.findViewById(R.id.img_fileIcon);
            holder.title = (TextView)convertView.findViewById(R.id.label_fileTitle);
            holder.info = (TextView)convertView.findViewById(R.id.label_fileMemo);
            holder.checkBox = (CheckBox)convertView.findViewById(R.id.chbox_filecheck);
            holder.image_select_file = (ImageView) convertView.findViewById(R.id.image_select_file);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        FileItemInfo fileItem = _filelist.get(position);
        File file= fileItem.getFile();
        holder.img.setImageResource(fileItem.getIcon());
        holder.title.setText(fileItem.getTitle());
        holder.info.setText(fileItem.getInfo());

        //文件夹
        if(_args.DialogType == FxHelp.DLG_BROWSE_FILE ||
                (_args.DialogType == FxHelp.DLG_SELECT_FOLDER && file.isFile()) ||
                (_args.DialogType != FxHelp.DLG_SELECT_FOLDER && file.isDirectory())){
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.image_select_file.setVisibility(View.VISIBLE);
            holder.layout_item_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myItemListener(position);
                }
            });
        } else {//文件
            holder.layout_item_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(_FxEvent != null){
                        if(holder.checkBox.isChecked()){
                            _FxEvent.OnTrigger(v, position, false);
                        }else {
                            _FxEvent.OnTrigger(v, position, true);
                        }
                    }
                }
            });
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setEnabled(false);
            holder.image_select_file.setVisibility(View.GONE);
            holder.checkBox.setChecked(fileItem.isCheck());
        }
        return convertView;
    }

    /**
     * 设置某个项选中状态
     */
    public void setItemChecked(ListView view, int position, boolean isChecked){
        FileItemInfo map = _filelist.get(position);
        if(isChecked == map.isCheck()){
            return;
        }
        map.setCheck(isChecked);
        updateListViewPartly(view, isChecked, position);
        if(!_args.IsMultiSelect && isChecked){    //单选模式下只选择1个文件
            for (int i = 0; i < _filelist.size(); i++) {
                if(i != position && _filelist.get(i).isCheck())
                {
                    _filelist.get(i).setCheck(false);
                    updateListViewPartly(view, false, i);
                }
            }
        }
    }

    /**
     * 局部刷新listView，设置某一项状态
     */
    private void updateListViewPartly(ListView listView, boolean isChecked, int position) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View view = listView.getChildAt(position - firstVisiblePosition);
            if (view.getTag() instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) view.getTag();
                vh.checkBox.setChecked(isChecked);
            }
        }
    }

    /**
     * 设置某个文件夹下所有的文件选中状态
     *  true: 全选；false: 反选
     */
    public void setAllItemChecked(ListView listView, boolean isChecked){
        for(int i = 0; i < _filelist.size(); i++){
            if(!_filelist.get(i).getFile().isDirectory()){
                FileItemInfo map = _filelist.get(i);
                if(isChecked == map.isCheck()){
                    continue;
                }
                map.setCheck(isChecked);
                int firstVisiblePosition = listView.getFirstVisiblePosition();
                int lastVisiblePosition = listView.getLastVisiblePosition();
                if (i >= firstVisiblePosition && i <= lastVisiblePosition) {
                    View view = listView.getChildAt(i - firstVisiblePosition);
                    if (view.getTag() instanceof ViewHolder) {
                        ViewHolder vh = (ViewHolder) view.getTag();
                        vh.checkBox.setChecked(isChecked);
                    }
                }
            }
        }
    }

    /**
     * 获取当前选中的文件列表
     */
    public ArrayList<File> getItemChecked(){
        ArrayList<File> ls = new ArrayList<>();
        for (int i = 0; i < _filelist.size(); i++){
            if(_filelist.get(i).isCheck()){
                ls.add(_filelist.get(i).getFile());
            }
        }
        return ls;
    }

    class ViewHolder{
        private LinearLayout layout_item_file;
        private ImageView img;
        private TextView title;
        private TextView info;
        private CheckBox checkBox;
        private ImageView image_select_file;
    }
}
