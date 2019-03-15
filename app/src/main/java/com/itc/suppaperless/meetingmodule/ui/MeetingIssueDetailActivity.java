package com.itc.suppaperless.meetingmodule.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.YitiDetailAdapter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadFailEvent;
import com.itc.suppaperless.meetingmodule.eventbean.ProgressEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.YitiDetailContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.YitiDetailPresenter;
import com.itc.suppaperless.switch_conference.widget.RightNavigationPopView;
import com.itc.suppaperless.utils.ActivityManageUtil;
import com.itc.suppaperless.utils.DownloadFileUtil;
import com.itc.suppaperless.utils.FileDaoUtil;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.lzy.okgo.model.Progress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.OnClick;
import cn.carbs.android.expandabletextview.library.ExpandableTextView;

/**
 * 议题详情
 */
public class MeetingIssueDetailActivity extends BaseActivity implements YitiDetailContract.View{
    @BindView(R.id.rl_parentView)
    RelativeLayout rl_parentView;
    @BindView(R.id.iv_yiti_back)
    ImageView iv_yiti_back;
    @BindView(R.id.tv_yiti_name)
    TextView tv_yiti_name;
    @BindView(R.id.tv_yiti_status)
    TextView tv_yiti_status;
    @BindView(R.id.tv_yiti_report)
    TextView tv_yiti_report;
    @BindView(R.id.tv_yiti_attendee)
    ExpandableTextView tv_yiti_attendee;
    @BindView(R.id.lv_yiti_material)
    RecyclerView lv_yiti_material;
    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;
    @BindView(R.id.iv_navigation)
    ImageView iv_navigation;

