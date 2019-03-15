package com.itc.suppaperless.meeting_vote.ui;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.meeting_vote.decoration.GridSpacingItemDecoration;
import com.itc.suppaperless.meeting_vote.adapter.VoteOptionAdapter;
import com.itc.suppaperless.meeting_vote.bean.MeetingVoteBean;
import com.itc.suppaperless.meeting_vote.bean.UserVoteBean;
import com.itc.suppaperless.meeting_vote.contract.UserVoteContract;
import com.itc.suppaperless.meeting_vote.presenter.UserVotePresenter;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.carbs.android.expandabletextview.library.ExpandableTextView;

import static com.itc.suppaperless.global.Config.IS_COMPLETE;
import static com.itc.suppaperless.global.Config.LST_VOTE;
import static com.itc.suppaperless.global.Config.USER_ID;
import static com.itc.suppaperless.utils.AppUtils.getVoteTotal;
import static com.itc.suppaperless.utils.AppUtils.getVoteTotalPeople;

public class VoteActivity extends BaseActivity implements UserVoteContract.UserVoteUI {

    @BindView(R.id.tv_vote_title)
    TextView tvVoteTitle;
    @BindView(R.id.tv_vote_content)
    ExpandableTextView tvVoteContent;
    @BindView(R.id.tv_option)
    TextView tvOption;
    @BindView(R.id.tv_option_model)
    TextView tvOptionModel;
    @BindView(R.id.tv_countdown_time)
    TextView tvCountdownTime;
    @BindView(R.id.rv_option)
    RecyclerView rvOption;
    @BindView(R.id.tv_total_people)
    TextView tvTotalPeople;
    @BindView(R.id.tv_voted_num)
    TextView tvVotedNum;
    @BindView(R.id.tv_not_vote_num)
    TextView tvNotVoteNum;
    @BindView(R.id.tv_vote_sure)
    TextView tvVoteSure;
    @BindView(R.id.tv_vote_back)
    TextView tvVoteBack;
    @BindView(R.id.tv_vote_finish)
    TextView tvVoteFinish;
    @BindView(R.id.tv_vote_remark)
    TextView tvVoteRemark;
    @BindView(R.id.lin_vote_countdown)
    LinearLayout linVoteCountdown;
    @BindView(R.id.cl_vote_main)
    ConstraintLayout clVoteMain;

    private VoteOptionAdapter mVoteOptionAdapter;
    private UserVotePresenter mUserVotePresenter;
    private int iVoteID,iIsComment,totalNum;
    private VoteRemarkPopupWindow voteRemarkPopupWindow;
    private List<MeetingVoteBean.LstVoteBean.LstOptionBean> mLstOption;
    private boolean isComplete;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vote;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new UserVotePresenter(this);
    }

    @Override
    public void init() {
        String lst_vote = getIntent().getStringExtra(LST_VOTE);
        isComplete = getIntent().getBooleanExtra(IS_COMPLETE,false);
        MeetingVoteBean.LstVoteBean lstVoteBean = new Gson().fromJson(lst_vote, MeetingVoteBean.LstVoteBean.class);
        mLstOption = lstVoteBean.getLstOption();

        tvVoteTitle.setText(lstVoteBean.getStrVoteTitle());
        tvVoteContent.setText(lstVoteBean.getStrVoteName());
        //1 可多选  0 不可多选
        int iCheckbox = lstVoteBean.getICheckbox();
        if (iCheckbox == 1){
            tvOptionModel.setText(R.string.multiple_selection);
        }else {
            tvOptionModel.setText(R.string.single_selection);
        }
        //0 不需要  1 需要
        int iCountdown = lstVoteBean.getICountdown();
        if (iCountdown == 0){
            linVoteCountdown.setVisibility(View.GONE);
        }else {
            linVoteCountdown.setVisibility(View.VISIBLE);
        }

        totalNum = lstVoteBean.getAiUserID().size();
        tvTotalPeople.setText(getString(R.string.all_person_vote1) + totalNum + getString(R.string.all_person_vote2));

        int mVoteTotalPeople = getVoteTotalPeople(mLstOption);
        tvNotVoteNum.setText(totalNum - mVoteTotalPeople + "");
        tvVotedNum.setText(mVoteTotalPeople + "");

        iVoteID = lstVoteBean.getIVoteID();
        //是否备注 0 不需要  1 需要
        iIsComment = lstVoteBean.getIIsComment();

        mVoteOptionAdapter = new VoteOptionAdapter(R.layout.item_option,iCheckbox);
        mVoteOptionAdapter.setiFinishVoteNum(getVoteTotal(mLstOption));
        mVoteOptionAdapter.setComplete(isComplete);
        mVoteOptionAdapter.setNewData(mLstOption);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rvOption.setLayoutManager(gridLayoutManager);
        rvOption.addItemDecoration(new GridSpacingItemDecoration(ScreenUtil.dip2px(10)));
        rvOption.setAdapter(mVoteOptionAdapter);

        mUserVotePresenter = (UserVotePresenter) getPresenter();
    }

    @OnClick({R.id.tv_vote_sure, R.id.tv_vote_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vote_sure:
                if (isComplete){
                    finish();
                }else {
                    List<Integer> integers = new ArrayList<>();
                    for (MeetingVoteBean.LstVoteBean.LstOptionBean mLstOptionBean:mVoteOptionAdapter.getData()){
                        if (mLstOptionBean.isCheck()){
                            integers.add(mLstOptionBean.getIOptionID());
                        }
                    }

                    if (integers.size() == 0){
                        ToastUtil.show(this,R.string.please_select_voting_options);
                        return;
                    }
                    //初始化需要得到的数组
                    int[] array = new int[integers.size()];
                    //使用for循环得到数组
                    for(int i = 0; i < integers.size();i++){
                        array[i] = integers.get(i);
                    }

                    if (iIsComment == 1){
                        if (voteRemarkPopupWindow == null) voteRemarkPopupWindow = new VoteRemarkPopupWindow(this);
                        voteRemarkPopupWindow.showAtLocation(clVoteMain, Gravity.CENTER,0,0);
                        return;
                    }
                    mUserVotePresenter.userVote(AppDataCache.getInstance().getInt(USER_ID),iVoteID,"",array);
                }
                break;
            case R.id.tv_vote_back:
                finish();
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void userVoteFinish(UserVoteBean mUserVoteBean) {
        linVoteCountdown.setVisibility(View.GONE);
        tvVoteFinish.setVisibility(View.VISIBLE);
        tvVoteBack.setVisibility(View.GONE);

        List<MeetingVoteBean.LstVoteBean.LstOptionBean> lstOption = mUserVoteBean.getLstOption();
        int mVoteTotalPeople = getVoteTotalPeople(lstOption);
        tvVotedNum.setText(mVoteTotalPeople + "");
        tvNotVoteNum.setText(totalNum - mVoteTotalPeople + "");
        if (iIsComment == 0){
            tvVoteRemark.setVisibility(View.GONE);
        }else {
            tvVoteRemark.setVisibility(View.VISIBLE);
        }

        for (int i = 0;i < lstOption.size();i++){
            MeetingVoteBean.LstVoteBean.LstOptionBean mLstOptionBean = lstOption.get(i);
            mLstOptionBean.setStrOptionName(mLstOption.get(i).getStrOptionName());
        }

        mVoteOptionAdapter.setComplete(true);
        isComplete = true;
        mVoteOptionAdapter.setiFinishVoteNum(getVoteTotal(lstOption));
        mVoteOptionAdapter.setNewData(lstOption);
    }

    @Override
    public void getVotingControl() {
        finish();
    }
}
