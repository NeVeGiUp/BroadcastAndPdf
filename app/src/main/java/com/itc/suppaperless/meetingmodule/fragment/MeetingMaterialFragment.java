package com.itc.suppaperless.meetingmodule.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.CailiaoAdapter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.FxFileDialogArgs;
import com.itc.suppaperless.meetingmodule.bean.MuiltFileUploadEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DeleteOrModifyEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadFailEvent;
import com.itc.suppaperless.meetingmodule.eventbean.ProgressEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.CailiaoContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.CailiaoPresenter;
import com.itc.suppaperless.meetingmodule.ui.FileBrowseActivity;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;
import com.itc.suppaperless.utils.DownloadFileUtil;
import com.itc.suppaperless.utils.FileDaoUtil;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.FxHelp;
import com.itc.suppaperless.utils.GsonUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.FileUploadDialog;
import com.lzy.okgo.model.Progress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.会议材料
 */
public class MeetingMaterialFragment extends BaseFragment implements CailiaoContract.View {

    @BindView(R.id.tv_wenjian)
    TextView tvWenjian;
    @BindView(R.id.tv_wenjian_num)
    TextView tvWenjianNum;
    @BindView(R.id.upload_btn)
    Button uploadBtn;
    @BindView(R.id.upload_list_btn)
    Button uploadListBtn;
    @BindView(R.id.ziliao_tab_divide)
    RelativeLayout ziliaoTabDivide;
    @BindView(R.id.ziliao_below_tab_view)
    View ziliaoBelowTabView;
    @BindView(R.id.rl_cailiao)
    RecyclerView rlCailiao;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.text_no_data)
    TextView textNoData;
    Unbinder unbinder;
    private CailiaoAdapter adapter;
    private List<CommentUploadListInfo.LstFileBean> jsonFile = new ArrayList<>();
    private List<CommentUploadListInfo.LstFileBean> lsFile = new ArrayList<>(); //adapter显示使用的数组
    private int currentDirId = 0;       //当前显示的父文件夹id
    private Stack<Integer> parentDirIdStack = new Stack<>();//存放父目录文件夹id的栈
    private boolean isConnect = true;//用来判断可不可以上传文件
    public FileUploadDialog fileUploadDialog;//上传文件的对话框

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cailiao;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new CailiaoPresenter(this);
    }


    @Override
    public void init() {
        getPresenter();
        EventBus.getDefault().register(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlCailiao.setLayoutManager(linearLayoutManager);
        parentDirIdStack.push(0);
        adapter = new CailiaoAdapter(R.layout.item_cailiao);
        rlCailiao.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommentUploadListInfo.LstFileBean bean = ((CailiaoAdapter) adapter).getData().get(position);
                //点击的是文件夹
                if (bean.getiIsDir() == 1) {
                    parentDirIdStack.push(bean.getiID());
                    currentDirId = bean.getiID(); //点击的文件夹的id
                    resetRecyView(currentDirId);

                } else {//是文件
                    if (bean.getIsDown() == 2) {//判断是否下载完
                        String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
                        int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
                        File file = new File(Config.downloadpath + StringUtil.strSplit(ip)+port+"/"+AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/" +bean.getiID());
                        String[] filelist = file.list();
                        if (filelist == null || filelist.length <= 0) {  //文件夹下文件不存在，重新下载
                            bean.setIsDown(0);
                            DownloadFileUtil.getInstance().setDownFile(bean.getStrPath(),bean.getiID());
                        } else {  //文件夹下文件存在，打开该文件
                            FileOpenUtil.OpenFile(mContext, bean.getiID(), bean.getStrName(), filelist[0], bean.getStrPath(), 0);
                        }
                    }
                }
            }
        });
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

    //设置文件数量
    public void setFeilNum() {
        int number = 0;
        if (jsonFile.size() != 0) {
            for (int i = 0; i < jsonFile.size(); i++) {
                if (jsonFile.get(i).getiIsDir() == 0) {
                    number++;
                }
            }
        }
        tvWenjianNum.setText(number + "");
    }

    //文件更新处理
    @Override
    public void getUpdateFile(List<CommentUploadListInfo.LstFileBean> lstFiles) {
        isConnect = true;
        for (int i = 0; i < lstFiles.size(); i++) {
            if (lstFiles.get(i).getiType() == 4) {//为材料文件
                switch (lstFiles.get(i).getiUpdateType()) {
                    case 1://文件添加
                        jsonFile.add(lstFiles.get(i));
                        if (lstFiles.get(i).getiParentDirID() == currentDirId) {
                            lstFiles.get(i).setiIsShowProgress(true);
                            lsFile.add(lstFiles.get(i));
                        }
                        if (lstFiles.get(i).getiIsDir() == 0) {//判断是否为文件
                            if (!FileDaoUtil.getIsDown(lstFiles.get(i).getiID())) {//判断添加文件是否已经下载过
                                String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
                                int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
                                lstFiles.get(i).setMeetingID(AppDataCache.getInstance().getInt(Config.MEETING_ID));
                                lstFiles.get(i).setIpAndPort(StringUtil.strSplit(ip)+port);
                                //文件下载
                                DownloadFileUtil.getInstance().setDownFile(lstFiles.get(i).getStrPath(), lstFiles.get(i).getiID());
                            } else {
                                jsonFile.get(jsonFile.size() - 1).setIsDown(2);
                            }
                        }
                        break;
                    case 2://文件编辑
                        EventBus.getDefault().post(new DeleteOrModifyEvent(lstFiles.get(i).getiID()));
                        for (int k = (jsonFile.size() - 1); k >= 0; k--) {  //设计如此：重复数据下后面的数据才是最新的，所以修改后面，下一版本重写
                            if (lstFiles.get(i).getiID() == jsonFile.get(k).getiID()) {
                                lstFiles.get(i).setIsDown(jsonFile.get(k).getIsDown());
                                jsonFile.set(k, lstFiles.get(i));
                                break;
                            }
                            for (int j = lsFile.size() - 1; j >= 0; j--) {
                                if (lstFiles.get(i).getiID() == lsFile.get(j).getiID()) {
                                    lsFile.set(j, lstFiles.get(i));
                                    break;
                                }
                            }
                        }

                        break;
                    case 3://文件删除
                        EventBus.getDefault().post(new DeleteOrModifyEvent(lstFiles.get(i).getiID()));
                        for (int j = 0; j < jsonFile.size(); j++) {
                            if (lstFiles.get(i).getiID() == jsonFile.get(j).getiID()) {
                                FileDaoUtil.deleteDownFile(jsonFile.get(j).getiID()); //删除保存的文件数据
                                jsonFile.remove(j); //jsonFile 删除数据，免的在lsFile数组中添加再删除
                                j--;
                            }
                        }
                        //是否显示到UI,adapter显示
                        if (lstFiles.get(i).getiParentDirID() == currentDirId) {
                            for (int k = 0; k < lsFile.size(); k++) {
                                if (lsFile.get(k).getiID() == lstFiles.get(i).getiID()) {
                                    lsFile.remove(k);
                                    k--;
                                }
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
        //删除lsFile可能存在重复的数据
        for (int i = 0; i < lsFile.size(); i++) {
            for (int j = 0; j < lsFile.size(); j++) {
                if (i != j) {
                    if (lsFile.get(i).getiID() == lsFile.get(j).getiID()) {
                        lsFile.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        setFeilNum();
        initadapter(lsFile);


    }

    //设置adapter数据
    private void initadapter(List<CommentUploadListInfo.LstFileBean> lsFile) {
        if (lsFile.size() == 0) {
            rlNoData.setVisibility(View.VISIBLE);
        } else {
            rlNoData.setVisibility(View.GONE);
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

    //设置下载进度
    private void setProress(Progress progress) {
        for (int i = 0; i < jsonFile.size(); i++) {
            if (jsonFile.get(i).getiID()==Integer.parseInt(progress.tag)){
                if (jsonFile.get(i).getIsDown() != 2) {
                    if (jsonFile.get(i).getiID()==Integer.parseInt(progress.tag)) {
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
                        jsonFile.get(i).setIsDown(2);
                        FileDaoUtil.insertFile(jsonFile.get(i).getiID(), jsonFile.get(i).getStrPath(), jsonFile.get(i).getiType(), new Gson().toJson(jsonFile.get(i))); //文件数据添加到数据库
                        FileDaoUtil.setIsDown(jsonFile.get(i).getiID());
                        adapter.notifyDataSetChanged();
                    }

            }
        }

    }

    //下载失败
    private void setLoadFail(String obj) {
        for (int i = 0; i < jsonFile.size(); i++) {
            if (jsonFile.get(i).getiID()==Integer.parseInt(obj)){
                if (jsonFile.get(i).getiID()==Integer.parseInt(obj)) {
                    jsonFile.get(i).setIsDown(3);
                    adapter.notifyDataSetChanged();
                }
            }
        }

    }

    //下载进度通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final ProgressEvent event) {
        Log.i("zwdevent", "下载进度" + event.getData());
        Progress progress = event.getData();
        setProress(progress);
    }

    //下载完成通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DownloadEvent event) {
        Progress progresss = event.getData();
        setLoadFinish(progresss);
    }

    //下载失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DownloadFailEvent event) {
        Log.i("zwdevent", "下载失败" + event.getData());
        String fail = event.getData();
        setLoadFail(fail);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public boolean isInRootPath() {
        if (parentDirIdStack.size() == 1) {
            return true;
        }
        return false;
    }

    public void backToUpperLevel() {
        parentDirIdStack.pop();
        currentDirId = parentDirIdStack.peek();
        resetRecyView(currentDirId);
    }


    @OnClick({R.id.upload_btn, R.id.upload_list_btn})
    public void onViewClicked(View view) {
        if (!PaperlessApplication.getGlobalConstantsBean().getConnect()){
            ToastUtil.show(getActivity(), "您已处于离线状态,无法操作", 1);
            return;
        }
        switch (view.getId()) {
            case R.id.upload_btn:
                    showFileChooser();
                break;
            case R.id.upload_list_btn:
                    showUpLoadFileDialog();
                break;
        }
    }
    /**
     * 显示文件上传选择器
     */
    private void showFileChooser() {
        FxFileDialogArgs _Args = new FxFileDialogArgs();
        _Args.DialogTitle = "选择文件";
        _Args.DialogType = FxHelp.DLG_OPEN_FILE;
        //需要展示的文件的后缀名
        _Args.Filter = Config.SELECTFILESUFFIX;
        _Args.IsMultiSelect = true;
        FxHelp.changeActivity(getActivity(), FileBrowseActivity.class, _Args, true, 0);
    }
    /**
     *  关闭上传文件对话框列表
     */
    public void showUpLoadFileDialog() {
        if(fileUploadDialog == null){
            fileUploadDialog = new FileUploadDialog(mContext);
        }
        fileUploadDialog.show();
    }
    //选择文件后的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MuiltFileUploadEvent event) {
        String[] paths = event.getData();
        showUpLoadFileDialog();
        fileUploadDialog.setDataToAdapter(paths, null, true);
    }
}
