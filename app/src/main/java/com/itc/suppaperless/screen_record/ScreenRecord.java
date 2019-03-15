package com.itc.suppaperless.screen_record;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Surface;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.MediaNettyTcpCommonClient;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.switch_conference.bean.ExitScreenBroadcast;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.itc.suppaperless.global.Config.USER_ID;
import static com.itc.suppaperless.switch_conference.presenter.EnterMeetingPresenter.isApplication;

/**
 * Created by cong on 19-2-15.
 */

public class ScreenRecord {
    private final String TAG = "ScreenRecorder";
    private VirtualDisplay mVirtualDisplay;
    private MediaCodec mediaCodec;
    private Surface mSurface;
    private short num;

    private volatile static ScreenRecord mScreenRecord = null;
    private ScreenRecord () {}

    public static ScreenRecord getInstance() {
        if (mScreenRecord == null) {
            synchronized (ScreenRecord.class) {
                if (mScreenRecord == null) {
                    mScreenRecord = new ScreenRecord();
                }
            }
        }
        return mScreenRecord;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createDisplay(VideoEncodeConfig mVideoEncodeConfig, MediaProjection mMediaProjection){
        MediaFormat mediaFormat = mVideoEncodeConfig.toFormat();
        try {
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaCodec.setCallback(mCodecCallback);
        mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        //创建关联的输入surface
        mSurface = mediaCodec.createInputSurface();
        mediaCodec.start();

        mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG + "-display",
                mVideoEncodeConfig.width, mVideoEncodeConfig.height, 1, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                mSurface, null, null);
    }

    private byte[] mDecodePPSBuffers;//缓冲pps关键信息,每一次发送关键帧之前都发送一次此数据
    private long timeStamp = 0;
    private int secondFrame = 3000;// 3秒一关键帧

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private MediaCodec.Callback mCodecCallback = new MediaCodec.Callback() {
        @Override
        public void onInputBufferAvailable(MediaCodec codec, int index) {

        }

        @Override
        public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
            num = 94;

            if (index > -1) {
                ByteBuffer outputBuffer = codec.getOutputBuffer(index);
                byte[] data = new byte[info.size];
                assert outputBuffer != null;
                outputBuffer.get(data);
                int s = checkDataType(data[4]);
                if(s == 7){ // sps帧
//                            data[4] = 65;
                    mDecodePPSBuffers = data;
                }
                if (isApplication) MediaNettyTcpCommonClient.getInstance().sendByteData(num,data);
                Log.e("pds", data.length + "发送的数据" + Arrays.toString(data));
                codec.releaseOutputBuffer(index, false);
            }

            if (System.currentTimeMillis() - timeStamp >= secondFrame) {//5000毫秒后，设置参数
                timeStamp = System.currentTimeMillis();
                Bundle params = new Bundle();
                params.putInt(MediaCodec.PARAMETER_KEY_REQUEST_SYNC_FRAME, 0);//请求编码器“即时”产生同步帧
                codec.setParameters(params);
                if(mDecodePPSBuffers != null && isApplication) {
                    MediaNettyTcpCommonClient.getInstance().sendByteData(num,mDecodePPSBuffers);
                    Log.e("pds", mDecodePPSBuffers.length + "发送的spspps数据" + Arrays.toString(mDecodePPSBuffers));
                }
            }
        }

        @Override
        public void onError(MediaCodec codec, MediaCodec.CodecException e) {
            e.printStackTrace();
        }

        @Override
        public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {

        }
    };

    private int checkDataType(byte nalu){
        String tString = Integer.toBinaryString((nalu & 0xFF) + 0x100).substring(1);
        int i = Integer.parseInt(tString.substring(3), 2);

        if((nalu & 31) == 5){
//            "I帧"
            return 5;
        }else if(i == 7){
            //            "SPS"
            return 7;
        }else if(i == 8){
//            return "PPS";
            return 8;
        }else if(i == 1){
//            return "P帧";
            return 1;
        }else{
//          不知道是啥
            return -1;
        }
    }

    public void stop(){
        mVirtualDisplay.release();
        mSurface.release();
        if (mediaCodec != null) {
            mediaCodec.stop();
            mediaCodec.release();
            mediaCodec = null;
            exitScreenBroadcast();
        }
    }

    public MediaCodec getMediaCodec() {
        return mediaCodec;
    }

    public void exitScreenBroadcast() {
        ExitScreenBroadcast mExitScreenBroadcast = new ExitScreenBroadcast();
        mExitScreenBroadcast.setiCmdEnum(221);
        mExitScreenBroadcast.setiUserID(AppDataCache.getInstance().getInt(USER_ID));
        NettyTcpCommonClient.sendPackage(mExitScreenBroadcast);
    }
}
