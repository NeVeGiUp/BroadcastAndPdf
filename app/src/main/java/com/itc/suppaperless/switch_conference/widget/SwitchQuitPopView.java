package com.itc.suppaperless.switch_conference.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itc.suppaperless.R;
import com.itc.suppaperless.channels.common.MediaNettyTcpCommonClient;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.loginmodule.ui.LoginActivity;
import com.itc.suppaperless.switch_conference.ui.PageConferenceActivity;

import static com.itc.suppaperless.global.Config.IS_RECONNECT;


/**
 * Created by cong on 19-1-17.
 */

public class SwitchQuitPopView extends PopupWindow implements View.OnClickListener{

    private TextView tvSwitchMeeting;
    private TextView tvSignOut;
    private View mSwitchQuitPopView;
    private Context mContext;
    private LinearLayout ll_all;

    public SwitchQuitPopView(Context context) {
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSwitchQuitPopView = layoutInflater.inflate(R.layout.layout_switch_quit_popview, null);
        setContentView(mSwitchQuitPopView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setFocusable(true);
        initView();
    }

    private void initView() {
        tvSignOut = mSwitchQuitPopView.findViewById(R.id.tv_sign_out);
        tvSwitchMeeting = mSwitchQuitPopView.findViewById(R.id.tv_switch_meeting);
        ll_all=mSwitchQuitPopView.findViewById(R.id.ll_all);
        tvSignOut.setOnClickListener(this);
        tvSwitchMeeting.setOnClickListener(this);
        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.tv_switch_meeting:
                mIntent = new Intent(mContext, PageConferenceActivity.class);
                mIntent.putExtra(IS_RECONNECT,true);
                mContext.startActivity(mIntent);
                ((Activity)mContext).finish();
                break;
            case R.id.tv_sign_out:
                mIntent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(mIntent);
                ((Activity)mContext).finish();
                break;

        }
        NettyTcpCommonClient.getInstance().onStop();
        MediaNettyTcpCommonClient.getInstance().onStop();
    }
}
