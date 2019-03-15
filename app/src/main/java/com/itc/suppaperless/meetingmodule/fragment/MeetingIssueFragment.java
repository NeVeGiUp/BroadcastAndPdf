package com.itc.suppaperless.meetingmodule.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.YitiAdapter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;
import com.itc.suppaperless.meetingmodule.eventbean.DeleteOrModifyEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadFailEvent;
import com.itc.suppaperless.meetingmodule.eventbean.ProgressEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.YitiContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.YitiPresenter;
import com.itc.suppaperless.meetingmodule.ui.MeetingIssueDetailActivity;
import com.itc.suppaperless.utils.DownloadFileUtil;
import com.itc.suppaperless.utils.FileDaoUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.lzy.okgo.model.Progress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 会议议题
 */
public class MeetingIssueFragment extends BaseFragment implements YitiContract.View {


    @BindView(R.id.rl_yiti)
    RecyclerView rlYiti;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    private YitiAdapter yitiAdapter;
    private List<IssueInfo.LstIssue> lsIssues = new ArrayList<>(); //议题数据list
    private List<CommentUploadListInfo.LstFileBean> jsonFile = new ArrayList<>();
    private int number = 0;//统计文件数量

    @Override
    public int getLayoutId() {
        return R.layout.fragment_yiti;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new YitiPresenter(this);
    }


