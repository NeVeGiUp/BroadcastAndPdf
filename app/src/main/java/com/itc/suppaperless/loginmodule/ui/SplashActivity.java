package com.itc.suppaperless.loginmodule.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.utils.AppUtils;

import butterknife.BindView;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-7 下午5:54
 * @ desc   : 应用启动界面
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_start_anim)
    ImageView ivStartAnim;
    @BindView(R.id.tv_start_version)
    TextView tvStartVersion;

    private AnimationDrawable mAnimeStart;


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        if (AppUtils.isCustomizePhone()) {
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
            window.setAttributes(params);
        }
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAnimeStart = (AnimationDrawable)ivStartAnim.getDrawable();
        mhandler.sendEmptyMessageDelayed(1,3000);
    }


    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finish();
            mAnimeStart.stop();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mAnimeStart.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAnimeStart.stop();
    }


    @Override
    public IBaseXPresenter createPresenter() {
        return null;
    }



}
