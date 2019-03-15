package com.itc.suppaperless.fingerprintmodule;

import android.util.Log;

/**
 * Created by zhangwd on 2017/11/17 0017.
 */

public class FingerPrintmodel {

    /**
     * 获取指纹特征
     */
    public static byte[] getFeatures() {
        byte[] bytes = new byte[7];
        bytes[0] = (byte) 0x6c;
        bytes[1] = (byte) 0x63;
        bytes[2] = (byte) 0x62;
        bytes[3] = (byte) 0x51;
        bytes[4] = (byte) 0;
        bytes[5] = (byte) 0;
        bytes[6] = ByteUtil.checkSum((bytes));
        Log.e("获取指纹特征", ByteUtil.bytes2HexString(bytes));
        return bytes;
    }
    /**
     * 获取云端比对时指纹特征点
     */
    public static byte[] getMatchingFPFeature() {
        byte[] bytes = new byte[7];
        bytes[0] = (byte) 0x6c;
        bytes[1] = (byte) 0x63;
        bytes[2] = (byte) 0x62;
        bytes[3] = (byte) 0x26;
        bytes[4] = (byte) 0;
        bytes[5] = (byte) 0;
        bytes[6] = ByteUtil.checkSum((bytes));
        Log.e("获取指纹特征", ByteUtil.bytes2HexString(bytes));
        return bytes;
    }


}