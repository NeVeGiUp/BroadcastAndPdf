package com.itc.suppaperless.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.itc.suppaperless.R;


public class PrepareNoticeDialog extends Dialog implements View.OnClickListener {
    private Button btn_back,btn_sure;
    private Spinner sp_time_select;
    private TextView tv_close_window;
    private ArrayAdapter arrayAdapter;

    private Context mcontext;
    private IDialogListener dialogClickListener;
    private int issueId;
    private int time = 5;

    public PrepareNoticeDialog(@NonNull Context mcontext, @NonNull IDialogListener dialogClickListener) {
        super(mcontext);
        this.mcontext = mcontext;
        this.dialogClickListener = dialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_management_topic_infom);
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();

    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_sure= (Button) findViewById(R.id.btn_sure);
        tv_close_window = (TextView) findViewById(R.id.tv_close_window);
        sp_time_select= (Spinner) findViewById(R.id.sp_time_select);
        initSpinner();

        btn_back.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        tv_close_window.setOnClickListener(this);
    }

    private void initSpinner(){
        String[] timeaArrays = {"5", "10", "15"};
        arrayAdapter = new ArrayAdapter(mcontext, android.R.layout.simple_spinner_item, timeaArrays);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_time_select.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_close_window:
            case R.id.btn_back:
                this.dismiss();
                break;
            case R.id.btn_sure:
                dialogClickListener.dialogClick(v.getId(), issueId, Integer.parseInt(sp_time_select.getSelectedItem().toString()));
                this.dismiss();
                break;
        }
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }
}
