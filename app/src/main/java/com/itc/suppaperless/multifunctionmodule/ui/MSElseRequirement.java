package com.itc.suppaperless.multifunctionmodule.ui;

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
import android.widget.Toast;

import com.itc.suppaperless.R;
import com.itc.suppaperless.multifunctionmodule.util.MeetServerUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.SoftKeyboardUtils;
import com.itc.suppaperless.utils.StringUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.DialogNewInterface;

/**
 * Create by zhengwp on 19-3-7.3
 *
 * MeetServer_Else_Requirement.
 */
public class MSElseRequirement extends PopupWindow implements View.OnClickListener {
    private final String TAG = "meet-server-else";
    private LinearLayout ll_root_view;
    private RelativeLayout rl_parent;
    private ImageView iv_close;
    private TextView tv_else_btn_send;
    private TextView tv_else_btn_cancel;
    private EditText et_else_input;

    private Context mcontext;
    private LayoutInflater mInflater;

    public MSElseRequirement(Context context) {
        this.mcontext = context;
        mInflater = LayoutInflater.from(mcontext);
        intPopup();
        initListener();
    }

    private void intPopup() {
        View view = mInflater.inflate(R.layout.popupwindow_meetserver_else, null);
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
        lp.width = (int) (ScreenUtil.getScreenWidth(mcontext) * 0.8);
        lp.height = (int) (ScreenUtil.getScreenHeight(mcontext) * 0.7);
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
                if (!StringUtil.isEmpty(et_else_input.getText().toString())){
                    ToastUtil.show(mcontext, mcontext.getResources().getString(R.string.meetserver_send_success), Toast.LENGTH_SHORT);
                    MeetServerUtil.seneMeetServer2PS(et_else_input.getText().toString());
                    dismiss();
                }else {
                    ToastUtil.show(mcontext, mcontext.getResources().getString(R.string.null_text), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.tv_else_btn_cancel:
                if (!StringUtil.isEmpty(et_else_input.getText().toString())){
                    new DialogNewInterface(mcontext).setText(mcontext.getString(R.string.meetserver_input)).setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                        @Override
                        public void onClick() {
                            dismiss();
                        }
                    }).show();
                }else {
                    dismiss();
                }
                break;
            case R.id.ll_root_view:
                /** hide the keyboara if show. */
                if (SoftKeyboardUtils.isSoftShowing((Activity) mcontext)){
                    SoftKeyboardUtils.hideSoftKeyboard(((Activity) mcontext));
                }
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
}
