package com.itc.suppaperless.meetingmodule.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.adapter.TopicManagementAdapter;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.TopicStatus;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;
import com.itc.suppaperless.meetingmodule.mvp.contract.TopicManagementContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.TopicManagementPresenter;
import com.itc.suppaperless.widget.DialogNewInterface;
import com.itc.suppaperless.widget.IDialogListener;
import com.itc.suppaperless.widget.PrepareNoticeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 议题管理Fragment
 */
public class TopicManagementFragment extends BaseFragment<TopicManagementPresenter> implements TopicManagementContract.View{


    @BindView(R.id.rl_management_topic)
    RecyclerView rlTopicList;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    private PrepareNoticeDialog prepareNoticeDialog;

    private TopicManagementAdapter topicListAdapter;
    private List<IssueInfo.LstIssue> lsTopics = new ArrayList<>(); //议题数据list
    @Override
    public int getLayoutId() {
        return R.layout.fragment_management_topic;
    }

    @Override
    public TopicManagementPresenter createPresenter() {
        return new TopicManagementPresenter(this);
    }


    @Override
    public void init() {
        getPresenter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlTopicList.setLayoutManager(linearLayoutManager);
        topicListAdapter = new TopicManagementAdapter(R.layout.item_management_topic);

        rlTopicList.setAdapter(topicListAdapter);
        topicListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.btn_management_topic:
                        String status = lsTopics.get(position).getiStatus();
                        if (lsTopics.get(position).getiStatus().equals("0") || lsTopics.get(position).getiStatus().equals("3")) {
                            new DialogNewInterface(getActivity()).setText("确认启用选中的会议议题吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                                @Override
                                public void onClick() {
                                    getPresenter().setTopicStart(lsTopics.get(position).getiIssueId());
                                }
                            }).show();
                        } else if (lsTopics.get(position).getiStatus().equals("1")) {
                            new DialogNewInterface(getActivity()).setText("确认结束选中的会议议题吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                                @Override
                                public void onClick() {
                                    getPresenter().setTopicStop(lsTopics.get(position).getiIssueId());
//                                    NetSession.getInstance().IssueControl(lsTopics.get(position).getIssueId(), 2);
                                }
                            }).show();
                        } else if (lsTopics.get(position).getiStatus().equals("2")) {  //重启会议
                            new DialogNewInterface(getActivity()).setText("确认重新开始选中的会议议题吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                                @Override
                                public void onClick() {
                                    getPresenter().setTopicRestart(lsTopics.get(position).getiIssueId());
//                                    NetSession.getInstance().IssueControl(lsTopics.get(position).getIssueId(), 1);
                                }
                            }).show();
                        }
                        break;
                    case R.id.btn_management_inform:
                        if (prepareNoticeDialog == null) {
                            prepareNoticeDialog = new PrepareNoticeDialog(mContext, new IDialogListener() {
                                @Override
                                public void dialogClick(int id, int issueId, int time) {
                                    getPresenter().sendTopicInform(issueId, time);
                                }
                            });
                        }
                        prepareNoticeDialog.setIssueId(lsTopics.get(position).getIssueId());
                        prepareNoticeDialog.show();
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //处理下发议题列表
    @Override
    public void setTopic(List<IssueInfo.LstIssue> lstIssue) {
        this.lsTopics = lstIssue;
        setNoDataView(lsTopics);
        //删除重复的数据
        for (int i = 0; i < lsTopics.size(); i++) {
            for (int j = 0; j < lsTopics.size(); j++) {
                if (i != j) {
                    if (lsTopics.get(i).getiIssueId() == lsTopics.get(j).getiIssueId()) {
                        lsTopics.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        if (lsTopics != null && lsTopics.size() != 0)
            topicListAdapter.setNewData(lsTopics);
    }



    //处理议题更新
    @Override
    public void setTopicUpdate(IssueInfo.LstIssue issue) {

        switch (issue.getiUpdateType()) {
            case 1://添加议题
                lsTopics.add(issue);
                break;
            case 2://修改议题
                for (int i = 0; i < lsTopics.size(); i++) {
                    if (issue.getiIssueId() == lsTopics.get(i).getiIssueId()) {
                        lsTopics.set(i, issue);
                    }
                }

                break;
            case 3://删除议题
                for (int i = 0; i < lsTopics.size(); i++) {
                    if (issue.getiIssueId() == lsTopics.get(i).getiIssueId()) {
                        lsTopics.remove(i);
                        break;
                    }
                }

                break;

        }
        setNoDataView(lsTopics);
        topicListAdapter.setNewData(lsTopics);

    }

    //处理议题状态更新
    @Override
    public void setTopicStatus(YitichangeInfo yitichangeInfo) {
        for (int i = 0; i < lsTopics.size(); i++) {
            if (yitichangeInfo.getiIssueID() == lsTopics.get(i).getiIssueId()) {
                lsTopics.get(i).setiStatus(yitichangeInfo.getiStatus());
                topicListAdapter.notifyDataSetChanged();
            }
        }
    }



private void setNoDataView(List list){
    if (list.size() == 0) {
        rlNoData.setVisibility(View.VISIBLE);
    } else {
        rlNoData.setVisibility(View.GONE);
    }
}

}
