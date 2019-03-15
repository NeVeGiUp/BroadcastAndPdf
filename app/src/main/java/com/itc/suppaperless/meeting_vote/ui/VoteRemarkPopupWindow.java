package com.itc.suppaperless.meeting_vote.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.meeting_vote.presenter.UserVotePresenter;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.SoftKeyboardUtils;
import com.itc.suppaperless.utils.ToastUtil;

import static com.itc.suppaperless.global.Config.USER_ID;

/**
 * Create by zhengwp on 19-3-7.3
 *
 * MeetServer_Else_Requirement.
 */
public class VoteRemarkPopupWindow extends PopupWindow implements View.OnClickListener {
    private LinearLayout ll_root_view;
    private RelativeLayout rl_parent;
    private ImageView iv_close;
    private TextView tv_else_btn_send;
    private TextView tv_else_btn_cancel;
    private EditText et_else_input;

    private Context mContext;
    private LayoutInflater mInflater;
    private UserVotePresenter mUserVotePresenter;
    private int iVoteID;
    private int[] array;


    public VoteRemarkPopupWindow(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        intPopup();
        initListener();
    }

    private void intPopup() {
        View view = mInflater.inflate(R.layout.popupwindow_vote_remarks, null);
        setContentView(view);
        ll_root_view = view.findViewById(R.id.ll_root_view);
        rl_parent = view.findViewById(R.id.rl_parent);
        iv_close = view.findViewById(R.id.iv_close);
        tv_else_btn_send = view.findViewById(R.id.tv_else_btn_send);
        tv_else_btn_cancel = view.findViewById(R.id.tv_else_btn_cancel);
        et_else_input = view.findViewById(R.id.et_else_input);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rl_parent.getLayoutParams();
        lp.width = (int) (ScreenUtil.getScreenWidth(mContext) * 0.8);
        lp.height = (int) (ScreenUtil.getScreenHeight(mContext) * 0.7);
        rl_parent.setLayoutParams(lp);
    }

    public void initListener(){
        ll_root_view.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        tv_else_btn_send.setOnClickListener(this);
        tv_else_btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_else_btn_send:
                String mRemarks = et_else_input.getText().toString();
                if (mRemarks.isEmpty()){
                    ToastUtil.show(mContext,R.string.fill_remarks);
                    return;
                }
                mUserVotePresenter.userVote(AppDataCache.getInstance().getInt(USER_ID),iVoteID,mRemarks,array);
                break;
            case R.id.tv_else_btn_cancel:
                dismiss();
                break;

            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        et_else_input.setText("");
    }

    private void setUserVoteParameter(UserVotePresenter mUserVotePresenter, int iVoteID,int[] array){
        this.mUserVotePresenter = mUserVotePresenter;
        this.iVoteID = iVoteID;
        this.array = array;
    }
}