    private YitiDetailAdapter adapter;
    private IssueInfo.LstIssue issue;
    private List<CommentUploadListInfo.LstFileBean> jsonFile = new ArrayList<>();
    private List<CommentUploadListInfo.LstFileBean> lsFile = new ArrayList<>(); //adapter显示使用的数组
    private int currentDirId = 0;       //当前显示的父文件夹id
    private Stack<Integer> parentDirIdStack = new Stack<>();//存放父目录文件夹id的栈
    private Gson gson;
    private RightNavigationPopView mRightNavigationPopView;
    private MediaProjectionManager mMediaProjectionManager;//录屏
    private YitiDetailPresenter yitiDetailPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yiti_detail;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new YitiDetailPresenter(this);
    }


    @Override
    public void init() {
        yitiDetailPresenter = (YitiDetailPresenter) getPresenter();
        ActivityManageUtil.insertActivity(Config.ActivityManage.YitiDetailActivity,this);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        gson = new Gson();
        ////////////   初始化屏幕广播相应控件   ////////////
        mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
        mRightNavigationPopView = new RightNavigationPopView(MeetingIssueDetailActivity.this, mMediaProjectionManager,getWindow().getDecorView());

        issue = gson.fromJson(intent.getStringExtra("issues"), IssueInfo.LstIssue.class);
        jsonFile=gson.fromJson(intent.getStringExtra("jsonFiles"),new TypeToken<List<CommentUploadListInfo.LstFileBean>>() {}.getType());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_yiti_material.setLayoutManager(linearLayoutManager);
        adapter = new YitiDetailAdapter(R.layout.item_yiti_detail);
        lv_yiti_material.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommentUploadListInfo.LstFileBean bean = ((YitiDetailAdapter) adapter).getData().get(position);
                //点击的是文件夹
                if (bean.getiIsDir() == 1) {
                    parentDirIdStack.push(bean.getiID());
                    currentDirId = bean.getiID(); //点击的文件夹的id
                    resetRecyView(currentDirId);

                } else {//是文件
                    if (bean.getIsDown() == 2) {//判断是否下载完
                        String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
                        int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
                        File file = new File(Config.downloadpath +  StringUtil.strSplit(ip)+port+"/"+AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/"+ bean.getiID());
                        String[] filelist = file.list();
                        if (filelist==null||filelist.length <= 0) {  //文件夹下文件不存在，重新下载
                            bean.setIsDown(0);
                            DownloadFileUtil.getInstance().setDownFile(bean.getStrPath(),bean.getiID());

                        } else {  //文件夹下文件存在，打开该文件
                            FileOpenUtil.OpenFile(MeetingIssueDetailActivity.this, bean.getiID(), bean.getStrName(), filelist[0], bean.getStrPath(), 0);
                        }
                    }
                }
            }
        });
        parentDirIdStack.push(0);
        initData(issue);
        resetRecyView(currentDirId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 101:  //屏幕广播申请
                if (resultCode == RESULT_OK){
                    yitiDetailPresenter.applicationScreenBroadcast(0,1, AppDataCache.getInstance().getInt(Config.USER_ID));
                    MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
                    yitiDetailPresenter.startRecorder(mediaProjection);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initData(IssueInfo.LstIssue issues) {
        tv_yiti_name.setText(issues.getStrName());
        if (issues.getiStatus().equals("2")) {
            setYitistatus("已结束",R.mipmap.icon_yjs,R.color.color_gray);
        } else if (issues.getiStatus().equals("1")){
            setYitistatus("进行中",R.mipmap.icon_jxz,R.color.corlor_yiti_green);
        }else if (issues.getiStatus().equals("1")){
            setYitistatus("未开始",R.mipmap.icon_wks,R.color.color_blue_common);
        }else {
            setYitistatus("准备中",R.mipmap.ic_clock,R.color.color_start_vote_font_red);
        }
        tv_yiti_report.setText(issues.getStrReporter());
        tv_yiti_attendee.setText(mergeAttendee(issues));

    }
    //设置会议议题状态
    private void setYitistatus(String status,int id,int color){
        tv_yiti_status.setVisibility(View.VISIBLE);
        tv_yiti_status.setText(status);
        tv_yiti_status.setTextColor(this.getResources().getColor(color));
        Drawable drawable = ContextCompat.getDrawable(this, id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_yiti_status.setCompoundDrawables(drawable, null, null, null);
    }

    //合并列席人员
    private String mergeAttendee(IssueInfo.LstIssue issue) {
        if (gson == null) {
            gson = new Gson();
        }
        String userlistJson = AppDataCache.getInstance().getString(Config.SAVE_USER);
        JiaoLiuUserInfo userList = gson.fromJson(userlistJson, JiaoLiuUserInfo.class);
        String attendeeArr = "";
        for (int i = 0; i < issue.getAiUserID().length; i++) {
            for (JiaoLiuUserInfo.LstUserBean user : userList.getLstUser()) {
                if (issue.getAiUserID()[i] == user.getIUserID()) {
                    if (i == issue.getAiUserID().length - 1)
                        attendeeArr += user.getStrUserName();
                    else
                        attendeeArr += user.getStrUserName() + "、";
                }
            }
        }
        return attendeeArr;
    }


    //重新设置RecycleView的数据,stack重置
    protected void resetRecyView(int currentDirId) {
        lsFile.clear();
        // 值为0代表进入的是首页目录
        if (currentDirId == 0) {
            parentDirIdStack.clear();
            parentDirIdStack.push(0);
        }

        for (int i = 0; i < jsonFile.size(); i++) {
            jsonFile.get(i).setiIsShowProgress(false);
            if (jsonFile.get(i).getiParentDirID() == currentDirId) {
                if (jsonFile.get(i).getiIsDir() == 0) {
                    jsonFile.get(i).setiIsShowProgress(true);
                }
                lsFile.add(jsonFile.get(i));
            }
        }
        initadapter(lsFile);
    }

    //设置adapter数据
    private void initadapter(List<CommentUploadListInfo.LstFileBean> lsFile) {
        if (lsFile.size() == 0) {
            rl_no_data.setVisibility(View.VISIBLE);
        } else {
            rl_no_data.setVisibility(View.GONE);
        }
        Collections.sort(lsFile, new Comparator<CommentUploadListInfo.LstFileBean>() {
            @Override
            public int compare(CommentUploadListInfo.LstFileBean o1, CommentUploadListInfo.LstFileBean o2) {
                if (o1.getiOrderNo() < o2.getiOrderNo()) {
                    return -1;
                } else if (o1.getiOrderNo() > o2.getiOrderNo()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        for (int j = 0; j < lsFile.size(); j++) {
            int num = 0;
            if (lsFile.get(j).getiIsDir() == 1) {
                for (int i = 0; i < jsonFile.size(); i++) {
                    if (lsFile.get(j).getiID() == jsonFile.get(i).getiParentDirID()) {
                        num++;
                    }
                }

            }
            lsFile.get(j).setFileNum(num);
        }
        adapter.setNewData(lsFile);


    }

    /**
     * 界面点击事件
     *
     * @param view
     */
    @OnClick({R.id.iv_yiti_back, R.id.iv_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_yiti_back:
                if (parentDirIdStack.size() == 1) {
                    finish();
                } else {
                    parentDirIdStack.pop();
                    currentDirId=parentDirIdStack.get(parentDirIdStack.size()-1);
                    resetRecyView(currentDirId);
                }
                break;
            case R.id.iv_navigation:
                yitiDetailPresenter.setRightNavigation();
                mRightNavigationPopView.showAtLocation(rl_parentView, Gravity.RIGHT, 0, 500);
                break;
        }
    }

    //处理241下发的文件
    @Override
    public void setcurrentFil(List<CommentUploadListInfo.LstFileBean> lstfiles) {
        for (int i = 0; i < lstfiles.size(); i++) {
            if (lstfiles.get(i).getiType() == 2 && lstfiles.get(i).getiModuleID() == issue.getiIssueId()) {
                switch (lstfiles.get(i).getiUpdateType()) {
                    case 1://增加文件
                        jsonFile.add(lstfiles.get(i));
                        break;
                    case 2://编辑文件
                        for (int k = (jsonFile.size() - 1); k >= 0; k--) {  //设计如此：重复数据下后面的数据才是最新的，所以修改后面，下一版本重写
                            if (lstfiles.get(i).getiID() == jsonFile.get(k).getiID()) {
                                lstfiles.get(i).setIsDown(jsonFile.get(k).getIsDown());
                                jsonFile.set(k, lstfiles.get(i));
                                break;
                            }
                        }
                        break;
                    case 3://删除文件
                        for (int k = 0; k < jsonFile.size(); k++) {
                            if (lstfiles.get(i).getiID() == jsonFile.get(k).getiID()) {
                                FileDaoUtil.deleteDownFile(jsonFile.get(k).getiID()); //删除保存的文件数据
                                jsonFile.remove(k);
                                k--;
                            }
                        }
                        break;
                }
                //删除jsonFile可能存在重复的数据
                for (int k = 0; k < jsonFile.size(); k++) {
                    for (int j = 0; j < jsonFile.size(); j++) {
                        if (k != j) {
                            if (jsonFile.get(k).getiID() == jsonFile.get(j).getiID()) {
                                jsonFile.remove(k);
                                k--;
                                break;
                            }
                        }
                    }
                }
                resetRecyView(currentDirId);
            }
        }


    }

    //处理议题状态更新
    @Override
    public void setIssueState(YitichangeInfo yitichangeInfo) {
        if (yitichangeInfo.getiIssueID() == issue.getiIssueId()) {
            issue.setiStatus(yitichangeInfo.getiStatus());
        }
    }

    //处理议题更新
    @Override
    public void setIssueUpdate(IssueInfo.LstIssue issues) {
        switch (issues.getiUpdateType()) {
            case 2://修改议题
                if (issues.getiIssueId() == issue.getiIssueId()) {
                    issue = issues;
                    initData(issue);
                }

                break;
            case 3://删除议题
                if (issue.getiIssueId() == issues.getiIssueId()) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void changeTrackStatus(boolean isSpeaker){
        mRightNavigationPopView.setTrackStatus(isSpeaker);
    }

    @Override
    public void changeScreenBroadcastStatus(boolean isBroadcasting) {
        mRightNavigationPopView.setBroadcasting(isBroadcasting);
    }

    //设置下载进度
    private void setProress(Progress progress) {
        for (int i = 0; i < jsonFile.size(); i++) {
            if (jsonFile.get(i).getiID()==Integer.parseInt(progress.tag)){
                if (jsonFile.get(i).getIsDown() != 2) {
                    if (jsonFile.get(i).getStrPath().equals(progress.tag)) {
                        jsonFile.get(i).setIsDown(1);
                        jsonFile.get(i).setCurrentprogress(progress.currentSize);
                        jsonFile.get(i).setTotelprogress(progress.totalSize);
                        adapter.notifyDataSetChanged();
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
                    if (jsonFile.get(i).getStrPath().equals(progress.tag)) {
                        jsonFile.get(i).setIsDown(2);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    //下载失败
    private void setLoadFail(String obj) {
        for (int i = 0; i < jsonFile.size(); i++) {
            if (jsonFile.get(i).getiID()==Integer.parseInt(obj)){
                if (jsonFile.get(i).getStrPath().equals(obj)) {
                    jsonFile.get(i).setIsDown(3);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }



    //下载进度通知
    @Subscribe
    public void onEventMainThread(final ProgressEvent event) {
        setProress(event.getData());

    }

    //文件下载失败
    @Subscribe
    public void onEventMainThread(DownloadFailEvent event) {
        setLoadFail(event.getData());

    }

    //下载完成通知
    @Subscribe
    public void onEventMainThread(final DownloadEvent event) {
        setLoadFinish(event.getData());
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
