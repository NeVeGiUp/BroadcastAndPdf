package com.itc.suppaperless.screen_record;

import android.media.MediaCodec;

import com.itc.suppaperless.base.BaseBean;

import java.nio.ByteBuffer;

/**
 * Created by cong on 19-1-17.
 */

public class SendScreenBroadcastBean extends BaseBean{
    private MediaCodec.BufferInfo info;
    private ByteBuffer encodedData;

    public MediaCodec.BufferInfo getInfo() {
        return info;
    }

    public void setInfo(MediaCodec.BufferInfo info) {
        this.info = info;
    }

    public ByteBuffer getEncodedData() {
        return encodedData;
    }

    public void setEncodedData(ByteBuffer encodedData) {
        this.encodedData = encodedData;
    }
}
