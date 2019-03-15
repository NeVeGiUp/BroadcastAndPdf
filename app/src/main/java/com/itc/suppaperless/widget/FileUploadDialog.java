package com.itc.suppaperless.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.itc.suppaperless.R;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.meetingmodule.adapter.FileUploadAdapter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.FileUploadDao;
import com.itc.suppaperless.meetingmodule.bean.UploadFailEvent;
import com.itc.suppaperless.meetingmodule.bean.UploadProgressEvent;
import com.itc.suppaperless.meetingmodule.bean.UploadSuccessEvent;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.utils.AppUtils;
import com.itc.suppaperless.utils.DownloadFileUtil;
import com.itc.suppaperless.utils.FileUtil;
import com.itc.suppaperless.utils.GreenDaoUtil;
import com.itc.suppaperless.utils.GsonUtil;
import com.itc.suppaperless.utils.HttpGsonUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.lzy.okgo.model.Progress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件上传对话框列表
 */

public class FileUploadDialog extends Dialog {

    private Context context;
    private LinearLayout layout_allView;
    private ImageView upload_img;
    private RelativeLayout relativeLayoutUploadNoFile;
    private ListView upload_listView;
    private FileUploadAdapter mAdapter;
    private List<FileUploadDao> pathList = new ArrayList<>(); // 获取数据库的文件集合
    private Long fileId = 1L;  // 自定义保存数据库文件的id, 否则也可以采用自增长的问题

