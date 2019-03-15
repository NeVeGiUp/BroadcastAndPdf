package com.itc.suppaperless.meetingmodule.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.VideoServiceAdapter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.DoLive;
import com.itc.suppaperless.meetingmodule.mvp.contract.VideoServiceContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.VideoServicePresenter;
import com.itc.suppaperless.player.FFmpegPlayActivity;
import com.itc.suppaperless.widget.DialogNewInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 视频服务
 */
public class VideoServiceFragment extends BaseFragment<VideoServicePresenter> implements VideoServiceContract.View {


    @BindView(R.id.rb_zhibo)
    RadioButton rbZhibo;
    @BindView(R.id.rb_db)
    RadioButton rbDb;
    @BindView(R.id.rv_spfw)
    RecyclerView rvSpfw;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    private VideoServiceAdapter adapter;
    private List<CommentUploadListInfo.LstFileBean> zbList = new ArrayList<>();
    private List<CommentUploadListInfo.LstFileBean> dbList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public VideoServicePresenter createPresenter() {
        return new VideoServicePresenter(this);
    }

    @Override
    public void init() {
        getPresenter();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        adapter = new VideoServiceAdapter(R.layout.item_video_list);
        rvSpfw.setLayoutManager(manager);
        rvSpfw.setAdapter(adapter);
        adapter.setNewData(zbList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommentUploadListInfo.LstFileBean lstFileBean=((VideoServiceAdapter)adapter).getData().get(position);
                if (lstFileBean.getiType()==8){
                    if (AppDataCache.getInstance().getInt(lstFileBean.getStrPath())==0){
                        new DialogNewInterface(getActivity()).setText("直播暂未开始").setSingleCancle("我知道了").show();
                        return;
                    }
                }
                Intent intent = new Intent(mContext, FFmpegPlayActivity.class);
                intent.putExtra(Config.FILE_PATH, lstFileBean.getStrPath());
                intent.putExtra(Config.FILE_NAME,lstFileBean.getStrName());
                mContext.startActivity(intent);
            }
        });
        rbDb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (dbList.size() == 0) {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setNewData(dbList);
                    } else {
                        rlNoData.setVisibility(View.GONE);
                        adapter.setNewData(dbList);
                    }
                }
            }
        });
        rbZhibo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (zbList.size() == 0) {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setNewData(zbList);
                    } else {
                        rlNoData.setVisibility(View.GONE);
                        adapter.setNewData(zbList);
                    }
                }

            }
        });

    }


    //文件更新
    @Override
    public void getUpdateFile(List<CommentUploadListInfo.LstFileBean> lstFiles) {
        for (int i = 0; i < lstFiles.size(); i++) {
            if (lstFiles.get(i).getiType() == 8) {//直播文件
                if (lstFiles.get(i).getiUpdateType() == 1) {//增加
                    zbList.add(lstFiles.get(i));
                } else if (lstFiles.get(i).getiUpdateType() == 2) {//修改
                    for (int k = 0; k < zbList.size(); k++) {
                        if (lstFiles.get(i).getiID() == zbList.get(k).getiID()) {
                            zbList.set(k, lstFiles.get(i));
                        }
                    }
                } else if (lstFiles.get(i).getiUpdateType() == 3) {//删除
                    for (int k = 0; k < zbList.size(); k++) {
                        if (lstFiles.get(i).getiID() == zbList.get(k).getiID()) {
                            zbList.remove(k);
                        }
                    }

                }
            }
            if (lstFiles.get(i).getiType() == 9) {//点播文件
                if (lstFiles.get(i).getiUpdateType() == 1) {//增加
                    dbList.add(lstFiles.get(i));
                } else if (lstFiles.get(i).getiUpdateType() == 2) {//修改
                    for (int k = 0; k < dbList.size(); k++) {
                        if (lstFiles.get(i).getiID() == dbList.get(k).getiID()) {
                            dbList.set(k, lstFiles.get(i));
                        }
                    }

                } else if (lstFiles.get(i).getiUpdateType() == 3) {//删除
                    for (int k = 0; k < dbList.size(); k++) {
                        if (lstFiles.get(i).getiID() == dbList.get(k).getiID()) {
                            dbList.remove(k);
                        }
                    }

                }

            }
        }
        if (zbList.size() != 0 || dbList.size() != 0) {
            if (rbZhibo.isChecked()) {
                if (zbList.size() == 0) {
                    rlNoData.setVisibility(View.VISIBLE);
                } else {
                    rlNoData.setVisibility(View.GONE);
                }

            } else if (rbDb.isChecked()) {
                if (dbList.size() == 0) {
                    rlNoData.setVisibility(View.VISIBLE);
                } else {
                    rlNoData.setVisibility(View.GONE);
                }
            }
            //删除dbList可能存在重复的数据
            for (int i = 0; i < dbList.size(); i++) {
                for (int j = 0; j < dbList.size(); j++) {
                    if (i != j) {
                        if (dbList.get(i).getiID() == dbList.get(j).getiID()) {
                            dbList.remove(i);
                            i--;
                            break;
                        }
                    }
                }
            }
            //删除zbList可能存在重复的数据
            for (int i = 0; i < zbList.size(); i++) {
                for (int j = 0; j < zbList.size(); j++) {
                    if (i != j) {
                        if (zbList.get(i).getiID() == zbList.get(j).getiID()) {
                            zbList.remove(i);
                            i--;
                            break;
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void doLive(DoLive live) {
        AppDataCache.getInstance().putInt(live.getStrUrl(),live.getiStatus());
    }
}
