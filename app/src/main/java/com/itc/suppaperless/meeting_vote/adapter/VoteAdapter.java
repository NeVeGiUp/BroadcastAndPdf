package com.itc.suppaperless.meeting_vote.adapter;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.ui.VoteActivity;

import static com.itc.suppaperless.global.Config.IS_COMPLETE;
import static com.itc.suppaperless.global.Config.LST_VOTE;
import static com.itc.suppaperless.utils.AppUtils.getVoteTotal;
import static com.itc.suppaperless.utils.AppUtils.getVoteTotalPeople;

/**
 * Created by cong on 19-3-7.
 */

public class VoteAdapter extends BaseQuickAdapter<MeetingVoteBean.LstVoteBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     */
    public VoteAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, MeetingVoteBean.LstVoteBean item) {
        helper.setText(R.id.tv_vote_title,item.getStrVoteTitle());
        TextView tvVoteStatusTwo = helper.getView(R.id.tv_vote_status_two);
        TextView tvVoteStatusOne = helper.getView(R.id.tv_vote_status_one);
        TextView tvVoteNum = helper.getView(R.id.tv_vote_num);
        TextView tvVote = helper.getView(R.id.tv_vote);
        ImageView ivMeetingVote = helper.getView(R.id.iv_meeting_vote);
        LinearLayout linCountdown = helper.getView(R.id.lin_countdown);

        tvVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VoteActivity.class);
                String lstVote = new Gson().toJson(item);
                intent.putExtra(LST_VOTE,lstVote);
                if (tvVote.getText().equals(mContext.getString(R.string.vote))){
                    intent.putExtra(IS_COMPLETE,false);
                }else {
                    intent.putExtra(IS_COMPLETE,true);
                }
                mContext.startActivity(intent);
            }
        });

        /**
         * 启用的状态
         * 0 未启用1 启用 2 结束
         */
        switch (item.getIStatus()){
            case 1:
                setEnabledState(tvVoteStatusTwo,tvVoteStatusOne,R.string.in_progress,
                        ivMeetingVote,R.mipmap.hytp_jxz_n,View.VISIBLE,tvVoteNum,R.drawable.bg_green);
                //是否已投票 0 未投票  1 已投票
                if (item.getIIsVote() == 0){
                    setVoteState(tvVoteStatusTwo,tvVote,R.string.not_vote,R.string.vote,R.drawable
                            .bg_vote_green,R.color.white);
                }else {
                    setVoteState(tvVoteStatusTwo,tvVote,R.string.voted,R.string.look_over,R.drawable
                            .bg_stroke_blue,R.color.btn_color_toupiao_text);
                }
                //是否限时投票 0 不限时 1 限时
                if (item.getIIsTimeLimit() == 1 && item.getIIsVote() == 0){
                    linCountdown.setVisibility(View.VISIBLE);
                }else {
                    linCountdown.setVisibility(View.GONE);
                }

                int mVoteTotalPeople = getVoteTotalPeople(item.getLstOption());
                tvVoteNum.setText(mVoteTotalPeople + mContext.getString(R.string.vote_num));
                break;
            case 2:
                setEnabledState(tvVoteStatusTwo,tvVoteStatusOne,R.string.over, ivMeetingVote,R.mipmap.
                        hytp_wks_n,View.GONE,tvVoteNum,R.drawable.bg_vote_gray);

                setVoteState(tvVoteStatusTwo,tvVote,R.string.voted,R.string.look_over,R.drawable
                        .bg_stroke_blue,R.color.btn_color_toupiao_text);

                linCountdown.setVisibility(View.GONE);
                break;
        }
        helper.setText(R.id.tv_vote_title,item.getStrVoteTitle());
        helper.setText(R.id.tv_vote_content,item.getStrIssueName());
    }

    private void setEnabledState(TextView tvVoteStatusTwo, TextView tvVoteStatusOne, int strID,
                                 ImageView ivMeetingVote, int mipmapID, int visibility,
                                 TextView tvVoteNum,int drawableID){
        tvVoteStatusOne.setText(strID);
        tvVoteStatusOne.setBackgroundResource(drawableID);
        ivMeetingVote.setImageResource(mipmapID);
        tvVoteStatusTwo.setVisibility(visibility);
        tvVoteNum.setVisibility(visibility);
    }

    private void setVoteState(TextView tvVoteStatusTwo,TextView tvVote,int strID1,int strID2,int drawableID,int colorID){
        tvVoteStatusTwo.setText(strID1);
        tvVote.setText(strID2);
        tvVote.setBackgroundResource(drawableID);
        tvVote.setTextColor(ContextCompat.getColor(mContext,colorID));
    }
}
