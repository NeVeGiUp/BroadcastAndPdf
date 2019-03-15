package com.itc.suppaperless.switch_conference.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itc.suppaperless.MainPagerAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.MediaNettyTcpCommonClient;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meeting_vote.ui.MeetingVoteFragment;
import com.itc.suppaperless.meeting_vote.ui.VoteManageFragment;
import com.itc.suppaperless.meetingmodule.bean.FxFileDialogArgs;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.bean.MuiltFileUploadEvent;
import com.itc.suppaperless.meetingmodule.fragment.MeetingInfoFragment;
import com.itc.suppaperless.meetingmodule.fragment.MeetingIssueFragment;
import com.itc.suppaperless.meetingmodule.fragment.MeetingMaterialFragment;
import com.itc.suppaperless.meetingmodule.fragment.CenterControlFragment;
import com.itc.suppaperless.meetingmodule.fragment.ConferenceSoganFragment;
import com.itc.suppaperless.meetingmodule.fragment.MeetingPersonFragment;
import com.itc.suppaperless.meetingmodule.fragment.ScreenBroadCastFragment;
import com.itc.suppaperless.meetingmodule.fragment.SignInFragment;
import com.itc.suppaperless.meetingmodule.fragment.VideoServiceFragment;
import com.itc.suppaperless.meetingmodule.fragment.TopicManagementFragment;
import com.itc.suppaperless.meetingmodule.fragment.ViewAnnotationFragment;
import com.itc.suppaperless.meetingmodule.ui.DisplayNameActivity;
import com.itc.suppaperless.multifunctionmodule.ui.MultiFunctionPopupWindow;
import com.itc.suppaperless.multifunctionmodule.historymeetingmodule.ui.HistoryMeetingFragment;
import com.itc.suppaperless.multifunctionmodule.webbrowsingmodule.ui.WebBrowsingFragment;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.ui.ElectronicWhiteBoardFragment;
import com.itc.suppaperless.switch_conference.bean.GetUserInformationBean;
import com.itc.suppaperless.switch_conference.contract.EnterMeetingContract;
import com.itc.suppaperless.switch_conference.presenter.EnterMeetingPresenter;
import com.itc.suppaperless.switch_conference.widget.RightNavigationPopView;
import com.itc.suppaperless.switch_conference.widget.SwitchQuitPopView;
import com.itc.suppaperless.utils.ActivityManageUtil;
import com.itc.suppaperless.utils.AppUtils;
import com.itc.suppaperless.utils.FxHelp;
import com.itc.suppaperless.widget.DialogNewInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.itc.suppaperless.global.Config.IP_ADDRESS;
import static com.itc.suppaperless.global.Config.IS_RECONNECT;
import static com.itc.suppaperless.global.Config.MEETING_ID;
import static com.itc.suppaperless.global.Config.MEETING_NAME;
import static com.itc.suppaperless.global.Config.MEETING_ROOM_ID;
import static com.itc.suppaperless.global.Config.PORT_ADDRESS;
import static com.itc.suppaperless.global.Config.USER_ID;
import static com.itc.suppaperless.global.Config.WELCOME_URL;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-7 上午12:07
 * @ desc   : MainActivity
 */
