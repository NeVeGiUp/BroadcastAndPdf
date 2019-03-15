package com.itc.suppaperless.player;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.itc.suppaperless.global.Config.FILE_NAME;
import static com.itc.suppaperless.global.Config.FILE_PATH;

public class FFmpegPlayActivity extends AppCompatActivity implements View.OnTouchListener,SeekBar.
        OnSeekBarChangeListener, com.itc.suppaperless.player.XPlay.OnGLSurfaceViewLife {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.iv_play_back)
    ImageView ivPlayBack;
    @BindView(R.id.tv_play_name)
    TextView tvPlayName;
    @BindView(R.id.cl_back_name)
    ConstraintLayout clBackName;
    @BindView(R.id.XPlay)
    com.itc.suppaperless.player.XPlay XPlay;
    @BindView(R.id.iv_play_pause)
    ImageView ivPlayPause;
    @BindView(R.id.tv_now_time)
    TextView tvNowTime;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.cl_operations_navigator)
    ConstraintLayout clOperationsNavigator;
    @BindView(R.id.iv_volume_brightness)
    ImageView ivVolumeBrightness;
    @BindView(R.id.tv_volume_brightness)
    TextView tvVolumeBrightness;
    @BindView(R.id.lin_volume_brightness)
    LinearLayout linVolumeBrightness;

    private int screenWidth;
    private final int DISAPPEARANCE_TIME = 3000;
    private final int DISAPPEARANCE_MSG = 1;
    private final int NOW_TIME = 2;
    private double mPlayPos = -1;
    private float mBrightness;//当前屏幕的亮度
    private GestureDetector mGestureDetector;
    private AudioManager mAudioManager;
    private boolean isPlayOrPause = true;
    private Thread th;
    private boolean isProgressChang = false;
    private volatile boolean isExit = false;
    private boolean isFirstOpen = true;
    private String mFileName;
    private String mFilePath;
    /** 最大声音 */
    private int mMaxVolume;
    /** 当前声音 */
    private int mVolume = -1;
    /** seek进度*/
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (!isExit){
                while (!isProgressChang){
                    int mPosTime = PosTime();
                    if (mPosTime > 0){
                        mHandler.sendEmptyMessage(NOW_TIME);
                    }

                    seekBar.setProgress((int) (PlayPos() * 1000));
                    try {
                        Thread.sleep( 40 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    /** 两秒隐藏操作栏*/
    private boolean isShow;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DISAPPEARANCE_MSG:
                    setHideShow(View.INVISIBLE);
                    break;
                case NOW_TIME:
                    int mPosTime = PosTime();
                    int mTotalDuration = TotalDuration();
                    String mPosTimeStr = TimeUtil.getMSTimes(mPosTime);
                    String mTotalDurationStr = TimeUtil.getMSTimes(mTotalDuration);
                    tvNowTime.setText(mPosTimeStr);
                    tvTotalTime.setText(mTotalDurationStr);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg_play);
        ButterKnife.bind(this);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        screenWidth = PaperlessApplication.getmContext().getResources().getDisplayMetrics().widthPixels;
        mGestureDetector = new GestureDetector(this, new MyGestureListener());

        XPlay.setOnGLSurfaceViewLife(this);
        XPlay.setOnTouchListener(this);
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(this);
        //启动播放进度线程
        th = new Thread(mRunnable);
        th.start();
        mHandler.sendEmptyMessageDelayed(DISAPPEARANCE_MSG,DISAPPEARANCE_TIME);
        setPlayOrPause();

        Intent intent = getIntent();
        mFilePath = intent.getStringExtra(FILE_PATH);
        mFileName = intent.getStringExtra(FILE_NAME);
        tvPlayName.setText(mFileName);
    }
    @OnClick({R.id.iv_play_back, R.id.iv_play_pause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_play_back:
                finish();
                break;
            case R.id.iv_play_pause:
                PlayOrPause();
                setPlayOrPause();
                break;
        }
    }

    private void setVolumeBrightness(int mVisible, int mImageResource) {
        linVolumeBrightness.setVisibility(mVisible);
        ivVolumeBrightness.setImageResource(mImageResource);
    }
    /**
     * 滑动监听
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP://手指离开
//                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, 3000);//延迟3秒发送隐藏控制面板的消息
                setVolumeBrightness(View.GONE, R.mipmap.icon_brightness);
                mBrightness = lpa.screenBrightness;
                mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                break;
        }
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (!isShow){
                setHideShow(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(DISAPPEARANCE_MSG,DISAPPEARANCE_TIME);
            }
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            PlayOrPause();
            setPlayOrPause();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();

            int mXPlayHeight = XPlay.getHeight();

            if (mOldX < screenWidth / 2) {
                // 左边滑动
                setBrightnessSlide((mOldY - y) / (mXPlayHeight * 2));
                setVolumeBrightness(View.VISIBLE, R.mipmap.icon_brightness);
            }else {
                // 右边滑动
                onVolumeSlide((mOldY - y) / (mXPlayHeight * 2));
                setVolumeBrightness(View.VISIBLE, R.mipmap.icon_volume);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
    /**
     * seekBar监听
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mPlayPos = (double)seekBar.getProgress()/(double)seekBar.getMax();
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isProgressChang = true;
        mHandler.removeMessages(DISAPPEARANCE_MSG);
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Seek( (double)seekBar.getProgress()/(double)seekBar.getMax(),isPlayOrPause);
        mHandler.sendEmptyMessageDelayed(DISAPPEARANCE_MSG,DISAPPEARANCE_TIME);
        isProgressChang = false;
        if (isPlayOrPause){
            PlayOrPause();
        }
    }
    /**
     * 滑动改变亮度
     */
    private void setBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;
        }
        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;

        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;

        getWindow().setAttributes(lpa);
        tvVolumeBrightness.setText(((int) (lpa.screenBrightness * 100)) + "%");
    }
    /**
     * 滑动改变声音大小
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0) mVolume = 0;
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;
        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        int i = (int) (index* 1.0  / mMaxVolume * 100);
        tvVolumeBrightness.setText(i + "%");
    }
    /**
     * 设置播放或暂停
     */
    private void setPlayOrPause(){
        if (isPlayOrPause){
            ivPlayPause.setImageResource(R.mipmap.icon_pause);
        }else {
            ivPlayPause.setImageResource(R.mipmap.icon_play);
        }
        isPlayOrPause = !isPlayOrPause;
    }
    /**
     * 设置隐藏或显示
     */
    private void setHideShow(int visibility){
        if (visibility == View.INVISIBLE){
            isShow = false;
        }else {
            isShow = true;
        }
        clBackName.setVisibility(visibility);
        clOperationsNavigator.setVisibility(visibility);
    }

    /**
     * GLSurface生命周期回调
     */
    @Override
    public void GLSurfaceCreated() {
        if (isFirstOpen){
            Open(mFilePath);
            isFirstOpen = false;
        }else {
            PlayOrPause();
            setPlayOrPause();
        }
    }

    @Override
    public void GLSurfaceDestroyed() {
        if (!isPlayOrPause){
            PlayOrPause();
            setPlayOrPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isExit = true;
    }

    public native void PlayOrPause();
    public native void Seek(double pos,boolean isPlay);
    public native double PlayPos();
    public native int TotalDuration();
    public native int PosTime();
    public native void Open(String url);
    public native void SetPause(boolean isPause);
}
