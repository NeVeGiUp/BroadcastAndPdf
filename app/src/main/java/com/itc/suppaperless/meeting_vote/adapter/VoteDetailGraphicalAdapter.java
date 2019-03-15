package com.itc.suppaperless.meeting_vote.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;

/**
 * Created by xiaogf on 19-3-9.
 */

public class VoteDetailGraphicalAdapter extends BaseQuickAdapter<MeetingVoteBean.LstVoteBean.LstOptionBean,BaseViewHolder>{
    private int voteTotal;

    public VoteDetailGraphicalAdapter(int layoutResId,int voteTotal) {
        super(layoutResId);
        this.voteTotal = voteTotal;
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetingVoteBean.LstVoteBean.LstOptionBean item) {
        helper.setText(R.id.tv_vote_number,item.getINum()+"");
        helper.setText(R.id.tv_option_name,item.getStrOptionName());
        LinearLayout linProgress = helper.getView(R.id.lin_progress);
        View viewProgress = helper.getView(R.id.view_progress);

        double iNum = item.getINum();//一个选项的数量
        final double percentage;
        if (voteTotal == 0){
            percentage = 0;
        }else {
            percentage = iNum / voteTotal;
        }

        //监听cardView控件的高度
        ViewTreeObserver vto = viewProgress.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewProgress.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                double height = linProgress.getHeight();
                double tvCardViewQuantityHeight = percentage * height;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) tvCardViewQuantityHeight);
                viewProgress.setLayoutParams(layoutParams);
            }
        });
    }
}
