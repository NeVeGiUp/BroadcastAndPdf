package com.itc.suppaperless.meetingmodule.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;


public class TopicManagementAdapter extends BaseQuickAdapter<IssueInfo.LstIssue,BaseViewHolder> {

    public TopicManagementAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IssueInfo.LstIssue item) {
        Log.e("------","-----------item.getStrName():"+item.getStrName());

        helper.setText(R.id.tv_management_topic_item_title,item.getStrName());
        helper.setText(R.id.tv_management_topic_item_report,"汇报:"+item.getStrReporter());
        TextView tvTopicStatus = helper.getView(R.id.tv_management_topic_item_status);
        Button btnStatus = helper.getView(R.id.btn_management_topic);
        Log.e("----","-----item.getiStatus():"+item.getiStatus());
        helper.addOnClickListener(R.id.btn_management_topic);
        Button btnInform = helper.getView(R.id.btn_management_inform);
        switch(item.getiStatus()){
            case "3":
                setTopicStatus(tvTopicStatus,"预备中",R.mipmap.ic_clock,R.color.color_start_vote_font_red);
                setTopicButton(btnStatus,"开始",R.drawable.bg_blue_15radius);
                btnInform.setVisibility(View.GONE);

                break;
            case "2":
                setTopicStatus(tvTopicStatus,"已结束",R.mipmap.icon_yjs,R.color.color_gray);
                setTopicButton(btnStatus,"重新开始",R.drawable.bg_blue_15radius);
                btnInform.setVisibility(View.GONE);

                break;
            case "1":
                setTopicStatus(tvTopicStatus,"进行中",R.mipmap.icon_jxz,R.color.corlor_yiti_green);
                setTopicButton(btnStatus,"结束",R.drawable.bg_orange_15radius);
                btnInform.setVisibility(View.GONE);

                break;
            case "0":
                setTopicStatus(tvTopicStatus,"未开始",R.mipmap.icon_wks,R.color.color_blue_common);
                setTopicButton(btnStatus,"开始",R.drawable.bg_blue_15radius);
                btnInform.setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.btn_management_inform);
                break;

        }

    }
    //设置会议议题状态
    private void setTopicStatus(TextView textView, String status, int id, int color){
        textView.setVisibility(View.VISIBLE);
        textView.setText(status);
        textView.setTextColor(mContext.getResources().getColor(color));
        Drawable drawable = ContextCompat.getDrawable(mContext, id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    //设置操作按钮
    private void setTopicButton(Button btn,String title,int resource){
        btn.setText(title);
        btn.setBackgroundResource(resource);
        btn.setVisibility(View.VISIBLE);
    }
}
