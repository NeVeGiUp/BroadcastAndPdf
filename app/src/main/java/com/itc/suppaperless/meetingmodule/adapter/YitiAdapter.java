package com.itc.suppaperless.meetingmodule.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;


/**
 * Created by xiaogf on 19-1-17.
 */

public class YitiAdapter extends BaseQuickAdapter<IssueInfo.LstIssue,BaseViewHolder> {

    public YitiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IssueInfo.LstIssue item) {
        helper.setText(R.id.yiti_group_name,item.getStrName());
        helper.setText(R.id.tv_yiti_report_person,"汇报:"+item.getStrReporter());
        helper.setText(R.id.tv_yiti_look_material,"查看材料 ("+item.getFilenum()+") >");
        TextView tv_yiti_status=helper.getView(R.id.tv_yiti_status);
        if (item.getiStatus().equals("2")){
            setYitistatus(tv_yiti_status,"已结束",R.mipmap.icon_yjs,R.color.color_gray);
        }else if (item.getiStatus().equals("1")){
            setYitistatus(tv_yiti_status,"进行中",R.mipmap.icon_jxz,R.color.corlor_yiti_green);
        }else if (item.getiStatus().equals("0")){
            setYitistatus(tv_yiti_status,"未开始",R.mipmap.icon_wks,R.color.color_blue_common);
        }else {
            setYitistatus(tv_yiti_status,"准备中",R.mipmap.ic_clock,R.color.color_start_vote_font_red);
        }

    }
    //设置会议议题状态
    private void setYitistatus(TextView textView,String status,int id,int color){
        textView.setVisibility(View.VISIBLE);
        textView.setText(status);
        textView.setTextColor(mContext.getResources().getColor(color));
        Drawable drawable = ContextCompat.getDrawable(mContext, id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }
}
