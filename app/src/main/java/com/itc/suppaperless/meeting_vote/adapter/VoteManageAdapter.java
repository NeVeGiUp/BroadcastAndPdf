package com.itc.suppaperless.meeting_vote.adapter;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.presenter.VoteManagePresenter;
import com.itc.suppaperless.meeting_vote.ui.VotingManagerDetailActivity;
import com.itc.suppaperless.widget.DialogNewInterface;

import static com.itc.suppaperless.global.Config.LST_VOTE;

/**
 * Created by cong on 19-3-12.
 */

public class VoteManageAdapter extends BaseQuickAdapter<MeetingVoteBean.LstVoteBean,BaseViewHolder> {
    private boolean isHaveOpen = false;
    private VoteManagePresenter mVoteManagePresenter;
    private DialogNewInterface mDialogNewInterface;

    public VoteManageAdapter(int layoutResId, VoteManagePresenter voteManagePresenter, DialogNewInterface mDialogNewInterface) {
        super(layoutResId);
        mVoteManagePresenter = voteManagePresenter;
        this.mDialogNewInterface = mDialogNewInterface;
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
        tvVoteStatusTwo.setVisibility(View.GONE);
        TextView tvVoteStatusOne = helper.getView(R.id.tv_vote_status_one);
        TextView tvVoteNum = helper.getView(R.id.tv_vote_num);
        TextView tvVote = helper.getView(R.id.tv_vote);
        TextView tvVoteEnd = helper.getView(R.id.tv_vote_end);
        ImageView ivMeetingVote = helper.getView(R.id.iv_meeting_vote);

        tvVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvVote.getText().toString().equals(mContext.getString(R.string.open))){
                    if (isHaveOpen){
                        mDialogNewInterface.setText(mContext.getString(R.string.please_end_vote))
                                .setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                                    @Override
                                    public void onClick() {
                                    }
                                }).show();
                    }else {
                        mDialogNewInterface.setText(mContext.getString(R.string.confirm_open_meeting_vote))
                                .setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                                    @Override
                                    public void onClick() {
                                        mVoteManagePresenter.setVotingControl(item.getIVoteID(),1);

                                    }
                                }).show();
                    }
                }else {
                    Intent intent = new Intent(mContext, VotingManagerDetailActivity.class);
                    String lstVote = new Gson().toJson(item);
                    intent.putExtra(LST_VOTE,lstVote);
                    mContext.startActivity(intent);
                }
            }
        });

        tvVoteEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogNewInterface.setText(mContext.getString(R.string.confirm_end_meeting_vote))
                        .setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        isHaveOpen = false;
                        mVoteManagePresenter.setVotingControl(item.getIVoteID(),2);
                    }
                }).show();
            }
        });
        /**
         * 启用的状态
         * 0 未启用 1 启用 2 结束
         */
        switch (item.getIStatus()){
            case 0:
                setVoteState(tvVoteStatusOne,tvVote,R.string.has_not_started,R.string.open,R.drawable
                        .bg_orange,R.drawable.bg_vote_green,R.color.white,tvVoteEnd,View.GONE,
                        ivMeetingVote,R.mipmap.hytp_jxz_n);
                break;
            case 1:
                isHaveOpen = true;
                setVoteState(tvVoteStatusOne,tvVote,R.string.in_progress,R.string.look_over,R.drawable
                        .bg_green,R.drawable.bg_stroke_blue,R.color.btn_color_toupiao_text,tvVoteEnd,
                        View.VISIBLE,ivMeetingVote,R.mipmap.hytp_ddz_n);
                break;
            case 2:
                setVoteState(tvVoteStatusOne,tvVote,R.string.over,R.string.look_over,R.drawable
                        .bg_vote_gray,R.drawable.bg_stroke_blue,R.color.btn_color_toupiao_text,tvVoteEnd,
                        View.GONE,ivMeetingVote,R.mipmap.hytp_wks_n);
                break;
        }
    }

    private void setVoteState(TextView tvVoteStatusOne,TextView tvVote,int strID1,int strID2,int drawableID1,
                              int drawableID2,int colorID,TextView tvVoteEnd,int visibility,ImageView ivMeetingVote,int mipmapID){
        tvVoteStatusOne.setText(strID1);
        tvVoteStatusOne.setBackgroundResource(drawableID1);
        tvVote.setText(strID2);
        tvVote.setBackgroundResource(drawableID2);
        tvVote.setTextColor(ContextCompat.getColor(mContext,colorID));
        tvVoteEnd.setVisibility(visibility);
        ivMeetingVote.setImageResource(mipmapID);
    }
}
