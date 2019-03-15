package com.itc.suppaperless.meetingmodule.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.utils.ActivityManageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.itc.suppaperless.global.Config.WELCOME_URL;

/**
 * 显示人名
 */
public class DisplayNameActivity extends AppCompatActivity {
    @BindView(R.id.mWebView)
    WebView mWebView;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_name);
        bind = ButterKnife.bind(this);
        ActivityManageUtil.insertActivity(Config.ActivityManage.DisplayNameActivity, this);
        String welcomeUrl = getIntent().getStringExtra(WELCOME_URL);
        WebSettings webSettings = mWebView.getSettings();
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(welcomeUrl);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


}
