package com.itc.suppaperless.pdfmodule.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.itc.suppaperless.R;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.pdfmodule.adapter.SwitchFileAdapter;
import com.itc.suppaperless.pdfmodule.eventbean.ChangeFileEvent;
import com.itc.suppaperless.utils.ScreenUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Create by zhengwp on 19-2-26.
 */
public class SwitchFilePopupWindow extends PopupWindow{
    private LinearLayout ll_root_view;
    private RelativeLayout rl_parent;
    private ImageView iv_close;
    private RecyclerView rv_switchfile_list;
    private RelativeLayout rl_no_data;

    private Context context;
    private SwitchFileAdapter sfAdapter;
    private List<CommentUploadListInfo.LstFileBean> itemData;

    public SwitchFilePopupWindow(Context context){
        this.context = context;

        intPopup();
    }

    private void intPopup() {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_switch_file, null);
        setContentView(view);

        ll_root_view = view.findViewById(R.id.ll_root_view);
        rl_parent = view.findViewById(R.id.rl_parent);
        iv_close = view.findViewById(R.id.iv_close);
        rl_no_data = view.findViewById(R.id.rl_no_data);
        rv_switchfile_list = view.findViewById(R.id.rv_switchfile_list);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setAnimationStyle(R.style.AnimBottom);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rl_parent.getLayoutParams();
        lp.width = (int) (ScreenUtil.getScreenWidth(context) * 0.8);
        lp.height = (int) (ScreenUtil.getScreenHeight(context) * 0.8);
        rl_parent.setLayoutParams(lp);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        rv_switchfile_list.setLayoutManager(gridLayoutManager);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ll_root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setAdapterData(List<CommentUploadListInfo.LstFileBean> data) {
        //Set the data of file item.
        this.itemData = data;

        if (itemData.size() == 0) {
            rl_no_data.setVisibility(View.VISIBLE);
            return;
        } else {
            rl_no_data.setVisibility(View.GONE);
        }
        if (sfAdapter == null){
            sfAdapter = new SwitchFileAdapter(context, itemData);
            sfAdapter.setAdpterClick(new IAdapterClickListener() {
                @Override
                public void adapterClick(int id, int position) {
                    EventBus.getDefault().post(new ChangeFileEvent(itemData.get(position)));
                    dismiss();
                }
            });
            rv_switchfile_list.setAdapter(sfAdapter);
        }else{
            sfAdapter.setItemData(itemData);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