    @Override
    public void init() {
        getPresenter();
        EventBus.getDefault().register(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlYiti.setLayoutManager(linearLayoutManager);
        yitiAdapter = new YitiAdapter(R.layout.item_yiti);
        rlYiti.setAdapter(yitiAdapter);
        yitiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IssueInfo.LstIssue issue = ((YitiAdapter) adapter).getData().get(position);
                List<CommentUploadListInfo.LstFileBean> jsonFiles = new ArrayList<>();
                for (int i = 0; i < jsonFile.size(); i++) {
                    if (jsonFile.get(i).getiModuleID() == issue.getiIssueId()) {
                        jsonFiles.add(jsonFile.get(i));
                    }
                }
                if (AppDataCache.getInstance().getInt(Config.CHAIRMAN)==0&&AppDataCache.getInstance().getInt(Config.SECRETARY)==0){
                    if (issue.getiStartDatum()==1&&!issue.getiStatus().equals("1")){//保密设置，不是主席不是秘书根据状态查看
                        return;
                    }
                }
                Intent intent = new Intent(mContext, MeetingIssueDetailActivity.class);
                Gson gson = new Gson();
                intent.putExtra("issues", gson.toJson(issue));
                intent.putExtra("jsonFiles", gson.toJson(jsonFiles));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //处理下发议题列表
    @Override
    public void setIssue(List<IssueInfo.LstIssue> lstIssue) {
        this.lsIssues = lstIssue;
        if (lsIssues != null && lsIssues.size() == 0) {
            rlNoData.setVisibility(View.VISIBLE);
        } else {
            rlNoData.setVisibility(View.GONE);
        }
        //删除重复的数据
        for (int i = 0; i < lsIssues.size(); i++) {
            for (int j = 0; j < lsIssues.size(); j++) {
                if (i != j) {
                    if (lsIssues.get(i).getiIssueId() == lsIssues.get(j).getiIssueId()) {
                        lsIssues.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        setnum();
        if (lsIssues != null && lsIssues.size() != 0)
            yitiAdapter.setNewData(lsIssues);
    }

    //设置每个议题的文件个数
    private void setnum() {
        if (jsonFile.size() != 0 && lsIssues != null && lsIssues.size() != 0) {
            for (int i = 0; i < lsIssues.size(); i++) {
                number = 0;
                for (int j = 0; j < jsonFile.size(); j++) {
                    if (jsonFile.get(j).getiIsDir() == 0) {
                        if (lsIssues.get(i).getiIssueId() == jsonFile.get(j).getiModuleID()) {
                            number++;
                        }
                    }

                }
                lsIssues.get(i).setFilenum(number);
            }
            yitiAdapter.notifyDataSetChanged();
        }
    }

    //处理241下发的文件
    @Override
    public void setcurrentFil(List<CommentUploadListInfo.LstFileBean> lstfiles) {
        for (int i = 0; i < lstfiles.size(); i++) {
            if (lstfiles.get(i).getiType() == 2) {
                switch (lstfiles.get(i).getiUpdateType()) {
                    case 1://增加文件xgfxx032gxxffdfghdhxgf
                        jsonFile.add(lstfiles.get(i));
                        if (lstfiles.get(i).getiIsDir() == 0) {
                            if (!FileDaoUtil.getIsDown(lstfiles.get(i).getiID())) {//判断添加文件是否已经下载过
                                String ip= AppDataCache.getInstance().getString(Config.IP_MEETING);
                                int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
                                lstfiles.get(i).setMeetingID(AppDataCache.getInstance().getInt(Config.MEETING_ID));
                                lstfiles.get(i).setIpAndPort(StringUtil.strSplit(ip)+port);
                                //文件下载
                                DownloadFileUtil.getInstance().setDownFile(lstfiles.get(i).getStrPath(),lstfiles.get(i).getiID());
                            } else {
                                jsonFile.get(jsonFile.size() - 1).setIsDown(2);
                            }
                        }
                        break;
                    case 2://编辑文件
                        EventBus.getDefault().post(new DeleteOrModifyEvent(lstfiles.get(i).getiID()));
                        for (int k = (jsonFile.size() - 1); k >= 0; k--) {  //设计如此：重复数据下后面的数据才是最新的，所以修改后面，下一版本重写
                            if (lstfiles.get(i).getiID() == jsonFile.get(k).getiID()) {
                                lstfiles.get(i).setIsDown(jsonFile.get(k).getIsDown());
                                jsonFile.set(k, lstfiles.get(i));
                                break;
                            }
                        }
                        break;
                    case 3://删除文件
                        EventBus.getDefault().post(new DeleteOrModifyEvent(lstfiles.get(i).getiID()));
                        for (int k = 0; k < jsonFile.size(); k++) {
                            if (lstfiles.get(i).getiID() == jsonFile.get(k).getiID()) {
                                FileDaoUtil.deleteDownFile(jsonFile.get(k).getiID()); //删除保存的文件数据
                                jsonFile.remove(k);
                                k--;
                            }
                        }
                        break;
                }
            }
        }
        //删除jsonFile可能存在重复的数据
        for (int i = 0; i < jsonFile.size(); i++) {
            for (int j = 0; j < jsonFile.size(); j++) {
                if (i != j) {
                    if (jsonFile.get(i).getiID() == jsonFile.get(j).getiID()) {
                        jsonFile.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        //设置每个议题的文件个数
        setnum();

    }

    //处理议题更新
    @Override
    public void setIssueUpdate(IssueInfo.LstIssue issue) {

        switch (issue.getiUpdateType()) {
            case 1://添加议题
                lsIssues.add(issue);
                setnum();
                break;
            case 2://修改议题
                for (int i = 0; i < lsIssues.size(); i++) {
                    if (issue.getiIssueId() == lsIssues.get(i).getiIssueId()) {
                        lsIssues.set(i, issue);
                    }
                }

                break;
            case 3://删除议题
                for (int i = 0; i < lsIssues.size(); i++) {
                    if (issue.getiIssueId() == lsIssues.get(i).getiIssueId()) {
                        lsIssues.remove(i);
                        break;
                    }
                }

                break;

        }
        if (lsIssues.size()!=0){
            rlNoData.setVisibility(View.GONE);
        }else {
            rlNoData.setVisibility(View.VISIBLE);
        }
        yitiAdapter.setNewData(lsIssues);
//        yitiAdapter.notifyDataSetChanged();
    }

    //处理议题状态更新
    @Override
    public void setIssueState(YitichangeInfo yitichangeInfo) {
        for (int i = 0; i < lsIssues.size(); i++) {
            if (yitichangeInfo.getiIssueID() == lsIssues.get(i).getiIssueId()) {
                lsIssues.get(i).setiStatus(yitichangeInfo.getiStatus());
                yitiAdapter.notifyDataSetChanged();
            }
        }
    }

    //设置下载进度
    private void setProress(Progress progress) {
        for (int i = 0; i < jsonFile.size(); i++) {
            if (jsonFile.get(i).getiID()==Integer.parseInt(progress.tag)){
                if (jsonFile.get(i).getIsDown() != 2) {
                    if (jsonFile.get(i).getiID()==Integer.parseInt(progress.tag)) {
                        jsonFile.get(i).setIsDown(1);
                        jsonFile.get(i).setCurrentprogress(progress.currentSize);
                        jsonFile.get(i).setTotelprogress(progress.totalSize);
                    }
                }
            }
        }

    }

    //下载完成
    private void setLoadFinish(Progress progress) {
        for (int i = 0; i < jsonFile.size(); i++) {
            if (jsonFile.get(i).getiID()==Integer.parseInt(progress.tag)){
                if (jsonFile.get(i).getIsDown() != 2) {
                    if (jsonFile.get(i).getiID()==(Integer.parseInt(progress.tag))) {
                        jsonFile.get(i).setIsDown(2);
                        FileDaoUtil.insertFile(jsonFile.get(i).getiID(), jsonFile.get(i).getStrPath(), jsonFile.get(i).getiType(),new Gson().toJson(jsonFile.get(i))); //文件数据添加到数据库
                        FileDaoUtil.setIsDown(Integer.parseInt(progress.tag));
                    }
                }
            }
        }
    }

    //下载失败
    private void setLoadFail(String obj) {
        for (int i = 0; i < jsonFile.size(); i++) {
            if (jsonFile.get(i).getiID()==Integer.parseInt(obj)){
                if (jsonFile.get(i).getiID()==(Integer.parseInt(obj))) {
                    jsonFile.get(i).setIsDown(3);
                }
            }
        }
    }

    //下载进度通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final ProgressEvent event) {
        setProress(event.getData());
    }

    //文件下载失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DownloadFailEvent event) {
        setLoadFail(event.getData());

    }

    //下载完成通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DownloadEvent event) {
        setLoadFinish(event.getData());
    }
}
