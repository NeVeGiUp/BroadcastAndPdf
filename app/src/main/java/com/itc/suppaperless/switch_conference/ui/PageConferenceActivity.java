package com.itc.suppaperless.switch_conference.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.loginmodule.ui.LoginActivity;
import com.itc.suppaperless.switch_conference.adapter.SwitchConferenceAdapter;
import com.itc.suppaperless.switch_conference.bean.MeetingListBean;
import com.itc.suppaperless.switch_conference.contract.MeetingListContract;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.switch_conference.presenter.MeetingListPresenter;
import com.itc.suppaperless.utils.PowerUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.DialogNewInterface;
import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.itc.suppaperless.global.Config.IP_ADDRESS;
import static com.itc.suppaperless.global.Config.IS_RECONNECT;
import static com.itc.suppaperless.global.Config.MEETING_ID;
import static com.itc.suppaperless.global.Config.MEETING_NAME;
import static com.itc.suppaperless.global.Config.MEETING_ROOM_ID;
import static com.itc.suppaperless.global.Config.PORT_ADDRESS;
import static com.itc.suppaperless.global.Config.USER_ID;

public class PageConferenceActivity extends BaseActivity implements MeetingListContract.MeetingListUI{

    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.cl_switch_title)
    ConstraintLayout clSwitchTitle;
    @BindView(R.id.mMultiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.rv_conference)
    RecyclerView rvConference;
    @BindView(R.id.tv_sign_out)
    TextView tvSignOut;

    private String mUserID,cmsIP,cmsPort;
    private boolean isReconnect;
    private SwitchConferenceAdapter mSwitchConferenceAdapter;
    private DialogNewInterface dialogNewInterface;
    private MeetingListPresenter mMeetingListPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_page_conference;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return new MeetingListPresenter(this);
    }

    @Override
    public void init() {
        mMeetingListPresenter = (MeetingListPresenter) getPresenter();
        dialogNewInterface = new DialogNewInterface(this).setText(getString(R.string.sign_out));
        dialogNewInterface.setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(PageConferenceActivity.this, LoginActivity.class);
                intent.putExtra("isStartAnimation",false);
                startActivity(intent);
                finish();
            }
        });
        /**
         * 初始化rvConference
         */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        mSwitchConferenceAdapter = new SwitchConferenceAdapter(R.layout.layout_switch_conference_item);
        rvConference.setAdapter(mSwitchConferenceAdapter);
        rvConference.setLayoutManager(gridLayoutManager);
        mSwitchConferenceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MeetingListBean.LstMeetingBean lstMeetingBean = ((SwitchConferenceAdapter)adapter).getData().get(position);
                if (lstMeetingBean.getIStatus()==0){
                    ToastUtil.show(PageConferenceActivity.this,"会议未开始");
                    return;
                }
                Intent intent = new Intent(PageConferenceActivity.this,MainActivity.class);
                intent.putExtra(IP_ADDRESS,lstMeetingBean.getStrServerIP());
                intent.putExtra(PORT_ADDRESS,lstMeetingBean.getIServerPort());
                intent.putExtra(MEETING_ID,lstMeetingBean.getIMeetingID());
                intent.putExtra(MEETING_ROOM_ID,lstMeetingBean.getIMeetingRoomID());
                intent.putExtra(MEETING_NAME,lstMeetingBean.getStrMeetingName());
                intent.putExtra(Config.IS_FIRST,1);
                startActivity(intent);
                NettyTcpCommonClient.getInstance().onStop();
                finish();
            }
        });
        //获取会议列表
        mUserID = AppDataCache.getInstance().getInt(USER_ID) + "";
        cmsIP = AppDataCache.getInstance().getString(IP_ADDRESS);
        cmsPort = AppDataCache.getInstance().getString(PORT_ADDRESS);
        isReconnect = getIntent().getBooleanExtra(IS_RECONNECT, false);
        mMeetingListPresenter.sendMeetingList(mUserID,cmsIP,Integer.parseInt(cmsPort),isReconnect);
        initPermission();
    }

    @OnClick({R.id.iv_refresh, R.id.tv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_refresh:
                mMeetingListPresenter.sendMeetingList(mUserID,cmsIP,Integer.parseInt(cmsPort),isReconnect);
                break;
            case R.id.tv_sign_out:
                dialogNewInterface.show();
                break;
        }
    }

    @Override
    public void getMeetingListSuccess(MeetingListBean data) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mSwitchConferenceAdapter.setNewData(data.getLstMeeting());
    }

    @Override
    public void getMeetingStatusChange() {
        mMeetingListPresenter.sendMeetingList(mUserID,cmsIP,Integer.parseInt(cmsPort),isReconnect);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialogNewInterface.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public Activity getActivity() {
        return this;
    }
    //添加动态权限
    private void initPermission() {
        PowerUtil.verifyStoragePermissions(this);
    }
}