    public FileUploadDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_file_upload_list);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        layout_allView = (LinearLayout) findViewById(R.id.layout_allView);
        upload_img = (ImageView) findViewById(R.id.upload_img);
        relativeLayoutUploadNoFile = (RelativeLayout) findViewById(R.id.relativeLayout_upload_no_file);
        upload_listView = (ListView) findViewById(R.id.upload_listView);

        if(AppUtils.isIPad(context)){//平板
            upload_img.setImageResource(R.mipmap.but_guanbi_n);
        }else {//手机
            upload_img.setImageResource(R.mipmap.but_guanbi_n);
        }
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pathList = GreenDaoUtil.queryUserList(); // 查询数据库数据
        setDataToAdapter(null, pathList, false);
    }

    //isSelectFileBtn== true:点击了上传按钮并选择了文件上传；false :点击了上传列表
    public void setDataToAdapter(String[] paths, List<FileUploadDao> data, boolean isSelectFileBtn){
        relativeLayoutUploadNoFile.setVisibility(View.GONE);
        upload_listView.setVisibility(View.VISIBLE);
        if((data != null && data.size() == 0 && !isSelectFileBtn) || (paths == null && isSelectFileBtn)){
            relativeLayoutUploadNoFile.setVisibility(View.VISIBLE);
            upload_listView.setVisibility(View.GONE);
            return;
        }

        if(isSelectFileBtn){
            //获取保存的文件id初始化
            if(AppDataCache.getInstance().getLong("fileId") > 0){
                fileId = AppDataCache.getInstance().getLong("fileId");
            }
            for (final String pathName : paths) {
                fileId ++;
                final FileUploadDao uploadDao = new FileUploadDao(fileId, pathName, 0, false, 0, 0, 0);
                pathList.add(uploadDao);
//                Log.e("==paths==", pathName + "         " + fileId);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!StringUtil.isEmpty(pathName)) {
                            String fileName = FileUtil.getFileName(pathName);
                            if (fileName != null) {
                                DownloadFileUtil.getInstance().UpLoadFile(pathName.getBytes(), fileName.getBytes(), 4);
                                //插入数据到数据库(在数据源处)
                                GreenDaoUtil.insertInfo(uploadDao);
                            }
                        }
                    }
                }).start();
            }
            AppDataCache.getInstance().putLong("fileId", fileId);
            mAdapter = new FileUploadAdapter(context, pathList);
        } else {
            //处理当上传没完成重新进入应用时的处理
            if (data != null) {
                for(int i = 0; i < data.size(); i++){
                    if(data.get(i).getUploadState() == 2){
                        data.get(i).setUploadState(1);
                    }
                }
            }
            mAdapter = new FileUploadAdapter(context, data);
        }
        upload_listView.setAdapter(mAdapter);
        mAdapter.setAdapterClickListener(new IAdapterClickListener() {
            @Override
            public void adapterClick(int id, final int position) {
                if(pathList.get(position).getUploadState() == 1){ // 上传失败重发
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String pathName = pathList.get(position).getFileName();
                            if (!StringUtil.isEmpty(pathName)) {
                                String fileName = FileUtil.getFileName(pathName);
                                if (fileName != null) {
                                    DownloadFileUtil.getInstance().UpLoadFile(pathName.getBytes(), fileName.getBytes(), 4);
                                    pathList.get(position).setUploadState(0);
                                    //更新数据到数据库
                                    GreenDaoUtil.updateInfo(pathList.get(position));
                                    layout_allView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mAdapter != null) {
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(final UploadFailEvent event) {//上传文件失败
        final String jsonStr = event.getData();
        if (jsonStr != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < pathList.size(); i++){
                        if(pathList.get(i).getFileName().equals(jsonStr)){
                            pathList.get(i).setUploadState(1);
                            pathList.get(i).setIsHasUploadFile(true);
                            //更新数据到数据库
                            GreenDaoUtil.updateInfo(pathList.get(i));
                        }
                    }
                    layout_allView.post(new Runnable() {
                        @Override
                        public void run() {
                            if(mAdapter != null){
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }).start();
        }
    }

    @Subscribe
    public void onEventMainThread(UploadSuccessEvent event) {//上传文件成功
        final String jsonStr = event.getData();
        if (jsonStr != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < pathList.size(); i++) {
                        if (pathList.get(i).getFileName().equals(jsonStr)) {
                            pathList.get(i).setUploadState(3);
                            pathList.get(i).setIsHasUploadFile(true);
                            //更新数据到数据库
                            GreenDaoUtil.updateInfo(pathList.get(i));
                        }
                    }
                    layout_allView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }).start();
        }
    }

    @Subscribe
    public void onEventMainThread(UploadProgressEvent event) {//上传文件的进度
                Progress progress = event.getData();
                final String fileName = progress.tag;
                final long currentFileSize = progress.currentSize;
                final long totalFileSize = progress.totalSize;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < pathList.size(); i++) {
                            //文件名相同的处理
                            if (pathList.get(i).getFileName().equals(fileName) && !pathList.get(i).getIsHasUploadFile()) {
                                pathList.get(i).setUploadState(2);
                                pathList.get(i).setCurrentProgress(currentFileSize);
                                pathList.get(i).setTotelProgress(totalFileSize);
                                //更新数据到数据库
                                GreenDaoUtil.updateInfo(pathList.get(i));
                            }
                        }
                        layout_allView.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mAdapter != null) {
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }).start();


    }

    @Subscribe
    public void onEventMainThread(FileUpdateEvent event) {//列表的数据的更新
        final String json = event.getData();
        if (!StringUtil.isEmpty(json)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CommentUploadListInfo.LstFileBean lstFileBean = HttpGsonUtil.getInstance().parseFileUpdate(json);
                    if (lstFileBean.getiUpdateType() == 1) {//文件的添加
                        if (lstFileBean.getiType() == 4){
                            for (int i = 0; i < pathList.size(); i++){
                                String fileName = FileUtil.getFileName(pathList.get(i).getFileName());
                                if(!StringUtil.isEmpty(fileName)){
                                    if (fileName.equals(lstFileBean.getStrName()) && pathList.get(i).getIsHasUploadFile()) {
//                                        Log.e("==111==", fileName + " === " + lstFileBean.getStrFileName() + " === " + lstFileBean.getIFileID() + " === " + pathList.get(i).getIsHasUploadFile());
                                        pathList.get(i).setIFileID(lstFileBean.getiID());
                                        //更新数据到数据库
                                        GreenDaoUtil.updateInfo(pathList.get(i));
                                    }
                                }
                            }
                        }
                    }else if (lstFileBean.getiUpdateType() == 2) {//文件的删除
                        for (int i = 0; i < pathList.size(); i++) {
//                            Log.e("==222==", pathList.get(i).getIFileID() + "===" + lstFileBean.getIFileID());
                             if (pathList.get(i).getIFileID() == lstFileBean.getiID()) {
                                 pathList.get(i).setUploadState(4);
                                 //更新数据到数据库
                                 GreenDaoUtil.updateInfo(pathList.get(i));
                            }
                        }
                        layout_allView.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mAdapter != null) {
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }).start();
        }
    }

    public void closeEventBus(){
        EventBus.getDefault().unregister(this);
    }
}