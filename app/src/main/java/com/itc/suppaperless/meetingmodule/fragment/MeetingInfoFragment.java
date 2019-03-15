package com.itc.suppaperless.meetingmodule.fragment;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.ServiceSendMeetingInfo;
import com.itc.suppaperless.meetingmodule.eventbean.DeleteOrModifyEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadEvent;
import com.itc.suppaperless.meetingmodule.eventbean.DownloadFailEvent;
import com.itc.suppaperless.meetingmodule.eventbean.ProgressEvent;
import com.itc.suppaperless.meetingmodule.mvp.contract.HyxxContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.HyxxPresenter;
import com.itc.suppaperless.utils.DownloadFileUtil;
import com.itc.suppaperless.utils.FileDaoUtil;
import com.itc.suppaperless.utils.FileOpenUtil;
import com.itc.suppaperless.utils.FileUtil;
import com.itc.suppaperless.utils.LayoutUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.itc.suppaperless.utils.UiUtil;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * 会议信息
 */

public class MeetingInfoFragment extends BaseFragment implements HyxxContract.View {

    @BindView(R.id.tv_meeting_name)
    TextView tvMeetingName;
    @BindView(R.id.tv_meeting_position)
    TextView tvMeetingPosition;
    @BindView(R.id.tv_meeting_time)
    TextView tvMeetingTime;
    @BindView(R.id.tv_meeting_chairman)
    TextView tvMeetingChairman;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.tv_upload_title)
    TextView tvUploadTitle;
    @BindView(R.id.tv_top)
    TextView tv_top;
    @BindView(R.id.download_text)
    TextView download_text;
    @BindView(R.id.rl_view_width)
    RelativeLayout rl_view_width;
    @BindView(R.id.ll_yicheng)
    LinearLayout ll_yicheng;
    private CommentUploadListInfo.LstFileBean yichengFile;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hyxx;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new HyxxPresenter(this);
    }


    @Override
    public void init() {
        EventBus.getDefault().register(this);
        getPresenter();
        rl_view_width.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yichengFile != null) {
                    if (yichengFile.getIsDown() == 2) {//判断是否下载完
                        String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
                        int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
                        File file = new File(Config.downloadpath + StringUtil.strSplit(ip)+port+"/"+ AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/"+ yichengFile.getiID());
                        String[] filelist = file.list();
                        if (filelist == null || filelist.length <= 0) {  //文件夹下文件不存在，重新下载
                            yichengFile.setIsDown(0);
                            DownloadFileUtil.getInstance().setDownFile(yichengFile.getStrPath(),yichengFile.getiID());

                        } else {  //文件夹下文件存在，打开该文件
                            FileOpenUtil.OpenFile(mContext, yichengFile.getiID(), yichengFile.getStrName(), filelist[0], yichengFile.getStrPath(), 1);
                        }
                    }
                }


            }
        });
    }

    @Override
    public void getMeetingInfo(ServiceSendMeetingInfo info) {//得到返回的会议信息
        tvMeetingName.setText(info.getStrName());
        tvMeetingPosition.setText(info.getStrMeetingRoomName());
        tvMeetingTime.setText(info.getStrStartTime() + " 至\n" + info.getStrEndTime());


    }
    //241文件更新
    @Override
    public void getFileUpdate(CommentUploadListInfo info) {
        for (int i = 0; i < info.getLstFile().size(); i++) {
            if (info.getLstFile().get(i).getiType() == 1) {//判断是否为议程文件
                if (info.getLstFile().get(i).getiUpdateType() == 1 || info.getLstFile().get(i).getiUpdateType() == 2) {
                    yichengFile = info.getLstFile().get(i);
                    if (yichengFile != null) {
                        EventBus.getDefault().post(new DeleteOrModifyEvent(yichengFile.getiID()));
                        ll_yicheng.setVisibility(View.VISIBLE);
                        UiUtil.showCurFileImage(mContext, ivUpload, yichengFile.getStrName());
                        tvUploadTitle.setText(yichengFile.getStrName());
                        String ip=AppDataCache.getInstance().getString(Config.IP_MEETING);
                        int port=AppDataCache.getInstance().getInt(Config.PORT_MEETING);
                        File file = new File(Config.downloadpath + StringUtil.strSplit(ip)+port+"/"+ AppDataCache.getInstance().getInt(Config.MEETING_ID)+"/"+ yichengFile.getiID());
                        if (file.exists()) {
                            FileUtil.deleteFile(file);
                        }
                            OkDownload.getInstance().removeTask(yichengFile.getiID()+"");
                            DownloadFileUtil.getInstance().setDownFile(yichengFile.getStrPath(),yichengFile.getiID());
                    }
                } else {
                    ll_yicheng.setVisibility(View.GONE);
                    yichengFile = null;
                }

                break;
            }

        }

    }

    @Override
    public void getJiaoliuUserInfo(JiaoLiuUserInfo info) {//重置人员跟下发参会人员
        for (int i = 0; i < info.getLstUser().size(); i++) {
            if (info.getLstUser().get(i).getiIsChairMan() == 1) {
                tvMeetingChairman.setText(info.getLstUser().get(i).getStrUserName());
            }
            int userId=AppDataCache.getInstance().getInt(Config.USER_ID);
            if (userId==info.getLstUser().get(i).getIUserID()){
                AppDataCache.getInstance().putString(Config.USER_NAME,info.getLstUser().get(i).getStrUserName());
            }
        }

    }

    private void setloadProess() {
        switch (yichengFile.getIsDown()) {
            case 0:
                download_text.setVisibility(View.VISIBLE);
                download_text.setText("等待下载");
                break;
            case 1:
                download_text.setVisibility(View.VISIBLE);
                download_text.setText("下载中....");
                if (yichengFile.getTotelprogress() != 0) {
                    LayoutUtil.SetLayoutWidth(tv_top, ((ScreenUtil.getScreenWidth(mContext) * 2 / 3) * yichengFile.getCurrentprogress() / yichengFile.getTotelprogress()));
                }
                break;
            case 2://下载完成
                download_text.setVisibility(View.GONE);
                LayoutUtil.SetLayoutWidth(tv_top, 0);
//                FileDaoUtil.setIsDown(yichengFile.getStrPath());
                yichengFile.setIsDown(2);
                break;
            case 3:
                download_text.setVisibility(View.VISIBLE);
                LayoutUtil.SetLayoutWidth(tv_top, 0);
                download_text.setText("下载失败");
                break;

        }


    }

    //下载进度通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final ProgressEvent event) {
        Progress progress = event.getData();
        if (yichengFile!=null){
            if (yichengFile.getiID()==Integer.parseInt(progress.tag)) {
                if (yichengFile.getIsDown() != 2) {
                    yichengFile.setIsDown(1);
                    yichengFile.setCurrentprogress(progress.currentSize);
                    yichengFile.setTotelprogress(progress.totalSize);
                    setloadProess();
                }
            }
        }


    }

    //文件下载失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DownloadFailEvent event) {
        String progress = event.getData();
        if (yichengFile!=null){
            if (yichengFile.getiID()==Integer.parseInt(progress)) {
                yichengFile.setIsDown(3);
                setloadProess();
            }
        }


    }

    //下载完成通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DownloadEvent event) {
        Progress progress = event.getData();
        if (yichengFile!=null){
            if (yichengFile.getiID()==Integer.parseInt(progress.tag)) {
                yichengFile.setIsDown(2);
                setloadProess();
            }
        }

    }

    /**
     * 把文件日期换乘unix时间戳
     *
     * @param strDate
     * @return
     */
    public String getFileTimestamp(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Long.toString(date.getTime() / 1000);
    }

}
