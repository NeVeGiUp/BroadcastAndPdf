package com.itc.suppaperless.fingerprintmodule;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.itc.suppaperless.loginmodule.bean.FingergetDataEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidParameterException;
import java.util.Timer;
import java.util.TimerTask;

import android_serialport_api.SerialPort;


/**
 * 指纹串口工具类
 */
public class FingerprintUtil {
    private static FingerprintUtil INSTANCE;
    private static final String TAG = "Fingerprint";
    public static OutputStream mOutputStreamBox;
    public static FileInputStream mInputStreamBox;
    public static Thread receiveThread = null;
    //    private FingerprintInterface fingerprintInterface;
    public SerialPort boxPort;
    boolean firstRegisterBox = true;
    private static Context mContext;
    private String path = "/dev/ttyS0";
    private int baudrate = 115200;
    public static int allZie = 0;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    FileChannel channel;

    private FingerprintUtil() {


    }

    public void init(Context context) {
        mContext = context;
        setSerialPort(context);
    }

    public static FingerprintUtil getInstance() {
        synchronized (FingerprintUtil.class) {
            if (INSTANCE == null) {
                INSTANCE = new FingerprintUtil();
            }
        }
        return INSTANCE;
    }


    public SerialPort getboxPort() throws SecurityException, IOException, InvalidParameterException {
        return boxPort;
    }


    /**
     * 串口关闭
     */
    public void closeSerialPort() {
        if (boxPort != null) {
            boxPort.close();
            boxPort = null;
            boxFlag = false;
            byteBuffer.clear();
            try {
                mInputStreamBox.close();
                mOutputStreamBox.close();
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 串口初始化出错的提示
     *
     * @param context
     * @param resourceId
     */
    private void DisplayError(Context context, int resourceId) {
        Toast.makeText(context, path + "\n" + "指纹设备出了点问题", Toast.LENGTH_SHORT).show();
    }

    public static boolean boxFlag = true;
    public static byte[] values;
    public static String allValue = "";

    /**
     * 读串口数据的子线程
     */
    public void receiveSerialPort(final Context context) {
        Log.i("test", "接收串口数据");
        /*定义一个handler对象要来接收子线程中接收到的数据
            并调用Activity中的刷新界面的方法更新UI界面
         */
        final Handler handler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    byte[] values = (byte[]) (msg.obj);
                    //对接收到的数据进行处理
                    getValues(values);
                }
            }
        };
        /*创建子线程接收串口数据
         */
        receiveThread = new Thread() {
            @Override
            public void run() {
                Log.i("run", "1");
                while (boxFlag) {
                    Log.i("run", boxFlag + "");
                    try {
                        byte[] readData = new byte[1024];
                        if (mInputStreamBox == null) {
                            Log.i("run", "2");
                            return;
                        }
                        int size = mInputStreamBox.read(readData);
                        allZie += size;
                        Log.i("size", size + "");
                        if (size > 0 && boxFlag) {
                            values = new byte[size];
                            //当前为16进制接收
                            for (int i = 0; i < values.length; i++) {
                                values[i] = (readData[i]);
                            }
                             /*将接收到的数据封装进Message中，然后发送给主线程
                               */
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = values;
                            handler.sendMessage(msg);
                            Thread.sleep(100);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        //启动接收线程
        receiveThread.start();
    }

    /*
    得到指纹特征值
     */
    public void getValues(byte[] values) {
        //对接收到的数据进行处理
        if (values != null) {
            Log.i("allValue", ByteUtil.bytes2HexString(values));
            if (values[0] == 0x6c) {
                allValue = "";
                allValue += ByteUtil.bytes2HexString(values);
                if (values.length == 494) {//当等于494位的话可能是获取特征的数据
                    Log.i("allValuedd", "333");
                    if (values[3] == 38 && values[4] == 0) {
                        Log.i("allValuedd", "获取指纹成功");
                        EventBus.getDefault().post(new FingergetDataEvent(allValue));
                        allValue = "";
                    }
                } else if (values.length == 8) {
                    if (values[3] == 0x51 && values[4] == 0) {
                        if (timer != null) {
                            time = 0;
                            timer.cancel();
                        }
                        sendParams(FingerPrintmodel.getMatchingFPFeature());
                        allValue = "";
                    }

                }
            } else {
                allValue += ByteUtil.bytes2HexString(values);
                Log.i("allValues", allValue);
                byte[] b = ByteUtil.hexString2Bytes(allValue);
                Log.i("allValuesss", ByteUtil.hexString2Bytes(allValue).length + "");
                if (b != null) {
                    if (b.length == 8) {
                        if (b[3] == 0x51 && b[4] == 0) {
                            if (timer != null) {
                                time = 0;
                                timer.cancel();
                            }
                            sendParams(FingerPrintmodel.getMatchingFPFeature());
                        }
                    } else {
                        if (b.length >= 494) {
                            if (b.length == 494) {
                                Log.i("MatchingFPFeature", "333");
                                if (b[3] == 38 && b[4] == 0) {
                                    Log.i("MatchingFPFeature", "获取指纹成功");
                                }
                            } else {
                                byte[] a = ByteUtil.subBytes(b, b.length - 494, 494);
                                Log.i("MatchingFPFeature", "333");
                                if (a[3] == 38 && a[4] == 0) {
                                    Log.i("allValuesmm", ByteUtil.bytes2HexString(a));
                                    Log.i("MatchingFPFeature", "获取指纹成功" + a.length);
                                    EventBus.getDefault().post(new FingergetDataEvent(ByteUtil.bytes2HexString(a)));
                                }
                            }

                        }
                        else if(b.length >=300){
                            EventBus.getDefault().post(new FingergetDataEvent(ByteUtil.bytes2HexString(b)));
                        }
                    }
                    allValue = "";
                }


            }
        }


    }

    /**
     * 初始化串口
     */
    private void setSerialPort(Context context) {
        try {
            if ((path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }
            boxPort = new SerialPort(new File(path), baudrate, 0);
            mOutputStreamBox = boxPort.getOutputStream();
            mInputStreamBox = boxPort.getInputStream();
            channel = mInputStreamBox.getChannel();
            receiveSerialPort(context);

            if (firstRegisterBox) {
                if (mContext == null) {
                    Log.e("-------", "-------finger---mContext null");

                }
                firstRegisterBox = false;
                Log.e("-------", "-------finger---指纹串口注册完毕");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }

    int time = 0;
    Timer timer;

    /**
     * @param发送指令
     */
    public void sendParams(byte[] bytes) {
        if (mOutputStreamBox == null) {
            Log.e("----", "--finger---mOutputStreamBox == null");

            return;
        }
        // TODO 发送指令
        try {
            Log.e("----", "--finger---send bytes:" + ByteUtil.bytes2HexString(bytes));
            mOutputStreamBox.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取指纹特征
     */
    public void getFeatures() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sendParams(FingerPrintmodel.getFeatures());
//                Log.i("time", time + "");
//                time++;
//                if (time == 60) {
//                    time = 0;
//                    timer.cancel();
//                }
            }
        };

        timer.schedule(timerTask, 0, 200);
    }
    public void settimerCancel(){
        timer.cancel();
    }


}