public class MainActivity extends BaseActivity implements EnterMeetingContract.EnterMeetingUI {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.cl_title)
    ConstraintLayout clTitle;
    @BindView(R.id.cl_fragment)
    ConstraintLayout clFragment;
    @BindView(R.id.cl_parent_layout)
    ConstraintLayout clParentLayout;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.lin_indicator)
    LinearLayout linIndicator;
    @BindView(R.id.iv_indicator_left)
    ImageView ivIndicatorLeft;
    @BindView(R.id.iv_indicator_right)
    ImageView ivIndicatorRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.iv_navigation)
    ImageView ivNavigation;
    @BindView(R.id.tv_lost_connection)
    TextView tvLostConnection;
    @BindView(R.id.tv_function_name)
    TextView tvFunctionName;
    @BindView(R.id.rl_welcome_mobile)
    RelativeLayout rl_welcome_mobile;
    @BindView(R.id.app_vision_mobile)
    TextView app_vision_mobile;
    @BindView(R.id.iv_loading_mobile)
    ImageView iv_loading_mobile;
    @BindView(R.id.cl_system_notification)
    ConstraintLayout clSystemNotification;

    private List<Fragment> mStack;
    private HashMap<Integer, Fragment> mFunctionFragmentStack;
    private RightNavigationPopView mRightNavigationPopView;
    private SwitchQuitPopView mSwitchQuitPopView;
    private DialogNewInterface mDialogNewInterface;
    private MainPagerAdapter mMainPagerAdapter;
    private String mMeetingName;
    private boolean isBack = false;//用于判断是否处于功能型fragment
    private Fragment hyxxFragment, yitiFragment, cailiaoFragment, mFeaturesTwoFragment, mFeaturesOneFragment,
            signInFragment, conferenceSoganFragment, viewAnnotationFragment, topicFragment, meetingVoteFragment,
            videoServiceFragment, centerConrolFragment, electronicWhiteBoardFragment, webBrowsingFragment, historyMeetingFragment,
            meetringPersonFragment,ScreenBroadFragment,voteManageFragment;
    private MediaProjectionManager mMediaProjectionManager;//录屏
    private EnterMeetingPresenter mEnterMeetingPresenter;//实现Presenter
    private int mUserID;
    GetUserInformationBean getUserInformationBean;
    private MultiFunctionPopupWindow mMultiFunctionPopupWindow;//会议服务

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new EnterMeetingPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init() {
        ActivityManageUtil.setMainActivity(this);
        AppUtils.registerReceiverNetwork(this);
        if (getIntent().getIntExtra(Config.IS_FIRST, 0) == 1) {
            rl_welcome_mobile.setVisibility(View.VISIBLE);
        }
        app_vision_mobile.setText(AppUtils.getAppInfo(this));
        iv_loading_mobile.setBackgroundResource(R.drawable.progress_loading_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading_mobile.getBackground();
        animationDrawable.start();
        newObject();
        //加载页面
        vpMain.setAdapter(mMainPagerAdapter);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    ivIndicatorLeft.setImageResource(R.mipmap.icon_xz_n);
                    ivIndicatorRight.setImageResource(R.mipmap.icon_wx_n);
                } else {
                    ivIndicatorLeft.setImageResource(R.mipmap.icon_wx_n);
                    ivIndicatorRight.setImageResource(R.mipmap.icon_xz_n);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        initFragment();
        //退出的Dialog
        mDialogNewInterface = new DialogNewInterface(this);
        mDialogNewInterface.setText(getString(R.string.exit_paperless_meeting));
        mDialogNewInterface.setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
            @Override
            public void onClick() {
                System.exit(0);//正常退出
            }
        });
        //连接会议
        String mIPAddress = getIntent().getStringExtra(IP_ADDRESS);
        mMeetingName = getIntent().getStringExtra(MEETING_NAME);
        int mPortAddress = getIntent().getIntExtra(PORT_ADDRESS, -1);
        int mMeetingID = getIntent().getIntExtra(MEETING_ID, -1);
        int mMeetingRoomID = getIntent().getIntExtra(MEETING_ROOM_ID, -1);
        mUserID = AppDataCache.getInstance().getInt(USER_ID);
        AppDataCache.getInstance().putInt(MEETING_ID, mMeetingID);
        AppDataCache.getInstance().putString(Config.IP_MEETING, mIPAddress);
        AppDataCache.getInstance().putInt(Config.PORT_MEETING, mPortAddress);
        Config.strFileServerIP = mIPAddress;
        mEnterMeetingPresenter = (EnterMeetingPresenter) getPresenter();
        mEnterMeetingPresenter.enterMeeting(mIPAddress, mPortAddress, mUserID, mMeetingID, mMeetingRoomID);
    }

    @OnClick({R.id.iv_back, R.id.iv_navigation, R.id.cl_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (((MeetingMaterialFragment) cailiaoFragment).isInRootPath()) {
                    if (isBack) {
                        tvLostConnection.setVisibility(View.INVISIBLE);
                        backInterfaceChange(View.INVISIBLE, View.VISIBLE, R.mipmap.ico_qita_n, false);
                    } else {
                        mSwitchQuitPopView.showAsDropDown(ivBack);
                    }
                } else {
                    ((MeetingMaterialFragment) cailiaoFragment).backToUpperLevel();//CailiaoFragment内部文件夹返回上一层
                }

                break;
            case R.id.iv_navigation:
                mRightNavigationPopView.showAtLocation(clParentLayout, Gravity.RIGHT, 0, 500);
                break;
            case R.id.cl_fragment:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            mDialogNewInterface.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public RightNavigationPopView getmRightNavigationPopView() {
        return mRightNavigationPopView;
    }

    @Override
    protected void onPause() {

        super.onPause();
        if (mSwitchQuitPopView != null && mSwitchQuitPopView.isShowing()) {
            mSwitchQuitPopView.dismiss();
            mSwitchQuitPopView = null;
        }
    }

    @Override
    public void getUserInformationSuccess(GetUserInformationBean getUserInformationBean) {

        this.getUserInformationBean = getUserInformationBean;
        tvName.setText(getUserInformationBean.getStrUserName() + getString(R.string.welcome_you));
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(WELCOME_URL, getUserInformationBean.getStrUrl());
        startActivity(intent);
        AppDataCache.getInstance().putInt(Config.CHAIRMAN, getUserInformationBean.getIChairman());
        AppDataCache.getInstance().putInt(Config.SECRETARY, getUserInformationBean.getISecretary());
        if (mStack.size() == 0) {
            if (getUserInformationBean.getIChairman() == 0 && getUserInformationBean.getISecretary() == 0) {
                mStack.add(mFeaturesOneFragment);
                linIndicator.setVisibility(View.GONE);
            } else {
                mStack.add(mFeaturesOneFragment);
                mStack.add(mFeaturesTwoFragment);
                linIndicator.setVisibility(View.VISIBLE);
            }
            mMainPagerAdapter.notifyDataSetChanged();
        }

        tvLostConnection.setVisibility(View.INVISIBLE);
        tvTitle.setTextColor(getResources().getColor(R.color.white));
        tvTitle.setText(mMeetingName);
    }

    private void initFragment() {
        mFunctionFragmentStack.put(R.string.jizhong_message, hyxxFragment);
        mFunctionFragmentStack.put(R.string.issue_material, yitiFragment);
        mFunctionFragmentStack.put(R.string.meeting_person, meetringPersonFragment);
        mFunctionFragmentStack.put(R.string.meeting_material, cailiaoFragment);
        mFunctionFragmentStack.put(R.string.sign_in_management, signInFragment);
        mFunctionFragmentStack.put(R.string.meeting_slogan, conferenceSoganFragment);
        mFunctionFragmentStack.put(R.string.topic_management, topicFragment);
        mFunctionFragmentStack.put(R.string.centralized_control, centerConrolFragment);
        mFunctionFragmentStack.put(R.string.electronic_whiteboard, electronicWhiteBoardFragment);
        mFunctionFragmentStack.put(R.string.web_browsing, webBrowsingFragment);
        mFunctionFragmentStack.put(R.string.video_service, videoServiceFragment);
        mFunctionFragmentStack.put(R.string.view_comments, viewAnnotationFragment);
        mFunctionFragmentStack.put(R.string.meeting_voting, meetingVoteFragment);
        mFunctionFragmentStack.put(R.string.history_meeting, historyMeetingFragment);
        mFunctionFragmentStack.put(R.string.screen_broad, ScreenBroadFragment);
        mFunctionFragmentStack.put(R.string.voting_management, voteManageFragment);
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.cl_fragment, hyxxFragment);
        mFragmentTransaction.add(R.id.cl_fragment, yitiFragment);
        mFragmentTransaction.add(R.id.cl_fragment, cailiaoFragment);
        mFragmentTransaction.add(R.id.cl_fragment, meetringPersonFragment);
        mFragmentTransaction.add(R.id.cl_fragment, signInFragment);
        mFragmentTransaction.add(R.id.cl_fragment, conferenceSoganFragment);
        mFragmentTransaction.add(R.id.cl_fragment, topicFragment);
        mFragmentTransaction.add(R.id.cl_fragment, centerConrolFragment);
        mFragmentTransaction.add(R.id.cl_fragment, electronicWhiteBoardFragment);
        mFragmentTransaction.add(R.id.cl_fragment, webBrowsingFragment);
        mFragmentTransaction.add(R.id.cl_fragment, viewAnnotationFragment);
        mFragmentTransaction.add(R.id.cl_fragment, videoServiceFragment);
        mFragmentTransaction.add(R.id.cl_fragment, meetingVoteFragment);
        mFragmentTransaction.add(R.id.cl_fragment, historyMeetingFragment);
        mFragmentTransaction.add(R.id.cl_fragment, ScreenBroadFragment);
        mFragmentTransaction.add(R.id.cl_fragment, voteManageFragment);
        mFragmentTransaction.commit();
    }

    public void setGone() {
        rl_welcome_mobile.setVisibility(View.GONE);
    }


    private void changeFragment(Fragment fragment) {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        //显示调用map.entrySet()的集合迭代器
        Iterator<Map.Entry<Integer, Fragment>> iterator = mFunctionFragmentStack.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Fragment> entry = iterator.next();
            mFragmentTransaction.hide(entry.getValue());
        }
        mFragmentTransaction.show(fragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 返回的界面变化
     */
    private void backInterfaceChange(int clFragmentVisible, int tvNameVisible, int ivBackImg, boolean isBack) {
        clFragment.setVisibility(clFragmentVisible);
        tvName.setVisibility(tvNameVisible);
        tvFunctionName.setVisibility(clFragmentVisible);
        ivBack.setImageResource(ivBackImg);
        this.isBack = isBack;
    }

    /**
     * 切换FunctionFragment
     */
    @Override
    public void changeFunctionFragment(int strID) {
        if (!PaperlessApplication.getGlobalConstantsBean().getConnect()) {
            tvLostConnection.setVisibility(View.VISIBLE);
        }


        Fragment fragment = mFunctionFragmentStack.get(strID);
        if (fragment != null) {
            changeFragment(fragment);
            backInterfaceChange(View.VISIBLE, View.INVISIBLE, R.mipmap.ico_fanhui_n, true);
            tvFunctionName.setText(getString(strID));
        } else {
            if (mMultiFunctionPopupWindow == null) {
                mMultiFunctionPopupWindow = new MultiFunctionPopupWindow(this);
            }
            switch (strID) {
                case R.string.meeting_service:
                    mMultiFunctionPopupWindow.setInitType(Config.meetserver);
                    mMultiFunctionPopupWindow.setParentView(clParentLayout);
                    mMultiFunctionPopupWindow.showAtLocation(clParentLayout, Gravity.CENTER, 0, 0);
                    break;
                case R.string.more_features:
                    mMultiFunctionPopupWindow.setInitType(Config.morefunction);
                    mMultiFunctionPopupWindow.showAtLocation(clParentLayout, Gravity.CENTER, 0, 0);
                    break;
            }
        }
    }

    @Override
    public void receivingNetworkState() {
        if (isBack) {
            tvLostConnection.setVisibility(View.VISIBLE);
        }
        NettyTcpCommonClient.getInstance().onStop();
        MediaNettyTcpCommonClient.getInstance().onStop();
        tvTitle.setText(getString(R.string.lost_connection_no_brackets));
        tvTitle.setTextColor(getResources().getColor(R.color.red));
    }

    //集中控制
    @Override
    public void getCenterControlType(int iControlType) {
        switch (iControlType) {
            case 1://欢迎页面
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.putExtra(WELCOME_URL, getUserInformationBean.getStrUrl());
                startActivity(intent);
                if (ActivityManageUtil.getActivity(Config.ActivityManage.DisplayNameActivity) instanceof DisplayNameActivity) {
                    ActivityManageUtil.getActivity(Config.ActivityManage.DisplayNameActivity).finish();
                }
                break;
            case 2://会议信息
                ActivityManageUtil.delAllActivity();
                changeFunctionFragment(R.string.jizhong_message);
                break;
            case 3://显示人名
                Intent intents = new Intent(this, DisplayNameActivity.class);
                intents.putExtra(WELCOME_URL, getUserInformationBean.getStrUrl());
                startActivity(intents);
                if (ActivityManageUtil.getActivity(Config.ActivityManage.WelcomeActivity) instanceof WelcomeActivity) {
                    ActivityManageUtil.getActivity(Config.ActivityManage.WelcomeActivity).finish();
                }

                break;
            case 5://退出标语
                Activity activity = ActivityManageUtil.getActivity(Config.ActivityManage.ConferenceSoganActivity);
                if (activity != null) {
                    activity.finish();
                }
                break;
            case 8://结束会议
                Intent mIntent = new Intent(this, PageConferenceActivity.class);
                mIntent.putExtra(IS_RECONNECT, true);
                this.startActivity(mIntent);
                ActivityManageUtil.delAllActivity();
                this.finish();
                break;
            case 9://开机
                break;
            case 10://关机
                break;
            case 11://统一上升
                break;
            case 12://统一下降
                break;
        }

    }

    //重置人员和用户列表
    @Override
    public void getJiaoliuUserInfo(JiaoLiuUserInfo jiaoLiuUserInfo) {
        for (int i = 0; i < jiaoLiuUserInfo.getLstUser().size(); i++) {
            JiaoLiuUserInfo.LstUserBean lstUserBean = jiaoLiuUserInfo.getLstUser().get(i);
            if (lstUserBean.getIUserID() == getUserInformationBean.getIUserID()) {
                if (lstUserBean.getiIsChairMan() != getUserInformationBean.getIChairman() || lstUserBean.getiIsSecretary() != getUserInformationBean.getISecretary()) {
                    getUserInformationBean.setIChairman(lstUserBean.getiIsChairMan());
                    getUserInformationBean.setISecretary(lstUserBean.getiIsSecretary());
                    if (lstUserBean.getiIsChairMan() == 0 && lstUserBean.getiIsSecretary() == 0) {
                        mStack.clear();
                        mStack.add(mFeaturesOneFragment);
                        linIndicator.setVisibility(View.GONE);
                    } else {
                        mStack.clear();
                        mStack.add(mFeaturesOneFragment);
                        mStack.add(mFeaturesTwoFragment);
                        linIndicator.setVisibility(View.VISIBLE);
                    }
                    mMainPagerAdapter.notifyDataSetChanged();
                }

                break;
            }
        }

    }

    /**
     * 接收系统消息
     */
    private Handler mHandler = new Handler();

    @Override
    public void getMeetingMessage(String strContent) {
        clSystemNotification.setVisibility(View.VISIBLE);
        tvMsg.setText(strContent);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clSystemNotification.setVisibility(View.GONE);
            }
        }, 4000);
    }

    @Override
    public void changeScreenBroadcastStatus(boolean isBroadcasting) {
        mRightNavigationPopView.setBroadcasting(isBroadcasting);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void newObject() {
        mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
        //fragment
        signInFragment = new SignInFragment();
        yitiFragment = new MeetingIssueFragment();
        cailiaoFragment = new MeetingMaterialFragment();
        meetringPersonFragment = new MeetingPersonFragment();
        hyxxFragment = new MeetingInfoFragment();
        hyxxFragment = new MeetingInfoFragment();
        voteManageFragment = new VoteManageFragment();
        centerConrolFragment = new CenterControlFragment();
        electronicWhiteBoardFragment = new ElectronicWhiteBoardFragment();
        webBrowsingFragment = new WebBrowsingFragment();
        topicFragment = new TopicManagementFragment();
        historyMeetingFragment = new HistoryMeetingFragment();
        mFunctionFragmentStack = new HashMap<>();
        conferenceSoganFragment = new ConferenceSoganFragment();
        viewAnnotationFragment = new ViewAnnotationFragment();
        mFeaturesOneFragment = new FeaturesOneFragment();
        mFeaturesTwoFragment = new FeaturesTwoFragment();
        videoServiceFragment = new VideoServiceFragment();
        meetingVoteFragment = new MeetingVoteFragment();
        ScreenBroadFragment=new ScreenBroadCastFragment();
        //viewPager
        mStack = new ArrayList<>();
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mStack);
        //PopView
        mRightNavigationPopView = new RightNavigationPopView(this, mMediaProjectionManager,getWindow().getDecorView());
        mSwitchQuitPopView = new SwitchQuitPopView(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            mEnterMeetingPresenter.applicationScreenBroadcast(0, 1, mUserID);
            MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
            mEnterMeetingPresenter.startRecorder(mediaProjection);
        } else if (requestCode == 1 && resultCode == 1) {//上传文件的回调
            if (data != null) {
                FxFileDialogArgs args = (FxFileDialogArgs) data.getSerializableExtra(FxHelp.ACTIVITY_ARG_PARAM_NAME);
                if (args != null) {
                    if (args.DialogResult == FxHelp.DLGRES_OK) {
                        String[] paths = args.FileNames;
                        EventBus.getDefault().post(new MuiltFileUploadEvent(paths));
                    }
                }
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.unregisterReceiverNetwork(this);
    }
}
