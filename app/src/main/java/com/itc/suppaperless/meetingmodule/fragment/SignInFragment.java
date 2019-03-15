package com.itc.suppaperless.meetingmodule.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.SignInAdapter;
import com.itc.suppaperless.meetingmodule.bean.GetStartOrEndSign;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.PresideSignInfo;
import com.itc.suppaperless.meetingmodule.mvp.contract.SignInContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.SignInPresenter;
import com.itc.suppaperless.meetingmodule.ui.StartSignActivity;
import com.itc.suppaperless.utils.TimeUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.DialogNewInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 签到管理
 */
public class SignInFragment extends BaseFragment<SignInPresenter> implements SignInContract.View {
    @BindView(R.id.tv_sign_num)
    TextView tvSignNum;
    @BindView(R.id.tv_no_sign_num)
    TextView tvNoSignNum;
    @BindView(R.id.tv_start_sign)
    TextView tvStartSign;
    @BindView(R.id.tv_uniform_sign)
    TextView tvUniformSign;
    @BindView(R.id.tv_end_sign)
    TextView tvEndSign;
    @BindView(R.id.rl_all_sign)
    RelativeLayout rlAllSign;
    @BindView(R.id.tv_seat_num)
    TextView tvSeatNum;
    @BindView(R.id.rv_signin)
    RecyclerView rvSignin;
    SignInAdapter adapter;
    @BindView(R.id.cb_select_all)
    CheckBox cbSelectAll;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_sign_time)
    TextView tvSignTime;
    @BindView(R.id.ll_select_shows)
    LinearLayout llSelectShows;
    @BindView(R.id.tv_sign_to_screen)
    TextView tvSignToScreen;
    @BindView(R.id.tv_sign_cancel_screen)
    TextView tvSignCancelScreen;
    private int signNum = 0;
    List<JiaoLiuUserInfo.LstUserBean> lstUserBeans=new ArrayList<>();
    private List<PresideSignInfo> list = new ArrayList<>();
    private DialogNewInterface dialogNewInterface;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public SignInPresenter createPresenter() {
        return new SignInPresenter(this);
    }

    @Override
    public void init() {
        getPresenter();
        adapter = new SignInAdapter(R.layout.item_qiandaocontrol_list);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSignin.setLayoutManager(manager);
        rvSignin.setAdapter(adapter);
        tvNoSignNum.setText(signNum + "人");
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (tvStartSign.getVisibility() == View.GONE) {
                    if (lstUserBeans.get(position).isSeclet()) {
                        lstUserBeans.get(position).setSeclet(false);
                    } else if (lstUserBeans.get(position).getStrSignTime()==null||lstUserBeans.get(position).getStrSignTime().isEmpty()){
                        lstUserBeans.get(position).setSeclet(true);
                    }
                    adapter.notifyDataSetChanged();
                }


            }
        });
    }


    @Override
    public void getSignMessage(PresideSignInfo presideSignInfo) {
        list.add(presideSignInfo);
        signNum += presideSignInfo.getiUserID().length;
        if (lstUserBeans.size()!=0) {
            for (int k = 0; k < list.size(); k++) {
                int[] userID = list.get(k).getiUserID();
                for (int j = 0; j < userID.length; j++) {
                    for (int i = 0; i < lstUserBeans.size(); i++) {
                        if (userID[j] == lstUserBeans.get(i).getIUserID()) {
                            lstUserBeans.get(i).setStrSignTime(list.get(k).getStrTime());
                            break;
                        }
                    }
                }
            }
            adapter.setNewData(lstUserBeans);
        }
        tvNoSignNum.setText(signNum + "人");
    }

    @Override
    public void getPerson(JiaoLiuUserInfo jiaoLiuUserInfo) {
        for (int i=0;i<jiaoLiuUserInfo.getLstUser().size();i++){
            if (jiaoLiuUserInfo.getLstUser().get(i).getIUserID()!=0){
                lstUserBeans.add(jiaoLiuUserInfo.getLstUser().get(i));
            }
        }
        tvSignNum.setText(lstUserBeans.size() + "人");
        if (list.size() != 0) {
            for (int k = 0; k < list.size(); k++) {
                int[] userID = list.get(k).getiUserID();
                for (int j = 0; j < userID.length; j++) {
                    for (int i = 0; i < lstUserBeans.size(); i++) {
                        if (userID[j] == lstUserBeans.get(i).getIUserID()) {
                            lstUserBeans.get(i).setStrSignTime(list.get(k).getStrTime());
                            break;
                        }
                    }
                }
            }
        }
        adapter.setNewData(lstUserBeans);
    }

    //签到控制返回来消息
    @Override
    public void getStartOrEndSign(GetStartOrEndSign startOrEndSign) {
        if (startOrEndSign.getiControlType() == 1 || startOrEndSign.getiControlType() == 3) {
            tvStartSign.setVisibility(View.GONE);
            tvEndSign.setVisibility(View.VISIBLE);
            tvUniformSign.setVisibility(View.VISIBLE);
            tvSignToScreen.setVisibility(View.VISIBLE);
            tvSignCancelScreen.setVisibility(View.VISIBLE);
        } else if (startOrEndSign.getiControlType() == 2) {
            tvStartSign.setVisibility(View.VISIBLE);
            tvEndSign.setVisibility(View.GONE);
            tvUniformSign.setVisibility(View.GONE);
            tvSignToScreen.setVisibility(View.GONE);
            tvSignCancelScreen.setVisibility(View.GONE);
        }
        if (startOrEndSign.getiControlType() == 1) {
            Intent intent = new Intent(mContext, StartSignActivity.class);
            mContext.startActivity(intent);
        }

    }

    @OnClick({R.id.tv_start_sign, R.id.tv_uniform_sign, R.id.tv_end_sign, R.id.ll_select_shows,R.id.tv_sign_to_screen, R.id.tv_sign_cancel_screen})
    public void onViewClicked(View view) {
        Boolean IsConnect = PaperlessApplication.getGlobalConstantsBean().getConnect();
        if (IsConnect) {
            switch (view.getId()) {
                case R.id.tv_start_sign:
                    if (dialogNewInterface == null) {
                        dialogNewInterface = new DialogNewInterface(mContext);
                    }
                    dialogNewInterface.setText("确定要开始签到吗?").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getPresenter().startOrEndSign(1);
                            tvStartSign.setVisibility(View.GONE);
                            tvEndSign.setVisibility(View.VISIBLE);
                            tvUniformSign.setVisibility(View.VISIBLE);
                            tvSignToScreen.setVisibility(View.VISIBLE);
                            tvSignCancelScreen.setVisibility(View.VISIBLE);

                        }
                    }).show();
                    break;
                case R.id.tv_uniform_sign:
                    boolean isCan = false;
                    int[] userIds = new int[lstUserBeans.size()];
                    for (int i = 0; i < lstUserBeans.size(); i++) {
                        if (lstUserBeans.get(i).isSeclet()) {
                            isCan = true;
                            userIds[i] = lstUserBeans.get(i).getIUserID();
                        }
                    }
                    if (isCan) {
                        if (dialogNewInterface == null) {
                            dialogNewInterface = new DialogNewInterface(mContext);
                        }
                        dialogNewInterface.setText("正在进行统一签到操作,\n确定要统一签到吗?").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                            @Override
                            public void onClick() {
                                getPresenter().unifySign(userIds, TimeUtil.getCurrentTime());
                                for (int i = 0; i < lstUserBeans.size(); i++) {
                                    lstUserBeans.get(i).setSeclet(false);
                                }
                                adapter.notifyDataSetChanged();

                            }
                        }).show();

                    } else {
                        ToastUtil.show(getActivity(), "还未选择任何选项..");
                    }

                    break;
                case R.id.tv_end_sign:
                    if (dialogNewInterface == null) {
                        dialogNewInterface = new DialogNewInterface(mContext);
                    }
                    dialogNewInterface.setText("确定要结束签到吗?").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getPresenter().startOrEndSign(2);
                            tvStartSign.setVisibility(View.VISIBLE);
                            tvEndSign.setVisibility(View.GONE);
                            tvUniformSign.setVisibility(View.GONE);
                            tvSignToScreen.setVisibility(View.GONE);
                            tvSignCancelScreen.setVisibility(View.GONE);
                            cbSelectAll.setChecked(false);
                            signNum=0;
                            tvNoSignNum.setText(signNum + "人");
                            for (int i = 0; i < lstUserBeans.size(); i++) {
                                lstUserBeans.get(i).setSeclet(false);
                                lstUserBeans.get(i).setStrSignTime("");
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }).show();

                    break;
                case R.id.ll_select_shows:
                    if (tvStartSign.getVisibility() == View.GONE) {
                        if (cbSelectAll.isChecked()) {
                            cbSelectAll.setChecked(false);
                            for (int i = 0; i < lstUserBeans.size(); i++) {
                                lstUserBeans.get(i).setSeclet(false);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            cbSelectAll.setChecked(true);
                            for (int i = 0; i < lstUserBeans.size(); i++) {
                                if (lstUserBeans.get(i).getStrSignTime()==null||lstUserBeans.get(i).getStrSignTime().isEmpty()){
                                    lstUserBeans.get(i).setSeclet(true);
                                }else {
                                    lstUserBeans.get(i).setSeclet(false);
                                }

                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case R.id.tv_sign_to_screen:
                    if (dialogNewInterface == null) {
                        dialogNewInterface = new DialogNewInterface(mContext);
                    }
                    dialogNewInterface.setText("是否把签到结果显示在大屏?").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getPresenter().signScreen(1);
                            tvSignToScreen.setBackgroundResource(R.drawable.bg_gray_50radius);
                            tvSignCancelScreen.setBackgroundResource(R.drawable.tv_vote_bg);
                            tvSignToScreen.setEnabled(false);
                            tvSignCancelScreen.setEnabled(true);
                        }
                    }).show();

                    break;
                case R.id.tv_sign_cancel_screen:
                    if (dialogNewInterface == null) {
                        dialogNewInterface = new DialogNewInterface(mContext);
                    }
                    dialogNewInterface.setText("是否取消投屏?").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getPresenter().signScreen(0);
                            tvSignCancelScreen.setBackgroundResource(R.drawable.bg_gray_50radius);
                            tvSignToScreen.setBackgroundResource(R.drawable.tv_vote_bg);
                            tvSignToScreen.setEnabled(true);
                            tvSignCancelScreen.setEnabled(false);
                        }
                    }).show();
                    break;
            }
        } else {
            ToastUtil.show(getActivity(), "您已处于离线状态,无法操作", 1);
        }
    }

}
