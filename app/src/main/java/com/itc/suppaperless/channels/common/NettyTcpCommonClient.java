package com.itc.suppaperless.channels.common;

import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itc.suppaperless.base.BaseBean;
import com.itc.suppaperless.cache.AppDataCache;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import static com.itc.suppaperless.global.Config.MEETING_ID;
import static com.itc.suppaperless.global.Config.USER_ID;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-10 下午3:17
 * @ desc   : Common Client  (登录,常规,媒体)
 */
public class NettyTcpCommonClient {

    public static final String TAG = "NettyTcpMeetingClient";

    private Bootstrap b;
    private EventLoopGroup eventLoopGroup;          //EventLoop线程组
    private ScheduledExecutorService executorService;
    public static Channel channel;
    public static NettyTcpCommonClient nettyClient;

    public boolean isStop = false;
    private boolean isPortError = true;
    public boolean isConnecting = false;                //防止多个连接同时进行导致无限重连
    public boolean isConnSucc = false;

    public String host;                                 //ip地址
    public int port;                                    //端口号

    public static CommandListener commandListener;      //Tcp数据回调监听
    private int mUserId;                                //userID
    private TimerTask task;                             //心跳任务

    /**
     * NettyTcpMeetingClient
     *
     * @return 单例对象
     */
    public synchronized static NettyTcpCommonClient getInstance() {
        if (nettyClient == null) {
            synchronized (NettyTcpCommonClient.class) {
                if (nettyClient == null) {
                    nettyClient = new NettyTcpCommonClient();

                }
            }
        }
        return nettyClient;
    }


    public NettyTcpCommonClient() {
        eventLoopGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(eventLoopGroup);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new DataDecoder());
                pipeline.addLast(new DataEncoder());
                pipeline.addLast("handler", new NettyTcpCommonClientHandler());
                pipeline.addLast(new IdleStateHandler(6, 10, 0));
            }
        });

    }

    /**
     * 连接通道
     *
     * @param mhost
     * @param mport
     */
    public void connServer(String mhost, int mport, int mUserId) {
        this.host = mhost;
        this.port = mport;
        isStop = false;
        isPortError = true;
        this.mUserId = mUserId;
        if (executorService != null && executorService.isShutdown()) {
            executorService.shutdown();
            executorService = null;
        }
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(1);
        }

        executorService.scheduleWithFixedDelay(new Runnable() {
            boolean isConnSucc = true;

            @Override
            public void run() {
                try {
                    //连接服务器
                    if (channel != null && channel.isOpen()) {
                        channel.close();
                        channel = null;
                    }
                    channel = b.connect(host, port).sync().channel();
                    if (channel.isOpen()) {
                        isConnSucc = true;
                        //连接成功后开启心跳
                        heartTask();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: 19-1-15 连接失败回调
                } finally {
                    Log.e("pds", "executorService.shutdown  before isConnSucc = " + isConnSucc);
                    if (isConnSucc) {
                        if (executorService != null) {
                            executorService.shutdown();
                            executorService = null;
                        }
                    }
                }
            }
        }, 1, 3, TimeUnit.SECONDS);
    }


    /**
     * 心跳
     */
    private void heartTask() {
        if (task == null){
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    if (channel != null) {
                        BaseBean heartBeat = new BaseBean();
                        heartBeat.setiCmdEnum(101);
                        sendPackage(heartBeat);
                    }
                }
            };
        }
        timer.schedule(task, 1000, 5000);
    }


    /**
     * 发送心跳
     */
    Timer timer ;


    /**
     * 终止server
     */
    public synchronized void onStop() {
        isStop = true;
        if (channel != null && channel.isOpen()) {
            channel.close();
            channel = null;
        }
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer!=null){
            timer.cancel();
            timer= null;
        }

    }


    /**
     * 统一处理发送数据包
     */
    public static void sendPackage(BaseBean contentData) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(contentData);
        try {
            byte[] data = jsonObject.toString().getBytes();
            //登录通道 iTerminalID 为1 ,其他通道都为 USER_ID
            int cmdEnum = contentData.getiCmdEnum();
            int iTerminalID = 0;
            int cacheUserID = AppDataCache.getInstance().getInt(USER_ID);
            if (601 == cmdEnum || 603 == cmdEnum || 605 == cmdEnum || 607 == cmdEnum || 609 == cmdEnum ||
                    611 == cmdEnum || 613 == cmdEnum || 615 == cmdEnum || 801 == cmdEnum)
                iTerminalID = 1;
                //心跳
            else if (101 == cmdEnum)
                if (0 == cacheUserID)
                    iTerminalID = 1;
                else
                    iTerminalID = cacheUserID;
            else
                iTerminalID = cacheUserID;
            //前三个参数为数据head,最后一个数据为数据body
            BaseBean nettyData = new CommandData((short) contentData.getiCmdEnum(), iTerminalID, data.length + 24, data);
            if (channel != null){
                channel.writeAndFlush(nettyData);
                Log.e("pds", "CMS通道数据" + jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: 19-1-25 清除userID
    }

    /**
     * Send the data to paperless server by using date byte instead of bean.
     * @param iCmdEnum Protocol.
     * @param data
     */
    public static void sendPackage(int iCmdEnum, byte[] data) {
        BaseBean nettyData = new CommandData((short) iCmdEnum, AppDataCache.getInstance().getInt(USER_ID), data.length + 24, data);
        if (channel != null) {
            channel.writeAndFlush(nettyData);
        }
    }

    //上传文件
    public void UploadFile(String strFileName, String strFilePath, long iFileSize, int iFileType) {
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("iCmdEnum", 240);
            object.put("iTerminalID", AppDataCache.getInstance().getInt(USER_ID));
            object.put("iUserID", AppDataCache.getInstance().getInt("userID"));
            object.put("strFileName", strFileName);
            object.put("strFilePath", strFilePath);
            object.put("iFileSize", iFileSize);
            object.put("iFileType", iFileType);
            byte[] data = object.toString().getBytes();
            CommandData nettyData = new CommandData((short) 240, AppDataCache.getInstance().getInt(USER_ID), data.length + 24, data);
            channel.writeAndFlush(nettyData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
