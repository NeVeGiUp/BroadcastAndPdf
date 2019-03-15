package com.itc.suppaperless.meetingmodule.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itc.suppaperless.R;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.utils.ActivityManageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

// 会议标语显示页面
public class ConferenceSoganActivity extends AppCompatActivity {

    @BindView(R.id.iv_sogan)
    ImageView iv_sogan;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_sogan);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ActivityManageUtil.insertActivity(Config.ActivityManage.ConferenceSoganActivity,this);
         url = getIntent().getStringExtra(Config.ConferenceSogan);
        Glide.with(this).load(url).into(iv_sogan);
        iv_sogan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        url=getIntent().getStringExtra(Config.ConferenceSogan);
        Glide.with(this).load(url).into(iv_sogan);
    }
}
