package com.itc.suppaperless.switch_conference.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseRecyclerViewAdapter;
import com.itc.suppaperless.switch_conference.bean.MeetingListBean;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

/**
 * Created by cong on 19-1-15.
 */

public class SwitchConferenceAdapter extends BaseQuickAdapter<MeetingListBean.LstMeetingBean,BaseViewHolder> {
    public SwitchConferenceAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, MeetingListBean.LstMeetingBean item) {
        helper.setText(R.id.tv_meeting_name,item.getStrMeetingName());
        TextView textView=helper.getView(R.id.tv_meeting_status);
        if (item.getIStatus()==0){
            helper.setText(R.id.tv_meeting_status,"未开始");
            textView.setTextColor(mContext.getResources().getColor(R.color.text_gray_deep));
            helper.setBackgroundColor(R.id.layout_meeting_list_bottom, mContext.getResources().getColor((R.color.text_gray_deep)));
        }else {
            helper.setText(R.id.tv_meeting_status,"进行中");
            textView.setTextColor(mContext.getResources().getColor(R.color.color_meeting_green));
            helper.setBackgroundColor(R.id.layout_meeting_list_bottom, mContext.getResources().getColor((R.color.color_meeting_green)));

        }

        helper.setText(R.id.tv_meeting_time,item.getStrStartTime());
        helper.setText(R.id.tv_meeting_position,item.getStrMeetingRoomName());
    }
}
