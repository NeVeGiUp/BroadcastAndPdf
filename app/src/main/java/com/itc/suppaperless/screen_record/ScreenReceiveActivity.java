package com.itc.suppaperless.screen_record;

import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.screen_record.event.DecodingScreentEvent;
import com.itc.suppaperless.screen_record.event.IsOpenScreenEvent;
import com.itc.suppaperless.switch_conference.event.StopBroadcastEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pengds on 2017/7/17.
 *
 */

public class ScreenReceiveActivity extends AppCompatActivity implements SurfaceHolder.Callback /*implements TextureView.SurfaceTextureListener*/ {
    int width = 1280;
    int height = 720;
    private int mCount = 0;
    //    private String iUserName = ""; //显示sharer的人名 getIntent.getStringRxtra("iUserName");
    private MediaCodec mediaCodec;
    int framerate = 30;
    private SurfaceView surface_view;
    private ImageView screen_share_re_loading;
    private TextView screen_share_re_sharer; //页面提示屏幕广播分享者
    private boolean isInitOk;
    private boolean isShowScreen;
    private AnimationDrawable drawable;
    private SurfaceHolder mSurfaceHolder;
    public static List<byte[]> mDecodeBuffers  = new LinkedList<>();//缓冲前面的3个数据流

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 保持屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_screen_share_re);
        screen_share_re_loading = findViewById(R.id.screen_share_re_loading);
        screen_share_re_sharer = findViewById(R.id.screen_share_re_sharer);
        drawable = (AnimationDrawable) screen_share_re_loading.getDrawable();
        drawable.start();

        isShowScreen = true;
        EventBus.getDefault().register(this);
        surface_view = findViewById(R.id.screen_share_re_surface_view);
        //surface_view.setSurfaceTextureListener(this);
        mSurfaceHolder = surface_view.getHolder();
        mSurfaceHolder.setFormat(PixelFormat.RGBX_8888);
        mSurfaceHolder.addCallback(this);

        num = 0;

//        iUserName = AppDataCache.getInstance().getString("screenSharer");
//        if (!iUserName.equals("") ){
//            screen_share_re_sharer.setText(iUserName  + this.getResources().getString(R.string.menu_track_screen_sharer));
//        }

        findViewById(R.id.screen_share_re_asyn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private int num = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DecodingScreentEvent event) {//发布线程执行...
        byte[] data = event.getData();

        if(!isInitOk)
            return;

        if (surface_view == null || mediaCodec == null) {
            //在这里做缓存 当不return的时候,  把return里的第一帧数据喂到解码器中, android共享第一帧数据是pps和sps 所以要取后一帧的
            return;
        }
        if(num < 3){
//            Log.e("pds", "解析缓存数据"+ConfigUtil.mDecodeBuffers.size());
            onFrame(mDecodeBuffers.get(num));
            Log.e("pds", num+"喂进去的数据:"+ Arrays.toString(mDecodeBuffers.get(num)));
            num++;
        }else {
            try {
                onFrame(data);
            } catch (IllegalStateException e) {
                finish();
                Log.e("pds", "报错占用了?..." + e.toString());  // 会吃掉媒体通道?...............
                e.printStackTrace();
            }
        }
    }
    private boolean isFirst = true;
    public void onFrame(byte[] buf) {
        if (buf == null)
            return;
        int value = buf[4] & 0x0f;
        if(isFirst){//保证首次喂入解码器的必须是sps
            if (value == 7)
                isFirst = false;
            else
                return;
        }
        int length = buf.length;

        ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();//拿到输入缓冲区,用于传送数据进行编码
        Log.e("pds", "length" + length + "  类型" + Arrays.toString(inputBuffers) + "关键帧的第五个字节" + value);
        //返回一个填充了有效数据的input buffer的索引，如果没有可用的buffer则返回-1.当timeoutUs==0时，该方法立即返回；当timeoutUs<0时，无限期地等待一个可用的input buffer;当timeoutUs>0时，至多等待timeoutUs微妙
        int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);//    <-1时,无限期地等待一个可用的input buffer  会出现:一直等待导致的加载失败
        Log.e("pds", "inputBufferIndex" + inputBufferIndex);
        if (inputBufferIndex >= 0) {//当输入缓冲区有效时,就是>=0
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            inputBuffer.clear();
            inputBuffer.put(buf, 0, length);//往输入缓冲区写入数据,关键点
//            int value = buf[4] & 0x0f;
            if (value == 7) {
                mediaCodec.queueInputBuffer(inputBufferIndex, 0, length, mCount * 30, MediaCodec.BUFFER_FLAG_CODEC_CONFIG);//
            }
            else
                mediaCodec.queueInputBuffer(inputBufferIndex, 0, length, mCount * 30, 0);//将缓冲区入队
            mCount++;
        }

        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);//拿到输出缓冲区的索引
        Log.e("pds", "outputBufferIndex" + outputBufferIndex);
        if(isShowScreen && outputBufferIndex > 0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    drawable.stop();
                    screen_share_re_loading.setVisibility(View.GONE);
                    Log.e("pds", "drawable.isVisible" + drawable.isVisible());
                }
            });
            isShowScreen = false;
        }
        while (outputBufferIndex >= 0) {
            mediaCodec.releaseOutputBuffer(outputBufferIndex, true);//显示并释放资源
            outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);//再次获取数据，如果没有数据输出则outIndex=-1 循环结束
        }
    }

    @Subscribe
    public void onStopBroadcastEvent(StopBroadcastEvent event) {
        mDecodeBuffers.clear();
        if (mediaCodec != null) {
            mediaCodec.stop();
            mediaCodec.release();
            mediaCodec = null;
        }
        ScreenReceiveActivity.this.finish();
    }


    void creatMediaCodec(Surface holder) {
        try {
            //通过多媒体格式名创建一个可用的解码器
            mediaCodec = MediaCodec.createDecoderByType("video/avc");
        } catch (IOException e) {
            Log.e("pds", "通过多媒体格式名创建一个可用的解码器" + e.toString());
            e.printStackTrace();
        }
        //初始化编码器
        MediaFormat mediaformat = MediaFormat.createVideoFormat("video/avc", width, height);
        mediaformat.setInteger(MediaFormat.KEY_FRAME_RATE, 24);
        //指定解码后的帧格式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaformat.setInteger(MediaFormat.KEY_FRAME_RATE, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible);//解码器将编码的帧解码为这种指定的格式,YUV420Flexible是几乎所有解码器都支持的, Y4 是亮度 UV20是色度(色彩及饱和度)
        }

        //设置配置参数，参数介绍 ：
        // format   如果为解码器，此处表示输入数据的格式；如果为编码器，此处表示输出数据的格式。
        //surface   指定一个surface，可用作decode的输出渲染。
        //crypto    如果需要给媒体数据加密，此处指定一个crypto类.
        //   flags  如果正在配置的对象是用作编码器，此处加上CONFIGURE_FLAG_ENCODE 标签。
        mediaCodec.configure(mediaformat, holder, null, 0);
        mediaCodec.start();
        Log.e("pds", "创建解码器");
        num = 0;
        isInitOk = true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("pds", "onDestroy");
        surface_view = null;
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        creatMediaCodec(holder.getSurface());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}