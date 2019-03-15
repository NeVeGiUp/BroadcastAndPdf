package com.itc.suppaperless.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.IDialogClickListener;


public class JzcontrolCloseDialog extends Dialog implements View.OnClickListener {
    private LinearLayout ll_close_server_terminal, ll_close_terminal;
    private CheckBox cb_close_server_terminal, cb_close_terminal;
    private Button btn_jzc_back,btn_jzc_sure;
    private TextView tv_close_window,tv_title,tv_content_one,tv_content_two;

    private Context mcontext;
    private IDialogClickListener dialogClickListener;

    public JzcontrolCloseDialog(@NonNull Context mcontext, @NonNull IDialogClickListener dialogClickListener) {
        super(mcontext);
        this.mcontext = mcontext;
        this.dialogClickListener = dialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jizhong_control_shutdown);
        //初始化界面控件
        initView();
    }

    private void initView() {
        ll_close_server_terminal = (LinearLayout) findViewById(R.id.ll_close_server_terminal);
        ll_close_terminal = (LinearLayout) findViewById(R.id.ll_close_terminal);
        cb_close_server_terminal = (CheckBox) findViewById(R.id.cb_close_server_terminal);
        cb_close_terminal = (CheckBox) findViewById(R.id.cb_close_terminal);
        btn_jzc_back = (Button) findViewById(R.id.btn_jzc_back);
        btn_jzc_sure= (Button) findViewById(R.id.btn_jzc_sure);
        tv_close_window = (TextView) findViewById(R.id.tv_close_window);
        tv_title=findViewById(R.id.tv_title);
        tv_content_one=findViewById(R.id.tv_content_one);
        tv_content_two=findViewById(R.id.tv_content_two);

        ll_close_server_terminal.setOnClickListener(this);
        ll_close_terminal.setOnClickListener(this);
        btn_jzc_back.setOnClickListener(this);
        btn_jzc_sure.setOnClickListener(this);
        tv_close_window.setOnClickListener(this);
        cb_close_server_terminal.setOnClickListener(this);
        cb_close_terminal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_close_server_terminal:
                cb_close_server_terminal.setChecked(true);
                cb_close_terminal.setChecked(false);
                break;
            case R.id.ll_close_terminal:
                cb_close_server_terminal.setChecked(false);
                cb_close_terminal.setChecked(true);
                break;
            case R.id.cb_close_server_terminal:
                if (cb_close_terminal.isChecked()){
                    cb_close_terminal.setChecked(false);
                }
                break;
            case R.id.cb_close_terminal:
                if (cb_close_server_terminal.isChecked()){
                    cb_close_server_terminal.setChecked(false);
                }
                break;
            case R.id.tv_close_window:
            case R.id.btn_jzc_back:
                this.dismiss();
                break;
            case R.id.btn_jzc_sure:
                if (cb_close_terminal.isChecked()){
                    dialogClickListener.dialogClick(R.id.btn_jzc_sure, 1);
                    this.dismiss();
                }else if (cb_close_server_terminal.isChecked()){
                    dialogClickListener.dialogClick(R.id.btn_jzc_sure, 2);
                    this.dismiss();
                }
                break;
        }
    }
    public void setText(String title,String oneText,String twoText){
        tv_title.setText(title);
        tv_content_one.setText(oneText);
        tv_content_two.setText(twoText);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        cb_close_server_terminal.setChecked(false);
        cb_close_terminal.setChecked(false);
    }
}
