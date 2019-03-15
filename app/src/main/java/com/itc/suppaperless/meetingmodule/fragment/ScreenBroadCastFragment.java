package com.itc.suppaperless.meetingmodule.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.ScreenBroadAdapter;
import com.itc.suppaperless.meetingmodule.bean.BroadCatstMsg;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IDialogClickListener;
import com.itc.suppaperless.meetingmodule.mvp.contract.ScreenBroadContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.ScreenBoroadPresenter;
import com.itc.suppaperless.utils.TimeUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.JzcontrolCloseDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 大屏广播
 */
public class ScreenBroadCastFragment extends BaseFragment<ScreenBoroadPresenter> implements ScreenBroadContract.View {


    @BindView(R.id.rl_baodcast)
    RecyclerView rlBaodcast;
    @BindView(R.id.tv_screen)
    TextView tvScreen;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_volume_tishi)
    TextView tvVolumeTishi;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.pb_volune)
    SeekBar pbVolune;
    @BindView(R.id.tv_jindu_tishi)
    TextView tvJinduTishi;
    @BindView(R.id.tv_jingdu)
    TextView tvJingdu;
    @BindView(R.id.tv_all_jingdu)
    TextView tvAllJingdu;
    @BindView(R.id.ll_jindu)
    LinearLayout llJindu;
    @BindView(R.id.pb_jindu)
    SeekBar pbJindu;
    @BindView(R.id.tv_backoff)
    TextView tvBackoff;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.tv_dapin)
    TextView tvDapin;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.tv_terminal)
    TextView tvTerminal;
    ScreenBroadAdapter mAdapter;
    Unbinder unbinder;
    private boolean isStart = false;//判断是否开始点播
    private boolean Terminal;//判断是否已经广播到终端
    private int isPlay = 1;//暂停还是播放
    private boolean jinduFromeUser;//判断播放进度是否认为拖动
    private boolean volumeFromeUser;//判断音量进度是否认为拖动
    CommentUploadListInfo.LstFileBean lstFileBeans;
    private List<CommentUploadListInfo.LstFileBean> dbList = new ArrayList<>();
    private JzcontrolCloseDialog jzcontrolDialog;  //广播到终端的dialog
    private double currentProgress;//当前的播放的进度

    @Override
    public int getLayoutId() {
        return R.layout.fragment_screen_broad_cast;
    }

    @Override
    public ScreenBoroadPresenter createPresenter() {
        return new ScreenBoroadPresenter(this);
    }

    @Override
    public void init() {
        getPresenter();
        mAdapter = new ScreenBroadAdapter(R.layout.item_screen_broad);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rlBaodcast.setLayoutManager(manager);
        rlBaodcast.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<CommentUploadListInfo.LstFileBean> lstFileBean = ((ScreenBroadAdapter) adapter).getData();
                for (int i = 0; i < lstFileBean.size(); i++) {
                    lstFileBean.get(i).setIsDown(0);
                }
                lstFileBean.get(position).setIsDown(1);
                lstFileBeans = lstFileBean.get(position);
                mAdapter.notifyDataSetChanged();
                tvContent.setText(lstFileBeans.getStrName());

            }
        });
        pbJindu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                jinduFromeUser = fromUser;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (jinduFromeUser && isStart) {
                    double pro = new BigDecimal((float) (seekBar.getProgress() * 1.00 / 100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    currentProgress=pro;
                    getPresenter().setPlayProgress(pro);

                }

            }
        });
        pbVolune.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeFromeUser = fromUser;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (volumeFromeUser && isStart) {
                    getPresenter().setVolume(seekBar.getProgress());
                    tvVolume.setText(seekBar.getProgress()+"");


                }
            }
        });
    }

    //文件更新
    @Override
    public void getUpdateFile(List<CommentUploadListInfo.LstFileBean> lstFiles) {
        for (int i = 0; i < lstFiles.size(); i++) {
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
        mAdapter.setNewData(dbList);

    }

    @Override
    public void getBroadCasst(BroadCatstMsg strCmdMsg) {//800回发的播放进度
        switch (strCmdMsg.getVodCtrlType()) {
            case 7://音量回调
                pbVolune.setProgress(strCmdMsg.getVolume());
                tvVolume.setText("" + strCmdMsg.getVolume());
                break;
            case 8://播放进度回调
                setPlayProgress(strCmdMsg);
                break;
            case 11://播放状态回调4为结束
                if (strCmdMsg.getStatus() == 4) {
                    if (tvDapin.getText().equals("正在处理")) {
                        tvDapin.setText("大屏点播");
                        tvDapin.setEnabled(true);
                    }
                }
                break;
        }


    }

    private void setPlayProgress(BroadCatstMsg strCmdMsg) {
        double pro = new BigDecimal((float) (strCmdMsg.getCurrentTime() * 1.00 / strCmdMsg.getMediaLen())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        currentProgress=pro;
        if (isStart) {
            pbJindu.setProgress((int) (pro * 100));
            tvJingdu.setText(TimeUtil.getSMSTimes(strCmdMsg.getCurrentTime()));
            tvAllJingdu.setText("/" + TimeUtil.getSMSTimes(strCmdMsg.getMediaLen()));
            if (tvDapin.getText().equals("正在处理")) {
                tvDapin.setText("关闭点播");
                tvDapin.setEnabled(true);
            }
        } else {
            if (tvContent.getText() == null || tvContent.getText().equals("")) {//还没关闭点播，退出会议再进来执行
                isStart = true;
                tvContent.setText(AppDataCache.getInstance().getString(Config.BROADCAST));
                Drawable drawabless = getResources().getDrawable(R.mipmap.but_play_n);
                drawabless.setBounds(0, 0, drawabless.getMinimumWidth(), drawabless.getMinimumHeight());
                tvDapin.setText("关闭点播");
                tvDapin.setCompoundDrawables(null, drawabless, null, null);
                Drawable drawables = getResources().getDrawable(R.mipmap.but_broadcast_h);
                drawables.setBounds(0, 0, drawables.getMinimumWidth(), drawables.getMinimumHeight());
                Log.i("aaaaaaffffffffff", "fdfdfdfdfd");
                tvTerminal.setText("广播到终端");
                tvTerminal.setCompoundDrawables(null, drawables, null, null);
                Drawable drawable = getResources().getDrawable(R.mipmap.but_start_n);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvStop.setCompoundDrawables(null, drawable, null, null);
                isPlay = 1;
            }
            pbJindu.setProgress(0);
            tvJingdu.setText("0");
            tvAllJingdu.setText("/0");

        }
    }

    @OnClick({R.id.tv_backoff, R.id.tv_go, R.id.tv_dapin, R.id.tv_stop, R.id.tv_terminal})
    public void onViewClicked(View view) {
        if (!PaperlessApplication.getGlobalConstantsBean().getConnect()) {
            ToastUtil.show(getActivity(), "您已处于离线状态,无法操作", 1);
            return;
        }
        switch (view.getId()) {
            case R.id.tv_backoff:
                if (currentProgress!=0){
                    if ((currentProgress-0.1)>0){
                        currentProgress=currentProgress-0.1;
                    }else {
                        currentProgress=0;
                    }
                    getPresenter().setPlayProgress(currentProgress);
//                    pbJindu.setProgress((int)(currentProgress*100));
                }
                break;
            case R.id.tv_go:
                if (currentProgress!=1){
                    if ((currentProgress+0.1)<1){
                        currentProgress=currentProgress+0.1;
                    }else {
                        currentProgress=1;
                    }
                    getPresenter().setPlayProgress(currentProgress);
//                    pbJindu.setProgress((int)(currentProgress*100));
                }

                break;
            case R.id.tv_dapin:
                clikDbPlay(isStart);
                break;
            case R.id.tv_stop:
                if (isStart) {//判断是否开始播放
                    if (isPlay == 1) {
                        isPlay = 2;
                        Drawable drawable = getResources().getDrawable(R.mipmap.but_start_h);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvStop.setCompoundDrawables(null, drawable, null, null);
                        getPresenter().suspendPlay();
                        tvStop.setText("播放");
                    } else {
                        isPlay = 1;
                        getPresenter().continuePlay();
                        Drawable drawable = getResources().getDrawable(R.mipmap.but_start_n);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvStop.setCompoundDrawables(null, drawable, null, null);
                        tvStop.setText("暂停");
                    }
                }
                break;

            case R.id.tv_terminal:
                if (isStart) {
                    if (!Terminal) {
                        if (jzcontrolDialog == null) {
                            jzcontrolDialog = new JzcontrolCloseDialog(getActivity(), new IDialogClickListener() {
                                @Override
                                public void dialogClick(int id, int type) {
                                    if (id == R.id.btn_jzc_sure) {
                                        if (type == 1) {
                                            getPresenter().broadCastToAll(false);
                                        } else if (type == 2) {
                                            getPresenter().broadCastToAll(true);
                                        }
                                        Drawable drawable = getResources().getDrawable(R.mipmap.but_broadcast_n);
                                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                        tvTerminal.setCompoundDrawables(null, drawable, null, null);
                                        tvTerminal.setText("结束广播");
                                        Terminal = true;
                                    }
                                }
                            });
                        }
                        jzcontrolDialog.show();
                        jzcontrolDialog.setText("广播到终端", "广播到所有终端(可异步浏览)", "强制广播到所有终端");
                    } else {
                        getPresenter().stopBroadCastToAll();
                        Drawable drawable = getResources().getDrawable(R.mipmap.but_broadcast_h);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvTerminal.setCompoundDrawables(null, drawable, null, null);
                        tvTerminal.setText("广播到终端");
                        Terminal = false;
                    }

                }
                break;
        }
    }

    private void clikDbPlay(boolean isStarts) {//点击大屏点播
        if (!isStarts) {
            if (lstFileBeans == null) {
                ToastUtil.show(mContext, "请选择播放视频");
                return;
            }
            isStart = true;
            getPresenter().startPlay(lstFileBeans.getStrPath(), lstFileBeans.getiID());
            AppDataCache.getInstance().putString(Config.BROADCAST, lstFileBeans.getStrName());
            Drawable drawabless = getResources().getDrawable(R.mipmap.but_play_n);
            drawabless.setBounds(0, 0, drawabless.getMinimumWidth(), drawabless.getMinimumHeight());
            tvDapin.setText("正在处理");
            tvDapin.setEnabled(false);
            tvDapin.setCompoundDrawables(null, drawabless, null, null);
            Drawable drawables = getResources().getDrawable(R.mipmap.but_broadcast_h);
            drawables.setBounds(0, 0, drawables.getMinimumWidth(), drawables.getMinimumHeight());

            tvTerminal.setText("广播到终端");
            tvTerminal.setCompoundDrawables(null, drawables, null, null);
            Drawable drawable = getResources().getDrawable(R.mipmap.but_start_n);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvStop.setCompoundDrawables(null, drawable, null, null);
            isPlay = 1;
        } else {
            isStart = false;
            getPresenter().stopPlay();
            Drawable drawabless = getResources().getDrawable(R.mipmap.but_play_h);
            drawabless.setBounds(0, 0, drawabless.getMinimumWidth(), drawabless.getMinimumHeight());
            tvDapin.setText("正在处理");
            tvDapin.setEnabled(false);
            tvDapin.setCompoundDrawables(null, drawabless, null, null);

            Drawable drawable = getResources().getDrawable(R.mipmap.but_start_d);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvStop.setCompoundDrawables(null, drawable, null, null);
            tvStop.setText("暂停");

            Drawable drawables = getResources().getDrawable(R.mipmap.but_broadcast_d);
            drawables.setBounds(0, 0, drawables.getMinimumWidth(), drawables.getMinimumHeight());
            tvTerminal.setText("广播到终端");
            tvTerminal.setCompoundDrawables(null, drawables, null, null);
        }
    }

}
