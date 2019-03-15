package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-7 下午3:16
 * @ desc   : Electronic whiteboard interface, including personal whiteboard, interactive whiteboard
 */
public class ElectronicWhiteBoardFragment extends BaseFragment {

    @BindView(R.id.whiteboard_oneself_tv)
    TextView mWhiteboardOneselfTv;
    @BindView(R.id.whiteboard_mutual_tv)
    TextView mWhiteboardMutualTv;

    @Override
    public int getLayoutId() {
        return R.layout.electronic_whiteboard_activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.whiteboard_oneself_tv, R.id.whiteboard_mutual_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.whiteboard_oneself_tv:  /** personal whiteboard */
                Intent intent = new Intent(mContext, WhiteBoardActivity.class);
                intent.putExtra("operationType", "personalWhiteBoard");
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.alpha_activity_out,
                        R.anim.alpha_activity_in);
                break;
            case R.id.whiteboard_mutual_tv:  /** interactive whiteboard */
                break;
        }
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return null;
    }

    @Override
    public void init() {

    }
}
