package com.itc.suppaperless.meetingmodule.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.mvp.contract.StartSignContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.StartSignPresenter;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.utils.UploadFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 开始签到
 */

public class StartSignActivity extends BaseActivity<StartSignPresenter> implements StartSignContract.View {

    Bitmap mBitmap;
    @BindView(R.id.tv_sign_name)
    TextView tvSignName;
    @BindView(R.id.iv_view)
    ImageView ivView;
    @BindView(R.id.bt_shouxie_sign_cancel)
    TextView btShouxieSignCancel;
    @BindView(R.id.iv_start_sign)
    TextView ivStartSign;
    @BindView(R.id.bt_shouxie_sign_sure)
    TextView btShouxieSignSure;
    @BindView(R.id.ll_name)
    LinearLayout ll_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_sign;
    }

    @Override
    public StartSignPresenter createPresenter() {
        return new StartSignPresenter(this);
    }

    @Override
    public void init() {
        getPresenter();
        tvSignName.setText(AppDataCache.getInstance().getString(Config.USER_NAME));

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    private void showImage() {
        // 创建一张空白图片
        mBitmap = Bitmap.createBitmap(ivView.getMeasuredWidth(), ivView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        // 创建一张画布
        Canvas canvas = new Canvas(mBitmap);
        // 画布背景为白色
        canvas.drawColor(Color.WHITE);
        // 创建画笔
        Paint paint = new Paint();
        // 画笔颜色为蓝色
        paint.setColor(Color.BLUE);
        // 宽度5个像素
        paint.setStrokeWidth(5);
        // 先将白色背景画上
        canvas.drawBitmap(mBitmap, new Matrix(), paint);
        ivView.setImageBitmap(mBitmap);

        ivView.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取手按下时的坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取手移动后的坐标
                        int endX = (int) event.getX();
                        int endY = (int) event.getY();
                        // 在开始和结束坐标间画一条线
                        canvas.drawLine(startX, startY, endX, endY, paint);
                        // 刷新开始坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        ivView.setImageBitmap(mBitmap);
                        break;
                }
                return true;
            }
        });

    }


    @OnClick({R.id.bt_shouxie_sign_cancel, R.id.bt_shouxie_sign_sure,R.id.iv_start_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_shouxie_sign_cancel:
                ivView.setImageBitmap(null);
                showImage();
                break;
            case R.id.bt_shouxie_sign_sure://确定签到
                File file = new File(Config.SIGNIPATHMAGE,Config.SIGNIFILEMAGE);
                OutputStream stream;
                try {
                    stream = new FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UploadFileUtil.getInstance().UploadFile(Config.SIGNIPATHMAGE + Config.SIGNIFILEMAGE,1);

                break;
            case R.id.iv_start_sign:
                ivView.setVisibility(View.VISIBLE);
                ivStartSign.setVisibility(View.INVISIBLE);
                ll_name.setVisibility(View.GONE);
                btShouxieSignCancel.setVisibility(View.VISIBLE);
                btShouxieSignSure.setVisibility(View.VISIBLE);
                showImage();
                break;
        }
    }

    @Override
    public void getJiaoliuUserInfo(JiaoLiuUserInfo jiaoLiuUserInfo) {
        for (int i=0;i<jiaoLiuUserInfo.getLstUser().size();i++){
            if (AppDataCache.getInstance().getInt(Config.USER_ID)==jiaoLiuUserInfo.getLstUser().get(i).getIUserID()){
                tvSignName.setText(jiaoLiuUserInfo.getLstUser().get(i).getStrUserName());
            }
        }
    }

    @Override
    public void signSucceeful() {//签到成功
        ToastUtil.show(this,"签到成功");
        finish();
    }
}
