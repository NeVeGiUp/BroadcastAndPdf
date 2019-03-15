package com.itc.suppaperless.switch_conference.ui;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.utils.ActivityManageUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.itc.suppaperless.global.Config.WELCOME_URL;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.btn_enter_meeting)
    Button btnEnterMeeting;

    private String welcomeUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return null;
    }

    @Override
    public void init() {
        ActivityManageUtil.insertActivity(Config.ActivityManage.WelcomeActivity,this);

        welcomeUrl = getIntent().getStringExtra(WELCOME_URL);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDisplayZoomControls(false);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl(welcomeUrl + "&flag=1");
    }


    @OnClick({R.id.btn_enter_meeting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_enter_meeting:
                if (ActivityManageUtil.getMainActivity()!=null&&ActivityManageUtil.getMainActivity() instanceof MainActivity){
                    ((MainActivity) ActivityManageUtil.getMainActivity()).setGone();
                }
                finish();
                break;
        }
    }
}
