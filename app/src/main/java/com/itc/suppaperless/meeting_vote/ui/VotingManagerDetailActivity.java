package com.itc.suppaperless.meeting_vote.ui;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.meeting_vote.decoration.HorizontalSpacingItemDecoration;
import com.itc.suppaperless.meeting_vote.decoration.VerticalSpacingItemDecoration;
import com.itc.suppaperless.meeting_vote.adapter.VoteDetailGraphicalAdapter;
import com.itc.suppaperless.meeting_vote.adapter.VoteDetailWordAdapter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.VoteUserNameBean;
import com.itc.suppaperless.meeting_vote.contract.VoteManageDetailContract;
import com.itc.suppaperless.meeting_vote.presenter.VoteManageDetailPresenter;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.itc.suppaperless.global.Config.LST_VOTE;
import static com.itc.suppaperless.utils.AppUtils.getVoteTotal;
import static com.itc.suppaperless.utils.AppUtils.getVoteTotalPeople;

public class VotingManagerDetailActivity extends BaseActivity<VoteManageDetailPresenter> implements VoteManageDetailContract
        .VoteManageDetailUI{
    @BindView(R.id.vote_result_exit)
    ImageView voteResultExit;
    @BindView(R.id.vote_result_name)
    TextView voteResultName;
    @BindView(R.id.iv_passing)
    ImageView ivPassing;
    @BindView(R.id.vote_result_has_voted)
    TextView voteResultHasVoted;
    @BindView(R.id.vote_result_not_voted)
    TextView voteResultNotVoted;
    @BindView(R.id.vote_result_total_num)
    TextView voteResultTotalNum;
    @BindView(R.id.vote_result_cast_srceen)
    TextView voteResultCastSrceen;
    @BindView(R.id.vote_result_details)
    TextView voteResultDetails;
    @BindView(R.id.vote_chart_rv)
    RecyclerView voteChartRv;
    @BindView(R.id.fl_vote_result_graphical)
    FrameLayout flVoteResultGraphical;
    @BindView(R.id.vote_details_has_cast)
    TextView voteDetailsHasCast;
    @BindView(R.id.vote_details_has_cast_line)
    View voteDetailsHasCastLine;
    @BindView(R.id.vote_details_not_cast)
    TextView voteDetailsNotCast;
    @BindView(R.id.vote_details_not_cast_line)
    View voteDetailsNotCastLine;
    @BindView(R.id.vote_details_info_rv)
    RecyclerView voteDetailsInfoRv;
    @BindView(R.id.fl_vote_result_word)
    LinearLayout flVoteResultWord;
    @BindView(R.id.lin_not_voting)
    LinearLayout linNotVoting;
    @BindView(R.id.lin_voted)
    LinearLayout linVoted;
    private VoteDetailWordAdapter wordAdapter;
    private VoteDetailGraphicalAdapter graphicalAdapter;
    private List<MeetingVoteBean.LstVoteBean.LstOptionBean> mLstOption;
    private List<VoteUserNameBean> voteUserNameBeanList;
    private List<VoteUserNameBean> notVoteUserNameBeanList;
    private int totalNum;
    private boolean isShowDetails;

    @Override
    public int getLayoutId() {
        return R.layout.activity_voting_manager_detail;
    }

    @Override
    public VoteManageDetailPresenter createPresenter() {
        return new VoteManageDetailPresenter(this);
    }

    @Override
    public void init() {
        voteUserNameBeanList = new ArrayList<>();
        notVoteUserNameBeanList = new ArrayList<>();
        String lst_vote = getIntent().getStringExtra(LST_VOTE);
        MeetingVoteBean.LstVoteBean lstVoteBean = new Gson().fromJson(lst_vote, MeetingVoteBean.LstVoteBean.class);
        mLstOption = lstVoteBean.getLstOption();

        //1 可多选  0 不可多选
        int iCheckbox = lstVoteBean.getICheckbox();
//        if (iCheckbox == 1){
//            tvOptionModel.setText(R.string.multiple_selection);
//        }else {
//            tvOptionModel.setText(R.string.single_selection);
//        }
        //0 不需要  1 需要
//        int iCountdown = lstVoteBean.getICountdown();
//        if (iCountdown == 0){
//            linVoteCountdown.setVisibility(View.GONE);
//        }else {
//            linVoteCountdown.setVisibility(View.VISIBLE);
//        }
        voteResultName.setText(lstVoteBean.getStrVoteTitle());

        totalNum = lstVoteBean.getAiUserID().size();
        voteResultTotalNum.setText(totalNum + "");

        int mVoteTotalPeople = getVoteTotalPeople(mLstOption);
        voteResultNotVoted.setText(totalNum - mVoteTotalPeople + "");
        voteResultHasVoted.setText(mVoteTotalPeople + "");

//        iVoteID = lstVoteBean.getIVoteID();
        //是否备注 0 不需要  1 需要
//        iIsComment = lstVoteBean.getIIsComment();
        VoteManageDetailPresenter voteManageDetailPresenter = getPresenter();
        voteManageDetailPresenter.getUserList();
        //详情Adapter
        wordAdapter = new VoteDetailWordAdapter(R.layout.item_vote_detail_word);
        voteDetailsInfoRv.setLayoutManager(new LinearLayoutManager(this));
        voteDetailsInfoRv.addItemDecoration(new VerticalSpacingItemDecoration(ScreenUtil.dip2px(15)));
        voteDetailsInfoRv.setAdapter(wordAdapter);
        //图表Adapter
        int voteTotal = getVoteTotal(mLstOption);//投票总数
        graphicalAdapter = new VoteDetailGraphicalAdapter(R.layout.item_vote_detail_graphical,voteTotal);
        graphicalAdapter.setNewData(mLstOption);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        voteChartRv.setLayoutManager(linearLayoutManager);
        voteChartRv.addItemDecoration(new HorizontalSpacingItemDecoration(ScreenUtil.dip2px(20)));
        voteChartRv.setAdapter(graphicalAdapter);
    }

    @OnClick({R.id.lin_voted, R.id.lin_not_voting,R.id.vote_result_exit,R.id.vote_result_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_voted:
                setTextChange(voteDetailsHasCast,voteDetailsNotCast,R.color.text_black_common,R.color.
                        text_gray_light,voteDetailsHasCastLine,voteDetailsNotCastLine,View.VISIBLE,
                        View.INVISIBLE,voteUserNameBeanList);
                break;
            case R.id.lin_not_voting:
                setTextChange(voteDetailsHasCast,voteDetailsNotCast,R.color.text_gray_light,R.color.
                        text_black_common,voteDetailsHasCastLine,voteDetailsNotCastLine,View.INVISIBLE,
                        View.VISIBLE,notVoteUserNameBeanList);
                break;
            case R.id.vote_result_exit:
                finish();
                break;
            case R.id.vote_result_details:
                if (isShowDetails) {
                    flVoteResultGraphical.setVisibility(View.VISIBLE);
                    flVoteResultWord.setVisibility(View.GONE);
                    voteResultDetails.setText("详情");
                    voteResultDetails.setBackground(ContextCompat.getDrawable(this, R.mipmap.tpgl_xq_n));
                } else {
                    flVoteResultWord.setVisibility(View.VISIBLE);
                    flVoteResultGraphical.setVisibility(View.GONE);
                    voteResultDetails.setText("图表");
                    voteResultDetails.setBackground(ContextCompat.getDrawable(this, R.mipmap.tpgl_tb_n));
                }
                isShowDetails = !isShowDetails;
                break;
        }
    }

    private void setTextChange(TextView textView1,TextView textView2,int textColor1,int textColor2,View view1,
                    View view2,int visibility1,int visibility2,List<VoteUserNameBean> voteUserNameBeans){
        textView1.setTextColor(ContextCompat.getColor(this,textColor1));
        textView2.setTextColor(ContextCompat.getColor(this,textColor2));
        view1.setVisibility(visibility1);
        view2.setVisibility(visibility2);
        wordAdapter.setNewData(voteUserNameBeans);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void getUserListSuccess(List<JiaoLiuUserInfo.LstUserBean> lstUser) {
        voteUserNameBeanList.clear();
        notVoteUserNameBeanList.clear();
        String voteUserName = "";
        String notVoteUserName = "";
        VoteUserNameBean voteUserNameBean;
        //循环选项
        for (MeetingVoteBean.LstVoteBean.LstOptionBean lstOptionBean : mLstOption){
            voteUserNameBean = new VoteUserNameBean();
            voteUserNameBean.setOption(lstOptionBean.getStrOptionName());
            List<Integer> aiVotedUserID = lstOptionBean.getAiVotedUserID();
            //循环用户列表
            for (JiaoLiuUserInfo.LstUserBean lstUserBean : lstUser){
                //循环投票用户列表
                for (int mInteger: aiVotedUserID){
                    //判断该用户是否投了票
                    if (lstUserBean.getIUserID() == mInteger){
                        voteUserName += lstUserBean.getStrUserName() + "、";
                    }else {
                        String strUserName = lstUserBean.getStrUserName();
                        if (notVoteUserName.indexOf(strUserName) == -1){
                            notVoteUserName += strUserName + "、";
                        }
                    }
                }
            }

            if (voteUserName.isEmpty()){
                voteUserNameBean.setUserName("无");
            }else {
                voteUserNameBean.setUserName(voteUserName.substring(0,voteUserName.length()-1));
            }
            voteUserNameBeanList.add(voteUserNameBean);

            voteUserName = "";
        }
        //未投票的用户
        voteUserNameBean = new VoteUserNameBean();
        voteUserNameBean.setOption("未投");
        if (notVoteUserName.isEmpty()){
            voteUserNameBean.setUserName("无");
        }else {
            voteUserNameBean.setUserName(notVoteUserName.substring(0,notVoteUserName.length()-1));
        }
        notVoteUserNameBeanList.add(voteUserNameBean);

        wordAdapter.setNewData(voteUserNameBeanList);
    }
}
