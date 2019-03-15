package com.itc.suppaperless.meetingmodule.fragment;


import android.view.View;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.meetingmodule.bean.IDialogClickListener;
import com.itc.suppaperless.meetingmodule.mvp.contract.CenterControlContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.CenterControlPresenter;
import com.itc.suppaperless.switch_conference.ui.MainActivity;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.DialogNewInterface;
import com.itc.suppaperless.widget.JzcontrolCloseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 集中控制Fragment
 */
public class CenterControlFragment extends BaseFragment<CenterControlPresenter> implements CenterControlContract.View {


    @BindView(R.id.control_fragment_welcome)
    TextView controlFragmentWelcome;
    @BindView(R.id.control_fragment_metting_info)
    TextView controlFragmentMettingInfo;
    @BindView(R.id.control_fragment_show_name)
    TextView controlFragmentShowName;
    @BindView(R.id.control_fragment_close_metting)
    TextView controlFragmentCloseMetting;
    @BindView(R.id.control_fragment_up)
    TextView controlFragmentUp;
    @BindView(R.id.control_fragment_down)
    TextView controlFragmentDown;
    @BindView(R.id.control_fragment_boot)
    TextView controlFragmentBoot;
    @BindView(R.id.control_fragment_shut_down)
    TextView controlFragmentShutDown;
    @BindView(R.id.control_fragment_unlock)
    TextView controlFragmentUnlock;
    @BindView(R.id.control_fragment_lock)
    TextView controlFragmentLock;
    private JzcontrolCloseDialog jzcontrolDialog;  //关机的dialog

    @Override
    public int getLayoutId() {
        return R.layout.fragment_center_control;
    }

    @Override
    public CenterControlPresenter createPresenter() {
        return new CenterControlPresenter(this);
    }

    @Override
    public void init() {
        getPresenter();
    }

    @OnClick({R.id.control_fragment_welcome, R.id.control_fragment_metting_info, R.id.control_fragment_show_name, R.id.control_fragment_close_metting, R.id.control_fragment_up, R.id.control_fragment_down, R.id.control_fragment_boot, R.id.control_fragment_shut_down,
            R.id.control_fragment_unlock,R.id.control_fragment_lock})
    public void onViewClicked(View view) {
        if (!PaperlessApplication.getGlobalConstantsBean().getConnect()) {
            ToastUtil.show(getActivity(), "您已处于离线状态,无法操作", 1);
        } else {
            switch (view.getId()) {
                case R.id.control_fragment_welcome:
                    new DialogNewInterface(getActivity()).setText("确定要做欢迎界面的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(1);
                            ((MainActivity) getActivity()).getCenterControlType(1);

                        }
                    }).show();

                    break;
                case R.id.control_fragment_metting_info:
                    new DialogNewInterface(getActivity()).setText("确定要做会议信息的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(2);
                            ((MainActivity) getActivity()).getCenterControlType(2);
                        }
                    }).show();

                    break;
                case R.id.control_fragment_show_name:
                    new DialogNewInterface(getActivity()).setText("确定要做显示人名的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(3);
                            ((MainActivity) getActivity()).getCenterControlType(3);
                        }
                    }).show();

                    break;
                case R.id.control_fragment_close_metting:
                    new DialogNewInterface(getActivity()).setText("确定要做结束会议的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(8);
                            ((MainActivity) getActivity()).getCenterControlType(8);
                        }
                    }).show();

                    break;
                case R.id.control_fragment_up:
                    new DialogNewInterface(getActivity()).setText("确定要做降器上升的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(6);
                        }
                    }).show();

                    break;
                case R.id.control_fragment_down:
                    new DialogNewInterface(getActivity()).setText("确定要做升降器下降的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(7);
                        }
                    }).show();

                    break;
                case R.id.control_fragment_boot:
                    new DialogNewInterface(getActivity()).setText("确定要做开机的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(9);
                        }
                    }).show();

                    break;
                case R.id.control_fragment_shut_down:
                    if (jzcontrolDialog == null) {
                        jzcontrolDialog = new JzcontrolCloseDialog(getActivity(), new IDialogClickListener() {
                            @Override
                            public void dialogClick(int id, int type) {
                                if (id == R.id.btn_jzc_sure) {
                                    if (type == 1) {
                                        getmPresenter().centerControl(10);
                                    } else if (type == 2) {
                                        getmPresenter().centerControl(10);
                                        getmPresenter().centerControl(16);
                                    }
                                }
                            }
                        });
                    }
                    jzcontrolDialog.show();

                    break;
                case R.id.control_fragment_lock:
                    new DialogNewInterface(getActivity()).setText("确定要做锁大屏的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(21);
                        }
                    }).show();

                    break;
                case R.id.control_fragment_unlock:
                    new DialogNewInterface(getActivity()).setText("确定要做解锁大屏的操作吗？").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            getmPresenter().centerControl(22);
                        }
                    }).show();

                    break;
            }
        }
    }


}
