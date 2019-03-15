package com.itc.suppaperless.meetingmodule.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.SoganAdapter;
import com.itc.suppaperless.meetingmodule.bean.MeetingSloganInfo;
import com.itc.suppaperless.meetingmodule.bean.Sogan;
import com.itc.suppaperless.meetingmodule.bean.SoganList;
import com.itc.suppaperless.meetingmodule.mvp.contract.ConferenceSoganContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.ConferenceSoganPresenter;
import com.itc.suppaperless.meetingmodule.ui.ConferenceSoganActivity;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.utils.UiUtil;
import com.itc.suppaperless.widget.DialogNewInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 会议标语
 */
public class ConferenceSoganFragment extends BaseFragment<ConferenceSoganPresenter> implements ConferenceSoganContract.View {


    @BindView(R.id.slogan_left_txt_show)
    TextView sloganLeftTxtShow;
    @BindView(R.id.slogan_left_image_show)
    ImageView sloganLeftImageShow;
    @BindView(R.id.slogan_left_check)
    TextView sloganLeftCheck;
    @BindView(R.id.slogan_left_exit)
    TextView sloganLeftExit;
    @BindView(R.id.slogan_left_change)
    TextView sloganLeftChange;
    @BindView(R.id.slogan_left_bottom_ll)
    LinearLayout sloganLeftBottomLl;
    @BindView(R.id.meeting_slogan_left_all_view)
    RelativeLayout meetingSloganLeftAllView;
    @BindView(R.id.slogan_right_list)
    RecyclerView sloganRightList;
    private SoganAdapter mAdapter;
    private List<SoganList> data;
    private boolean isFirst;
    private int isCheckScreen = 0;
    private SoganList soganList;
    private DialogNewInterface dialogNewInterface = null;
    private boolean isZiji=false;//判斷是否偉自己發起的切換文檔

    public ConferenceSoganFragment() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_conference_sogan;
    }

    @Override
    public ConferenceSoganPresenter createPresenter() {
        return new ConferenceSoganPresenter(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !isFirst) {
            isFirst = true;
            getPresenter().sendSogan();
        }
    }

    @Override
    public void init() {
        getPresenter();
        mAdapter = new SoganAdapter(R.layout.item_slogan);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        sloganRightList.setAdapter(mAdapter);
        sloganRightList.setLayoutManager(manager);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                soganList = data.get(position);
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setHas(true);
                    } else {
                        data.get(i).setHas(false);
                    }
                }
                mAdapter.setIsUpdateAllData(false);
            }
        });

    }

    @Override
    public void getSogan(Sogan sogan) {
        Glide.with(mContext).load(sogan.getStrUrl()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(sloganLeftImageShow);
        sloganLeftTxtShow.setText("");
        if (sogan.getStrUrl() != null && !sogan.getStrUrl().isEmpty()&&sogan.getiStatus()==1&&!isZiji) {
            Intent intent = new Intent(mContext, ConferenceSoganActivity.class);
            intent.putExtra(Config.ConferenceSogan, sogan.getStrUrl());
            mContext.startActivity(intent);
        }
        isZiji=false;
    }


    @Override
    public void getSoganList(MeetingSloganInfo meetingSloganInfo) {
        data = meetingSloganInfo.getLstSlogan();
        mAdapter.setNewData(meetingSloganInfo.getLstSlogan());
        for (int i = 0; i < data.size(); i++) {
            if (meetingSloganInfo.getICurSloganID() == data.get(i).getiSloganID()) {
                Glide.with(mContext).load(data.get(i).getStrUrl()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(sloganLeftImageShow);
                sloganLeftTxtShow.setText(data.get(i).getStrSloganName());
                soganList = data.get(i);
            }
        }

    }

    @Override
    public void getSoganAddOrD(SoganList soganList) {
        if (data != null) {
            switch (soganList.getiUpdateType()) {
                case 1://增加
                    boolean equally = false;
                    for (int i = 0; i < data.size(); i++) {
                        if (soganList.getiSloganID() == data.get(i).getiSloganID()) {
                            equally = true;
                        }
                    }
                    if (!equally) {
                        data.add(soganList);
                    }

                    break;
                case 2://修改
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getiSloganID() == soganList.getiSloganID()) {
                            data.set(i, soganList);
                            break;
                        }
                    }
                    if (this.soganList != null) {
                        if (this.soganList.getiSloganID() == soganList.getiSloganID()) {
                            this.soganList = soganList;
                            sloganLeftTxtShow.setText(soganList.getStrSloganName());
                            Glide.with(mContext).load(soganList.getStrUrl()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(sloganLeftImageShow);
                        }
                    }

                    break;
                case 3://删除
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getiSloganID() == soganList.getiSloganID()) {
                            data.remove(i);
                        }
                        if (this.soganList != null) {
                            if (soganList.getiSloganID() == this.soganList.getiSloganID()) {
                                this.soganList = null;
                                sloganLeftTxtShow.setText("");
                                sloganLeftImageShow.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.tishi_wushuju));

                            }
                        }
                    }

                    break;
            }
            mAdapter.setIsUpdateAllData(true);
        }

    }

    @OnClick({R.id.slogan_left_check, R.id.slogan_left_exit, R.id.slogan_left_change})
    public void onViewClicked(View view) {
        if (!PaperlessApplication.getGlobalConstantsBean().getConnect()) {
            ToastUtil.show(getActivity(), "您已处于离线状态,无法操作", 1);
            return;
        }
        switch (view.getId()) {
            case R.id.slogan_left_check:
                if (isCheckScreen == 0) {//R.drawable.ico_gou_h
                    isCheckScreen = 1;
                    UiUtil.setTextViewDrawable(mContext, 0, sloganLeftCheck, R.mipmap.ico_gou_h);
                } else {
                    UiUtil.setTextViewDrawable(mContext, 0, sloganLeftCheck, R.mipmap.ico_gou_d);
                    isCheckScreen = 0;
                }
                break;
            case R.id.slogan_left_exit:
                if (dialogNewInterface == null) {
                    dialogNewInterface = new DialogNewInterface(mContext);
                }

                dialogNewInterface.setText("确定要做退出标语的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        getPresenter().exitSogan(5);
                    }
                }).show();
                break;
            case R.id.slogan_left_change:
                if (dialogNewInterface == null) {
                    dialogNewInterface = new DialogNewInterface(mContext);
                }
                dialogNewInterface.setText("确定要做切换标语的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        if (soganList != null) {
                            isZiji=true;
                            getPresenter().changeSogan(soganList.getiSloganID(), isCheckScreen);
                            Log.i("isCheckScreen",isCheckScreen+"");
                        } else {
                            ToastUtil.show(mContext, "请选择标语");
                        }

                    }
                }).show();
                break;
        }
    }
}
