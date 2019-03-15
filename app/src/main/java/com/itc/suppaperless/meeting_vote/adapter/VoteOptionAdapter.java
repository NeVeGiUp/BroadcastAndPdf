package com.itc.suppaperless.meeting_vote.adapter;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.ui.VoteActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static com.itc.suppaperless.global.Config.LST_VOTE;

/**
 * Created by cong on 19-3-7.
 */

public class VoteOptionAdapter extends BaseQuickAdapter<MeetingVoteBean.LstVoteBean.LstOptionBean,BaseViewHolder> {
    private boolean isComplete;
    private int iCheckbox;
    private int iFinishVoteNum;//已投票的人数

    public VoteOptionAdapter(int layoutResId,int iCheckbox) {
        super(layoutResId);
        this.iCheckbox = iCheckbox;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setiFinishVoteNum(int iFinishVoteNum) {
        this.iFinishVoteNum = iFinishVoteNum;
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetingVoteBean.LstVoteBean.LstOptionBean item) {
        TextView tvOptionName = helper.getView(R.id.tv_option_name);
        CardView cardView = helper.getView(R.id.cardView);
        TextView tvCardViewQuantity = helper.getView(R.id.tv_cardView_quantity);
        tvOptionName.setText(item.getStrOptionName());
        LinearLayout linVoteNum = helper.getView(R.id.lin_vote_num);
        if (isComplete){
            setOptionModel(tvOptionName,cardView,R.color.text_gray_deep,R.color.white);
            linVoteNum.setVisibility(View.VISIBLE);
            tvCardViewQuantity.setVisibility(View.VISIBLE);

            double iNum = item.getINum();//一个选项的数量
            final double percentage;
            if (iFinishVoteNum == 0){
                percentage = 0;
            }else {
                percentage = iNum / iFinishVoteNum;
            }
            //监听cardView控件的高度
            ViewTreeObserver vto = cardView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    cardView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    double height = cardView.getHeight();
                    double tvCardViewQuantityHeight = percentage * height;
                    tvCardViewQuantity.setLayoutParams(new FrameLayout
                            .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)tvCardViewQuantityHeight, Gravity.BOTTOM));
                }
            });

            BigDecimal bg = new BigDecimal(percentage);
            double mPercentage = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            helper.setText(R.id.tv_percentage,(mPercentage * 100) + "%");
            helper.setText(R.id.tv_vote_quantity,"("+item.getINum()+"票)");

            cardView.setEnabled(false);
        }else {
            cardView.setEnabled(true);
            linVoteNum.setVisibility(View.GONE);
            boolean check = item.isCheck();
            if (check){
                setOptionModel(tvOptionName,cardView,R.color.white,R.color.btn_color_toupiao_control);
            }else {
                setOptionModel(tvOptionName,cardView,R.color.text_gray_deep,R.color.white);
            }

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iCheckbox == 0){
                        for (MeetingVoteBean.LstVoteBean.LstOptionBean lstOptionBean: getData()){
                            lstOptionBean.setCheck(false);
                        }
                    }
                    notifyDataSetChanged();

                    if (!check){
                        setOptionModel(tvOptionName,cardView,R.color.white,R.color.btn_color_toupiao_control);
                    }else {
                        setOptionModel(tvOptionName,cardView,R.color.text_gray_deep,R.color.white);
                    }
                    item.setCheck(!check);
                }
            });
        }
    }

    private void setOptionModel(TextView tvOptionName,CardView cardView,int coloID1,int coloID2){
        tvOptionName.setTextColor(ContextCompat.getColor(mContext,coloID1));
        cardView.setCardBackgroundColor(ContextCompat.getColor(mContext,coloID2));
    }
}
